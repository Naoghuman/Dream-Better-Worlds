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
package de.pro.dbw.application.testdata.component.extendedcombobox;

import de.pro.dbw.application.testdata.api.ETestdataSize;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;

/**
 *
 * @author PRo
 */
public class ExtendedComboBoxPresenter implements Initializable {
    
    @FXML private ComboBox cbTestdataSize;
    @FXML private TextField tfTestdataSize;
    
    private ResourceBundle resources = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize ExtendedComboBoxPresenter"); // NOI18N
        
        this.resources = resources;
        
        assert (cbTestdataSize != null) : "fx:id=\"cbTestdataSize\" was not injected: check your FXML file 'ExtendedComboBox.fxml'."; // NOI18N
        assert (tfTestdataSize != null) : "fx:id=\"tfTestdataSize\" was not injected: check your FXML file 'ExtendedComboBox.fxml'."; // NOI18N
    
        this.initializeComboBox();
        
        this.onActionRefresh();
    }

    private void initializeComboBox() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize ComboBox");
        
        cbTestdataSize.getItems().clear();
        cbTestdataSize.setButtonCell(new ListCell<ETestdataSize>() {
                @Override
                protected void updateItem(ETestdataSize model, boolean empty) {
                    super.updateItem(model, empty);

                    super.setText((model == null || empty) ? null : model.getText());
                }
            });
        
        cbTestdataSize.setCellFactory((values) -> {
            return new ListCell<ETestdataSize>() {
                @Override
                protected void updateItem(ETestdataSize model, boolean empty) {
                    super.updateItem(model, empty);

                    super.setText((model == null || empty) ? null : model.getText());
                }
            };
        });
    }
    
    public void configure(final String suffixForSizeKey) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Configure ExtendedComboBoxPresenter");
        
        cbTestdataSize.valueProperty().addListener(new ChangeListener<ETestdataSize>() {

            @Override
            public void changed(ObservableValue observable, ETestdataSize oldValue, ETestdataSize newValue) {
                tfTestdataSize.setText(resources.getString(newValue.getSizeKey(suffixForSizeKey)));
            }
        });
    }
    
    private void onActionRefresh() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Refresh");
        
        cbTestdataSize.getItems().addAll(Arrays.asList(ETestdataSize.values()));
    }

}
