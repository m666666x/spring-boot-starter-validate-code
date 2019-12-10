package spring.boot.starter.validate.code;

/**
 * @author zhailiang
 *
 */
public class ValidateCodeException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = -7285211528095468156L;

  public ValidateCodeException(String msg, Throwable t) {
    super(msg, t);
  }

  public ValidateCodeException(String msg) {
    super(msg);
  }

}
