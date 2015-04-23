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

package com.openpojo.random.util;

import java.util.Collections;
import java.util.List;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.relation.Role;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.exception.RandomGeneratorException;

/**
 * @author oshoukry
 */
public class SomeRole extends Role {

    @SuppressWarnings("ConstantConditions")
    public SomeRole() {
        super(anyString(), anyRoleValue());
    }

    protected static String anyString() {
        return RandomFactory.getRandomValue(String.class);
    }

    @SuppressWarnings("ConstantConditions")
    private static List<ObjectName> anyRoleValue() {
        try {
            return Collections.singletonList(new ObjectName("*:type=" + anyString() +",name=" + anyString()));
        } catch (MalformedObjectNameException e) {
            throw RandomGeneratorException.getInstance("Failed to create Role", e);
        }
    }
}
