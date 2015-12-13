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
package de.pro.dbw.application.performance;

import com.airhacks.afterburner.views.FXMLView;
import de.pro.dbw.application.performance.entity.dream.DreamView;
import de.pro.dbw.application.performance.entity.reflection.ReflectionView;
import de.pro.dbw.application.performance.entity.tipofthenight.TipOfTheNightView;
import de.pro.dbw.base.component.api.listview.checkbox.CheckBoxListCell;
import de.pro.dbw.base.component.api.listview.checkbox.CheckBoxListCellModel;
import de.pro.dbw.file.dream.api.DreamModel;
import de.pro.dbw.file.reflection.api.ReflectionModel;
import de.pro.dbw.file.tipofthenight.api.TipOfTheNightModel;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 *
 * @author PRo
 */
public class PerformancePresenter implements Initializable {
    private static final String ENTITY_SUFFIX = "Model"; // NOI18N
    
    private static final Map<String, FXMLView> ENTITIES = FXCollections.observableHashMap();
    
    @FXML private CheckBox cbSelectAll;
    @FXML private ListView lvEntities;
    @FXML private TabPane tpEntities;
    @FXML private TabPane tpEntitiesInEditor;
    
    private final BooleanProperty disableProperty = new SimpleBooleanProperty(Boolean.FALSE);

    private ResourceBundle resources = null;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        
        assert (cbSelectAll != null)      : "fx:id=\"cbSelectAll\" was not injected: check your FXML file 'PerformancePresenter.fxml'."; // NOI18N
        assert (lvEntities != null)       : "fx:id=\"lvEntities\" was not injected: check your FXML file 'PerformancePresenter.fxml'."; // NOI18N
        assert (tpEntities != null)       : "fx:id=\"tpEntities\" was not injected: check your FXML file 'PerformancePresenter.fxml'."; // NOI18N
        
        this.initializeDesktopSplitPane();
        this.initializeEntities();
        this.initializeListView();
        
        this.onActionRefresh();
    }
    
    private void initializeDesktopSplitPane() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize desktop SplitPane"); // NOI18N
    
        SplitPane.setResizableWithParent(tpEntities, Boolean.FALSE);
    }
    
    private void initializeEntities() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Register entities"); // NOI18N
        
        final DreamView dreamView = new DreamView();
        dreamView.getView().setId(DreamModel.class.getSimpleName());
        dreamView.getRealPresenter().bind(disableProperty);
        ENTITIES.put(DreamModel.class.getSimpleName(), dreamView);
        
        final ReflectionView reflectionView = new ReflectionView();
        reflectionView.getView().setId(ReflectionModel.class.getSimpleName());
        reflectionView.getRealPresenter().bind(disableProperty);
        ENTITIES.put(ReflectionModel.class.getSimpleName(), reflectionView);
        
        final TipOfTheNightView tipOfTheNightView = new TipOfTheNightView();
        tipOfTheNightView.getView().setId(TipOfTheNightModel.class.getSimpleName());
        tipOfTheNightView.getRealPresenter().bind(disableProperty);
        ENTITIES.put(TipOfTheNightModel.class.getSimpleName(), tipOfTheNightView);
    }
    
    private void initializeListView() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize ListView in TestDataPresenter"); // NOI18N

//        lvEntities.getStylesheets().addAll(this.getClass().getResource(CSS__TAG_CATEGORY_CHOOSER).toExternalForm());
//        lvEntities.getItems().clear();

        lvEntities.setCellFactory(CheckBoxListCell.forListView(CheckBoxListCellModel::selectedProperty, disableProperty));
    }
    
    private CheckBoxListCellModel getCheckBoxListCellModel(final String key) {
        final CheckBoxListCellModel model = new CheckBoxListCellModel();
        model.setName(key.substring(0, key.length() - ENTITY_SUFFIX.length()));
        model.setId(key);
        
        model.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            // Selected
            if (newValue) {
                final Tab tab = new Tab();
                tab.setClosable(false);
                tab.setContent(ENTITIES.get(key).getView());
                tab.setId(key);
                tab.setText("Entity: " + model.getName()); // NOI18N
                tpEntitiesInEditor.getTabs().add(tab);
                
                tpEntitiesInEditor.getSelectionModel().select(tab);
                
                return;
            }
            
            // Don't selected
            for (Tab tab : tpEntitiesInEditor.getTabs()) {
                if (tab.getId().equals(key)) {
                    tpEntitiesInEditor.getTabs().remove(tab);
                    return;
                }
            }
        });
        
        // TODO disable?
        
        return model;
    }
    
    private void onActionRefresh() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Refresh"); // NOI18N
       
        Platform.runLater(() -> {
            this.onActionRefreshNavigationArea();
            this.onActionRefreshEditorArea();
        });
    }
    
    private void onActionRefreshEditorArea() {
        LoggerFacade.INSTANCE.info(this.getClass(), "On action refresh Editor area"); // NOI18N
        
        tpEntitiesInEditor.getTabs().clear();
    }
    
    private void onActionRefreshNavigationArea() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action refresh Navigation area"); // NOI18N
       
        lvEntities.getItems().clear();

        final List<CheckBoxListCellModel> models = FXCollections.observableArrayList();
        ENTITIES.keySet().stream().forEach((key) -> {
            models.add(this.getCheckBoxListCellModel(key));
        });

        lvEntities.getItems().addAll(models);
    }
    
    public void onActionSelectAll(ActionEvent ae) {
        LoggerFacade.INSTANCE.info(this.getClass(), "On action select all"); // NOI18N
        
        if (!(ae.getSource() instanceof CheckBox)) {
            return;
        }
        
        final CheckBox checkBox = (CheckBox) ae.getSource();
        final Boolean isSelected = checkBox.isSelected();
        for (Object item : lvEntities.getItems()) {
            if (!(item instanceof CheckBoxListCellModel)) {
                continue;
            }
            
            final CheckBoxListCellModel model = (CheckBoxListCellModel) item;
            model.setSelected(isSelected);
        }
    }

    public void shutdown() {
        
    }
    
}
