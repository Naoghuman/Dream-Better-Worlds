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
package de.pro.dbw.file.reflection.impl.reflection;

import de.pro.dbw.core.configuration.api.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.application.defaultid.IDefaultIdConfiguration;
import de.pro.dbw.core.configuration.api.application.util.IUtilConfiguration;
import de.pro.dbw.core.sql.provider.SqlProvider;
import de.pro.dbw.dialog.provider.DialogProvider;
import de.pro.dbw.file.reflection.api.ReflectionCommentModel;
import de.pro.dbw.file.reflection.api.ReflectionModel;
import de.pro.dbw.file.reflection.impl.reflectioncomment.ReflectionCommentPresenter;
import de.pro.dbw.file.reflection.impl.reflectioncomment.ReflectionCommentView;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 *
 * @author PRo
 */
public class ReflectionPresenter implements Initializable, IActionConfiguration, 
        IDateConverter, IDefaultIdConfiguration, IUtilConfiguration {
    
    @FXML private Button bDelete;
    @FXML private Button bSave;
    @FXML private CheckBox cbTime;
//    @FXML private ScrollPane spComments;
    @FXML private TextArea taText;
    @FXML private TextField tfDate;
    @FXML private TextField tfSource;
    @FXML private TextField tfTime;
    @FXML private TextField tfTitle;
    @FXML private VBox vbReflection;
    @FXML private ListView lvComments;
    
    private ReflectionModel model = null;
    private ReflectionModel oldModel = null;
    
    private BooleanChangeListener booleanChangeListener = null;
    private StringChangeListener stringChangeListener = null;
    
//    private final List<ReflectionCommentModel> reflectionCommentModels = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize ReflectionPresenter"); // NOI18N
    
        assert (bDelete != null)      : "fx:id=\"bDelete\" was not injected: check your FXML file 'Reflection.fxml'."; // NOI18N
        assert (bSave != null)        : "fx:id=\"bSave\" was not injected: check your FXML file 'Reflection.fxml'."; // NOI18N
        assert (cbTime != null)       : "fx:id=\"cbTime\" was not injected: check your FXML file 'Reflection.fxml'."; // NOI18N
//        assert (spComments != null)   : "fx:id=\"spComments\" was not injected: check your FXML file 'Reflection.fxml'."; // NOI18N
        assert (taText != null)       : "fx:id=\"taText\" was not injected: check your FXML file 'Reflection.fxml'."; // NOI18N
        assert (tfDate != null)       : "fx:id=\"tfDate\" was not injected: check your FXML file 'Reflection.fxml'."; // NOI18N
        assert (tfSource != null)     : "fx:id=\"tfSource\" was not injected: check your FXML file 'Reflection.fxml'."; // NOI18N
        assert (tfTime != null)       : "fx:id=\"tfTime\" was not injected: check your FXML file 'Reflection.fxml'."; // NOI18N
        assert (tfTitle != null)      : "fx:id=\"tfTitle\" was not injected: check your FXML file 'Reflection.fxml'."; // NOI18N
        assert (vbReflection != null) : "fx:id=\"vbReflection\" was not injected: check your FXML file 'Reflection.fxml'."; // NOI18N
        assert (lvComments != null)   : "fx:id=\"lvComments\" was not injected: check your FXML file 'Reflection.fxml'."; // NOI18N
        
        this.initializeCommentArea();
        this.initializeListeners();
    }
    
    private void initializeListeners() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize listeners in ReflectionPresenter"); // NOI18N
        
        booleanChangeListener = new BooleanChangeListener();
        stringChangeListener = new StringChangeListener();
    }
    
    private void initializeCommentArea() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize Comment area in ReflectionPresenter"); // NOI18N
    
