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

package com.openpojo.business.sampleclasses;

import com.openpojo.business.annotation.BusinessKey;

/**
 * @author oshoukry
 *
 */
public class Human {

    @BusinessKey
    private Character sex;

    /**
     * @return the sex
     */
    public Character getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(final Character sex) {
        this.sex = sex;
    }
}
