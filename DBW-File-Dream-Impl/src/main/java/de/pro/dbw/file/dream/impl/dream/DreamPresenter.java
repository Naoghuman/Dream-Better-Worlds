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
package de.pro.dbw.file.dream.impl.dream;

import de.pro.dbw.core.configuration.api.application.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.application.defaultid.IDefaultIdConfiguration;
import de.pro.dbw.core.configuration.api.application.util.IUtilConfiguration;
import de.pro.dbw.dialog.provider.DialogProvider;
import de.pro.dbw.file.dream.api.DreamModel;
import de.pro.dbw.core.sql.provider.SqlProvider;
import de.pro.dbw.util.api.IDateConverter;
import de.pro.dbw.util.provider.UtilProvider;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.action.api.ActionTransferModel;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author PRo
 */
public class DreamPresenter implements Initializable, IActionConfiguration, 
        IDateConverter, IDefaultIdConfiguration, IUtilConfiguration
{
    private static final String KEY__DIALOG_DELETE__TITLE = "dialog.delete.title"; // NOI18N
    
    @FXML private Button bDelete;
    @FXML private Button bSave;
    @FXML private CheckBox cbTime;
    @FXML private TextArea taDescription;
    @FXML private TextArea taDream;
    @FXML private TextField tfDate;
    @FXML private TextField tfTime;
    @FXML private TextField tfTitle;
    
    private DreamModel model = null;
    private DreamModel oldModel = null;
    
    private BooleanChangeListener booleanChangeListener = null;
    private ResourceBundle resources = null;
    private StringChangeListener stringChangeListener = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize DreamFilePresenter"); // NOI18N
        
        this.resources = resources;
        
        assert (bDelete != null)       : "fx:id=\"bDelete\" was not injected: check your FXML file 'Dream.fxml'."; // NOI18N
        assert (bSave != null)         : "fx:id=\"bSave\" was not injected: check your FXML file 'Dream.fxml'."; // NOI18N
        assert (cbTime != null)        : "fx:id=\"cbTime\" was not injected: check your FXML file 'Dream.fxml'."; // NOI18N
        assert (taDescription != null) : "fx:id=\"taDescription\" was not injected: check your FXML file 'Dream.fxml'."; // NOI18N
        assert (taDream != null)       : "fx:id=\"taDream\" was not injected: check your FXML file 'Dream.fxml'."; // NOI18N
        assert (tfDate != null)        : "fx:id=\"tfDate\" was not injected: check your FXML file 'Dream.fxml'."; // NOI18N
        assert (tfTime != null)        : "fx:id=\"tfTime\" was not injected: check your FXML file 'Dream.fxml'."; // NOI18N
        assert (tfTitle != null)       : "fx:id=\"tfTitle\" was not injected: check your FXML file 'Dream.fxml'."; // NOI18N
        
        System.out.println("XXX DreamFilePresenter.initialize() look when the save-button is enable (see binding in dreammapwizard)");
        
        this.initializeTime();
        this.initializeListeners();
    }
    
    private void initializeTime() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize Time component"); // NOI18N
        
        tfTime.disableProperty().bind(cbTime.selectedProperty().not());
    }
    
    private void initializeListeners() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize listeners"); // NOI18N
        
        booleanChangeListener = new BooleanChangeListener();
        stringChangeListener = new StringChangeListener();
    }
    
    public Boolean isMarkAsChanged() {
        return model.isMarkAsChanged();
    }
    
    public void onActionDelete() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action delete"); // NOI18N

        DialogProvider.getDefault().showDeleteDialog(
                resources.getString(KEY__DIALOG_DELETE__TITLE),
                (ActionEvent ae) -> { // Yes
                    SqlProvider.getDefault().getDreamSqlProvider().delete(model.getId());
                    
                    DialogProvider.getDefault().hide();
                    
                    final Boolean removeFile = Boolean.TRUE;
                    this.onActionUpdateGui(removeFile);
                },
                (ActionEvent ae) -> { // No
                    DialogProvider.getDefault().hide();
                });
    }
    
    public void onActionRefresh() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action refresh"); // NOI18N
        
        if (oldModel == null) {
            return;
        }
        
        this.show(oldModel);
    }
    
    public void onActionSave() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action save"); // NOI18N
        
        onActionSave(Boolean.TRUE);
    }
    
    public void onActionSave(Boolean updateGui) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action save"); // NOI18N
        
        System.out.println(" XXX DreamPresenter.onActionSave() add validation for input");
        
        // Unbind
        model.titleProperty().unbind();
        model.descriptionProperty().unbind();
        model.textProperty().unbind();
        
        // Convert date + time
        final String time = (tfTime.getText() != null) ? tfTime.getText()
                : PATTERN__TIME_IS_EMPTY;
        model.setGenerationTime(UtilProvider.getDefault().getDateConverter().convertDateTimeToLong(
                tfDate.getText() + SIGN__SPACE + time,
                PATTERN__DATETIME));
        
        // Save the dream
        SqlProvider.getDefault().getDreamSqlProvider().createOrUpdate(model, FILE__DREAM__DEFAULT_ID);
        oldModel = DreamModel.copy(model);
        
        if (!updateGui) {
            return;
        }
        
        // Bind
        model.titleProperty().bind(tfTitle.textProperty());
        model.descriptionProperty().bind(taDescription.textProperty());
        model.textProperty().bind(taDream.textProperty());
        
        // Update gui
        model.setMarkAsChanged(Boolean.FALSE);
        
        final Boolean removeFile = Boolean.FALSE;
        this.onActionUpdateGui(removeFile);
    }
    
