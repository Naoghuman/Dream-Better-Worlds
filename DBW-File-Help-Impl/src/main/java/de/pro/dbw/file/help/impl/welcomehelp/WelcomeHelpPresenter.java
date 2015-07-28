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
package de.pro.dbw.file.help.impl.welcomehelp;

import de.pro.dbw.core.configuration.api.application.preferences.IPreferencesConfiguration;
import de.pro.lib.logger.api.LoggerFacade;
import de.pro.lib.preferences.api.PreferencesFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;

/**
 *
 * @author PRo
 */
public class WelcomeHelpPresenter implements Initializable, IPreferencesConfiguration {
    
    @FXML private CheckBox cbShowAtStart;
//    @FXML private TextArea taWhatToShow = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.getLogger().info(this.getClass(), "Initialize WelcomeHelpFilePresenter"); // NOI18N
        
        assert (cbShowAtStart != null) : "fx:id=\"cbShowAtStart\" was not injected: check your FXML file 'WelcomeFile.fxml'."; // NOI18N
//        assert (taWhatToShow != null)  : "fx:id=\"taWhatToShow\" was not injected: check your FXML file 'WelcomeFile.fxml'."; // NOI18N
    
        this.initializeShowAtStart();
    }
    
    private void initializeShowAtStart() {
        final Boolean selected = PreferencesFacade.INSTANCE.getPreferences().getBoolean(
                PREF__SHOW_AT_START__WELCOME,
                PREF__SHOW_AT_START__WELCOME__DEFAULT_VALUE);
        cbShowAtStart.setSelected(selected);
    }
    
    public void showAtStart() {
        LoggerFacade.INSTANCE.getLogger().debug(this.getClass(), "Show at start: " + cbShowAtStart.isSelected());
    
        PreferencesFacade.INSTANCE.getPreferences().putBoolean(
                PREF__SHOW_AT_START__WELCOME, cbShowAtStart.isSelected());
    }
}
