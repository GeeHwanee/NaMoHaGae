package kr.kro.namohagae.global.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kr.kro.namohagae.global.dao.TownDao;
import org.springframework.beans.factory.annotation.Autowired;

public class TownValidator implements ConstraintValidator<Town,String> {
    @Autowired
    private TownDao dao;
    @Override
    public boolean isValid(String town, ConstraintValidatorContext context) {
        if(town == null){return false;}
           Boolean a =  null;
        for (int i = 0; i < dao.findDong().size(); i++) {
           a= town.matches(dao.findDong().get(i).getTownDong());
           if (a==false){
               break;
           }
        }
        return a;
    }
}
