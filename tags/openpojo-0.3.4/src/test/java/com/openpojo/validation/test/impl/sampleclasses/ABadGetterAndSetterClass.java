/**
 * Copyright (C) 2010 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openpojo.validation.test.impl.sampleclasses;

import java.util.Map;

/**
 * @author oshoukry
 *
 */
public final class ABadGetterAndSetterClass {
    private String attribute;
    private final String stringAttribute = "a String that is Final";
    public String stringNoGetterOrSetterAttribute;

    @SuppressWarnings("rawtypes")
    private Map myMap;

    /**
     * @return the myMap
     */
    @SuppressWarnings("rawtypes")
    public Map getMyMap() {
        return myMap;
    }

    /**
     * @param myMap the myMap to set
     */
    public void setMyMap(@SuppressWarnings("rawtypes") final Map myMap) {
        this.myMap = myMap;
    }

    /**
     * @return the stringAttribute
     */
    public String getStringAttribute() {
        return stringAttribute + stringAttribute;
    }

    /**
     * @return the attribute
     */
    public String getAttribute() {
        return attribute + attribute;
    }

    /**
     * @param attribute the attribute to set
     */
    public void setAttribute(final String attribute) {
        this.attribute = attribute + attribute;
    }

}
