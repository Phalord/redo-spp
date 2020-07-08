package com.spp.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;

public class ControllerGenerateDocumentation {
    @FXML private Menu topMenu;
    @FXML private BorderPane borderPane;

    public void setTopMenuText(String username) {
        topMenu.setText(username);
    }
}
