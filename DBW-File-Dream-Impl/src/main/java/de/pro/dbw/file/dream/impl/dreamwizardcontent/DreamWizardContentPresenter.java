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
package de.pro.dbw.file.dream.impl.dreamwizardcontent;

import de.pro.dbw.core.configuration.api.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.application.dialog.IDialogConfiguration;
import de.pro.dbw.core.configuration.api.application.util.IUtilConfiguration;
import de.pro.dbw.dialog.provider.DialogProvider;
import de.pro.dbw.file.dream.api.DreamModel;
import de.pro.dbw.core.sql.provider.SqlProvider;
import de.pro.dbw.dialog.api.IDialogSize;
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
 * TODO add checkbox in create mode -> open dream in editor
 *
 * @author PRo
 */
public class DreamWizardContentPresenter implements Initializable, IActionConfiguration,
        IDateConverter, IDialogSize, IUtilConfiguration
{
    @FXML private Button bCreateDream;
    @FXML private CheckBox cbTime;
    @FXML private ProgressIndicator piSave;
    @FXML private StackPane spProgress;
    @FXML private TextArea taDescription;
    @FXML private TextField tfDate;
    @FXML private TextField tfTime;
    @FXML private TextField tfTitle;
    
    private BooleanBinding disableBinding = null;
    private BooleanBinding descriptionBinding = null;
    private BooleanBinding titleBinding = null;
    private BooleanProperty dateProperty = null;
    private BooleanProperty timeProperty = null;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize DreamWizardPresenter"); // NOI18N
    
        assert (bCreateDream != null)  : "fx:id=\"bCreateDream\" was not injected: check your FXML file 'DreamWizard.fxml'."; // NOI18N
        assert (cbTime != null)        : "fx:id=\"cbTime\" was not injected: check your FXML file 'DreamWizard.fxml'."; // NOI18N
        assert (piSave != null)        : "fx:id=\"piSave\" was not injected: check your FXML file 'DreamWizard.fxml'."; // NOI18N
        assert (spProgress != null)    : "fx:id=\"apProgress\" was not injected: check your FXML file 'DreamWizard.fxml'."; // NOI18N
        assert (taDescription != null) : "fx:id=\"taDescription\" was not injected: check your FXML file 'DreamWizard.fxml'."; // NOI18N
        assert (tfDate != null)        : "fx:id=\"tfDate\" was not injected: check your FXML file 'DreamWizard.fxml'."; // NOI18N
        assert (tfTime != null)        : "fx:id=\"tfTime\" was not injected: check your FXML file 'DreamWizard.fxml'."; // NOI18N
        assert (tfTitle != null)       : "fx:id=\"tfTitle\" was not injected: check your FXML file 'DreamWizard.fxml'."; // NOI18N
        
        this.initializeDescription();
        this.initializeBindings();
        this.initializeSaveProgress();
        this.initializeTime();
    }
    
    private void initializeBindings() {
        dateProperty = new SimpleBooleanProperty(Boolean.TRUE);
        timeProperty = new SimpleBooleanProperty(Boolean.TRUE);
        titleBinding = tfTitle.textProperty().isEmpty();
        descriptionBinding = taDescription.textProperty().isEmpty();
        disableBinding = new BooleanBinding() {
            {
                super.bind(
                        descriptionBinding, titleBinding,
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
                
                return titleBinding.getValue() || descriptionBinding.getValue();
            }
        };
        
        bCreateDream.disableProperty().bind(disableBinding);
    }
    
    private void initializeDescription() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize description"); // NOI18N
        
        taDescription.setText( // XXX Test
                "Categories:\n" // NOI18N
                + " - ...\n" // NOI18N
                + "Tags:\n" // NOI18N
                + " - ...\n\n" // NOI18N
                + "Notes:" // NOI18N
                + " - ..."); // NOI18N
    }
    
    private void initializeSaveProgress() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize save progress"); // NOI18N
        
        piSave.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        piSave.setVisible(Boolean.FALSE);
        piSave.setManaged(Boolean.FALSE);
        
        spProgress.setVisible(Boolean.FALSE);
        spProgress.setManaged(Boolean.FALSE);
    }
    
    private void initializeTime() {
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

    @Override
    public Dimension getSize() {
        return IDialogConfiguration.SIZE__W495_H330;
    }
    
    public void onActionReset() {
        tfTitle.setText(null);
        taDescription.setText(null);
        
        final Long now = System.currentTimeMillis();
        tfDate.setText(UtilProvider.getDefault().getDateConverter().convertLongToDateTime(now, PATTERN__DATE));
        cbTime.setSelected(Boolean.TRUE);
        tfTime.setText(UtilProvider.getDefault().getDateConverter().convertLongToDateTime(now, PATTERN__TIME));
    }
    
    public void onActionClose() {
        DialogProvider.getDefault().hide();
    }
    
    public void onActionCreate() {
        final SequentialTransition st = new SequentialTransition();
        final PauseTransition pt1 = new PauseTransition(Duration.ZERO);
        pt1.setOnFinished((ActionEvent event) -> {
            spProgress.setVisible(Boolean.TRUE);
            spProgress.setManaged(Boolean.TRUE);
            piSave.setVisible(Boolean.TRUE);
            piSave.setManaged(Boolean.TRUE);
        });
        
        final PauseTransition pt2 = new PauseTransition(Duration.millis(125.0d));// XXX add duration to configuration
        pt2.setOnFinished((ActionEvent event) -> {
            this.save();
        });
        
        final PauseTransition pt3 = new PauseTransition(Duration.millis(250.0d));// XXX add duration to configuration
        pt3.setOnFinished((ActionEvent event) -> {
            piSave.setVisible(Boolean.FALSE);
            piSave.setManaged(Boolean.FALSE);
            spProgress.setVisible(Boolean.FALSE);
            spProgress.setManaged(Boolean.FALSE);
        });
        st.getChildren().addAll(pt1, pt2, pt3);
        
        st.playFromStart();
    }
    
    private void save() {
        LoggerFacade.getDefault().info(this.getClass(), "Save new dream to database"); // NOI18N
        
        // Catch data
        final DreamModel model = new DreamModel();
        model.setId(System.currentTimeMillis());
        model.setTitle(tfTitle.getText());
        model.setDescription(taDescription.getText());
        
        final String time = (tfTime.getText() != null) ? tfTime.getText()
                : PATTERN__TIME_IS_EMPTY;
        model.setGenerationTime(UtilProvider.getDefault().getDateConverter().convertDateTimeToLong(
                tfDate.getText() + SIGN__SPACE + time,
                PATTERN__DATETIME));
        
        // Save the dream
        SqlProvider.getDefault().getDreamSqlProvider().create(model);
        
        // Update gui
        ActionFacade.getDefault().handle(ACTION__REFRESH_NAVIGATION__DREAMBOOK);
        ActionFacade.getDefault().handle(ACTION__REFRESH_NAVIGATION__HISTORY);
    }
    
}
