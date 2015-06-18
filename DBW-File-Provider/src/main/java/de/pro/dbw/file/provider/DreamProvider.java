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
package de.pro.dbw.file.provider;

import de.jensd.fx.glyphs.weathericons.WeatherIcon;
import de.pro.dbw.file.dream.api.DreamModel;
import de.pro.dbw.core.configuration.api.application.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.application.action.IRegisterActions;
import de.pro.dbw.core.configuration.api.application.defaultid.IDefaultIdConfiguration;
import de.pro.dbw.core.configuration.api.application.util.IUtilConfiguration;
import de.pro.dbw.dialog.provider.DialogProvider;
import de.pro.dbw.file.dream.impl.dream.DreamPresenter;
import de.pro.dbw.file.dream.impl.dream.DreamView;
import de.pro.dbw.file.dream.impl.dreamwizardcontent.DreamWizardContentView;
import de.pro.dbw.core.sql.provider.SqlProvider;
import de.pro.dbw.dialog.impl.dialogtemplate.DialogTemplatePresenter;
import de.pro.dbw.dialog.impl.dialogtemplate.DialogTemplateView;
import de.pro.dbw.file.dream.impl.dreamwizardcontent.DreamWizardContentPresenter;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.action.api.ActionTransferModel;
import de.pro.lib.logger.api.LoggerFacade;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author PRo
 */
public final class DreamProvider implements IActionConfiguration, IDefaultIdConfiguration, 
        IUtilConfiguration, IRegisterActions
{
    private static DreamProvider instance = null;
    
    public static DreamProvider getDefault() {
        if (instance == null) {
            instance = new DreamProvider();
        }
        
        return instance;
    }
    
    private TabPane tpEditor = null;
    
    private DreamProvider() {
        this.initialize();
    }
    
    private void initialize() {
        
    }
    
    public void register(TabPane tpEditor) {
        LoggerFacade.getDefault().info(this.getClass(), "Register TabPane editor in DreamProvider"); // NOI18N
        
        this.tpEditor = tpEditor;
    }

    @Override
    public void registerActions() {
        LoggerFacade.getDefault().debug(this.getClass(), "Register actions in DreamProvider"); // NOI18N
        
        this.registerOnActionCreateNewDream();
        this.registerOnActionCreateNewFastDream();
        this.registerOnActionOpenDreamFromNavigation();
    }
    
    private void registerOnActionCreateNewDream() {
        ActionFacade.getDefault().register(
                ACTION__CREATE_NEW_DREAM,
                (ActionEvent ae) -> {
                    this.show();
                });
    }
    
    private void registerOnActionCreateNewFastDream() {
        ActionFacade.getDefault().register(
                ACTION__CREATE_NEW_FAST_DREAM,
                (ActionEvent ae) -> {
                    this.showDreamWizard();
                });
    }
    
    private void registerOnActionOpenDreamFromNavigation() {
        ActionFacade.getDefault().register(
                ACTION__OPEN_DREAM__FROM_NAVIGATION,
                (ActionEvent ae) -> {
                    final ActionTransferModel transferModel = (ActionTransferModel) ae.getSource();
                    this.show(transferModel.getLong());
                });
    }
    
    public void saveAll() {
        for (Tab tab : tpEditor.getTabs()) {
            if (tab.getUserData() instanceof DreamPresenter) {
                final DreamPresenter presenter = (DreamPresenter) tab.getUserData();
                if (presenter.isMarkAsChanged()) {
                    final Boolean updateGui = Boolean.FALSE;
                    presenter.onActionSave(updateGui);
                }
            }
        }
    }
    
    private void show() {
        // Check if a new dream is open
        for (Tab tab : tpEditor.getTabs()) {
            if (tab.getId().equals(String.valueOf(FILE__DREAM__DEFAULT_ID))) {
                tpEditor.getSelectionModel().select(tab);
                return;
            }
        }
        
        // Create new dream
        final DreamModel model = new DreamModel();
        model.setMarkAsChanged(Boolean.TRUE);
        this.show(model);
    }
    
    private void show(Long dreamId) {
        // Check if the dream is always open
        for (Tab tab : tpEditor.getTabs()) {
            if (tab.getId().equals(String.valueOf(dreamId))) {
                tpEditor.getSelectionModel().select(tab);
                return;
            }
        }
        
        // Load content and show it
        final DreamModel model = SqlProvider.getDefault().getDreamSqlProvider().findById(dreamId);
        if (model == null) {
            return;
        }
        
        this.show(model);
    }
    
    private void show(DreamModel model) {
        final DreamView view = new DreamView();
        final DreamPresenter presenter = view.getRealPresenter();
        presenter.show(model);
        
        final Tab tab = new Tab();
        final HBox hBox = new HBox();
        final WeatherIcon weatherIcon = new WeatherIcon();
        weatherIcon.setGlyphName("SUNRISE");
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
                this.showDreamSaveDialog(presenter, model.getId());
            }
        });
        tab.setUserData(presenter);
        
        tpEditor.getTabs().add(tab);
        tpEditor.getSelectionModel().select(tab);
    }
    
    private void showDreamSaveDialog(
            DreamPresenter dreamPresenter, Long idToRemove
    ) {
        LoggerFacade.getDefault().debug(this.getClass(), "Show Save Dream Dialog"); // NOI18N
        
        final ActionTransferModel transferModel = new ActionTransferModel();
        transferModel.setActionKey(ACTION__REMOVE_FILE_FROM_EDITOR);
        transferModel.setLong(idToRemove);
        
        DialogProvider.getDefault().showSaveSingleFileDialog(
                (ActionEvent ae) -> { // Yes
                    dreamPresenter.onActionSave();
                    
                    DialogProvider.getDefault().hide();
                    ActionFacade.getDefault().handle(transferModel);
                },
                (ActionEvent ae) -> { // No
                    dreamPresenter.onActionRefresh();
                    
                    DialogProvider.getDefault().hide();
                    ActionFacade.getDefault().handle(transferModel);
                });
    }
    
    public void showDreamWizard() {
        LoggerFacade.getDefault().debug(this.getClass(), "Show Dream Wizard"); // NOI18N
        
        final DreamWizardContentView contentView = new DreamWizardContentView();
        final DreamWizardContentPresenter contentPresenter = contentView.getRealPresenter();
        
        final DialogTemplateView dialogView = new DialogTemplateView();
        final DialogTemplatePresenter dialogPresenter = dialogView.getRealPresenter();
        dialogPresenter.configure("Dream Wizard", contentView.getView(), contentPresenter.getSize()); // NOI18N
        
        final Parent dialog = dialogView.getView();
        DialogProvider.getDefault().show(dialog);
    }
}
