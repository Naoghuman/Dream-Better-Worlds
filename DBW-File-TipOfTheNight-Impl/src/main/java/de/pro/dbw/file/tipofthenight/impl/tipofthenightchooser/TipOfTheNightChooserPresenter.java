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
package de.pro.dbw.file.tipofthenight.impl.tipofthenightchooser;

import de.pro.dbw.core.configuration.api.application.preferences.IPreferencesConfiguration;
import de.pro.dbw.file.tipofthenight.api.TipOfTheNightModel;
import de.pro.dbw.core.sql.provider.SqlProvider;
import de.pro.lib.logger.api.LoggerFacade;
import de.pro.lib.preferences.api.PreferencesFacade;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import org.apache.commons.lang.time.StopWatch;

/**
 *
 * @author PRo
 */
public class TipOfTheNightChooserPresenter implements Initializable, IPreferencesConfiguration {
    
    private static final Random RANDOM = new Random();
    
    @FXML private Button bNext;
    @FXML private Button bRandom;
    @FXML private CheckBox cbShowAtStart;
    @FXML private ImageView ivBackground;
    @FXML private Label lNumber;
    @FXML private Label lTitle;
    @FXML private TextArea taTipOfTheNight;
    
    private boolean performanceCheck = Boolean.FALSE;
    private long count = 0L;
    private int index = PREF__TIP_OF_THE_NIGHT_INDEX__DEFAULT_VALUE;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize TipOfTheNightChooserPresenter"); // NOI18N
        
        assert (bNext != null)           : "fx:id=\"bNext\" was not injected: check your FXML file 'TipOfTheNightChooser.fxml'."; // NOI18N
        assert (bRandom != null)         : "fx:id=\"bRandom\" was not injected: check your FXML file 'TipOfTheNightChooser.fxml'."; // NOI18N
        assert (cbShowAtStart != null)   : "fx:id=\"cbShowAtStart\" was not injected: check your FXML file 'TipOfTheNightChooser.fxml'."; // NOI18N
        assert (ivBackground != null)    : "fx:id=\"ivBackground\" was not injected: check your FXML file 'TipOfTheNightChooser.fxml'."; // NOI18N
        assert (lNumber != null)         : "fx:id=\"lNumber\" was not injected: check your FXML file 'TipOfTheNightChooser.fxml'."; // NOI18N
        assert (lTitle != null)          : "fx:id=\"lTitle\" was not injected: check your FXML file 'TipOfTheNightChooser.fxml'."; // NOI18N
        assert (taTipOfTheNight != null) : "fx:id=\"taTipOfTheNight\" was not injected: check your FXML file 'TipOfTheNightChooser.fxml'."; // NOI18N
    
