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
package de.pro.dbw.application.testdata;

import de.pro.dbw.core.configuration.api.application.IApplicationConfiguration;
import static de.pro.dbw.core.configuration.api.application.IApplicationConfiguration.DBW__RESOURCE_BUNDLE;
import de.pro.lib.database.api.DatabaseFacade;
import de.pro.lib.logger.api.LoggerFacade;
import de.pro.lib.properties.api.PropertiesFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.util.Duration;

/**
 *
 * @author PRo
 */
public class TestDataPresenter implements Initializable, IApplicationConfiguration {
    
//    @FXML private Button bNo = null;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize TestDataPresenter");
    
//        assert (bNo != null)  : "fx:id=\"bNo\" was not injected: check your FXML file 'SaveMultiFiles.fxml'."; // NOI18N
    }
    
    private String getProperty(String propertyKey) {
        return PropertiesFacade.getDefault().getProperty(DBW__RESOURCE_BUNDLE, propertyKey);
    }
    
    public void onActionCreateTestData() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action create TestData"); // NOI18N
        
        final SequentialTransition st = new SequentialTransition();
        final PauseTransition ptDropPreviousDB = new PauseTransition(Duration.ZERO);
        ptDropPreviousDB.setOnFinished((ActionEvent event) -> {
            DatabaseFacade.getDefault().drop(this.getProperty(KEY__APPLICATION__DATABASE));
            // TODO how to access to the 
        });
        st.getChildren().add(ptDropPreviousDB);
        
        final PauseTransition ptCreateNewDB = new PauseTransition(DBW__LITTLE_DELAY__DURATION_250);
        ptCreateNewDB.setOnFinished((ActionEvent event) -> {
            DatabaseFacade.getDefault().register(this.getProperty(KEY__APPLICATION__DATABASE));
        });
        st.getChildren().add(ptCreateNewDB);

        st.playFromStart();
    }
    
}
