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

import de.pro.dbw.core.configuration.api.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.file.dream.IDreamConfiguration;
import de.pro.dbw.core.configuration.api.navigation.INavigationConfiguration;
import de.pro.dbw.navigation.history.api.HistoryNavigationModel;
import de.pro.dbw.navigation.history.impl.listview.childdreamelement.ChildDreamElementPresenter;
import de.pro.dbw.navigation.history.impl.listview.childdreamelement.ChildDreamElementView;
import de.pro.dbw.navigation.history.impl.listview.parentelement.ParentElementPresenter;
import de.pro.dbw.navigation.history.impl.listview.parentelement.ParentElementView;
import de.pro.dbw.file.dream.api.DreamModel;
import de.pro.dbw.core.sql.provider.SqlProvider;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.action.api.ActionTransferModel;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
 *
 * @author PRo
 */
public class HistoryNavigationPresenter 
    implements Initializable, IActionConfiguration,
        IDreamConfiguration, INavigationConfiguration
{
    @FXML private Label lDreamCount;
    @FXML private ListView lvNavigation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize HistoryNavigationPresenter");
        
        assert (lDreamCount != null)       : "fx:id=\"lDreamCount\" was not injected: check your FXML file 'HistoryNavigation.fxml'."; // NOI18N
        assert (lvNavigation != null)      : "fx:id=\"lvNavigation\" was not injected: check your FXML file 'HistoryNavigation.fxml'."; // NOI18N
        
        this.initializeHistoryListView();
        this.registerOnActionUpdateNavigationHistory();
        this.refreshHistory();
    }
    
    private void initializeHistoryListView() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize navigation history"); // NOI18N
        
        lvNavigation.getStylesheets().addAll(this.getClass().getResource("HistoryNavigation.css").toExternalForm()); // NOI18N
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
                
                ActionFacade.getDefault().handle(transferModel);
            }
        });
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
        LoggerFacade.getDefault().info(this.getClass(), "Refresh History-Navigation");

        // Load all dreams
        final List<DreamModel> dreams = SqlProvider.getDefault().getHistoryNavigationSqlProvider().findAll(-30);
        if (dreams.isEmpty()) {
            lvNavigation.getItems().clear();
            return;
        }

        // Create for every dream a view
        final List<ChildDreamElementView> cdeViews = FXCollections.observableArrayList();
        for (DreamModel dream : dreams) {
            final ChildDreamElementView cdeView = new ChildDreamElementView();
            final ChildDreamElementPresenter cdePresenter = cdeView.getRealPresenter();
            cdePresenter.configure(dream.getGenerationTime(), dream.getTitle(), dream.getId());

            cdeViews.add(cdeView);
        }
        Collections.sort(cdeViews);

        // Create a day entry
        final List<ParentElementView> peViews = FXCollections.observableArrayList();
        for (ChildDreamElementView cdeView : cdeViews) {
            if (peViews.isEmpty()) {
                final ParentElementView peView = new ParentElementView();
                final ParentElementPresenter pePresenter = peView.getRealPresenter();
                final Long minGenerationTime = Math.min(pePresenter.getGenerationTime(), cdeView.getRealPresenter().getGenerationTime());
                pePresenter.configure(minGenerationTime, cdeView.getRealPresenter().getDate());
                
                peViews.add(peView);
                continue;
            }
            
            boolean isDateAdded = Boolean.FALSE;
            for (ParentElementView peView : peViews) {
                if (peView.getRealPresenter().getDate().equals(cdeView.getRealPresenter().getDate())) {
                    isDateAdded = Boolean.TRUE;
                    break;
                }
            }

            if (!isDateAdded) {
                final ParentElementView peView = new ParentElementView();
                final ParentElementPresenter pePresenter = peView.getRealPresenter();
                final Long minGenerationTime = Math.min(pePresenter.getGenerationTime(), cdeView.getRealPresenter().getGenerationTime());
                pePresenter.configure(minGenerationTime, cdeView.getRealPresenter().getDate());

                peViews.add(peView);
            }
        }
        Collections.sort(peViews);
        
        // Create mapping
        final Map<ParentElementView, List<ChildDreamElementView>> mappedElements = FXCollections.observableHashMap();
        for (ParentElementView peView : peViews) {
            final List<ChildDreamElementView> mappedChildDreamViews = FXCollections.observableArrayList();
            for (ChildDreamElementView cdeView : cdeViews) {
                if (peView.getRealPresenter().getDate().equals(cdeView.getRealPresenter().getDate())) {
                    mappedChildDreamViews.add(cdeView);
                }
            }

            peView.getRealPresenter().setSize(mappedChildDreamViews.size());
            mappedElements.put(peView, mappedChildDreamViews);
        }
          
        // Refresh history
        lvNavigation.getItems().clear();
        
        for (ParentElementView peView : peViews) {
            final HistoryNavigationModel hnParentModel = new HistoryNavigationModel();
            hnParentModel.setView(peView.getView());
            lvNavigation.getItems().add(hnParentModel);

            final List<ChildDreamElementView> mappedChildDreamViews = mappedElements.get(peView);
            for (ChildDreamElementView mappedChildDreamView : mappedChildDreamViews) {
                final HistoryNavigationModel hnChildModel = new HistoryNavigationModel();
                hnChildModel.setActionKey(ACTION__OPEN_DREAM__FROM_NAVIGATION);
                hnChildModel.setGenerationTime(mappedChildDreamView.getRealPresenter().getGenerationTime());
                hnChildModel.setIdToOpen(mappedChildDreamView.getRealPresenter().getIdToOpen());
                hnChildModel.setView(mappedChildDreamView.getView());
                lvNavigation.getItems().add(hnChildModel);
            }
        }

        // Refresh info label
        this.refreshInfoLabel(dreams.size());
    }
    
    private void refreshInfoLabel(int dreamSize) {
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

    private void registerOnActionUpdateNavigationHistory() {
        ActionFacade.getDefault().register(ACTION__REFRESH_NAVIGATION__HISTORY,
                (ActionEvent ae) -> {
                    this.refresh();
                });
    }
    
    public ObservableList<HistoryNavigationModel> getItems() {
        return lvNavigation.getItems();
    }
    
}
