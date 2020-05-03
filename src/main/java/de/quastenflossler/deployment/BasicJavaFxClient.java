package de.quastenflossler.deployment;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import java.io.IOException;
import java.util.Locale;

@SpringBootApplication
@Import(SpringConfig.class)
public class BasicJavaFxClient extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicJavaFxClient.class);

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() throws IOException {

        LOGGER.debug("[START] Client will be initialized...");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        LOGGER.debug("context will be initialized");
        applicationContext = (ConfigurableApplicationContext) SpringContextFactory.getApplicationContext();
        LOGGER.debug("context is initialized");

        ControlManager.getInstance().setApplicationContext(applicationContext);
        ControlManager.getInstance().setActiveLocale(Locale.forLanguageTag("de-DE"));
        ControlManager.getInstance().init();

        stopWatch.stop();
        LOGGER.debug("[END] Client will be initialized... | Duration: {}ms", stopWatch.getTime());
    }

    @Override
    public void stop() {

        applicationContext.stop();
    }

    @Override
    public void start(final Stage primaryStage) {

        try {

            Stage mainStage = new Stage(StageStyle.UTILITY);
            mainStage.setTitle("Quastenflossler - JavaFX Deployment");
            mainStage.setResizable(false);

            ControlManager.getInstance().showScene(mainStage, BasicScene.MAIN);

        } catch (Exception e) {

            LOGGER.error("Error during application startup!", e);
        }
    }

    public static void main(String[] args) {

        try {

            launch(args);

        } catch (Exception e) {
            LOGGER.error("Error during application startup!", e);
        }
    }
}
