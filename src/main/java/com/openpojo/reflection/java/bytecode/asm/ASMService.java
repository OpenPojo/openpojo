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

package com.openpojo.reflection.java.bytecode.asm;

import com.openpojo.cache.CacheStorage;
import com.openpojo.cache.CacheStorageFactory;
import com.openpojo.log.Logger;
import com.openpojo.log.LoggerFactory;
import com.openpojo.reflection.exception.ReflectionException;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

/**
 * @author oshoukry
 */
public class ASMService {
    private SimpleClassLoader simpleClassLoader = new SimpleClassLoader();
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private CacheStorage<Class<?>> alreadyGeneratedClasses = CacheStorageFactory.getPersistentCacheStorage();

    private ASMService() {
    }

    public static ASMService getInstance() {
        return Instance.INSTANCE;
    }

    public <T> Class<? extends T> createSubclassFor(Class<T> clazz) {
        SubClassDefinition subClassDefinition = new DefaultSubClassDefinition(clazz);
        return createSubclassFor(clazz, subClassDefinition);
    }

    @SuppressWarnings("unchecked")
    public <T> Class<? extends T> createSubclassFor(Class<T> clazz, SubClassDefinition subClassDefinition) {
        Class<? extends T> generatedClass = (Class<? extends T>) alreadyGeneratedClasses.get(subClassDefinition.getGeneratedClassName());

        if (generatedClass != null) {
            logger.info("Reusing already generated sub-class for class [{0}]", clazz.getName());
        } else {
            try {
                generatedClass = (Class<? extends T>) simpleClassLoader.loadThisClass(getSubClassByteCode(subClassDefinition),
                        subClassDefinition.getGeneratedClassName());
                alreadyGeneratedClasses.add(subClassDefinition.getGeneratedClassName(), generatedClass);
            } catch (Throwable throwable) {
                throw ReflectionException.getInstance("Failed to create subclass for class: " + clazz, throwable);
            }
        }
        return generatedClass;
    }

    private byte[] getSubClassByteCode(SubClassDefinition subClassDefinition) {

        ClassReader classReader = subClassDefinition.getClassReader();
        ClassWriter cw = new ClassWriter(0);

        classReader.accept(new SubClassCreator(cw, subClassDefinition.getGeneratedClassNameAsJDKPath()), 0);

        cw.visitEnd();
        return cw.toByteArray();
    }

    private static class Instance {
        private static final ASMService INSTANCE = new ASMService();
    }
}
