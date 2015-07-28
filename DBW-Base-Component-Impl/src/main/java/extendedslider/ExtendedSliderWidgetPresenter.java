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
package extendedslider;

import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

/**
 *
 * @author PRo
 */
public class ExtendedSliderWidgetPresenter implements Initializable {

    @FXML private Button bEdit = null;
    @FXML private Label lSelection = null;
    @FXML private Slider sValues = null;
    @FXML private TextArea taDescription = null;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.getLogger().info(this.getClass(), "Initialize ExtendedSliderWidgetPresenter"); // NOI18N
    
        assert (bEdit != null)         : "fx:id=\"bEdit\" was not injected: check your FXML file 'ExtendedSliderWidget.fxml'."; // NOI18N
        assert (lSelection != null)    : "fx:id=\"lSelection\" was not injected: check your FXML file 'ExtendedSliderWidget.fxml'."; // NOI18N
        assert (sValues != null)       : "fx:id=\"sValues\" was not injected: check your FXML file 'ExtendedSliderWidget.fxml'."; // NOI18N
        assert (taDescription != null) : "fx:id=\"taDescription\" was not injected: check your FXML file 'ExtendedSliderWidget.fxml'."; // NOI18N
        
    }
    
    public void onActionEdit() {
        LoggerFacade.INSTANCE.getLogger().info(this.getClass(), "On action edit"); // NOI18N
        System.out.println("  ExtendedSliderWidgetPresenter.XXX onActionEdit()"
                + "show dialog ExtendedSliderEditor with data from here");
    }
    
    public void onActionReadMore() {
        LoggerFacade.INSTANCE.getLogger().info(this.getClass(), "On action read more"); // NOI18N
    
        /*
        TODO
         - Click opens singletion tab with extended data for this widget-element
         - In this extended tab the data can be edit.
        */
    }
    
}
