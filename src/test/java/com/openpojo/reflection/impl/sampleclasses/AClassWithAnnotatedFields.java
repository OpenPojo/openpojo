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

package com.openpojo.reflection.impl.sampleclasses;

import com.openpojo.reflection.impl.sampleannotation.SomeAnnotation;

/**
 * @author oshoukry
 */
public class AClassWithAnnotatedFields {

  @SomeAnnotation
  private String annotatedField1;

  @SomeAnnotation
  private String annotatedField2;

  private String nonAnnotatedField1;
  private String nonAnnotatedField2;

}
