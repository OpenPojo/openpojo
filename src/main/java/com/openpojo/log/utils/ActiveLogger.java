/*
 * Copyright (c) 2010-2014 Osman Shoukry
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

import com.openpojo.log.Logger;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.facade.FacadeFactory;
import com.openpojo.reflection.impl.PojoClassFactory;

/**
 * This ActiveLogger class is a container that holds the active logging underlying framework for
 * the current instance of the VM.
 * The reason this is in a separate class is to enable lazy loading, the resolution of the active logging mechanism
 * won't happen until the first call to get the logging framework.
 *
 * @author oshoukry
 */
public class ActiveLogger {
    public static final String[] SUPPORTED_LOGGERS = new String[]{ "com.openpojo.log.impl.SLF4JLogger",
            "com.openpojo.log.impl.Log4JLogger", "com.openpojo.log.impl.JavaLogger" };
    private static final String NULL_CATEGORY = "NULL_CATEGORY";

    private static PojoClass activeLoggerPojoClass;

    static {
        autoDetect();
    }

    /**
     * This method returns a Logger instance for use in logging.
     *
     * @param category
     *            The category for the requested logger.
     * @return
     *         Instance of a logger for logging usage.
     */
    public static synchronized Logger getInstance(final String category) {
        return (Logger) InstanceFactory.getInstance(activeLoggerPojoClass, getLoggerCategory(category));
    }

    private static String getLoggerCategory(final String category) {
        return category == null ? NULL_CATEGORY : category;
    }

    /**
     * This method allows full control to set the active logger to a specific one.
     *
     * @param loggerClass
     *            The logger class to use.
     */
    public static void setActiveLogger(final Class<? extends Logger> loggerClass) {
        setActiveLoggerPojoClass(PojoClassFactory.getPojoClass(loggerClass));
    }

    /**
     * This method will auto detect the underlying logging framework available and set the current active logging
     * framework accordingly.
     */
    public static void autoDetect() {
        PojoClass newActiveLoggerPojoClass = FacadeFactory.getLoadedFacadePojoClass(SUPPORTED_LOGGERS);
        if (activeLoggerPojoClass == null || newActiveLoggerPojoClass.getClass() != activeLoggerPojoClass.getClass()) {
            setActiveLoggerPojoClass(newActiveLoggerPojoClass);
        }
    }

    private static synchronized void setActiveLoggerPojoClass(final PojoClass activeLoggerPojoClass) {
        ActiveLogger.activeLoggerPojoClass = activeLoggerPojoClass;
        reportActiveLogger();
    }

    private static void reportActiveLogger() {
        ((Logger) getInstance(ActiveLogger.class.getName())).info("Logging subsystem initialized to [{0}]",
                activeLoggerPojoClass.getClazz());
    }
}
