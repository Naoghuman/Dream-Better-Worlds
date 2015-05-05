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

import de.pro.dbw.base.component.api.ExtendedTabModel;
import de.pro.dbw.base.component.api.IExtendedTextField;
import de.pro.dbw.base.component.impl.extendedtab.ExtendedTab;
import de.pro.dbw.base.component.impl.extendedtextfield.ExtendedTextFieldImpl;

/**
 *
 * @author PRo
 */
public class ComponentProvider {
    
    private static ComponentProvider instance = null;
    
    public static ComponentProvider getDefault() {
        if (instance == null) {
            instance = new ComponentProvider();
        }
        
        return instance;
    }
    
    private ComponentProvider() {
        this.initialize();
    }
    
    private void initialize() {
        
    }
    
    public ExtendedTab getTab(ExtendedTabModel model) {
        return new ExtendedTab(model);
    }
    
    public IExtendedTextField getExtendedTextField() {
        return new ExtendedTextFieldImpl();
    }
    
}
