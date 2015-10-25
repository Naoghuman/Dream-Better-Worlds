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
package de.pro.dbw.feature.tag.impl.tagcategorychooser;

import de.pro.dbw.base.component.api.listcell.checkbox.CheckBoxListCellManager;
import de.pro.dbw.base.component.api.listcell.checkbox.CheckBoxListCellModel;
import de.pro.dbw.base.provider.BaseProvider;
import de.pro.dbw.core.configuration.api.application.IApplicationConfiguration;
import de.pro.dbw.core.configuration.api.application.defaultid.IDefaultIdConfiguration;
import de.pro.dbw.core.configuration.api.application.dialog.IDialogConfiguration;
import de.pro.dbw.core.configuration.api.application.util.IUtilConfiguration;
import de.pro.dbw.core.sql.provider.SqlProvider;
import de.pro.dbw.dialog.api.IDialogSize;
import de.pro.dbw.dialog.provider.DialogProvider;
import de.pro.dbw.feature.tag.api.TagCategoryModel;
import de.pro.dbw.util.provider.UtilProvider;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.action.api.ActionTransferModel;
import de.pro.lib.logger.api.LoggerFacade;
import java.awt.Dimension;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 *
 * @author PRo
 */
public class TagCategoryChooserPresenter implements Initializable, IApplicationConfiguration,
        IDefaultIdConfiguration, IDialogSize, IUtilConfiguration
{
    private static final String CSS__TAG_CATEGORY_CHOOSER = "TagCategoryChooser.css"; // NOI18N
    
    @FXML private Button bCancel;
    @FXML private Button bOkay;
    @FXML private ListView lvAvailableTagCategories;
    
    private String responseActionKey = null;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize TagCategoryChooserPresenter");
        
        assert (bCancel != null)  : "fx:id=\"bAdd\" was not injected: check your FXML file 'TagCategoryChooser.fxml'."; // NOI18N
        assert (bOkay != null)    : "fx:id=\"bRemove\" was not injected: check your FXML file 'TagCategoryChooser.fxml'."; // NOI18N
        assert (lvAvailableTagCategories != null) : "fx:id=\"lvAvailableTagCategories\" was not injected: check your FXML file 'TagCategoryChooser.fxml'."; // NOI18N
        
        this.initializeListView();
    }
    
    private void initializeListView() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize ListView in TagCategoryChooser"); // NOI18N

        final int maxSelectedCheckBoxes = 3;
        CheckBoxListCellManager.configure(maxSelectedCheckBoxes);
        
        lvAvailableTagCategories.getStylesheets().addAll(this.getClass().getResource(CSS__TAG_CATEGORY_CHOOSER).toExternalForm());
        lvAvailableTagCategories.getItems().clear();
        
        lvAvailableTagCategories.setCellFactory(BaseProvider.getDefault().getComponentProvider()
                .getCheckBoxListCellCallback(CheckBoxListCellModel::selectedProperty, CheckBoxListCellModel::disableProperty));
    }

    public void configure(List<TagCategoryModel> selectedTagCategories, String responseActionKey) {
        this.responseActionKey = responseActionKey;
        
        Platform.runLater(() -> {
            // Load all TagCategoryModels
            final List<TagCategoryModel> allTagCategories = FXCollections.observableArrayList();
            allTagCategories.addAll(SqlProvider.getDefault().getTagCategorySqlProvider().findAll());
            Collections.sort(allTagCategories);
            
            // Convert to CheckBoxListCellModel
            final List<CheckBoxListCellModel> allCheckBoxListCellModels = FXCollections.observableArrayList();
            for (TagCategoryModel tagCategoryModel : allTagCategories) {
                final CheckBoxListCellModel checkBoxListCellModel = new CheckBoxListCellModel();
                checkBoxListCellModel.setCheckBoxGraphic(this.getNode(tagCategoryModel.getTitle(), tagCategoryModel.getColor()));
                checkBoxListCellModel.setModel(tagCategoryModel);
                checkBoxListCellModel.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                    final int addToCurrentSelectedCheckBoxes = isSelected ? 1 : -1;
                    CheckBoxListCellManager.check(addToCurrentSelectedCheckBoxes, lvAvailableTagCategories.getItems());
                });
                
                allCheckBoxListCellModels.add(checkBoxListCellModel);
            }
            
            // Add them
            lvAvailableTagCategories.getItems().clear();
            lvAvailableTagCategories.getItems().addAll(allCheckBoxListCellModels);
            
            // Select them
            for (CheckBoxListCellModel checkBoxListCellModel : allCheckBoxListCellModels) {
                final TagCategoryModel tagCategoryModel = (TagCategoryModel) checkBoxListCellModel.getModel();
                for (TagCategoryModel selectedTagCategoryModel : selectedTagCategories) {
                    if (Objects.equals(selectedTagCategoryModel.getId(), tagCategoryModel.getId())) {
                        checkBoxListCellModel.setSelected(Boolean.TRUE);
                        break;
                    }
                }
            }
        });
    }
    
    private Node getNode(String text, String textFill) {
        final Label node = new Label();
        node.setText(text);
        node.setTextFill(UtilProvider.getDefault().getColorConverter().convertStringToColor(textFill));
        
        return node;
    }
    
    @Override
    public Dimension getSize() {
        return IDialogConfiguration.SIZE__W200_H300;
    }
    
    public void onActionCancel() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Close"); // NOI18N

        if (responseActionKey != null) {
            ActionFacade.INSTANCE.remove(responseActionKey);
        }
        
        DialogProvider.getDefault().hide2();
    }
    
    public void onActionOkay() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Okay"); // NOI18N

        if (responseActionKey == null) {
            DialogProvider.getDefault().hide2();
            return;
        }
        
        // Catch selected TagCategoryModels
        final List<CheckBoxListCellModel> allCheckBoxListCellModels = lvAvailableTagCategories.getItems();
        final List<TagCategoryModel> allTagCategories = FXCollections.observableArrayList();
        for (CheckBoxListCellModel checkBoxListCellModel : allCheckBoxListCellModels) {
            if (checkBoxListCellModel.isSelected()) {
                allTagCategories.add((TagCategoryModel) checkBoxListCellModel.getModel());
            }
        }
        
        // Add them
        final ActionTransferModel actionTransferModel = new ActionTransferModel();
        actionTransferModel.setObject(allTagCategories);
        actionTransferModel.setActionKey(responseActionKey);
        
        // Fire action
        ActionFacade.INSTANCE.handle(actionTransferModel);
        ActionFacade.INSTANCE.remove(responseActionKey);
        
        DialogProvider.getDefault().hide2();
    }
    
}
