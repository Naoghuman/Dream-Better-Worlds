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

import de.pro.dbw.core.configuration.api.application.action.IActionConfiguration;
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
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
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
        IDateConverter, IDefaultIdConfiguration, IUtilConfiguration
{
    private static final String CSS__REFLECTION = "Reflection.css"; // NOI18N
    
    private static final String KEY__DIALOG_DELETE__TITLE = "dialog.delete.title"; // NOI18N
    
    @FXML private Button bDelete;
    @FXML private Button bSave;
    @FXML private CheckBox cbTime;
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
    private ResourceBundle resources = null;
    private StringChangeListener stringChangeListener = null;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize ReflectionPresenter"); // NOI18N
        
        this.resources = resources;
    
        assert (bDelete != null)      : "fx:id=\"bDelete\" was not injected: check your FXML file 'Reflection.fxml'."; // NOI18N
        assert (bSave != null)        : "fx:id=\"bSave\" was not injected: check your FXML file 'Reflection.fxml'."; // NOI18N
        assert (cbTime != null)       : "fx:id=\"cbTime\" was not injected: check your FXML file 'Reflection.fxml'."; // NOI18N
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
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize listeners in ReflectionPresenter"); // NOI18N
        
        booleanChangeListener = new BooleanChangeListener();
        stringChangeListener = new StringChangeListener();
    }
    
    private void initializeCommentArea() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize Comment area in ReflectionPresenter"); // NOI18N
    
        lvComments.getStylesheets().addAll(this.getClass().getResource(CSS__REFLECTION).toExternalForm());
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

    private String registerOnDynamicActionDeleteComment() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on dynamic action delete Comment"); // NOI18N
    
        final String actionKeyForDeleting = ACTION__DELETE_ + System.currentTimeMillis();
        ActionFacade.INSTANCE.register(
                actionKeyForDeleting,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    ActionFacade.INSTANCE.remove(actionTransferModel.getActionKey());
                    
                    final ReflectionCommentModel reflectionCommentModelToDelete = (ReflectionCommentModel) actionTransferModel.getObject();
                    final Long idToDelete = reflectionCommentModelToDelete.getId();
                    final Long generationTime = reflectionCommentModelToDelete.getGenerationTime();
                    for (ReflectionCommentModel reflectionCommentModel : model.getReflectionCommentModels()) {
                        if (
                                Objects.equals(idToDelete, reflectionCommentModel.getId())
                                && Objects.equals(generationTime, reflectionCommentModel.getGenerationTime())
                        ) {
                            reflectionCommentModel.setMarkAsDeleted(Boolean.TRUE);
                        }
                    }
                    
                    if (!Objects.equals(idToDelete, FILE__REFLECTION_COMMENT__DEFAULT_ID)) {
                        model.setMarkAsChanged(Boolean.TRUE);
                    }
                    
                    this.removeCommentFromGui(generationTime, idToDelete);
                });
        
        return actionKeyForDeleting;
    }
    
    private void removeCommentFromGui(Long generationTime, Long idToCompare) {
        for (Iterator iterator = lvComments.getItems().iterator(); iterator.hasNext();) {
            final Object next = iterator.next();
            final boolean isReflectionCommentView = (next instanceof ReflectionCommentView);
            if (!isReflectionCommentView) {
                continue;
            }

            final ReflectionCommentView view = (ReflectionCommentView) next;
            if (view == null) {
                continue;
            }

            final ReflectionCommentPresenter presenter = view.getRealPresenter();
            if (
                    Objects.equals(presenter.getReflectionCommentModel().getId(), idToCompare)
                    && Objects.equals(presenter.getReflectionCommentModel().getGenerationTime(), generationTime)
            ) {
                presenter.getReflectionCommentModel().setMarkAsDeleted(Boolean.TRUE);
                iterator.remove();
                
                return;
            }
        }
    }
    
    public Boolean isMarkAsChanged() {
        return model.isMarkAsChanged();
    }
    
    public void onActionAddComment() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action add Comment"); // NOI18N
    
        final ReflectionCommentView view = new ReflectionCommentView();
        final ReflectionCommentPresenter presenter = view.getRealPresenter();
        final String actionKeyForDeleting = this.registerOnDynamicActionDeleteComment();
        presenter.configure(new ReflectionCommentModel(), actionKeyForDeleting);
        presenter.textProperty().addListener(stringChangeListener);
        
        model.getReflectionCommentModels().add(presenter.getReflectionCommentModel());
        model.setMarkAsChanged(Boolean.TRUE);
        
        lvComments.getItems().add(0, view);
    }
    
    public void onActionDelete() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Delete"); // NOI18N

        // TODO properties
        DialogProvider.getDefault().showDeleteDialog(
                resources.getString(KEY__DIALOG_DELETE__TITLE),
                (ActionEvent ae) -> { // Yes
                    SqlProvider.getDefault().getReflectionSqlProvider().deleteReflectionWithAllComments(model.getId());
                    
                    DialogProvider.getDefault().hide();
                    
                    final Boolean removeFile = Boolean.TRUE;
                    this.onActionRefreshGui(removeFile);
                },
                (ActionEvent ae) -> { // No
                    DialogProvider.getDefault().hide();
                });
    }

    public void onActionRefresh() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Refresh"); // NOI18N
        // TODO not better to load the old state from db?
        if (oldModel == null) {
            return;
        }
        
        this.show(oldModel);
    }
    
    private void onActionRefreshGui(Boolean removeFile) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Refresh Gui"); // NOI18N
    
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
        
        ActionFacade.INSTANCE.handle(transferModels);
    }

    public void onActionSave() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action save"); // NOI18N
        
        final Boolean updateGui = Boolean.TRUE;
        this.onActionSave(updateGui);
    }
    
    public void onActionSave(Boolean updateGui) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Save Reflection and Comments to database"); // NOI18N
        
        System.out.println(" XXX ReflectionPresenter.onActionSave(boolean) add validation for input");
        
        // Unbind
        model.titleProperty().unbind();
        model.sourceProperty().unbind();
        model.textProperty().unbind();
        
        // Convert date + time
        final String time = (tfTime.getText() != null) ? tfTime.getText() : PATTERN__TIME_IS_EMPTY;
        model.setGenerationTime(UtilProvider.getDefault().getDateConverter().convertDateTimeToLong(
                tfDate.getText() + SIGN__SPACE + time,
                PATTERN__DATETIME));
        
        // Save the Reflection file
        SqlProvider.getDefault().getReflectionSqlProvider().createOrUpdate(model);
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
        this.onActionRefreshGui(removeFile);
    }
    
    public void show(ReflectionModel model) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Show reflection: " + model.getTitle()); // NOI18N
        
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
        
        // Load all comments here
        lvComments.getItems().clear();
        if (this.model.getReflectionCommentModels().isEmpty()) {
            return;
        }
        
        final List<ReflectionCommentView> reflectionCommentViews = FXCollections.observableArrayList();
        for (ReflectionCommentModel reflectionCommentModel : this.model.getReflectionCommentModels()) {
            final ReflectionCommentView reflectionCommentView = new ReflectionCommentView();
            final ReflectionCommentPresenter presenter = reflectionCommentView.getRealPresenter();
        
            final String actionKeyForDeleting = this.registerOnDynamicActionDeleteComment();
            presenter.configure(reflectionCommentModel, actionKeyForDeleting);
            presenter.textProperty().addListener(stringChangeListener);
            
            reflectionCommentViews.add(reflectionCommentView);
        }
        
        lvComments.getItems().addAll(reflectionCommentViews);
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
