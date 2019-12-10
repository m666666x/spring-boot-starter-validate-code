package spring.boot.starter.validate.code;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * @author zhailiang
 *
 */
public class ValidateCode implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1588203828504660915L;

  private String code;

  private LocalDateTime expireTime;


  public ValidateCode() {
    super();
  }

  public ValidateCode(String code, int expireIn) {
    this.code = code;
    this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
  }

  public ValidateCode(String code, LocalDateTime expireTime) {
    this.code = code;
    this.expireTime = expireTime;
  }

  @JsonIgnore
  public boolean isExpried() {
    return LocalDateTime.now().isAfter(expireTime);
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public LocalDateTime getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(LocalDateTime expireTime) {
    this.expireTime = expireTime;
  }

}
