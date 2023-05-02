package kr.kro.namohagae.global.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone,String> {
    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        if(phone == null){return false;}
        return  phone.matches("^[0-9]{11}$");
    }
}
