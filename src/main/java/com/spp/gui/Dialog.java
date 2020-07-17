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

    public static void displayNotYetSupportedDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Funcionamiento sin implementar");
        alert.setContentText("Función a implementar en siguientes versiones.");
        alert.showAndWait();
    }

    public static void displayConnectionError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Error de Conexión");
        alert.setContentText("Fallo al conectar con el servidor. Verifique su conexión a internet.");
        alert.showAndWait();
    }

    public static void displaySuccessDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Operación Exitosa");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
