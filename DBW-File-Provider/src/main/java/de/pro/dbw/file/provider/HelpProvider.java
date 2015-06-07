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
package de.pro.dbw.file.provider;

import de.pro.dbw.core.configuration.api.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.action.IRegisterActions;
import de.pro.lib.logger.api.LoggerFacade;
import javafx.scene.control.TabPane;

/**
 *
 * @author PRo
 */
public class HelpProvider implements IActionConfiguration, IRegisterActions {
    
    private static HelpProvider instance = null;
    
    public static HelpProvider getDefault() {
        if (instance == null) {
            instance = new HelpProvider();
        }
        
        return instance;
    }
    
    private TabPane tpEditor = null;
    private TabPane tpNavigationRight = null;
    
    private HelpProvider() {
        this.initialize();
    }
    
    private void initialize() {
        
    }

    void checkShowWelcomeHelpAtStart() {
        LoggerFacade.getDefault().info(this.getClass(), "Check if Welcome should show at start"); // NOI18N
    
//        final Boolean isShowAtStart = PreferencesFacade.getDefault().getBoolean(
//                PREF__SHOW_AT_START__TIP_OF_THE_NIGHT,
//                PREF__SHOW_AT_START__TIP_OF_THE_NIGHT__DEFAULT_VALUE);
//        if (!isShowAtStart) {
//            return;
//        }
//        
//        this.onActionShowTipOfTheNightWindow();
    }
    
    public void register(TabPane tpEditor, TabPane tpNavigationRight) {
        LoggerFacade.getDefault().info(this.getClass(), "Register TabPanes tpEditor, tpNavigationRight in HelpProvider"); // NOI18N
        
        this.tpEditor = tpEditor;
        this.tpNavigationRight = tpNavigationRight;
    }

    @Override
    public void registerActions() {
//        this.registerOnActionCreateNewDream();
    }
}