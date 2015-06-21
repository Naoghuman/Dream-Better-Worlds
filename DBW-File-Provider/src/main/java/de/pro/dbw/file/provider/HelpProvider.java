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

import de.pro.dbw.core.configuration.api.application.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.application.action.IRegisterActions;
import de.pro.dbw.core.configuration.api.application.defaultid.IDefaultIdConfiguration;
import de.pro.dbw.core.configuration.api.application.preferences.IPreferencesConfiguration;
import de.pro.dbw.dialog.provider.DialogProvider;
import de.pro.dbw.file.help.impl.aboutdialogcontent.AboutDialogContentPresenter;
import de.pro.dbw.file.help.impl.aboutdialogcontent.AboutDialogContentView;
import de.pro.dbw.file.help.impl.welcomehelp.WelcomeHelpView;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.logger.api.LoggerFacade;
import de.pro.lib.preferences.api.PreferencesFacade;
import javafx.event.ActionEvent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 *
 * @author PRo
 */
public class HelpProvider implements IActionConfiguration, IDefaultIdConfiguration,
        IPreferencesConfiguration, IRegisterActions
{    
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

    void checkShowAtStartWelcomeHelp() {
        LoggerFacade.getDefault().info(this.getClass(), "Check if Welcome should show at start"); // NOI18N
    
        final Boolean isShowAtStart = PreferencesFacade.getDefault().getBoolean(
                PREF__SHOW_AT_START__WELCOME,
                PREF__SHOW_AT_START__WELCOME__DEFAULT_VALUE);
        if (!isShowAtStart) {
            return;
        }
        
        ActionFacade.getDefault().handle(ACTION__SHOW_HELP__WELCOME);
    }
    
    public void register(TabPane tpEditor, TabPane tpNavigationRight) {
        LoggerFacade.getDefault().info(this.getClass(), "Register TabPanes tpEditor, tpNavigationRight in HelpProvider"); // NOI18N
        
        this.tpEditor = tpEditor;
        this.tpNavigationRight = tpNavigationRight;
    }

    @Override
    public void registerActions() {
        LoggerFacade.getDefault().debug(this.getClass(), "Register actions in HelpProvider"); // NOI18N
        
        this.registerOnActionShowHelpAbout();
        this.registerOnActionShowHelpWelcome();
    }

    private void registerOnActionShowHelpAbout() {
        ActionFacade.getDefault().register(
                ACTION__SHOW_HELP__ABOUT,
                (ActionEvent ae) -> {
                    final AboutDialogContentView contentView = new AboutDialogContentView();
                    final AboutDialogContentPresenter contentPresenter = contentView.getRealPresenter();
                    DialogProvider.getDefault().show("About", contentView.getView(), contentPresenter.getSize()); // NOI18N
                });
    }
    
    private void registerOnActionShowHelpWelcome() {
        ActionFacade.getDefault().register(
                ACTION__SHOW_HELP__WELCOME,
                (ActionEvent ae) -> {
                    // Check if the welcome-help is open
                    for (Tab tab : tpEditor.getTabs()) {
                        if (tab.getId().equals(String.valueOf(FILE__WELCOME__DEFAULT_ID))) {
                            tpEditor.getSelectionModel().select(tab);
                            return;
                        }
                    }

                    // Create new welcome-help
                    final Tab tab = new Tab();
                    tab.setText("Welcome"); // XXX properties
                    final WelcomeHelpView view = new WelcomeHelpView();
                    tab.setContent(view.getView());
                    tab.setId(String.valueOf(FILE__WELCOME__DEFAULT_ID));

                    tpEditor.getTabs().add(0, tab);
                    tpEditor.getSelectionModel().select(tab);
                });
    }
    
}
