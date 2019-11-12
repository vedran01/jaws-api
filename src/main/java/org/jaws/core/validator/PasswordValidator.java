package org.jaws.core.validator;

import lombok.extern.slf4j.Slf4j;
import org.jaws.core.annotation.ValidPassword;
import org.passay.*;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class PasswordValidator implements ConstraintValidator<ValidPassword, String>, ApplicationContextAware {

  private org.passay.PasswordValidator validator;
  private ApplicationContext context;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    context = applicationContext;
  }

  @Override
  public void initialize(ValidPassword constraintAnnotation) {
    log.info("Initialized password validator");
    validator = context.getBean("passayValidator", org.passay.PasswordValidator.class);
  }

  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {

    if (password != null) {

      RuleResult validationResult = validator.validate(new PasswordData(password));

      if (validationResult.isValid()) return true;

      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(String.valueOf(validator.getMessages(validationResult)))
          .addConstraintViolation();
    }
    return false;
  }
}
