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
package de.pro.dbw.base.component.impl.extendedtab;

import de.pro.dbw.base.component.api.ExtendedTabModel;
import de.pro.dbw.core.configuration.api.application.util.IUtilConfiguration;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;

/**
 * TODO
 *  - wenn fertig
 *  - erstetzt tab dreamfile
 *  - ersettztt tab welcomefile
 * 
 *  - desktop save multifiles
 *
 * @author PRo
 */
public class ExtendedTab extends Tab implements IUtilConfiguration {
    
    private ExtendedTabModel model = null;

    public ExtendedTab(ExtendedTabModel model) {
        this.model = model;
        
        this.initialize();
    }
    
    private void initialize() {
        final ScrollPane scroller = new ScrollPane();
        scroller.setContent(model.getView());
        scroller.setFitToWidth(Boolean.TRUE);
        scroller.setFitToHeight(Boolean.TRUE);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        super.setClosable(model.isCloseable());
        super.setContent(scroller);
        super.setGraphic(this.createTextGraphic());
        super.setId(String.valueOf(model.getId()));
        super.idProperty().bind(StringBinding.stringExpression(model.idProperty()));
        super.setUserData(model.getPresenter());
        
        if (model.getActionKey() != null) {
            super.setOnCloseRequest((Event event) -> {
                if (!model.isMarkAsChanged()) {
                    return;
                }

                event.consume();
                final String title = model.getTitle().substring(1);

                // TODO dialog
            });
        }
    }
    
    private HBox createTextGraphic() {
        final HBox hBox = new HBox();
        
        // TODO add image here
        
        final Label lMarkAsChanged = new Label();
        lMarkAsChanged.setPadding(new Insets(0.0d, 0.0d, 0.0d, 5.0d));
        lMarkAsChanged.textProperty().bind(
                Bindings.when(model.markAsChangedProperty())
                        .then(SIGN__STAR)
                        .otherwise(SIGN__EMPTY));
        lMarkAsChanged.managedProperty().bind(
                Bindings.when(lMarkAsChanged.textProperty().isEmpty())
                        .then(false)
                        .otherwise(true));
        hBox.getChildren().add(lMarkAsChanged);
        
        final Label lText = new Label();
        lText.setText(model.getTitle());
        lText.textProperty().bind(model.titleProperty());
        lText.paddingProperty().bind(
                Bindings.when(model.markAsChangedProperty())
                        .then(Insets.EMPTY)
                        .otherwise(new Insets(0.0d, 0.0d, 0.0d, 5.0d)));
        hBox.getChildren().add(lText);
        
        return hBox;
    }
    
    public Boolean isMarkAsChanged() {
        return model.isMarkAsChanged();
    }
    
    public ExtendedTabModel getModel() {
        return model;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (int) (model.getId().longValue() ^ (model.getId().longValue() >>> 32));
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
        final ExtendedTab other = (ExtendedTab) obj;
        if (model.getId().longValue() != other.getModel().getId().longValue()) {
            return false;
        }
        return true;
    }
    
    
    
}
