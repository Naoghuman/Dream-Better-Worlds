/*
 * Copyright (C) 2014 Dream Better Worlds
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
package de.pro.dbw.navigation.search.impl.searchnavigation;

import de.pro.dbw.core.configuration.api.application.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.file.dream.IDreamConfiguration;
import de.pro.dbw.navigation.search.impl.searchindreams.SearchInDreamsView;
import de.pro.dbw.navigation.search.impl.searchintipsofthenight.SearchInTipsOfTheNightView;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;

/**
 *
 * @author PRo
 */
public class SearchNavigationPresenter implements Initializable, IActionConfiguration, IDreamConfiguration {
    
    @FXML private Tab tSearchInDreams;
    @FXML private Tab tSearchInTipsOfTheNight;
    
    private SearchInDreamsView searchInDreamsView = null;
    private SearchInTipsOfTheNightView searchInTipsOfTheNightView = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize SearchNavigationPresenter"); // NOI18N
        
        assert (tSearchInDreams != null)           : "fx:id=\"tSearchInDreams\" was not injected: check your FXML file 'SearchNavigation'."; // NOI18N
        assert (tSearchInTipsOfTheNight != null)   : "fx:id=\"tSearchInTipsOfTheNight\" was not injected: check your FXML file 'SearchNavigation.fxml'."; // NOI18N
        
        this.initializeViews();
    }
    
    private void initializeViews() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize views"); // NOI18N
        
        searchInDreamsView = new SearchInDreamsView();
        tSearchInDreams.setContent(searchInDreamsView.getView());
        
        searchInTipsOfTheNightView = new SearchInTipsOfTheNightView();
        tSearchInTipsOfTheNight.setContent(searchInTipsOfTheNightView.getView());
    }
    
}
