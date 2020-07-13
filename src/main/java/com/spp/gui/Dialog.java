package com.spp.gui;

import javafx.scene.control.Alert;

public class Dialog {

    public static void displaySomethingWentWrong() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Algo ha salido mal");
        alert.setContentText("Lamentamos las molestias que esto pueda ocasionarle.");
        alert.showAndWait();
    }

}
