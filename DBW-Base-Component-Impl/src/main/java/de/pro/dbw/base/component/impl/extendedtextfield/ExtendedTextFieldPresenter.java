/*
 * Copyright (C) 2015 PRo
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
package de.pro.dbw.base.component.impl.extendedtextfield;

import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author PRo
 */
public class ExtendedTextFieldPresenter implements Initializable {

    @FXML private CheckBox checkBox;
    @FXML private Label label;
    @FXML private TextField textField;
    
    private String oldTitle = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize ExtendedTextFieldPresenter"); // NOI18N
    
        assert (checkBox != null)  : "fx:id=\"checkBox\" was not injected: check your FXML file 'ExtendedTextField.fxml'."; // NOI18N
        assert (label != null)     : "fx:id=\"label\" was not injected: check your FXML file 'ExtendedTextField.fxml'."; // NOI18N
        assert (textField != null) : "fx:id=\"textField\" was not injected: check your FXML file 'ExtendedTextField.fxml'."; // NOI18N
    }
    
    public void configure(String title, double minWidthForLabel) {
        label.setText(title);
        label.setMinWidth(minWidthForLabel);
        
        textField.disableProperty().bind(checkBox.selectedProperty().not());
        
        checkBox.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            // CheckBox is active
            if (newValue) {
                if (oldTitle != null) {
                    textField.setText(oldTitle);
                }
                
                return;
            }
            
            // CheckBox isn't active
            if (
                    textField != null
                    && !textField.getText().trim().isEmpty()
            ) {
                oldTitle = textField.getText().trim();
            }
            textField.setText(null);
        });
    }
    
    public CheckBox getCheckBox() {
        return checkBox;
    }
    
    public String getText() {
        return textField.getText().trim();
    }
    
}
