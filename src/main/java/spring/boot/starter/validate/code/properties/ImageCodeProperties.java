/**
 * 
 */
package spring.boot.starter.validate.code.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhailiang
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ImageCodeProperties extends SmsCodeProperties {

  public ImageCodeProperties() {
    setLength(4);
  }

  private int width = 67;
  private int height = 23;

}
