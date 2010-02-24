package com.openpojo.validation;

import java.util.LinkedList;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.test.Tester;

/**
 * @author oshoukry
 *
 */
public class PojoValidator {
    private List<Rule> rules = new LinkedList<Rule>();
    private List<Tester> testers = new LinkedList<Tester>();
    
    /**
     * Add Rule to use for validation.
     * @param rule
     *          The rule to Add.
     */
    public void add(Rule rule) {
        rules.add(rule);
    }
    
    /**
     * Add Tester to use for validation.
     * @param tester
     *          The Tester to Add.
     */
    public void add(Tester tester) {
        
        testers.add(tester);
    }
    
    /**
     * Run the validation, this will invoke all the rule.evaluate as well as tester.run.
     * @param pojoClass
     *          The PojoClass to validate.
     */
    public void runValidation(PojoClass pojoClass) {
        for (Rule rule : rules) {
            rule.evaluate(pojoClass);
        }
        
        for (Tester tester : testers) {
            tester.run(pojoClass);
        }
    }

}
