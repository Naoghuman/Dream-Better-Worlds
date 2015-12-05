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
package de.pro.dbw.core.configuration.api.application.performance;

import javafx.util.Duration;

/**
 *
 * @author PRo
 */
public interface IPerformanceConfiguration {
    
    public static final Duration DBW__LITTLE_DELAY__DURATION_125 = Duration.millis(125.0d);
    
    public static final String DBW__RESOURCE_BUNDLE__PERFORMANCE = "/de/pro/dbw/application/performance/PerformanceApplication.properties"; // NOI18N
    
    public static final String KEY__APPLICATION__DATABASE = "application.database"; // NOI18N
    public static final String KEY__APPLICATION_PERFORMANCE__BORDER_SIGN = "application.performance.border.sign"; // NOI18N
    public static final String KEY__APPLICATION_PERFORMANCE__MESSAGE_START = "application.performance.message.start"; // NOI18N
    public static final String KEY__APPLICATION_PERFORMANCE__MESSAGE_STOP = "application.performance.message.stop"; // NOI18N
    public static final String KEY__APPLICATION_PERFORMANCE__TITLE = "application.performance.title"; // NOI18N
   
}
