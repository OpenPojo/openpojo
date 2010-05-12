/**
 * Copyright (C) 2010 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openpojo.reflection.impl.sampleclasses;

/**
 * @author oshoukry
 *
 */
public class PojoFieldImplClass {

    private static final String STATICFINALFIELD = "Static Final Field";
    private String nonStaticNonFinal;
    private int primitiveIntField;

    private int privateIntField;
    protected int protectedIntField;
    public int publicIntField;

    private Boolean booleanField;

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
}
