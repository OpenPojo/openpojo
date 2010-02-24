package com.openpojo.validation.rule;

import com.openpojo.reflection.PojoClass;

/**
 * This Class handles applying rules to PojoClass-es for testing.
 * A rule will return True or False weather 
 * @author oshoukry
 */
public interface Rule {
    /**
     * @param pojoClass
     *          The PojoClass to evaluate that rule on.
     * @throws
     *      RuleViolationException if the rule was violated.
     */
    public void evaluate(PojoClass pojoClass);
}
