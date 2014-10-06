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

package com.openpojo.reflection.coverage.impl;

import com.openpojo.reflection.adapt.impl.CloverPojoClassAdapter;
import com.openpojo.reflection.filters.FilterCloverClasses;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class Clover4Test {

    @Test
    public void shouldHaveCloverPojoClassAdapter() {
        Assert.assertEquals(CloverPojoClassAdapter.class, Clover4.getInstance().getPojoClassAdapter().getClass());
    }

    @Test
    public void shouldHaveFilterCloverClasses() {
        Assert.assertEquals(FilterCloverClasses.class, Clover4.getInstance().getPojoClassFilter().getClass());
    }

    @Test
    public void nameIsClover4() {
        Assert.assertEquals("Clover 4", Clover4.getInstance().getName());
    }

    @Test
    public void coverageClassNameIs__com_atlassian_cloverDOTTestNameSniffer() {
        Assert.assertEquals("com_atlassian_clover.TestNameSniffer", Clover4.getInstance().getCoverageClassName());
    }

}
