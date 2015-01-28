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

package com.openpojo.reflection.java.packageloader.utils;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

/**
 * @author oshoukry
 */
public class PackageNameHelperTest {

    @Test
    public void whenChildSubPath_thenSubPath() {
        String somePackage = "com.openpojo.parent";
        String subPackage = "com.openpojo.parent.subpath";

        Assert.assertThat(PackageNameHelper.getDirectSubPackageName(somePackage, subPackage), is(subPackage));
    }

    @Test
    public void whenNonChildPath_thenNull() {
        String somePackage = "com.openpojo.parent";
        String subPackage = "com.openpojo.subpath";

        Assert.assertThat(PackageNameHelper.getDirectSubPackageName(somePackage, subPackage), is(nullValue()));
    }

    @Test
    public void whenGrandChildPath_thenChildPath() {
        String somePackage = "com.openpojo.parent";
        String subPackage = "com.openpojo.parent.childpath";
        String grandChild = subPackage + ".grandChild";

        Assert.assertThat(PackageNameHelper.getDirectSubPackageName(somePackage, grandChild), is(subPackage));
    }

    @Test
    public void whenNonChildPathButNameOverlap_thenNull() {
        String somePackage = "com.openpojo.parent";
        String subPackage = "com.openpojo.parentalso.childpath";
        String grandChild = subPackage + ".grandChild";

        Assert.assertThat(PackageNameHelper.getDirectSubPackageName(somePackage, grandChild), is(nullValue()));
    }


}
