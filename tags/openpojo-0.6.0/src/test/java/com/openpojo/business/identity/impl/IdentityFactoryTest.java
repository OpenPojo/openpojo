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

package com.openpojo.business.identity.impl;

import java.util.ArrayList;
import java.util.List;

import com.openpojo.business.exception.BusinessException;
import com.openpojo.business.identity.IdentityFactory;
import com.openpojo.business.identity.IdentityHandler;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class IdentityFactoryTest {

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void shouldNotAllowRegistrationOfNullHandler() {
        IdentityFactory.registerIdentityHandler(null);
    }

    @Test
    public void whenNoIdentityHandlerFoundShouldThrowException() {
        Object o = new Object();
        List<IdentityHandler> identityHandlers = new ArrayList<IdentityHandler>();

        try {
            while (IdentityFactory.getIdentityHandler(o) != null) {
                identityHandlers.add(IdentityFactory.getIdentityHandler(o));
                IdentityFactory.unregisterIdentityHandler(IdentityFactory.getIdentityHandler(o));

            }
        } catch (BusinessException ignored) {
            return;
        } finally {
            // restore setting;
            for (int i = identityHandlers.size() - 1; i >= 0; i--) {
                IdentityFactory.registerIdentityHandler(identityHandlers.get(i));
            }
        }
        Assert.fail("BusinessException should have been thrown");
    }
}