//    public void registerOnActionShowExtendedSliderDialog() {
//        LoggerFacade.getDefault().debug(this.getClass(), "Register on action show Extended Slider Dialog"); // NOI18N
//        
//        // dynamic action (with open elements (can also dreamfile-id))
//        final String actionKey = ACTION__SHOW_EXTENDED_SLIDER_DIALOG + model.getId();
//        ActionFacade.getDefault().register(actionKey,
//                (ActionEvent ae) -> {
//                    /*
//                    TODO
//                     - get selected elements from ae.getSource(): Object
//                     - show then
//                    */
//                    System.out.println("xxxxxxxxxxxxxxxxxxx");
//                    
//                    DialogProvider.getDefault().hide();
//                });
//    }
    
    private void onActionUpdateGui(Boolean removeFile) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action update gui"); // NOI18N
        
        final List<ActionTransferModel> transferModels = FXCollections.observableArrayList();
        ActionTransferModel transferModel = new ActionTransferModel();
        if (removeFile) {
            transferModel.setActionKey(ACTION__REMOVE_FILE_FROM_EDITOR);
            transferModel.setLong(model.getId());
            transferModels.add(transferModel);
        }
        
        transferModel = new ActionTransferModel();
        transferModel.setActionKey(ACTION__REFRESH_NAVIGATION__DREAMBOOK);
        transferModels.add(transferModel);
        
        transferModel = new ActionTransferModel();
        transferModel.setActionKey(ACTION__REFRESH_NAVIGATION__HISTORY);
        transferModels.add(transferModel);
        
        ActionFacade.getDefault().handle(transferModels);
    }
    
    public void show(DreamModel model) {
        LoggerFacade.getDefault().info(this.getClass(), "Show dream: " + model.getTitle()); // NOI18N
        System.out.println(" XXX DreamPresenter.show() validation date + time");
        
        this.model = model;
        this.model.setMarkAsChanged(this.model.getId() == FILE__DREAM__DEFAULT_ID.longValue());
        
        this.oldModel = DreamModel.copy(model);
        
        // ToolBar
        bDelete.disableProperty().unbind();
        bDelete.setDisable(this.model.getId() == FILE__DREAM__DEFAULT_ID.longValue());
        bDelete.disableProperty().bind(BooleanBinding.booleanExpression(
                this.model.idProperty().isEqualTo(FILE__DREAM__DEFAULT_ID)));
        
        bSave.disableProperty().unbind();
        bSave.setDisable(!this.model.isMarkAsChanged());
        bSave.disableProperty().bind(this.model.markAsChangedProperty().not());
        
        // Title
        this.model.titleProperty().unbind();
        tfTitle.textProperty().removeListener(stringChangeListener);
        tfTitle.setText(this.model.getTitle());
        tfTitle.textProperty().addListener(stringChangeListener);
        this.model.titleProperty().bind(tfTitle.textProperty());
        
        // Date
        tfDate.textProperty().removeListener(stringChangeListener);
        tfDate.setText(UtilProvider.getDefault().getDateConverter().convertLongToDateTime(
                this.model.getGenerationTime(), PATTERN__DATE));
        tfDate.textProperty().addListener(stringChangeListener);
        
        // Time
        tfTime.textProperty().removeListener(stringChangeListener);
        cbTime.selectedProperty().removeListener(booleanChangeListener);
        final String time = UtilProvider.getDefault().getDateConverter().convertLongToDateTime(
                this.model.getGenerationTime(), PATTERN__TIME);
        cbTime.setSelected(!time.equals(PATTERN__TIME_IS_EMPTY));
        cbTime.selectedProperty().addListener(booleanChangeListener);
        if (!time.equals(PATTERN__TIME_IS_EMPTY)) {
            tfTime.setText(time);
        }
        tfTime.textProperty().addListener(stringChangeListener);
        
        // Description
        this.model.descriptionProperty().unbind();
        taDescription.textProperty().removeListener(stringChangeListener);
        taDescription.setText(this.model.getDescription());
        taDescription.textProperty().addListener(stringChangeListener);
        this.model.descriptionProperty().bind(taDescription.textProperty());
        
        // Text
        this.model.textProperty().unbind();
        taDream.textProperty().removeListener(stringChangeListener);
        taDream.setText(this.model.getText());
        taDream.textProperty().addListener(stringChangeListener);
        this.model.textProperty().bind(taDream.textProperty());
    }
    
    private class BooleanChangeListener implements ChangeListener<Boolean> {

        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            tfTime.setCursor(!newValue ? Cursor.DEFAULT : Cursor.TEXT);
            tfTime.setText(!newValue ? null : UtilProvider.getDefault().getDateConverter().convertLongToDateTime(
                    model.getGenerationTime(), PATTERN__TIME));
        }
    }
    
    private class StringChangeListener implements ChangeListener<String> {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (
                    model != null
                    && !model.isMarkAsChanged()
            ) { 
                model.setMarkAsChanged(Boolean.TRUE);
            }
        }
    }
}
