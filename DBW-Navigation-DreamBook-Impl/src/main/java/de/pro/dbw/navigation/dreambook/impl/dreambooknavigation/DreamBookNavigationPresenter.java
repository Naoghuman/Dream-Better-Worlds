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
package de.pro.dbw.navigation.dreambook.impl.dreambooknavigation;

import de.pro.dbw.core.configuration.api.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.action.IRegisterActions;
import de.pro.dbw.core.configuration.api.file.dream.IDreamConfiguration;
import de.pro.dbw.core.configuration.api.navigation.INavigationConfiguration;
import de.pro.dbw.navigation.dreambook.api.DreamBookNavigationModel;
import de.pro.dbw.navigation.dreambook.impl.listview.dreamelement.DreamElementPresenter;
import de.pro.dbw.navigation.dreambook.impl.listview.dreamelement.DreamElementView;
import de.pro.dbw.file.dream.api.DreamModel;
import de.pro.dbw.core.sql.provider.SqlProvider;
import de.pro.dbw.file.reflection.api.ReflectionModel;
import de.pro.dbw.navigation.dreambook.impl.listview.reflectionelement.ReflectionElementPresenter;
import de.pro.dbw.navigation.dreambook.impl.listview.reflectionelement.ReflectionElementView;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.action.api.ActionTransferModel;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class DreamBookNavigationPresenter implements Initializable, IActionConfiguration,
        IDreamConfiguration, INavigationConfiguration, IRegisterActions
{
    @FXML private ListView lvNavigation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize DreamBookPresenter"); // NOI18N
        
        assert (lvNavigation != null) : "fx:id=\"lvNavigation\" was not injected: check your FXML file 'DreamBookNavigation.fxml'."; // NOI18N
        
        this.initializeNavigationLeft();
        
        this.refreshDreamBook();
    }
    
    private void initializeNavigationLeft() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize navigation left"); // NOI18N
        
        lvNavigation.getStylesheets().addAll(this.getClass().getResource("DreamBookNavigation.css").toExternalForm()); // NOI18N
        lvNavigation.getItems().clear();
        
        lvNavigation.setCellFactory(new Callback<ListView<DreamBookNavigationModel>, ListCell<DreamBookNavigationModel>>() {

            @Override
            public ListCell<DreamBookNavigationModel> call(ListView<DreamBookNavigationModel> param) {
                return new ListCell<DreamBookNavigationModel>() {
                    @Override
                    public void updateItem(DreamBookNavigationModel item, boolean empty) {
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
                final DreamBookNavigationModel model = (DreamBookNavigationModel) lvNavigation.getSelectionModel().getSelectedItem();
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
    
    private void refreshDreamBook() {
        final PauseTransition pt = new PauseTransition();
        pt.setDuration(REFRESH_AFTER_100_MILLIS);
        pt.setOnFinished((ActionEvent event) -> {
            this.refresh();
        });
        
        pt.playFromStart();
    }

    public void refresh() {
        Platform.runLater(() -> {
            LoggerFacade.getDefault().info(this.getClass(), "Load navigation for DreamBook"); // NOI18N

            final List<DreamBookNavigationModel> models = FXCollections.observableArrayList();
            final List<DreamModel> dreams = SqlProvider.getDefault().getDreamBookNavigationSqlProvider().findAllDreams();
            for (DreamModel dream : dreams) {
                final DreamBookNavigationModel model = new DreamBookNavigationModel();
                model.setActionKey(ACTION__OPEN_DREAM__FROM_NAVIGATION);
                model.setIdToOpen(dream.getId());
                model.setGenerationTime(dream.getGenerationTime());
                
                final DreamElementView view = new DreamElementView();
                final DreamElementPresenter presenter = view.getRealPresenter();
                presenter.configure(model.hasPrefixNew(), dream.getGenerationTime(), dream.getTitle());
                model.setView(view.getView());
                
                models.add(model);
            }
            
            final List<ReflectionModel> reflections = SqlProvider.getDefault().getDreamBookNavigationSqlProvider().findAllReflections();
            for (ReflectionModel reflection : reflections) {
                final DreamBookNavigationModel model = new DreamBookNavigationModel();
                model.setActionKey(ACTION__OPEN_REFLECTION__FROM_NAVIGATION);
                model.setIdToOpen(reflection.getId());
                model.setGenerationTime(reflection.getGenerationTime());
                
                final ReflectionElementView view = new ReflectionElementView();
                final ReflectionElementPresenter presenter = view.getRealPresenter();
                presenter.configure(model.hasPrefixNew(), reflection.getGenerationTime(), reflection.getTitle());
                model.setView(view.getView());
                
                models.add(model);
            }
            
            Collections.sort(models);
            
            lvNavigation.getItems().clear();
            lvNavigation.getItems().addAll(models);
        });
    }

    @Override
    public void registerActions() {
        LoggerFacade.getDefault().debug(this.getClass(), "Register actions in DreamBookNavigationPresenter"); // NOI18N
        
        this.registerOnActionRefreshDreamBookNavigation();
    }

    private void registerOnActionRefreshDreamBookNavigation() {
        ActionFacade.getDefault().register(
                ACTION__REFRESH_NAVIGATION__DREAMBOOK,
                (ActionEvent ae) -> {
                    this.refresh();
                });
    }
    
    public ObservableList<DreamBookNavigationModel> getItems() {
        return lvNavigation.getItems();
    }
    
}
