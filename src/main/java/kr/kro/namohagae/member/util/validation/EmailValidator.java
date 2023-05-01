package kr.kro.namohagae.member.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email,String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if(email == null){return false;}
        return  email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$");
    }
}
