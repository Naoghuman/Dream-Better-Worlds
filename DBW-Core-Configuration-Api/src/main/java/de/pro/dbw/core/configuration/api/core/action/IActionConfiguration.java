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
package de.pro.dbw.core.configuration.api.core.action;

/**
 *
 * @author PRo
 */
public interface IActionConfiguration {
    
    public static final String ACTION__CREATE_NEW_DREAM = "ACTION__CREATE_NEW_DREAM"; // NOI18N
    public static final String ACTION__CREATE_NEW_FAST_DREAM = "ACTION__CREATE_NEW_FAST_DREAM"; // NOI18N
    public static final String ACTION__CREATE_NEW_FILE__REFLECTION = "ACTION__CREATE_NEW_FILE__REFLECTION"; // NOI18N
    public static final String ACTION__EDIT_FILE__REFLECTION = "ACTION__EDIT_FILE__REFLECTION"; // NOI18N
    
    public static final String ACTION__DELETE_ = "ACTION__DELETE_"; // NOI18N
    
    public static final String ACTION__JOB_CHECK_NAVIGATION__DREAMBOOK = "ACTION__JOB_CHECK_NAVIGATION__DREAMBOOK";  // NOI18N
    public static final String ACTION__JOB_CHECK_NAVIGATION__HISTORY = "ACTION__JOB_CHECK_NAVIGATION__HISTORY";  // NOI18N
    
    public static final String ACTION__OPEN_DREAM__FROM_NAVIGATION = "ACTION__OPEN_DREAM__FROM_NAVIGATION"; // NOI18N
    public static final String ACTION__OPEN_DREAMMAP__FROM_WIZARD = "ACTION__OPEN_DREAMMAP__FROM_WIZARD"; // NOI18N
    public static final String ACTION__OPEN_DREAMMAP__FROM_NAVIGATION = "ACTION__OPEN_DREAMMAP__FROM_NAVIGATION"; // NOI18N
    public static final String ACTION__OPEN_REFLECTION__FROM_NAVIGATION = "ACTION__OPEN_REFLECTION__FROM_NAVIGATION"; // NOI18N
    public static final String ACTION__OPEN_VOTING__FROM_NAVIGATION = "ACTION__OPEN_VOTING__FROM_NAVIGATION"; // NOI18N
    
    public static final String ACTION__REFRESH_NAVIGATION__DREAMBOOK = "ACTION__REFRESH_NAVIGATION__DREAMBOOK"; // NOI18N
    public static final String ACTION__REFRESH_NAVIGATION__DREAMMAP = "ACTION__REFRESH_NAVIGATION__DREAMMAP"; // NOI18N
    public static final String ACTION__REFRESH_NAVIGATION__HISTORY = "ACTION__REFRESH_NAVIGATION__HISTORY"; // NOI18N
    public static final String ACTION__REFRESH_NAVIGATION__SEARCH = "ACTION__REFRESH_NAVIGATION__SEARCH"; // NOI18N
    public static final String ACTION__REFRESH_NAVIGATION__TAG = "ACTION__REFRESH_NAVIGATION__TAG"; // NOI18N
    public static final String ACTION__REFRESH_NAVIGATION__VOTING = "ACTION__REFRESH_NAVIGATION__VOTING"; // NOI18N
    public static final String ACTION__REFRESH_TIP_OF_THE_NIGHT__EDITOR = "ACTION__REFRESH_TIP_OF_THE_NIGHT__EDITOR"; // NOI18N
    
    public static final String ACTION__REMOVE_FILE_FROM_EDITOR = "ACTION__REMOVE_FILE_FROM_EDITOR"; // NOI18N
    
//    public static final String ACTION__SAVE_ = "ACTION__SAVE_"; // NOI18N
    public static final String ACTION__SAVE_ALL_CHANGED_FILES = "ACTION__SAVE_ALL_CHANGED_FILES"; // NOI18N
//    public static final String ACTION__DONT_SAVE_ = "ACTION__DONT_SAVE_"; // NOI18N
    
    public static final String ACTION__SEARCH_IN__DREAMS = "ACTION__SEARCH_IN__DREAMS"; // NOI18N
    public static final String ACTION__SEARCH_IN__REFLECTIONS = "ACTION__SEARCH_IN__REFLECTIONS"; // NOI18N
    public static final String ACTION__SEARCH_IN__TIPS_OF_THE_NIGHT = "ACTION__SEARCH_IN__TIPS_OF_THE_NIGHT"; // NOI18N
    
    public static final String ACTION__SHOW_EXTENDED_SLIDER_DIALOG = "ACTION__SHOW_EXTENDED_SLIDER_DIALOG_"; // NOI18N
    public static final String ACTION__SHOW_HELP = "ACTION__SHOW_HELP"; // NOI18N
    public static final String ACTION__SHOW_HELP__ABOUT = "ACTION__SHOW_HELP__ABOUT"; // NOI18N
    public static final String ACTION__SHOW_HELP__WELCOME = "ACTION__SHOW_HELP__WELCOME"; // NOI18N
    public static final String ACTION__SHOW_SEARCH_IN_DREAMS = "ACTION__SHOW_SEARCH_IN_DREAMS"; // NOI18N
    public static final String ACTION__SHOW_TIP_OF_THE_NIGHT__EDITOR = "ACTION__SHOW_TIP_OF_THE_NIGHT__EDITOR"; // NOI18N
    public static final String ACTION__SHOW_TIP_OF_THE_NIGHT__WINDOW = "ACTION__SHOW_TIP_OF_THE_NIGHT__WINDOW"; // NOI18N
    
}
