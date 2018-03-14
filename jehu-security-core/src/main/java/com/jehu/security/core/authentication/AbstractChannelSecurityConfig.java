/**
* @Title:：AbstractChannelSecurityConfig.java 
* @Package ：com.jehu.security.core.authentication 
* @Description： TODO
* @author： JehuXie   
* @date： 2018年3月13日 下午1:49:53 
 */
package com.jehu.security.core.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.jehu.security.core.properties.SecurityConstants;

/**
* @ClassName:：AbstractChannelSecurityConfig 
* @Description： TODO
* @author ：JehuXie
* @date ：2018年3月13日 下午1:49:53 
 */
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	protected AuthenticationSuccessHandler jehuAuthenticationSuccessHandler;
	
	@Autowired
	protected AuthenticationFailureHandler jehuAuthenticationFailureHandler;
	
	protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
		http.formLogin()
			.loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
			.loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
			.successHandler(jehuAuthenticationSuccessHandler)
			.failureHandler(jehuAuthenticationFailureHandler);
			
	}
}
