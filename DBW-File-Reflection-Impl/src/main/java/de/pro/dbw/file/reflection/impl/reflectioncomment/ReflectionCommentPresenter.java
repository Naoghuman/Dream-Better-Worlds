/*
 * Copyright (C) 2015 PRo
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
package de.pro.dbw.file.reflection.impl.reflectioncomment;

import de.pro.dbw.core.configuration.api.application.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.application.defaultid.IDefaultIdConfiguration;
import de.pro.dbw.core.configuration.api.application.util.IUtilConfiguration;
import de.pro.dbw.dialog.provider.DialogProvider;
import de.pro.dbw.file.reflection.api.ReflectionCommentModel;
import de.pro.dbw.util.api.IDateConverter;
import de.pro.dbw.util.provider.UtilProvider;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.action.api.ActionTransferModel;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author PRo
 */
public class ReflectionCommentPresenter implements Initializable, IActionConfiguration,
        IDateConverter, IDefaultIdConfiguration, IUtilConfiguration
{
    private static final String KEY__DIALOG_DELETE__TITLE = "dialog.delete.title"; // NOI18N
    private static final String KEY__REFLECTION_COMMENT__AT = "reflection.comment.at"; // NOI18N
    
    @FXML private AnchorPane apComment;
    @FXML private Label lComment;
    @FXML private TextArea taComment;
    
    private ReflectionCommentModel reflectionCommentModel = null;
    
    private ResourceBundle resources = null;
    private String actionKeyForDeletion = null;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize ReflectionCommentPresenter"); // NOI18N
        
        this.resources = resources;
    
        assert (apComment != null) : "fx:id=\"apComment\" was not injected: check your FXML file 'ReflectionComment.fxml'."; // NOI18N
        assert (lComment != null)  : "fx:id=\"lComment\" was not injected: check your FXML file 'ReflectionComment.fxml'."; // NOI18N
        assert (taComment != null) : "fx:id=\"taComment\" was not injected: check your FXML file 'ReflectionComment.fxml'."; // NOI18N
    }
    
    public void bind() {
        this.reflectionCommentModel.textProperty().bind(taComment.textProperty());
    }

    public void configure(ReflectionCommentModel reflectionCommentModel, String actionKeyForDeletion) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Configure ReflectionCommentPresenter"); // NOI18N
    
        this.reflectionCommentModel = reflectionCommentModel;
        this.actionKeyForDeletion = actionKeyForDeletion;
        
        final String date = UtilProvider.getDefault().getDateConverter().convertLongToDateTime(
                this.reflectionCommentModel.getGenerationTime(),
                IDateConverter.PATTERN__DATE__COMMENT);
        final String at = resources.getString(KEY__REFLECTION_COMMENT__AT);
        final String time = UtilProvider.getDefault().getDateConverter().convertLongToDateTime(
                this.reflectionCommentModel.getGenerationTime(),
                IDateConverter.PATTERN__TIME);
        lComment.setText(date + at + time);
        
        taComment.setText(this.reflectionCommentModel.getText());
        this.bind();
    }
    
    public ReflectionCommentModel getReflectionCommentModel() {
        return reflectionCommentModel;
    }
    
    public void onActionDelete() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action delete"); // NOI18N
    
        final ActionTransferModel actionTransferModel = new ActionTransferModel();
        actionTransferModel.setActionKey(actionKeyForDeletion);
        actionTransferModel.setObject(reflectionCommentModel);
        
        DialogProvider.getDefault().showDeleteDialog(
                resources.getString(KEY__DIALOG_DELETE__TITLE),
                (ActionEvent ae) -> { // Yes
                    ActionFacade.INSTANCE.handle(actionTransferModel);
                    DialogProvider.getDefault().hide();
                },
                (ActionEvent ae) -> { // No
                    DialogProvider.getDefault().hide();
                });
    }
    
    public StringProperty textProperty() {
        return taComment.textProperty();
    }
    
}
