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

package com.openpojo.validation.impl;

import java.util.LinkedList;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.filters.FilterChain;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.utils.ValidationHelper;

/**
 * @author oshoukry
 */
public class DefaultValidator implements Validator {
    private final List<Rule> rules = new LinkedList<Rule>();
    private final List<Tester> testers = new LinkedList<Tester>();

    public DefaultValidator(List<Rule> rules, List<Tester> testers) {
        this.rules.addAll(rules);
        this.testers.addAll(testers);
    }

    public void validate(String packageName, PojoClassFilter... filters) {
        PojoClassFilter pojoClassFilter = new FilterChain(filters);
        List<PojoClass> pojoClasses = PojoClassFactory.getPojoClasses(packageName, pojoClassFilter);
        validate(pojoClasses);
    }

    public void validateRecursively(String packageName, PojoClassFilter... filters) {
        PojoClassFilter pojoClassFilter = new FilterChain(filters);
        List<PojoClass> pojoClasses = PojoClassFactory.getPojoClassesRecursively(packageName, pojoClassFilter);
        validate(pojoClasses);
    }

    public void validate(List<PojoClass> pojoClasses) {
        for (PojoClass pojoClass : pojoClasses)
            validate(pojoClass);
    }

    public void validate(PojoClass pojoClass) {
        ValidationHelper.runValidation(pojoClass, this.rules, this.testers);
    }


}