        this.initialize();
    }
    
    private void initialize() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize Tip of the Night presenter"); // NOI18N
        
        this.initializeIndex();
        this.initializeShowAtStart();
        
        this.showTipOfTheNightAtStart();
    }
    
    private void initializeIndex() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize Tip of the Night index"); // NOI18N
        
        index = PreferencesFacade.INSTANCE.getInt(
                PREF__TIP_OF_THE_NIGHT_INDEX,
                PREF__TIP_OF_THE_NIGHT_INDEX__DEFAULT_VALUE);
        
        count = SqlProvider.getDefault().getTipOfTheNightProvider().count();
        if (index >= count) {
            index = PREF__TIP_OF_THE_NIGHT_INDEX__DEFAULT_VALUE;
            PreferencesFacade.INSTANCE.putInt(PREF__TIP_OF_THE_NIGHT_INDEX, index);
        }
    }
    
    private void initializeShowAtStart() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize show Tip of the Night as start"); // NOI18N
        
        final Boolean isShowAtStart = PreferencesFacade.INSTANCE.getBoolean(
                PREF__SHOW_AT_START__TIP_OF_THE_NIGHT,
                PREF__SHOW_AT_START__TIP_OF_THE_NIGHT__DEFAULT_VALUE);
        cbShowAtStart.setSelected(isShowAtStart);
    }
    
    public StringProperty textProperty() {
        return taTipOfTheNight.textProperty();
    }
    
    public StringProperty titleProperty() {
        return lTitle.textProperty();
    }
    
    public void onActionNext() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Next"); // NOI18N
        
        final List<Long> tipsOfTheNightIDs = FXCollections.observableArrayList();
        tipsOfTheNightIDs.addAll(SqlProvider.getDefault().getTipOfTheNightProvider().findAllIDs());
        if (tipsOfTheNightIDs.isEmpty()) {
            index = PREF__TIP_OF_THE_NIGHT_INDEX__DEFAULT_VALUE;
            PreferencesFacade.INSTANCE.putInt(PREF__TIP_OF_THE_NIGHT_INDEX, index);
            this.show(null);
            
            return;
        }
        
        ++index;
        if (index >= tipsOfTheNightIDs.size()) {
            index = PREF__TIP_OF_THE_NIGHT_INDEX__DEFAULT_VALUE;
        }
        PreferencesFacade.INSTANCE.putInt(PREF__TIP_OF_THE_NIGHT_INDEX, index);
        
        final long tipOfTheNightModelId = tipsOfTheNightIDs.get(index);
        final TipOfTheNightModel tipOfTheNightModel = SqlProvider.getDefault().getTipOfTheNightProvider().findByID(tipOfTheNightModelId);
        this.show(tipOfTheNightModel);
    }

    public String onActionNextForPerformanceCheck() {
        LoggerFacade.INSTANCE.deactivate(Boolean.TRUE);
        
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        performanceCheck = Boolean.TRUE;
        this.onActionNext();
        
        stopWatch.split();
        final String executedTime = stopWatch.toSplitString();
        stopWatch.stop();
        
        LoggerFacade.INSTANCE.deactivate(Boolean.FALSE);
        
        return executedTime;
    }
    
    public void onActionRandom() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Random"); // NOI18N
        
        final List<Long> tipsOfTheNightIDs = FXCollections.observableArrayList();
        tipsOfTheNightIDs.addAll(SqlProvider.getDefault().getTipOfTheNightProvider().findAllIDs());
        if (tipsOfTheNightIDs.isEmpty()) {
            index = PREF__TIP_OF_THE_NIGHT_INDEX__DEFAULT_VALUE;
            PreferencesFacade.INSTANCE.putInt(PREF__TIP_OF_THE_NIGHT_INDEX, index);
            this.show(null);
            
            return;
        }

        if (tipsOfTheNightIDs.size() < 2) {
            this.onActionNext();
            return;
        }
    
        final int oldIndex = index;
        do {
           index = RANDOM.nextInt(tipsOfTheNightIDs.size()); 
        }
        while (index == oldIndex);
        PreferencesFacade.INSTANCE.putInt(PREF__TIP_OF_THE_NIGHT_INDEX, index);
        
        final long tipOfTheNightModelId = tipsOfTheNightIDs.get(index);
        final TipOfTheNightModel tipOfTheNightModel = SqlProvider.getDefault().getTipOfTheNightProvider().findByID(tipOfTheNightModelId);
        this.show(tipOfTheNightModel);
    }

    public String onActionRandomForPerformanceCheck() {
        LoggerFacade.INSTANCE.deactivate(Boolean.TRUE);
        
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        performanceCheck = Boolean.TRUE;
        this.onActionRandom();
        
        stopWatch.split();
        final String executedTime = stopWatch.toSplitString();
        
        LoggerFacade.INSTANCE.deactivate(Boolean.FALSE);
        
        return executedTime;
    }
    
    public void onActionShowAtStart() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action show at start: " + cbShowAtStart.isSelected()); // NOI18N
    
        PreferencesFacade.INSTANCE.putBoolean(PREF__SHOW_AT_START__TIP_OF_THE_NIGHT, cbShowAtStart.isSelected());
    }

    public void prepareForPreview() {
        this.showNoTipOfTheNight();
    }

    public void prepareForPerformanceCheck() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Prepare for Performance check..."); // NOI18N
    
        bNext.setDisable(Boolean.TRUE);
        bRandom.setDisable(Boolean.TRUE);
        cbShowAtStart.setDisable(Boolean.TRUE);
    }
    
    private void show(TipOfTheNightModel model) {
        if (model == null) {
            this.showNoTipOfTheNight();
            return;
        }
        
        this.showTipOfTheNight(model);
    }
    
    private void showNoTipOfTheNight() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "No TipOfTheNights are created"); // NOI18N
    
        lTitle.setText(null);
        lNumber.setText(null);
        taTipOfTheNight.setText(null);
        
        bNext.setDisable(Boolean.TRUE);
        bRandom.setDisable(Boolean.TRUE);
    }
    
    private void showTipOfTheNight(TipOfTheNightModel model) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Show TipOfTheNight: " + model.getTitle()); // NOI18N
    
        lTitle.setText(model.getTitle());
        lNumber.setText("Nr. " + (index + 1) + " / " + count); // NOI18N
        taTipOfTheNight.setText(model.getText());
        
        if (!performanceCheck) {
            bNext.setDisable(Boolean.FALSE);
            bRandom.setDisable(Boolean.FALSE);
        }
    }
    
    private void showTipOfTheNightAtStart() {
        final List<Long> tipsOfTheNightIDs = FXCollections.observableArrayList();
        tipsOfTheNightIDs.addAll(SqlProvider.getDefault().getTipOfTheNightProvider().findAllIDs());
        
        if (tipsOfTheNightIDs.isEmpty()) {
            index = PREF__TIP_OF_THE_NIGHT_INDEX__DEFAULT_VALUE;
            PreferencesFacade.INSTANCE.putInt(PREF__TIP_OF_THE_NIGHT_INDEX, index);
            this.show(null);
            
            return;
        }
        
        if (index < 0 || index >= tipsOfTheNightIDs.size()) {
            index = PREF__TIP_OF_THE_NIGHT_INDEX__DEFAULT_VALUE;
            PreferencesFacade.INSTANCE.putInt(PREF__TIP_OF_THE_NIGHT_INDEX, index);
        }
        
        final long tipOfTheNightModelId = tipsOfTheNightIDs.get(index);
        final TipOfTheNightModel tipOfTheNightModel = SqlProvider.getDefault().getTipOfTheNightProvider().findByID(tipOfTheNightModelId);
        this.show(tipOfTheNightModel);
    }
    
}
