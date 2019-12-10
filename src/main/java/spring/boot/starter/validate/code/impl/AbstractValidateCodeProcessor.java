package spring.boot.starter.validate.code.impl;

import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;
import spring.boot.starter.validate.code.ValidateCode;
import spring.boot.starter.validate.code.ValidateCodeException;
import spring.boot.starter.validate.code.ValidateCodeGenerator;
import spring.boot.starter.validate.code.ValidateCodeProcessor;
import spring.boot.starter.validate.code.ValidateCodeType;
import spring.boot.starter.validate.code.persistence.repository.ValidateCodeRedisRepository;

/**
 * @author zhailiang
 *
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode>
    implements ValidateCodeProcessor {

  /**
   * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
   */
  @Autowired
  private Map<String, ValidateCodeGenerator> validateCodeGenerators;

  @Autowired
  private ValidateCodeRedisRepository ValidateCodeRedisRepository;

  /*
   * (non-Javadoc)
   * 
   * @see com.imooc.security.core.validate.code.ValidateCodeProcessor#create(org.
   * springframework.web.context.request.ServletWebRequest)
   */
  @Override
  public void create(ServletWebRequest request) throws Exception {
    C validateCode = generate(request);
    save(request, validateCode);
    send(request, validateCode);
  }

  /**
   * 生成校验码
   * 
   * @param request
   * @return
   */
  @SuppressWarnings("unchecked")
  private C generate(ServletWebRequest request) {
    String type = getValidateCodeType(request).toString().toLowerCase();
    String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
    ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
    if (validateCodeGenerator == null) {
      throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
    }
    return (C) validateCodeGenerator.generate(request);
  }

  /**
   * 保存校验码
   * 
   * @param request
   * @param validateCode
   * @throws Exception 
   */
  private void save(ServletWebRequest request, C validateCode) throws Exception {
    ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
    ValidateCodeRedisRepository.add(getPersistenceKey(request), code, 1800);// 验证码保存到redis，30分钟后自动删除
  }

  /**
   * 构建验证码放入session时的key
   * 
   * @param request
   * @return
   * @throws Exception 
   */
  protected abstract String getPersistenceKey(ServletWebRequest request) throws ValidateCodeException;

  /**
   * 发送校验码，由子类实现
   * 
   * @param request
   * @param validateCode
   * @throws Exception
   */
  protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

  /**
   * 根据请求的url获取校验码的类型
   * 
   * @param request
   * @return
   */
  protected ValidateCodeType getValidateCodeType(ServletWebRequest request) {
    String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
    return ValidateCodeType.valueOf(type.toUpperCase());
  }

  @SuppressWarnings("unchecked")
  @Override
  public void validate(ServletWebRequest request) throws ValidateCodeException {

    ValidateCodeType processorType = getValidateCodeType(request);
    String sessionKey = getPersistenceKey(request);

    C codeInSession = (C) ValidateCodeRedisRepository.getValue(sessionKey);

    String codeInRequest = request.getRequest().getHeader(processorType.getParamNameOnValidate()); 

    if (StringUtils.isBlank(codeInRequest)) {
      throw new ValidateCodeException(processorType + "验证码的值不能为空");
    }

    if (codeInSession == null) {
      throw new ValidateCodeException(processorType + "验证码不存在");
    }

    if (codeInSession.isExpried()) {
      ValidateCodeRedisRepository.delete(sessionKey);
      throw new ValidateCodeException(processorType + "验证码已过期");
    }

    if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
      throw new ValidateCodeException(processorType + "验证码不匹配");
    }

    ValidateCodeRedisRepository.delete(sessionKey);
  }

}
