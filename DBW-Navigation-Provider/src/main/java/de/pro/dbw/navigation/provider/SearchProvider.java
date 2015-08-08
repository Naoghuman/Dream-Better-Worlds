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
package de.pro.dbw.navigation.provider;

import de.pro.dbw.base.component.api.ExtendedTabModel;
import de.pro.dbw.base.component.impl.extendedtab.ExtendedTab;
import de.pro.dbw.core.configuration.api.application.action.IActionConfiguration;
import de.pro.dbw.base.provider.BaseProvider;
import de.pro.dbw.core.configuration.api.application.action.IRegisterActions;
import de.pro.dbw.core.configuration.api.navigation.INavigationConfiguration;
import static de.pro.dbw.core.configuration.api.navigation.INavigationConfiguration.KEY__NAVIGATION_TAB__DREAMBOOK;
import static de.pro.dbw.core.configuration.api.navigation.INavigationConfiguration.NAVIGATION__RESOURCE_BUNDLE;
import de.pro.dbw.navigation.search.impl.searchnavigation.SearchNavigationView;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.action.api.ActionTransferModel;
import de.pro.lib.logger.api.LoggerFacade;
import de.pro.lib.properties.api.PropertiesFacade;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 *
 * @author PRo
 */
public class SearchProvider implements IActionConfiguration, INavigationConfiguration, IRegisterActions {
    
    private static SearchProvider instance = null;
    
    public static SearchProvider getDefault() {
        if (instance == null) {
            instance = new SearchProvider();
        }
        
        return instance;
    }
    
    private TabPane tbEditor = null;
    
    private SearchNavigationView searchNavigationView = null;
    
    private SearchProvider() {
        this.initialize();
    }
    
    private void initialize() {
        searchNavigationView = new SearchNavigationView();
    }

    public void register(TabPane tpNavigationLeft, TabPane tbEditor) {
        LoggerFacade.INSTANCE.getLogger().info(this.getClass(), "Register TabPane tpNavigationLeft, tbEditor in SearchProvider");
        
        this.tbEditor = tbEditor;
        
        final String tabName = PropertiesFacade.INSTANCE.getProperties().getProperty(NAVIGATION__RESOURCE_BUNDLE, KEY__NAVIGATION_TAB__SEARCH);
        final Tab tab = new Tab(tabName);
        tab.setClosable(false);
        tab.setContent(searchNavigationView.getView());
        
        tpNavigationLeft.getTabs().add(tab);
        tpNavigationLeft.getSelectionModel().select(tab);
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.getLogger().debug(this.getClass(), "Register actions in SearchProvider");
        
        this.registerOnActionSearchInDreams();
        this.registerOnActionSearchInReflections();
        this.registerOnActionSearchInTipsOfTheNight();
    }
    
    private void registerOnActionSearchInDreams() {
        ActionFacade.INSTANCE.getAction().register(
                ACTION__SEARCH_IN__DREAMS,
                (ActionEvent ae) -> {
                    final ActionTransferModel transferModel = (ActionTransferModel) ae.getSource();
                    this.onActionSearchIn(transferModel);
                });
    }
    
    private void registerOnActionSearchInReflections() {
        ActionFacade.INSTANCE.getAction().register(
                ACTION__SEARCH_IN__REFLECTIONS,
                (ActionEvent ae) -> {
                    final ActionTransferModel transferModel = (ActionTransferModel) ae.getSource();
                    this.onActionSearchIn(transferModel);
                });
    }
    
    private void registerOnActionSearchInTipsOfTheNight() {
        ActionFacade.INSTANCE.getAction().register(
                ACTION__SEARCH_IN__TIPS_OF_THE_NIGHT,
                (ActionEvent ae) -> {
                    final ActionTransferModel transferModel = (ActionTransferModel) ae.getSource();
                    this.onActionSearchIn(transferModel);
                });
    }
    
    private void onActionSearchIn(final ActionTransferModel transferModel) {
        Platform.runLater(() -> {
            final ExtendedTabModel model = (ExtendedTabModel) transferModel.getObject();
            final ExtendedTab tab = BaseProvider.getDefault().getComponentProvider().getTab(model);
            
            tbEditor.getTabs().add(tab);
            tbEditor.getSelectionModel().select(tab);
        });
    }
    
}
