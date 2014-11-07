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

package com.openpojo.business.performance;

import java.util.concurrent.CountDownLatch;

import com.openpojo.business.identity.IdentityFactory;

/**
 * @author oshoukry
 */
public class RegisterUnRegisterJob implements Runnable {

    private final CountDownLatch startSignal;
    private volatile int count;
    private final NoOpIdentityHandler noOpIdentityHandler = new NoOpIdentityHandler();

    public RegisterUnRegisterJob(final CountDownLatch startSignal, final int count) {
        this.startSignal = startSignal;
        this.count = count;
    }

    public void run() {
        startSignal.countDown();
        try {
            startSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        try {
            while (count > 0) {
                --count;
                IdentityFactory.registerIdentityHandler(noOpIdentityHandler);
                IdentityFactory.unregisterIdentityHandler(noOpIdentityHandler);
                IdentityFactory.getIdentityHandler(new Object());
            }

        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            IdentityFactory.unregisterIdentityHandler(noOpIdentityHandler);
        }
    }

    public boolean hasCompletedSuccessfully() {
        return count <= 0;
    }
}
