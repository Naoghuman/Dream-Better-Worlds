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
package de.pro.dbw.base.provider;

/**
 *
 * @author PRo
 */
public class BaseProvider {
    
    private static BaseProvider instance = null;
    
    public static BaseProvider getDefault() {
        if (instance == null) {
            instance = new BaseProvider();
        }
        
        return instance;
    }
    
    private BaseProvider() {
        this.initialize();
    }
    
    private void initialize() {
        
    }
    
    public ComponentProvider getComponentProvider() {
        return ComponentProvider.getDefault();
    }
    
    public JobProvider getJobProvider() {
        return JobProvider.getDefault();
    }
    
}
