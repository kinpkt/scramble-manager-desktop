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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ScrambleManagerController {
    @FXML private TextField competitionIdTextField;
    @FXML private Label wcifLoadingStatusLabel;
    @FXML private Button uploadScrambleButton;
    @FXML private Button downloadOrganizedZipButton;

    private final WcifApiService apiService = WcifApiService.getInstance();
    private final ScrambleOrganizeService scrambleOrganizeService = ScrambleOrganizeService.getInstance();
    private PublicWcif wcif;
    private File organizedZip;

    private void changeStatusTextColor(String className) {
        wcifLoadingStatusLabel.getStyleClass().clear();
        wcifLoadingStatusLabel.getStyleClass().add(className);
        wcifLoadingStatusLabel.getStyleClass().add("bold");
    }

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
        changeStatusTextColor("text-warning");

        apiService.fetchWcifData(competitionId)
            .thenAccept(wcifData -> {
                Platform.runLater(() -> {
                    wcifLoadingStatusLabel.setText("Success: " + wcifData.getName());
                    changeStatusTextColor("text-success");
                    uploadScrambleButton.setDisable(false);
                    wcif = wcifData;
                });
            })
            .exceptionally(e -> {
                System.err.println("API Request Error: " + e.getMessage());
                e.printStackTrace();

                Platform.runLater(() -> {
                    wcifLoadingStatusLabel.setText("Error fetching competition data.");
                    changeStatusTextColor("text-danger");
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

        File downloadsDir = new File(System.getProperty("user.home"), "Downloads");
        if (downloadsDir.exists() && downloadsDir.isDirectory())
            fileChooser.setInitialDirectory(downloadsDir);

        File file = fileChooser.showOpenDialog(competitionIdTextField.getScene().getWindow());

        if (file != null) {
            System.out.println("Selected Archive: " + file.getAbsolutePath());

            wcifLoadingStatusLabel.setText("Archive Selected: " + file.getName());
            changeStatusTextColor("text-success");

            try {
                organizedZip = scrambleOrganizeService.organizeScrambles(file, wcif);
                downloadOrganizedZipButton.setDisable(false);
            }
            catch (Exception e) {
                System.err.println("Error while organizing Scrambles to ZIP: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleDownloadOrganizedZip() {
        if (organizedZip == null || !organizedZip.exists()) {
            System.out.println("No organized zip file available to download.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Organized Scrambles");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("ZIP files (*.zip)", "*.zip");
        fileChooser.getExtensionFilters().add(extFilter);

        if (wcif != null)
            fileChooser.setInitialFileName("[ORGANIZED] - " + wcif.getName() + ".zip");

        File downloadsDir = new File(System.getProperty("user.home"), "Downloads");
        if (downloadsDir.exists() && downloadsDir.isDirectory())
            fileChooser.setInitialDirectory(downloadsDir);

        File destinationFile = fileChooser.showSaveDialog(downloadOrganizedZipButton.getScene().getWindow());

        if (destinationFile != null) {
            try {
                // Copy the temp zip file to the user's chosen destination
                Files.copy(organizedZip.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                System.out.println("File successfully saved to: " + destinationFile.getAbsolutePath());

                // Update the UI to show success
                wcifLoadingStatusLabel.setText("Saved: " + destinationFile.getName());
                changeStatusTextColor("text-success");
            }
            catch (IOException e) {
                System.err.println("Error saving file: " + e.getMessage());
                e.printStackTrace();

                wcifLoadingStatusLabel.setText("Error saving file.");
                changeStatusTextColor("text-danger");
            }
        }
    }
}