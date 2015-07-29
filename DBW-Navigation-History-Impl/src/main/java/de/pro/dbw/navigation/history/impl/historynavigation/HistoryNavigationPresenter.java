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
package de.pro.dbw.navigation.history.impl.historynavigation;

import de.pro.dbw.core.configuration.api.application.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.application.action.IRegisterActions;
import de.pro.dbw.core.configuration.api.file.dream.IDreamConfiguration;
import de.pro.dbw.core.configuration.api.navigation.INavigationConfiguration;
import de.pro.dbw.navigation.history.api.HistoryNavigationModel;
import de.pro.dbw.navigation.history.impl.listview.dreamelement.DreamElementPresenter;
import de.pro.dbw.navigation.history.impl.listview.dreamelement.DreamElementView;
import de.pro.dbw.navigation.history.impl.listview.parentelement.ParentElementPresenter;
import de.pro.dbw.navigation.history.impl.listview.parentelement.ParentElementView;
import de.pro.dbw.file.dream.api.DreamModel;
import de.pro.dbw.core.sql.provider.SqlProvider;
import de.pro.dbw.file.reflection.api.ReflectionModel;
import de.pro.dbw.navigation.history.impl.listview.childelement.ChildElementPresenter;
import de.pro.dbw.navigation.history.impl.listview.childelement.ChildElementView;
import de.pro.dbw.navigation.history.impl.listview.reflectionelement.ReflectionElementPresenter;
import de.pro.dbw.navigation.history.impl.listview.reflectionelement.ReflectionElementView;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.action.api.ActionTransferModel;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * TODO the prefix (if show) shouldn't resize.
 * TODO show corret info (xy dreams, ry reflections... and add animation
 *
 * @author PRo
 */
public class HistoryNavigationPresenter implements Initializable, IActionConfiguration,
        IDreamConfiguration, INavigationConfiguration, IRegisterActions
{
    private static final String CSS__HISTORY_NAVIGATION = "HistoryNavigation.css"; // NOI18N
    
    @FXML private Label lDreamCount;
    @FXML private ListView lvNavigation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.getLogger().info(this.getClass(), "Initialize HistoryNavigationPresenter");
        
        assert (lDreamCount != null)       : "fx:id=\"lDreamCount\" was not injected: check your FXML file 'HistoryNavigation.fxml'."; // NOI18N
        assert (lvNavigation != null)      : "fx:id=\"lvNavigation\" was not injected: check your FXML file 'HistoryNavigation.fxml'."; // NOI18N
        
        this.initializeHistoryListView();
        
        this.refreshHistory();
    }
    
    private void initializeHistoryListView() {
        LoggerFacade.INSTANCE.getLogger().info(this.getClass(), "Initialize navigation history"); // NOI18N
        
        lvNavigation.getStylesheets().addAll(this.getClass().getResource(CSS__HISTORY_NAVIGATION).toExternalForm());
        lvNavigation.getItems().clear();
        
        lvNavigation.setCellFactory(new Callback<ListView<HistoryNavigationModel>, ListCell<HistoryNavigationModel>>() {

            @Override
            public ListCell<HistoryNavigationModel> call(ListView<HistoryNavigationModel> param) {
                return new ListCell<HistoryNavigationModel>() {
                    @Override
                    public void updateItem(HistoryNavigationModel item, boolean empty) {
                        super.updateItem(item, empty);
                        
                        this.setText(null);
                        this.setGraphic(item == null ? null : item.getView());
                    }
                };
            }
        });
        
        lvNavigation.setOnMouseClicked((MouseEvent event) -> {
            if (lvNavigation.getItems().isEmpty()) {
                return;
            }
            
            if (
                    event.getButton().equals(MouseButton.PRIMARY)
                    && event.getClickCount() >= 2
            ) {
                final HistoryNavigationModel model = (HistoryNavigationModel) lvNavigation.getSelectionModel().getSelectedItem();
                final ActionTransferModel transferModel = new ActionTransferModel();
                transferModel.setActionKey(model.getActionKey());
                transferModel.setLong(model.getIdToOpen());
                
                ActionFacade.INSTANCE.getAction().handle(transferModel);
            }
        });
    }
    
    private List<ChildElementView> createChildElements(List<DreamModel> dreamModels, List<ReflectionModel> reflectionModels) {
        final List<ChildElementView> childElementViews = FXCollections.observableArrayList();
        for (DreamModel dreamModel : dreamModels) {
            final ChildElementView childElementView = new ChildElementView();
            final ChildElementPresenter childElementPresenter = childElementView.getRealPresenter();
            
            final DreamElementView dreamElementView = new DreamElementView();
            final DreamElementPresenter dreamElementPresenter = dreamElementView.getRealPresenter();
            dreamElementPresenter.configure(dreamModel.getGenerationTime(), dreamModel.getTitle(), dreamModel.getId());
            
            childElementPresenter.configure(
                    dreamElementView.getView(), dreamModel.getTitle(),
                    dreamModel.getGenerationTime(), dreamModel.getId(),
                    ACTION__OPEN_DREAM__FROM_NAVIGATION);
            childElementViews.add(childElementView);
        }
        
        for (ReflectionModel reflectionModel : reflectionModels) {
            final ChildElementView childElementView = new ChildElementView();
            final ChildElementPresenter childElementPresenter = childElementView.getRealPresenter();
            
            final ReflectionElementView reflectionElementView = new ReflectionElementView();
            final ReflectionElementPresenter reflectionElementPresenter = reflectionElementView.getRealPresenter();
            reflectionElementPresenter.configure(reflectionModel.getGenerationTime(), reflectionModel.getTitle(), reflectionModel.getId());
            
            childElementPresenter.configure(
                    reflectionElementView.getView(), reflectionModel.getTitle(),
                    reflectionModel.getGenerationTime(), reflectionModel.getId(),
                    ACTION__OPEN_REFLECTION__FROM_NAVIGATION);
            childElementViews.add(childElementView);
        }
        
        Collections.sort(childElementViews);
        
        return childElementViews;
    }
    
    private List<ParentElementView> createParentElements(List<ChildElementView> childElementViews){
        final List<ParentElementView> parentElementViews = FXCollections.observableArrayList();
        for (ChildElementView childElementView : childElementViews) {
            if (parentElementViews.isEmpty()) {
                final ParentElementView parentElementView = new ParentElementView();
                final ParentElementPresenter parentElementPresenter = parentElementView.getRealPresenter();
                final Long minGenerationTime = Math.min(parentElementPresenter.getGenerationTime(), childElementView.getRealPresenter().getGenerationTime());
                parentElementPresenter.configure(minGenerationTime, childElementView.getRealPresenter().getDate());
                
                parentElementViews.add(parentElementView);
                continue;
            }
            
            boolean isDateAdded = Boolean.FALSE;
            for (ParentElementView parentElementView : parentElementViews) {
                if (parentElementView.getRealPresenter().getDate().equals(childElementView.getRealPresenter().getDate())) {
                    isDateAdded = Boolean.TRUE;
                    break;
                }
            }

            if (!isDateAdded) {
                final ParentElementView parentElementView = new ParentElementView();
                final ParentElementPresenter parentElementPresenter = parentElementView.getRealPresenter();
                final Long minGenerationTime = Math.min(parentElementPresenter.getGenerationTime(), childElementView.getRealPresenter().getGenerationTime());
                parentElementPresenter.configure(minGenerationTime, childElementView.getRealPresenter().getDate());

                parentElementViews.add(parentElementView);
            }
        }
        
        Collections.sort(parentElementViews);
        
        return parentElementViews;
    }
    
    public ObservableList<HistoryNavigationModel> getItems() {
        return lvNavigation.getItems();
    }
    
    private void refreshHistory() {
        final PauseTransition pt = new PauseTransition();
        pt.setDuration(REFRESH_AFTER_100_MILLIS);
        pt.setOnFinished((ActionEvent event) -> {
            this.refresh();
        });
        
        pt.playFromStart();
    }
    
    public void refresh() {
        LoggerFacade.INSTANCE.getLogger().info(this.getClass(), "Refresh History-Navigation");
        
        // Load data from db
        final List<DreamModel> dreamModels = SqlProvider.getDefault().getHistoryNavigationSqlProvider().findAllDreams(-30);
        final List<ReflectionModel> reflectionModels = SqlProvider.getDefault().getHistoryNavigationSqlProvider().findAllReflections(-30);
        if (
                dreamModels.isEmpty()
                && reflectionModels.isEmpty()
        ) {
            lvNavigation.getItems().clear();
            return;
        }
        
        // Create child and parent elements
        final List<ChildElementView> childElementViews = this.createChildElements(dreamModels, reflectionModels);
        final List<ParentElementView> parentElementViews = this.createParentElements(childElementViews);
        this.showElementsInListView(parentElementViews, childElementViews);
        
        // Refresh info
        this.refreshInfoLabel(-1);//dreams.size());
    }
    
    private void refreshInfoLabel(int dreamSize) {
        // TODO design the message new (rolling text?)
        String dreamCount = "zero"; // NOI18N
        String dreamSuffix = "s"; // NOI18N
        if (dreamSize == 1) {
            dreamCount = "1"; // NOI18N
            dreamSuffix = ""; // NOI18N
        }
        if (dreamSize >= 2) {
            dreamCount = String.valueOf(dreamSize);
        }
        
        lDreamCount.setText(dreamCount + " dream" + dreamSuffix); // NOI18N
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.getLogger().debug(this.getClass(), "Register actions in HistoryNavigationPresenter"); // NOI18N
        
        this.registerOnActionRefreshHistoryNavigation();
    }

    private void registerOnActionRefreshHistoryNavigation() {
        ActionFacade.INSTANCE.getAction().register(
                ACTION__REFRESH_NAVIGATION__HISTORY,
                (ActionEvent ae) -> {
                    this.refresh();
                });
    }
    
    private void showElementsInListView(List<ParentElementView> parentElementViews, List<ChildElementView> childElementViews) {
        lvNavigation.getItems().clear();
        
        for (ParentElementView parentView : parentElementViews) {
            final HistoryNavigationModel parentModel = new HistoryNavigationModel();
            parentModel.setView(parentView.getView());
            lvNavigation.getItems().add(parentModel);

            int size = 0;
            for (ChildElementView childView : childElementViews) {
                final ChildElementPresenter childPresenter = childView.getRealPresenter();
                if (!parentView.getRealPresenter().getDate().equals(childPresenter.getDate())) {
                    continue;
                }
                
                final HistoryNavigationModel childModel = new HistoryNavigationModel();
                childModel.setActionKey(childPresenter.getActionKey());
                childModel.setGenerationTime(childPresenter.getGenerationTime());
                childModel.setIdToOpen(childPresenter.getIdToOpen());
                childModel.setView(childView.getView());
                
                lvNavigation.getItems().add(childModel);
                ++size;
            }
            
            final ParentElementPresenter parentElementPresenter = parentView.getRealPresenter();
            parentElementPresenter.setSize(size);
        }
    }
    
}
