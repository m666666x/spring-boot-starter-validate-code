package spring.boot.starter.validate.code.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.boot.starter.validate.code.ValidateCodeGenerator;
import spring.boot.starter.validate.code.image.ImageCodeGenerator;
import spring.boot.starter.validate.code.properties.ValidateCodeProperties;
import spring.boot.starter.validate.code.sms.SmsCodeGenerator;

/**
 * @author zhailiang
 *
 */
@Configuration
public class ValidateCodeBeanConfig {

  @Autowired
  private ValidateCodeProperties codeProperties;

  @Bean
  @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
  public ValidateCodeGenerator imageValidateCodeGenerator() {
    ImageCodeGenerator codeGenerator = new ImageCodeGenerator(codeProperties);
    return codeGenerator;
  }


  @Bean
  @ConditionalOnMissingBean(name = "smsValidateCodeGenerator")
  public ValidateCodeGenerator smsCodeGenerator() {
    SmsCodeGenerator codeGenerator = new SmsCodeGenerator(codeProperties);
    return codeGenerator;
  }

}
