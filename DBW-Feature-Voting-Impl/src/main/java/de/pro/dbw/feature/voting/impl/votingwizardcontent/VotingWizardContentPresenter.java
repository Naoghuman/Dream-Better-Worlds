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
package de.pro.dbw.feature.voting.impl.votingwizardcontent;

import de.pro.dbw.base.provider.BaseProvider;
import de.pro.dbw.core.configuration.api.application.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.application.dialog.IDialogConfiguration;
import de.pro.dbw.core.configuration.api.navigation.INavigationConfiguration;
import de.pro.dbw.core.sql.provider.SqlProvider;
import de.pro.dbw.dialog.api.IDialogSize;
import de.pro.dbw.dialog.provider.DialogProvider;
import de.pro.dbw.feature.voting.api.EVotingEditorMode;
import de.pro.dbw.feature.voting.api.VotingElementModel;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.action.api.ActionTransferModel;
import de.pro.lib.logger.api.LoggerFacade;
import java.awt.Dimension;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 *
 * @author PRo
 */
public class VotingWizardContentPresenter implements Initializable, IActionConfiguration,
        IDialogSize, INavigationConfiguration
{
    @FXML private Button bAdd;
    @FXML private Button bRemove;
    @FXML private Button bSave;
    @FXML private ComboBox cbAvailableVotings;
    @FXML private DatePicker dpFrom;
    @FXML private DatePicker dpTo;
    @FXML private ListView lvActiveVotings;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize VotingWizardContentPresenter");
        
        assert (bAdd != null)    : "fx:id=\"bAdd\" was not injected: check your FXML file 'VotingWizardContent.fxml'."; // NOI18N
        assert (bRemove != null) : "fx:id=\"bRemove\" was not injected: check your FXML file 'VotingWizardContent.fxml'."; // NOI18N
        assert (bSave != null)   : "fx:id=\"bSave\" was not injected: check your FXML file 'VotingWizardContent.fxml'."; // NOI18N
        assert (cbAvailableVotings != null) : "fx:id=\"cbAvailableVotings\" was not injected: check your FXML file 'VotingWizardContent.fxml'."; // NOI18N
        assert (dpFrom != null)  : "fx:id=\"dpFrom\" was not injected: check your FXML file 'VotingWizardContent.fxml'."; // NOI18N
        assert (dpTo != null)    : "fx:id=\"dpTo\" was not injected: check your FXML file 'VotingWizardContent.fxml'."; // NOI18N
        assert (lvActiveVotings != null)    : "fx:id=\"lvActiveVotings\" was not injected: check your FXML file 'VotingWizardContent.fxml'."; // NOI18N
    
        this.initializeButtons();
        this.initializeComboBox();
        this.initializeDatePicker();
        this.initializeListView();
        
        this.onActionRefreshLittleLater();
    }
    
    private void initializeButtons() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize Buttons");
        
        bAdd.setDisable(Boolean.TRUE);
        bAdd.disableProperty().bind(lvActiveVotings.selectionModelProperty().isNotNull());
        
        bRemove.setDisable(Boolean.TRUE);
        bRemove.disableProperty().bind(lvActiveVotings.selectionModelProperty().isNotNull());
        
        // TODO save-button?
    }
    
    private void initializeComboBox() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize ComboBox in Voting Wizard"); // NOI18N
        
        cbAvailableVotings.getItems().clear();
        cbAvailableVotings.setButtonCell(new ListCell<VotingElementModel>() {
                @Override
                protected void updateItem(VotingElementModel model, boolean empty) {
                    super.updateItem(model, empty);

                    super.setText((model == null || empty) ? null : model.getTitle());
                }
            });
        cbAvailableVotings.setCellFactory((list) -> {
            return new ListCell<VotingElementModel>() {
                @Override
                protected void updateItem(VotingElementModel model, boolean empty) {
                    super.updateItem(model, empty);

                    super.setText(model != null ? model.getTitle() : null);
                }
            };
        });
    }
    
    private void initializeDatePicker() {
        /*
        TODO load date.now and date.now+1?
        */
    }
    
    private void initializeListView() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize ListView in Voting Wizard"); // NOI18N
        
        /*
        TODO
         - init listview with votingcomponentmodel
         - load all active votings from db in listview
        */
        lvActiveVotings.getStylesheets().addAll(this.getClass().getResource("VotingWizardContent.css").toExternalForm()); // NOI18N
        lvActiveVotings.getItems().clear();
        
