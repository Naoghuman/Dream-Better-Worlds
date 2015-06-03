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
package de.pro.dbw.file.provider;

import de.pro.dbw.core.configuration.api.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.action.IRegisterActions;
//import de.pro.dbw.file.dreammap.api.DreammapModel;
//import de.pro.dbw.file.dreammap.impl.dreammap.DreammapPresenter;
//import de.pro.dbw.file.dreammap.impl.dreammap.DreammapView;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.logger.api.LoggerFacade;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 *
 * @author PRo
 */
public final class DreammapProvider implements IActionConfiguration, IRegisterActions {
    
    private static DreammapProvider instance = null;
    
    public static DreammapProvider getDefault() {
        if (instance == null) {
            instance = new DreammapProvider();
        }
        
        return instance;
    }
    
    private TabPane tpEditor = null;
    
    private DreammapProvider() {
        this.init();
    }
    
    private void init() {
    }
    
//    public Button getToolBarButton() {
//        final Button btn = new Button();
//        btn.setText("Dreammap"); // XXX load from properties
////        btn.setGraphic(btn); // Load icon with lib-resources.
//        btn.setOnAction((ActionEvent event) -> {
////            DialogProvider.getDefault().showDreamMapWizard();
//        });
//        
//        return btn;
//    }
    
//    private void show(Long dreamMapId) {
//        // Check if the dream is always open
//        for (Tab tab : tpEditor.getTabs()) {
//            if (tab.getId().equals(String.valueOf(dreamMapId))) {
//                tpEditor.getSelectionModel().select(tab);
//                return;
//            }
//        }
        
        // Load content and show it
//        final DreammapModel model = null; //TestDatabase.loadDreamMap(dreamMapId);
//        if (model == null) {
//            return;
//        }
//        
//        this.show(model);
//    }
    
//    public void show(DreammapModel model) {
//        // Check if the dreammap is always open
//        for (Tab tab : tpEditor.getTabs()) {
//            if (tab.getId().equals(String.valueOf(model.getId()))) {
//                tpEditor.getSelectionModel().select(tab);
//                return;
//            }
//        }
//        
//        // Create new dreammap tab and show it
//        final DreammapView view = new DreammapView();
//        final DreammapPresenter presenter = view.getRealPresenter();
//        presenter.show(model);
//        
//        final Tab tab = new Tab(model.getTitle());
//        tab.setContent(view.getView());
//        tab.setId(String.valueOf(model.getId()));
//        
//        tpEditor.getTabs().add(tab);
//        tpEditor.getSelectionModel().select(tab);
//    }
    
    public void register(TabPane tpEditor) {
        LoggerFacade.getDefault().info(this.getClass(), "Register TabPane editor in DreammapProvider");
        
        this.tpEditor = tpEditor;
    }
    
    @Override
    public void registerActions() {
//        this.registerOnActionOpenDreammapFromNavigation();
//        this.registerOnActionOpenDreammapFormWizard();
    }
    
//    public void registerOnActionOpenDreammapFromNavigation() {
//        ActionFacade.getDefault().register(
//                ACTION__OPEN_DREAMMAP__FROM_NAVIGATION,
//                (ActionEvent ae) -> {
//                    final Long idToOpen = (Long) ae.getSource();
//                    this.show(idToOpen);
//                });
//    }

//    private void registerOnActionOpenDreammapFormWizard() {
//        ActionFacade.getDefault().register(
//                ACTION__OPEN_DREAMMAP__FROM_WIZARD,
//                (ActionEvent ae) -> {
////                    final DreammapModel model = (DreammapModel) ae.getSource();
////                    this.show(model);
//                });
//    }
    
}
