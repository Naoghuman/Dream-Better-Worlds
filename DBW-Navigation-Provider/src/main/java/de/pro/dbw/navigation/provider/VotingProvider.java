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

import de.pro.dbw.core.configuration.api.application.action.IRegisterActions;
import de.pro.dbw.navigation.voting.impl.votingnavigation.VotingNavigationPresenter;
import de.pro.dbw.navigation.voting.impl.votingnavigation.VotingNavigationView;
import de.pro.lib.logger.api.LoggerFacade;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 *
 * @author PRo
 */
public class VotingProvider implements IRegisterActions {
    
    private static VotingProvider instance = null;
    
    public static VotingProvider getDefault() {
        if (instance == null) {
            instance = new VotingProvider();
        }
        
        return instance;
    }
    
    private VotingNavigationView votingNavigationView = null;
    
    private VotingProvider() {
        this.initialize();
    }
    
    private void initialize() {
        votingNavigationView = new VotingNavigationView();
    }
    
    public void register(TabPane tpNavigationLeft) {
        LoggerFacade.INSTANCE.getLogger().info(this.getClass(), "Register TabPane tpNavigationLeft in VotingProvider"); // NOI18N
        
        final Tab tab = new Tab("Voting"); // XXX properties
        tab.setClosable(false);
        tab.setContent(votingNavigationView.getView());
        tpNavigationLeft.getTabs().add(tab);
        
        tpNavigationLeft.getSelectionModel().select(tab);
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.getLogger().debug(this.getClass(), "Register actions in VotingProvider"); // NOI18N
        
        final VotingNavigationPresenter presenter = votingNavigationView.getRealPresenter();
        presenter.registerActions();
    }
    
}
