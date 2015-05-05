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
package de.pro.dbw.core.configuration.api.application.desktop;

/**
 *
 * @author PRo
 */
public interface IDesktopConfiguration {
    
    public static final Double DESKTOP__DIVIDER_POSITION__DURATION = 0.75d;
    public static final Integer DESKTOP__DIVIDER_POSITION__INDEX_0 = 0;
    public static final Integer DESKTOP__DIVIDER_POSITION__INDEX_1 = 1;
    
    public static final String DESKTOP__DIVIDER_POSITION__LEFT = "DESKTOP__DIVIDER_POSITION__LEFT"; // NOI18N
    public static final Double DESKTOP__DIVIDER_POSITION__LEFT__DEFAULT_VALUE = 0.25d;
    
    public static final String DESKTOP__DIVIDER_POSITION__RIGHT = "DESKTOP__DIVIDER_POSITION__RIGHT"; // NOI18N
    public static final Double DESKTOP__DIVIDER_POSITION__RIGHT__DEFAULT_VALUE = 0.80d;
    
}
