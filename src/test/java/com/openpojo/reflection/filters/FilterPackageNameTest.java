package com.openpojo.reflection.filters;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Test;

import java.util.List;

/**
 * @author ohiocowboy
 */
public class FilterPackageNameTest
{
    @Test
    public void testNonFilteredPackageNameSize()
    {
        List<PojoClass> pojoClasses = PojoClassFactory
                .getPojoClassesRecursively("com.openpojo", null);
        Affirm.affirmEquals("Classes added / removed?", 751, pojoClasses.size());

        pojoClasses = PojoClassFactory
                .getPojoClassesRecursively("com.openpojo", new FilterPackageName("sampleclasses"));
        Affirm.affirmEquals("Classes added / removed?", 140, pojoClasses.size());
    }

    @Test
    public void testVerifyPackageNames()
    {
        String packageName = "sampleclasses";
        List<PojoClass> pojoClasses = PojoClassFactory
                .getPojoClassesRecursively("com.openpojo", new FilterPackageName(packageName));
        for (PojoClass pojoClass : pojoClasses)
        {
            Package aPackage = pojoClass.getClazz().getPackage();
            Affirm.affirmTrue(String.format("[%s] has correct string in package", pojoClass),
                    aPackage.getName().contains(packageName));
        }
    }
}
