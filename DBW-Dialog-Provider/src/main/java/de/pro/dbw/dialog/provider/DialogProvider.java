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

import de.pro.dbw.dialog.impl.deletedialog.DeleteDialogPresenter;
import de.pro.dbw.dialog.impl.deletedialog.DeleteDialogView;
import de.pro.dbw.dialog.impl.savemultifilesdialog.SaveMultiFilesDialogPresenter;
import de.pro.dbw.dialog.impl.savemultifilesdialog.SaveMultiFilesDialogView;
import de.pro.dbw.dialog.impl.savesinglefiledialog.SaveSingleFileDialogPresenter;
import de.pro.dbw.dialog.impl.savesinglefiledialog.SaveSingleFileDialogView;
import de.pro.lib.logger.api.LoggerFacade;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author PRo
 */
public class DialogProvider {
    
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
        LoggerFacade.getDefault().debug(this.getClass(), "Register AnchorPane dialoglayer and dialoglayer2");
        
        this.apDialogLayer = apDialogLayer;
        this.apDialogLayer2 = apDialogLayer2;
        
        this.hide();
        this.hide2();
    }
    
    public void show(Parent dialog) {
        LoggerFacade.getDefault().debug(this.getClass(), "Show dialoglayer");
        
        apDialogLayer.getChildren().add(dialog);
        apDialogLayer.setVisible(Boolean.TRUE);
        apDialogLayer.setManaged(Boolean.TRUE);
    }
    
    public void show2(Parent dialog) {
        LoggerFacade.getDefault().debug(this.getClass(), "Show dialoglayer2");
        
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
        
        final DeleteDialogView view = new DeleteDialogView();
        final DeleteDialogPresenter presenter = view.getRealPresenter();
        presenter.configure(message, onActionYes, onActionNo);
        
        final Parent dialog = view.getView();
        this.show(dialog);
    }
    
    public void showDeleteDialog2(
            String message,
            EventHandler<ActionEvent> onActionYes,
            EventHandler<ActionEvent> onActionNo
    ) {
        LoggerFacade.getDefault().debug(this.getClass(), "Show dialog for Delete2"); // NOI18N
        
        final DeleteDialogView view = new DeleteDialogView();
        final DeleteDialogPresenter presenter = view.getRealPresenter();
        presenter.configure(message, onActionYes, onActionNo);
        
        final Parent dialog = view.getView();
        this.show2(dialog);
    }
    
    public void showSaveMultiFilesDialog(
        EventHandler<ActionEvent> onActionYes,
        EventHandler<ActionEvent> onActionNo
    ) {   
        LoggerFacade.getDefault().debug(this.getClass(), "Show dialog for SaveMultiFiles"); // NOI18N
        
        final SaveMultiFilesDialogView view = new SaveMultiFilesDialogView();
        final SaveMultiFilesDialogPresenter presenter = view.getRealPresenter();
        presenter.configure(onActionYes, onActionNo);
        
        final Parent dialog = view.getView();
        this.show(dialog);
    }
    
    public void showSaveSingleFileDialog(
        EventHandler<ActionEvent> onActionYes,
        EventHandler<ActionEvent> onActionNo
    ) {
        LoggerFacade.getDefault().debug(this.getClass(), "Show dialog for SaveSingleFile"); // NOI18N
        
        final SaveSingleFileDialogView view = new SaveSingleFileDialogView();
        final SaveSingleFileDialogPresenter presenter = view.getRealPresenter();
        presenter.configure(onActionYes, onActionNo);
        
        final Parent dialog = view.getView();
        this.show(dialog);
    }
    
}
