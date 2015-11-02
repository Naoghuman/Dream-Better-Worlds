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
package de.pro.dbw.application.testdata;

import com.airhacks.afterburner.views.FXMLView;
import de.pro.dbw.application.testdata.entity.dream.DreamPresenter;
import de.pro.dbw.application.testdata.entity.dream.DreamView;
import de.pro.dbw.application.testdata.entity.tipofthenight.TipOfTheNightPresenter;
import de.pro.dbw.application.testdata.entity.tipofthenight.TipOfTheNightView;
import de.pro.dbw.application.testdata.listview.checkbox.CheckBoxListCellModel;
import de.pro.dbw.application.testdata.service.DreamService;
import de.pro.dbw.application.testdata.service.SequentialThreadFactory;
import de.pro.dbw.application.testdata.service.TipOfTheNightService;
import de.pro.dbw.core.configuration.api.application.testdata.ITestdataConfiguration;
import de.pro.dbw.file.dream.api.DreamModel;
import de.pro.dbw.file.tipofthenight.api.TipOfTheNightModel;
import de.pro.lib.database.api.DatabaseFacade;
import de.pro.lib.logger.api.LoggerFacade;
import de.pro.lib.properties.api.PropertiesFacade;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 *
 * @author PRo
 */
public class TestdataPresenter implements Initializable, ITestdataConfiguration {
    
    private static final String ENTITY_SUFFIX = "Model"; // NOI18N
    
    private static final Map<String, FXMLView> ENTITIES = FXCollections.observableHashMap();
    
    @FXML private Button bCreateTestdata;
    @FXML private AnchorPane apDialogLayer;
    @FXML private CheckBox cbDeleteDatabase;
    @FXML private ListView lvEntities;
    @FXML private TabPane tpEntities;
    @FXML private TabPane tpTestdata;
    @FXML private VBox vbEntities;
    
    private ExecutorService parallelExecutorService;
    private ExecutorService sequentialExecutorService;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize TestDataPresenter");
    
        assert (bCreateTestdata != null)  : "fx:id=\"bCreateTestdata\" was not injected: check your FXML file 'TestdataPresenter.fxml'."; // NOI18N
        assert (apDialogLayer != null)    : "fx:id=\"apDialogLayer\" was not injected: check your FXML file 'TestdataPresenter.fxml'."; // NOI18N
        assert (cbDeleteDatabase != null) : "fx:id=\"cbDeleteDatabase\" was not injected: check your FXML file 'TestdataPresenter.fxml'."; // NOI18N
        assert (lvEntities != null)       : "fx:id=\"lvEntities\" was not injected: check your FXML file 'TestdataPresenter.fxml'."; // NOI18N
        assert (tpEntities != null)       : "fx:id=\"tpEntities\" was not injected: check your FXML file 'TestdataPresenter.fxml'."; // NOI18N
        assert (tpTestdata != null)       : "fx:id=\"tpTestdata\" was not injected: check your FXML file 'TestdataPresenter.fxml'."; // NOI18N
        assert (vbEntities != null)       : "fx:id=\"vbEntities\" was not injected: check your FXML file 'TestdataPresenter.fxml'."; // NOI18N
    
        this.initializeDesktopSplitPane();
        this.initializeDialogLayer();
        this.initializeEntities();
        this.initializeListView();
        this.initializeProcesses();
        
        this.onActionRefresh();
    }
    
    private void initializeDesktopSplitPane() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize desktop SplitPane"); // NOI18N
    
        SplitPane.setResizableWithParent(tpEntities, Boolean.FALSE);
    }
    
    private void initializeDialogLayer() {
        apDialogLayer.setVisible(Boolean.FALSE);
        apDialogLayer.setManaged(Boolean.FALSE);
    }
    
    private void initializeEntities() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Register entities"); // NOI18N
        
        final DreamView dreamView = new DreamView();
        dreamView.getView().setId(DreamModel.class.getSimpleName());
        ENTITIES.put(DreamModel.class.getSimpleName(), dreamView);
        
        final TipOfTheNightView tipOfTheNightView = new TipOfTheNightView();
        tipOfTheNightView.getView().setId(TipOfTheNightModel.class.getSimpleName());
        ENTITIES.put(TipOfTheNightModel.class.getSimpleName(), tipOfTheNightView);
    }
    
    private void initializeListView() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize ListView in TestDataPresenter"); // NOI18N

