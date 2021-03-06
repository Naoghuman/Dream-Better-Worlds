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
package de.pro.dbw.file.provider;

import de.jensd.fx.glyphs.weathericons.WeatherIcon;
import de.pro.dbw.core.configuration.api.application.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.application.action.IRegisterActions;
import de.pro.dbw.core.configuration.api.application.dialog.IDialogConfiguration;
import de.pro.dbw.core.configuration.api.application.util.IUtilConfiguration;
import de.pro.dbw.core.sql.provider.SqlProvider;
import de.pro.dbw.dialog.provider.DialogProvider;
import de.pro.dbw.file.reflection.api.ReflectionModel;
import de.pro.dbw.file.reflection.impl.reflection.ReflectionPresenter;
import de.pro.dbw.file.reflection.impl.reflection.ReflectionView;
import de.pro.dbw.file.reflection.impl.reflectionwizard.ReflectionWizardPresenter;
import de.pro.dbw.file.reflection.impl.reflectionwizard.ReflectionWizardView;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.action.api.ActionTransferModel;
import de.pro.lib.logger.api.LoggerFacade;
import de.pro.lib.properties.api.PropertiesFacade;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author PRo
 */
public class ReflectionProvider implements IActionConfiguration, IDialogConfiguration,
        IRegisterActions, IUtilConfiguration
{
    private static ReflectionProvider instance = null;
    
    public static ReflectionProvider getDefault() {
        if (instance == null) {
            instance = new ReflectionProvider();
        }
        
        return instance;
    }
    
    private TabPane tpEditor = null;
    
    private ReflectionProvider() {
        this.initialize();
    }
    
    private void initialize() {
        
    }
    
    public void register(TabPane tpEditor) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Register TabPane editor in ReflectionProvider"); // NOI18N
        
        this.tpEditor = tpEditor;
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register actions in ReflectionProvider"); // NOI18N
        
        this.registerOnActionCreateNewFileReflection();
        this.registerOnActionEditFileReflection();
        this.registerOnActionOpenFileReflectionFromNavigation();
    }

    private void registerOnActionCreateNewFileReflection() {
        ActionFacade.INSTANCE.register(
                ACTION__CREATE_NEW_FILE__REFLECTION,
                (ActionEvent ae) -> {
                    LoggerFacade.INSTANCE.debug(this.getClass(), "Show Reflection Wizard in CREATE mode."); // NOI18N

                    final ReflectionWizardView view = new ReflectionWizardView();
                    final ReflectionWizardPresenter presenter = view.getRealPresenter();
                    presenter.configureWizardForCreateMode();
                    
                    final String title = PropertiesFacade.INSTANCE.getProperty(DIALOG__RESOURCE_BUNDLE, KEY__DIALOG_TITLE__REFLECTION_WIZARD);
                    DialogProvider.getDefault().show(title, view.getView(), presenter.getSize());
                });
    }

    private void registerOnActionEditFileReflection() {
        ActionFacade.INSTANCE.register(
                ACTION__EDIT_FILE__REFLECTION,
                (ActionEvent ae) -> {
                    LoggerFacade.INSTANCE.debug(this.getClass(), "Show Reflection Wizard in EDIT mode."); // NOI18N

                    final ReflectionWizardView view = new ReflectionWizardView();
                    final ReflectionWizardPresenter presenter = view.getRealPresenter();
                    // TODO catch the ReflectionModel from ae.getSource()
                    presenter.configureWizardForEditMode(new ReflectionModel());
                    
                    final String title = PropertiesFacade.INSTANCE.getProperty(DIALOG__RESOURCE_BUNDLE, KEY__DIALOG_TITLE__REFLECTION_WIZARD);
                    DialogProvider.getDefault().show(title, view.getView(), presenter.getSize());
                });
    }
    
    private void registerOnActionOpenFileReflectionFromNavigation() {
        ActionFacade.INSTANCE.register(
                ACTION__OPEN_REFLECTION__FROM_NAVIGATION,
                (ActionEvent ae) -> {
                    final ActionTransferModel transferModel = (ActionTransferModel) ae.getSource();
                    this.show(transferModel.getLong());
                });
    }
    
    public void saveAll() {
        for (Tab tab : tpEditor.getTabs()) {
            if (tab.getUserData() instanceof ReflectionPresenter) {
                final ReflectionPresenter presenter = (ReflectionPresenter) tab.getUserData();
                if (presenter.isMarkAsChanged()) {
                    final Boolean updateGui = Boolean.FALSE;
                    presenter.onActionSave(updateGui);
                }
            }
        }
    }
    
    private void show(Long reflectionId) {
        // Check if the Reflection file is always open
        for (Tab tab : tpEditor.getTabs()) {
            if (tab.getId().equals(String.valueOf(reflectionId))) {
                tpEditor.getSelectionModel().select(tab);
                return;
            }
        }
        
        // Load content and show it
        final ReflectionModel model = SqlProvider.getDefault().getReflectionSqlProvider().findReflectionById(reflectionId);
        if (model == null) {
            return;
        }
        
        this.show(model);
    }
    
    private void show(ReflectionModel model) {
        final ReflectionView view = new ReflectionView();
        final ReflectionPresenter presenter = view.getRealPresenter();
        presenter.show(model);
        
        final Tab tab = new Tab();
        final HBox hBox = new HBox();
        final WeatherIcon weatherIcon = new WeatherIcon();
        weatherIcon.setGlyphName("ALIEN");
        weatherIcon.setSize("12");
        hBox.getChildren().add(weatherIcon);
        
        final Label lMarkAsChanged = new Label();
        lMarkAsChanged.setPadding(new Insets(0.0d, 0.0d, 0.0d, 5.0d));
        lMarkAsChanged.textProperty().bind(
                Bindings.when(model.markAsChangedProperty())
                        .then(SIGN__STAR)
                        .otherwise(SIGN__EMPTY));
        lMarkAsChanged.managedProperty().bind(
                Bindings.when(lMarkAsChanged.textProperty().isEmpty())
                        .then(false)
                        .otherwise(true));
        hBox.getChildren().add(lMarkAsChanged);
        
        final Label lText = new Label();
        lText.setText(model.getTitle());
        lText.textProperty().bind(model.titleProperty());
        lText.paddingProperty().bind(
                Bindings.when(model.markAsChangedProperty())
                        .then(Insets.EMPTY)
                        .otherwise(new Insets(0.0d, 0.0d, 0.0d, 5.0d)));
        hBox.getChildren().add(lText);
        tab.setGraphic(hBox);
        
        tab.setContent(view.getView());
        tab.setId(String.valueOf(model.getId()));
        tab.idProperty().bind(StringBinding.stringExpression(model.idProperty()));
        tab.setOnCloseRequest((Event event) -> {
            if (model.isMarkAsChanged()) {
                event.consume();
                this.showReflectionSaveDialog(presenter, model.getId());
            }
        });
        tab.setUserData(presenter);
        
        tpEditor.getTabs().add(tab);
        tpEditor.getSelectionModel().select(tab);
    }
    
    private void showReflectionSaveDialog(ReflectionPresenter presenter, Long idToRemove) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Show Save Reflection Dialog"); // NOI18N
        
        final ActionTransferModel transferModel = new ActionTransferModel();
        transferModel.setActionKey(ACTION__REMOVE_FILE_FROM_EDITOR);
        transferModel.setLong(idToRemove);
        
        DialogProvider.getDefault().showSaveSingleFileDialog(
                (ActionEvent ae) -> { // Yes
                    presenter.onActionSave();
                    
                    DialogProvider.getDefault().hide();
                    ActionFacade.INSTANCE.handle(transferModel);
                },
                (ActionEvent ae) -> { // No
                    presenter.onActionRefresh();
                    
                    DialogProvider.getDefault().hide();
                    ActionFacade.INSTANCE.handle(transferModel);
                });
    }
    
}
