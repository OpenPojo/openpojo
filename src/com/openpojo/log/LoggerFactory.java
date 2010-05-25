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

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.facade.FacadeFactory;

/**
 * This Factory will create and return a logger based on the current availability of the underlying logger.
 * The system has support currently for SL4J, Log4J, JavaLogger.
 * The factory will detect runtime which logger is available and return the appropriate logger
 * The order of precedence is as follows:
 * 1. SL4J
 * 2. org.apache.log4j.Logger
 * 3. java.util.logging.Logger.
 */
public final class LoggerFactory {

    private LoggerFactory() {
    }

    /**
     * This method returns an instance of Logger class for logging.
     *
     * @param clazz
     *            The Class you are using to log through.
     * @return
     *         returns an instance of the Logger.
     */
    public static Logger getLogger(final Class<?> clazz) {
        return (Logger) ActiveLogger.activeLoggerPojoClass.newInstance(getLoggerCategory(clazz));
    }

    /**
     * This method returns an instance of Logger class for logging.
     *
     * @param catagory
     *            The category you want your logs to go through.
     * @return
     *         returns and instance of the logger.
     */
    public static Logger getLogger(final String category) {
        return (Logger) ActiveLogger.activeLoggerPojoClass.newInstance(getLoggerCategory(category));
    }

    private static String getLoggerCategory(final String category) {
        return category == null ? "NULL_CATEGORY" : category;
    }

    private static String getLoggerCategory(final Class<?> clazz) {
        return clazz == null ? getLoggerCategory((String) null) : clazz.getName();
    }

    /**
     * This ActiveLogger class is a container that holds the active logging underlying framework for
     * the current instance of the VM.
     * The reason this is in a seperate class is to enable lazy loading, the resolution of the active logging mechanism
     * won't happen until the first call to get the logging framework.
     *
     * @author oshoukry
     */
    private static class ActiveLogger {
        private static final String[] SUPPORTED_LOGGERS = new String[]{ "com.openpojo.log.impl.SLF4JLogger",
                "com.openpojo.log.impl.Log4JLogger", "com.openpojo.log.impl.JavaLogger" };

        private final static PojoClass activeLoggerPojoClass = FacadeFactory
                .getLoadedFacadePojoClass(SUPPORTED_LOGGERS);

        static {
            ((Logger) activeLoggerPojoClass.newInstance(ActiveLogger.class.getName())).info(
                    "Logging subsystem initialized to utilized to [{0}]", activeLoggerPojoClass.getClazz());
        }

    }
}
