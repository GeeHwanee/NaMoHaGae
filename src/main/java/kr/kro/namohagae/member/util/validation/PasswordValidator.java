package kr.kro.namohagae.member.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Valid;

public class PasswordValidator implements ConstraintValidator<Password,String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if(password == null){return false;}
        return  password.matches("^[0-9a-zA-Z]{8,16}$");
    }
}
