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
package de.pro.dbw.application.testdata;

import com.airhacks.afterburner.injection.Injector;
import de.pro.dbw.core.configuration.api.application.testdata.ITestdataConfiguration;
import de.pro.lib.database.api.DatabaseFacade;
import de.pro.lib.logger.api.LoggerFacade;
import de.pro.lib.properties.api.PropertiesFacade;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * 
 * @author PRo
 */
public class TestdataApplication extends Application implements ITestdataConfiguration {
    
    private TestdataPresenter testdataPresenter = null;
    
    @Override
    public void init() throws Exception {
        PropertiesFacade.INSTANCE.register(DBW__RESOURCE_BUNDLE__TESTDATA);
        
        final char borderSign = this.getProperty(KEY__APPLICATION_TESTDATA__BORDER_SIGN).charAt(0);
        final String message = this.getProperty(KEY__APPLICATION_TESTDATA__MESSAGE_START);
        final String title = this.getProperty(KEY__APPLICATION_TESTDATA__TITLE);
        LoggerFacade.INSTANCE.message(borderSign, 80, message + title);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final TestdataView view = new TestdataView();
        testdataPresenter = view.getRealPresenter();
        
        final Scene scene = new Scene(view.getView(), 1280.0d, 720.0d);
        primaryStage.setTitle(this.getProperty(KEY__APPLICATION_TESTDATA__TITLE));
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
           we.consume();
           
           this.onCloseRequest();
        });
        
        primaryStage.show();
    }
    
    private String getProperty(String propertyKey) {
        return PropertiesFacade.INSTANCE.getProperty(DBW__RESOURCE_BUNDLE__TESTDATA, propertyKey);
    }
    
    private void onCloseRequest() {
        final char borderSign = this.getProperty(KEY__APPLICATION_TESTDATA__BORDER_SIGN).charAt(0);
        final String message = this.getProperty(KEY__APPLICATION_TESTDATA__MESSAGE_STOP);
        final String title = this.getProperty(KEY__APPLICATION_TESTDATA__TITLE);
        LoggerFacade.INSTANCE.message(borderSign, 80, message + title);
        
        try {
            testdataPresenter.shutdown();
        } catch (InterruptedException e) {
        }
        
        Injector.forgetAll();
        DatabaseFacade.INSTANCE.shutdown();
        
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
