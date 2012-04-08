package com.openpojo.random.collection;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.openpojo.log.utils.MessageFormatter;
import com.openpojo.random.RandomGenerator;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.filters.FilterChain;
import com.openpojo.reflection.filters.FilterNestedClasses;
import com.openpojo.reflection.filters.FilterNonConcrete;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.affirm.Affirm;

public class CollectionAndMapPackageRandomGeneratorsTest {
    private static final List<PojoClass> collectionRandomGenerators = new LinkedList<PojoClass>();
    private static final String[] packages = new String[] { "com.openpojo.random.collection", "com.openpojo.random.map" };

    @BeforeClass
    public static void setup() {
        for (String pkg : packages) {
            collectionRandomGenerators.addAll(PojoClassFactory.getPojoClassesRecursively(pkg, new FilterChain(
                    new RandomGeneratorFilter(), new FilterNestedClasses(), new FilterNonConcrete())));
        }
    }

    /**
     * This test will test every random generator against its declared types, for every type returned from getTypes.
     */
    @Test
    public void shouldReturnRandomInstanceForDeclaredType() {
        for (PojoClass randomGeneratorPojoClass : collectionRandomGenerators) {
            RandomGenerator randomGenerator = (RandomGenerator) InstanceFactory.getInstance(randomGeneratorPojoClass);
            Collection<Class<?>> generatorTypes = randomGenerator.getTypes();
            for (Class<?> type : generatorTypes) {
                Object firstInstance = randomGenerator.doGenerate(type);
                Affirm.affirmNotNull(
                        MessageFormatter.format("[{0}] returned null for type [{1}]", randomGenerator.getClass(), type),
                        firstInstance);

                Affirm.affirmTrue(MessageFormatter.format(
                        "[{0}] returned incompatible type [{1}] when requesting type [{2}]",
                        randomGenerator.getClass(), firstInstance.getClass(), type), type
                        .isAssignableFrom(firstInstance.getClass()));

                int counter = 10;
                Object secondInstance = null;
                while (counter > 0) {
                    secondInstance = randomGenerator.doGenerate(type);
                    if (!firstInstance.equals(secondInstance)) {
                        break;
                    }
                    counter--;
                }
                Affirm.affirmFalse(
                        MessageFormatter.format("[{0}] returned identical instances for type [{1}]",
                                randomGenerator.getClass(), type), firstInstance.equals(secondInstance));

            }
        }
    };

    private static class RandomGeneratorFilter implements PojoClassFilter {
        public boolean include(PojoClass pojoClass) {
            if (pojoClass.extendz(RandomGenerator.class))
                return true;
            return false;
        }
    }
}