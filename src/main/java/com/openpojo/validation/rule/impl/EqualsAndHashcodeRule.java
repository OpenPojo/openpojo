package com.openpojo.validation.rule.impl;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.PojoParameter;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.Rule;

import java.util.List;

public class EqualsAndHashcodeRule implements Rule {

    public void evaluate(PojoClass pojoClass) {
        boolean implementsEquals = false;
        boolean implementsHashcode = false;

       for (PojoMethod method : pojoClass.getPojoMethods()){
           if ("hashCode".equals(method.getName()) &&
                   Integer.TYPE.equals(method.getReturnType()) &&
                   method.getPojoParameters().isEmpty()){
               implementsHashcode = true;
           }

           if ("equals".equals(method.getName()) &&
                   Boolean.TYPE.equals(method.getReturnType()) &&
                   onlyOneObjectParameter(method.getPojoParameters())){
               implementsEquals = true;
           }
       }

       Affirm.affirmFalse("Class [" + pojoClass.getName() + "] does not implement both hashCode() and equals(Object) ", implementsEquals ^ implementsHashcode);
    }

    private static boolean onlyOneObjectParameter(List<PojoParameter> pojoParameters){
        if (pojoParameters.size() == 1){
            return pojoParameters.iterator().next().getType() == Object.class;
        }
        return false;
    }

}
