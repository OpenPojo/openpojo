/**
 * Copyright (C) 2010 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openpojo.log;

import java.text.FieldPosition;
import java.text.MessageFormat;
import java.util.Arrays;

/**
 * This utility class is used by the logger to format log messages.
 * @author oshoukry
 */
final class MessageFormatter {
    private static final FieldPosition POSITION = new FieldPosition(0);
    private static final int BUFFER_FACTOR = 8;

    /**
     * This string will be used if the logger is ever called with "null" message.
     */
    static final String DEFAULT_MESSAGE = "No message to be logged, here are the arguments passed:";

    /**
     * Blocking the default constructor on this utility class.
     */
    private MessageFormatter() {
    }

    /**
     * This utility method is used by the Logger, and will fall back on a default message if we don't have any.
     * @param message
     *              The message to use with tokens.
     * @param args
     *              The objects to print inside the tokens
     * @return
     *              A formatted string ready for logging.
     */
    static String format(final String message, final Object ...args) {
        if (message == null) {
            return usingCurlyBrackets(DEFAULT_MESSAGE
                    + generateCurlyBracketTokens(args == null ? 0 : args.length), args);
        }
        return usingCurlyBrackets(message, args);
    }

    /**
     * This method takes a message and arguments in the format of tokens using {#} and substitute those for the objects
     * in the array. (i.e. usingCurlyBrackets("this is a {0} message", "cool") returns "this is a cool message".)
     *
     * for further notes on how the formatting actually works, see {@link java.text.MessageFormat}
     *
     * To Note:
     *
     * 1. Remember that to get a single quote out again from "message" you need to double it. (i.e.
     * "It''s great to be alive" and NOT "It's great to be alive").
     *
     * 2: if you pass in a date object, it doesn't use the date.toString(), but formats it using short date version.
     *
     * @param message
     *          The message to replace its tokens with objects.
     * @param args
     *          The objects to log in the string.
     * @return
     *          Returns a formated string filling all tokens with the objects.
     */
    static String usingCurlyBrackets(final String message, final Object... args) {
        if (message == null) {
            return null;
        }

        Object[] params = flattenArrayElementsToString(args);

        StringBuffer buf = new StringBuffer(message.length() + (params == null ? 0 : BUFFER_FACTOR * params.length));
        new MessageFormat(message).format(params, buf, POSITION);

        return buf.toString();
    }

    /**
     * This method is responsible for taking arrays turning them into a string. (i.e. if we pass in an Object[][]), it
     * will become and Object[String];
     *
     * @param args
     *            The list of params to scan for arrays out.
     * @return a one level array of objects to format.
     */
    static Object[] flattenArrayElementsToString(final Object... args) {
        Object[] params = null;
        if (args != null) {
            params = new Object[args.length];
            for (int counter = 0; counter < args.length; counter++) {
                if (args[counter] != null) {
                    Class<? extends Object> type = args[counter].getClass();
                    if (type.isArray()) {
                        params[counter] = Arrays.toString((Object[])args[counter]);
                    } else {
                        params[counter] = args[counter];
                    }
                }
            }
        }
        return params;
    }

    private static final String DEFAULT_FIELDS_FORMAT_PREFIX = "[{";
    private static final String DEFAULT_FIELDS_FORMAT_POSTFIX = "}]";
    /**
     * This is a quick utility that will generate enclosed curly brackets for convenience.
     *
     * @param numberOfTokens
     *            The number of tokens you want to get back.
     * @return a string with tokens that can take in the "args" right away in "usingCurlyBrackets"
     */
    static String generateCurlyBracketTokens(final int numberOfTokens) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int counter = 0; counter < numberOfTokens; counter++) {
            stringBuilder.append(DEFAULT_FIELDS_FORMAT_PREFIX).append(counter).append(DEFAULT_FIELDS_FORMAT_POSTFIX);
        }
        return stringBuilder.toString();
    }
}
