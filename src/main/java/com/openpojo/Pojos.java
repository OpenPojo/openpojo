package com.openpojo;

import java.util.List;

import com.google.common.base.Preconditions;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.PojoValidator;

public class Pojos {

    /**
     * Returns a SimpleValidator instance
     * 
     * @param packageName
     * @return
     */
    public static SimpleValidator createValidator(final String packageName) {
	return createValidator(packageName, null);

    }

    /**
     * Returns a SimpleValidator instance
     * 
     * @param packageName
     * @param filter
     * @return
     */
    public static SimpleValidator createValidator(final String packageName,
	    final PojoClassFilter filter) {
	Preconditions.checkNotNull(packageName, "packageName can not be null");
	final List<PojoClass> classes = PojoClassFactory
		.getPojoClasses(packageName);
	return new SimpleValidator(new PojoValidator(), classes);

    }
}
