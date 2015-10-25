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
package de.pro.dbw.feature.tag.impl.tageditor;

import de.pro.dbw.core.configuration.api.application.IApplicationConfiguration;
import de.pro.dbw.core.configuration.api.application.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.application.defaultid.IDefaultIdConfiguration;
import de.pro.dbw.core.configuration.api.application.dialog.IDialogConfiguration;
import de.pro.dbw.core.configuration.api.application.util.IUtilConfiguration;
import de.pro.dbw.core.sql.provider.SqlProvider;
import de.pro.dbw.dialog.api.IDialogSize;
import de.pro.dbw.dialog.provider.DialogProvider;
import de.pro.dbw.feature.tag.api.ETagEditorMode;
import de.pro.dbw.feature.tag.api.TagCategoryModel;
import de.pro.dbw.feature.tag.api.TagModel;
import de.pro.dbw.feature.tag.impl.tagcategorychooser.TagCategoryChooserPresenter;
import de.pro.dbw.feature.tag.impl.tagcategorychooser.TagCategoryChooserView;
import de.pro.dbw.feature.tag.impl.tagcategoryeditor.TagCategoryEditorPresenter;
import de.pro.dbw.feature.tag.impl.tagcategoryeditor.TagCategoryEditorView;
import de.pro.dbw.util.provider.UtilProvider;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.action.api.ActionTransferModel;
import de.pro.lib.logger.api.LoggerFacade;
import de.pro.lib.properties.api.PropertiesFacade;
import java.awt.Dimension;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;

/**
 *
 * @author PRo
 */
