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
package de.pro.dbw.dialog.provider;

import de.pro.dbw.core.configuration.api.application.dialog.IDialogConfiguration;
import de.pro.dbw.core.configuration.api.application.preferences.IPreferencesConfiguration;
import de.pro.dbw.dialog.impl.deletedialog.DeleteDialogPresenter;
import de.pro.dbw.dialog.impl.deletedialog.DeleteDialogView;
import de.pro.dbw.dialog.impl.dialogtemplate.DialogTemplatePresenter;
import de.pro.dbw.dialog.impl.dialogtemplate.DialogTemplateView;
import de.pro.dbw.dialog.impl.savemultifilesdialog.SaveMultiFilesDialogPresenter;
import de.pro.dbw.dialog.impl.savemultifilesdialog.SaveMultiFilesDialogView;
import de.pro.dbw.dialog.impl.savesinglefiledialog.SaveSingleFileDialogPresenter;
import de.pro.dbw.dialog.impl.savesinglefiledialog.SaveSingleFileDialogView;
import de.pro.lib.logger.api.LoggerFacade;
import de.pro.lib.preferences.api.PreferencesFacade;
import de.pro.lib.properties.api.PropertiesFacade;
import java.awt.Dimension;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author PRo
 */
public class DialogProvider implements IDialogConfiguration, IPreferencesConfiguration {
    
    private static DialogProvider instance = null;
    
    public static DialogProvider getDefault() {
        if (instance == null) {
            instance = new DialogProvider();
        }
        
        return instance;
    }
    
    private AnchorPane apDialogLayer = null;
    private AnchorPane apDialogLayer2 = null;
    
    public DialogProvider() {
        this.initialize();
    }
    
    private void initialize() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize DialogProvider"); // NOI18N
        
