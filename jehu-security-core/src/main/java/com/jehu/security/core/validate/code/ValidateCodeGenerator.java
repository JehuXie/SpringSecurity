/**
 * 
 */
package com.jehu.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author Administrator
 *
 */
public interface ValidateCodeGenerator {

	//ImageCode createImageCode(HttpServletRequest request);
	
	ValidateCode generate(ServletWebRequest request);
}
