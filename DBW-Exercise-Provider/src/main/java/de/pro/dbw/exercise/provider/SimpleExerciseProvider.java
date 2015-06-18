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

import de.pro.dbw.core.configuration.api.core.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.core.action.IRegisterActions;
import de.pro.lib.logger.api.LoggerFacade;
import javafx.scene.control.TabPane;

/**
 *
 * @author PRo
 */
public class SimpleExerciseProvider implements IActionConfiguration, IRegisterActions {
    
    private static SimpleExerciseProvider instance = null;
    
    public static SimpleExerciseProvider getDefault() {
        if (instance == null) {
            instance = new SimpleExerciseProvider();
        }
        
        return instance;
    }
    
    private TabPane tpEditor = null;
    
    private SimpleExerciseProvider() {
        this.initialize();
    }
    
    private void initialize() {
        
    }
    
    public void register(TabPane tpEditor) {
        LoggerFacade.getDefault().info(this.getClass(), "Register TabPane editor in SimpleExerciseProvider"); // NOI18N
        
        this.tpEditor = tpEditor;
    }

    @Override
    public void registerActions() {
        LoggerFacade.getDefault().debug(this.getClass(), "Register actions in SimpleExerciseProvider"); // NOI18N
        
    }
    
}
