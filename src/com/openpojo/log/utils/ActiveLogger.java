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
package com.openpojo.log.utils;

import com.openpojo.log.Logger;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.facade.FacadeFactory;

/**
 * This ActiveLogger class is a container that holds the active logging underlying framework for
 * the current instance of the VM.
 * The reason this is in a seperate class is to enable lazy loading, the resolution of the active logging mechanism
 * won't happen until the first call to get the logging framework.
 *
 * @author oshoukry
 */
public class ActiveLogger {
    public static final String[] SUPPORTED_LOGGERS = new String[]{ "com.openpojo.log.impl.SLF4JLogger",
            "com.openpojo.log.impl.Log4JLogger", "com.openpojo.log.impl.JavaLogger" };

    public final static PojoClass activeLoggerPojoClass = FacadeFactory.getLoadedFacadePojoClass(SUPPORTED_LOGGERS);

    static {
        ((Logger) activeLoggerPojoClass.newInstance(ActiveLogger.class.getName())).info(
                "Logging subsystem initialized to utilized to [{0}]", activeLoggerPojoClass.getClazz());
    }

}