public class TagEditorPresenter implements Initializable, IActionConfiguration, 
        IApplicationConfiguration, IDefaultIdConfiguration, IDialogSize, IUtilConfiguration
{
    private static final String CSS__TAG_EDITOR = "TagEditor.css"; // NOI18N
    
    @FXML private Button bClose;
    @FXML private Button bNew;
    @FXML private Button bSave;
    @FXML private Button bShowTagCategoryChooser;
    @FXML private FlowPane fpTagCategories;
    @FXML private ListView lvAvailableTags;
    @FXML private TextArea taDescription;
    @FXML private TextField tfTitle;
    
    private ETagEditorMode tagEditorMode = ETagEditorMode.OPEN_FROM_MENU;
    private String responseActionKey = null;
    private TagChangeListener tagChangeListener = null;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize TagEditorPresenter");
        
        assert (bClose != null)  : "fx:id=\"bAdd\" was not injected: check your FXML file 'TagEditor.fxml'."; // NOI18N
        assert (bNew != null)    : "fx:id=\"bRemove\" was not injected: check your FXML file 'TagEditor.fxml'."; // NOI18N
        assert (bSave != null)   : "fx:id=\"bSave\" was not injected: check your FXML file 'TagEditor.fxml'."; // NOI18N
        assert (bShowTagCategoryChooser != null)   : "fx:id=\"bShowTagCategoryChooser\" was not injected: check your FXML file 'TagEditor.fxml'."; // NOI18N
        assert (fpTagCategories != null) : "fx:id=\"fpTagCategories\" was not injected: check your FXML file 'TagEditor.fxml'."; // NOI18N
        assert (lvAvailableTags != null) : "fx:id=\"lvAvailableTags\" was not injected: check your FXML file 'TagEditor.fxml'."; // NOI18N
        assert (taDescription != null)   : "fx:id=\"taDescription\" was not injected: check your FXML file 'TagEditor.fxml'."; // NOI18N
        assert (tfTitle != null) : "fx:id=\"tfTitle\" was not injected: check your FXML file 'TagEditor.fxml'."; // NOI18N
        
        this.initializeButtons();
        this.initializeDescription();
        this.initializeListView();
        this.initializeTitle();
        
        this.onActionRefresh();
    }
    
    private void initializeButtons() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize Buttons");
        
        bSave.disableProperty().bind(Bindings.isEmpty(lvAvailableTags.getItems()));
    }
    
    private void initializeDescription() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize TextArea Description"); // NOI18N
        
        taDescription.setText(null);
    }
    
    private void initializeListView() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize ListView in Voting Editor"); // NOI18N

        lvAvailableTags.getStylesheets().addAll(this.getClass().getResource(CSS__TAG_EDITOR).toExternalForm());
        lvAvailableTags.getItems().clear();
        
        lvAvailableTags.setCellFactory((list) -> {
            return new ListCell<TagModel>() {
                @Override
                protected void updateItem(TagModel model, boolean empty) {
                    super.updateItem(model, empty);
                    
                    if (model == null || empty) {
                        super.setText(null);
                        super.setTooltip(null);
                        
                        return;
                    }
                    
                    super.setText(model.getTitle());
                    super.setTooltip(new Tooltip(model.getDescription()));
                }
            };
        });
        
        tagChangeListener = new TagChangeListener();
        lvAvailableTags.getSelectionModel().selectedItemProperty().addListener(tagChangeListener);
    }
    
    private void initializeTitle() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize TextField title"); // NOI18N
        
        tfTitle.setText(null);
    }
    
    public void configure(ETagEditorMode tagCategoryEditorMode) {
        this.configure(tagCategoryEditorMode, null);
    }

    public void configure(ETagEditorMode tagCategoryEditorMode, String responseActionKey) {
        this.tagEditorMode = tagCategoryEditorMode;
        this.responseActionKey = responseActionKey;
    }
    
    private List<Node> convertTagCategoryModels(List<TagCategoryModel> tagCategories) {
        final List<Node> nodes = FXCollections.observableArrayList();
        if (tagCategories == null || tagCategories.isEmpty()) {
            return nodes;
        }
        
        for (TagCategoryModel model : tagCategories) {
            final Label node = new Label(model.getTitle());
            node.setTextFill(UtilProvider.getDefault().getColorConverter().convertStringToColor(model.getColor()));
            node.setUserData(model);
            nodes.add(node);
        }
        
        return nodes;
    }
    
    @Override
    public Dimension getSize() {
        return IDialogConfiguration.SIZE__W495_H330;
    }
    
    public void onActionClose() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Close"); // NOI18N
        
        if (responseActionKey != null) {
            ActionFacade.INSTANCE.handle(responseActionKey);
            ActionFacade.INSTANCE.remove(responseActionKey);
        }
        
        switch (tagEditorMode) {
            case OPEN_FROM_MENU:   { DialogProvider.getDefault().hide(); break; }
            case OPEN_FROM_WIZARD: { DialogProvider.getDefault().hide2(); break; }
        }
    }
    
    public void onActionNew() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action New"); // NOI18N
        
        // Check if a new Tag exists
        final List<TagModel> allTags = FXCollections.observableArrayList();
        allTags.addAll(lvAvailableTags.getItems());
        for (TagModel model : allTags) {
            if (Objects.equals(model.getId(), FEATURE__TAG__DEFAULT_ID)) {
                Platform.runLater(() -> {
                    lvAvailableTags.getSelectionModel().select(model);
                    lvAvailableTags.scrollTo(model);
                });
                
                return;
            }
        }
        
        // Create new one
        final TagModel model = new TagModel();
        model.setGenerationTime(System.currentTimeMillis());
        model.setId(FEATURE__TAG__DEFAULT_ID);
        model.setTagCategoryModels(new ArrayList<>());
        model.setDescription(SIGN__EMPTY);
        model.setMarkAsChanged(Boolean.TRUE);// TODO true == * before the title
        model.setTitle(PropertiesFacade.INSTANCE.getProperty(DBW__RESOURCE_BUNDLE, KEY__APPLICATION__PREFIX_NEW));

        allTags.add(model);
        this.select(model, allTags);
    }
    
    private void onActionRefresh() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Refresh"); // NOI18N
        
        final List<TagModel> allTags = FXCollections.observableArrayList();
        allTags.addAll(SqlProvider.getDefault().getTagSqlProvider().findAll());
        if (allTags.isEmpty()) {
            tfTitle.setText(null);
            fpTagCategories.getChildren().clear();
            taDescription.setText(null);
            
            lvAvailableTags.getSelectionModel().selectedItemProperty().removeListener(tagChangeListener);
            lvAvailableTags.getItems().clear();
                
            return;
        }
        
        final TagModel model = (TagModel) lvAvailableTags.getSelectionModel().getSelectedItem();
        this.select(model, allTags);
    }
    
    public void onActionSave() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Save"); // NOI18N
        /*
        TODO
            - The title must be unique (only [A-Z][a-z][0-9] + leer + - + _)
        */
        final TagModel model = (TagModel) lvAvailableTags.getSelectionModel().getSelectedItem();
        model.setTitle(tfTitle.getText());
        model.setDescription(taDescription.getText());
        
        final List<TagCategoryModel> tagCategories = FXCollections.observableArrayList();
        for (Node node : fpTagCategories.getChildren()) {
            if (!(node instanceof Label)) {
                continue;
            }
            
            final Label label = (Label) node;
            final TagCategoryModel tagCategoryModel = (TagCategoryModel) label.getUserData();
            tagCategories.add(tagCategoryModel);
        }
        model.setTagCategoryModels(tagCategories);
        
        SqlProvider.getDefault().getTagSqlProvider().createOrUpdate(model, FEATURE__TAG__DEFAULT_ID);
        
        // Update gui
        final List<TagModel> allTags = FXCollections.observableArrayList();
        allTags.addAll(lvAvailableTags.getItems());
        this.select(model, allTags);
    }
    
    public void onActionShowTagCategoryChooser() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action show TagCategoryChooser"); // NOI18N
        
        final TagCategoryChooserView contentView = new TagCategoryChooserView();
        final TagCategoryChooserPresenter contentPresenter = contentView.getRealPresenter();
        final List<TagCategoryModel> existingTagCategories = FXCollections.observableArrayList();
        for (Node node : fpTagCategories.getChildren()) {
            if (!(node instanceof Label)) {
                continue;
            }
            
            final Label label = (Label) node;
            final TagCategoryModel tagCategoryModel = (TagCategoryModel) label.getUserData();
            existingTagCategories.add(tagCategoryModel);
        }
        
        final String responseActionKeyFromTagCategoryChooser = this.registerOnDynamicActionRefreshFromTagCategoryChooser();
        contentPresenter.configure(existingTagCategories, responseActionKeyFromTagCategoryChooser);
        
        final String title = PropertiesFacade.INSTANCE.getProperty(DBW__RESOURCE_BUNDLE, KEY__FEATURE_TAG__TAG_CATEGORY_CHOOSER_TITLE);
        DialogProvider.getDefault().show2(title, contentView.getView(), contentPresenter.getSize());
    }
    
    public void onActionShowTagCategoryEditor() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action show TagCategoryEditor"); // NOI18N
        
        final TagCategoryEditorView contentView = new TagCategoryEditorView();
        final TagCategoryEditorPresenter contentPresenter = contentView.getRealPresenter();
        final String responseActionKeyFromTagCategoryEditor = this.registerOnDynamicActionRefreshFromTagCategoryEditor();
        contentPresenter.configure(ETagEditorMode.OPEN_FROM_WIZARD, responseActionKeyFromTagCategoryEditor);
        
        final String title = PropertiesFacade.INSTANCE.getProperty(DBW__RESOURCE_BUNDLE, KEY__FEATURE_TAG__TAG_CATEGORY_EDTIOR_TITLE);
        DialogProvider.getDefault().show2(title, contentView.getView(), contentPresenter.getSize());
    }
    
    private void select(TagModel model, final List<TagModel> allTags) {
            
        Platform.runLater(() -> {
            Collections.sort(allTags);
            
            lvAvailableTags.getSelectionModel().selectedItemProperty().removeListener(tagChangeListener);
            lvAvailableTags.getItems().clear();
            lvAvailableTags.getItems().addAll(allTags);
            lvAvailableTags.getSelectionModel().selectedItemProperty().addListener(tagChangeListener);
            
            final TagModel modelToSelect = (model != null) ? model : allTags.get(0);
            lvAvailableTags.getSelectionModel().select(modelToSelect);
            lvAvailableTags.scrollTo(modelToSelect);
        });
    }
    
    private String registerOnDynamicActionRefreshFromTagCategoryChooser() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on dynamic action refresh from TagCategoryChooser"); // NOI18N
    
        final String responseActionKeyFromTagCategoryChooser = ACTION__REFRESH_ + System.currentTimeMillis();
        ActionFacade.INSTANCE.register(
                responseActionKeyFromTagCategoryChooser,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    final List<TagCategoryModel> selectedTagCategories = (List<TagCategoryModel>) actionTransferModel.getObject();
                    if (selectedTagCategories.isEmpty()) {
                        fpTagCategories.getChildren().clear();
                        // todo mark tag as changed
                        return;
                    }
                    
                    fpTagCategories.getChildren().clear();
                    fpTagCategories.getChildren().addAll(this.convertTagCategoryModels(selectedTagCategories));
                });
        
        return responseActionKeyFromTagCategoryChooser;
    }
    
    private String registerOnDynamicActionRefreshFromTagCategoryEditor() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on dynamic action refresh from TagCategoryEditor"); // NOI18N
    
        final String responseActionKeyFromTagCategoryEditor = ACTION__REFRESH_ + System.currentTimeMillis();
        ActionFacade.INSTANCE.register(
                responseActionKeyFromTagCategoryEditor,
                (ActionEvent ae) -> {
                    this.onActionRefresh();
                });
        
        return responseActionKeyFromTagCategoryEditor;
    }
    
    private class TagChangeListener implements ChangeListener<TagModel> {

        @Override
        public void changed(ObservableValue<? extends TagModel> observable, TagModel oldValue, TagModel model) {
            if (model == null) {
                tfTitle.setText(null);
                fpTagCategories.getChildren().clear();
                bShowTagCategoryChooser.setDisable(Boolean.TRUE);
                taDescription.setText(null);
                
                return;
            }
            
            tfTitle.setText(model.getTitle());
            fpTagCategories.getChildren().clear();
            fpTagCategories.getChildren().addAll(convertTagCategoryModels(model.getTagCategoryModels()));
            bShowTagCategoryChooser.setDisable(Boolean.FALSE);
            taDescription.setText(model.getDescription());
        }
    }
}
