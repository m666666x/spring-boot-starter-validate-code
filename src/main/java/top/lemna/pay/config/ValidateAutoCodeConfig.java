package top.lemna.pay.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import top.lemna.pay.properties.ValidateCodeProperties;

/**
 * @author zhailiang
 *
 */
@Configuration
@EnableConfigurationProperties(ValidateCodeProperties.class)
@Order(1)
@ComponentScan("spring.boot.starter.validate.code")
@ServletComponentScan(basePackages = "spring.boot.starter.validate.code")
public class ValidateAutoCodeConfig {


}
