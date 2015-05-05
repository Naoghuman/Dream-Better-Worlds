/*
 * Copyright (C) 2014 PRo
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
package de.pro.dbw.file.tipofthenight.api;

import de.pro.dbw.core.configuration.api.application.defaultid.IDefaultIdConfiguration;
import de.pro.dbw.core.configuration.api.application.util.IUtilConfiguration;
import java.util.Objects;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author PRo
 */
public final class TipOfTheNightModelOld implements Comparable<TipOfTheNightModelOld>, IDefaultIdConfiguration, IUtilConfiguration {
    
    private static final String TIP_OF_THE_NIGHT__DEFAULT_TITLE = "New Tip of the Night"; // NOI18N
    
    private BooleanProperty markAsChangedProperty = null;
    private LongProperty idProperty = null;
    private StringProperty textProperty = null;
    private StringProperty titleProperty = null;
    private StringProperty titleForListViewProperty = null;
    
    public TipOfTheNightModelOld() {
        this.initialize();
    }
    
    private void initialize() {
        markAsChangedProperty = new SimpleBooleanProperty(Boolean.FALSE);
        idProperty = new SimpleLongProperty(FILE__TIP_OF_THE_NIGHT___DEFAULT_ID);
        textProperty = new SimpleStringProperty(SIGN__EMPTY);
        titleProperty = new SimpleStringProperty(TIP_OF_THE_NIGHT__DEFAULT_TITLE);
        titleForListViewProperty = new SimpleStringProperty(titleProperty.getValue());
        titleForListViewProperty.bind(Bindings
                .when(markAsChangedProperty)
                .then(Bindings.concat(SIGN__STAR).concat(
                        StringBinding.stringExpression(titleProperty)))
                .otherwise(StringBinding.stringExpression(titleProperty)));
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
    
    public void setId(Long id) {
        idProperty.setValue(id);
    }
    
    public LongProperty idProperty() {
        return idProperty;
    }
    
    public String getText() {
        return textProperty.getValue();
    }
    
    public void setText(String text) {
        textProperty.setValue(text);
    }
    
    public StringProperty textProperty() {
        return textProperty;
    }
    
    public String getTitle() {
        return titleProperty.getValue();
    }
    
    public void setTitle(String title) {
        titleProperty.unbind();
        titleProperty.setValue(title);
        
        this.setTitleForWizard(titleForListViewProperty.getValue());
    }
    
    public StringProperty titleProperty() {
        return titleProperty;
    }
    
    public void setTitleForWizard(String titleForWizard) {
        titleForListViewProperty.unbind();
        titleForListViewProperty.setValue(titleForWizard);
        titleForListViewProperty.bind(Bindings
                .when(markAsChangedProperty)
                .then(Bindings.concat(SIGN__STAR).concat(
                        StringBinding.stringExpression(titleProperty)))
                .otherwise(titleProperty));
    }
    
    public StringProperty titleForWizardProperty() {
        return titleForListViewProperty;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Long.hashCode(this.getId());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TipOfTheNightModelOld other = (TipOfTheNightModelOld) obj;
        if (!Objects.equals(this.getId(), other.getId())) {
            return false;
        }
        
        return true;
    }

    @Override
    public int compareTo(TipOfTheNightModelOld other) {
        System.out.println("XXX TipOfTheNightModel.compareTo() 1.generationtime, 2.title,3.id");
        return Long.compare(other.getId(), this.getId());
    }
    
}
