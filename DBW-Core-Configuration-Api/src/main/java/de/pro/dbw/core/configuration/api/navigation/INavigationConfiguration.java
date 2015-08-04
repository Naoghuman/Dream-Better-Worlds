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
package de.pro.dbw.core.configuration.api.navigation;

import javafx.util.Duration;

/**
 *
 * @author PRo
 */
public interface INavigationConfiguration {
    
    public static final Double NAVIGATION__PREVIOUS_TAB_SELECTION__DURATION = 100d;
    
    public static final String NAVIGATION__PREVIOUS_TAB_SELECTION__LEFT = "NAVIGATION__PREVIOUS_TAB_SELECTION__LEFT"; // NOI18N
    public static final Integer NAVIGATION__PREVIOUS_TAB_SELECTION__LEFT__DEFAULT_VALUE = 0;
    
    public static final String NAVIGATION__PREVIOUS_TAB_SELECTION__RIGHT = "NAVIGATION__PREVIOUS_TAB_SELECTION__RIGHT"; // NOI18N
    public static final Integer NAVIGATION__PREVIOUS_TAB_SELECTION__RIGHT__DEFAULT_VALUE = 0;
    
    public static final Duration REFRESH_AFTER_100_MILLIS = Duration.millis(100.0d);
    
    public static final String KEY__NAVIGATION_TAB__DREAMBOOK = "key.navigation.tab.dreambook"; // NOI18N
    public static final String KEY__NAVIGATION_TAB__HISTORY = "key.navigation.tab.history"; // NOI18N
    public static final String KEY__NAVIGATION_TAB__SEARCH = "key.navigation.tab.search"; // NOI18N
    public static final String KEY__NAVIGATION_TAB__VOTING = "key.navigation.tab.voting"; // NOI18N
    
    public static final String NAVIGATION__RESOURCE_BUNDLE = "/de/pro/dbw/navigation/provider/NavigationProvider.properties"; // NOI18N
    
}