        PropertiesFacade.INSTANCE.register(DIALOG__RESOURCE_BUNDLE);
    }
    
    public void hide() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Hide dialoglayer"); // NOI18N
        
        apDialogLayer.getChildren().clear();
        apDialogLayer.setVisible(Boolean.FALSE);
        apDialogLayer.setManaged(Boolean.FALSE);
    }
    
    public void hide2() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Hide dialoglayer2"); // NOI18N
        
        apDialogLayer2.getChildren().clear();
        apDialogLayer2.setVisible(Boolean.FALSE);
        apDialogLayer2.setManaged(Boolean.FALSE);
    }
    
    public void register(AnchorPane apDialogLayer, AnchorPane apDialogLayer2) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register AnchorPane apDialogLayer, apDialogLayer2 in DialogProvider"); // NOI18N
        
        this.apDialogLayer = apDialogLayer;
        this.apDialogLayer2 = apDialogLayer2;
        
        this.hide();
        this.hide2();
    }
    
    private void setDialogPosition(Parent dialog) {
        final Double width = PreferencesFacade.INSTANCE.getDouble(PREF__DBW_WIDTH, PREF__DBW_WIDTH__DEFAULT_VALUE);
        final Double height = PreferencesFacade.INSTANCE.getDouble(PREF__DBW_HEIGHT, PREF__DBW_HEIGHT__DEFAULT_VALUE);
        dialog.setLayoutX((width / 2) - (dialog.prefWidth(Double.MAX_VALUE) / 2));
        dialog.setLayoutY((height / 2) - (dialog.prefHeight(Double.MAX_VALUE) / 2));
    }
    
    private void setDialogVisible(Parent dialog) {
        apDialogLayer.getChildren().add(dialog);
        apDialogLayer.setVisible(Boolean.TRUE);
        apDialogLayer.setManaged(Boolean.TRUE);
    }
    
    private void setDialogVisible2(Parent dialog) {
        apDialogLayer2.getChildren().add(dialog);
        apDialogLayer2.setVisible(Boolean.TRUE);
        apDialogLayer2.setManaged(Boolean.TRUE);
    }
    
    public void show(String title, Parent content, Dimension size) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Show dialoglayer"); // NOI18N
        
        final DialogTemplateView dialogView = new DialogTemplateView();
        final DialogTemplatePresenter dialogPresenter = dialogView.getRealPresenter();
        dialogPresenter.configure(title, content, size);
        
        final Parent dialog = dialogView.getView();
        this.setDialogPosition(dialog);
        this.setDialogVisible(dialog);
    }
    
    public void show2(String title, Parent content, Dimension size) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Show dialoglayer2"); // NOI18N
        
        final DialogTemplateView dialogView = new DialogTemplateView();
        final DialogTemplatePresenter dialogPresenter = dialogView.getRealPresenter();
        dialogPresenter.configure(title, content, size);
        
        final Parent dialog = dialogView.getView();
        this.setDialogPosition(dialog);
        this.setDialogVisible2(dialog);
    }
    
    public void showDeleteDialog(
            String message,
            EventHandler<ActionEvent> onActionYes,
            EventHandler<ActionEvent> onActionNo
    ) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Show dialog for Delete"); // NOI18N
        
        final DeleteDialogView view = new DeleteDialogView();
        final DeleteDialogPresenter presenter = view.getRealPresenter();
        presenter.configure(message, onActionYes, onActionNo);
        
        final DialogTemplateView templateView = new DialogTemplateView();
        final DialogTemplatePresenter templatePresenter = templateView.getRealPresenter();
        final String title = PropertiesFacade.INSTANCE.getProperty(DIALOG__RESOURCE_BUNDLE, KEY__DIALOG_TITLE__DELETE);
        templatePresenter.configure(title, view.getView(), presenter.getSize());
        
        final Parent dialog = templateView.getView();
        this.setDialogPosition(dialog);
        this.setDialogVisible(dialog);
    }
    
    public void showDeleteDialog2(
            String message,
            EventHandler<ActionEvent> onActionYes,
            EventHandler<ActionEvent> onActionNo
    ) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Show dialog for Delete2"); // NOI18N
        
        final DeleteDialogView view = new DeleteDialogView();
        final DeleteDialogPresenter presenter = view.getRealPresenter();
        presenter.configure(message, onActionYes, onActionNo);
        
        final DialogTemplateView templateView = new DialogTemplateView();
        final DialogTemplatePresenter templatePresenter = templateView.getRealPresenter();
        final String title = PropertiesFacade.INSTANCE.getProperty(DIALOG__RESOURCE_BUNDLE, KEY__DIALOG_TITLE__DELETE);
        templatePresenter.configure(title, view.getView(), presenter.getSize());
        
        final Parent dialog = templateView.getView();
        this.setDialogPosition(dialog);
        this.setDialogVisible2(dialog);
    }
    
    public void showSaveMultiFilesDialog(
        EventHandler<ActionEvent> onActionYes,
        EventHandler<ActionEvent> onActionNo
    ) {   
        LoggerFacade.INSTANCE.debug(this.getClass(), "Show dialog for SaveMultiFiles"); // NOI18N
        
        final SaveMultiFilesDialogView view = new SaveMultiFilesDialogView();
        final SaveMultiFilesDialogPresenter presenter = view.getRealPresenter();
        presenter.configure(onActionYes, onActionNo);
        
        final DialogTemplateView templateView = new DialogTemplateView();
        final DialogTemplatePresenter templatePresenter = templateView.getRealPresenter();
        final String title = PropertiesFacade.INSTANCE.getProperty(DIALOG__RESOURCE_BUNDLE, KEY__DIALOG_TITLE__SAVE);
        templatePresenter.configure(title, view.getView(), presenter.getSize());
        
        final Parent dialog = templateView.getView();
        this.setDialogPosition(dialog);
        this.setDialogVisible(dialog);
    }
    
    public void showSaveSingleFileDialog(
        EventHandler<ActionEvent> onActionYes,
        EventHandler<ActionEvent> onActionNo
    ) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Show dialog for SaveSingleFile"); // NOI18N
        
        final SaveSingleFileDialogView view = new SaveSingleFileDialogView();
        final SaveSingleFileDialogPresenter presenter = view.getRealPresenter();
        presenter.configure(onActionYes, onActionNo);
        
        final DialogTemplateView templateView = new DialogTemplateView();
        final DialogTemplatePresenter templatePresenter = templateView.getRealPresenter();
        final String title = PropertiesFacade.INSTANCE.getProperty(DIALOG__RESOURCE_BUNDLE, KEY__DIALOG_TITLE__SAVE);
        templatePresenter.configure(title, view.getView(), presenter.getSize());
        
        final Parent dialog = templateView.getView();
        this.setDialogPosition(dialog);
        this.setDialogVisible(dialog);
    }
    
}