//        lvActiveVotings.setCellFactory(new Callback<ListView<VotingComponentModel>, ListCell<VotingComponentModel>>() {
//
//            @Override
//            public ListCell<VotingComponentModel> call(ListView<VotingComponentModel> param) {
//                return new ListCell<VotingComponentModel>() {
//                    @Override
//                    public void updateItem(VotingComponentModel item, boolean empty) {
//                        super.updateItem(item, empty);
//                        
//                        if (item != null) {
//                            final Parent component = BaseProvider.getDefault().getComponentProvider().getVotingComponent(item);
//                            this.setGraphic(component);
//                        } else {
//                            this.setGraphic(null);
//                        }
//                    }
//                };
//            }
//        });
        
        lvActiveVotings.setOnMouseClicked((MouseEvent event) -> {
            if (lvActiveVotings.getItems().isEmpty()) {
                return;
            }
            
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() >= 2) {
                /*
                TODO show element in preview and combobox + datepicker
                */
            }
        });
    }

    @Override
    public Dimension getSize() {
        return IDialogConfiguration.SIZE__W495_H330;
    }
    
//    @Override
//    public Dimension getSize() {
//        return IDialogConfiguration.SIZE__W495_H330;
//    }
    
    public void onActionAdd() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action add Voting"); // NOI18N
        
        /*
        TODO
         - catch the selected VotingComponentModel from the ComboBox
         - prepare the selected VotingComponentModel for ListView
         - add it to the ListView
        */
    }
    
    public void onActionClose() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action close Voting Wizard"); // NOI18N
        
        DialogProvider.getDefault().hide();
    }
    
    public void onActionShowVotingEditor() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action show Voting Editor"); // NOI18N
        
        final ActionTransferModel model = new ActionTransferModel();
        model.setActionKey(ACTION__SHOW_VOTING__EDITOR);
        model.setObject(EVotingEditorMode.OPEN_FROM_WIZARD);
        model.setResponseActionKey(this.registerOnActionRefreshFromVotingEditor());
        
        ActionFacade.INSTANCE.handle(model);
    }
    
    public void onActionRefresh() {
        Platform.runLater(() -> {
            LoggerFacade.INSTANCE.info(this.getClass(), "Refresh Votings in ListView"); // NOI18N

            /*
            TODO
             - load all VotingComponentModels from db (active-votings-table)
                - mockup table
             - prepare vcm?
             - clear ListView
             - all vcms to ListView
            */
            cbAvailableVotings.getItems().clear();
            
            final List<VotingElementModel> allVotings = FXCollections.observableArrayList();
            allVotings.addAll(SqlProvider.getDefault().getVotingSqlProvider().findAll());
            cbAvailableVotings.getItems().addAll(allVotings);
        });
    }
    
    private void onActionRefreshLittleLater() {
        final PauseTransition pt = new PauseTransition();
        pt.setDuration(REFRESH_AFTER_100_MILLIS);
        pt.setOnFinished((ActionEvent event) -> {
            this.onActionRefresh();
        });
        
        pt.playFromStart();
    }
    
    public void onActionRemove() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action remove Voting"); // NOI18N
        
        /*
        TODO
         - Catch the selected VotingComponentModel from the ListView
         - Check if Votings happens in this VCM
            - if yes then show warning
               - when yes then remove and save
               - when no nothing happens
            - if no then delete the voting in db (from active-voting-table)
            - update ListView if remove
        */
    }
    
    public void onActionSave() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action save Votings"); // NOI18N
        
    }
    
    private String registerOnActionRefreshFromVotingEditor() {
        final String actionKey = ACTION__REFRESH_ + System.currentTimeMillis();
        
        ActionFacade.INSTANCE.register(
                actionKey,
                (ActionEvent ae) -> {
                    LoggerFacade.INSTANCE.debug(this.getClass(), "Refresh Voting Wizard"); // NOI18N

                    this.onActionRefreshLittleLater();
                });
        
        return actionKey;
    }
    
}
