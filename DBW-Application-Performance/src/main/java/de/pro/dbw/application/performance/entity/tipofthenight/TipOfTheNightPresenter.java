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
package de.pro.dbw.application.performance.entity.tipofthenight;

import de.pro.dbw.application.performance.helper.EntityHelper;
import de.pro.dbw.application.performance.helper.PerformanceHelper;
import de.pro.dbw.core.configuration.api.application.preferences.IPreferencesConfiguration;
import de.pro.dbw.file.provider.FileProvider;
import de.pro.dbw.file.tipofthenight.impl.tipofthenightchooser.TipOfTheNightChooserPresenter;
import de.pro.dbw.file.tipofthenight.impl.tipofthenightchooser.TipOfTheNightChooserView;
import de.pro.dbw.util.api.IDateConverter;
import de.pro.dbw.util.provider.UtilProvider;
import de.pro.lib.logger.api.LoggerFacade;
import de.pro.lib.preferences.api.PreferencesFacade;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 *
 * @author PRo
 */
public class TipOfTheNightPresenter implements Initializable, IPreferencesConfiguration {

    @FXML private Button bPerformanceCheckForChooserNext;
    @FXML private Button bPerformanceCheckForChooserRandom;
    @FXML private ComboBox cbIntervalForChooserNext;
    @FXML private ComboBox cbIntervalForChooserRandom;
    @FXML private ComboBox cbRepeatForChooserNext;
    @FXML private ComboBox cbRepeatForChooserRandom;
    @FXML private FlowPane fpChooser;
    @FXML private Label lInfoForChooserNext;
    @FXML private Label lInfoForChooserRandom;
    @FXML private VBox vbEditor;
    
    private TipOfTheNightChooserPresenter tipOfTheNightChooserPresenter;
    private TipOfTheNightChooserView tipOfTheNightChooserView;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize TipOfTheNightPresenter"); // NOI18N
        
        assert (bPerformanceCheckForChooserNext != null)   : "fx:id=\"bPerformanceCheckForChooserNext\" was not injected: check your FXML file 'TipOfTheNight.fxml'."; // NOI18N
        assert (bPerformanceCheckForChooserRandom != null) : "fx:id=\"bPerformanceCheckForChooserRandom\" was not injected: check your FXML file 'TipOfTheNight.fxml'."; // NOI18N
        assert (cbIntervalForChooserNext != null)   : "fx:id=\"cbIntervalForChooserNext\" was not injected: check your FXML file 'TipOfTheNight.fxml'."; // NOI18N
        assert (cbIntervalForChooserRandom != null) : "fx:id=\"cbIntervalForChooserRandom\" was not injected: check your FXML file 'TipOfTheNight.fxml'."; // NOI18N
        assert (cbRepeatForChooserNext != null)     : "fx:id=\"cbRepeatForChooserNext\" was not injected: check your FXML file 'TipOfTheNight.fxml'."; // NOI18N
        assert (cbRepeatForChooserRandom != null)   : "fx:id=\"cbRepeatForChooserRandom\" was not injected: check your FXML file 'TipOfTheNight.fxml'."; // NOI18N
        assert (fpChooser != null)             : "fx:id=\"fpChooser\" was not injected: check your FXML file 'TipOfTheNight.fxml'."; // NOI18N
        assert (lInfoForChooserNext != null)   : "fx:id=\"lInfoForChooserNext\" was not injected: check your FXML file 'TipOfTheNight.fxml'."; // NOI18N
        assert (lInfoForChooserRandom != null) : "fx:id=\"lInfoForChooserRandom\" was not injected: check your FXML file 'TipOfTheNight.fxml'."; // NOI18N
        assert (vbEditor != null)              : "fx:id=\"vbEditor\" was not injected: check your FXML file 'TipOfTheNight.fxml'."; // NOI18N
        
        this.initializeChooser();
        this.initializeChooserNext();
        this.initializeChooserRandom();
        this.initializeEditor();
        
