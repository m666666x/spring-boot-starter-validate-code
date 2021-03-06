package spring.boot.starter.validate.code;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import com.alibaba.fastjson.JSON;
import spring.boot.starter.validate.code.properties.ValidateCodeProperties;

/**
 * @author zhailiang
 *
 */
//@Configuration
@WebFilter(urlPatterns = "/*", filterName = "validateCodeFilter")
@Order(2)
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {
  /**
   * 系统配置信息
   */
  @Autowired
  private ValidateCodeProperties codeProperties;
  /**
   * 系统中的校验码处理器
   */
  @Autowired
  private ValidateCodeProcessorHolder validateCodeProcessorHolder;
  /**
   * 存放所有需要校验验证码的url
   */
  private Map<String, ValidateCodeType> urlMap = new HashMap<>();
  /**
   * 验证请求url与配置的url是否匹配的工具类
   */
  private AntPathMatcher pathMatcher = new AntPathMatcher();

  /**
   * 初始化要拦截的url配置信息
   */
  @Override
  public void afterPropertiesSet() throws ServletException {
    super.afterPropertiesSet();

    addUrlToMap(codeProperties.getImage().getUrl(), ValidateCodeType.IMAGE);

    addUrlToMap(codeProperties.getSms().getUrl(), ValidateCodeType.SMS);
  }

  /**
   * 讲系统中配置的需要校验验证码的URL根据校验的类型放入map
   * 
   * @param urlString
   * @param type
   */
  protected void addUrlToMap(String urlString, ValidateCodeType type) {
    if (StringUtils.isNotBlank(urlString)) {
      String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
      for (String url : urls) {
        urlMap.put(url, type);
      }
    }
  }


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws ServletException, IOException {

    ValidateCodeType type = getValidateCodeType(request);
    if (type != null) {
      logger.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + type);
      try {
        validateCodeProcessorHolder.findValidateCodeProcessor(type)
            .validate(new ServletWebRequest(request, response));
        logger.info("验证码校验通过");
      } catch (ValidateCodeException exception) {
        onValidateFailure(request, response, exception);
        return;
      }
    }
    chain.doFilter(request, response);
  }

  /**
   * 获取校验码的类型，如果当前请求不需要校验，则返回null
   * 
   * @param request
   * @return
   */
  private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
    ValidateCodeType result = null;
    if (StringUtils.equalsIgnoreCase(request.getMethod(), "post")) {
      Set<String> urls = urlMap.keySet();
      for (String url : urls) {
        if (pathMatcher.match(url, request.getRequestURI())) {
          result = urlMap.get(url);
        }
      }
    }
    return result;
  }

  public void onValidateFailure(HttpServletRequest request, HttpServletResponse response,
      ValidateCodeException exception) throws IOException, ServletException {
    logger.warn("验证码验证失败",exception);

    Map<String,Object> map = new HashMap<>();
    map.put("timestamp",new Date().getTime());
    map.put("message",exception.getMessage());
    map.put("status",HttpStatus.INTERNAL_SERVER_ERROR.value());
    
    
    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write(JSON.toJSONString(map)); 
  }

}
