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
package de.pro.dbw.file.provider;

import de.pro.dbw.core.configuration.api.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.action.IRegisterActions;
import de.pro.dbw.dialog.provider.DialogProvider;
import de.pro.dbw.file.reflection.api.ReflectionModel;
import de.pro.dbw.file.reflection.impl.reflectionwizard.ReflectionWizardPresenter;
import de.pro.dbw.file.reflection.impl.reflectionwizard.ReflectionWizardView;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.logger.api.LoggerFacade;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;

/**
 *
 * @author PRo
 */
public class ReflectionProvider implements IActionConfiguration, IRegisterActions {
    
    private static ReflectionProvider instance = null;
    
    public static ReflectionProvider getDefault() {
        if (instance == null) {
            instance = new ReflectionProvider();
        }
        
        return instance;
    }
    
    private TabPane tpEditor = null;
    
    private ReflectionProvider() {
        this.initialize();
    }
    
    private void initialize() {
        
    }
    
    public void register(TabPane tpEditor) {
        this.tpEditor = tpEditor;
    }

    @Override
    public void registerActions() {
        this.registerOnActionCreateNewFileReflection();
        this.registerOnActionEditFileReflection();
    }

    private void registerOnActionCreateNewFileReflection() {
        ActionFacade.getDefault().register(
                ACTION__CREATE_NEW_FILE__REFLECTION,
                (ActionEvent ae) -> {
                    LoggerFacade.getDefault().debug(this.getClass(), "Show Reflection Wizard in CREATE mode."); // NOI18N

                    final ReflectionWizardView view = new ReflectionWizardView();
                    final ReflectionWizardPresenter presenter = view.getRealPresenter();
                    presenter.configureWizardForCreateMode();
                            
                    final Parent dialog = view.getView();
                    DialogProvider.getDefault().show(dialog);
                });
    }

    private void registerOnActionEditFileReflection() {
        ActionFacade.getDefault().register(
                ACTION__EDIT_FILE__REFLECTION,
                (ActionEvent ae) -> {
                    LoggerFacade.getDefault().debug(this.getClass(), "Show Reflection Wizard in EDIT mode."); // NOI18N

                    final ReflectionWizardView view = new ReflectionWizardView();
                    final ReflectionWizardPresenter presenter = view.getRealPresenter();
                    // TODO catch the ReflectionModel from ae.getSource()
                    presenter.configureWizardForEditMode(new ReflectionModel());
                            
                    final Parent dialog = view.getView();
                    DialogProvider.getDefault().show(dialog);
                });
    }
    
}
