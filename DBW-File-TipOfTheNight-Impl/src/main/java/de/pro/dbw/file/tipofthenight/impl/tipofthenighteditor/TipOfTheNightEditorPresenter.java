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
package de.pro.dbw.file.tipofthenight.impl.tipofthenighteditor;

import de.pro.dbw.core.configuration.api.application.preferences.IPreferencesConfiguration;
import de.pro.dbw.dialog.api.DialogEventHandler;
import de.pro.dbw.file.tipofthenight.impl.tipofthenightchooser.TipOfTheNightChooserPresenter;
import de.pro.dbw.file.tipofthenight.impl.tipofthenighteditor.tabtipofthenight.TabTipOfTheNightView;
import de.pro.dbw.file.tipofthenight.impl.tipofthenightchooser.TipOfTheNightChooserView;
import de.pro.dbw.file.tipofthenight.impl.tipofthenighteditor.tabtipofthenight.TabTipOfTheNightPresenter;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author PRo
 */
public class TipOfTheNightEditorPresenter implements Initializable, IPreferencesConfiguration {

    @FXML private AnchorPane apDialog;
    @FXML private BorderPane bpPreview;
    @FXML private Tab tTemplate;
    @FXML private Tab tTipOfTheNight;
    @FXML private TitledPane tpDialog;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize TipOfTheNightEditorPresenter"); // NOI18N
        
        assert (apDialog != null)       : "fx:id=\"apDialog\" was not injected: check your FXML file 'TipOfTheNightEditor.fxml'."; // NOI18N
        assert (bpPreview != null)      : "fx:id=\"bpPreview\" was not injected: check your FXML file 'TipOfTheNightEditor.fxml'."; // NOI18N
        assert (tTemplate != null)      : "fx:id=\"tTemplate\" was not injected: check your FXML file 'TipOfTheNightEditor.fxml'."; // NOI18N
        assert (tTipOfTheNight != null) : "fx:id=\"tTipOfTheNight\" was not injected: check your FXML file 'TipOfTheNightEditor.fxml'."; // NOI18N
        assert (tpDialog != null)       : "fx:id=\"tpDialog\" was not injected: check your FXML file 'TipOfTheNightEditor.fxml'."; // NOI18N
        
        this.initialize();
        this.initializeEventHandlers();
    }
    
    private void initializeEventHandlers() {
        Platform.runLater(() -> {
            final Pane pTitledPaneHeader = (Pane) tpDialog.lookup(".title"); // NOI18N
            DialogEventHandler.getDefault().configure(pTitledPaneHeader, apDialog);
        });
    }
    
    private void initialize() {
        final TipOfTheNightChooserView chooser = new TipOfTheNightChooserView();
        final TipOfTheNightChooserPresenter chooserPresenter = chooser.getRealPresenter();
        chooserPresenter.prepareForPreview();
        bpPreview.setCenter(chooser.getView());
        
        final TabTipOfTheNightView tabTipOfTheNight = new TabTipOfTheNightView();
        final TabTipOfTheNightPresenter tabTipOfTheNightPresenter = tabTipOfTheNight.getRealPresenter();
        tTipOfTheNight.setContent(tabTipOfTheNight.getView());
        
        chooserPresenter.textProperty().bind(tabTipOfTheNightPresenter.textProperty());
        chooserPresenter.titleProperty().bind(tabTipOfTheNightPresenter.titleProperty());
    }

}
