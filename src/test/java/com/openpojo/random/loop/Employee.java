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

package com.openpojo.random.loop;

import java.io.Serializable;

public final class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Minimal business constructor.
     */
    public Employee(final String fullName, final Employee manager) {
        this.fullName = fullName;
        this.manager = manager;
    }

    private String fullName;

    /**
     * This is an Object Loop, to demonstrate how the random factory would handle it.
     */
    private Employee manager;

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return fullName;
    }

    /**
     * @param firstname
     *            the firstname to set
     */
    public void setFirstname(final String firstname) {
        this.fullName = firstname;
    }

    /**
     * @param manager
     *            the manager to set.
     */
    public void setManager(final Employee manager) {
        this.manager = manager;
    }

    /**
     * @return
     *         the manager of this person.
     */
    public Employee getManager() {
        return manager;
    }

    @Override
    public String toString() {
        return String
                .format("Employee [fullName=%s, manager=%s]", fullName, manager);
    }

}
