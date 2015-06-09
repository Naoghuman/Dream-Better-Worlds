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

import de.pro.dbw.core.configuration.api.application.preferences.IPreferencesConfiguration;
import de.pro.dbw.dialog.impl.deletedialogcontent.DeleteDialogContentPresenter;
import de.pro.dbw.dialog.impl.deletedialogcontent.DeleteDialogContentView;
import de.pro.dbw.dialog.impl.dialogtemplate.DialogTemplatePresenter;
import de.pro.dbw.dialog.impl.dialogtemplate.DialogTemplateView;
import de.pro.dbw.dialog.impl.savemultifilesdialogcontent.SaveMultiFilesDialogContentPresenter;
import de.pro.dbw.dialog.impl.savemultifilesdialogcontent.SaveMultiFilesDialogContentView;
import de.pro.dbw.dialog.impl.savesinglefiledialogcontent.SaveSingleFileDialogContentPresenter;
import de.pro.dbw.dialog.impl.savesinglefiledialogcontent.SaveSingleFileDialogContentView;
import de.pro.lib.logger.api.LoggerFacade;
import de.pro.lib.preferences.api.PreferencesFacade;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author PRo
 */
public class DialogProvider implements IPreferencesConfiguration {
    
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
        
    }
    
    public void hide() {
        LoggerFacade.getDefault().debug(this.getClass(), "Hide dialoglayer");
        
        apDialogLayer.getChildren().clear();
        apDialogLayer.setVisible(Boolean.FALSE);
        apDialogLayer.setManaged(Boolean.FALSE);
    }
    
    public void hide2() {
        LoggerFacade.getDefault().debug(this.getClass(), "Hide dialoglayer2");
        
        apDialogLayer2.getChildren().clear();
        apDialogLayer2.setVisible(Boolean.FALSE);
        apDialogLayer2.setManaged(Boolean.FALSE);
    }
    
    public void register(AnchorPane apDialogLayer, AnchorPane apDialogLayer2) {
        LoggerFacade.getDefault().debug(this.getClass(), "Register AnchorPane apDialogLayer, apDialogLayer2 in DialogProvider");
        
        this.apDialogLayer = apDialogLayer;
        this.apDialogLayer2 = apDialogLayer2;
        
        this.hide();
        this.hide2();
    }
    
    private void setPositionFromDialog(Parent dialog) {
        final Double width = PreferencesFacade.getDefault().getDouble(
                PREF__DBW_WIDTH, PREF__DBW_WIDTH__DEFAULT_VALUE);
        final Double height = PreferencesFacade.getDefault().getDouble(
                PREF__DBW_HEIGHT, PREF__DBW_HEIGHT__DEFAULT_VALUE);
        dialog.setLayoutX((width / 2) - (dialog.prefWidth(Double.MAX_VALUE) / 2));
        dialog.setLayoutY((height / 2) - (dialog.prefHeight(Double.MAX_VALUE) / 2));
    }
    
    public void show(Parent dialog) {
        LoggerFacade.getDefault().debug(this.getClass(), "Show dialoglayer");
        
        this.setPositionFromDialog(dialog);
        apDialogLayer.getChildren().add(dialog);
        apDialogLayer.setVisible(Boolean.TRUE);
        apDialogLayer.setManaged(Boolean.TRUE);
    }
    
    public void show2(Parent dialog) {
        LoggerFacade.getDefault().debug(this.getClass(), "Show dialoglayer2"); // NOI18N
        
        this.setPositionFromDialog(dialog);
        apDialogLayer2.getChildren().add(dialog);
        apDialogLayer2.setVisible(Boolean.TRUE);
        apDialogLayer2.setManaged(Boolean.TRUE);
    }
    
    public void showDeleteDialog(
            String message,
            EventHandler<ActionEvent> onActionYes,
            EventHandler<ActionEvent> onActionNo
    ) {
        LoggerFacade.getDefault().debug(this.getClass(), "Show dialog for Delete"); // NOI18N
        
        final DeleteDialogContentView contentView = new DeleteDialogContentView();
        final DeleteDialogContentPresenter contentPresenter = contentView.getRealPresenter();
        contentPresenter.configure(message, onActionYes, onActionNo);
        
        final DialogTemplateView dialogView = new DialogTemplateView();
        final DialogTemplatePresenter dialogPresenter = dialogView.getRealPresenter();
        dialogPresenter.configure("Delete", contentView.getView(), contentPresenter.getSize()); // NOI18N
        
        final Parent dialog = dialogView.getView();
        this.show(dialog);
    }
    
    public void showDeleteDialog2(
            String message,
            EventHandler<ActionEvent> onActionYes,
            EventHandler<ActionEvent> onActionNo
    ) {
        LoggerFacade.getDefault().debug(this.getClass(), "Show dialog for Delete2"); // NOI18N
        
        final DeleteDialogContentView contentView = new DeleteDialogContentView();
        final DeleteDialogContentPresenter contentPresenter = contentView.getRealPresenter();
        contentPresenter.configure(message, onActionYes, onActionNo);
        
        final DialogTemplateView dialogView = new DialogTemplateView();
        final DialogTemplatePresenter dialogPresenter = dialogView.getRealPresenter();
        dialogPresenter.configure("Delete", contentView.getView(), contentPresenter.getSize()); // NOI18N
        
        final Parent dialog = dialogView.getView();
        this.show2(dialog);
    }
    
    public void showSaveMultiFilesDialog(
        EventHandler<ActionEvent> onActionYes,
        EventHandler<ActionEvent> onActionNo
    ) {   
        LoggerFacade.getDefault().debug(this.getClass(), "Show dialog for SaveMultiFiles"); // NOI18N
        
        final SaveMultiFilesDialogContentView contentView = new SaveMultiFilesDialogContentView();
        final SaveMultiFilesDialogContentPresenter contentPresenter = contentView.getRealPresenter();
        contentPresenter.configure(onActionYes, onActionNo);
        
        final DialogTemplateView dialogView = new DialogTemplateView();
        final DialogTemplatePresenter dialogPresenter = dialogView.getRealPresenter();
        dialogPresenter.configure("Save", contentView.getView(), contentPresenter.getSize()); // NOI18N
        
        final Parent dialog = dialogView.getView();
        this.show(dialog);
    }
    
    public void showSaveSingleFileDialog(
        EventHandler<ActionEvent> onActionYes,
        EventHandler<ActionEvent> onActionNo
    ) {
        LoggerFacade.getDefault().debug(this.getClass(), "Show dialog for SaveSingleFile"); // NOI18N
        
        final SaveSingleFileDialogContentView contentView = new SaveSingleFileDialogContentView();
        final SaveSingleFileDialogContentPresenter contentPresenter = contentView.getRealPresenter();
        contentPresenter.configure(onActionYes, onActionNo);
        
        final DialogTemplateView dialogView = new DialogTemplateView();
        final DialogTemplatePresenter dialogPresenter = dialogView.getRealPresenter();
        dialogPresenter.configure("Save", contentView.getView(), contentPresenter.getSize()); // NOI18N
        
        final Parent dialog = dialogView.getView();
        this.show(dialog);
    }
    
}
