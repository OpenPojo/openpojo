package com.openpojo.business;

import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.openpojo.business.annotation.BusinessKey;
import com.openpojo.business.exception.BusinessException;
import com.openpojo.utils.dummypackage.Person;

public class BusinessIdentityTest {

    public List<HashCodeTestData> getHashCodeTestData() {

        List<HashCodeTestData> hashCodeTestData = new LinkedList<HashCodeTestData>();
        hashCodeTestData.add(new HashCodeTestData("id", null, "Optional1", null, null));
        hashCodeTestData.add(new HashCodeTestData("id", "name", null, "Optional2", null));
        hashCodeTestData.add(new HashCodeTestData("id", "name", null, "Optional2", "nonBusinessKey"));
        return hashCodeTestData;
    }

    public List<PersonEqualityPairTestData> getEqualityTestData() {
        List<PersonEqualityPairTestData> equalityTestData = new LinkedList<PersonEqualityPairTestData>();
        equalityTestData.add(new PersonEqualityPairTestData(new Person(null, "MiddleName", "LastName"), new Person(
                null, "MiddleName", "LastName"), true));

        equalityTestData.add(new PersonEqualityPairTestData(new Person("FirstName", null, "LastName"), new Person(
                "FirstName", null, "LastName"), true));

        equalityTestData.add(new PersonEqualityPairTestData(new Person("FirstName", null, "LastName"), new Person(null,
                "MiddleName", "LastName"), false));

        equalityTestData.add(new PersonEqualityPairTestData(new Person("FirstName", "MiddleName", "LastName"),
                new Person("FiRsTnAmE", "MiDdLeNaMe", "LaStNaMe"), true));

        equalityTestData.add(new PersonEqualityPairTestData(new Person("Id", "FirstName", "MiddleName", "LastName",
                null, null, null), new Person("FiRsTnAmE", "MiDdLeNaMe", "LaStNaMe"), true));

        equalityTestData.add(new PersonEqualityPairTestData(new Person(null, "FirstName", "MiddleName", "LastName",
                "Password", null, null), new Person("FiRsTnAmE", "MiDdLeNaMe", "LaStNaMe"), false));

        equalityTestData.add(new PersonEqualityPairTestData(new Person(null, "FirstName", "MiddleName", "LastName",
                null, null, null), new Person(null, "FiRsTnAmE", "MiDdLeNaMe", "LaStNaMe", "Password", null, null),
                false));

        equalityTestData.add(new PersonEqualityPairTestData(new Person(null, "FirstName", "MiddleName", "LastName",
                "Password", null, null),
                new Person(null, "FiRsTnAmE", "MiDdLeNaMe", "LaStNaMe", "password", null, null), false));

        equalityTestData.add(new PersonEqualityPairTestData(new Person(null, "FirstName", "MiddleName", "LastName",
                "Password", null, null),
                new Person(null, "FiRsTnAmE", "MiDdLeNaMe", "LaStNaMe", "Password", null, null), true));

        equalityTestData.add(new PersonEqualityPairTestData(null, new Person(null, "FiRsTnAmE", "MiDdLeNaMe",
                "LaStNaMe", "Password", null, null), false));

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

        try {
            BusinessIdentity.getHashCode(null);
            Assert.fail("Expected Exception due to null object");
        } catch (BusinessException be) {
            // expected
        }
    }

    @Test
    public void testAreEqual() {
        for (PersonEqualityPairTestData testData : getEqualityTestData()) {
            Assert.assertEquals(String.format("Equality test failed for left=[%s], right=[%s]", testData.left,
                    testData.right), testData.expectedEqualityResult, BusinessIdentity.areEqual(testData.left,
                    testData.right));
        }
    }

    @Test
    public void testGetHashCode() {
        for (HashCodeTestData hashCodeTestData : getHashCodeTestData()) {
            Assert.assertEquals(String.format("HashCode test failed for Data=[%s]", hashCodeTestData), hashCodeTestData.expectedHashCode, BusinessIdentity.getHashCode(hashCodeTestData));
        }

    }

    @Test
    public void testToString() {
        ToStringTestData toStringTestData = new ToStringTestData();
        String toString = BusinessIdentity.toString(toStringTestData);
        Assert.assertTrue("BusinessIdentity.toString() failed!!", toString.startsWith("com.openpojo.business.BusinessIdentityTest$ToStringTestData [@") && toString.endsWith(": instance_name=Instance Name, static_name=Static Name, STATIC_FINAL_NAME=Static Final Name]"));
    }

    private static class PersonEqualityPairTestData {
        private final Person left;
        private final Person right;
        private final boolean expectedEqualityResult;

        /**
         * @param left
         * @param right
         * @param expectedEqualityResult
         */
        public PersonEqualityPairTestData(final Person left, final Person right, final boolean expected) {
            this.left = left;
            this.right = right;
            expectedEqualityResult = expected;
        }
    }

    @SuppressWarnings("unused")
    private static class HashCodeTestData {
        @BusinessKey
        private final String id;

        @BusinessKey(required = false, caseSensitive = false)
        private final String name;

        @BusinessKey(composite = true)
        private final String optionalPart1;

        @BusinessKey(composite = true)
        private final String optionalPart2;

        private final String nonBusinessKey;

        private int expectedHashCode = 1;
        /**
         * @param id
         * @param name
         */
        public HashCodeTestData(final String id, final String name, final String optionalPart1, final String optionalPart2, final String nonBusinessKey) {
            this.id = id;
            this.name = name;
            this.optionalPart1 = optionalPart1;
            this.optionalPart2 = optionalPart2;
            this.nonBusinessKey = nonBusinessKey;
            expectedHashCode = 31 * expectedHashCode + (id == null ? 0 : id.hashCode());
            expectedHashCode = 31 * expectedHashCode + (name == null ? 0 : name.hashCode());
            expectedHashCode = 31 * expectedHashCode + (optionalPart1 == null ? 0 : optionalPart1.hashCode());
            expectedHashCode = 31 * expectedHashCode + (optionalPart2 == null ? 0 : optionalPart2.hashCode());
         }
    }


    @SuppressWarnings("unused")
    private static class ToStringTestData {
        private final String instance_name="Instance Name";
        private static String static_name = "Static Name";
        private static final String STATIC_FINAL_NAME = "Static Final Name";
    }
}
