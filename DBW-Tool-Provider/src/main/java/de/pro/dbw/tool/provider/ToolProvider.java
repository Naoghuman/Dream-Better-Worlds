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
package de.pro.dbw.tool.provider;

import de.pro.dbw.core.configuration.api.action.IRegisterActions;
import de.pro.lib.logger.api.LoggerFacade;

/**
 *
 * @author PRo
 */
public class ToolProvider implements IRegisterActions {
    
    private static ToolProvider instance = null;
    
    public static ToolProvider getDefault() {
        if (instance == null) {
            instance = new ToolProvider();
        }
        
        return instance;
    }
    
    private ToolProvider() {
        this.initialize();
    }
    
    private void initialize() {
        
    }
    
    public TimerProvider getTimerProvider() {
        return TimerProvider.getDefault();
    }

    @Override
    public void registerActions() {
        LoggerFacade.getDefault().info(this.getClass(), "Register actions in ToolProvider"); // NOI18N
        
    }
    
}