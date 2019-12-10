package spring.boot.starter.validate.code.sms;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import spring.boot.starter.sms.SmsSender;
import spring.boot.starter.validate.code.ValidateCode;
import spring.boot.starter.validate.code.ValidateCodeException;
import spring.boot.starter.validate.code.impl.AbstractValidateCodeProcessor;
import spring.boot.starter.validate.code.properties.ValidateCodeConstants;

/**
 * 短信验证码处理器
 * 
 * @author zhailiang
 *
 */
@Component("smsValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

  /**
   * 短信验证码发送器
   */
  @Autowired
  private SmsSender smsSender;

  @Override
  protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {

    HttpServletRequest httpRequest = request.getRequest();
    String mobile =
        ServletRequestUtils.getRequiredStringParameter(httpRequest, ValidateCodeConstants.DEFAULT_PARAMETER_NAME_MOBILE);
    String templateNo =
        ServletRequestUtils.getRequiredStringParameter(httpRequest, ValidateCodeConstants.DEFAULT_PARAMETER_NAME_TEMPLATE);
    smsSender.send(mobile, templateNo, validateCode.getCode());
  }

  protected String getPersistenceKey(ServletWebRequest request) throws ValidateCodeException {
    String paramName = ValidateCodeConstants.DEFAULT_PARAMETER_NAME_MOBILE;
    HttpServletRequest httpRequest = request.getRequest();
    String mobile = httpRequest.getHeader(paramName);
    try {
      if (StringUtils.isEmpty(mobile)) {
        mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
      }
    } catch (ServletRequestBindingException e) {
      throw new ValidateCodeException("从请求中获取手机号时发生异常", e);
    }
    return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase() + ":"
        + mobile;
  }
}
