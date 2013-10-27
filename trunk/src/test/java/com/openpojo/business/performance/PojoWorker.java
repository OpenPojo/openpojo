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

import com.openpojo.random.RandomFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
* @author oshoukry
*/
class PojoWorker implements Runnable {
    private PojoWithBusinessIdentity[] pojos;
    private int numberOfRepetitions;

    public PojoWorker(int pojoCount, int numberOfRepetitions) {
        this.numberOfRepetitions = numberOfRepetitions;
        pojos = new PojoWithBusinessIdentity[pojoCount];
        for (int i = 0; i < pojoCount; i++) {
            pojos[i] = RandomFactory.getRandomValue(PojoWithBusinessIdentity.class);
        }
    }

    public void run() {
        for (int i = 0; i < numberOfRepetitions; i++) {
            Set<PojoWithBusinessIdentity> mySet = new HashSet<PojoWithBusinessIdentity>();
            Collections.addAll(mySet, pojos);
        }
        
    }
}
