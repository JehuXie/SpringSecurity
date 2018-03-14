/**
 * 
 */
package com.jehu.security.browser;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jehu.security.browser.support.SimpleResponse;
import com.jehu.security.core.properties.SecurityProperties;

/**
 * @author Administrator
 *
 */
@RestController
public class BrowserSecurityController {
	
	private RequestCache requestCache = new HttpSessionRequestCache();
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@RequestMapping("/authentication/require")
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	//当需要身份验证时 跳转到这里
	public SimpleResponse requireAuthentication(HttpServletRequest request,HttpServletResponse responser) throws IOException {
		
		SavedRequest savedRequest = requestCache.getRequest(request, responser);
		if(savedRequest != null) {
			String targetUrl = savedRequest.getRedirectUrl();
			logger.info("引发跳转的请求是:"+ targetUrl);
			if(StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
				redirectStrategy.sendRedirect(request, responser, securityProperties.getBrowser().getLoginPage());
			}
		}
		return new SimpleResponse("访问的服务需要身份验证，请引导用户到登录页面！");
	}
}



