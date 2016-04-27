package com.openpojo.validation.test.impl;

import com.openpojo.validation.rule.impl.CommonCode;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.test.impl.sampleclasses.BadEqualsImplementation_IncorrectFieldUsageClass;
import com.openpojo.validation.test.impl.sampleclasses.BadEqualsImplementation_IncorrectInitialCheckClass;
import com.openpojo.validation.test.impl.sampleclasses.BadEqualsImplementation_InstanceEqualsToNullClass;
import com.openpojo.validation.test.impl.sampleclasses.BadHashCodeImplementationClass;
import com.openpojo.validation.test.impl.sampleclasses.GoodHashCodeEqualsImplementationWithFieldsClass;
import com.openpojo.validation.test.impl.sampleclasses.NoDefinitionOfHashcodeAndEqualsIsCorrectClass;
import org.junit.Test;

public class EqualsAndHashcodeContractTesterTest {
  Class<?>[] failClasses = new Class<?>[] { BadEqualsImplementation_IncorrectFieldUsageClass.class, BadEqualsImplementation_IncorrectInitialCheckClass.class, BadEqualsImplementation_InstanceEqualsToNullClass.class, BadHashCodeImplementationClass.class };
  Class<?>[] passClasses = new Class<?>[] { GoodHashCodeEqualsImplementationWithFieldsClass.class, NoDefinitionOfHashcodeAndEqualsIsCorrectClass.class };
  Tester test = new EqualsAndHashcodeContractTester();

  @Test
  public void testEvaluate() {
    CommonCode.shouldPassTesterValidation(test, passClasses);
    CommonCode.shouldFailTesterValidation(test, failClasses);
  }

}
