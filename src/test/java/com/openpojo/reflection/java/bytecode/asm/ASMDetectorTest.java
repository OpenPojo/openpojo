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

package com.openpojo.reflection.java.bytecode.asm;

import com.openpojo.reflection.java.bytecode.ByteCodeFactory;
import com.openpojo.reflection.java.load.ClassUtil;
import com.openpojo.reflection.java.version.Version;
import com.openpojo.reflection.java.version.VersionFactory;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

/**
 * @author oshoukry
 */
public class ASMDetectorTest {
  private ASMDetector asmDetector = ASMDetector.getInstance();

  @Test
  public void canDetectASMLoaded() {
    assertThat(asmDetector.isASMLoaded(), is(true));
  }

  @Test
  public void canGetBundleVersion() {
    Version currentVersion = asmDetector.getBundleVersion(ClassUtil.loadClass(ASMDetector.ASM_CLASS_NAME));
    assertThat(currentVersion, notNullValue());

    assertGreaterOrEqualToMinimumExpected(currentVersion);
    assertLesserOrEqualToMaxExpected(currentVersion);
  }

  @Test
  public void shouldNotThrowNullPointerExceptionIfClassIsNotLoaded() {
    Version version = asmDetector.getBundleVersion(null);
    assertThat(version, notNullValue());
  }

  private void assertGreaterOrEqualToMinimumExpected(Version version) {
    assertThat(version.compareTo(ByteCodeFactory.ASM_MIN_VERSION), greaterThanOrEqualTo(0));
  }

  private void assertLesserOrEqualToMaxExpected(Version version) {
    System.out.println("Version compareTo Max: " + version.compareTo(ByteCodeFactory.ASM_MAX_VERSION));
    assertThat(version.getVersion(), version.compareTo(ByteCodeFactory.ASM_MAX_VERSION), lessThanOrEqualTo(0));
  }
}
