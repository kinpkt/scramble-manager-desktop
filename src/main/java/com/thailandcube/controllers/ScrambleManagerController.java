package com.thailandcube.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class ScrambleManagerController {
    @FXML private Label copyrightLabel;

    @FXML
    public void initialize() {
        copyrightLabel.setText("Copyright 2026 ThailandCube");
    }

    @FXML
    private void handleLoadWcifClick() {
        System.out.println("Loading WCIF");
    }
}
