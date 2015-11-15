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

package com.openpojo.reflection.coverage.impl;

import com.openpojo.reflection.adapt.impl.CoberturaPojoClassAdapter;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class CoberturaTest {

  @Test
  public void shouldHaveCoberturaPojoClassAdapter() {
    Assert.assertEquals(CoberturaPojoClassAdapter.class, Cobertura.getInstance().getPojoClassAdapter().getClass());
  }

  @Test
  public void shouldHaveNoFilter() {
    Assert.assertNull(Cobertura.getInstance().getPojoClassFilter());
  }

  @Test
  public void nameIsCobertura() {
    Assert.assertEquals("Cobertura", Cobertura.getInstance().getName());
  }

  @Test
  public void coverageClassNameIs__netDOTsourceforgeDOTcoberturaDOTcoveragedataDOTLightClassmapListener() {
    Assert.assertEquals("net.sourceforge.cobertura.coveragedata.LightClassmapListener",
        Cobertura.getInstance().getCoverageClassName());
  }
}
