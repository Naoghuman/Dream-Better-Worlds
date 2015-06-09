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
package de.pro.dbw.application.desktop;

import de.pro.dbw.core.configuration.api.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.action.IRegisterActions;
import de.pro.dbw.core.configuration.api.application.desktop.IDesktopConfiguration;
import de.pro.dbw.core.configuration.api.application.util.IUtilConfiguration;
import de.pro.dbw.dialog.provider.DialogProvider;
import de.pro.dbw.exercise.provider.ExerciseProvider;
import de.pro.dbw.feature.provider.FeatureProvider;
import de.pro.dbw.file.provider.FileProvider;
import de.pro.dbw.navigation.provider.NavigationProvider;
import de.pro.dbw.tool.provider.ToolProvider;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.logger.api.LoggerFacade;
import de.pro.lib.preferences.api.PreferencesFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

/**
 *
 * @author PRo
 */
public class DesktopPresenter implements Initializable, IActionConfiguration, 
        IDesktopConfiguration, IRegisterActions, IUtilConfiguration
{
    @FXML private AnchorPane apDialogLayer;
    @FXML private AnchorPane apDialogLayer2;
    @FXML private BorderPane bpNavigationLeft;
    @FXML private BorderPane bpNavigationRight;
    @FXML private MenuBar mbDesktop;
    @FXML private TabPane tpEditor;
    @FXML private TabPane tpNavigationLeft;
    @FXML private TabPane tpNavigationRight;
    @FXML private ToolBar tbDesktop;
    @FXML private SplitPane spDesktop;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize DesktopPresenter");
        
        assert (apDialogLayer != null)     : "fx:id=\"apDialogLayer\" was not injected: check your FXML file 'Desktop.fxml'."; // NOI18N
        assert (apDialogLayer2 != null)    : "fx:id=\"apDialogLayer2\" was not injected: check your FXML file 'Desktop.fxml'."; // NOI18N
        assert (bpNavigationLeft != null)  : "fx:id=\"bpNavigationLeft\" was not injected: check your FXML file 'Desktop.fxml'."; // NOI18N
        assert (bpNavigationRight != null) : "fx:id=\"bpNavigationRight\" was not injected: check your FXML file 'Desktop.fxml'."; // NOI18N
        assert (mbDesktop != null)         : "fx:id=\"mbDesktop\" was not injected: check your FXML file 'Desktop.fxml'."; // NOI18N
        assert (tpEditor != null)          : "fx:id=\"tpEditor\" was not injected: check your FXML file 'Desktop.fxml'."; // NOI18N
        assert (tpNavigationLeft != null)  : "fx:id=\"tpNavigationLeft\" was not injected: check your FXML file 'Desktop.fxml'."; // NOI18N
        assert (tpNavigationRight != null) : "fx:id=\"tpNavigationRight\" was not injected: check your FXML file 'Desktop.fxml'."; // NOI18N
        assert (tbDesktop != null)         : "fx:id=\"tbDesktop\" was not injected: check your FXML file 'Desktop.fxml'."; // NOI18N
        assert (spDesktop != null)         : "fx:id=\"spDesktop\" was not injected: check your FXML file 'Desktop.fxml'."; // NOI18N
        
        this.registerActions();
        this.registerDialogLayer();
        this.registerEditorAndNavigation();
        
        this.initializeToolBar();
        this.initializeMenuBar();
        this.initializeDesktopSplitPane();
    }

    private void initializeDesktopSplitPane() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize desktop SplitPane"); // NOI18N
        
        SplitPane.setResizableWithParent(bpNavigationLeft, Boolean.FALSE);
        SplitPane.setResizableWithParent(bpNavigationRight, Boolean.FALSE);
        
        // Load divider positions
        Platform.runLater(() -> {
            final Double dividerPositionLeft = PreferencesFacade.getDefault().getDouble(
                    this.getClass(), DESKTOP__DIVIDER_POSITION__LEFT,
                    DESKTOP__DIVIDER_POSITION__LEFT__DEFAULT_VALUE);
            spDesktop.getDividers().get(DESKTOP__DIVIDER_POSITION__INDEX_0).setPosition(dividerPositionLeft);
            
            final Double dividerPositionRight = PreferencesFacade.getDefault().getDouble(
                    this.getClass(), DESKTOP__DIVIDER_POSITION__RIGHT,
                    DESKTOP__DIVIDER_POSITION__RIGHT__DEFAULT_VALUE);
            spDesktop.getDividers().get(DESKTOP__DIVIDER_POSITION__INDEX_1).setPosition(dividerPositionRight);
        });
        
        // Listener for left divider
        final PauseTransition pauseTransitionLeft = new PauseTransition();
        pauseTransitionLeft.setDuration(Duration.millis(DESKTOP__DIVIDER_POSITION__DURATION));
        pauseTransitionLeft.setOnFinished((ActionEvent event) -> {
            PreferencesFacade.getDefault().putDouble(
                    this.getClass(), DESKTOP__DIVIDER_POSITION__LEFT,
                    spDesktop.getDividerPositions()[DESKTOP__DIVIDER_POSITION__INDEX_0]);
        });
        
        spDesktop.getDividers().get(DESKTOP__DIVIDER_POSITION__INDEX_0).positionProperty().addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) ->
        {
            if (pauseTransitionLeft.getStatus().equals(Animation.Status.RUNNING)) {
                pauseTransitionLeft.stop();
            }

            pauseTransitionLeft.playFromStart();
        });
        
        // Listener for right divider
        final PauseTransition pauseTransitionRight = new PauseTransition();
        pauseTransitionRight.setDuration(Duration.millis(DESKTOP__DIVIDER_POSITION__DURATION));
        pauseTransitionRight.setOnFinished((ActionEvent event) -> {
            PreferencesFacade.getDefault().putDouble(
                    this.getClass(), DESKTOP__DIVIDER_POSITION__RIGHT,
                    spDesktop.getDividerPositions()[DESKTOP__DIVIDER_POSITION__INDEX_1]);
        });
        
        spDesktop.getDividers().get(DESKTOP__DIVIDER_POSITION__INDEX_1).positionProperty().addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) ->
        {
            if (pauseTransitionRight.getStatus().equals(Animation.Status.RUNNING)) {
                pauseTransitionRight.stop();
            }

            pauseTransitionRight.playFromStart();
        });
    }

    private void initializeToolBar() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize ToolBar"); // NOI18N
        
        tbDesktop.getItems().add(this.createToolBarButton("Dream", ACTION__CREATE_NEW_DREAM)); // NOI18N
        tbDesktop.getItems().add(this.createToolBarButton("Fast Dream", ACTION__CREATE_NEW_FAST_DREAM)); // NOI18N
