package com.openpojo.issues.issue22;

import org.junit.Test;

import com.openpojo.issues.issue22.sampleclasses.SomeBean;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;

public class GetterSetterTest {

    @Test
    public void shouldPassGetterSetterTest() {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(SomeBean.class);
        PojoValidator pojoValidator = new PojoValidator();
        pojoValidator.addRule(new SetterMustExistRule());
        pojoValidator.addRule(new GetterMustExistRule());
        pojoValidator.runValidation(pojoClass);
    }

}
