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
package de.pro.dbw.base.component.impl.listcell.checkbox;

import de.pro.dbw.base.component.api.listcell.checkbox.CheckBoxListCellModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 *
 * @author PRo
 */
public class CheckBoxListCell extends ListCell<CheckBoxListCellModel> {
    
    private final ObjectProperty<Callback<CheckBoxListCellModel, ObservableValue<Boolean>>> disableStateCallbackProperty 
            = new SimpleObjectProperty<>(this, "disableStateCallbackProperty"); // NOI18N
    private final ObjectProperty<Callback<CheckBoxListCellModel, ObservableValue<Boolean>>> selectedStateCallbackProperty 
            = new SimpleObjectProperty<>(this, "selectedStateCallbackProperty"); // NOI18N
    
    public static Callback<ListView<CheckBoxListCellModel>, ListCell<CheckBoxListCellModel>> create(
            final Callback<CheckBoxListCellModel, ObservableValue<Boolean>> selectedProperty,
            final Callback<CheckBoxListCellModel, ObservableValue<Boolean>> disableProperty
    ) {
        return list -> new CheckBoxListCell(selectedProperty, disableProperty);
    }
    
    private CheckBox checkBox;
    
    private ObservableValue<Boolean> disableBooleanProperty;
    private ObservableValue<Boolean> selectedBooleanProperty;
    
    public CheckBoxListCell() { 
        this(null, null);
    }
    
    public CheckBoxListCell(
            final Callback<CheckBoxListCellModel, ObservableValue<Boolean>> selectedProperty,
            final Callback<CheckBoxListCellModel, ObservableValue<Boolean>> disableProperty
    ) {
        this.initialize(selectedProperty, disableProperty);
    }
    
    private void initialize(
            final Callback<CheckBoxListCellModel, ObservableValue<Boolean>> selectedProperty,
            final Callback<CheckBoxListCellModel, ObservableValue<Boolean>> disableProperty
    ) {
        super.setAlignment(Pos.CENTER_LEFT);
        super.setContentDisplay(ContentDisplay.LEFT);
        super.setGraphic(null);
        
        // TODO get style-sheet from modena.css (javafx8) and add it to own css
//        this.getStyleClass().add("check-box-list-cell");
        this.checkBox = new CheckBox();
        
        disableStateCallbackProperty.setValue(disableProperty);
        selectedStateCallbackProperty.setValue(selectedProperty);
    }
    
    @Override
    public void updateItem(CheckBoxListCellModel model, boolean empty) {
        super.updateItem(model, empty);
        
        if (model == null || empty) {
            setGraphic(null);
            checkBox.setGraphic(null);
            return;
        }
        
        // Configure graphic
        checkBox.setGraphic(model.getCheckBoxGraphic());
        setGraphic(checkBox);

        // and bind it
        this.bindSelectedProperty(model);
        this.bindDisableProperty(model);
    }
    
    private void bindDisableProperty(CheckBoxListCellModel model) {
        if (disableStateCallbackProperty.getValue() == null) {
            throw new NullPointerException("Parameter disableStateCallbackProperty.getValue() can't be NULL");
        }
        
        if (disableBooleanProperty != null) {
            checkBox.disableProperty().unbindBidirectional((BooleanProperty)disableBooleanProperty);
        }
        
        disableBooleanProperty = disableStateCallbackProperty.getValue().call(model);
        if (disableBooleanProperty != null) {
            checkBox.disableProperty().bindBidirectional((BooleanProperty)disableBooleanProperty);
        }
    }
    
    private void bindSelectedProperty(CheckBoxListCellModel model) {
        if (selectedStateCallbackProperty.getValue() == null) {
            throw new NullPointerException("Parameter selectedStateCallbackProperty.getValue() can't be NULL");
        }
        
        if (selectedBooleanProperty != null) {
            checkBox.selectedProperty().unbindBidirectional((BooleanProperty)selectedBooleanProperty);
        }
        
        selectedBooleanProperty = selectedStateCallbackProperty.getValue().call(model);
        if (selectedBooleanProperty != null) {
            checkBox.selectedProperty().bindBidirectional((BooleanProperty)selectedBooleanProperty);
        }
    }
    
}
