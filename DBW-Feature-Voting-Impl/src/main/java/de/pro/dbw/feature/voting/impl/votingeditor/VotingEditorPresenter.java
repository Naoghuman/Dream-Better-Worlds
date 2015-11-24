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
package de.pro.dbw.feature.voting.impl.votingeditor;

import de.pro.dbw.core.configuration.api.application.IApplicationConfiguration;
import de.pro.dbw.core.configuration.api.application.defaultid.IDefaultIdConfiguration;
import de.pro.dbw.core.configuration.api.application.dialog.IDialogConfiguration;
import de.pro.dbw.core.configuration.api.application.util.IUtilConfiguration;
import de.pro.dbw.core.sql.provider.SqlProvider;
import de.pro.dbw.dialog.api.IDialogSize;
import de.pro.dbw.dialog.provider.DialogProvider;
import de.pro.dbw.feature.voting.api.EVotingEditorMode;
import de.pro.dbw.feature.voting.api.VotingElementModel;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.logger.api.LoggerFacade;
import de.pro.lib.properties.api.PropertiesFacade;
import java.awt.Dimension;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author PRo
 */
public class VotingEditorPresenter implements Initializable, IApplicationConfiguration,
        IDefaultIdConfiguration, IDialogSize, IUtilConfiguration
{    
    @FXML private Button bNew;
    @FXML private Button bSave;
    @FXML private ListView lvExistingVotings;
    @FXML private TextArea taDescription;
    @FXML private TextField tfTitle;
    
    private EVotingEditorMode votingEditorMode = EVotingEditorMode.OPEN_FROM_MENU;
    private String responseActionKey = null;
    private VotingChangeListener changeListener = null;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize VotingEditorPresenter"); // NOI18N
        
        assert (bNew != null)              : "fx:id=\"bNew\" was not injected: check your FXML file 'VotingEditor.fxml'."; // NOI18N
        assert (bSave != null)             : "fx:id=\"bSave\" was not injected: check your FXML file 'VotingEditor.fxml'."; // NOI18N
        assert (lvExistingVotings != null) : "fx:id=\"lvExistingVotings\" was not injected: check your FXML file 'VotingEditor.fxml'."; // NOI18N
        assert (taDescription != null)     : "fx:id=\"taDescription\" was not injected: check your FXML file 'VotingEditor.fxml'."; // NOI18N
        assert (tfTitle != null)           : "fx:id=\"tfTitle\" was not injected: check your FXML file 'VotingEditor.fxml'."; // NOI18N
    
        this.initializeButtons();
        this.initializeDescription();
        this.initializeListView();
        this.initializeTitle();
        
        this.onActionRefresh();
    }
    
    private void initializeButtons() {
        // TODO own binding = if any VotingElementModel.isMarkedAsChanged, 
        // then actived the button 
        // otherwise not
        bSave.disableProperty().bind(Bindings.isEmpty(lvExistingVotings.getItems()));
    }
    
    private void initializeDescription() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize TextArea Description"); // NOI18N
        
        taDescription.setText(null);
    }
    
    private void initializeListView() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize ListView in Voting Editor"); // NOI18N

        lvExistingVotings.getStylesheets().addAll(this.getClass().getResource("VotingEditor.css").toExternalForm()); // NOI18N
        lvExistingVotings.getItems().clear();
        
        lvExistingVotings.setCellFactory((list) -> {
            return new ListCell<VotingElementModel>() {
                @Override
                protected void updateItem(VotingElementModel model, boolean empty) {
                    super.updateItem(model, empty);

                    super.setText((model == null || empty) ? null : model.getTitle());
                }
            };
        });
        
        changeListener = new VotingChangeListener();
        lvExistingVotings.getSelectionModel().selectedItemProperty().addListener(changeListener);
    }
    
    private void initializeTitle() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize TextField title"); // NOI18N
        
        tfTitle.setText(null);
    }

    public void configure(EVotingEditorMode votingEditorMode) {
        this.configure(votingEditorMode, null);
    }

    public void configure(EVotingEditorMode votingEditorMode, String responseActionKey) {
        this.votingEditorMode = votingEditorMode;
        this.responseActionKey = responseActionKey;
    }

    @Override
    public Dimension getSize() {
        return IDialogConfiguration.SIZE__W495_H330;
    }
    
    public void onActionClose() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action close Voting Editor"); // NOI18N
        
        if (responseActionKey != null) {
            ActionFacade.INSTANCE.handle(responseActionKey);
            ActionFacade.INSTANCE.remove(responseActionKey);
        }
        
        switch (votingEditorMode) {
            case OPEN_FROM_MENU:   { DialogProvider.getDefault().hide(); break; }
            case OPEN_FROM_WIZARD: { DialogProvider.getDefault().hide2(); break; }
        }
    }
    
    public void onActionNew() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action create new Voting"); // NOI18N
        
        // Check if a new Voting exists
        final List<VotingElementModel> allVotings = FXCollections.observableArrayList();
        allVotings.addAll(lvExistingVotings.getItems());
        
        for (VotingElementModel model : allVotings) {
            if (Objects.equals(model.getId(), FEATURE__VOTING__DEFAULT_ID)) {
                Platform.runLater(() -> {
                    lvExistingVotings.getSelectionModel().select(model);
                    lvExistingVotings.scrollTo(model);
                });
                
                return;
            }
        }
        
        // Create new one
        final VotingElementModel model = new VotingElementModel();
        model.setGenerationTime(System.currentTimeMillis());
        model.setId(FEATURE__VOTING__DEFAULT_ID);
        model.setDescription(SIGN__EMPTY);
        model.setMarkAsChanged(Boolean.TRUE);// TODO true == * before the title
        model.setTitle(PropertiesFacade.INSTANCE.getProperty(DBW__RESOURCE_BUNDLE, KEY__APPLICATION__PREFIX_NEW));
        // TODO all other parameter can be null in entity

        allVotings.add(model);
        this.select(model, allVotings);
    }
    
    private void onActionRefresh() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Refresh"); // NOI18N
        
        final VotingElementModel model = (VotingElementModel) lvExistingVotings.getSelectionModel()
                .getSelectedItem();
        
        final List<VotingElementModel> allVotings = FXCollections.observableArrayList();
        allVotings.addAll(SqlProvider.getDefault().getVotingSqlProvider().findAll());
        if (allVotings.isEmpty()) {
            tfTitle.setText(null);
            taDescription.setText(null);
            
            lvExistingVotings.getSelectionModel().selectedItemProperty().removeListener(changeListener);
            lvExistingVotings.getItems().clear();
                
            return;
        }
        
        this.select(model, allVotings);
    }
    
    public void onActionSave() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Save"); // NOI18N
        /*
        TODO
            - The title must be unique (only [A-Z][a-z][0-9] + leer + - + _)
        */
        final VotingElementModel model = (VotingElementModel) lvExistingVotings.getSelectionModel()
                .getSelectedItem();
        model.setTitle(tfTitle.getText());
        model.setDescription(taDescription.getText());
        
        SqlProvider.getDefault().getVotingSqlProvider().createOrUpdate(model, FEATURE__VOTING__DEFAULT_ID);
        
        // Update gui
        final List<VotingElementModel> allVotings = FXCollections.observableArrayList();
        allVotings.addAll(lvExistingVotings.getItems());
        this.select(model, allVotings);
    }
    
    private void select(VotingElementModel model, final List<VotingElementModel> allVotings) {
            
        Platform.runLater(() -> {
            Collections.sort(allVotings);
            
            lvExistingVotings.getSelectionModel().selectedItemProperty().removeListener(changeListener);
            lvExistingVotings.getItems().clear();
            lvExistingVotings.getItems().addAll(allVotings);
            lvExistingVotings.getSelectionModel().selectedItemProperty().addListener(changeListener);
            
            VotingElementModel modelToSelect = model != null ? model : allVotings.get(0);
            lvExistingVotings.getSelectionModel().select(modelToSelect);
            lvExistingVotings.scrollTo(modelToSelect);
        });
    }
    
    private class VotingChangeListener implements ChangeListener<VotingElementModel> {

        @Override
        public void changed(ObservableValue<? extends VotingElementModel> observable, VotingElementModel oldValue, VotingElementModel newValue) {
            if (newValue == null) {
                tfTitle.setText(null);
                taDescription.setText(null);
                
                return;
            }
            
            tfTitle.setText(newValue.getTitle());
            taDescription.setText(newValue.getDescription());
        }
    }
    
}
