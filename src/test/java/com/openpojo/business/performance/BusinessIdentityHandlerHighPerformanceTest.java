/*
 * Copyright (c) 2010-2013 Osman Shoukry
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

package com.openpojo.business.performance;

import com.openpojo.business.identity.IdentityFactory;
import com.openpojo.business.identity.IdentityHandler;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * @author oshoukry
 */
public class BusinessIdentityHandlerHighPerformanceTest {
    private EndToEndBusinessIdentityExecutor endToEndBusinessIdentityExecutor;
    private List<IdentityHandler> handlers;

    @Before
    public void setup() {
        handlers = new LinkedList<IdentityHandler>();
        endToEndBusinessIdentityExecutor = new EndToEndBusinessIdentityExecutor(handlers);
        int numberOfIdentityHandlers = 1;
        registerIdentityHandlers(numberOfIdentityHandlers);
    }

    @After
    public void tearDown() {
        for (IdentityHandler entry : handlers) {
            deregisterIdentityHandler(entry);
        }
    }

    @Test(timeout = 5000) // This test typically runs under 3 of a second and over 24 if IdentityFactory is synchronized
    public void timeHowLongSynchronizationTakes() {
        endToEndBusinessIdentityExecutor.execute();
        reportErrorsToConsole();
        Assert.assertEquals("Underlying concurrency threw exceptions, see log", 0, endToEndBusinessIdentityExecutor.getExceptions().size());
    }

    @Test
    public void identityHandlerShouldHandleConcurrentModificationInflight() {
        endToEndBusinessIdentityExecutor.shuffleIdentityHandlers(true);
        endToEndBusinessIdentityExecutor.execute();
        reportErrorsToConsole();
        Assert.assertEquals("Underlying concurrency threw exceptions, see log", 0, endToEndBusinessIdentityExecutor.getExceptions().size());
    }

    private void reportErrorsToConsole() {
        for (Throwable entry : endToEndBusinessIdentityExecutor.getExceptions()) {
            entry.printStackTrace();
        }
    }

    private void deregisterIdentityHandler(IdentityHandler entry) {
        IdentityFactory.unregisterIdentityHandler(entry);
    }

    private void registerIdentityHandlers(int count) {
        for (int i = 0; i < count; i++) {
            int identityHandlerMSDelay = 1;
            IdentityHandler handler = new LazyIdentityHandler(identityHandlerMSDelay);
            handlers.add(handler);
            IdentityFactory.registerIdentityHandler(handler);
        }
    }

}
