package spring.boot.starter.validate.code.sms;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import spring.boot.starter.validate.code.ValidateCode;
import spring.boot.starter.validate.code.ValidateCodeGenerator;
import spring.boot.starter.validate.code.properties.ValidateCodeProperties;

/**
 * @author zhailiang
 *
 */
//@Component("smsValidateCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

  @Autowired
  private final ValidateCodeProperties codeProperties;

  public SmsCodeGenerator(ValidateCodeProperties codeProperties) {
    super();
    this.codeProperties = codeProperties;
  }


  @Override
  public ValidateCode generate(ServletWebRequest request) {
    String code = RandomStringUtils.randomNumeric(codeProperties.getSms().getLength());
    return new ValidateCode(code, codeProperties.getSms().getExpireIn());
  }
}
