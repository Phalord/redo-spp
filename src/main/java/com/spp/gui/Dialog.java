package com.spp.gui;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Dialog {

    public static void displaySomethingWentWrong() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Algo ha salido mal");
        alert.setContentText("Lamentamos las molestias que esto pueda ocasionarle.");
        alert.showAndWait();
    }

    public static void displayNotYetSupportedDialog() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Funcionamiento sin implementar");
        alert.setContentText("Función a implementar en siguientes versiones.");
        alert.showAndWait();
    }

    public static boolean displayConfirmationDialog(String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        return result.filter(buttonType -> (buttonType == ButtonType.OK)).isPresent();
    }

    public static void displayConnectionError() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Error de Conexión");
        alert.setContentText("Fallo al conectar con el servidor. Verifique su conexión a internet.");
        alert.showAndWait();
    }

    public static void displaySuccessDialog(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Operación Exitosa");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void displayRecordAlreadyExist() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("No se pudo realizar el registro");
        alert.setContentText("El registro ya se encuentra en la base de datos. Intente otro");
        alert.showAndWait();
    }
    
    public static boolean displayRecordConfirmation() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("¿Está seguro que desea realizar el registro con esos datos?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.filter(buttonType -> (buttonType == ButtonType.OK)).isPresent();
    }
    
    public static boolean displayDeleteConfirmation() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("¿Esta seguro que desea cambiar a 'no activo' el registro seleccionado?");
        Optional<ButtonType> result = alert.showAndWait();
        return (result.filter(buttonType -> (buttonType == ButtonType.OK)).isPresent());
    }
    
    public static boolean displayCancelConfirmation() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("¿Esta seguro de que desea cancelar?, No se guardará ningún cambio");
        Optional<ButtonType> result = alert.showAndWait();
        return result.filter(buttonType -> (buttonType == ButtonType.OK)).isPresent();
    }
    
    public static void displayRecordSuccessDialog() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Registro exitoso");
        alert.setContentText("¿Se ha realizado el registro exitosamente!");
        alert.showAndWait();      
    }
    
    public static void displayEmptyFields() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Campos vacíos");
        alert.setContentText("Debe llenar todos los campos");
        alert.showAndWait(); 
    }
    
    public static void displaySuccessDisableDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Eliminación exitosa");
        alert.setContentText("El registro seleccionado ahora esta inactivo");
        alert.showAndWait();  
    } 

    public static void displayNoActivitiesToReport() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Sin Actividades Abiertas");
        alert.setContentText("No cuenta con actividades asignadas por entregar");
        alert.showAndWait();
    }

    public static void displayTooManyCharacters(short characterLength) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Límite de caracteres excedido");
        alert.setContentText(String.format("El límite de es de %d. caracteres", characterLength));
        alert.showAndWait();
    }

    public static void displayNoActivitiesAdded() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Sin actividades a reportar");
        alert.setContentText("No ha añadido ninguna actividad para reportar.");
        alert.showAndWait();
    }
}
