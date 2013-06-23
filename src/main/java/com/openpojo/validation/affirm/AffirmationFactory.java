/*
 * Copyright (c) 2010-2013 Osman Shoukry
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License or any
 *    later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
    private static final String[] SUPPORTED_ASSERTIONS = new String[]{
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
     * @return
     *         An AffirmationFactoryInstance.
     */
    public static AffirmationFactory getInstance() {
        return Instance.INSTANCE;
    }

    /**
     * Get the underlying currently active Affirmation.
     *
     * @return
     *         The currently active affirmation.
     */
    public Affirmation getAffirmation() {
        return affirmation;
    }

    /**
     * This method allows full control to set the active affirmation to a specific one.
     *
     * @param affirmation
     *            The Affirmation class to use.
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
