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

package com.openpojo.reflection.filters;

import java.lang.annotation.Annotation;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.PojoMethod;

/**
 * @author oshoukry
 */
public class PojoClassStub implements PojoClass {

    private final boolean isSynthetic;

    public PojoClassStub(boolean isSynthetic) {
        this.isSynthetic = isSynthetic;
    }

    public boolean isInterface() {
        return false;
    }

    public boolean isAbstract() {
        return false;
    }

    public boolean isConcrete() {
        return false;
    }

    public boolean isEnum() {
        return false;
    }

    public boolean isArray() {
        return false;
    }

    public boolean isFinal() {
        return false;
    }

    public boolean isSynthetic() {
        return isSynthetic;
    }

    public List<PojoField> getPojoFields() {
        return null;
    }

    public List<PojoMethod> getPojoMethods() {
        return null;
    }

    public List<PojoMethod> getPojoConstructors() {
        return null;
    }

    public boolean extendz(Class<?> type) {
        return false;
    }

    public PojoClass getSuperClass() {
        return null;
    }

    public List<PojoClass> getInterfaces() {
        return null;
    }

    public Class<?> getClazz() {
        return null;
    }

    public boolean isNestedClass() {
        return false;
    }

    public boolean isStatic() {
        return false;
    }

    public void copy(Object from, Object to) {

    }

    public String toString(Object instance) {
        return null;
    }

    public String getSourcePath() {
        return null;
    }

    public String getName() {
        return null;
    }

    public List<? extends Annotation> getAnnotations() {
        return null;
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return null;
    }
}
