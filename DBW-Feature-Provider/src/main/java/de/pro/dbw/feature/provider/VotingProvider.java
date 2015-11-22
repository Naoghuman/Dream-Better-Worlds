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

import de.pro.dbw.core.configuration.api.application.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.application.action.IRegisterActions;
import de.pro.dbw.dialog.provider.DialogProvider;
import de.pro.dbw.feature.voting.api.EVotingEditorMode;
import de.pro.dbw.feature.voting.impl.votingeditorcontent.VotingEditorContentPresenter;
import de.pro.dbw.feature.voting.impl.votingeditorcontent.VotingEditorContentView;
import de.pro.dbw.feature.voting.impl.votingwizardcontent.VotingWizardContentPresenter;
import de.pro.dbw.feature.voting.impl.votingwizardcontent.VotingWizardContentView;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.action.api.ActionTransferModel;
import de.pro.lib.logger.api.LoggerFacade;
import de.pro.lib.properties.api.PropertiesFacade;
import javafx.event.ActionEvent;
import javafx.scene.control.TabPane;

/**
 *
 * @author PRo
 */
public class VotingProvider implements IActionConfiguration, IRegisterActions {
    
    private static final String KEY__VOTING_PROVIDER__EDITOR_TITLE = "voting.provider.editor.title"; // NOI18N
    private static final String KEY__VOTING_PROVIDER__WIZARD_TITLE = "voting.provider.wizard.title"; // NOI18N
    
    private static VotingProvider instance = null;
    
    public static VotingProvider getDefault() {
        if (instance == null) {
            instance = new VotingProvider();
        }
        
        return instance;
    }
    
    private TabPane tpEditor = null;
    
    private VotingProvider() {
        this.initialize();
    }
    
    private void initialize() {
        
    }
    
    private String getProperty(String propertyKey) {
        return PropertiesFacade.INSTANCE.getProperty(IFeatureProvider.FEATURE_PROVIDER__RESOURCE_BUNDLE, propertyKey);
    }
    
    public void register(TabPane tpEditor) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Register TabPane tpEditor in VotingProvider");
        
        this.tpEditor = tpEditor;
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register actions in VotingProvider"); // NOI18N
        
        this.registerOnActionShowVotingEditor();
        this.registerOnActionShowVotingWizard();
    }
    
    private void registerOnActionShowVotingEditor() {
        ActionFacade.INSTANCE.register(
                ACTION__SHOW_VOTING__EDITOR,
                (ActionEvent ae) -> {
                    LoggerFacade.INSTANCE.debug(this.getClass(), "Show Voting Editor"); // NOI18N

                    final VotingEditorContentView contentView = new VotingEditorContentView();
                    final VotingEditorContentPresenter contentPresenter = contentView.getRealPresenter();
                    
                    final ActionTransferModel model = (ActionTransferModel) ae.getSource();
                    final EVotingEditorMode votingEditorMode = (EVotingEditorMode) model.getObject();
                    final String responseActionKey = model.getResponseActionKey();
                    contentPresenter.configure(votingEditorMode, responseActionKey);
                    
                    final String votingEditor = this.getProperty(KEY__VOTING_PROVIDER__EDITOR_TITLE);
                    switch (votingEditorMode) {
                        case OPEN_FROM_MENU:   {
                            DialogProvider.getDefault().show(votingEditor, contentView.getView(), contentPresenter.getSize());
                            break;
                        }
                        case OPEN_FROM_WIZARD: {
                            DialogProvider.getDefault().show2(votingEditor, contentView.getView(), contentPresenter.getSize());
                            break;
                        }
                    }
                });
    }
    
    private void registerOnActionShowVotingWizard() {
        ActionFacade.INSTANCE.register(
                ACTION__SHOW_VOTING__WIZARD,
                (ActionEvent ae) -> {
                    LoggerFacade.INSTANCE.debug(this.getClass(), "Show Voting Wizard"); // NOI18N

                    final VotingWizardContentView contentView = new VotingWizardContentView();
                    final VotingWizardContentPresenter contentPresenter = contentView.getRealPresenter();
                    final String votingWizard = this.getProperty(KEY__VOTING_PROVIDER__WIZARD_TITLE);
                    DialogProvider.getDefault().show(votingWizard, contentView.getView(), contentPresenter.getSize());
                });
    }
  
}
