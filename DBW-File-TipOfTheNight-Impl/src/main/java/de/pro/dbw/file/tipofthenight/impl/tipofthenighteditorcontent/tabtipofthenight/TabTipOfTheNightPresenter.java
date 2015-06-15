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
package de.pro.dbw.file.tipofthenight.impl.tipofthenighteditorcontent.tabtipofthenight;

import de.pro.dbw.core.configuration.api.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.application.defaultid.IDefaultIdConfiguration;
import de.pro.dbw.core.configuration.api.application.preferences.IPreferencesConfiguration;
import de.pro.dbw.core.configuration.api.file.tipofthenight.ITipOfTheNightConfiguration;
import de.pro.dbw.dialog.provider.DialogProvider;
import de.pro.dbw.file.tipofthenight.api.TipOfTheNightModel;
import de.pro.dbw.core.sql.provider.SqlProvider;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.logger.api.LoggerFacade;
import de.pro.lib.preferences.api.PreferencesFacade;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

/**
 * 
 * @author PRo
 */
public class TabTipOfTheNightPresenter implements Initializable, IActionConfiguration, IDefaultIdConfiguration, IPreferencesConfiguration, ITipOfTheNightConfiguration {

    @FXML private Button bDelete;
    @FXML private Button bNew;
    @FXML private Button bSave;
    @FXML private Button bShowCategoryChooser;
    @FXML private FlowPane fpCategory;
    @FXML private ListView lvTipsOfTheNight;
    @FXML private TextArea taTip;
    @FXML private TextField tfTitle;
    
    private int index = PREF__TIP_OF_THE_NIGHT_INDEX__DEFAULT_VALUE;
    
    private TipOfTheNightChangeListener changeListener = null;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize TabTipOfTheNightPresenter"); // NOI18N
        
        assert (bDelete != null)          : "fx:id=\"bDelete\" was not injected: check your FXML file 'TabTipOfTheNight.fxml'."; // NOI18N
        assert (bNew != null)             : "fx:id=\"bNew\" was not injected: check your FXML file 'TabTipOfTheNight.fxml'."; // NOI18N
        assert (bSave != null)            : "fx:id=\"bSave\" was not injected: check your FXML file 'TabTipOfTheNight.fxml'."; // NOI18N
        assert (bShowCategoryChooser != null) : "fx:id=\"bShowCategoryChooser\" was not injected: check your FXML file 'TabTipOfTheNight.fxml'."; // NOI18N
        assert (fpCategory != null)       : "fx:id=\"fpCategory\" was not injected: check your FXML file 'TabTipOfTheNight.fxml'."; // NOI18N
        assert (lvTipsOfTheNight != null) : "fx:id=\"lvTipsOfTheNight\" was not injected: check your FXML file 'TabTipOfTheNight.fxml'."; // NOI18N
        assert (taTip != null)            : "fx:id=\"taTip\" was not injected: check your FXML file 'TabTipOfTheNight.fxml'."; // NOI18N
        assert (tfTitle != null)          : "fx:id=\"tfTitle\" was not injected: check your FXML file 'TabTipOfTheNight.fxml'."; // NOI18N

