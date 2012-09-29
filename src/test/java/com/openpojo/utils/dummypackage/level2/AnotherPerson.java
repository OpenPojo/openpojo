/*
 * Copyright (c) 2010-2012 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License or any
 *   later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openpojo.utils.dummypackage.level2;

import java.io.Serializable;
import java.sql.Timestamp;

import com.openpojo.utils.dummypackage.Persistable;

public final class AnotherPerson implements Serializable, Persistable {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor for persistence service loading.
     */
    public AnotherPerson() {
    }

    /**
     * Minimal business constructor.
     *
     * @param firstname
     * @param middlename
     * @param lastname
     */
    public AnotherPerson(final String firstname, final String middlename, final String lastname) {
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    private String firstname;

    private String middlename;

    private String lastname;

    private Timestamp created;
    private Timestamp lastupdated;

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname
     *            the firstname to set
     */
    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the middlename
     */
    public String getMiddlename() {
        return middlename;
    }

    /**
     * @param middlename
     *            the middlename to set
     */
    public void setMiddlename(final String middlename) {
        this.middlename = middlename;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname
     *            the lastname to set
     */
    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(final Timestamp created) {
        this.created = created;
    }

    public Timestamp getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(final Timestamp lastupdated) {
        this.lastupdated = lastupdated;
    }

}
