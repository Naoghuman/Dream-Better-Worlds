/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pro.dbw.navigation.provider;

import de.pro.dbw.core.configuration.api.application.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.application.action.IRegisterActions;
import de.pro.dbw.core.configuration.api.navigation.INavigationConfiguration;
import static de.pro.dbw.core.configuration.api.navigation.INavigationConfiguration.KEY__NAVIGATION_TAB__DREAMBOOK;
import static de.pro.dbw.core.configuration.api.navigation.INavigationConfiguration.NAVIGATION__RESOURCE_BUNDLE;
import de.pro.dbw.navigation.history.api.HistoryNavigationModel;
import de.pro.dbw.navigation.history.impl.historynavigation.HistoryNavigationPresenter;
import de.pro.dbw.navigation.history.impl.historynavigation.HistoryNavigationView;
import de.pro.dbw.util.provider.UtilProvider;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.logger.api.LoggerFacade;
import de.pro.lib.properties.api.PropertiesFacade;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 *
 * @author PRo
 */
public class HistoryProvider implements IActionConfiguration, INavigationConfiguration,IRegisterActions {
    
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
        historyNavigationView = new HistoryNavigationView();
    }

    public void register(TabPane tpNavigationLeft) {
        LoggerFacade.INSTANCE.getLogger().info(this.getClass(), "Register TabPane tpNavigationLeft in HistoryProvider"); // NOI18N
        
        final String tabName = PropertiesFacade.INSTANCE.getProperties().getProperty(NAVIGATION__RESOURCE_BUNDLE, KEY__NAVIGATION_TAB__HISTORY);
        final Tab tab = new Tab(tabName);
        tab.setClosable(false);
        tab.setContent(historyNavigationView.getView());
        tpNavigationLeft.getTabs().add(tab);
        
        tpNavigationLeft.getSelectionModel().select(tab);
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.getLogger().debug(this.getClass(), "Register actions in HistoryProvider"); // NOI18N
        
        this.registerOnActionJobCheckHistoryNavigation();
        
        final HistoryNavigationPresenter presenter = historyNavigationView.getRealPresenter();
        presenter.registerActions();
    }
    
    private void registerOnActionJobCheckHistoryNavigation() {
                    LoggerFacade.INSTANCE.getLogger().debug(this.getClass(), "Register job for check History-Navigation"); // NOI18N
        ActionFacade.INSTANCE.getAction().register(
                ACTION__JOB_CHECK_NAVIGATION__HISTORY,
                (ActionEvent ae) -> {
                    LoggerFacade.INSTANCE.getLogger().debug(this.getClass(), "Check if History-Navigation is up to date"); // NOI18N
                    
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
                            LoggerFacade.INSTANCE.getLogger().debug(HistoryProvider.class,
                                    "DateConverter.isBefore(-3, item.getGenerationTime())"); // NOI18N
                                    
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
                            LoggerFacade.INSTANCE.getLogger().debug(HistoryProvider.class,
                                    "DateConverter.isBefore(-30, item.getGenerationTime())"); // NOI18N
                            
                            presenter.refresh();
                            return;
                        }
                    }
                });
    }
    
}
