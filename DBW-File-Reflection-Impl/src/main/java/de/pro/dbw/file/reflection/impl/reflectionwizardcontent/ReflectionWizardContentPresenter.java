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
package de.pro.dbw.file.reflection.impl.reflectionwizardcontent;

import de.pro.dbw.core.configuration.api.core.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.application.defaultid.IDefaultIdConfiguration;
import de.pro.dbw.core.configuration.api.application.dialog.IDialogConfiguration;
import de.pro.dbw.core.configuration.api.application.util.IUtilConfiguration;
import de.pro.dbw.core.configuration.api.file.IFileConfiguration;
import de.pro.dbw.dialog.provider.DialogProvider;
import de.pro.dbw.core.sql.provider.SqlProvider;
import de.pro.dbw.dialog.api.IDialogSize;
import de.pro.dbw.file.reflection.api.ReflectionModel;
import de.pro.dbw.util.api.IDateConverter;
import de.pro.dbw.util.provider.UtilProvider;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.logger.api.LoggerFacade;
import java.awt.Dimension;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 *
 * @author PRo
 */
public class ReflectionWizardContentPresenter implements Initializable, IActionConfiguration,
        IDateConverter, IDefaultIdConfiguration, IDialogSize, IFileConfiguration, IUtilConfiguration
{
    @FXML private Button bCreate;
    @FXML private Button bEdit;
    @FXML private Button bReset;
    @FXML private CheckBox cbTime;
    @FXML private ProgressIndicator piSave;
    @FXML private StackPane spProgress;
    @FXML private TextArea taText;
    @FXML private TextField tfDate;
    @FXML private TextField tfSource;
    @FXML private TextField tfTime;
    @FXML private TextField tfTitle;
    
    private BooleanBinding disableBinding = null;
    private BooleanBinding textBinding = null;
    private BooleanBinding titleBinding = null;
    private BooleanProperty dateProperty = null;
    private BooleanProperty timeProperty = null;
    
    private ReflectionModel model = null;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize ReflectionWizardPresenter"); // NOI18N
    
        assert (bCreate != null)    : "fx:id=\"bCreate\" was not injected: check your FXML file 'ReflectionWizard.fxml'."; // NOI18N
        assert (bEdit != null)      : "fx:id=\"bEdit\" was not injected: check your FXML file 'ReflectionWizard.fxml'."; // NOI18N
        assert (bReset != null)     : "fx:id=\"bReset\" was not injected: check your FXML file 'ReflectionWizard.fxml'."; // NOI18N
        assert (cbTime != null)     : "fx:id=\"cbTime\" was not injected: check your FXML file 'ReflectionWizard.fxml'."; // NOI18N
        assert (piSave != null)     : "fx:id=\"piSave\" was not injected: check your FXML file 'ReflectionWizard.fxml'."; // NOI18N
        assert (spProgress != null) : "fx:id=\"apProgress\" was not injected: check your FXML file 'ReflectionWizard.fxml'."; // NOI18N
        assert (taText != null)     : "fx:id=\"taText\" was not injected: check your FXML file 'ReflectionWizard.fxml'."; // NOI18N
        assert (tfDate != null)     : "fx:id=\"tfDate\" was not injected: check your FXML file 'ReflectionWizard.fxml'."; // NOI18N
        assert (tfSource != null)   : "fx:id=\"tfSource\" was not injected: check your FXML file 'ReflectionWizard.fxml'."; // NOI18N
        assert (tfTime != null)     : "fx:id=\"tfTime\" was not injected: check your FXML file 'ReflectionWizard.fxml'."; // NOI18N
        assert (tfTitle != null)    : "fx:id=\"tfTitle\" was not injected: check your FXML file 'ReflectionWizard.fxml'."; // NOI18N
        
        this.initializeBindings();
        this.initializeSaveProgress();
        this.initializeTimeComponents();
    }
    
    private void initializeBindings() {
        dateProperty = new SimpleBooleanProperty(Boolean.TRUE);
        timeProperty = new SimpleBooleanProperty(Boolean.TRUE);
        titleBinding = tfTitle.textProperty().isEmpty();
        textBinding = taText.textProperty().isEmpty();
        disableBinding = new BooleanBinding() {
            {
                super.bind(
                        textBinding, titleBinding,
                        dateProperty,timeProperty);
            }

            @Override
            protected boolean computeValue() {
                if (dateProperty.not().getValue()) {
                    return Boolean.TRUE;
                }
                
                if (cbTime.isSelected() && timeProperty.not().getValue()) {
                    return Boolean.TRUE;
                }
                
                return titleBinding.getValue() || textBinding.getValue();
            }
        };
        
        bCreate.disableProperty().bind(disableBinding);
        bEdit.disableProperty().bind(disableBinding);
    }
    
    private void initializeSaveProgress() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize save progress"); // NOI18N
        
        piSave.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        piSave.setVisible(Boolean.FALSE);
        piSave.setManaged(Boolean.FALSE);
        
        spProgress.setVisible(Boolean.FALSE);
        spProgress.setManaged(Boolean.FALSE);
    }
    
    private void initializeTimeComponents() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize Time component"); // NOI18N
        
        final Long now = System.currentTimeMillis();
        tfDate.setText(UtilProvider.getDefault().getDateConverter().convertLongToDateTime(now, PATTERN__DATE));
        tfDate.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            dateProperty.setValue(UtilProvider.getDefault().getDateConverter().isValid(PATTERN__DATE, tfDate.getText()));
        });
        
        final String time = UtilProvider.getDefault().getDateConverter().convertLongToDateTime(now, PATTERN__TIME);
        cbTime.setSelected(!time.equals(PATTERN__TIME_IS_EMPTY));
        cbTime.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            tfTime.setCursor(!newValue ? Cursor.DEFAULT : Cursor.TEXT);
            tfTime.setText(!newValue ? null : UtilProvider.getDefault().getDateConverter().convertLongToDateTime(now, PATTERN__TIME));
        });
        tfTime.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (cbTime.isSelected()) {
                timeProperty.setValue(UtilProvider.getDefault().getDateConverter().isValid(PATTERN__TIME, tfTime.getText()));
            }
        });
        if (!time.equals(PATTERN__TIME_IS_EMPTY)) {
            tfTime.setText(time);
        }
        tfTime.disableProperty().bind(cbTime.selectedProperty().not());
    }
    
    public void configureWizardForCreateMode() {
        LoggerFacade.getDefault().info(this.getClass(), "Configure Reflection Wizard for CREATE mode."); // NOI18N
    
        bEdit.setVisible(Boolean.FALSE);
        bEdit.setManaged(Boolean.FALSE);
        
        this.show(new ReflectionModel());
    }
    
    public void configureWizardForEditMode(ReflectionModel model) {
        LoggerFacade.getDefault().info(this.getClass(), "Configure Reflection Wizard for EDIT mode."); // NOI18N
    
        bCreate.setVisible(Boolean.FALSE);
        bCreate.setManaged(Boolean.FALSE);
        bReset.setVisible(Boolean.FALSE);
        bReset.setManaged(Boolean.FALSE);
        
        this.show(model);
    }

    @Override
    public Dimension getSize() {
        return IDialogConfiguration.SIZE__W495_H414;
    }
    
    public void onActionReset() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action reset"); // NOI18N
    
        tfTitle.setText(null);
        taText.setText(null);
        
        final Long now = System.currentTimeMillis();
        tfDate.setText(UtilProvider.getDefault().getDateConverter().convertLongToDateTime(now, PATTERN__DATE));
        cbTime.setSelected(Boolean.TRUE);
        tfTime.setText(UtilProvider.getDefault().getDateConverter().convertLongToDateTime(now, PATTERN__TIME));
    }
    
    public void onActionClose() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action close"); // NOI18N
    
        DialogProvider.getDefault().hide();
    }
    
    public void onActionCreate() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action create"); // NOI18N
    
        final SequentialTransition st = new SequentialTransition();
        final PauseTransition pt1 = new PauseTransition(Duration.ZERO);
        pt1.setOnFinished((ActionEvent event) -> {
            spProgress.setVisible(Boolean.TRUE);
            spProgress.setManaged(Boolean.TRUE);
            piSave.setVisible(Boolean.TRUE);
            piSave.setManaged(Boolean.TRUE);
        });
        
        final PauseTransition pt2 = new PauseTransition(PAUSE_DURATION_125);
        pt2.setOnFinished((ActionEvent event) -> {
            this.save();
        });
        
        final PauseTransition pt3 = new PauseTransition(PAUSE_DURATION_250);
        pt3.setOnFinished((ActionEvent event) -> {
            piSave.setVisible(Boolean.FALSE);
            piSave.setManaged(Boolean.FALSE);
            spProgress.setVisible(Boolean.FALSE);
            spProgress.setManaged(Boolean.FALSE);
        });
        st.getChildren().addAll(pt1, pt2, pt3);
        
        st.playFromStart();
    }
    
    public void onActionEdit() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action edit"); // NOI18N
    
        this.onActionCreate();
    }
    
    private void save() {
        LoggerFacade.getDefault().info(this.getClass(), "Save new Reflection file to database"); // NOI18N
        
        // Catch data
        model.setTitle(tfTitle.getText());
        model.setSource(tfSource.getText());

        final String time = (tfTime.getText() != null) ? tfTime.getText() : PATTERN__TIME_IS_EMPTY;
        model.setGenerationTime(UtilProvider.getDefault().getDateConverter().convertDateTimeToLong(
                tfDate.getText() + SIGN__SPACE + time,
                PATTERN__DATETIME));
        
        model.setText(taText.getText());
        
        // Save the reflection
        SqlProvider.getDefault().getReflectionSqlProvider().createOrUpdate(model);
        model = new ReflectionModel();

        // Update gui
        ActionFacade.getDefault().handle(ACTION__REFRESH_NAVIGATION__DREAMBOOK);
        ActionFacade.getDefault().handle(ACTION__REFRESH_NAVIGATION__HISTORY);
    }
    
    private void show(ReflectionModel model) {
        LoggerFacade.getDefault().info(this.getClass(), "Show ReflectionModel"); // NOI18N
        
        this.model = model;

        // Set data
        tfTitle.setText(this.model.getTitle());
        tfSource.setText(this.model.getSource());
        
        final Long generationTime = this.model.getGenerationTime();
        tfDate.setText(UtilProvider.getDefault().getDateConverter().convertLongToDateTime(generationTime, PATTERN__DATE));
        
        final String time = UtilProvider.getDefault().getDateConverter().convertLongToDateTime(generationTime, PATTERN__TIME);
        cbTime.setSelected(!time.equals(PATTERN__TIME_IS_EMPTY));
        tfTime.setText(UtilProvider.getDefault().getDateConverter().convertLongToDateTime(generationTime, PATTERN__TIME));
        
        taText.setText(this.model.getText());
    }
    
}
