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

package com.openpojo.issues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey;
import org.junit.Test;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class PerformanceTest {

    private final int max = 1000 * 1000;
    static private final Map stringMap2IntegerList = new HashMap();

    static {

        for (int i = 0; i < 100; i++) {
            List list = new ArrayList(100);
            for (int j = 0; j < 100; j++) {
                list.add(100);
            }
            stringMap2IntegerList.put(i + "", list);
        }
    }

    @Test
    public void testNewIdentity() {
        for (int i = 0; i < max; i++) {
            NewIdentity o1 = new NewIdentity("", stringMap2IntegerList);
            NewIdentity o2 = new NewIdentity("", stringMap2IntegerList);
            o1.equals(o2);
        }
    }

    @Test
    public void testOldIdentity() {
        for (int i = 0; i < max; i++) {
            OldIdentity o1 = new OldIdentity("", stringMap2IntegerList);
            OldIdentity o2 = new OldIdentity("", stringMap2IntegerList);
            o1.equals(o2);
        }
    }
}

@SuppressWarnings("rawtypes")
class OldIdentity {

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((stringMap2IntegerList == null) ? 0 : stringMap2IntegerList.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OldIdentity other = (OldIdentity) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (stringMap2IntegerList == null) {
            if (other.stringMap2IntegerList != null)
                return false;
        } else if (!stringMap2IntegerList.equals(other.stringMap2IntegerList))
            return false;
        return true;
    }

    @BusinessKey
    private final String name;

    @BusinessKey
    private final Map stringMap2IntegerList;

    public OldIdentity(final String name, final Map stringMap2IntegerList) {
        this.name = name;
        this.stringMap2IntegerList = stringMap2IntegerList;
    }

}

@SuppressWarnings("rawtypes")
class NewIdentity extends OldIdentity {

    public NewIdentity(final String name, final Map stringMap2IntegerList) {
        super(name, stringMap2IntegerList);
    }

    @Override
    public boolean equals(final Object obj) {
        return BusinessIdentity.areEqual(this, obj);
    }

    @Override
    public int hashCode() {
        return BusinessIdentity.getHashCode(this);
    }
}
