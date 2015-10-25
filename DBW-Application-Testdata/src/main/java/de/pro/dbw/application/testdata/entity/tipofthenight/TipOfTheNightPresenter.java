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
package de.pro.dbw.application.testdata.entity.tipofthenight;

import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 *
 * @author PRo
 */
public class TipOfTheNightPresenter implements Initializable {
    
//    private static final String PROPERTY__TIP_OF_THE_NIGHT = ".tipofthenight"; // NOI18N
    
    @FXML private ComboBox cbTipOfTheNight;
    @FXML private Label lProgressTipOfTheNight;
    @FXML private Label lProgressInformationTipOfTheNight;
    @FXML private ProgressBar pbTipOfTheNight;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize TipOfTheNightPresenter");
        
        assert (cbTipOfTheNight != null)        : "fx:id=\"cbTipOfTheNight\" was not injected: check your FXML file 'TipOfTheNight.fxml'."; // NOI18N
        assert (lProgressTipOfTheNight != null) : "fx:id=\"lProgressTipOfTheNight\" was not injected: check your FXML file 'TipOfTheNight.fxml'."; // NOI18N
        assert (lProgressInformationTipOfTheNight != null) : "fx:id=\"lProgressInformationTipOfTheNight\" was not injected: check your FXML file 'TipOfTheNight.fxml'."; // NOI18N
        assert (pbTipOfTheNight != null)        : "fx:id=\"pbTipOfTheNight\" was not injected: check your FXML file 'TipOfTheNight.fxml'."; // NOI18N
    
        this.initializeComboBox();
    }

    private void initializeComboBox() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize ComboBox");
        
    }
    
}
