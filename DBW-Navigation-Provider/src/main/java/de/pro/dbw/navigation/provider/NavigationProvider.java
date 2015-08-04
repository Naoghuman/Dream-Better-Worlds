/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pro.dbw.navigation.provider;

import de.pro.dbw.core.configuration.api.application.action.IRegisterActions;
import de.pro.dbw.core.configuration.api.navigation.INavigationConfiguration;
import de.pro.lib.logger.api.LoggerFacade;
import de.pro.lib.preferences.api.PreferencesFacade;
import de.pro.lib.properties.api.PropertiesFacade;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.TabPane;
import javafx.util.Duration;

/**
 *
 * @author PRo
 */
public class NavigationProvider implements INavigationConfiguration, IRegisterActions {
    
    private static NavigationProvider instance = null;
    
    public static NavigationProvider getDefault() {
        if (instance == null) {
            instance = new NavigationProvider();
        }
        
        return instance;
    }
    
    private NavigationProvider() {
        this.initialize();
    }
    
    private void initialize() {
        LoggerFacade.INSTANCE.getLogger().info(this.getClass(), "Initialize NavigationProvider");
        
        PropertiesFacade.INSTANCE.getProperties().register(NAVIGATION__RESOURCE_BUNDLE);
    }
    
    public void register(TabPane tpNavigationLeft, TabPane tbEditor, TabPane tpNavigationRight) {
        LoggerFacade.INSTANCE.getLogger().info(this.getClass(), "Register TabPane tpNavigationLeft, tbEditor, tpNavigationRight in NavigationProvider"); // NOI18N
        
        this.registerNavigationLeft(tpNavigationLeft, tbEditor);
        this.registerNavigationRight(tpNavigationRight);
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.getLogger().debug(this.getClass(), "Register actions in NavigationProvider"); // NOI18N
        
        DreamBookProvider.getDefault().registerActions();
        HistoryProvider.getDefault().registerActions();
        SearchProvider.getDefault().registerActions();
        VotingProvider.getDefault().registerActions();
    }
    
    private void registerNavigationLeft(TabPane tpNavigationLeft, TabPane tbEditor) {
        LoggerFacade.INSTANCE.getLogger().info(this.getClass(), "Register TabPane tpNavigationLeft, tbEditor in NavigationProvider"); // NOI18N
        
        DreamBookProvider.getDefault().register(tpNavigationLeft);
        SearchProvider.getDefault().register(tpNavigationLeft, tbEditor);
        VotingProvider.getDefault().register(tpNavigationLeft);
        
        final PauseTransition pauseTransitionLeft = new PauseTransition();
        pauseTransitionLeft.setDuration(Duration.millis(NAVIGATION__PREVIOUS_TAB_SELECTION__DURATION));
        pauseTransitionLeft.setOnFinished((ActionEvent event) -> {
            this.selectPreviousTabSelection(
                    tpNavigationLeft, NAVIGATION__PREVIOUS_TAB_SELECTION__LEFT,
                    NAVIGATION__PREVIOUS_TAB_SELECTION__LEFT__DEFAULT_VALUE);
        });
        pauseTransitionLeft.playFromStart();
    }
    
    private void registerNavigationRight(TabPane tpNavigationRight) {
        LoggerFacade.INSTANCE.getLogger().info(this.getClass(), "Register TabPane tpNavigationRight in NavigationProvider");
        
        HistoryProvider.getDefault().register(tpNavigationRight);
        
        final PauseTransition pauseTransitionRight = new PauseTransition();
        pauseTransitionRight.setDuration(Duration.millis(NAVIGATION__PREVIOUS_TAB_SELECTION__DURATION));
        pauseTransitionRight.setOnFinished((ActionEvent event) -> {
            this.selectPreviousTabSelection(
                    tpNavigationRight, NAVIGATION__PREVIOUS_TAB_SELECTION__RIGHT,
                    NAVIGATION__PREVIOUS_TAB_SELECTION__RIGHT__DEFAULT_VALUE);
        });
        pauseTransitionRight.playFromStart();
    }

    private void selectPreviousTabSelection(final TabPane tpNavigation, final String key, Integer value) {
        LoggerFacade.INSTANCE.getLogger().info(this.getClass(), "Select previous tab for: " + key);
        
        Platform.runLater(() -> {
            final Integer previousTab = PreferencesFacade.INSTANCE.getPreferences().getInt(
                    this.getClass(), key, value);
            tpNavigation.getSelectionModel().select(previousTab);
            
            tpNavigation.getSelectionModel().selectedIndexProperty().addListener(
                    new ChangeListener<Number>()
            {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    PreferencesFacade.INSTANCE.getPreferences().putInt(this.getClass(), key,
                            tpNavigation.getSelectionModel().getSelectedIndex());
                }
            });
        });
    }
    
}
