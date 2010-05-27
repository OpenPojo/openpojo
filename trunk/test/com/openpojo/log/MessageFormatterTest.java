package com.openpojo.log;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class MessageFormatterTest {

    /**
     * Populate and return the input data for testing MessageFormatter.UsingCurlyBrackets(String, Object[]).
     *
     * @return List of data used to test the method.
     */
    private List<UsingCurlyBracketsTestData> getUsingCurlyBracketsTestData() {
        List<UsingCurlyBracketsTestData> usingCurlyBracketsTestData = new LinkedList<UsingCurlyBracketsTestData>();

        usingCurlyBracketsTestData.add(new UsingCurlyBracketsTestData("SimpleMessage", "SimpleMessage", null));
        usingCurlyBracketsTestData.add(new UsingCurlyBracketsTestData(null, null,
                new Object[] { "nothing to format to" }));
        usingCurlyBracketsTestData.add(new UsingCurlyBracketsTestData(
                "Here is how to put single quotes in messages '[wrapped 'with' quotes]'",
                "Here is how to put single quotes in messages ''[{0}]''", new Object[] { "wrapped 'with' quotes" }));

        Date date = new Date();
        usingCurlyBracketsTestData.add(new UsingCurlyBracketsTestData("Two Params a string=[myString] and a date=["
                + date.toString() + "]", "Two Params a string=[{0}] and a date=[{1}]", new Object[] { "myString",
                date.toString() }));

        usingCurlyBracketsTestData.add(new UsingCurlyBracketsTestData(
                "Exception=[java.lang.Exception: This is an exception]", "Exception=[{0}]",
                new Object[] { new Exception("This is an exception") }));
        usingCurlyBracketsTestData.add(new UsingCurlyBracketsTestData("only one param assigned 1=[1st Param] 2=[{1}]",
                "only one param assigned 1=[{0}] 2=[{1}]", new Object[] { "1st Param" }));
        usingCurlyBracketsTestData.add(new UsingCurlyBracketsTestData("only one will print - [extra parameter]",
                "only one will print - [{0}]", new Object[] { "extra parameter", " should not print" }));
        usingCurlyBracketsTestData.add(new UsingCurlyBracketsTestData("nothing shows up for null args=[{0}]",
                "nothing shows up for null args=[{0}]", null));
        usingCurlyBracketsTestData.add(new UsingCurlyBracketsTestData(
                "Message and one param [param one] and this one is null=[null]",
                "Message and one param [{0}] and this one is null=[{1}]", new Object[] { "param one", null }));
        usingCurlyBracketsTestData.add(new UsingCurlyBracketsTestData(
                "Nested Array unfolds [[1, 2, 3], [check, me, out]]", "Nested Array unfolds [{0}, {1}]", new Object[] {
                        new Integer[] { 1, 2, 3 }, new String[] { "check", "me", "out" } }));

        return usingCurlyBracketsTestData;
    }

    /**
     * Populate and return the input data for testing MessageFormatter.flattenArrayElementsToString(Object[]).
     *
     * @return List of data used to test the method.
     */
    private List<FlattenArrayToStringTestData> getFlattenArrayToStringTestData() {
        List<FlattenArrayToStringTestData> flattenArrayToStringTestData = new LinkedList<FlattenArrayToStringTestData>();
        flattenArrayToStringTestData.add(new FlattenArrayToStringTestData("[[1, 2, 3]]", new Object[] { new Integer[] {
                1, 2, 3 } }));
        flattenArrayToStringTestData.add(new FlattenArrayToStringTestData("[[This, is, a string]]",
                new Object[] { new String[] { "This", "is", "a string" } }));

        return flattenArrayToStringTestData;
    }

    /**
     * This method tests the formatter's use with curly brackets. Test method for
     * {@link com.cobalt.dap.log.MessageFormatter#usingCurlyBrackets(java.lang.String, java.lang.Object[])}.
     */
    @Test
    @Ignore
    public final void testUsingCurlyBrackets() {
        for (UsingCurlyBracketsTestData entry : getUsingCurlyBracketsTestData()) {
            //TODO: Assert.assertEquals(entry.expected, MessageFormatter.usingCurlyBrackets(entry.message, entry.fields));
        }
    }

    /**
     * Test method for {@link com.cobalt.dap.log.MessageFormatter#flattenArrayElementsToString(java.lang.Object[])}.
     */
    @Test
    @Ignore
    public final void testFlattenArrayToString() {
        for (FlattenArrayToStringTestData entry : getFlattenArrayToStringTestData()) {
//TODO:            Assert.assertEquals(entry.expected, Arrays.toString(MessageFormatter
//                    .flattenArrayElementsToString(entry.array)));
        }

    }

    private static final String GENERATE_CURLY_BRACKET_TOKEN_PREFIX = "[{";
    private static final String GENERATE_CURLY_BRACKET_TOKEN_POSTFIX = "}]";
    private static final int TOKEN_COUNTER_START = 0;

    /**
     * Test boundary Conditions on GenerateCurlyBracketTokens(), testing with -ve, 0 and +ve.
     * {@link com.cobalt.dap.log.MessageFormatter#generateCurlyBracketTokens(int)}.
     */
    @Test
    @Ignore
    public final void testBoundaryConditionsOnGenerateCurlyBracketTokens() {

//        Assert.assertEquals("", MessageFormatter.generateCurlyBracketTokens(Integer.MIN_VALUE));
//        Assert.assertEquals("", MessageFormatter.generateCurlyBracketTokens(-1));
//        Assert.assertEquals("", MessageFormatter.generateCurlyBracketTokens(0));
//        Assert.assertEquals(GENERATE_CURLY_BRACKET_TOKEN_PREFIX + TOKEN_COUNTER_START
//                + GENERATE_CURLY_BRACKET_TOKEN_POSTFIX, MessageFormatter.generateCurlyBracketTokens(1));
    }

    private static final int MAX_NUMBER_OF_RANDOM_TOKENS = 10;

    /**
     * Test RandomFactory number of curly brackets on GenerateCurlyBracketTokens() see
     * {@link com.cobalt.dap.log.MessageFormatter#generateCurlyBracketTokens(int)}.
     */
    @Test
    @Ignore
    public final void testRandomGenerateCurlyBracketTokens() {
        int randomNumberOfTokensBetween0And10 = new Random().nextInt(MAX_NUMBER_OF_RANDOM_TOKENS + 1);

        StringBuilder assertString = new StringBuilder();
        for (int counter = 0; counter < randomNumberOfTokensBetween0And10; counter++) {
            assertString.append(GENERATE_CURLY_BRACKET_TOKEN_PREFIX).append(counter).append(
                    GENERATE_CURLY_BRACKET_TOKEN_POSTFIX);
        }
//TODO:        Assert.assertEquals(assertString.toString(), MessageFormatter
//                .generateCurlyBracketTokens(randomNumberOfTokensBetween0And10));

    }

    /**
     * Storage structure for testing MessageFormatter.usingCurlyBrackets(java.lang.String, java.lang.Object[]).
     * see {@link com.cobalt.dap.log.MessageFormatter#usingCurlyBrackets(java.lang.String, java.lang.Object[])}
     *
     * @author oshoukry
     *
     */
    private static final class UsingCurlyBracketsTestData {
        private final String expected;
        private final String message;
        private final Object[] fields;

        /**
         * Full contructor for convenience.
         *
         * @param expected
         *            What should the "MessageFormatter.UsingCurlyBrackets" return given the input.
         * @param message
         *            The input message for
         * @param fields
         *            The tokens for literal substitution.
         */
        private UsingCurlyBracketsTestData(final String expected, final String message, final Object[] fields) {
            this.expected = expected;
            this.message = message;
            this.fields = fields;
        }
    }

    /**
     * This class carries the expected final result as well as the test data input for usingCurlyBrackets method.
     * see {@link com.cobalt.dap.log.MessageFormatter#usingCurlyBrackets(java.lang.String, java.lang.Object[])}
     *
     * @author oshoukry
     *
     */
    private static final class FlattenArrayToStringTestData {
        private final String expected;
        private final Object[] array;

        /**
         * @param expected
         * @param array
         */
        private FlattenArrayToStringTestData(final String expected, final Object[] array) {
            this.expected = expected;
            this.array = array;
        }
    }
}
