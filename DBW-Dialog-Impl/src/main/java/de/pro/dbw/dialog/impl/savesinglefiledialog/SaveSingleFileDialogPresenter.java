/*
 * Copyright (C) 2014 Dream Better Worlds
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
package de.pro.dbw.dialog.impl.savesinglefiledialog;

import de.pro.dbw.dialog.api.DialogEventHandler;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author PRo
 */
public class SaveSingleFileDialogPresenter implements Initializable {
    
    @FXML private AnchorPane apDialog;
    @FXML private Button bNo;
    @FXML private Button bYes;
    @FXML private TitledPane tpDialog;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize SaveSingleFileDialogPresenter"); // NOI18N
        
        assert (apDialog != null) : "fx:id=\"apDialog\" was not injected: check your FXML file 'SaveMultiFiles.fxml'."; // NOI18N
        assert (bNo != null)      : "fx:id=\"bNo\" was not injected: check your FXML file 'SaveMultiFiles.fxml'."; // NOI18N
        assert (bYes != null)     : "fx:id=\"bYes\" was not injected: check your FXML file 'SaveMultiFiles.fxml'."; // NOI18N
        assert (tpDialog != null) : "fx:id=\"tpDialog\" was not injected: check your FXML file 'SaveMultiFiles.fxml'."; // NOI18N
    
        this.initializeEventHandlers();
    }
    
    private void initializeEventHandlers() {
        Platform.runLater(() -> {
            final Pane pTitledPaneHeader = (Pane) tpDialog.lookup(".title"); // NOI18N
            DialogEventHandler.getDefault().configure(pTitledPaneHeader, apDialog);
        });
    }

    public void configure(
            EventHandler<ActionEvent> onActionYes,
            EventHandler<ActionEvent> onActionNo
    ) {
        bNo.setOnAction(onActionNo);
        bYes.setOnAction(onActionYes);
    }
}
