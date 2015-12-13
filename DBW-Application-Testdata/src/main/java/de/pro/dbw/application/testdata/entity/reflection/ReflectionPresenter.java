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
package de.pro.dbw.application.testdata.entity.reflection;

import de.pro.dbw.application.testdata.entity.EntityHelper;
import de.pro.dbw.core.configuration.api.application.preferences.IPreferencesConfiguration;
import de.pro.lib.logger.api.LoggerFacade;
import de.pro.lib.preferences.api.PreferencesFacade;
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
public class ReflectionPresenter implements Initializable, IPreferencesConfiguration {
    
    @FXML private ComboBox cbQuantityEntities;
    @FXML private ComboBox cbQuantityTimePeriod;
    @FXML private Label lProgressBarInformation;
    @FXML private Label lProgressBarPercentInformation;
    @FXML private ProgressBar pbEntityReflection;

    private ResourceBundle resources = null;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize DreamPresenter"); // NOI18N
        
        this.resources = resources;
        
        assert (cbQuantityEntities != null)             : "fx:id=\"cbQuantityEntities\" was not injected: check your FXML file 'Reflection.fxml'."; // NOI18N
        assert (cbQuantityTimePeriod != null)           : "fx:id=\"cbQuantityTimePeriod\" was not injected: check your FXML file 'Reflection.fxml'."; // NOI18N
        assert (lProgressBarInformation != null)        : "fx:id=\"lProgressBarInformation\" was not injected: check your FXML file 'Reflection.fxml'."; // NOI18N
        assert (lProgressBarPercentInformation != null) : "fx:id=\"lProgressBarPercentInformation\" was not injected: check your FXML file 'Reflection.fxml'."; // NOI18N
        assert (pbEntityReflection != null)             : "fx:id=\"pbEntityReflection\" was not injected: check your FXML file 'Reflection.fxml'."; // NOI18N
    
        this.initializeComboBoxes();
    }

    private void initializeComboBoxes() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize ComboBoxes"); // NOI18N
        
        cbQuantityEntities.getItems().addAll(EntityHelper.getDefault().getQuantityEntities());
        cbQuantityEntities.setCellFactory(new Callback<ListView<Integer>, ListCell<Integer>>() {

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
        
        final Integer quantityEntities = PreferencesFacade.INSTANCE.getInt(
                PREF__TESTDATA__QUANTITY_ENTITIES__REFLECTION,
                PREF__TESTDATA__QUANTITY_ENTITIES__REFLECTION__DEFAULT_VALUE);
        cbQuantityEntities.getSelectionModel().select(quantityEntities);
        
        cbQuantityTimePeriod.getItems().addAll(EntityHelper.getDefault().getQuantityTimePeriods());
        cbQuantityTimePeriod.setCellFactory(new Callback<ListView<Integer>, ListCell<Integer>>() {

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
        
        final Integer quantityTimePeriod = PreferencesFacade.INSTANCE.getInt(
                PREF__TESTDATA__QUANTITY_TIMEPERIOD__REFLECTION,
                PREF__TESTDATA__QUANTITY_TIMEPERIOD__REFLECTION__DEFAULT_VALUE);
        cbQuantityTimePeriod.getSelectionModel().select(quantityTimePeriod);
    }

    public void bind(BooleanProperty disableProperty) {
        cbQuantityEntities.disableProperty().unbind();
        cbQuantityEntities.disableProperty().bind(disableProperty);
        
        cbQuantityTimePeriod.disableProperty().unbind();
        cbQuantityTimePeriod.disableProperty().bind(disableProperty);
    }
    
    public Label getProgressBarPercentInformation() {
        return lProgressBarPercentInformation;
    }

    public int getSaveMaxEntities() {
        Integer saveMaxEntitites = (Integer) cbQuantityEntities.getSelectionModel().getSelectedItem();
        if (saveMaxEntitites == null) {
            saveMaxEntitites = 0;
        }
        
        return saveMaxEntitites;
    }

    public int getTimePeriod() {
        Integer timePeriod = (Integer) cbQuantityTimePeriod.getSelectionModel().getSelectedItem();
        if (timePeriod == null) {
            timePeriod = 0;
        }
        
        return timePeriod;
    }
    
    public void onActionQuantityEntities() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Quantity Entities"); // NOI18N
        
        final Integer quantityEntities = (Integer) cbQuantityEntities.getSelectionModel().getSelectedItem();
        PreferencesFacade.INSTANCE.putInt(PREF__TESTDATA__QUANTITY_ENTITIES__REFLECTION, quantityEntities);
    }
    
    public void onActionQuantityTimePeriod() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Quantity TimePeriod"); // NOI18N
        
        final Integer quantityTimePeriod = (Integer) cbQuantityTimePeriod.getSelectionModel().getSelectedItem();
        PreferencesFacade.INSTANCE.putInt(PREF__TESTDATA__QUANTITY_TIMEPERIOD__REFLECTION, quantityTimePeriod);
    }
    
    public DoubleProperty progressPropertyFromEntityReflection() {
        return pbEntityReflection.progressProperty();
    }
    
    public void setProgressBarInformation(String message) {
        lProgressBarInformation.setText(message);
    }
    
}
