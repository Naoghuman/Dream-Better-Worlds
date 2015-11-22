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
package de.pro.dbw.feature.voting.impl.listview.votingelement;

import de.pro.dbw.core.configuration.api.application.action.IActionConfiguration;
import de.pro.dbw.feature.voting.api.VotingElementModel;
import de.pro.dbw.util.api.IDateConverter;
import de.pro.dbw.util.provider.UtilProvider;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author PRo
 */
public class VotingElementPresenter implements Initializable, IActionConfiguration,
        IDateConverter
{   
    private static final String KEY__FEATURE__VOTING_ACTIVE_FROM = "key.feature.voting.active.from"; // NOI18N
    private static final String KEY__FEATURE__VOTING_ACTIVE_TO = "key.feature.voting.active.to"; // NOI18N
    private static final String KEY__FEATURE__VOTING_DEFAULT_VALUE = "key.feature.voting.default.value"; // NOI18N
    private static final String KEY__FEATURE__VOTING_PATTERN = "key.feature.voting.pattern"; // NOI18N
    
    @FXML private AnchorPane apVotingComponent;
    @FXML private Button bShow;
    @FXML private Button bVote;
    @FXML private Label lActiveDuring;
    @FXML private Label lTitle;
    @FXML private Label lVotingChoose;
    @FXML private Slider sVoting;
    
    private VotingElementModel votingElementModel;
    
    private ResourceBundle resources = null;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize VotingElementPresenter");
        
        this.resources = resources;
    
        assert (apVotingComponent != null) : "fx:id=\"apVotingComponent\" was not injected: check your FXML file 'VotingElement.fxml'."; // NOI18N
        assert (bShow != null)         : "fx:id=\"bShow\" was not injected: check your FXML file 'VotingElement.fxml'."; // NOI18N
        assert (bVote != null)         : "fx:id=\"bVote\" was not injected: check your FXML file 'VotingElement.fxml'."; // NOI18N
        assert (lActiveDuring != null) : "fx:id=\"lActiveDuring\" was not injected: check your FXML file 'VotingElement.fxml'."; // NOI18N
        assert (lTitle != null)        : "fx:id=\"lName\" was not injected: check your FXML file 'VotingElement.fxml'."; // NOI18N
        assert (lVotingChoose != null) : "fx:id=\"lVotingChoose\" was not injected: check your FXML file 'VotingElement.fxml'."; // NOI18N
        assert (sVoting != null)       : "fx:id=\"sVoting\" was not injected: check your FXML file 'VotingElement.fxml'."; // NOI18N
    
        this.initialzeComponents();
    }
    
    private void initialzeComponents() {
        bVote.setDisable(Boolean.TRUE);
        lVotingChoose.setText(resources.getString(KEY__FEATURE__VOTING_DEFAULT_VALUE));
        lVotingChoose.textProperty().bind(sVoting.valueProperty().asString(resources.getString(KEY__FEATURE__VOTING_PATTERN)));
        sVoting.setDisable(Boolean.TRUE);
    }
    
    public void configure(VotingElementModel votingElementModel) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Configure VotingElementPresenter"); // NOI18N
        
        this.votingElementModel = votingElementModel;
        
        lTitle.setText(this.votingElementModel.getTitle());
        lActiveDuring.setText(this.getInfoActiveFrom());
        
        this.configureForReducedMode();
//        switch(votingComponentModel.getMode()) {
//            case EDITOR:     { this.configureForReducedMode(); break; }
//            case NAVIGATION: { this.configureForFullMode(); break; }
//            case WIZARD:     { this.configureForReducedMode(); break; }
//        }
    }
    
    private void configureForReducedMode() {
        lVotingChoose.setManaged(Boolean.FALSE);
        lVotingChoose.setVisible(Boolean.FALSE);
        sVoting.setManaged(Boolean.FALSE);
        sVoting.setVisible(Boolean.FALSE);
        
        bShow.setManaged(Boolean.FALSE);
        bShow.setVisible(Boolean.FALSE);
        bVote.setManaged(Boolean.FALSE);
        bVote.setVisible(Boolean.FALSE);
        
        apVotingComponent.setPrefHeight(38.0d);
        apVotingComponent.setPrefWidth(200.0d);
    }
    
    private String getInfoActiveFrom() {
        final StringBuilder sb = new StringBuilder();
        sb.append(resources.getString(KEY__FEATURE__VOTING_ACTIVE_FROM));
        
        final String convertedFromDate = UtilProvider.getDefault().getDateConverter()
                .convertLongToDateTime(votingElementModel.getFromDate(), PATTERN__DATE);
        sb.append(convertedFromDate);
        
        sb.append(resources.getString(KEY__FEATURE__VOTING_ACTIVE_TO));
        
        final String convertedToDate = UtilProvider.getDefault().getDateConverter()
                .convertLongToDateTime(votingElementModel.getToDate(), PATTERN__DATE);
        sb.append(convertedToDate);
        
        return sb.toString();
    }
    
//    private void configureForFullMode() {
//        if (this.votingComponentModel.hasVotedToDay()) {
//            sVoting.setValue(this.votingComponentModel.getLastVote());
//        }
//        sVoting.setDisable(this.votingComponentModel.hasVotedToDay());
//        
//        bShow.setDisable(!this.votingComponentModel.hasVotedToDay());
//        bVote.setDisable(this.votingComponentModel.hasVotedToDay());
//    }
    
    public void onActionShow() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Show"); // NOI18N
        
        /*
        TODO
         - Shows the chart for this Voting.
        */
    }
    
    public void onActionVote() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Vote"); // NOI18N
        
        /*
        TODO
         - Vote this Voting (only ones per day).
            - Disable the button bVote then.
            - Then shows the chart for this Voting.
        */
        
        
//        bShow.setDisable(!this.votingComponentModel.hasVotedToDay());
    }
    
}
