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

package utils.filter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;

/**
 * This class is a utility class to help with testing.
 * It will log every filter call for later validation.
 * 
 * @author oshoukry
 */
public class LoggingPojoClassFilter implements PojoClassFilter {
    private List<PojoClass> pojoClassCallLogs = new LinkedList<PojoClass>();
    private boolean returnValue;

    public void setReturnValue(final boolean returnValue) {
        this.returnValue = returnValue;
    }

    public boolean getReturnValue() {
        return returnValue;
    }
    
    public List<PojoClass> getPojoClassCallLogs() {
        return Collections.unmodifiableList(pojoClassCallLogs);
    }

    @Override
    public boolean include(PojoClass pojoClass) {
        pojoClassCallLogs.add(pojoClass);
        return returnValue;
    }
}
