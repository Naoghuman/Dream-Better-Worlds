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
package de.pro.dbw.feature.tag.impl.tagcategoryeditor;

import de.pro.dbw.core.configuration.api.application.IApplicationConfiguration;
import de.pro.dbw.core.configuration.api.application.defaultid.IDefaultIdConfiguration;
import de.pro.dbw.core.configuration.api.application.dialog.IDialogConfiguration;
import de.pro.dbw.core.configuration.api.application.util.IUtilConfiguration;
import de.pro.dbw.core.sql.provider.SqlProvider;
import de.pro.dbw.dialog.api.IDialogSize;
import de.pro.dbw.dialog.provider.DialogProvider;
import de.pro.dbw.feature.tag.api.ETagEditorMode;
import de.pro.dbw.feature.tag.api.TagCategoryModel;
import de.pro.dbw.util.provider.UtilProvider;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.logger.api.LoggerFacade;
import de.pro.lib.properties.api.PropertiesFacade;
import java.awt.Dimension;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;

/**
 *
 * @author PRo
 */
public class TagCategoryEditorPresenter implements Initializable, IApplicationConfiguration,
        IDefaultIdConfiguration, IDialogSize, IUtilConfiguration
{
    private static final String CSS__TAG_CATEGORY_EDITOR = "TagCategoryEditor.css"; // NOI18N
    
    @FXML private Button bClose;
    @FXML private Button bNew;
    @FXML private Button bSave;
    @FXML private ColorPicker cpColor;
    @FXML private ListView lvAvailableTagCategories;
    @FXML private TextArea taDescription;
    @FXML private TextField tfTitle;
    
    private ETagEditorMode tagCategoryEditorMode = ETagEditorMode.OPEN_FROM_MENU;
    private String responseActionKey = null;
    private TagCategoryChangeListener tagCategoryChangeListener = null;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize TagCategoryEditorPresenter");
        
        assert (bClose != null)  : "fx:id=\"bAdd\" was not injected: check your FXML file 'TagCategoryEditor.fxml'."; // NOI18N
        assert (bNew != null)    : "fx:id=\"bRemove\" was not injected: check your FXML file 'TagCategoryEditor.fxml'."; // NOI18N
        assert (bSave != null)   : "fx:id=\"bSave\" was not injected: check your FXML file 'TagCategoryEditor.fxml'."; // NOI18N
        assert (cpColor != null) : "fx:id=\"cpColor\" was not injected: check your FXML file 'TagCategoryEditor.fxml'."; // NOI18N
        assert (lvAvailableTagCategories != null) : "fx:id=\"lvAvailableTagCategories\" was not injected: check your FXML file 'TagCategoryEditor.fxml'."; // NOI18N
        assert (taDescription != null)            : "fx:id=\"taDescription\" was not injected: check your FXML file 'TagCategoryEditor.fxml'."; // NOI18N
        assert (tfTitle != null) : "fx:id=\"tfTitle\" was not injected: check your FXML file 'TagCategoryEditor.fxml'."; // NOI18N
        
        this.initializeButtons();
        this.initializeDescription();
        this.initializeListView();
        this.initializeTitle();
        
        this.onActionRefresh();
    }
    
    private void initializeButtons() {
        LoggerFacade.getDefault().debug(this.getClass(), "Initialize Buttons");
        
        bSave.disableProperty().bind(Bindings.isEmpty(lvAvailableTagCategories.getItems()));
    }
    
    private void initializeDescription() {
        LoggerFacade.getDefault().debug(this.getClass(), "Initialize TextArea Description"); // NOI18N
        
        taDescription.setText(null);
    }
    
    private void initializeListView() {
        LoggerFacade.getDefault().debug(this.getClass(), "Initialize ListView in Voting Editor"); // NOI18N

        lvAvailableTagCategories.getStylesheets().addAll(this.getClass().getResource(CSS__TAG_CATEGORY_EDITOR).toExternalForm());
        lvAvailableTagCategories.getItems().clear();
        
        lvAvailableTagCategories.setCellFactory((list) -> {
            return new ListCell<TagCategoryModel>() {
                @Override
                protected void updateItem(TagCategoryModel model, boolean empty) {
                    super.updateItem(model, empty);
                    
                    if (model == null || empty) {
                        super.setText(null);
                        super.setTextFill(null);
                        super.setTooltip(null);
                        
                        return;
                    }
                    
                    // TODO with css can the background color styled
                    super.setText(model.getTitle());
                    super.setTextFill(UtilProvider.getDefault().getColorConverter().convertStringToColor(model.getColor()));
                    super.setTooltip(new Tooltip(model.getDescription()));
                }
            };
        });
        
        tagCategoryChangeListener = new TagCategoryChangeListener();
        lvAvailableTagCategories.getSelectionModel().selectedItemProperty().addListener(tagCategoryChangeListener);
    }
    
    private void initializeTitle() {
        LoggerFacade.getDefault().debug(this.getClass(), "Initialize TextField title"); // NOI18N
        
        tfTitle.setText(null);
    }
    
    public void configure(ETagEditorMode tagCategoryEditorMode) {
        this.configure(tagCategoryEditorMode, null);
    }

    public void configure(ETagEditorMode tagCategoryEditorMode, String responseActionKey) {
        this.tagCategoryEditorMode = tagCategoryEditorMode;
        this.responseActionKey = responseActionKey;
    }
    
    @Override
    public Dimension getSize() {
        return IDialogConfiguration.SIZE__W495_H330;
    }
    
    public void onActionClose() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action Close"); // NOI18N
        
        if (responseActionKey != null) {
            ActionFacade.getDefault().handle(responseActionKey);
            ActionFacade.getDefault().remove(responseActionKey);
        }
        
        switch (tagCategoryEditorMode) {
            case OPEN_FROM_MENU:   { DialogProvider.getDefault().hide(); break; }
            case OPEN_FROM_WIZARD: { DialogProvider.getDefault().hide2(); break; }
        }
    }
    
    public void onActionNew() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action New"); // NOI18N
        
        // Check if a new TagCategory exists
        final List<TagCategoryModel> allTagCategories = FXCollections.observableArrayList();
        allTagCategories.addAll(lvAvailableTagCategories.getItems());
        
        for (TagCategoryModel model : allTagCategories) {
            if (Objects.equals(model.getId(), FEATURE__TAG_CATEGORY__DEFAULT_ID)) {
                Platform.runLater(() -> {
                    lvAvailableTagCategories.getSelectionModel().select(model);
                    lvAvailableTagCategories.scrollTo(model);
                });
                
                return;
            }
        }
        
        // Create new one
        final TagCategoryModel model = new TagCategoryModel();
        model.setGenerationTime(System.currentTimeMillis());
        model.setId(FEATURE__TAG_CATEGORY__DEFAULT_ID);
        model.setColor(UtilProvider.getDefault().getColorConverter().convertColorToString(Color.WHITE));
        model.setDescription(SIGN__EMPTY);
        model.setMarkAsChanged(Boolean.TRUE);// TODO true == * before the title
        model.setTitle(PropertiesFacade.getDefault().getProperty(DBW__RESOURCE_BUNDLE, KEY__APPLICATION__PREFIX_NEW));

        allTagCategories.add(model);
        this.select(model, allTagCategories);
    }
    
    private void onActionRefresh() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action Refresh"); // NOI18N
        
        final TagCategoryModel model = (TagCategoryModel) lvAvailableTagCategories.getSelectionModel()
                .getSelectedItem();
        
        final List<TagCategoryModel> allTagCategories = FXCollections.observableArrayList();
        allTagCategories.addAll(SqlProvider.getDefault().getTagCategorySqlProvider().findAll());
        if (allTagCategories.isEmpty()) {
            tfTitle.setText(null);
            cpColor.setValue(Color.WHITE);
            taDescription.setText(null);
            
            lvAvailableTagCategories.getSelectionModel().selectedItemProperty().removeListener(tagCategoryChangeListener);
            lvAvailableTagCategories.getItems().clear();
                
            return;
        }
        
        this.select(model, allTagCategories);
    }
    
    public void onActionSave() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action Save"); // NOI18N
        /*
        TODO
            - The title must be unique (only [A-Z][a-z][0-9] + leer + - + _)
        */
        final TagCategoryModel model = (TagCategoryModel) lvAvailableTagCategories.getSelectionModel()
                .getSelectedItem();
        model.setTitle(tfTitle.getText());
        model.setDescription(taDescription.getText());
        model.setColor(UtilProvider.getDefault().getColorConverter().convertColorToString(cpColor.getValue()));
        
        SqlProvider.getDefault().getTagCategorySqlProvider().createOrUpdate(model, FEATURE__TAG_CATEGORY__DEFAULT_ID);
        
        // Update gui
        final List<TagCategoryModel> allTagCategories = FXCollections.observableArrayList();
        allTagCategories.addAll(lvAvailableTagCategories.getItems());
        this.select(model, allTagCategories);
    }
    
    private void select(TagCategoryModel model, final List<TagCategoryModel> allTagCategories) {
            
        Platform.runLater(() -> {
            Collections.sort(allTagCategories);
            
            lvAvailableTagCategories.getSelectionModel().selectedItemProperty().removeListener(tagCategoryChangeListener);
            lvAvailableTagCategories.getItems().clear();
            lvAvailableTagCategories.getItems().addAll(allTagCategories);
            lvAvailableTagCategories.getSelectionModel().selectedItemProperty().addListener(tagCategoryChangeListener);
            
            final TagCategoryModel modelToSelect = (model != null) ? model : allTagCategories.get(0);
            lvAvailableTagCategories.getSelectionModel().select(modelToSelect);
            lvAvailableTagCategories.scrollTo(modelToSelect);
        });
    }
    
    private class TagCategoryChangeListener implements ChangeListener<TagCategoryModel> {

        @Override
        public void changed(ObservableValue<? extends TagCategoryModel> observable, TagCategoryModel oldValue, TagCategoryModel model) {
            if (model == null) {
                tfTitle.setText(null);
                cpColor.setValue(Color.WHITE);
                taDescription.setText(null);
                
                return;
            }
            
            tfTitle.setText(model.getTitle());
            cpColor.setValue(UtilProvider.getDefault().getColorConverter().convertStringToColor(model.getColor()));
            taDescription.setText(model.getDescription());
        }
    }
}
