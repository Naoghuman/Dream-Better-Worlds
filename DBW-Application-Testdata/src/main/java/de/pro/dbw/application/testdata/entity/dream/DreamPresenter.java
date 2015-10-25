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
package de.pro.dbw.application.testdata.entity.dream;

import de.pro.dbw.application.testdata.entity.EntityHelper;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
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
public class DreamPresenter implements Initializable {
    
    @FXML private ComboBox cbDream;
    @FXML private Label lProgressDream;
    @FXML private Label lProgressInformationDream;
    @FXML private ProgressBar pbDream;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize DreamPresenter");
        
        assert (cbDream != null)        : "fx:id=\"cbDream\" was not injected: check your FXML file 'Dream.fxml'."; // NOI18N
        assert (lProgressDream != null) : "fx:id=\"lProgressDream\" was not injected: check your FXML file 'Dream.fxml'."; // NOI18N
        assert (lProgressInformationDream != null) : "fx:id=\"lProgressInformationDream\" was not injected: check your FXML file 'Dream.fxml'."; // NOI18N
        assert (pbDream != null)        : "fx:id=\"pbDream\" was not injected: check your FXML file 'Dream.fxml'."; // NOI18N
    
        this.initializeComboBox();
    }

    private void initializeComboBox() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize ComboBox");
        
        cbDream.getItems().addAll(EntityHelper.getDefault().getQuantityEntities());
        cbDream.setCellFactory(new Callback<ListView<Integer>, ListCell<Integer>>() {

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
                            
                            super.setText("" + item);
                        }
                    };
            }
        });
        
        cbDream.getSelectionModel().selectFirst();
    }
    
}
