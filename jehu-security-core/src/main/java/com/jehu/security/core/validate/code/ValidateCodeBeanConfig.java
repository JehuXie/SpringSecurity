/**
 * 
 */
package com.jehu.security.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jehu.security.core.properties.SecurityProperties;
import com.jehu.security.core.validate.code.image.ImageCodeGenerator;
import com.jehu.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.jehu.security.core.validate.code.sms.SmsCodeSender;

/**
 * @author Administrator
 *
 */
@Configuration
public class ValidateCodeBeanConfig {
	@Autowired
	private SecurityProperties securityProperties;

	@Bean
	@ConditionalOnMissingBean(name="imageCodeGenerator")
	public ValidateCodeGenerator imageCodeGenerator() {
		ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
		codeGenerator.setSecurityProperties(securityProperties);
		return codeGenerator;
	}
	
	@Bean
	@ConditionalOnMissingBean(SmsCodeSender.class)
	public SmsCodeSender smsCodeSender() {
		
		return new DefaultSmsCodeSender();
	}
	
}
