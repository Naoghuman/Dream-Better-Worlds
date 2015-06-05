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
package de.pro.dbw.base.component.impl.votingcomponent;

import de.pro.dbw.base.component.api.VotingComponentModel;
import de.pro.dbw.core.configuration.api.action.IActionConfiguration;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

/**
 *
 * @author PRo
 */
public class VotingComponentPresenter implements Initializable, IActionConfiguration {
    
    @FXML private Button bShow;
    @FXML private Button bVote;
    @FXML private Label lActiveDuring;
    @FXML private Label lVotingChoose;
    @FXML private Label lName;
    @FXML private Slider sVoting;
    
    private VotingComponentModel votingComponentModel;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize VotingComponentPresenter");
        
        assert (bShow != null)         : "fx:id=\"bShow\" was not injected: check your FXML file 'VotingComponent.fxml'."; // NOI18N
        assert (bVote != null)         : "fx:id=\"bVote\" was not injected: check your FXML file 'VotingComponent.fxml'."; // NOI18N
        assert (lActiveDuring != null) : "fx:id=\"lActiveDuring\" was not injected: check your FXML file 'VotingComponent.fxml'."; // NOI18N
        assert (lVotingChoose != null) : "fx:id=\"lVotingChoose\" was not injected: check your FXML file 'VotingComponent.fxml'."; // NOI18N
        assert (lName != null)         : "fx:id=\"lName\" was not injected: check your FXML file 'VotingComponent.fxml'."; // NOI18N
        assert (sVoting != null)       : "fx:id=\"sVoting\" was not injected: check your FXML file 'VotingComponent.fxml'."; // NOI18N
    
        this.initialzeComponents();
    }
    
    private void initialzeComponents() {
        bVote.setDisable(Boolean.TRUE);
        lVotingChoose.setText("0.0"); // NOI18N
        lVotingChoose.textProperty().bind(sVoting.valueProperty().asString());
        sVoting.setDisable(Boolean.TRUE);
    }
    
    public void configure(VotingComponentModel votingComponentModel) {
        LoggerFacade.getDefault().info(this.getClass(), "Configure VotingComponentPresenter");
        
        this.votingComponentModel = votingComponentModel;
        
//        bVote.setDisable(!this.votingComponentModel.isActive());
//        lActiveDuring.setText(this.votingComponentModel.getActiveDuring());
//        lName.setText(this.votingComponentModel.getName());
//        sVoting.setValue(this.votingComponentModel.getValue());
    }
    
    public void onActionShow() {
        /*
        TODO
         - Shows the chart for this Voting.
        */
    }
    
    public void onActionVote() {
        /*
        TODO
         - Vote this Voting (only ones per day).
            - Disable the button bVote then.
            - Then shows the chart for this Voting.
        */
    }
    
}
