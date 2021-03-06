package de.quastenflossler.deployment;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class ControlManager {

    private static ControlManager INSTANCE;

    private static final Logger LOGGER = LoggerFactory.getLogger(ControlManager.class);
    private static final String FXML_DIR = "/fxml/";
    private static final String I18N_BASE_NAME = "lang.javafx_lang";

    private ConfigurableApplicationContext applicationContext;
    private Stage primaryStage;
    private Locale activeLocale;
    private BasicScene activeScene;
    private Map<String, Scene> scenes = new HashMap<>();

    private ControlManager() {
        // singleton ...
    }

    public void setApplicationContext(final ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public BasicScene getActiveScene() {
        return activeScene;
    }

    public Locale getActiveLocale() {
        return activeLocale;
    }

    public void setActiveLocale(final Locale locale) {

        activeLocale = locale;
        LOGGER.debug("Active locale is {}", activeLocale);
    }

    public static ControlManager getInstance() {

        if (INSTANCE == null) {

            INSTANCE = new ControlManager();
        }

        return INSTANCE;
    }

    public void init() throws IOException {

        LOGGER.debug("[START] initialization of control manager");

        loadScenesByFxml();

        LOGGER.debug("[END] initialization of control manager");
    }

    public void showScene(final Stage stage, final BasicScene scene) {

        stage.setScene(scenes.get(scene.getName()));
        stage.sizeToScene();
        stage.show();
        stage.toFront();
        UiHelper.centerOnScreen(stage);

        primaryStage = stage;
        activeScene = scene;
    }

    public void showScene(final BasicScene scene) {

        primaryStage.setScene(scenes.get(scene.getName()));
        primaryStage.sizeToScene();
        activeScene = scene;
    }

    public void replaceStage(final Stage newStage, final BasicScene scene, final Locale newLocale) throws IOException {

        setActiveLocale(newLocale);
        loadScenesByFxml();
        replaceStage(newStage, scene);
    }

    public void replaceStage(final Stage newStage, final BasicScene scene) {

        newStage.setScene(scenes.get(scene.getName()));
        newStage.sizeToScene();
        newStage.show();
        newStage.toFront();
        UiHelper.centerOnScreen(newStage);

        primaryStage.close();
        primaryStage = newStage;
        activeScene = scene;
    }

    private void loadScenesByFxml() throws IOException {

        LOGGER.debug("[START] preloading scenes...");

        scenes.clear();

        for (BasicScene scene : BasicScene.values()) {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML_DIR + scene.getFxmlFile()));
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            fxmlLoader.setResources(ResourceBundle.getBundle(I18N_BASE_NAME, activeLocale));
            scenes.put(scene.getName(), fxmlLoader.load());

            LOGGER.debug("Scene \"{}\" loaded successfully", scene);
        }

        LOGGER.debug("[END] preloading scenes...");
    }
}