        this.initialize();
    }
    
    private void initialize() {
        this.initializeButtons();
        this.initializeIndex();
        this.initializeListView();
        this.initializeTitle();
        this.initializeText();
        
        this.registerOnActionRefresh();
        this.onActionRefresh();
    }
    
    private void initializeButtons() {
        bDelete.disableProperty().bind(Bindings.isEmpty(lvTipsOfTheNight.getItems()));
        bSave.disableProperty().bind(Bindings.isEmpty(lvTipsOfTheNight.getItems()));
    }
    
    private void initializeIndex() {
        index = PreferencesFacade.getDefault().getInt(
                PREF__TIP_OF_THE_NIGHT_INDEX,
                PREF__TIP_OF_THE_NIGHT_INDEX__DEFAULT_VALUE);
    }
    
    private void initializeListView() {
        LoggerFacade.getDefault().debug(this.getClass(), "Initialize ListView TipsOfTheNight"); // NOI18N

        lvTipsOfTheNight.getStylesheets().addAll(this.getClass().getResource("TabTipOfTheNight.css").toExternalForm()); // NOI18N
        lvTipsOfTheNight.getItems().clear();
        
        lvTipsOfTheNight.setCellFactory((list) -> {
            return new ListCell<TipOfTheNightModel>() {
                @Override
                protected void updateItem(TipOfTheNightModel item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        return;
                    }
                    
                    setText(item.getTitle().isEmpty() ? "?" : item.getTitle());
                }
            };
        });
        
        changeListener = new TipOfTheNightChangeListener();
        lvTipsOfTheNight.getSelectionModel().selectedItemProperty().addListener(changeListener);
    }
    
    private void initializeTitle() {
        LoggerFacade.getDefault().debug(this.getClass(), "Initialize TextField Title"); // NOI18N
        
        tfTitle.setText(null);
    }
    
    private void initializeText() {
        LoggerFacade.getDefault().debug(this.getClass(), "Initialize TextArea Text"); // NOI18N
        
        taTip.setText(null);
    }
    
    public StringProperty textProperty() {
        return taTip.textProperty();
    }
    
    public StringProperty titleProperty() {
        return tfTitle.textProperty();
    }
    
    public void onActionShowCategoryChooserDialog() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action show Category Chooser Dialog"); // NOI18N
        
        /*
        TODO
         - Integrate when Feature-Tag is implemented.
         - Only one Category can added to a TipOfTheNight.
        */
    }
    
    public void onActionCloseTipOfTheNightEditor() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action close Tip of the Night Editor"); // NOI18N
        
        DialogProvider.getDefault().hide();
    }
    
    public void onActionDeleteTipOfTheNight() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action delete Tip of the Night"); // NOI18N
        
        final TipOfTheNightModel model = (TipOfTheNightModel) lvTipsOfTheNight.getSelectionModel().getSelectedItem();
        if (Objects.equals(model.getId(), FILE__TIP_OF_THE_NIGHT__DEFAULT_ID)) {
            this.onActionRefresh();
            
            return;
        }

        DialogProvider.getDefault().showDeleteDialog2(
                // TODO properties
                "Do you really want delete this tip of the night?",  // NOI18N
                (ActionEvent ae) -> { // Yes
                    SqlProvider.getDefault().getTipOfTheNightProvider().delete(model.getId());
        
                    ActionFacade.getDefault().handle(ACTION__REFRESH_TIP_OF_THE_NIGHT__EDITOR);
                    DialogProvider.getDefault().hide2();
                },
                (ActionEvent ae) -> { // No
                    DialogProvider.getDefault().hide2();
                });
    }
    
    public void onActionNewTipOfTheNight() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action new Tip of the Night"); // NOI18N

        // Check if a new TipOfTheNight exists
        final List<TipOfTheNightModel> allTipsOfTheNight = FXCollections.observableArrayList();
        allTipsOfTheNight.addAll(lvTipsOfTheNight.getItems());
        
        for (TipOfTheNightModel model : allTipsOfTheNight) {
            if (Objects.equals(model.getId(), FILE__TIP_OF_THE_NIGHT__DEFAULT_ID)) {
                Platform.runLater(() -> {
                    lvTipsOfTheNight.getSelectionModel().select(model);
                    lvTipsOfTheNight.scrollTo(model);
                });
                
                return;
            }
        }
        
        // Create new one
        final TipOfTheNightModel model = new TipOfTheNightModel();
        model.setGenerationTime(System.currentTimeMillis());
        model.setId(FILE__TIP_OF_THE_NIGHT__DEFAULT_ID);
        model.setText(""); // XXX properties
        model.setTitle("New"); // XXX properties

        allTipsOfTheNight.add(model);
        this.select(model, allTipsOfTheNight);
    }
    
    private void onActionRefresh() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action refresh"); // NOI18N
        
        final List<TipOfTheNightModel> allTipsOfTheNight = FXCollections.observableArrayList();
        allTipsOfTheNight.addAll(SqlProvider.getDefault().getTipOfTheNightProvider().findAll());
        if (allTipsOfTheNight.isEmpty()) {
            index = PREF__TIP_OF_THE_NIGHT_INDEX__DEFAULT_VALUE;
            tfTitle.setText(null);
            taTip.setText(null);
            
            lvTipsOfTheNight.getSelectionModel().selectedItemProperty().removeListener(changeListener);
            lvTipsOfTheNight.getItems().clear();
                
            return;
        }
        
        if (index >= allTipsOfTheNight.size()) {
            index = PREF__TIP_OF_THE_NIGHT_INDEX__DEFAULT_VALUE;
            PreferencesFacade.getDefault().putInt(PREF__TIP_OF_THE_NIGHT_INDEX, index);
        }
        
        final TipOfTheNightModel model = allTipsOfTheNight.get(index);
        this.select(model, allTipsOfTheNight);
    }
    
    public void onActionSaveTipOfTheNight() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action save Tip of the Night"); // NOI18N
        
        /*
        TODO
            - The title must be unique (only [A-Z][a-z][0-9] + leer + - + _)
        */
        final TipOfTheNightModel model = (TipOfTheNightModel) lvTipsOfTheNight.getSelectionModel()
                .getSelectedItem();
        model.setTitle(tfTitle.getText());
        model.setText(taTip.getText());
        
        SqlProvider.getDefault().getTipOfTheNightProvider().createOrUpdate(model, FILE__TIP_OF_THE_NIGHT__DEFAULT_ID);
        
        // Update gui
        final List<TipOfTheNightModel> allTipsOfTheNight = FXCollections.observableArrayList();
        allTipsOfTheNight.addAll(lvTipsOfTheNight.getItems());
        this.select(model, allTipsOfTheNight);
    }
    
    private void registerOnActionRefresh() {
        LoggerFacade.getDefault().debug(this.getClass(), "Register on action Refresh"); // NOI18N
        
        ActionFacade.getDefault().register(
                ACTION__REFRESH_TIP_OF_THE_NIGHT__EDITOR,
                (ActionEvent ae) -> {
                    this.onActionRefresh();
                });
    }
    
    private void select(TipOfTheNightModel model, List<TipOfTheNightModel> allTipsOfTheNight) {
        Platform.runLater(() -> {
            Collections.sort(allTipsOfTheNight);
            
            lvTipsOfTheNight.getSelectionModel().selectedItemProperty().removeListener(changeListener);
            lvTipsOfTheNight.getItems().clear();
            lvTipsOfTheNight.getItems().addAll(allTipsOfTheNight);
            lvTipsOfTheNight.getSelectionModel().selectedItemProperty().addListener(changeListener);
            lvTipsOfTheNight.getSelectionModel().select(model);
            lvTipsOfTheNight.scrollTo(model);
        });
    }

    private class TipOfTheNightChangeListener implements ChangeListener<TipOfTheNightModel> {

        @Override
        public void changed(ObservableValue<? extends TipOfTheNightModel> observable, TipOfTheNightModel oldValue, TipOfTheNightModel newValue) {
            if (newValue == null) {
                tfTitle.setText(null);
                taTip.setText(null);
                
                return;
            }
            
            final int newIndex = lvTipsOfTheNight.getSelectionModel().getSelectedIndex();
            if (newIndex != index) {
                index = newIndex;
                PreferencesFacade.getDefault().putInt(PREF__TIP_OF_THE_NIGHT_INDEX, index);
            }
            
            tfTitle.setText(newValue.getTitle());
            taTip.setText(newValue.getText());
        }
    }
    
}
