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
package de.pro.dbw.core.configuration.api.application.preferences;

/**
 *
 * @author PRo
 */
public interface IPreferencesConfiguration {
    
    public static final String PREF__DBW_WIDTH = "PREF__DBW_WIDTH"; // NOI18N
    public static final Double PREF__DBW_WIDTH__DEFAULT_VALUE = 1280.0d;
    
    public static final String PREF__DBW_HEIGHT = "PREF__DBW_HEIGHT"; // NOI18N
    public static final Double PREF__DBW_HEIGHT__DEFAULT_VALUE = 720.0d;
    
    public static final String PREF__DBW_POSITION_X = "PREF__DBW_POSITION_X"; // NOI18N
    public static final Double PREF__DBW_POSITION_X__DEFAULT_VALUE = 100.0d;
    
    public static final String PREF__DBW_POSITION_Y = "PREF__DBW_POSITION_Y"; // NOI18N
    public static final Double PREF__DBW_POSITION_Y__DEFAULT_VALUE = 100.0d;
    
    public static final String PREF__PERF__INTERVAL__TIP_OF_THE_NIGHT_CHOOSER_NEXT = "PREF__PERF__INTERVAL__TIP_OF_THE_NIGHT_CHOOSER_NEXT"; // NOI18N
    public static final Integer PREF__PERF__INTERVAL__TIP_OF_THE_NIGHT_CHOOSER_NEXT__DEFAULT_VALUE = 50;
    public static final String PREF__PERF__REPEAT__TIP_OF_THE_NIGHT_CHOOSER_NEXT = "PREF__PERF__REPEAT__TIP_OF_THE_NIGHT_CHOOSER_NEXT"; // NOI18N
    public static final Integer PREF__PERF__REPEAT__TIP_OF_THE_NIGHT_CHOOSER_NEXT__DEFAULT_VALUE = 100;
    
    public static final String PREF__PERF__INTERVAL__TIP_OF_THE_NIGHT_CHOOSER_RANDOM = "PREF__PERF__INTERVAL__TIP_OF_THE_NIGHT_CHOOSER_RANDOM"; // NOI18N
    public static final Integer PREF__PERF__INTERVAL__TIP_OF_THE_NIGHT_CHOOSER_RANDOM__DEFAULT_VALUE = 50;
    public static final String PREF__PERF__REPEAT__TIP_OF_THE_NIGHT_CHOOSER_RANDOM = "PREF__PERF__REPEAT__TIP_OF_THE_NIGHT_CHOOSER_RANDOM"; // NOI18N
    public static final Integer PREF__PERF__REPEAT__TIP_OF_THE_NIGHT_CHOOSER_RANDOM__DEFAULT_VALUE = 100;
    
    public static final String PREF__SHOW_AT_START__TIP_OF_THE_NIGHT = "PREF__SHOW_AT_START__TIP_OF_THE_NIGHT"; // NOI18N
    public static final Boolean PREF__SHOW_AT_START__TIP_OF_THE_NIGHT__DEFAULT_VALUE = Boolean.TRUE;
    
    public static final String PREF__SHOW_AT_START__WELCOME = "PREF__SHOW_AT_START__WELCOME"; // NOI18N
    public static final Boolean PREF__SHOW_AT_START__WELCOME__DEFAULT_VALUE = Boolean.TRUE;
    
    public static final String PREF__TESTDATA__IS_SELECTED_DELETE_DATABASE = "PREF__TESTDATA__IS_SELECTED_DELETE_DATABASE"; // NOI18N
    public static final Boolean PREF__TESTDATA__IS_SELECTED_DELETE_DATABASE__DEFAULT_VALUE = Boolean.FALSE;
    
    public static final String PREF__TESTDATA__QUANTITY_ENTITIES__DREAM = "PREF__TESTDATA__QUANTITY_ENTITIES__DREAM"; // NOI18N
    public static final Integer PREF__TESTDATA__QUANTITY_ENTITIES__DREAM__DEFAULT_VALUE = 100;
    public static final String PREF__TESTDATA__QUANTITY_TIMEPERIOD__DREAM = "PREF__TESTDATA__QUANTITY_TIMEPERIOD__DREAM"; // NOI18N
    public static final Integer PREF__TESTDATA__QUANTITY_TIMEPERIOD__DREAM__DEFAULT_VALUE = 1;
    
    public static final String PREF__TESTDATA__QUANTITY_ENTITIES__REFLECTION = "PREF__TESTDATA__QUANTITY_ENTITIES__REFLECTION"; // NOI18N
    public static final Integer PREF__TESTDATA__QUANTITY_ENTITIES__REFLECTION__DEFAULT_VALUE = 100;
    public static final String PREF__TESTDATA__QUANTITY_TIMEPERIOD__REFLECTION = "PREF__TESTDATA__QUANTITY_TIMEPERIOD__REFLECTION"; // NOI18N
    public static final Integer PREF__TESTDATA__QUANTITY_TIMEPERIOD__REFLECTION__DEFAULT_VALUE = 1;
    
    public static final String PREF__TESTDATA__QUANTITY_ENTITIES__TIP_OF_THE_NIGHT = "PREF__TESTDATA__QUANTITY_ENTITIES__TIP_OF_THE_NIGHT"; // NOI18N
    public static final Integer PREF__TESTDATA__QUANTITY_ENTITIES__TIP_OF_THE_NIGHT__DEFAULT_VALUE = 100;
    public static final String PREF__TESTDATA__QUANTITY_TIMEPERIOD__TIP_OF_THE_NIGHT = "PREF__TESTDATA__QUANTITY_TIMEPERIOD__TIP_OF_THE_NIGHT"; // NOI18N
    public static final Integer PREF__TESTDATA__QUANTITY_TIMEPERIOD__TIP_OF_THE_NIGHT__DEFAULT_VALUE = 1;
    
    public static final String PREF__TIP_OF_THE_NIGHT_INDEX = "PREF__TIP_OF_THE_NIGHT_INDEX"; // NOI18N
    public static final Integer PREF__TIP_OF_THE_NIGHT_INDEX__DEFAULT_VALUE = 0;
    
}
