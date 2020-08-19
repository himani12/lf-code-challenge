package com.labforward.api.core.validation;

import com.google.common.base.Preconditions;
import com.labforward.api.core.exception.EntityValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.util.UUID;

import static com.labforward.api.hello.constants.Messages.MESSAGE_INVALID_ID;
import static com.labforward.api.hello.constants.Messages.MESSAGE_NO_ID_MATCH;

public class EntityValidator {

	private SpringValidatorAdapter validatorAdapter;

	@Autowired
	public EntityValidator(@Qualifier("springValidatorAdapter") SpringValidatorAdapter validatorAdapter) {
		this.validatorAdapter = validatorAdapter;
	}

	public void validateCreate(Object target, Object... groups) throws EntityValidationException {
		validate(target, groups);
	}

	public void validateUpdate(Object target, Object... groups) throws EntityValidationException {
		validate(target, groups);
	}

	public void validateUpdate(String id, Entity target) throws EntityValidationException {
		validateId(id, target.getClass());
		Preconditions.checkArgument(id != null);
		Preconditions.checkArgument(target != null);

		BeanPropertyBindingResult result = new BeanPropertyBindingResult(target, target.getClass().getName());

		// validate ids match
		if (!id.equals(target.getId())) {
			FieldError fieldError = new FieldError(target.getClass().getName(), "id", MESSAGE_NO_ID_MATCH);
			result.addError(fieldError);
			throw new EntityValidationException(result);
		}

		validatorAdapter.validate(target, result, EntityUpdateValidatorGroup.class);

		if (result.hasErrors()) {
			throw new EntityValidationException(result);
		}
	}

	public void validateId(String id, Class<?> target) throws EntityValidationException {
		BeanPropertyBindingResult result = new BeanPropertyBindingResult(target, target.getClass().getName());
		try {
			Preconditions.checkArgument(id != null);
			Preconditions.checkArgument(!id.trim().isEmpty());
			if (!id.equals("default")) {
				UUID.fromString(id);
			}
		}catch(IllegalArgumentException e) {
			FieldError fieldError = new FieldError(target.getClass().getName(), "id", MESSAGE_INVALID_ID);
			result.addError(fieldError);
			throw new EntityValidationException(result);
		}
	}

	private void validate(Object target, Object... groups) throws EntityValidationException {
		Preconditions.checkArgument(target != null);

		BeanPropertyBindingResult result = new BeanPropertyBindingResult(target, target.getClass().getName());
		validatorAdapter.validate(target, result, groups);

		if (result.hasErrors()) {
			throw new EntityValidationException(result);
		}
	}
}
