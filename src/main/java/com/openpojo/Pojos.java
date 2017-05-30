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
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.filters.FilterChain;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.ValidatorBuilder;

import java.util.List;


public final class Pojos {

	/**
	 * Create a instance of ValidatorBuider
	 *
	 * @return
	 */
	public static ValidatorBuilder createValidatorBuilder( ) {
		return ValidatorBuilder.create( );
	}

	/**
	 * This method returns a list of PojoClasses in a package representation with filtering capabilities.
	 *
	 * @param packageName Package to introspect (eg. com.mypackage.pojo).
	 * @param filters     List of filter to apply to the list of PojoClasses. (The PojoClassFilter will wrap the list)
	 * @return
	 */
	public static List<PojoClass> getClasses( String packageName, PojoClassFilter... filters ) {
		return PojoClassFactory.getPojoClasses( packageName, createFilterChain( filters ) );
	}

	/**
	 * This method returns a list of PojoClasses in a package representation recursively with filtering capabilities.
	 *
	 * @param packageName Package to introspect (eg. com.mypackage.pojo).
	 * @param filters     List of filter to apply to the list of PojoClasses. (The PojoClassFilter will wrap the list)
	 * @return
	 */
	public static List<PojoClass> getClassesRecursively( String packageName, PojoClassFilter... filters ) {
		return PojoClassFactory.getPojoClassesRecursively( packageName, createFilterChain( filters ) );
	}

	private static PojoClassFilter createFilterChain( PojoClassFilter... filters ) {
		return new FilterChain( filters );
	}

}