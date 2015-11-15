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

import de.pro.dbw.application.testdata.entity.EntityHelper;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.util.Callback;

/**
 *
 * @author PRo
 */
public class TipOfTheNightPresenter implements Initializable {
    
    @FXML private ComboBox cbEnityTipOfTheNight;
    @FXML private Label lProgressBarInformation;
    @FXML private Label lProgressBarPercentInformation;
    @FXML private ProgressBar pbEnityTipOfTheNight;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize TipOfTheNightPresenter"); // NOI18N
        
        assert (cbEnityTipOfTheNight != null)           : "fx:id=\"cbEnityTipOfTheNight\" was not injected: check your FXML file 'TipOfTheNight.fxml'."; // NOI18N
        assert (lProgressBarInformation != null)        : "fx:id=\"lProgressBarInformation\" was not injected: check your FXML file 'TipOfTheNight.fxml'."; // NOI18N
        assert (lProgressBarPercentInformation != null) : "fx:id=\"lProgressBarPercentInformation\" was not injected: check your FXML file 'TipOfTheNight.fxml'."; // NOI18N
        assert (pbEnityTipOfTheNight != null)           : "fx:id=\"pbEnityTipOfTheNight\" was not injected: check your FXML file 'TipOfTheNight.fxml'."; // NOI18N
    
        this.initializeComboBox();
    }

    private void initializeComboBox() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize ComboBox"); // NOI18N
        
        cbEnityTipOfTheNight.getItems().addAll(EntityHelper.getDefault().getQuantityEntities());
        cbEnityTipOfTheNight.setCellFactory(new Callback<ListView<Integer>, ListCell<Integer>>() {

            @Override
            public ListCell<Integer> call(ListView<Integer> param) {
                return new ListCell<Integer>() {

                        @Override
                        protected void updateItem(Integer item, boolean empty) {
                            super.updateItem(item, empty);
                            
                            if (item == null) {
                                super.setText(null);
                                return;
                            }
                            
                            super.setText("" + item); // NOI18N
                        }
                    };
            }
        });
        
        cbEnityTipOfTheNight.getSelectionModel().selectFirst();
    }

    public void bind(BooleanProperty disableProperty) {
        cbEnityTipOfTheNight.disableProperty().unbind();
        cbEnityTipOfTheNight.disableProperty().bind(disableProperty);
    }
    
    public Label getProgressBarPercentInformation() {
        return lProgressBarPercentInformation;
    }

    public int getSaveMaxEntities() {
        Integer saveMaxEntitites = (Integer) cbEnityTipOfTheNight.getSelectionModel().getSelectedItem();
        if (saveMaxEntitites == null) {
            saveMaxEntitites = 0;
        }
        
        return saveMaxEntitites;
    }
    
    public DoubleProperty progressPropertyFromEntityDream() {
        return pbEnityTipOfTheNight.progressProperty();
    }
    
    public void setProgressBarInformation(String message) {
        lProgressBarInformation.setText(message);
    }
    
}
