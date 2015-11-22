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
package de.pro.dbw.feature.provider;

import de.pro.dbw.core.configuration.api.application.action.IRegisterActions;
import de.pro.lib.logger.api.LoggerFacade;
import de.pro.lib.properties.api.PropertiesFacade;
import javafx.scene.control.TabPane;

/**
 *
 * @author PRo
 */
public class FeatureProvider implements IRegisterActions {
    
    private static FeatureProvider instance = null;
    
    public static FeatureProvider getDefault() {
        if (instance == null) {
            instance = new FeatureProvider();
        }
        
        return instance;
    }
    
    private TabPane tpEditor = null;
    
    private FeatureProvider() {
        this.initialize();
    }
    
    private void initialize() {
        PropertiesFacade.INSTANCE.register(IFeatureProvider.FEATURE_PROVIDER__RESOURCE_BUNDLE);
    }
    
    public TagProvider getTagProvider() {
        return TagProvider.getDefault();
    }
    
    public VotingProvider getVotingProvider() {
        return VotingProvider.getDefault();
    }
    
    public void register(TabPane tpEditor) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Register TabPane tpEditor in FeatureProvider");
        
        this.tpEditor = tpEditor;
        
        VotingProvider.getDefault().register(tpEditor);
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register actions in FeatureProvider"); // NOI18N
        
        VotingProvider.getDefault().registerActions();
    }
    
}
