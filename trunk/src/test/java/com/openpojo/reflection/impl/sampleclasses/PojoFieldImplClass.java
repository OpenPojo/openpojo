/*
 * Copyright (c) 2010-2015 Osman Shoukry
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

package com.openpojo.reflection.impl.sampleclasses;

/**
 * @author oshoukry
 *
 */
public class PojoFieldImplClass {

    @SuppressWarnings("unused")
    private static final String STATICFINALFIELD = "Static Final Field";
    private String nonStaticNonFinal;

    @SuppressWarnings("unused")
    private int primitiveIntField;

    @SuppressWarnings("unused")
    private int privateIntField;
    protected int protectedIntField;
    public int publicIntField;

    private Boolean booleanField;

    private String privateString;

    public transient String transientString;

    public volatile String volatileString;

    /**
     * @return the nonStaticNonFinal
     */
    public String getNonStaticNonFinal() {
        return nonStaticNonFinal;
    }
    /**
     * @param nonStaticNonFinal the nonStaticNonFinal to set
     */
    public void setNonStaticNonFinal(final String nonStaticNonFinal) {
        this.nonStaticNonFinal = nonStaticNonFinal;
    }
    /**
     * @return the booleanField
     */
    public Boolean isBooleanField() {
        return booleanField;
    }
    /**
     * @param booleanField the booleanField to set
     */
    public void setBooleanField(final Boolean booleanField) {
        this.booleanField = booleanField;
    }
    /**
     * @return the privateString
     */
    public String getPrivateString() {
        return privateString;
    }
    /**
     * @param privateString the privateString to set
     */
    public void setPrivateString(final String privateString) {
        this.privateString = privateString;
    }
}
