package de.pro.dbw.application;

import static javafx.application.Application.launch;

import com.airhacks.afterburner.injection.Injector;
import de.pro.dbw.application.desktop.api.DesktopFacade;
import de.pro.dbw.core.configuration.api.application.IApplicationConfiguration;
import de.pro.dbw.core.configuration.api.application.preferences.IPreferencesConfiguration;
import de.pro.dbw.base.provider.BaseProvider;
import de.pro.dbw.dialog.provider.DialogProvider;
import de.pro.dbw.file.provider.FileProvider;
import de.pro.lib.database.api.DatabaseFacade;
import de.pro.lib.logger.api.LoggerFacade;
import de.pro.lib.preferences.api.PreferencesFacade;
import de.pro.lib.properties.api.PropertiesFacade;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class DreamBetterWorlds extends Application implements IApplicationConfiguration, IPreferencesConfiguration {

    private static final String CSS__DREAM_BETTER_WORLDS = "DreamBetterWorlds.css"; // NOI18N
    
    private static final String KEY__APPLICATION__MESSAGE_GOODBYE = "application.message.goodbye"; // NOI18N
    private static final String KEY__APPLICATION__MESSAGE_WELCOME = "application.message.welcome"; // NOI18N
    private static final String KEY__APPLICATION__TITLE = "application.title"; // NOI18N
    
    @Override
    public void init() throws Exception {
        System.out.println("XXX DreamBetterWorlds.init() remove lib joda-time");
        System.out.println("XXX DreamBetterWorlds.init() look why the generated jar not work (application will not start with doubleclick)");
        
        PropertiesFacade.INSTANCE.register(DBW__RESOURCE_BUNDLE);
        
        final char borderSign = this.getProperty(KEY__APPLICATION__BORDER_SIGN).charAt(0);
        final String message = this.getProperty(KEY__APPLICATION__MESSAGE_WELCOME);
        LoggerFacade.INSTANCE.message(borderSign, 80, message);
        
        final Boolean dropPreferencesFileAtStart = Boolean.FALSE;
        PreferencesFacade.INSTANCE.init(dropPreferencesFileAtStart);
        
        System.out.println("XXX DreamBetterWorlds.init() remove TestDatabase.createTestData()");
//        TestDatabase.createTestData();
        DatabaseFacade.INSTANCE.register(this.getProperty(KEY__APPLICATION__DATABASE));
        System.out.println("XXX DreamBetterWorlds.init() -> JavaFX+JPA+Serialization=https://gist.github.com/james-d/e485ac525c71e20bb453");
    
        BaseProvider.getDefault().getJobProvider().start();
    }

    @Override
    public void start(Stage stage) throws Exception {
        final Double width = PreferencesFacade.INSTANCE.getDouble(PREF__DBW_WIDTH, PREF__DBW_WIDTH__DEFAULT_VALUE);
        final Double height = PreferencesFacade.INSTANCE.getDouble(PREF__DBW_HEIGHT, PREF__DBW_HEIGHT__DEFAULT_VALUE);
        final Scene scene = new Scene(DesktopFacade.getDefault().getDesktop(), width, height);
        final String uriStylesheet = this.getClass().getResource(CSS__DREAM_BETTER_WORLDS).toExternalForm();
        scene.getStylesheets().add(uriStylesheet);
//        scene.setOnKeyReleased(DesktopFacade.getDefault().getGlobalKeyEventHandler());
        System.out.println(" XXX DreamBetterWorlds.start() GlobalKeyEventHandler()");
        
        stage.setTitle(this.getProperty(KEY__APPLICATION__TITLE));
        
        stage.setScene(scene);
        stage.setOnCloseRequest((WindowEvent we) -> {
           we.consume();
           
           this.onCloseRequest();
        });
        
        stage.show();
        
        this.checkShowAtStart();
    }
    
    private void checkShowAtStart() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Check show at start");
    
        final PauseTransition pt = new PauseTransition(DBW__LITTLE_DELAY__DURATION_250);
        pt.setOnFinished((ActionEvent event) -> {
            FileProvider.getDefault().checkShowAtStart();
        });
        pt.playFromStart();
    }
    
    private String getProperty(String propertyKey) {
        return PropertiesFacade.INSTANCE.getProperty(DBW__RESOURCE_BUNDLE, propertyKey);
    }
    
    private void onCloseRequest() {
        // Unsaved files open?
        final boolean hasUnsavedFiles = DesktopFacade.getDefault().checkForUnsavedFiles();
        if (!hasUnsavedFiles) {
            this.shutdownApplication();
            return;
        }
        
        // Ask dreamer save or not...
        DialogProvider.getDefault().showSaveMultiFilesDialog(
                (ActionEvent ae) -> { // Yes
                    DesktopFacade.getDefault().onActionSaveMultiFiles();
                    DialogProvider.getDefault().hide();
                    
                    final PauseTransition pt = new PauseTransition(DBW__LITTLE_DELAY__DURATION_250);
                    pt.setOnFinished((ActionEvent event) -> {
                        this.shutdownApplication();
                    });
                    pt.playFromStart();
                },
                (ActionEvent ae) -> { // No
                    DialogProvider.getDefault().hide();
                    this.shutdownApplication();
                });
    }
    
    private void shutdownApplication() {
        BaseProvider.getDefault().getJobProvider().stop();
        Injector.forgetAll();
        DatabaseFacade.INSTANCE.shutdown();
        
        final char borderSign = this.getProperty(KEY__APPLICATION__BORDER_SIGN).charAt(0);
        final String message = this.getProperty(KEY__APPLICATION__MESSAGE_GOODBYE);
        LoggerFacade.INSTANCE.message(borderSign, 80, message);
    
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
