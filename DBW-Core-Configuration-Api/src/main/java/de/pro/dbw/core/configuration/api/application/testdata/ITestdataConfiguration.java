/*
 * Copyright (C) 2015 Dream Better Worlds
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.pro.dbw.core.configuration.api.application.testdata;

import javafx.util.Duration;

/**
 *
 * @author PRo
 */
public interface ITestdataConfiguration {
    
    public static final Duration DBW__LITTLE_DELAY__DURATION_125 = Duration.millis(125.0d);
    
    public static final String DBW__RESOURCE_BUNDLE__TESTDATA = "/de/pro/dbw/application/testdata/TestdataApplication.properties"; // NOI18N
    
    public static final String KEY__APPLICATION__DATABASE = "application.database"; // NOI18N
    public static final String KEY__APPLICATION_TESTDATA__BORDER_SIGN = "application.testdata.border.sign"; // NOI18N
    public static final String KEY__APPLICATION_TESTDATA__MESSAGE_START = "application.testdata.message.start"; // NOI18N
    public static final String KEY__APPLICATION_TESTDATA__MESSAGE_STOP = "application.testdata.message.stop"; // NOI18N
    public static final String KEY__APPLICATION_TESTDATA__TITLE = "application.testdata.title"; // NOI18N
   
}
