package com.techbooker.sm.util.validator;

import com.techbooker.sm.util.CommonUtil;
import com.techbooker.sm.util.annotation.TraceIdConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

public class TraceIdValidator implements ConstraintValidator<TraceIdConstraint, String> {

	@Override
	public boolean isValid(@Valid @Pattern(regexp = "[^A-Za-z0-9]+") String traceId, ConstraintValidatorContext context) {

		return !(!CommonUtil.isLengthEqualValid(traceId, 15) || CommonUtil.hasEmptySpace(traceId)
				|| CommonUtil.haveSpecialCharacters(traceId) || !CommonUtil.isTraceIdFormat(traceId));
	}
	
}
