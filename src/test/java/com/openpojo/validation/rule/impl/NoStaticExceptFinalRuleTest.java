/*
 * Copyright (c) 2010-2018 Osman Shoukry
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.openpojo.validation.rule.impl;

import com.openpojo.reflection.java.Java;
import com.openpojo.reflection.java.bytecode.asm.SimpleClassLoader;
import com.openpojo.validation.CommonCode;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.rule.impl.sampleclasses.NoStaticExceptFinalDoesClass;
import com.openpojo.validation.rule.impl.sampleclasses.NoStaticExceptFinalDoesntClass;
import com.openpojo.validation.utils.AClassWithSyntheticFieldDumper;
import com.openpojo.validation.utils.AClassWithSyntheticStaticFieldDumper;
import org.junit.Test;

import static com.openpojo.reflection.java.bytecode.asm.SubClassDefinition.GENERATED_CLASS_POSTFIX;

/**
 * @author oshoukry
 */
public class NoStaticExceptFinalRuleTest {
  private Class<?>[] failClasses = new Class<?>[] { NoStaticExceptFinalDoesntClass.class };
  private Class<?>[] passClasses = new Class<?>[] { NoStaticExceptFinalDoesClass.class };
  private Rule rule = new NoStaticExceptFinalRule();

  @Test
  public void testEvaluate() {
    CommonCode.shouldPassRuleValidation(rule, passClasses);
    CommonCode.shouldFailRuleValidation(rule, failClasses);
  }

  @Test
  public void shouldIgnoreSyntheticFields() throws ClassNotFoundException {
    final SimpleClassLoader simpleClassLoader = new SimpleClassLoader();
    final String className = this.getClass().getPackage().getName() + ".AClassWithStaticSyntheticField" + GENERATED_CLASS_POSTFIX;
    final String classNameAsPath = className.replace(Java.PACKAGE_DELIMITER, Java.PATH_DELIMITER);

    CommonCode.shouldPassRuleValidation(rule, simpleClassLoader.loadThisClass(AClassWithSyntheticStaticFieldDumper.dump(classNameAsPath), className));
  }

}
