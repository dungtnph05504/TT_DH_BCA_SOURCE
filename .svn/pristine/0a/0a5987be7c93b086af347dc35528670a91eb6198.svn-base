package com.nec.asia.nic.admin.controller;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.nec.asia.nic.admin.domain.User;

/**
 * Flexible validator implementation 
 * that no need to implement some specific interfaces
 * 
 * @author bright_zheng
 *
 */
@Component
public class UserValidator{

	public void validate(User user, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(
				errors, "account", "global.form.field.required", new String[]{"User"});
		ValidationUtils.rejectIfEmptyOrWhitespace(
				errors, "password", "global.form.field.required", new String[]{"Password"});
		
	}

}