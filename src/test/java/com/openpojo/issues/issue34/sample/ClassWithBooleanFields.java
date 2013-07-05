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

package com.openpojo.issues.issue34.sample;

/**
 * @author oshoukry
 */
public class ClassWithBooleanFields {

    boolean flagOneUsingIs;
    boolean flagTwoUsingGet;

    Boolean flagThreeUsingIs;
    Boolean flagFourUsingGet;

    public boolean isFlagOneUsingIs() {
        return flagOneUsingIs;
    }

    public void setFlagOneUsingIs(boolean flagOneUsingIs) {
        this.flagOneUsingIs = flagOneUsingIs;
    }

    public boolean getFlagTwoUsingGet() {
        return flagTwoUsingGet;
    }

    public void setFlagTwoUsingGet(boolean flagTwoUsingGet) {
        this.flagTwoUsingGet = flagTwoUsingGet;
    }

    public Boolean getFlagThreeUsingIs() {
        return flagThreeUsingIs;
    }

    public void setFlagThreeUsingIs(Boolean flagThreeUsingIs) {
        this.flagThreeUsingIs = flagThreeUsingIs;
    }

    public Boolean getFlagFourUsingGet() {
        return flagFourUsingGet;
    }

    public void setFlagFourUsingGet(Boolean flagFourUsingGet) {
        this.flagFourUsingGet = flagFourUsingGet;
    }

}
