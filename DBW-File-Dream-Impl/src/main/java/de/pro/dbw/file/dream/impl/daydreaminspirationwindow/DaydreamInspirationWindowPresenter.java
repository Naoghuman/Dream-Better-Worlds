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
package de.pro.dbw.file.dream.impl.daydreaminspirationwindow;

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
public class DaydreamInspirationWindowPresenter implements Initializable {
    
    @FXML private Tab tSearch = null;
    @FXML private Tab tTopicFocus = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize DaydreamInspirationWindowPresenter"); // NOI18N
        
        assert (tSearch != null)    : "fx:id=\"tSearch\" was not injected: check your FXML file 'DaydreamInspirationWindow.fxml'."; // NOI18N
        assert (tTopicFocus != null)    : "fx:id=\"tTopicFocus\" was not injected: check your FXML file 'DaydreamInspirationWindow.fxml'."; // NOI18N
    
    }
    
}
