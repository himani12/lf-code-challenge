package com.labforward.api.core.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.Validator;

/*
 * Used for manually validating method parameter beans (JSR-303) in service layer
 */
@Configuration
public class ValidationConfig {

	private MessageSource messageSource;

	@Autowired
	public ValidationConfig(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Bean
	public EntityValidator preconditionValidator() {
		SpringValidatorAdapter springValidator = springValidatorAdapter();
		return new EntityValidator(springValidator);
	}

	@Bean
	public SpringValidatorAdapter springValidatorAdapter() {
		Validator localValidator = localValidatorFactoryBean();

		return new SpringValidatorAdapter(localValidator);
	}

	@Bean
	public Validator localValidatorFactoryBean() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();

		// use the application message bundle for validation messages
		bean.setValidationMessageSource(messageSource);
		return bean;
	}

}
