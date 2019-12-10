package spring.boot.starter.validate.code.properties;

import lombok.Data;

/**
 * @author zhailiang
 *
 */
@Data
public class SmsCodeProperties {

  private int length = 6;
  private int expireIn = 600;
  private String url;

}
