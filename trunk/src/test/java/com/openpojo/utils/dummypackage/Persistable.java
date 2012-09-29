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

package com.openpojo.utils.dummypackage;

import java.sql.Timestamp;

/**
 * @author oshoukry
 */
public interface Persistable {
    /**
     * Returns the UUId of the Object as identified in the DB.
     *
     * @return
     */
    public String getId();

    /**
     * Set the creation date/time.
     *
     * @param created
     */
    public void setCreated(Timestamp created);

    /**
     * Get the date/time of creation.
     *
     * @return Returns the date/time this persistable was created.
     */
    public Timestamp getCreated();

    /**
     * Set the last updated date/time.
     *
     * @param lastupdated
     */
    public void setLastupdated(Timestamp lastupdated);

    /**
     * Get the last updated date/time.
     *
     * @return Returns the time of last date/time this persistable was updated.
     */
    public Timestamp getLastupdated();
}
