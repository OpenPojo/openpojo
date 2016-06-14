/*
 * Copyright (c) 2010-2016 Osman Shoukry
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.openpojo;

import com.openpojo.reflection.PojoClass;
import com.openpojo.sample.DummyClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class PojosTest {

	@Test
	public void createValidatorBuilder(){
		assertNotNull(Pojos.createValidatorBuilder());
	}

	@Test
	public void getClasses() {
		List<PojoClass> classes = Pojos.getClasses( this.getClass( ).getPackage( ).getName( ) + ".sample" );
		assertEquals( 1, classes.size() );
		assertEquals( DummyClass.class, classes.get( 0 ).getClazz());
	}

	@Test
	public void getClassesRecursively() {
		List<PojoClass> classes = Pojos.getClassesRecursively( this.getClass( ).getPackage( ).getName( ) + ".sample" );
		assertEquals( 1, classes.size() );
		assertEquals( DummyClass.class, classes.get( 0 ).getClazz());
	}


}