        this.updateInfoForChooserNext();
        this.updateInfoForChooserRandom();
        
        this.initializeChooserNextChangeListeners();
        this.initializeChooserRandomChangeListeners();
        this.initializeEditorChangeListeners();
    }
    
    private void initializeChooser() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize Chooser"); // NOI18N
        
        tipOfTheNightChooserView = FileProvider.getDefault().getTipOfTheNightProvider().getTipOfTheNightChooserViewForPerformanceCheck();
        tipOfTheNightChooserPresenter = tipOfTheNightChooserView.getRealPresenter();
        fpChooser.getChildren().add(tipOfTheNightChooserView.getView());
    }
    
    private void initializeChooserNext() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize ChooserNext"); // NOI18N

        cbIntervalForChooserNext.getItems().addAll(EntityHelper.getDefault().getWithInterval());
        cbIntervalForChooserNext.setCellFactory(new Callback<ListView<Integer>, ListCell<Integer>>() {

            @Override
            public ListCell<Integer> call(ListView<Integer> param) {
                return new ListCell<Integer>() {

                        @Override
                        protected void updateItem(Integer item, boolean empty) {
                            super.updateItem(item, empty);
                            
                            if (item == null) {
                                super.setText(null);
                                return;
                            }
                            
                            super.setText("" + item); // NOI18N
                        }
                    };
            }
        });
        
        final Integer withInterval = PreferencesFacade.INSTANCE.getInt(PREF__PERF__INTERVAL__TIP_OF_THE_NIGHT_CHOOSER_NEXT,
                PREF__PERF__INTERVAL__TIP_OF_THE_NIGHT_CHOOSER_NEXT__DEFAULT_VALUE);
        cbIntervalForChooserNext.getSelectionModel().select(withInterval);
        
        cbRepeatForChooserNext.getItems().addAll(EntityHelper.getDefault().getRepeatEntities());
        cbRepeatForChooserNext.setCellFactory(new Callback<ListView<Integer>, ListCell<Integer>>() {

            @Override
            public ListCell<Integer> call(ListView<Integer> param) {
                return new ListCell<Integer>() {

                        @Override
                        protected void updateItem(Integer item, boolean empty) {
                            super.updateItem(item, empty);
                            
                            if (item == null) {
                                super.setText(null);
                                return;
                            }
                            
                            super.setText("" + item); // NOI18N
                        }
                    };
            }
        });
        
        final Integer quantityEntities = PreferencesFacade.INSTANCE.getInt(PREF__PERF__REPEAT__TIP_OF_THE_NIGHT_CHOOSER_NEXT,
                PREF__PERF__REPEAT__TIP_OF_THE_NIGHT_CHOOSER_NEXT__DEFAULT_VALUE);
        cbRepeatForChooserNext.getSelectionModel().select(quantityEntities);
    }
    
    private void initializeChooserRandom() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize ChooserRandom"); // NOI18N

        cbIntervalForChooserRandom.getItems().addAll(EntityHelper.getDefault().getWithInterval());
        cbIntervalForChooserRandom.setCellFactory(new Callback<ListView<Integer>, ListCell<Integer>>() {

            @Override
            public ListCell<Integer> call(ListView<Integer> param) {
                return new ListCell<Integer>() {

                        @Override
                        protected void updateItem(Integer item, boolean empty) {
                            super.updateItem(item, empty);
                            
                            if (item == null) {
                                super.setText(null);
                                return;
                            }
                            
                            super.setText("" + item); // NOI18N
                        }
                    };
            }
        });
        
        final Integer withInterval = PreferencesFacade.INSTANCE.getInt(PREF__PERF__INTERVAL__TIP_OF_THE_NIGHT_CHOOSER_RANDOM,
                PREF__PERF__INTERVAL__TIP_OF_THE_NIGHT_CHOOSER_RANDOM__DEFAULT_VALUE);
        cbIntervalForChooserRandom.getSelectionModel().select(withInterval);
        
        cbRepeatForChooserRandom.getItems().addAll(EntityHelper.getDefault().getRepeatEntities());
        cbRepeatForChooserRandom.setCellFactory(new Callback<ListView<Integer>, ListCell<Integer>>() {

            @Override
            public ListCell<Integer> call(ListView<Integer> param) {
                return new ListCell<Integer>() {

                        @Override
                        protected void updateItem(Integer item, boolean empty) {
                            super.updateItem(item, empty);
                            
                            if (item == null) {
                                super.setText(null);
                                return;
                            }
                            
                            super.setText("" + item); // NOI18N
                        }
                    };
            }
        });
        
        final Integer quantityEntities = PreferencesFacade.INSTANCE.getInt(PREF__PERF__REPEAT__TIP_OF_THE_NIGHT_CHOOSER_RANDOM,
                PREF__PERF__REPEAT__TIP_OF_THE_NIGHT_CHOOSER_RANDOM__DEFAULT_VALUE);
        cbRepeatForChooserRandom.getSelectionModel().select(quantityEntities);
    }

    private void initializeChooserNextChangeListeners() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize ChooserNextChangeListeners"); // NOI18N
        
        cbIntervalForChooserNext.valueProperty().addListener(new ChangeListener<Integer>() {

            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                PreferencesFacade.INSTANCE.putInt(PREF__PERF__INTERVAL__TIP_OF_THE_NIGHT_CHOOSER_NEXT, newValue);
                TipOfTheNightPresenter.this.updateInfoForChooserNext();
            }
        });
        
        cbRepeatForChooserNext.valueProperty().addListener(new ChangeListener<Integer>() {

            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                PreferencesFacade.INSTANCE.putInt(PREF__PERF__REPEAT__TIP_OF_THE_NIGHT_CHOOSER_NEXT, newValue);
                TipOfTheNightPresenter.this.updateInfoForChooserNext();
            }
        });
    }

    private void initializeChooserRandomChangeListeners() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize ChooserRandomChangeListeners"); // NOI18N
        
        cbIntervalForChooserRandom.valueProperty().addListener(new ChangeListener<Integer>() {

            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                PreferencesFacade.INSTANCE.putInt(PREF__PERF__INTERVAL__TIP_OF_THE_NIGHT_CHOOSER_RANDOM, newValue);
                TipOfTheNightPresenter.this.updateInfoForChooserRandom();
            }
        });
        
        cbRepeatForChooserRandom.valueProperty().addListener(new ChangeListener<Integer>() {

            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                PreferencesFacade.INSTANCE.putInt(PREF__PERF__REPEAT__TIP_OF_THE_NIGHT_CHOOSER_RANDOM, newValue);
                TipOfTheNightPresenter.this.updateInfoForChooserRandom();
            }
        });
    }
    
    private void initializeEditor() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize Editor"); // NOI18N
        
    }

    private void initializeEditorChangeListeners() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize Editor ChangeListeners"); // NOI18N
        
    }
    
    public void bind(BooleanProperty disableProperty) {
        bPerformanceCheckForChooserNext.disableProperty().unbind();
        bPerformanceCheckForChooserNext.disableProperty().bind(disableProperty);
        cbIntervalForChooserNext.disableProperty().unbind();
        cbIntervalForChooserNext.disableProperty().bind(disableProperty);
        cbRepeatForChooserNext.disableProperty().unbind();
        cbRepeatForChooserNext.disableProperty().bind(disableProperty);
    }
    
    public void onActionPerformanceCheckForChooserNext() {
        LoggerFacade.INSTANCE.info(this.getClass(), "On action Performance check for Chooser#onActionNext()"); // NOI18N
        
        final SequentialTransition st = new SequentialTransition();
        final Integer repeatEntities = (Integer) cbRepeatForChooserNext.getSelectionModel().getSelectedItem();
        final Integer withInterval = (Integer) cbIntervalForChooserNext.getSelectionModel().getSelectedItem();
        final Map<String, Integer> executedTimes = FXCollections.observableHashMap();
        
        PauseTransition pt;
        for (int i = 0; i < repeatEntities; i++) {
            pt = new PauseTransition();
            pt.setDuration(Duration.millis(withInterval));
            
            pt.setOnFinished((event) -> {
                final String key = tipOfTheNightChooserPresenter.onActionNextForPerformanceCheck();
                if (executedTimes.containsKey(key)) {
                    final int executed = executedTimes.get(key);
                    executedTimes.put(key, executed + 1);
                    
                    return;
                }
                
                executedTimes.put(key, 1);
            });
            
            st.getChildren().add(pt);
        }
        
        st.setOnFinished((ActionEvent event) -> {
            PerformanceHelper.getDefault().logPerformanceCheck(executedTimes, repeatEntities);
        });
        
        st.playFromStart();
    }
    
    public void onActionPerformanceCheckForChooserRandom() {
        LoggerFacade.INSTANCE.info(this.getClass(), "On action Performance check for Chooser#onActionRandom()"); // NOI18N
        
        final SequentialTransition st = new SequentialTransition();
        final Integer repeatEntities = (Integer) cbRepeatForChooserRandom.getSelectionModel().getSelectedItem();
        final Integer withInterval = (Integer) cbIntervalForChooserRandom.getSelectionModel().getSelectedItem();
        final Map<String, Integer> executedTimes = FXCollections.observableHashMap();
        
        PauseTransition pt;
        for (int i = 0; i < repeatEntities; i++) {
            pt = new PauseTransition();
            pt.setDuration(Duration.millis(withInterval));
            
            pt.setOnFinished((event) -> {
                final String key = tipOfTheNightChooserPresenter.onActionRandomForPerformanceCheck();
                if (executedTimes.containsKey(key)) {
                    final int executed = executedTimes.get(key);
                    executedTimes.put(key, executed + 1);
                    
                    return;
                }
                
                executedTimes.put(key, 1);
            });
            
            st.getChildren().add(pt);
        }
        
        st.setOnFinished((ActionEvent event) -> {
            PerformanceHelper.getDefault().logPerformanceCheck(executedTimes, repeatEntities);
        });
        
        st.playFromStart();
    }
    
    private void updateInfoForChooserNext() {
        final StringBuilder sbInfoForChooserNext = new StringBuilder();
        final Integer repeatEntitiesForChooserNext = (Integer) cbRepeatForChooserNext.getSelectionModel().getSelectedItem();
        final Integer withIntervalForChooserNext = (Integer) cbIntervalForChooserNext.getSelectionModel().getSelectedItem();
        final long neededTimeForChooserNext = repeatEntitiesForChooserNext * withIntervalForChooserNext;
        sbInfoForChooserNext.append(UtilProvider.getDefault().getDateConverter().convertLongToDateTimeForPerformance(
                neededTimeForChooserNext, IDateConverter.PATTERN__TIME_WITH_MILLIS));
        
        lInfoForChooserNext.setText(sbInfoForChooserNext.toString());
    }
    
    private void updateInfoForChooserRandom() {
        final StringBuilder sbInfoForChooserRandom = new StringBuilder();
        final Integer repeatEntitiesForChooserRandom = (Integer) cbRepeatForChooserRandom.getSelectionModel().getSelectedItem();
        final Integer withIntervalForChooserRandom = (Integer) cbIntervalForChooserRandom.getSelectionModel().getSelectedItem();
        final long neededTimeForChooserRandom = repeatEntitiesForChooserRandom * withIntervalForChooserRandom;
        sbInfoForChooserRandom.append(UtilProvider.getDefault().getDateConverter().convertLongToDateTimeForPerformance(
                neededTimeForChooserRandom, IDateConverter.PATTERN__TIME_WITH_MILLIS));
        
        lInfoForChooserRandom.setText(sbInfoForChooserRandom.toString());
    }
    
}
