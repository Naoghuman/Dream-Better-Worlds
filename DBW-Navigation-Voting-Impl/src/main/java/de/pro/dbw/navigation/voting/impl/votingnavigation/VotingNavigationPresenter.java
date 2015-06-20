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
package de.pro.dbw.navigation.voting.impl.votingnavigation;

import de.pro.dbw.base.component.api.VotingComponentModel;
import de.pro.dbw.base.provider.BaseProvider;
import de.pro.dbw.core.configuration.api.application.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.application.action.IRegisterActions;
import de.pro.dbw.core.configuration.api.navigation.INavigationConfiguration;
import de.pro.dbw.navigation.voting.api.VotingNavigationModel;
import de.pro.dbw.util.provider.UtilProvider;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.action.api.ActionTransferModel;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * TODO the prefix (if show) shouldn't resize.
 *
 * @author PRo
 */
public class VotingNavigationPresenter implements Initializable, IActionConfiguration,
        INavigationConfiguration, IRegisterActions
{
    private static final String CSS_VOTING_NAVIGATION = "VotingNavigation.css"; // NOI18N
    
    @FXML private ListView lvNavigation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize VotingNavigationPresenter"); // NOI18N
        
        assert (lvNavigation != null) : "fx:id=\"lvNavigation\" was not injected: check your FXML file 'VotingNavigation.fxml'."; // NOI18N
        
        this.initializeNavigationLeft();
        
        this.refreshVotingNavigation();
    }
    
    private void initializeNavigationLeft() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize navigation left"); // NOI18N
        
        lvNavigation.getStylesheets().addAll(this.getClass().getResource(CSS_VOTING_NAVIGATION).toExternalForm());
        lvNavigation.getItems().clear();
        
        lvNavigation.setCellFactory(new Callback<ListView<VotingNavigationModel>, ListCell<VotingNavigationModel>>() {

            @Override
            public ListCell<VotingNavigationModel> call(ListView<VotingNavigationModel> param) {
                return new ListCell<VotingNavigationModel>() {
                    @Override
                    public void updateItem(VotingNavigationModel item, boolean empty) {
                        super.updateItem(item, empty);
                        
                        if (item != null) {
                            this.setGraphic(item.getView());
                        } else {
                            this.setGraphic(null);
                        }
                    }
                };
            }
        });
        lvNavigation.setOnMouseClicked((MouseEvent event) -> {
            if (lvNavigation.getItems().isEmpty()) {
                return;
            }
            
            // hide old popup
//            if (contextMenu.isShowing()) {
//                contextMenu.hide();
//            }

            // show popup
//            if (event.getButton().equals(MouseButton.SECONDARY)) {
//                contextMenu.show(lvNavigationResult, event.getScreenX(), event.getScreenY());
//            }
            
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() >= 2) {
                final VotingNavigationModel model = (VotingNavigationModel) lvNavigation.getSelectionModel().getSelectedItem();
                final ActionTransferModel transferModel = new ActionTransferModel();
                transferModel.setActionKey(model.getActionKey());
                transferModel.setLong(model.getIdToOpen());

                ActionFacade.getDefault().handle(transferModel);
            }
        });
//        lvNavigation.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
//            bDeleteDream.setDisable(newValue == null);
//            bShowDream.setDisable(newValue == null);
//        });
    }
    
    private void refreshVotingNavigation() {
        final PauseTransition pt = new PauseTransition();
        pt.setDuration(REFRESH_AFTER_100_MILLIS);
        pt.setOnFinished((ActionEvent event) -> {
            this.refresh();
        });
        
        pt.playFromStart();
    }

    public void refresh() {
        Platform.runLater(() -> {
            LoggerFacade.getDefault().info(this.getClass(), "Load navigation for Voting"); // NOI18N

            final List<VotingNavigationModel> models = FXCollections.observableArrayList();
//            final List<VotingModel> votings = SqlProvider.getDefault().getVotingNavigationSqlProvider().findAllActiveVotings();
//            for (VotingModel voting : votings) {
//                final VotingNavigationModel model = new VotingNavigationModel();
//                model.setActionKey(ACTION__OPEN_VOTING__FROM_NAVIGATION);
//                model.setIdToOpen(System.currentTimeMillis());
//                model.setGenerationTime(System.currentTimeMillis());
//                
//                final Parent votingComponent = BaseProvider.getDefault().getComponentProvider().getVotingComponent();
//                model.setView(votingComponent);
                
//                models.add(model);
                models.add(this.testDataVotingIsActiveAndNotVoted());
                models.add(this.testDataVotingIsActiveAndIsVoted());
//            }
            
//            Collections.sort(models);
            
            lvNavigation.getItems().clear();
            lvNavigation.getItems().addAll(models);
        });
    }

    @Override
    public void registerActions() {
        LoggerFacade.getDefault().debug(this.getClass(), "Register actions in VotingNavigationPresenter"); // NOI18N
        
        this.registerOnActionRefreshVotingNavigation();
    }

    private void registerOnActionRefreshVotingNavigation() {
        ActionFacade.getDefault().register(
                ACTION__REFRESH_NAVIGATION__VOTING,
                (ActionEvent ae) -> {
                    this.refresh();
                });
    }
    
    public ObservableList<VotingNavigationModel> getItems() {
        return lvNavigation.getItems();
    }
    
    // XXX Remove it
    private VotingNavigationModel testDataVotingIsActiveAndNotVoted() {
        final VotingComponentModel vcm = new VotingComponentModel();
        vcm.setId(UtilProvider.getDefault().getDateConverter().addDays(-7));
        vcm.setGenerationTime(UtilProvider.getDefault().getDateConverter().addDays(-7));
        vcm.setFromDate(UtilProvider.getDefault().getDateConverter().addDays(-7));
        vcm.setToDate(UtilProvider.getDefault().getDateConverter().addDays(+7));
        vcm.setTitle("Voting 1");
        
        final VotingNavigationModel model = new VotingNavigationModel();
        model.setActionKey(ACTION__OPEN_VOTING__FROM_NAVIGATION);
        model.setIdToOpen(vcm.getId());
        model.setGenerationTime(vcm.getGenerationTime());

        final Parent votingComponent = BaseProvider.getDefault().getComponentProvider().getVotingComponent(vcm);
        model.setView(votingComponent);
                
        return model;
    }
    
    // XXX Remove it
    private VotingNavigationModel testDataVotingIsActiveAndIsVoted() {
        final VotingComponentModel vcm = new VotingComponentModel();
        vcm.setId(UtilProvider.getDefault().getDateConverter().addDays(-3));
        vcm.setGenerationTime(UtilProvider.getDefault().getDateConverter().addDays(-3));
        vcm.setFromDate(UtilProvider.getDefault().getDateConverter().addDays(-3));
        vcm.setToDate(UtilProvider.getDefault().getDateConverter().addDays(+10));
        vcm.setTitle("Voting 2");
        
        final VotingNavigationModel model = new VotingNavigationModel();
        model.setActionKey(ACTION__OPEN_VOTING__FROM_NAVIGATION);
        model.setIdToOpen(vcm.getId());
        model.setGenerationTime(vcm.getGenerationTime());

        final Parent votingComponent = BaseProvider.getDefault().getComponentProvider().getVotingComponent(vcm);
        model.setView(votingComponent);
                
        return model;
    }
    
}
