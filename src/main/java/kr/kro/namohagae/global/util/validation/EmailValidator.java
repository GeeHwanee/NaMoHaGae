package kr.kro.namohagae.global.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email,String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if(email == null){return false;}
        return  email.matches("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$");
    }
}
