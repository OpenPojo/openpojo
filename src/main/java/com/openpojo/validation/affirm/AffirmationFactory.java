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

package com.openpojo.validation.affirm;

import com.openpojo.log.Logger;
import com.openpojo.log.LoggerFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.facade.FacadeFactory;

/**
 * This Affirmation factory is responsible to return affirmation implementation.<br>
 *
 * @author oshoukry
 */
public final class AffirmationFactory {
  private static final String[] SUPPORTED_ASSERTIONS = new String[] {
      "com.openpojo.validation.affirm.TestNGAssertAffirmation",
      "com.openpojo.validation.affirm.JUnitAssertAffirmation",
      "com.openpojo.validation.affirm.JavaAssertionAffirmation" };

  private static final Logger log = LoggerFactory.getLogger(Affirmation.class);

  /**
   * The only affirmation implemented so far, so default to that.
   */
  private Affirmation affirmation;

  private AffirmationFactory() {
    affirmation = getActiveAffirmation();
    log.info("Dynamically setting affirmation implementation = [{0}]", affirmation);
  }

  /**
   * Get the Factory Instance.
   *
   * @return An AffirmationFactoryInstance.
   */
  public static AffirmationFactory getInstance() {
    return Instance.INSTANCE;
  }

  /**
   * Get the underlying currently active Affirmation.
   *
   * @return The currently active affirmation.
   */
  public Affirmation getAffirmation() {
    return affirmation;
  }

  /**
   * This method allows full control to set the active affirmation to a specific one.
   *
   * @param affirmation
   *     The Affirmation class to use.
   */
  public void setActiveAffirmation(final Affirmation affirmation) {
    this.affirmation = affirmation;
  }

  private static Affirmation getActiveAffirmation() {
    PojoClass loggerPojoClass = FacadeFactory.getLoadedFacadePojoClass(SUPPORTED_ASSERTIONS);
    return (Affirmation) InstanceFactory.getInstance(loggerPojoClass);
  }

  /**
   * This inner static class holds an instance of the holding class.<br>
   * This allows to have lazy instantiation on singleton and not need any
   * synchronization delays.
   *
   * @author oshoukry
   */
  private static class Instance {
    private static final AffirmationFactory INSTANCE = new AffirmationFactory();
  }
}
