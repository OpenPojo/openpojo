/*
 * Copyright (c) 2010-2014 Osman Shoukry
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

package com.openpojo.utils.log;

import java.util.List;

import com.openpojo.utils.log.LogEvent.Priority;

/**
 * @author oshoukry
 *
 */
public interface MockAppender {
    public void resetAppender();
    public Integer getCountBySourceByPriority(final String source, final Priority priority);
    public Integer getCountBySource(final String source);
    public List<LogEvent> getLoggedEventsBySourceByPriority(final String source, final Priority priority);
}
