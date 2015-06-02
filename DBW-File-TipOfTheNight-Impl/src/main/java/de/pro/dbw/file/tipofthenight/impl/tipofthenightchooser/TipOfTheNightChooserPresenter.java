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

/**
 *
 * @author PRo
 */
public class TipOfTheNightChooserPresenter implements Initializable, IPreferencesConfiguration {
    
    @FXML private Button bNext;
    @FXML private Button bRandom;
    @FXML private CheckBox cbShowAtStart;
    @FXML private ImageView ivBackground;
    @FXML private Label lTitle;
    @FXML private TextArea taTipOfTheNight;
    
    private int index = PREF__TIP_OF_THE_NIGHT_INDEX__DEFAULT_VALUE;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize TipOfTheNightChooserPresenter"); // NOI18N
        
        assert (bNext != null)           : "fx:id=\"bNext\" was not injected: check your FXML file 'TipOfTheNightChooser.fxml'."; // NOI18N
        assert (bRandom != null)         : "fx:id=\"bRandom\" was not injected: check your FXML file 'TipOfTheNightChooser.fxml'."; // NOI18N
        assert (cbShowAtStart != null)   : "fx:id=\"cbShowAtStart\" was not injected: check your FXML file 'TipOfTheNightChooser.fxml'."; // NOI18N
        assert (ivBackground != null)    : "fx:id=\"ivBackground\" was not injected: check your FXML file 'TipOfTheNightChooser.fxml'."; // NOI18N
        assert (lTitle != null)          : "fx:id=\"lTitle\" was not injected: check your FXML file 'TipOfTheNightChooser.fxml'."; // NOI18N
        assert (taTipOfTheNight != null) : "fx:id=\"taTipOfTheNight\" was not injected: check your FXML file 'TipOfTheNightChooser.fxml'."; // NOI18N
    
