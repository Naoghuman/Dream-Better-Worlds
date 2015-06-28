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
package de.pro.dbw.base.component.api.listcell.checkbox;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;

/**
 *
 * @author PRo
 */
public class CheckBoxListCellModel {
    
    private final BooleanProperty selectedProperty = new SimpleBooleanProperty();
    private final BooleanProperty disableProperty = new SimpleBooleanProperty();
    
    private Node checkBoxGraphic;
    private Object model;
    
    public final Node getCheckBoxGraphic() {
        return checkBoxGraphic;
    }
    
    public final Object getModel() {
        return model;
    }
    
    public final BooleanProperty disableProperty() {
        return disableProperty;
    }
    
    public final boolean isDisable() {
        return this.disableProperty().get();
    }

    public final boolean isSelected() {
        return this.selectedProperty().get();
    }
    
    public final BooleanProperty selectedProperty() {
        return this.selectedProperty;
    }
    
    public final void setCheckBoxGraphic(Node checkBoxGraphic) {
        this.checkBoxGraphic = checkBoxGraphic;
    }
    
    public final void setModel(Object model) {
        this.model = model;
    }
    
    public final void setDisable(boolean disable) {
        this.disableProperty().set(disable);
    }

    public final void setSelected(final boolean selected) {
        this.selectedProperty().set(selected);
    }
    
}
