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
package de.pro.dbw.file.provider;

import de.pro.dbw.core.configuration.api.application.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.application.action.IRegisterActions;
import de.pro.dbw.core.configuration.api.application.defaultid.IDefaultIdConfiguration;
import de.pro.dbw.core.configuration.api.application.dialog.IDialogConfiguration;
import de.pro.dbw.core.configuration.api.application.preferences.IPreferencesConfiguration;
import de.pro.dbw.dialog.provider.DialogProvider;
import de.pro.dbw.file.tipofthenight.impl.tipofthenightchooser.TipOfTheNightChooserPresenter;
import de.pro.dbw.file.tipofthenight.impl.tipofthenighteditor.TipOfTheNightEditorView;
import de.pro.dbw.file.tipofthenight.impl.tipofthenightchooser.TipOfTheNightChooserView;
import de.pro.dbw.file.tipofthenight.impl.tipofthenighteditor.TipOfTheNightEditorPresenter;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.logger.api.LoggerFacade;
import de.pro.lib.preferences.api.PreferencesFacade;
import de.pro.lib.properties.api.PropertiesFacade;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author PRo
 */
public class TipOfTheNightProvider implements IActionConfiguration, IDefaultIdConfiguration, 
        IDialogConfiguration, IPreferencesConfiguration, IRegisterActions
{
    private static TipOfTheNightProvider instance = null;
    
    public static TipOfTheNightProvider getDefault() {
        if (instance == null) {
            instance = new TipOfTheNightProvider();
        }
        
        return instance;
    }
    
    private TabPane tpEditor = null;
    
    private TipOfTheNightProvider() {
        this.initialize();
    }
    
    private void initialize() {
        
    }
    
    public void checkShowAtStartTipOfTheNight() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Check if TipOfTheNight should show at start"); // NOI18N
    
        final Boolean isShowAtStart = PreferencesFacade.INSTANCE.getBoolean(
                PREF__SHOW_AT_START__TIP_OF_THE_NIGHT,
                PREF__SHOW_AT_START__TIP_OF_THE_NIGHT__DEFAULT_VALUE);
        if (!isShowAtStart) {
            return;
        }
        
        this.onActionShowTipOfTheNightWindow();
    }
    
    public TipOfTheNightChooserView getTipOfTheNightChooserViewForPerformanceCheck() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Get TipOfTheNight view"); // NOI18N
    
        final TipOfTheNightChooserView view = new TipOfTheNightChooserView();
        final TipOfTheNightChooserPresenter presenter = view.getRealPresenter();
        presenter.prepareForPerformanceCheck();
        
        return view;
    }
    
    private void onActionShowTipOfTheNightEditor() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action show TipOfTheNight editor"); // NOI18N
        
        final TipOfTheNightEditorView view = new TipOfTheNightEditorView();
        final TipOfTheNightEditorPresenter presenter = view.getRealPresenter();
        final String title = PropertiesFacade.INSTANCE.getProperty(DIALOG__RESOURCE_BUNDLE, KEY__DIALOG_TITLE__TIP_OF_THE_NIGHT_EDITOR);
        DialogProvider.getDefault().show(title, view.getView(), presenter.getSize());
    }
    
    private void onActionShowTipOfTheNightWindow() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action show TipOfTheNight window"); // NOI18N
    
        final Stage window = new Stage();
        window.initModality(Modality.NONE);
        window.setAlwaysOnTop(Boolean.FALSE);
        window.setResizable(Boolean.FALSE);
        final String title = PropertiesFacade.INSTANCE.getProperty(DIALOG__RESOURCE_BUNDLE, KEY__DIALOG_TITLE__TIP_OF_THE_NIGHT);
        window.setTitle(title);
        
        final TipOfTheNightChooserView view = new TipOfTheNightChooserView();
        final Scene scene = new Scene(view.getView(), 375, 250);
        window.setScene(scene);
        
        window.show();
    }
    
    public void register(TabPane tpEditor) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register TabPane editor in TipOfTheNightProvider"); // NOI18N
        
        this.tpEditor = tpEditor;
    }
    
    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register actions in TipOfTheNightProvider"); // NOI18N
        
        this.registerOnActionShowTipOfTheNightEditor();
        this.registerOnActionShowTipOfTheNightWindow();
    }
    
    private void registerOnActionShowTipOfTheNightEditor() {
        ActionFacade.INSTANCE.register(
                ACTION__SHOW_TIP_OF_THE_NIGHT__EDITOR,
                (ActionEvent ae) -> {
                    this.onActionShowTipOfTheNightEditor();
                });
    }
    
    private void registerOnActionShowTipOfTheNightWindow() {
        ActionFacade.INSTANCE.register(
                ACTION__SHOW_TIP_OF_THE_NIGHT__WINDOW,
                (ActionEvent ae) -> {
                    this.onActionShowTipOfTheNightWindow();
                });
    }

    public void saveAll() {
        /*
        TODO
         - look if the editor is open.
         - look if the editor is changed.
            - yes = save changes.
        
        TipOfTheNightEditorPresenter
        */
    }
    
}
