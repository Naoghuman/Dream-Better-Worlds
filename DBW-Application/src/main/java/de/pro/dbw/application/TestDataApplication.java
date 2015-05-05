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
package de.pro.dbw.application;

import com.airhacks.afterburner.injection.Injector;
import de.pro.dbw.application.testdata.TestDataView;
import de.pro.dbw.core.configuration.api.application.IApplicationConfiguration;
import de.pro.lib.database.api.DatabaseFacade;
import de.pro.lib.logger.api.LoggerFacade;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * TODO
 * 
 * @author PRo
 */
public class TestDataApplication extends Application implements IApplicationConfiguration {

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoggerFacade.getDefault().message('~', 80, "Start " + DBW__TITLE__TESTDATA); // NOI18N
        
        final TestDataView view = new TestDataView();
        final Scene scene = new Scene(view.getView(), 1280.0d, 720.0d);
        primaryStage.setTitle(DBW__TITLE__TESTDATA);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
           we.consume();
           
           this.onCloseRequest();
        });
        
        primaryStage.show();
    }
    
    private void onCloseRequest() {
        LoggerFacade.getDefault().message('~', 80, "Stop " + DBW__TITLE__TESTDATA); // NOI18N
        
        Injector.forgetAll();
        DatabaseFacade.getDefault().shutdown();
        
        final PauseTransition pt = new PauseTransition(DBW__LITTLE_DELAY__DURATION_125);
        pt.setOnFinished((ActionEvent event) -> {
            Platform.exit();
        });
        pt.playFromStart();
    }
    
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
