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
package de.pro.dbw.navigation.history.impl.listview.childelement;

import de.pro.dbw.util.api.IDateConverter;
import de.pro.dbw.util.provider.UtilProvider;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author PRo
 */
public class ChildElementPresenter implements Initializable {
    
    @FXML private BorderPane bpContent;
    
    private Long generationTime;
    private Long idToOpen;
    private String actionKey;
    private String title;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.trace(this.getClass(), "Initialize ChildElementPresenter"); // NOI18N
        
        assert (bpContent != null) : "fx:id=\"bpContent\" was not injected: check your FXML file 'ChildElement.fxml'."; // NOI18N
    }
    
    public void configure(Parent content, String title, Long generationTime, Long idToOpen, String actionKey) {
        if (
                content == null
                || generationTime == null
                || idToOpen == null
                || title == null
                || actionKey == null
        ) {
            throw new IllegalArgumentException("None of the parameter can be NULL."); // NOI18N
        }
        
        this.title = title;
        this.generationTime = generationTime;
        this.idToOpen = idToOpen;
        this.actionKey = actionKey;
        
        bpContent.setCenter(content);
    }
    
    public String getActionKey() {
        return actionKey;
    }
    
    public String getDate() {
        return UtilProvider.getDefault().getDateConverter().convertLongToDateTime(
                generationTime, IDateConverter.PATTERN__DATE__HISTORY);
    }
    
    public Long getGenerationTime() {
        return generationTime;
    }
    
    public Long getIdToOpen() {
        return idToOpen;
    }
    
    public String getTitle() {
        return title;
    }
    
}