//      // lvComments
        lvComments.getStylesheets().addAll(this.getClass().getResource("Reflection.css").toExternalForm()); // NOI18N
        lvComments.getItems().clear();
        lvComments.setCellFactory(new Callback<ListView<ReflectionCommentView>, ListCell<ReflectionCommentView>>() {

            @Override
            public ListCell<ReflectionCommentView> call(ListView<ReflectionCommentView> param) {
                return new ListCell<ReflectionCommentView>() {
                    @Override
                    public void updateItem(ReflectionCommentView item, boolean empty) {
                        super.updateItem(item, empty);
                        
                        if (item != null) {
                            this.setGraphic(item.getView());
                        } else {
                            this.setGraphic(null);
                        }
                    }
                };
            }
        });
    }

    private String createOnActionDeleteComment() {
        final String actionKeyForDeleting = ACTION__DELETE_ + System.currentTimeMillis();
        ActionFacade.getDefault().register(
                actionKeyForDeleting,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    final ReflectionCommentModel reflectionCommentModel = (ReflectionCommentModel) actionTransferModel.getObject();
                    /*
                    if rcm.id=default-id
                      - rcm ist noch nicht in der db gespeichert, so entferne einfach die
                        komponente aus dem parent-file.
                    else
                     - rcm ist in der db gespeichert, so a) entferne die komponente im
                       parent-file und b) lösche das rcm in der db.
                    */
                    System.out.println("reflectionCommentModel :) " + reflectionCommentModel.getId());
                    
                    ActionFacade.getDefault().remove(actionTransferModel.getActionKey());
                });
        
        return actionKeyForDeleting;
    }
    
    public Boolean isMarkAsChanged() {
        return model.isMarkAsChanged();
    }
    
    public void onActionAddComment() {
        final ReflectionCommentView view = new ReflectionCommentView();
        final ReflectionCommentPresenter presenter = view.getRealPresenter();
        final String actionKeyForDeleting = this.createOnActionDeleteComment();
        presenter.configure(new ReflectionCommentModel(), actionKeyForDeleting);
        presenter.textProperty().addListener(stringChangeListener);
        
        lvComments.getItems().add(0, view);
    }
    
    public void onActionDelete() {
        LoggerFacade.getDefault().info(this.getClass(), "On action delete"); // NOI18N

        DialogProvider.getDefault().showDeleteSingleFileDialog(
                (ActionEvent ae) -> { // Yes
                    SqlProvider.getDefault().getReflectionSqlProvider().deleteReflectionWithAllComments(model.getId());
                    
                    DialogProvider.getDefault().hide();
                    
                    final Boolean removeFile = Boolean.TRUE;
                    this.onActionUpdateGui(removeFile);
                },
                (ActionEvent ae) -> { // No
                    DialogProvider.getDefault().hide();
                });
    }

    public void onActionRefresh() {
        LoggerFacade.getDefault().info(this.getClass(), "On action refresh"); // NOI18N
        
        if (oldModel == null) {
            return;
        }
        
        this.show(oldModel);
    }

    public void onActionSave() {
        LoggerFacade.getDefault().info(this.getClass(), "On action save"); // NOI18N
        
        final Boolean updateGui = Boolean.TRUE;
        this.onActionSave(updateGui);
    }
    
    public void onActionSave(Boolean updateGui) {
        LoggerFacade.getDefault().info(this.getClass(), "Save dream to database"); // NOI18N
        
        System.out.println(" XXX ReflectionPresenter.onActionSave(boolean) add validation for input");
        
        // Unbind
        model.titleProperty().unbind();
        model.sourceProperty().unbind();
        model.textProperty().unbind();
        
        // Convert date + time
        final String time = (tfTime.getText() != null) ? tfTime.getText()
                : PATTERN__TIME_IS_EMPTY;
        model.setGenerationTime(UtilProvider.getDefault().getDateConverter().convertDateTimeToLong(
                tfDate.getText() + SIGN__SPACE + time,
                PATTERN__DATETIME));
        
        // Save the dream
        SqlProvider.getDefault().getReflectionSqlProvider().createOrUpdate(model, FILE__DREAM__DEFAULT_ID);
        oldModel = ReflectionModel.copy(model);
        
        if (!updateGui) {
            return;
        }
        
        // Bind
        model.titleProperty().bind(tfTitle.textProperty());
        model.sourceProperty().bind(tfSource.textProperty());
        model.textProperty().bind(taText.textProperty());
        
        // Update gui
        model.setMarkAsChanged(Boolean.FALSE);
        
        final Boolean removeFile = Boolean.FALSE;
        this.onActionUpdateGui(removeFile);
    }
    
    private void onActionUpdateGui(Boolean removeFile) {
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
    
    public void show(ReflectionModel model) {
        LoggerFacade.getDefault().info(this.getClass(), "Show reflection: " + model.getTitle()); // NOI18N
        System.out.println(" XXX ReflectionPresenter.show() validation date + time");
        
        this.model = model;
        this.model.setMarkAsChanged(this.model.getId() == FILE__REFLECTION__DEFAULT_ID.longValue());
        
        this.oldModel = ReflectionModel.copy(model);
        
        // ToolBar
        bDelete.disableProperty().unbind();
        bDelete.setDisable(this.model.getId() == FILE__REFLECTION__DEFAULT_ID.longValue());
        bDelete.disableProperty().bind(BooleanBinding.booleanExpression(
                this.model.idProperty().isEqualTo(FILE__REFLECTION__DEFAULT_ID)));
        
        bSave.disableProperty().unbind();
        bSave.setDisable(!this.model.isMarkAsChanged());
        bSave.disableProperty().bind(this.model.markAsChangedProperty().not());
        
        // Title
        this.model.titleProperty().unbind();
        tfTitle.textProperty().removeListener(stringChangeListener);
        tfTitle.setText(this.model.getTitle());
        tfTitle.textProperty().addListener(stringChangeListener);
        this.model.titleProperty().bind(tfTitle.textProperty());
        
        // Source
        this.model.textProperty().unbind();
        tfSource.textProperty().removeListener(stringChangeListener);
        tfSource.setText(this.model.getSource());
        tfSource.textProperty().addListener(stringChangeListener);
        this.model.sourceProperty().bind(tfSource.textProperty());
        
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
        
        // Text
        this.model.textProperty().unbind();
        taText.textProperty().removeListener(stringChangeListener);
        taText.setText(this.model.getText());
        taText.textProperty().addListener(stringChangeListener);
        this.model.textProperty().bind(taText.textProperty());
        
        // TODO load all comments here
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
