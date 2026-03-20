package com.thailandcube.scramblemanagerdesktop;

import atlantafx.base.theme.PrimerLight;
import com.thailandcube.services.FXRouter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setMaximized(true);

        Font.loadFont(getClass().getResourceAsStream("/com/thailandcube/fonts/LINESeedSansTH_A_Rg.ttf"), 14);
        Font.loadFont(getClass().getResourceAsStream("/com/thailandcube/fonts/LINESeedSansTH_A.ttf"), 14);

        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());

        Application.getUserAgentStylesheet();

        FXRouter.bind(this, stage, "Scramble Manager");
        configRoute();
        FXRouter.goTo("scramble-manager");

        stage.getScene().getStylesheets().add(
            getClass().getResource("/com/thailandcube/css/theme.css").toExternalForm()
        );
    }

    private void configRoute() {
        String viewPath = "com/thailandcube/views/";
        FXRouter.when("scramble-manager", viewPath + "scramble-manager-view.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}