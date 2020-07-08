package com.spp.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;

public class ControllerProjectSection {
    @FXML private Menu topMenu;
    @FXML private BorderPane borderPane;

    public final void setTopMenuText(String username) {
        topMenu.setText(username);
    }
}
