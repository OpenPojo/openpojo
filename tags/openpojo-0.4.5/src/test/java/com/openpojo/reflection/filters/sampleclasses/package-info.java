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

/**
 * This package holdes the sample classes necessary for testing reflection.
 * @author oshoukry
 *
 * The annotation @SomeAnnotation was thrown here because ant will automatically filter out package-info
 * out of the test classes, causing the script to fail upon checking counts of elements returned.<br>
 * <br>
 * Eclipse on the other hand will compile package-info in the class path without any problems.
 */
@SampleAnnotation
package com.openpojo.reflection.filters.sampleclasses;
