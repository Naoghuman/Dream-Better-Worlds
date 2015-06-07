/*
 * Copyright (C) 2015 PRo
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
package de.pro.dbw.core.configuration.api.application;

import javafx.util.Duration;

/**
 *
 * @author PRo
 */
public interface IApplicationConfiguration {
    
    public static final Character DBW__BORDER_SIGN = '#'; // NOI18N
    
    public static final Duration DBW__LITTLE_DELAY__DURATION_125 = Duration.millis(125.0d);
    public static final Duration DBW__LITTLE_DELAY__DURATION_250 = Duration.millis(250.0d);
    
    public static final String DBW__CSS = "DreamBetterWorlds.css"; // NOI18N
    public static final String DBW__DATABASE_NAME = "dbw"; // NOI18N
    public static final String DBW__GOODBYE_MSG = "  Goodbye from 'Dream Better Worlds'. Happy real life :)"; // NOI18N
    // XXX load version from pom.xml
    public static final String DBW__TITLE__APPLICATION = "Dream Better Worlds v0.1.1"; // NOI18N -SNAPSHOT
    public static final String DBW__TITLE__TESTDATA = "Testdata v0.1.1"; // NOI18N -SNAPSHOT
    public static final String DBW__WELCOME_MSG = "  Welcome to 'Dream Better Worlds'. Clearly lucid dreaming :)"; // NOI18N
    
}
