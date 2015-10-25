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
package de.pro.dbw.application.testdata.component.extendedprogressbar;

import de.pro.dbw.application.testdata.api.IProgress;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 *
 * @author PRo
 */
public class ExtendedProgressBarPresenter implements Initializable {
    
    private final DoubleProperty entityProperty = new SimpleDoubleProperty(0.0d);
    
    @FXML private Label lProgressBar;
    @FXML private Label lProgressInfo;
    @FXML private ProgressBar pbProgress;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize ExtendedProgressBarPresenter"); // NOI18N
        
        assert (lProgressBar != null)  : "fx:id=\"lProgressBar\" was not injected: check your FXML file 'ExtendedProgressBar.fxml'."; // NOI18N
        assert (lProgressInfo != null) : "fx:id=\"lProgressInfo\" was not injected: check your FXML file 'ExtendedProgressBar.fxml'."; // NOI18N
        assert (pbProgress != null)    : "fx:id=\"pbProgress\" was not injected: check your FXML file 'ExtendedProgressBar.fxml'."; // NOI18N
    
        this.initializeProgressBar();
    }
    
    private void initializeProgressBar() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize ProgressBar"); // NOI18N
        
        pbProgress.setProgress(IProgress.NO_PROGRESS);
    }
    
    public DoubleProperty entityProperty() {
        return entityProperty;
    }
    
    public ProgressBar getProgressBar() {
        return pbProgress;
    }
    
    public void setMaxEntities(final int maxEntities) {
        lProgressBar.textProperty().bind(Bindings.createStringBinding(() -> {
            int process = (int) (entityProperty().getValue() * 100.0d);
            if (process <= 0) {
                process = 0;
            } else {
                ++process;
            }
            
            return process + "%"; // NOI18N
            
        }, entityProperty));
    }
    
    public void setTextForProgress(String info) {
        lProgressInfo.setText(info);
    }
    
}
