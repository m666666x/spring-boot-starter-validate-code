package spring.boot.starter.validate.code.persistence.repository;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import spring.boot.starter.validate.code.ValidateCode;


@Component
public class ValidateCodeRedisRepository {

  @Autowired
  private RedisTemplate<String, ValidateCode> redisTemplate;


  public void add(String key, ValidateCode ValidateCode) {
    redisTemplate.opsForValue().set(key, ValidateCode);
  }

  /**
   * 
   * @param key
   * @param ValidateCode
   * @param timeout 超时时间 单位:秒
   */
  public void add(String key, ValidateCode ValidateCode, long timeout) {
    redisTemplate.opsForValue().set(key, ValidateCode, timeout, TimeUnit.SECONDS);
  }

  public ValidateCode getValue(String key) {

    ValueOperations<String, ValidateCode> opsForValue = redisTemplate.opsForValue();
    return opsForValue.get(key);
  }

  public void delete(String key) {
    redisTemplate.opsForValue().getOperations().delete(key);
  }

}
