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

package com.openpojo.utils.log;

/**
 * @author oshoukry
 *
 */
public final class MockAppenderFactory {
    private static final MockAppender LOG4JAPPENDER = new MockAppenderLog4J();
    private static final MockAppender JAVALOGGERAPPENDER = new MockAppenderJavaLogger();

    public static MockAppender getMockAppender(final String name) {
        if (name.equals("Log4J")) {
            return LOG4JAPPENDER;
        }
        if (name.equals("Java")) {
            return JAVALOGGERAPPENDER;
        }
        throw new IllegalArgumentException(String.format("Unknown appender requested [%s]", name));
    }

}
