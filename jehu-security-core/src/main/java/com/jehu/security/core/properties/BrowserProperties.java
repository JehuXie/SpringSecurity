/**
 * 
 */
package com.jehu.security.core.properties;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author Administrator
 *
 */

public class BrowserProperties {
	@Value("${jehu.security.browser.loginPage}")
	private String loginPage = SecurityConstants.DEFAULT_SIGN_IN_PAGE_URL;
	@Value("${jehu.security.browser.loginType}")
	private LoginType loginType = LoginType.JSON;
	
	/**
	 * '记住我'功能的有效时间，默认1小时
	 */
	private int rememberMeSeconds = 3600;
	
	public BrowserProperties() {}
	
	public BrowserProperties(String loginPage) {
		super();
		this.loginPage = loginPage;
	}

	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

	public LoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}

	public int getRememberMeSeconds() {
		return rememberMeSeconds;
	}

	public void setRememberMeSeconds(int rememberMeSeconds) {
		this.rememberMeSeconds = rememberMeSeconds;
	}
	
}