//        tbDesktop.getItems().add(FileProvider.getDefault().getToolBarButton(EFileType.DREAMMAP));
//        tbDesktop.getItems().add(FileProvider.getDefault().getToolBarButton(EFileType.EXERCISE));
//        tbDesktop.getItems().add(new Separator(Orientation.VERTICAL));
//        tbDesktop.getItems().add(TipOfTheNightFeatureProvider.getDefault().getToolBarButton());
        tbDesktop.getItems().add(new Separator(Orientation.VERTICAL));
        tbDesktop.getItems().add(this.createToolBarButton("Tip of the Night", ACTION__SHOW_TIP_OF_THE_NIGHT__WINDOW)); // NOI18N
//        tbDesktop.getItems().add(this.createToolBarButton("Welcome", ACTION__SHOW_HELP__WELCOME)); // NOI18N
    }
    
    private Button createToolBarButton(String title, String actionKey) {
        final Button btn = new Button();
        btn.setText(title);
        btn.setOnAction((ActionEvent event) -> {
            ActionFacade.getDefault().handle(actionKey);
        });
        
        
        return btn;
    }
    
    private void initializeMenuBar() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize MenuBar"); // NOI18N
        
//        mbDesktop.prefHeightProperty().bind(tbDesktop.heightProperty());
//        mbDesktop.setVisible(false);
//        mbDesktop.setManaged(false);
    }
    
    public Boolean checkForUnsavedFiles() {
        LoggerFacade.getDefault().info(this.getClass(), "Check for unsaved files"); // NOI18N
        
        if (tpEditor.getTabs().isEmpty()) {
            return Boolean.FALSE;
        }
        
        for (final Tab tab : tpEditor.getTabs()) {
            final Node graphic = tab.getGraphic();
            if (graphic instanceof HBox) {
                final HBox hBox = (HBox) graphic;
                final Label lbl = (Label) hBox.getChildren().get(1);
                if (lbl.getText().startsWith(SIGN__STAR)) {
                    return Boolean.TRUE;
                }
            }
        }
        
        return Boolean.FALSE;
    }
    
    public void onActionCreateNewDream() {
//        ActionFacade.getDefault().handle(ACTION__CREATE_NEW_DREAM);
        
        ActionFacade.getDefault().handle(ACTION__SHOW_SEARCH_IN_DREAMS); // XXX test
    }
    
    public void onActionCreateNewFastDream() {
        ActionFacade.getDefault().handle(ACTION__CREATE_NEW_FAST_DREAM);
    }
    
    public void onActionCreateNewFileReflection() {
        ActionFacade.getDefault().handle(ACTION__CREATE_NEW_FILE__REFLECTION);
    }

    public void onActionSaveMultiFiles() {
        ActionFacade.getDefault().handle(ACTION__SAVE_ALL_CHANGED_FILES);
    }
    
    public void onActionShowExtendedSliderEditorDialog() {
//        DialogProvider1.getDefault().showExtendedSliderEditorDialog();
    }
    
    public void onActionShowHelp() {
//        ActionFacade.getDefault().handle(ACTION__SHOW_HELP);
    }
    
    public void onActionShowHelpAbout() {
        ActionFacade.getDefault().handle(ACTION__SHOW_HELP__ABOUT);
    }
    
    public void onActionShowHelpWelcome() {
//        ActionFacade.getDefault().handle(ACTION__SHOW_HELP__WELCOME);
    }
    
    public void onActionShowTipOfTheNightEditor() {
        ActionFacade.getDefault().handle(ACTION__SHOW_TIP_OF_THE_NIGHT__EDITOR);
    }
    
    public void onActionShowTipOfTheNightWindow() {
        ActionFacade.getDefault().handle(ACTION__SHOW_TIP_OF_THE_NIGHT__WINDOW);
    }

    @Override
    public void registerActions() {
        LoggerFacade.getDefault().info(this.getClass(), "Register actions in DesktopPresenter"); // NOI18N
        
        ExerciseProvider.getDefault().registerActions();
        FileProvider.getDefault().registerActions();
        NavigationProvider.getDefault().registerActions();
        ToolProvider.getDefault().registerActions();
    }
    
    private void registerDialogLayer() {
        LoggerFacade.getDefault().info(this.getClass(), "Register Dialog layers"); // NOI18N
        
        DialogProvider.getDefault().register(apDialogLayer, apDialogLayer2);
    }
    
    private void registerEditorAndNavigation() {
        LoggerFacade.getDefault().info(this.getClass(), "Register TabPanes tpNavigationLeft, tpEditor, tpNavigationRight in DesktopPresenter"); // NOI18N
        
        ExerciseProvider.getDefault().register(tpEditor);
        FeatureProvider.getDefault().register(tpEditor);
        FileProvider.getDefault().register(tpEditor, tpNavigationRight);
        NavigationProvider.getDefault().register(tpNavigationLeft, tpEditor, tpNavigationRight);
   
    }
    
}
