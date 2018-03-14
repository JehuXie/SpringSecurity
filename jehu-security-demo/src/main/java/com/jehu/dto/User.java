/**
 * 
 */
package com.jehu.dto;

import java.util.Date;

import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonView;
import com.jehu.validator.MyConstraint;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Administrator
 *
 */
public class User {
	
	public interface UserSimpleView {};
	
	public interface UserDetailView extends UserSimpleView{};
	
	private String id;
	@MyConstraint(message = "这是一个自定义注解")
	@ApiModelProperty(value = "用户名")
	private String username;
	@NotBlank(message = "密码不能为空")
	private String password;
	@Past(message = "生日必须为过去式")
	private Date birthday;
	
	public User() {}
	@JsonView(User.UserSimpleView.class)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@JsonView(User.UserDetailView.class)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@JsonView(User.UserSimpleView.class)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
}
