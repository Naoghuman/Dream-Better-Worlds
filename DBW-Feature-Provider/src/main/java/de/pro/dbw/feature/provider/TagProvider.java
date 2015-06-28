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

import de.pro.dbw.core.configuration.api.application.IApplicationConfiguration;
import de.pro.dbw.core.configuration.api.application.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.application.action.IRegisterActions;
import de.pro.dbw.dialog.provider.DialogProvider;
import de.pro.dbw.feature.tag.impl.tageditor.TagEditorPresenter;
import de.pro.dbw.feature.tag.impl.tageditor.TagEditorView;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.logger.api.LoggerFacade;
import de.pro.lib.properties.api.PropertiesFacade;
import javafx.event.ActionEvent;

/**
 *
 * @author PRo
 */
public class TagProvider implements IActionConfiguration, IApplicationConfiguration,
        IRegisterActions
{
    private static TagProvider instance = null;
    
    public static TagProvider getDefault() {
        if (instance == null) {
            instance = new TagProvider();
        }
        
        return instance;
    }
    
    private TagProvider() {
        this.initialize();
    }
    
    private void initialize() {
        
    }

    @Override
    public void registerActions() {
        LoggerFacade.getDefault().debug(this.getClass(), "Register actions in TagProvider"); // NOI18N
        
        this.registerOnActionShowTagEditor();
    }

    private void registerOnActionShowTagEditor() {
        ActionFacade.getDefault().register(
                ACTION__SHOW_TAG__EDITOR,
                (ActionEvent ae) -> {
                    this.showTagEditor();
                });
    }
    
    private void showTagEditor() {
        LoggerFacade.getDefault().debug(this.getClass(), "Show Tag Editor"); // NOI18N
        
        final TagEditorView contentView = new TagEditorView();
        final TagEditorPresenter contentPresenter = contentView.getRealPresenter();
        final String title = PropertiesFacade.getDefault().getProperty(DBW__RESOURCE_BUNDLE, KEY__FEATURE_TAG__TAG_EDTIOR_TITLE);
        DialogProvider.getDefault().show(title, contentView.getView(), contentPresenter.getSize());
    }
    
}
