package main.java.toolkit.annotation.validator;

import main.java.toolkit.annotation.EnumValid;
import main.java.toolkit.enums.EnumFileEnum;
import main.java.toolkit.utils.EnumValidUtils;
import main.java.utils.StringUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

/**
 * EnumValidator
 * Created by leon_zy on 2018/10/8
 */
public class EnumValidator implements ConstraintValidator<EnumValid,String> {

    private EnumValid valid;
    private Class clazz = null;
    EnumFileEnum enumFile = null;

    @Override public void initialize(EnumValid init) {
        valid = init;
        clazz = valid.clazz()[0];
        enumFile = valid.enumFile();
    }

    @Override public boolean isValid(String val, ConstraintValidatorContext constraintValidatorContext) {
        Boolean validFlag = true;
        try{
            if(valid.isNull()){
                if(null != clazz){
                    validFlag = validValue(val);
                }
            }else{
                validFlag = StringUtil.isNotBlank(val) && validValue(val);
            }
        } catch (Exception e){
            validFlag = false;
        }
        return validFlag;
    }

    private Boolean validValue(String val){
        Boolean validFlag = true;
        Map<String, Object> enumMap = EnumValidUtils.getEnumCodeMap(clazz, enumFile, EnumFileEnum.Code);
        if(null == enumMap.get(val)){
            validFlag = false;
        }
        return validFlag;
    }
}