package com.openpojo.validation.rule.impl;

import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.rule.impl.sampleclasses.EqualsAndHashcodeClass;
import com.openpojo.validation.rule.impl.sampleclasses.NoEqualsNorHashcodeClass;
import com.openpojo.validation.rule.impl.sampleclasses.NoEqualsWithHashcodeClass;
import com.openpojo.validation.rule.impl.sampleclasses.NoHashcodeWithEqualsClass;
import org.junit.Test;

public class EqualsAndHashcodeRuleTest {

  Class<?>[] failClasses = new Class<?>[] { NoHashcodeWithEqualsClass.class, NoEqualsWithHashcodeClass.class };
  Class<?>[] passClasses = new Class<?>[] { EqualsAndHashcodeClass.class, NoEqualsNorHashcodeClass.class };
  Rule rule = new EqualsAndHashcodeRule();

  @Test
  public void testEvaluate() {
    CommonCode.shouldPassRuleValidation(rule, passClasses);
    CommonCode.shouldFailRuleValidation(rule, failClasses);
  }

}
