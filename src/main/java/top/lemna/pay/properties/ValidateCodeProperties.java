/**
 * 
 */
package top.lemna.pay.properties;

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
