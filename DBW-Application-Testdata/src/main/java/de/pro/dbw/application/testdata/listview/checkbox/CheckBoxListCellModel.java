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
package de.pro.dbw.application.testdata.listview.checkbox;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author PRo
 */
public class CheckBoxListCellModel {
    
    private final BooleanProperty selectedProperty = new SimpleBooleanProperty();
    private final StringProperty name = new SimpleStringProperty();
    
    private String id = null;
    
    public final String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public final boolean isSelected() {
        return this.selectedProperty().get();
    }
    
    public final BooleanProperty selectedProperty() {
        return this.selectedProperty;
    }

    public final void setSelected(final boolean selected) {
        this.selectedProperty().set(selected);
    }
    
    public final StringProperty nameProperty() {
        return this.name;
    }

    public final String getName() {
        return nameProperty().getValue();
    }

    public final void setName(final String name) {
        nameProperty().setValue(name);
    }

    @Override
    public String toString() {
        return getName();
    }
    
}
