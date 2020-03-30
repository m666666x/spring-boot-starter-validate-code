/**
 * 
 */
package top.lemna.pay.properties;

/**
 * @author zhailiang
 *
 */
public interface ValidateCodeConstants {
	
	/**
	 * 默认的处理验证码的url前缀
	 */
	public static final String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";
  
	/**
	 * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
	 */
	public static final String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";
	/**
	 * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
	 */
	public static final String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";
    /**
     * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
     */
    public static final String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";
    /**
     * 发送短信验证码 或 验证短信验证码时，传递短信模板的参数的名称
     */
    public static final String DEFAULT_PARAMETER_NAME_TEMPLATE = "smsType";
    /**
     * 发送图片验证码 或 验证图片验证码时，传递用户名的参数的名称
     */
    public static final String DEFAULT_PARAMETER_NAME_USERNAME = "username";

}
