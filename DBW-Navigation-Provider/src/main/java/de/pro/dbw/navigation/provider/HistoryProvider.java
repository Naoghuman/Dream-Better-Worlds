/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pro.dbw.navigation.provider;

import de.pro.dbw.core.configuration.api.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.action.IRegisterActions;
import de.pro.dbw.navigation.history.api.HistoryNavigationModel;
import de.pro.dbw.navigation.history.impl.historynavigation.HistoryNavigationPresenter;
import de.pro.dbw.navigation.history.impl.historynavigation.HistoryNavigationView;
import de.pro.dbw.util.provider.UtilProvider;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.logger.api.LoggerFacade;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 *
 * @author PRo
 */
public class HistoryProvider implements IActionConfiguration, IRegisterActions {
    
    private static HistoryProvider instance = null;
    
    public static HistoryProvider getDefault() {
        if (instance == null) {
            instance = new HistoryProvider();
        }
        
        return instance;
    }
    
    private HistoryNavigationView historyNavigationView = null;
    
    private HistoryProvider() {
        this.initialize();
    }
    
    private void initialize() {
        
    }

    public void register(TabPane tpNavigationLeft) {
        LoggerFacade.getDefault().info(this.getClass(), "Register TabPane tpNavigationLeft in HistoryProvider");
        
        final Tab tab = new Tab("History");// XXX properties
        tab.setClosable(false);
        historyNavigationView = new HistoryNavigationView();
        tab.setContent(historyNavigationView.getView());
        tpNavigationLeft.getTabs().add(tab);
        
        tpNavigationLeft.getSelectionModel().select(tab);
    }

    @Override
    public void registerActions() {
        LoggerFacade.getDefault().info(this.getClass(), "Register actions in HistoryProvider");
        
        this.registerOnActionJobCheckHistoryNavigation();
    }
    
    private void registerOnActionJobCheckHistoryNavigation() {
        ActionFacade.getDefault().register(ACTION__JOB_CHECK_NAVIGATION__HISTORY,
                (ActionEvent ae) -> {
                    LoggerFacade.getDefault().debug(this.getClass(), "Check job for History-Navigation");
                    
                    final HistoryNavigationPresenter presenter = historyNavigationView.getRealPresenter();
                    final ObservableList<HistoryNavigationModel> items = presenter.getItems();
                    if (items.isEmpty()) {
                        return;
                    }
                    
                    // Check prefix 'New' is okay?
                    for (HistoryNavigationModel item : items) {
                        if (!item.hasPrefixNew()) {
                            continue;
                        }
                        
                        if (UtilProvider.getDefault().getDateConverter().isBefore(-3, item.getGenerationTime())) {
                            LoggerFacade.getDefault().debug(HistoryProvider.class,
                                    "DateConverter.isBefore(-3, item.getGenerationTime())");
                                    
                            presenter.refresh();
                            return;
                        }
                    }
                    
                    // Check history period (30 days)
                    for (HistoryNavigationModel item : items) {
                        if (item.getGenerationTime().longValue() == HistoryNavigationModel.GENERATION_TIME_FOR_PARENT.longValue()) {
                            continue;
                        }
                        
                        if (UtilProvider.getDefault().getDateConverter().isBefore(-30, item.getGenerationTime())) {
                            LoggerFacade.getDefault().debug(HistoryProvider.class,
                                    "DateConverter.isBefore(-30, item.getGenerationTime())");
                            
                            presenter.refresh();
                            return;
                        }
                    }
                });
    }
    
}
