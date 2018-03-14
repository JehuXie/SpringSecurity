/**
* @Title:：SmsCodeAuthenticationProvider.java 
* @Package ：com.jehu.security.core.authentication.mobile 
* @Description： TODO
* @author： JehuXie   
* @date： 2018年3月13日 上午11:00:40 
 */
package com.jehu.security.core.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
* @ClassName:：SmsCodeAuthenticationProvider 
* @Description： TODO
* @author ：JehuXie
* @date ：2018年3月13日 上午11:00:40 
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private UserDetailsService userDetailsService;

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
		UserDetails user = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());
		if(user == null) {
			throw new InternalAuthenticationServiceException("无法获取用户信息");
		}
		SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(user,user.getAuthorities());
		authenticationResult.setDetails(authenticationToken.getDetails());
		return authenticationResult;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	
}
