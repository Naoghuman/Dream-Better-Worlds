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
package de.pro.dbw.exercise.provider;

import de.pro.dbw.core.configuration.api.application.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.application.action.IRegisterActions;
import de.pro.lib.logger.api.LoggerFacade;
import javafx.scene.control.TabPane;

/**
 *
 * @author PRo
 */
public class ExerciseProvider implements IActionConfiguration, IRegisterActions {
    
    private static ExerciseProvider instance = null;
    
    public static ExerciseProvider getDefault() {
        if (instance == null) {
            instance = new ExerciseProvider();
        }
        
        return instance;
    }
    
    private TabPane tpEditor = null;
    
    private ExerciseProvider() {
        this.initialize();
    }
    
    private void initialize() {
        
    }
    
    public SimpleExerciseProvider getSimpleExerciseProvider() {
        return SimpleExerciseProvider.getDefault();
    }
    
    public void register(TabPane tpEditor) {
        LoggerFacade.INSTANCE.getLogger().info(this.getClass(), "Register TabPane editor in ExerciseProvider"); // NOI18N
        
        this.tpEditor = tpEditor;
        
        SimpleExerciseProvider.getDefault().register(tpEditor);
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.getLogger().debug(this.getClass(), "Register actions in ExerciseProvider"); // NOI18N
        
//        this.registerActionRemoveFileFromEditor();
//        this.registerActionSaveAllChangedFiles();
        
        SimpleExerciseProvider.getDefault().registerActions();
    }
    
}
