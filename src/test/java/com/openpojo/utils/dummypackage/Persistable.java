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
