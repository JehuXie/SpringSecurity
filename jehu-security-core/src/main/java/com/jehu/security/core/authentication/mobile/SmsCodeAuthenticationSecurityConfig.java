/**
* @Title:：SmsCodeAuthenticationSecurityConfig.java 
* @Package ：com.jehu.security.core.authentication.mobile 
* @Description： TODO
* @author： JehuXie   
* @date： 2018年3月13日 上午11:26:28 
 */
package com.jehu.security.core.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
* @ClassName:：SmsCodeAuthenticationSecurityConfig 
* @Description： TODO
* @author ：JehuXie
* @date ：2018年3月13日 上午11:26:28 
 */
@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	@Autowired
	private AuthenticationSuccessHandler jehuAuthenticationSuccessHandler;
	
	@Autowired
	private AuthenticationFailureHandler jehuAuthenticationFailureHandler;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		SmsCodeAuthenticationFilter authenticationFilter = new SmsCodeAuthenticationFilter();
		authenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		authenticationFilter.setAuthenticationSuccessHandler(jehuAuthenticationSuccessHandler);
		authenticationFilter.setAuthenticationFailureHandler(jehuAuthenticationFailureHandler);
		
		SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
		smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);
		
		http.authenticationProvider(smsCodeAuthenticationProvider)
			.addFilterAfter(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
