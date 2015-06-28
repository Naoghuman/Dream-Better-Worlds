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
import de.pro.dbw.feature.tag.impl.tagcategoryeditor.TagCategoryEditorPresenter;
import de.pro.dbw.feature.tag.impl.tagcategoryeditor.TagCategoryEditorView;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.logger.api.LoggerFacade;
import de.pro.lib.properties.api.PropertiesFacade;
import javafx.event.ActionEvent;

/**
 *
 * @author PRo
 */
public class TagCategoryProvider implements IActionConfiguration, IApplicationConfiguration,
        IRegisterActions
{
    private static TagCategoryProvider instance = null;
    
    public static TagCategoryProvider getDefault() {
        if (instance == null) {
            instance = new TagCategoryProvider();
        }
        
        return instance;
    }
    
    private TagCategoryProvider() {
        this.initialize();
    }
    
    private void initialize() {
        
    }

    @Override
    public void registerActions() {
        LoggerFacade.getDefault().debug(this.getClass(), "Register actions in TagCategoryProvider"); // NOI18N
        
        this.registerOnActionShowTagCategoryEditor();
    }

    private void registerOnActionShowTagCategoryEditor() {
        ActionFacade.getDefault().register(
                ACTION__SHOW_TAG_CATEGORY__EDITOR,
                (ActionEvent ae) -> {
                    this.showTagCategoryEditor();
                });
    }
    
    private void showTagCategoryEditor() {
        LoggerFacade.getDefault().debug(this.getClass(), "Show Tag Category Editor"); // NOI18N
        
        final TagCategoryEditorView contentView = new TagCategoryEditorView();
        final TagCategoryEditorPresenter contentPresenter = contentView.getRealPresenter();
        final String title = PropertiesFacade.getDefault().getProperty(DBW__RESOURCE_BUNDLE, KEY__FEATURE_TAG__TAG_CATEGORY_EDTIOR_TITLE);
        DialogProvider.getDefault().show(title, contentView.getView(), contentPresenter.getSize());
    }
    
}
