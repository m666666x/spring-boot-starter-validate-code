package top.lemna.pay.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import top.lemna.pay.properties.ValidateCodeProperties;

/**
 * @author zhailiang
 *
 */
@Configuration
public class ValidateCodeBeanConfig {

  @Autowired
  private ValidateCodeProperties codeProperties;

 
}