        this.initialize();
    }
    /*
    ----------------------------------------------------------------------------
    TODO
    - Button Tag(Category here).
       - Öffnet den TagChooserDialog mit max 1 Auswahl.
       - Klick auf einen hinzugefügte Tag im Dialog öffnet die Tag-Navigation 
         mit der Selektion von diesen Tag.
    
     - Test 0 tips
        - Buttons sind deaktiviert
     - Test 1, 2 tips
        - Ein tip = buttons deaktiviert, mehr tips buttons sind aktiv
     - Test viele tips
    
     - Default TipsOfTheNight von mir.
        - User kann die löschen, editieren?
        - Wenn löschen, sollte dann im Dialog ein Hinweis stehen, dass der
          User eigene über ... generieren kann.
    
    ----------------------------------------------------------------------------
    unittest in lib-database-objectdb
     - model wo teilweise parameter lädt (test parameter wo weggelassen werden 
       müssen null=true sein?)
     - neue version mit lib-logger

    ----------------------------------------------------------------------------
    */
    private void initialize() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize Tip of the Night presenter"); // NOI18N
        
        this.initializeIndex();
        this.initializeShowAtStart();
        
        this.showTipOfTheNightAtStart();
    }
    
    private void initializeIndex() {
        index = PreferencesFacade.getDefault().getInt(
                PREF__TIP_OF_THE_NIGHT_INDEX,
                PREF__TIP_OF_THE_NIGHT_INDEX__DEFAULT_VALUE);
    }
    
    private void initializeShowAtStart() {
        final Boolean isShowAtStart = PreferencesFacade.getDefault().getBoolean(
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
        LoggerFacade.getDefault().info(this.getClass(), "On action Next"); // NOI18N
    
        final List<TipOfTheNightModel> allTipsOfTheNight = FXCollections.observableArrayList();
        allTipsOfTheNight.addAll(SqlProvider.getDefault().getTipOfTheNightProvider().findAll());
        if (allTipsOfTheNight.isEmpty()) {
            index = PREF__TIP_OF_THE_NIGHT_INDEX__DEFAULT_VALUE;
            PreferencesFacade.getDefault().putInt(PREF__TIP_OF_THE_NIGHT_INDEX, index);
            this.show(null);
            
            return;
        }
        
        ++index;
        if (index >= allTipsOfTheNight.size()) {
            index = PREF__TIP_OF_THE_NIGHT_INDEX__DEFAULT_VALUE;
        }
        PreferencesFacade.getDefault().putInt(PREF__TIP_OF_THE_NIGHT_INDEX, index);
        
        this.show(allTipsOfTheNight.get(index));
    }
    
    public void onActionRandom() {
        LoggerFacade.getDefault().info(this.getClass(), "On action Random"); // NOI18N
        
        final List<TipOfTheNightModel> allTipsOfTheNight = FXCollections.observableArrayList();
        allTipsOfTheNight.addAll(SqlProvider.getDefault().getTipOfTheNightProvider().findAll());
        if (allTipsOfTheNight.isEmpty()) {
            index = PREF__TIP_OF_THE_NIGHT_INDEX__DEFAULT_VALUE;
            PreferencesFacade.getDefault().putInt(PREF__TIP_OF_THE_NIGHT_INDEX, index);
            this.show(null);
            
            return;
        }
        
        if (allTipsOfTheNight.size() < 2) {
            this.onActionNext();
            return;
        }
    
        final int oldIndex = index;
        final Random random = new Random();
        do {
           index = random.nextInt(allTipsOfTheNight.size()); 
        }
        while (index == oldIndex);
        PreferencesFacade.getDefault().putInt(PREF__TIP_OF_THE_NIGHT_INDEX, index);
        
        this.show(allTipsOfTheNight.get(index));
    }
    
    public void onActionShowAtStart() {
        LoggerFacade.getDefault().debug(this.getClass(),
                "Show at start: " + cbShowAtStart.isSelected()); // NOI18N
    
        PreferencesFacade.getDefault().putBoolean(
                PREF__SHOW_AT_START__TIP_OF_THE_NIGHT,
                cbShowAtStart.isSelected());
    }

    public void prepareForPreview() {
        this.showNoTipOfTheNight();
    }
    
    private void show(TipOfTheNightModel model) {
        if (model == null) {
            this.showNoTipOfTheNight();
            return;
        }
        
        this.showCurrentTipOfTheNight(model);
    }
    
    private void showCurrentTipOfTheNight(TipOfTheNightModel model) {
        LoggerFacade.getDefault().debug(this.getClass(), "Show TipOfTheNight: " + model.getTitle()); // NOI18N
    
        lTitle.setText(model.getTitle());
        taTipOfTheNight.setText(model.getText());
        
        bNext.setDisable(Boolean.FALSE);
        bRandom.setDisable(Boolean.FALSE);
    }
    
    private void showNoTipOfTheNight() {
        LoggerFacade.getDefault().debug(this.getClass(), "No TipOfTheNights are created"); // NOI18N
    
        lTitle.setText(null);
        taTipOfTheNight.setText(null);
        
        bNext.setDisable(Boolean.TRUE);
        bRandom.setDisable(Boolean.TRUE);
    }
    
    private void showTipOfTheNightAtStart() {
        final List<TipOfTheNightModel> allTipsOfTheNight = FXCollections.observableArrayList();
        allTipsOfTheNight.addAll(SqlProvider.getDefault().getTipOfTheNightProvider().findAll());
        if (allTipsOfTheNight.isEmpty()) {
            index = PREF__TIP_OF_THE_NIGHT_INDEX__DEFAULT_VALUE;
            this.show(null);
            
            return;
        }
        
        if (index < 0 || index >= allTipsOfTheNight.size()) {
            index = PREF__TIP_OF_THE_NIGHT_INDEX__DEFAULT_VALUE;
            PreferencesFacade.getDefault().putInt(PREF__TIP_OF_THE_NIGHT_INDEX, index);
        }
        
        final TipOfTheNightModel model = allTipsOfTheNight.get(index);
        this.show(model);
    }
    
}
