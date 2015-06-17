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
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.action.api.ActionTransferModel;
import de.pro.lib.logger.api.LoggerFacade;
import javafx.event.ActionEvent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 *
 * @author PRo
 */
public class FileProvider implements IActionConfiguration, IRegisterActions {
    
    private static FileProvider instance = null;
    
    public static FileProvider getDefault() {
        if (instance == null) {
            instance = new FileProvider();
        }
        
        return instance;
    }
    
    private TabPane tpEditor = null;
    
    private FileProvider() {
        this.initialize();
    }
    
    private void initialize() {
        
    }
    
    public void checkShowAtStart() {
        TipOfTheNightProvider.getDefault().checkShowTipOfTheNightAtStart();
        HelpProvider.getDefault().checkShowWelcomeHelpAtStart();
    }
    
    public DreammapProvider getDreammapProvider() {
        return DreammapProvider.getDefault();
    }
    
    public SpecialMomentProvider getSpecialMomentProvider() {
        return SpecialMomentProvider.getDefault();
    }
    
    public void register(TabPane tpEditor, TabPane tpNavigationRight) {
        LoggerFacade.getDefault().info(this.getClass(), "Register TabPanes tpEditor, tpNavigationRight in FileProvider"); // NOI18N
        
        this.tpEditor = tpEditor;
        
        DreamProvider.getDefault().register(tpEditor);
        DreammapProvider.getDefault().register(tpEditor);
        HelpProvider.getDefault().register(tpEditor, tpNavigationRight);
        ReflectionProvider.getDefault().register(tpEditor);
        TipOfTheNightProvider.getDefault().register(tpEditor);
    }

    @Override
    public void registerActions() {
        LoggerFacade.getDefault().debug(this.getClass(), "Register actions in FileProvider"); // NOI18N
        
        this.registerActionRemoveFileFromEditor();
        this.registerActionSaveAllChangedFiles();
        
        DreamProvider.getDefault().registerActions();
        DreammapProvider.getDefault().registerActions();
        HelpProvider.getDefault().registerActions();
        ReflectionProvider.getDefault().registerActions();
        TipOfTheNightProvider.getDefault().registerActions();
    }
    
    private void registerActionRemoveFileFromEditor() {
        ActionFacade.getDefault().register(
                ACTION__REMOVE_FILE_FROM_EDITOR,
                (ActionEvent ae) -> {
                    final ActionTransferModel transferModel = (ActionTransferModel) ae.getSource();
                    for (Tab tab : tpEditor.getTabs()) {
                        final String id = String.valueOf(transferModel.getLong());
                        if (tab.getId().equals(id)) {
                            tpEditor.getTabs().remove(tab);
                            return;
                        }
                    }
                });
    }
    
    public void registerActionSaveAllChangedFiles() {
        ActionFacade.getDefault().register(
                ACTION__SAVE_ALL_CHANGED_FILES,
                (ActionEvent ae) -> {
                    DreamProvider.getDefault().saveAll();
                    ReflectionProvider.getDefault().saveAll();
                    TipOfTheNightProvider.getDefault().saveAll();
                });
    }
    
}
