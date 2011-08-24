package com.openpojo.random.collection;

import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.openpojo.log.utils.MessageFormatter;
import com.openpojo.random.RandomGenerator;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.affirm.Affirm;

public class CollectionPackageRandomGeneratorsTest {
    List<PojoClass> collectionRandomGenerators = PojoClassFactory.getPojoClassesRecursively(
            "com.openpojo.random.collection", new RandomGeneratorFilter());

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

                Affirm.affirmTrue(
                        MessageFormatter.format("[{0}] returned incompatible type [{1}] when requesting type [{2}]", randomGenerator.getClass(), firstInstance.getClass(), type),
                        type.isAssignableFrom(firstInstance.getClass()));

                Object secondInstance = randomGenerator.doGenerate(type);
                Affirm.affirmFalse(MessageFormatter.format(
                        "[{0}] returned identical instance for type [{1}] got [[{2}], [{3}]]",
                        randomGenerator.getClass(), type, firstInstance, secondInstance), firstInstance
                        .equals(secondInstance));
            }
        }
    };

    private class RandomGeneratorFilter implements PojoClassFilter {

        public boolean include(PojoClass pojoClass) {
            if (pojoClass.extendz(RandomGenerator.class))
                return true;
            return false;
        }

    }
}
