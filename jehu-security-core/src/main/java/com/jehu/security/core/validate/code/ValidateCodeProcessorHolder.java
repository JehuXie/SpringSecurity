/**
* @Title:：ValidateCodeProcessorHolder.java 
* @Package ：com.jehu.security.core.validate.code 
* @Description： TODO
* @author： JehuXie   
* @date： 2018年3月13日 下午2:31:15 
 */
package com.jehu.security.core.validate.code;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
* @ClassName:：ValidateCodeProcessorHolder 
* @Description： TODO
* @author ：JehuXie
* @date ：2018年3月13日 下午2:31:15 
 */
@Component
public class ValidateCodeProcessorHolder {

	@Autowired
	private Map<String, ValidateCodeProcessor> validateCodeProcessors;

	public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {
		return findValidateCodeProcessor(type.toString().toLowerCase());
	}

	public ValidateCodeProcessor findValidateCodeProcessor(String type) {
		String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
		ValidateCodeProcessor processor = validateCodeProcessors.get(name);
		if (processor == null) {
			throw new ValidateCodeException("验证码处理器" + name + "不存在");
		}
		return processor;
	}
}
