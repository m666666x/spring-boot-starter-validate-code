package spring.boot.starter.validate.code.image;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import spring.boot.starter.validate.code.ValidateCodeException;
import spring.boot.starter.validate.code.impl.AbstractValidateCodeProcessor;
import spring.boot.starter.validate.code.properties.ValidateCodeConstants;

/**
 * 图片验证码处理器
 * 
 * @author zhailiang
 *
 */
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

  /**
   * 发送图形验证码，将其写到响应中
   */
  @Override
  protected void send(ServletWebRequest request, ImageCode imageCode) throws Exception {
    ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
  }

  protected String getPersistenceKey(ServletWebRequest request) throws ValidateCodeException {
    String paramName = ValidateCodeConstants.DEFAULT_PARAMETER_NAME_USERNAME;
    HttpServletRequest httpRequest = request.getRequest();
    String username = httpRequest.getHeader(paramName);
    try {
      if (StringUtils.isEmpty(username)) {
        username = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
      }
    } catch (ServletRequestBindingException e) {
      throw new ValidateCodeException("从请求中获取手机号时发生异常", e);
    }
    return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase() + ":"
    + username;
  }
}
