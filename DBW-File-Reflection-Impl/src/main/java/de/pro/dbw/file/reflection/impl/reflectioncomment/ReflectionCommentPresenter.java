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
package de.pro.dbw.file.reflection.impl.reflectioncomment;

import de.pro.dbw.core.configuration.api.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.application.util.IUtilConfiguration;
import de.pro.dbw.util.api.IDateConverter;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 *
 * @author PRo
 */
public class ReflectionCommentPresenter implements Initializable, IActionConfiguration, IDateConverter, IUtilConfiguration {
    
    @FXML private Label lComment;
    @FXML private TextArea taComment;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize ReflectionCommentPresenter"); // NOI18N
    
        assert (lComment != null)  : "fx:id=\"lComment\" was not injected: check your FXML file 'ReflectionComment.fxml'."; // NOI18N
        assert (taComment != null) : "fx:id=\"taComment\" was not injected: check your FXML file 'ReflectionComment.fxml'."; // NOI18N
        
//        this.initializeDescription();
    }
    
    public void onActionDelete() {
        // TODO delete this comment in db, refresh parent gui
    }
    
    
    
}
