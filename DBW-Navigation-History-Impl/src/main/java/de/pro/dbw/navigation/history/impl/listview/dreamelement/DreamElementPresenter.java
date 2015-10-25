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
package de.pro.dbw.navigation.history.impl.listview.dreamelement;

import de.pro.dbw.core.configuration.api.application.IApplicationConfiguration;
import de.pro.dbw.core.configuration.api.application.util.IUtilConfiguration;
import static de.pro.dbw.core.configuration.api.application.util.IUtilConfiguration.SIGN__EMPTY;
import de.pro.dbw.util.api.IDateConverter;
import de.pro.dbw.util.provider.UtilProvider;
import de.pro.lib.logger.api.LoggerFacade;
import de.pro.lib.properties.api.PropertiesFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author PRo
 */
public class DreamElementPresenter implements Initializable, IApplicationConfiguration,
        IUtilConfiguration
{
    @FXML private Label lDream;
    @FXML private Label lGenerationTime;
    @FXML private Label lPrefix;
    
    private Long idToOpen = null;
    private Long generationTime = null;
    private String date = null;
    private String time = null;
    private String title = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.trace(this.getClass(), "Initialize DreamElementPresenter"); // NOI18N
        
        assert (lDream != null)          : "fx:id=\"lDream\" was not injected: check your FXML file 'DreamElement.fxml'."; // NOI18N
        assert (lGenerationTime != null) : "fx:id=\"lGenerationTime\" was not injected: check your FXML file 'DreamElement.fxml'."; // NOI18N
        assert (lPrefix != null)         : "fx:id=\"lPrefix\" was not injected: check your FXML file 'DreamElement.fxml'."; // NOI18N
    }
    
    public void configure(Long generationTime, String title, Long idToOpen) {
        LoggerFacade.INSTANCE.trace(this.getClass(), "Configure DreamElementPresenter"); // NOI18N
        
        this.generationTime = generationTime;
        this.title = title;
        this.idToOpen = idToOpen;
        
        final Boolean hasPrefixNew = UtilProvider.getDefault().getDateConverter().isAfter(-3, generationTime);
        lPrefix.setVisible(hasPrefixNew);
        lPrefix.setManaged(hasPrefixNew);
        lPrefix.setText(hasPrefixNew ?
                PropertiesFacade.INSTANCE.getProperty(DBW__RESOURCE_BUNDLE, KEY__APPLICATION__PREFIX_NEW)
                : SIGN__EMPTY);
        
        date = UtilProvider.getDefault().getDateConverter().convertLongToDateTime(
                generationTime, IDateConverter.PATTERN__DATE__HISTORY);
//        time = DateConverter.convertLongToDateTime(generationTime, DateConverter.PATTERN__TIME);
        lGenerationTime.setText(UtilProvider.getDefault().getDateConverter().convertLongToDateTime(
                generationTime, IDateConverter.PATTERN__GENERATIONTIME));
        
        lDream.setText(title);
    }
    
    public String getDate() {
        return date;
    }
    
    public Long getGenerationTime() {
        return generationTime;
    }
    
    public Long getIdToOpen() {
        return idToOpen;
    }
    
}
