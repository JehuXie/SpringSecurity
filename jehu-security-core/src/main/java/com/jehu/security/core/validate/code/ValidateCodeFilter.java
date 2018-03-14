/**
 * 
 */
package com.jehu.security.core.validate.code;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jehu.security.core.properties.SecurityConstants;
import com.jehu.security.core.properties.SecurityProperties;

/**
 * @author Administrator
 *
 */
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean{

	/*private AuthenticationFailureHandler authenticationFailureHandler;
	
	private Set<String> urls = new HashSet<String>();
	
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	
	private SecurityProperties securityProperties;
	
	private AntPathMatcher pathMatcher = new AntPathMatcher();*/
	
	/**
	 * 验证码校验失败处理器
	 */
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	/**
	 * 系统配置信息
	 */
	@Autowired
	private SecurityProperties securityProperties;
	/**
	 * 系统中的校验码处理器
	 */
	@Autowired
	private ValidateCodeProcessorHolder validateCodeProcessorHolder;
	/**
	 * 存放所有需要校验验证码的url
	 */
	private Map<String, ValidateCodeType> urlMap = new HashMap<>();
	/**
	 * 验证请求url与配置的url是否匹配的工具类
	 */
	private AntPathMatcher pathMatcher = new AntPathMatcher();
	/**
	 * 初始化要拦截的url配置信息
	 */
	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		urlMap.put(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
		addUrlToMap(securityProperties.getCode().getImage().getUrl(), ValidateCodeType.IMAGE);

		urlMap.put(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
		addUrlToMap(securityProperties.getCode().getSmsCode().getUrl(), ValidateCodeType.SMS);
		/*if(securityProperties.getCode().getImage().getUrl() != null) {
			String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImage().getUrl(), ",");
			for (String configUrl : configUrls) {
				urls.add(configUrl);
			}
		}
		urls.add("/authentication/form");*/
	}
	private void addUrlToMap(String urlString, ValidateCodeType type) {
		if(StringUtils.isNotEmpty(urlString)) {
			String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
			for(String url:configUrls) {
				urlMap.put(url, type);
			}
		}
		
	}
	/* (non-Javadoc)
	 * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		ValidateCodeType type = getValidateCodeType(request);
		if (type != null) {
			logger.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + type);
			try {
				validateCodeProcessorHolder.findValidateCodeProcessor(type)
						.validate(new ServletWebRequest(request, response));
				logger.info("验证码校验通过");
			} catch (ValidateCodeException exception) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
				return;
			}
		}

		chain.doFilter(request, response);
		
		/*boolean action = false;
		for (String url : urls) {
			if(pathMatcher.match(url, request.getRequestURI())) {
				action = true;
			}
		}
		
		if(StringUtils.equals("/authentication/form", request.getRequestURI())
				&& StringUtils.equals(request.getMethod(), "POST")) 
		if(action){
			try {
				validate(new ServletWebRequest(request));
				
			} catch (ValidateCodeException e) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
				return;
			}
		}
			chain.doFilter(request, response);*/
	}

	/*private void validate(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
		ImageCode codeInSession = (ImageCode)sessionStrategy.getAttribute(servletWebRequest, 
				ValidateCodeController.SESSION_KEY);
		String codeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "imageCode");
		if(StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException("验证码不能为空");
		}
		if(codeInSession == null) {
			throw new ValidateCodeException("验证码不存在");
		}
		if( codeInSession.isExpried()) {
			sessionStrategy.removeAttribute(servletWebRequest, ValidateCodeController.SESSION_KEY);
			throw new ValidateCodeException("验证码已过期");
		}
		if(!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException("验证码不匹配");
		}
		sessionStrategy.removeAttribute(servletWebRequest, ValidateCodeController.SESSION_KEY);
	}*/

	/**
	 * 获取校验码的类型，如果当前请求不需要校验，则返回null
	 * 
	 * @param request
	 * @return
	 */
	private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
		ValidateCodeType result = null;
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
			Set<String> urls = urlMap.keySet();
			for (String url : urls) {
				if (pathMatcher.match(url, request.getRequestURI())) {
					result = urlMap.get(url);
				}
			}
		}
		return result;
	}
	
	public AuthenticationFailureHandler getAuthenticationFailureHandler() {
		return authenticationFailureHandler;
	}

	public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
		this.authenticationFailureHandler = authenticationFailureHandler;
	}

	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}
	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}
	public AntPathMatcher getPathMatcher() {
		return pathMatcher;
	}
	public void setPathMatcher(AntPathMatcher pathMatcher) {
		this.pathMatcher = pathMatcher;
	}

	
}
