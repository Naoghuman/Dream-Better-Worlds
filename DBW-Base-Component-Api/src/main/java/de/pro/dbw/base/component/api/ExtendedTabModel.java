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
package de.pro.dbw.base.component.api;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Parent;
import javafx.scene.image.Image;

/**
 *
 * @author PRo
 */
public class ExtendedTabModel {
    
    private Boolean closeable = Boolean.TRUE;
    private Image image = null;
    private Object model = null;
    private Object presenter = null;
    private Parent view = null;
    private String actionKey = null;
    
    private BooleanProperty markAsChangedProperty = null;
    private LongProperty idProperty = null;
    private StringProperty titleProperty = null;
    
    public ExtendedTabModel() {
        this.initialize();
    }

    private void initialize() {
        markAsChangedProperty = new SimpleBooleanProperty(Boolean.FALSE);
        idProperty = new SimpleLongProperty(System.currentTimeMillis());
        titleProperty = new SimpleStringProperty("");
    }

    public Boolean isCloseable() {
        return closeable;
    }

    public void setCloseable(Boolean closeable) {
        this.closeable = closeable;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }
    
    public Object getPresenter() {
        return presenter;
    }
    
    public void setPresenter(Object presenter) {
        this.presenter = presenter;
    }

    public Parent getView() {
        return view;
    }

    public void setView(Parent view) {
        this.view = view;
    }
    
    public Boolean isMarkAsChanged() {
        return markAsChangedProperty.getValue();
    }

    public BooleanProperty markAsChangedProperty() {
        return markAsChangedProperty;
    }

    public void setMarkAsChanged(Boolean isMarkAsChanged) {
        markAsChangedProperty.setValue(isMarkAsChanged);
    }
    
    public Long getId() {
        return idProperty.getValue();
    }

    public LongProperty idProperty() {
        return idProperty;
    }

    public void setId(Long id) {
        idProperty.setValue(id);
    }
    
    public String getTitle() {
        return titleProperty.getValue();
    }

    public StringProperty titleProperty() {
        return titleProperty;
    }

    public void setTitle(String title) {
        this.titleProperty.setValue(title);
    }

    public String getActionKey() {
        return actionKey;
    }

    public void setActionKey(String actionKey) {
        this.actionKey = actionKey;
    }
    
}
