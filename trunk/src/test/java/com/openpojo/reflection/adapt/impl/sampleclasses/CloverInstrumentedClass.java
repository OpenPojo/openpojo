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

package com.openpojo.reflection.adapt.impl.sampleclasses;

/**
 * @author oshoukry
 */
public class CloverInstrumentedClass {

    public CloverInstrumentedClass() {

    }

    public String normalField;
    public String aNon__CLR3_field;
    public String __CLR3_CloverField;
    public Object __CLR3_2_2_TEST_NAME_SNIFFER_Injected;


    public void __CLR3_NonCloverMethod() { //Clover doesn't inject methods.
    }

    public void normalMethod() {
    }

}
