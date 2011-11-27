/**
 * Copyright (C) 2011 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openpojo.random.service;

import com.openpojo.random.RandomGenerator;
import com.openpojo.registry.Service;

/**
 * This service holds a registry for RandomGenerators.
 *
 * @author oshoukry
 *
 */
public interface RandomGeneratorService extends Service {

    /**
     * Register a RandomGenerator. The RandomGenerator's getTypes will be invoked and all types returned will be
     * registered against this RandomGenerator over-writing any prior registrations for given types.
     *
     * @param randomGenerator
     */
    public void registerRandomGenerator(RandomGenerator randomGenerator);

    /**
     * This method set the RandomGenerator to use if non are registered for given type. This randomGenerator's
     * getTypes() return will be ignored and not consulted for routing.
     *
     * There can only be one (1) default random generator.
     *
     * @param randomGenerator
     */
    public void setDefaultRandomGenerator(RandomGenerator randomGenerator);

    /**
     * Returns the default registered RandomGenerator;
     *
     * @return
     */
    public RandomGenerator getDefaultRandomGenerator();

    /**
     * This retrieves the most appropriate RandomGenerator for a givenType. If there is more than one possible random
     * generator valid for a given type, one will randomly be selected.
     *
     * The only reason multiple may exist is due to inheritance, for example if you register two random generators one
     * to handle LinkedList, and one to handle Set, and request a randomGenerator for type "Collection", either of those
     * randomGenerators could be used. However, if there is a random generator registered for Collection, that will be
     * the one used.
     *
     * @param type
     */
    public RandomGenerator getRandomGeneratorByType(Class<?> type);
}
