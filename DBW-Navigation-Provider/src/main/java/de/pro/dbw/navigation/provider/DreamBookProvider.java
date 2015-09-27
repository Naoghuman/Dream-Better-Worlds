/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pro.dbw.navigation.provider;

import de.pro.dbw.core.configuration.api.application.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.application.action.IRegisterActions;
import de.pro.dbw.core.configuration.api.navigation.INavigationConfiguration;
import de.pro.dbw.navigation.dreambook.api.DreamBookNavigationModel;
import de.pro.dbw.navigation.dreambook.impl.dreambooknavigation.DreamBookNavigationPresenter;
import de.pro.dbw.navigation.dreambook.impl.dreambooknavigation.DreamBookNavigationView;
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
public class DreamBookProvider implements IActionConfiguration, INavigationConfiguration, IRegisterActions {
    
    private static DreamBookProvider instance = null;
    
    public static DreamBookProvider getDefault() {
        if (instance == null) {
            instance = new DreamBookProvider();
        }
        
        return instance;
    }
    
    private DreamBookNavigationView dreamBookNavigationView = null;
    
    private DreamBookProvider() {
        this.initialize();
    }
    
    private void initialize() {
        dreamBookNavigationView = new DreamBookNavigationView();
    }

    public void register(TabPane tpNavigationLeft) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Register TabPane tpNavigationLeft in DreamBookProvider"); // NOI18N
        
        final String tabName = PropertiesFacade.INSTANCE.getProperty(NAVIGATION__RESOURCE_BUNDLE, KEY__NAVIGATION_TAB__DREAMBOOK);
        final Tab tab = new Tab(tabName);
        tab.setClosable(false);
        tab.setContent(dreamBookNavigationView.getView());
        tpNavigationLeft.getTabs().add(tab);
        
        tpNavigationLeft.getSelectionModel().select(tab);
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register actions in DreamBookProvider"); // NOI18N
        
        this.registerOnActionJobCheckDreamBookNavigation();
        
        final DreamBookNavigationPresenter presenter = dreamBookNavigationView.getRealPresenter();
        presenter.registerActions();
    }
    
    private void registerOnActionJobCheckDreamBookNavigation() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register job for check DreamBook-Navigation"); // NOI18N
                    
        ActionFacade.INSTANCE.register(
                ACTION__JOB_CHECK_NAVIGATION__DREAMBOOK,
                (ActionEvent ae) -> {
                    LoggerFacade.INSTANCE.debug(this.getClass(), "Check DreamBook-Navigation is prefix New actual"); // NOI18N
                    
                    final DreamBookNavigationPresenter presenter = dreamBookNavigationView.getRealPresenter();
                    final ObservableList<DreamBookNavigationModel> items = presenter.getItems();
                    if (items.isEmpty()) {
                        return;
                    }
                    
                    // Check prefix 'New' is okay?
                    for (DreamBookNavigationModel item : items) {
                        if (!item.hasPrefixNew()) {
                            continue;
                        }
                        
                        if (UtilProvider.getDefault().getDateConverter().isBefore(-3, item.getGenerationTime())) {
                            LoggerFacade.INSTANCE.debug(DreamBookProvider.class,
                                    "DateConverter.isBefore(-3, item.getGenerationTime())"); // NOI18N
                                    
                            presenter.refresh();
                            return;
                        }
                    }
                });
    }
    
}
