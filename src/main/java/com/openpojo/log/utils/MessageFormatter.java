/*
 * Copyright (c) 2010-2015 Osman Shoukry
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License or any
 *    later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openpojo.log.utils;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.text.FieldPosition;
import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * This utility class is used by the logger to format log messages.
 *
 * @author oshoukry
 */
public final class MessageFormatter {
    private static final FieldPosition POSITION = new FieldPosition(0);
    private static final int BUFFER_FACTOR = 8;

    /**
     * This string will be used if the logger is ever called with "null" message.
     */
    static final String DEFAULT_MESSAGE = "No message to be logged, here are the arguments passed:";

    /**
     * This utility method is used by the Logger, and will fall back on a default message if we don't have any.
     *
     * @param message
     *            The message to use with tokens.
     * @param args
     *            The objects to print inside the tokens
     * @return A formatted string ready for logging.
     */
    public static String format(final String message, final Object... args) {
        if (message == null) {
            return usingCurlyBrackets(DEFAULT_MESSAGE + generateCurlyBracketTokens(args == null ? 0 : args.length),
                                      args);
        }
        return usingCurlyBrackets(message, args);
    }

    /**
     * This method takes a message and arguments in the format of tokens using {#} and substitute those for the objects
     * in the array. (i.e. usingCurlyBrackets("this is a {0} message", "cool") returns "this is a cool message".) for
     * further notes on how the formatting actually works, see {@link java.text.MessageFormat} To Note: 1. Remember that
     * to get a single quote out again from "message" you need to double it. (i.e. "It''s great to be alive" and NOT
     * "It's great to be alive"). 2: if you pass in a date object, it doesn't use the date.toString(), but formats it
     * using short date version.
     *
     * @param message
     *            The message to replace its tokens with objects.
     * @param args
     *            The objects to log in the string.
     * @return Returns a formatted string filling all tokens with the objects.
     */
    static String usingCurlyBrackets(final String message, final Object... args) {
        if (message == null) {
            return null;
        }

        final Object[] params = formatArgsToStrings(args);

        final StringBuffer buf = new StringBuffer(message.length()
                + (params == null ? 0 : BUFFER_FACTOR * params.length));
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
    static Object[] formatArgsToStrings(final Object... args) {
        Object[] params = null;
        if (args != null) {
            params = new Object[args.length];
            for (int counter = 0; counter < args.length; counter++) {
                params[counter] = format(args[counter]);
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
        String generatedTokens = "";
        for (int counter = 0; counter < numberOfTokens; counter++) {
            generatedTokens += DEFAULT_FIELDS_FORMAT_PREFIX + counter + DEFAULT_FIELDS_FORMAT_POSTFIX;
        }
        return generatedTokens;
    }

    public static String format(final Object message) {
        if (message == null) {
            return null;
        }

        String formattedMessage = message.toString();

        final Class<?> type = message.getClass();
        if (type.isArray()) {
            formattedMessage = formatArray(message);
        }

        if (message instanceof Throwable) {
            formattedMessage = format((Throwable) message);
        }
        return formattedMessage;
    }

    private static String formatArray(final Object message) {

        final int arrayLength = Array.getLength(message);

        String formattedString = "[";
        for (int i = 0; i < arrayLength; i++) {
            formattedString += format(Array.get(message, i));
            if (i != arrayLength - 1)
                formattedString += ", ";
        }
        formattedString += "]";

        return formattedString;
    }

    private static String format(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);

        pw.flush();
        final LineNumberReader reader = new LineNumberReader(new StringReader(sw.toString()));
        final ArrayList<String> lines = new ArrayList<String>();
        try {
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        } catch (final IOException ex) {
            if (ex instanceof InterruptedIOException) {
                Thread.currentThread().interrupt();
            }
            lines.add(ex.toString());
        }
        return lines.toString();
    }
}
