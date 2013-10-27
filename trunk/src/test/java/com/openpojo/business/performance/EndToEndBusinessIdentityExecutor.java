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

import java.util.List;

public class EndToEndBusinessIdentityExecutor {

    private List<IdentityHandler> identityHandlers;
    private boolean turnOnIdentityHandleShuffling;
    private List<Throwable> exceptions;

    public EndToEndBusinessIdentityExecutor(List<IdentityHandler> identityHandlers) {
        this.identityHandlers = identityHandlers;
    }

    public void execute() {
        int numberOfThreadsInThreadPool = 10;
        PausableThreadPoolExecutor pausableThreadPoolExecutor = new PausableThreadPoolExecutor(numberOfThreadsInThreadPool);
        pausableThreadPoolExecutor.pause();
        int numberOfJobs = 20;
        for (int i = 0; i < numberOfJobs; i++) {
            pausableThreadPoolExecutor.execute(getPojoWorker());
        }

        System.out.println("Starting ThreadPool work");
        pausableThreadPoolExecutor.shutdown();
        pausableThreadPoolExecutor.resume();

        while (!pausableThreadPoolExecutor.isTerminated()) {
            try {
                if (turnOnIdentityHandleShuffling)
                    doIdentityHandleShuffle();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        exceptions = pausableThreadPoolExecutor.getExceptions();
    }

    public List<Throwable> getExceptions() {
        return exceptions;
    }

    private void doIdentityHandleShuffle() {
        for (IdentityHandler entry : identityHandlers) {
            IdentityFactory.unregisterIdentityHandler(entry);
            IdentityFactory.registerIdentityHandler(entry);
        }
    }

    private PojoWorker getPojoWorker() {
        int pojoWorkerElementCount = 5;
        int numberOfTimesAPojoWorkerRepeatsWork = 100;
        return new PojoWorker(pojoWorkerElementCount, numberOfTimesAPojoWorkerRepeatsWork);
    }

    public void shuffleIdentityHandlers(boolean turnOnIdentityHandleShuffling) {
        this.turnOnIdentityHandleShuffling = turnOnIdentityHandleShuffling;

    }
}