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
package de.pro.dbw.dialog.impl.dialogtemplate;

import de.pro.dbw.core.configuration.api.action.IActionConfiguration;
import de.pro.dbw.dialog.api.DialogEventHandler;
import de.pro.lib.logger.api.LoggerFacade;
import java.awt.Dimension;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author PRo
 */
public class DialogTemplatePresenter implements Initializable, IActionConfiguration {
    
    @FXML private AnchorPane apDialog;
    @FXML private TitledPane tpDialog;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize DialogTemplatePresenter"); // NOI18N
        
        assert (apDialog != null) : "fx:id=\"apDialog\" was not injected: check your FXML file 'DialogTemplate.fxml'."; // NOI18N
        assert (tpDialog != null) : "fx:id=\"tpDialog\" was not injected: check your FXML file 'DialogTemplate.fxml'."; // NOI18N
    
        this.initializeEventHandlers();
    }
    
    private void initializeEventHandlers() {
        Platform.runLater(() -> {
            final Pane pTitledPaneHeader = (Pane) tpDialog.lookup(".title"); // NOI18N
            DialogEventHandler.getDefault().configure(pTitledPaneHeader, apDialog);
        });
    }
    
    public void configure(String title, Parent content, Dimension size) {
        tpDialog.setText(title);
        tpDialog.setContent(content);
        
        apDialog.setPrefWidth(size.getWidth());
        apDialog.setPrefHeight(size.getHeight());
    }
    
}
