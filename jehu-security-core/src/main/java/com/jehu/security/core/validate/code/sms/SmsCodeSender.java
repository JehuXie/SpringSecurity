/**
 * 
 */
package com.jehu.security.core.validate.code.sms;

/**
 * @author JehuXie
 *
 */
public interface SmsCodeSender {

	void send(String mobile,String code);
}
