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
package de.pro.dbw.navigation.history.impl.listview.parentelement;

import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author PRo
 */
public class ParentElementPresenter implements Initializable {
    
    @FXML private Label lDay = null;
    
    private int size = 0;
    private Long generationTime = -1L;
    private String date = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().debug(this.getClass(), "Initialize ParentElementPresenter");
        System.out.println(" XXX ParentElementPresenter -> log with trace(...)");
        
        assert (lDay != null) : "fx:id=\"lDay\" was not injected: check your FXML file 'History.fxml'."; // NOI18N
        
    }

    public void configure(Long generationTime, String date) {
        LoggerFacade.getDefault().debug(this.getClass(), "Configure ParentElementPresenter"); // NOI18N
        System.out.println(" XXX ParentElementPresenter -> log with trace(...)");
        
        this.generationTime = generationTime;
        this.date = date;
    }
    
    public String getDate() {
        return date;
    }
    
    public Long getGenerationTime() {
        return generationTime;
    }
    
    public void setSize(int size) {
        this.size = size;
        
        lDay.setText(date + " (" + size + ")"); // NOI18N
    }
    
}
