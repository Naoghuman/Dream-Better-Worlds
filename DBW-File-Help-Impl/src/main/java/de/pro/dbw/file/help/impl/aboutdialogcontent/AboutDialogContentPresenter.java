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
package de.pro.dbw.file.help.impl.aboutdialogcontent;

import de.pro.dbw.core.configuration.api.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.application.dialog.IDialogConfiguration;
import de.pro.dbw.dialog.api.IDialogSize;
import de.pro.dbw.dialog.provider.DialogProvider;
import de.pro.lib.logger.api.LoggerFacade;
import java.awt.Dimension;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 *
 * @author PRo
 */
public class AboutDialogContentPresenter implements Initializable, IActionConfiguration,
        IDialogSize
{    
//    @FXML private AnchorPane apDialog;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize AboutDialogContentPresenter"); // NOI18N
        
//        assert (apDialog != null) : "fx:id=\"apDialog\" was not injected: check your FXML file 'AboutDialogContent.fxml'."; // NOI18N
        
    }
    
    public void onActionClose() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action close"); // NOI18N
        
        DialogProvider.getDefault().hide();
    }

    @Override
    public Dimension getSize() {
        return IDialogConfiguration.SIZE__W400_H450;
    }
    
}
