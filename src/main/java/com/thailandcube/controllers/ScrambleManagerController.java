package com.thailandcube.controllers;

import com.thailandcube.models.PublicWcif;
import com.thailandcube.services.ScrambleOrganizeService;
import com.thailandcube.services.WcifApiService;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

public class ScrambleManagerController {
    @FXML private TextField competitionIdTextField;
    @FXML private Label wcifLoadingStatusLabel;
    @FXML private Button uploadScrambleButton;
    @FXML private Button downloadOrganizedZipButton;

    private final WcifApiService apiService = WcifApiService.getInstance();
    private final ScrambleOrganizeService scrambleOrganizeService = ScrambleOrganizeService.getInstance();
    private PublicWcif wcif;

    @FXML
    public void initialize() {
        uploadScrambleButton.setDisable(true);
        downloadOrganizedZipButton.setDisable(true);
    }

    @FXML
    private void handleLoadWcifClick() {
        String competitionId = competitionIdTextField.getText();
        uploadScrambleButton.setDisable(true);

        wcifLoadingStatusLabel.setText("Loading...");
        wcifLoadingStatusLabel.getStyleClass().clear();
        wcifLoadingStatusLabel.getStyleClass().add("text-warning");

        apiService.fetchWcifData(competitionId)
            .thenAccept(wcifData -> {
                Platform.runLater(() -> {
                    wcifLoadingStatusLabel.setText("Success: " + wcifData.getName());
                    wcifLoadingStatusLabel.getStyleClass().clear();
                    wcifLoadingStatusLabel.getStyleClass().add("text-success");
                    uploadScrambleButton.setDisable(false);
                    wcif = wcifData;
                });
            })
            .exceptionally(e -> {
                System.err.println("API Request Error: " + e.getMessage());
                e.printStackTrace();

                Platform.runLater(() -> {
                    wcifLoadingStatusLabel.setText("Error fetching competition data.");
                    wcifLoadingStatusLabel.getStyleClass().clear();
                    wcifLoadingStatusLabel.getStyleClass().add("text-danger");
                    uploadScrambleButton.setDisable(true);
                });
                return null;
            }
        );
    }

    @FXML
    private void handleFileUploadClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Scrambles ZIP File");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archive Files (*.zip, *.rar)", "*.zip", "*.rar");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(competitionIdTextField.getScene().getWindow());

        if (file != null) {
            System.out.println("Selected Archive: " + file.getAbsolutePath());

            wcifLoadingStatusLabel.setText("Archive Selected: " + file.getName());
            wcifLoadingStatusLabel.getStyleClass().clear();
            wcifLoadingStatusLabel.getStyleClass().add("text-success");

            try {
                scrambleOrganizeService.organizeScrambles(file, wcif);
            }
            catch (Exception e) {
                System.err.println("Error while organizing Scrambles to ZIP: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleDownloadOrganizedZip() {
        System.out.println("Button pressed");
    }
}