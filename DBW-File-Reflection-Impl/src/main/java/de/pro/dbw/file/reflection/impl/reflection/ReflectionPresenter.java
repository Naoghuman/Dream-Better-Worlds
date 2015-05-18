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

import de.pro.dbw.core.configuration.api.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.application.util.IUtilConfiguration;
import de.pro.dbw.file.reflection.api.ReflectionModel;
import de.pro.dbw.util.api.IDateConverter;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 *
 * @author PRo
 */
public class ReflectionPresenter implements Initializable, IActionConfiguration, IDateConverter, IUtilConfiguration {
    
    @FXML private CheckBox cbTime;
    @FXML private ScrollPane spComments;
    @FXML private SplitPane spReflection;
    @FXML private TextArea taText;
    @FXML private TextField tfDate;
    @FXML private TextField tfSource;
    @FXML private TextField tfTime;
    @FXML private TextField tfTitle;
    @FXML private VBox vbReflection;
    @FXML private VBox vbComments;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize ReflectionPresenter"); // NOI18N
    
        assert (cbTime != null)        : "fx:id=\"cbTime\" was not injected: check your FXML file 'Reflection.fxml'."; // NOI18N
        assert (taText != null)        : "fx:id=\"taText\" was not injected: check your FXML file 'Reflection.fxml'."; // NOI18N
        assert (tfDate != null)        : "fx:id=\"tfDate\" was not injected: check your FXML file 'Reflection.fxml'."; // NOI18N
        assert (tfSource != null)      : "fx:id=\"tfSource\" was not injected: check your FXML file 'Reflection.fxml'."; // NOI18N
        assert (tfTime != null)        : "fx:id=\"tfTime\" was not injected: check your FXML file 'Reflection.fxml'."; // NOI18N
        assert (tfTitle != null)       : "fx:id=\"tfTitle\" was not injected: check your FXML file 'Reflection.fxml'."; // NOI18N
        
//        this.initializeDescription();
    }
    
    public void show(ReflectionModel model) {
        
    }
    
}
