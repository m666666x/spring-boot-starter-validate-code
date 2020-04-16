package spring.boot.starter.validate.code.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import spring.boot.starter.validate.code.properties.ValidateCodeProperties;

/**
 * @author zhailiang
 *
 */
@Configuration
@EnableConfigurationProperties(ValidateCodeProperties.class)
@Order(100)
@ComponentScan("spring.boot.starter.validate.code")
@ServletComponentScan(basePackages = "spring.boot.starter.validate.code")
public class ValidateAutoCodeConfig {


}
