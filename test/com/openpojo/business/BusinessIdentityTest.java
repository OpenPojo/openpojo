package com.openpojo.business;

import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.openpojo.business.exception.BusinessException;

import utils.dummypackage.Person;

public class BusinessIdentityTest {

    public List<PersonEqualityPairTestData> getEqualityTestData() {
        List<PersonEqualityPairTestData> equalityTestData = new LinkedList<PersonEqualityPairTestData>();
        equalityTestData.add(new PersonEqualityPairTestData(new Person(null, "MiddleName", "LastName"),
                             new Person(null, "MiddleName", "LastName"), true));

        equalityTestData.add(new PersonEqualityPairTestData(new Person("FirstName", null, "LastName"),
                new Person("FirstName", null, "LastName"), true));

        equalityTestData.add(new PersonEqualityPairTestData(new Person("FirstName", null, "LastName"),
                new Person(null, "MiddleName", "LastName"), false));

        equalityTestData.add(new PersonEqualityPairTestData(new Person("FirstName", "MiddleName", "LastName"),
                new Person("FiRsTnAmE", "MiDdLeNaMe", "LaStNaMe"), true));

        equalityTestData.add(new PersonEqualityPairTestData(new Person("Id", "FirstName", "MiddleName", "LastName", null, null, null),
                new Person("FiRsTnAmE", "MiDdLeNaMe", "LaStNaMe"), true));

        equalityTestData.add(new PersonEqualityPairTestData(new Person(null, "FirstName", "MiddleName", "LastName", "Password", null, null),
                new Person("FiRsTnAmE", "MiDdLeNaMe", "LaStNaMe"), false));

        equalityTestData.add(new PersonEqualityPairTestData(new Person(null, "FirstName", "MiddleName", "LastName", null, null, null),
                new Person(null, "FiRsTnAmE", "MiDdLeNaMe", "LaStNaMe", "Password", null, null), false));

        equalityTestData.add(new PersonEqualityPairTestData(new Person(null, "FirstName", "MiddleName", "LastName", "Password", null, null),
                new Person(null, "FiRsTnAmE", "MiDdLeNaMe", "LaStNaMe", "password", null, null), false));

        equalityTestData.add(new PersonEqualityPairTestData(new Person(null, "FirstName", "MiddleName", "LastName", "Password", null, null),
                new Person(null, "FiRsTnAmE", "MiDdLeNaMe", "LaStNaMe", "Password", null, null), true));        

        equalityTestData.add(new PersonEqualityPairTestData(null,
                new Person(null, "FiRsTnAmE", "MiDdLeNaMe", "LaStNaMe", "Password", null, null), false));        

        Person samePointer = new Person("FirstName", "MiddleName", "LastName");
        equalityTestData.add(new PersonEqualityPairTestData(samePointer, samePointer, true));        

        return equalityTestData;
    }

    @Test
    public void testIncompleteObject() {
        try {
            BusinessIdentity.areEqual(new Person(null, "MiddleName", null), new Person(null, "MiddleName", null));
            Assert.fail("Expected Exception due to required BusinessKeys not fullfilled");
        } catch (BusinessException be) {
            // expected
        }

        try {
            BusinessIdentity.areEqual(new Person(null, null, "LastName"), new Person(null, null, "LastName"));
            Assert.fail("Expected Exception due to composite BusinessKeys not fullfilled");
        } catch (BusinessException be) {
            // expected
        }
        
        try {
            BusinessIdentity.areEqual("First", "First");
            Assert.fail("Expected Exception due to no BusinessKeys defined");
        } catch (BusinessException be) {
            // expected
        }
    }
    
    @Test
    public void testAreEqual() {
        for (PersonEqualityPairTestData testData : getEqualityTestData()) {
            Assert.assertEquals(String.format("Equality test failed for left=[%s], right=[%s]", testData.left, testData.right), testData.expected, BusinessIdentity.areEqual(testData.left, testData.right));
        }
    }

    @Test
    public void testGetHashCode() {
        Assert.fail("Not impelemented");
    }

    private static class PersonEqualityPairTestData {
        private Person left;
        private Person right;
        private boolean expected;

        /**
         * @param left
         * @param right
         * @param expected
         */
        public PersonEqualityPairTestData(final Person left, final Person right, final boolean expected) {
            this.left = left;
            this.right = right;
            this.expected = expected;
        }
    }
}
