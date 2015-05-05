/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pro.dbw.navigation.provider;

import de.pro.dbw.core.configuration.api.action.IActionConfiguration;
import de.pro.dbw.navigation.dreambook.api.DreamBookNavigationModel;
import de.pro.dbw.navigation.dreambook.impl.dreambooknavigation.DreamBookNavigationPresenter;
import de.pro.dbw.navigation.dreambook.impl.dreambooknavigation.DreamBookNavigationView;
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
public class DreamBookProvider implements IActionConfiguration {
    
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
        this.registerOnActionJobCheckDreamBookNavigation();
    }

    public void register(TabPane tpNavigationLeft) {
        LoggerFacade.getDefault().info(this.getClass(), "Register navigation left");
        
        System.out.println(" XXX load title Dreambook from properties");
        final Tab tab = new Tab("Dreambook");
        tab.setClosable(false);
        dreamBookNavigationView = new DreamBookNavigationView();
        tab.setContent(dreamBookNavigationView.getView());
        tpNavigationLeft.getTabs().add(tab);
        
        tpNavigationLeft.getSelectionModel().select(tab);
    }
    
    private void registerOnActionJobCheckDreamBookNavigation() {
        ActionFacade.getDefault().register(ACTION__JOB_CHECK_NAVIGATION__DREAMBOOK,
                (ActionEvent ae) -> {
                    LoggerFacade.getDefault().debug(this.getClass(), "Check job for DreamBook-Navigation");
                    
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
                            LoggerFacade.getDefault().debug(DreamBookProvider.class,
                                    "DateConverter.isBefore(-3, item.getGenerationTime())");
                                    
                            presenter.refresh();
                            return;
                        }
                    }
                });
    }
    
}
