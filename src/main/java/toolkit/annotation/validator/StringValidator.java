package main.java.toolkit.annotation.validator;

import main.java.toolkit.annotation.StringValid;
import main.java.toolkit.constants.ToolkitConstants;
import main.java.utils.StringUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * StringValidator
 * Created by leon_zy on 2018/10/8
 */
public class StringValidator implements ConstraintValidator<StringValid,String> {

    private StringValid valid;

    @Override public void initialize(StringValid init) {
        valid = init;
    }

    @Override public boolean isValid(String val, ConstraintValidatorContext constraintValidatorContext) {
        Boolean validFlag = true;
        try{
            if(valid.length() > 0){
                if(valid.isNull()){
                    if(StringUtil.isNotBlank(val)){
                        validFlag = val.getBytes(ToolkitConstants.UTF_8).length <= valid.length();
                    }
                }else{
                    validFlag = StringUtil.isNotBlank(val) && val.getBytes(ToolkitConstants.UTF_8).length <= valid.length();
                }
            }
        } catch (Exception e){
            validFlag = false;
        }
        return validFlag;
    }
}