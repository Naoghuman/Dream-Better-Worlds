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
package de.pro.dbw.navigation.search.impl.searchindreamsresult;

import de.pro.dbw.core.configuration.api.application.action.IActionConfiguration;
import de.pro.dbw.navigation.search.api.SearchDataModel;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 *
 * @author PRo
 */
public class SearchInDreamsResultPresenter implements Initializable, IActionConfiguration {
    
    @FXML private Button bSearchInDreams;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.getLogger().info(this.getClass(), "Initialize SearchInDreamsResultPresenter"); // NOI18N
        
//        assert (spSearchComponents != null) : "fx:id=\"spSearchComponents\" was not injected: check your FXML file 'SearchInDreamsResult.fxml'."; // NOI18N
        
//        this.initializeScrollPane();
    }

    public void configure(SearchDataModel searchDataModel) {
        LoggerFacade.INSTANCE.getLogger().debug(this.getClass(), "Configure SearchDataModel"); // NOI18N
        
    }
    

    
}
