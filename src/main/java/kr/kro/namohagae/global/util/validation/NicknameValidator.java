package kr.kro.namohagae.global.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NicknameValidator implements ConstraintValidator<Nickname, String> {
    @Override
    public boolean isValid(String nickname, ConstraintValidatorContext context) {
        if(nickname == null){return false;}
        return  nickname.matches("^[가-힣a-z]{2,8}$");
    }
}
