/**
 * 
 */
package spring.boot.starter.validate.code.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;

/**
 * @author zhailiang
 *
 */

@Data
@ConfigurationProperties(prefix = "spring.validate.code")
public class ValidateCodeProperties {

  private ImageCodeProperties image = new ImageCodeProperties();

  private SmsCodeProperties sms = new SmsCodeProperties();


}
