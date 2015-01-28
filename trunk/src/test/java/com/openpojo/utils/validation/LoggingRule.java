/*
 * Copyright (c) 2010-2015 Osman Shoukry
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

package com.openpojo.utils.validation;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.validation.rule.Rule;

/**
 * @author oshoukry
 */
public class LoggingRule implements Rule {
    private transient final List<PojoClass> callLogs = new LinkedList<PojoClass>();

    public void evaluate(final PojoClass pojoClass) {
        callLogs.add(pojoClass);
    }

    public List<PojoClass> getLogs() {
        return Collections.unmodifiableList(callLogs);
    }
}
