/*
 * Copyright (c) 2010-2018 Osman Shoukry
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

package com.openpojo.reflection.service;

import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.registry.Service;

/**
 * A Service to lookup java classes.
 *
 * @author oshoukry
 */
public interface PojoClassLookupService extends Service {

  List<PojoClass> enumerateClassesByExtendingType(final String packageName, final Class<?> type, final PojoClassFilter
      pojoClassFilter);

  PojoClass getPojoClass(final Class<?> clazz);

  List<PojoClass> getPojoClasses(final String packageName);

  List<PojoClass> getPojoClasses(final String packageName, final PojoClassFilter pojoClassFilter);

  List<PojoClass> getPojoClassesRecursively(final String packageName, final PojoClassFilter pojoClassFilter);

}