//        lvEntities.getStylesheets().addAll(this.getClass().getResource(CSS__TAG_CATEGORY_CHOOSER).toExternalForm());
//        lvEntities.getItems().clear();

        lvEntities.setCellFactory(CheckBoxListCell.forListView(CheckBoxListCellModel::selectedProperty));
    }
    
    private void initializeProcesses() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize Processes"); // NOI18N
        
        sequentialExecutorService = Executors.newFixedThreadPool(1, new SequentialThreadFactory());
    }
    
    private CheckBoxListCellModel getCheckBoxListCellModel(final String key) {
        final CheckBoxListCellModel model = new CheckBoxListCellModel();
        model.setName(key.substring(0, key.length() - ENTITY_SUFFIX.length()));
        model.setId(key);
        model.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            // Selected
            if (newValue) {
                vbEntities.getChildren().add(ENTITIES.get(key).getView());
                return;
            }
            
            // Don't selected
            for (Node child : vbEntities.getChildren()) {
                if (!(child instanceof Parent)) {
                    continue;
                }

                final Parent entityToRemove = ENTITIES.get(key).getView();
                final Parent entity = (Parent) child;
                if (entity.getId().equals(entityToRemove.getId())) {
                    vbEntities.getChildren().remove(entityToRemove);
                    return;
                }
            }
        });
        
        model.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            // Selected
            if (newValue) {
                bCreateTestdata.setDisable(Boolean.FALSE);
                return;
            }
            
            // Don't selected
            boolean disable = Boolean.TRUE;
            for (Object item : lvEntities.getItems()) {
                if (!(item instanceof CheckBoxListCellModel)) {
                    continue;
                }

                final CheckBoxListCellModel checkBoxListCellModel = (CheckBoxListCellModel) item;
                if (checkBoxListCellModel.isSelected()) {
                    disable = Boolean.FALSE;
                    break;
                }
            }

            bCreateTestdata.setDisable(disable);
        });
        
        return model;
    }
    
    private String getProperty(String propertyKey) {
        return PropertiesFacade.INSTANCE.getProperty(DBW__RESOURCE_BUNDLE__TESTDATA, propertyKey);
    }
    
    public void onActionCreateTestdata() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action create Testdata"); // NOI18N

        final SequentialTransition sequentialTransition = new SequentialTransition();
        if (cbDeleteDatabase.isSelected()) {
            final PauseTransition ptDropDatabase = new PauseTransition();
            ptDropDatabase.setDuration(Duration.ZERO);
            ptDropDatabase.setOnFinished((ActionEvent event) -> {
                LoggerFacade.INSTANCE.debug(this.getClass(), "Drop database"); // NOI18N
                DatabaseFacade.INSTANCE.drop(this.getProperty(KEY__APPLICATION__DATABASE));
            });
            sequentialTransition.getChildren().add(ptDropDatabase);
        }
        
        final PauseTransition ptRegisterDatabase = new PauseTransition();
        ptRegisterDatabase.setDuration(Duration.millis(250.0d));
        ptRegisterDatabase.setOnFinished((ActionEvent event) -> {
            LoggerFacade.INSTANCE.debug(this.getClass(), "Register database"); // NOI18N
            DatabaseFacade.INSTANCE.register(this.getProperty(KEY__APPLICATION__DATABASE));
        });
        sequentialTransition.getChildren().add(ptRegisterDatabase);
        
        final PauseTransition ptCreateTestdata = new PauseTransition();
        ptCreateTestdata.setDuration(Duration.millis(500.0d));
        ptCreateTestdata.setOnFinished((ActionEvent event) -> {
            this.startTestdataGeneration();
        });
        sequentialTransition.getChildren().add(ptCreateTestdata);
        
        sequentialTransition.playFromStart();
    }
    
    private void onActionRefresh() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Refresh"); // NOI18N
       
        Platform.runLater(() -> {
            this.onActionRefreshListView();
            this.onActionRefreshTabTestdata();
        });
    }
    
    private void onActionRefreshListView() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action refresh ListView"); // NOI18N
       
        lvEntities.getItems().clear();

        final List<CheckBoxListCellModel> models = FXCollections.observableArrayList();
        ENTITIES.keySet().stream().forEach((key) -> {
            models.add(this.getCheckBoxListCellModel(key));
        });

        lvEntities.getItems().addAll(models);
    }
    
    private void onActionRefreshTabTestdata() {
        LoggerFacade.INSTANCE.info(this.getClass(), "On action refresh tab TestData"); // NOI18N
        
        vbEntities.getChildren().clear();
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
    
    public void shutdown() throws InterruptedException {
        sequentialExecutorService.shutdown();
        sequentialExecutorService.awaitTermination(2, TimeUnit.SECONDS);
    }
    
    public void startTestdataGeneration() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Start with Testdata generation..."); // NOI18N
//        LoggerFacade.INSTANCE.deactivate(Boolean.TRUE); // XXX move to xy-service
        
//        parallelExecutorService = Executors.newFixedThreadPool(2, new ParallelThreadFactory());
        
        for (Object item : lvEntities.getItems()) {
            if (!(item instanceof CheckBoxListCellModel)) {
                continue;
            }

            final CheckBoxListCellModel checkBoxListCellModel = (CheckBoxListCellModel) item;
            if (!checkBoxListCellModel.isSelected()) {
                continue;
            }
            
            this.configureServiceForEntityDream(checkBoxListCellModel.getId());
            this.configureServiceForEntityTipOfTheNight(checkBoxListCellModel.getId());
        }
        
//        LoggerFacade.INSTANCE.deactivate(Boolean.FALSE); // XXX move to xy-service
        LoggerFacade.INSTANCE.debug(this.getClass(), "Ready with Testdata generation..."); // NOI18N
    }

    private void configureServiceForEntityDream(String id) {
        if (id.equals(DreamModel.class.getSimpleName())) {
            return;
        }
            
        final DreamService service = new DreamService(DreamModel.class.getName());
        final DreamPresenter presenter = (DreamPresenter) ENTITIES.get(DreamModel.class.getSimpleName()).getPresenter();
        service.bind(presenter);
        service.setExecutor(sequentialExecutorService);
        service.setOnStart("Start with testdata generation from entity Dream..."); // NOI18N
        service.setOnSucceeded("Ready with testdata generation from entity Dream..."); // NOI18N
        
        service.start();
    }
    
    private void configureServiceForEntityTipOfTheNight(String id) {
        if (id.equals(TipOfTheNightModel.class.getSimpleName())) {
            return;
        }
          
        final TipOfTheNightService service = new TipOfTheNightService(TipOfTheNightModel.class.getName());
        final TipOfTheNightPresenter presenter = (TipOfTheNightPresenter) ENTITIES.get(TipOfTheNightModel.class.getSimpleName()).getPresenter();
        service.bind(presenter);
        service.setExecutor(sequentialExecutorService);
        service.setOnStart("Start with testdata generation from entity TipOfTheNight..."); // NOI18N
        service.setOnSucceeded("Ready with testdata generation from entity TipOfTheNight..."); // NOI18N
        
        service.start();
    }
    
}
