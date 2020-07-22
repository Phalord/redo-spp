package com.spp.gui.controller;

import com.spp.model.dataaccess.dao.UserDAO;
import com.spp.model.dataaccess.idao.IUserDAO;
import com.spp.model.domain.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.function.UnaryOperator;

import static com.spp.gui.Dialog.displayTooManyCharacters;
import static com.spp.utils.TextValidator.*;

public class ControllerLogin {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    public final void display() {
        initializeUsernameFieldFormatter();
        initializePasswordFieldFormatter();
    }

    private void initializeUsernameFieldFormatter() {
        UnaryOperator<TextFormatter.Change> rejectChange = change -> {
            if (change.isContentChange()) {
                if (change.getControlNewText().length() > 9) {
                    displayTooManyCharacters((short) 9);
                    return null;
                }
                if (change.getControlNewText()
                        .matches("^[!¡?\\[\\]*´|'-.^~`}{:¨;,\"$% &/()=¿+#]+")) {
                    usernameField.setText("");
                    return null;
                }
            }
            return change;
        };
        usernameField.setTextFormatter(new TextFormatter<TextFormatter.Change>(rejectChange));
    }

    private void initializePasswordFieldFormatter() {
        UnaryOperator<TextFormatter.Change> rejectChange = change -> {
            if (change.isContentChange()) {
                if (change.getControlNewText().length() > 10) {
                    displayTooManyCharacters((short) 10);
                    return null;
                }
                if (!change.getControlNewText().matches("^[a-zA-Z0-9ñÑ]*$")) {
                    passwordField.setText("");
                    return null;
                }
            }
            return change;
        };
        passwordField.setTextFormatter(new TextFormatter<TextFormatter.Change>(rejectChange));
    }

    @FXML
    private void logIn() {
        IUserDAO<User> iUserDAO = new UserDAO();
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (validateUserName(username)) {
            User user = iUserDAO.getUserByUsername(username);
            if (user != null) {
                if (user.isActive()) {
                    if (BCrypt.checkpw(password, user.getPassword())) {
                        HomeController homeController =
                                new HomeController(user.getUserType(), username);
                        homeController.display();
                        closeWindow();
                    } else {
                        displayIncorrectCredentials();
                    }
                } else {
                    displayIncorrectCredentials();
                }
            } else {
                displayIncorrectCredentials();
            }
        } else {
            displayIncorrectUserName();
        }
    }

    private void displayIncorrectCredentials() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Credenciales Incorrectas");
        alert.setContentText("Usuario o contraseña incorrectas. Por favor intente nuevamente.");
        alert.showAndWait();
    }

    private void displayIncorrectUserName() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Nombre de Usuario incorrecto");
        alert.setContentText("Formato de nombre de usuario incorrecto. Intente nuevamente.");
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage1 = (Stage) passwordField.getScene().getWindow();
        stage1.close();
    }

    private boolean validateUserName(String userName) {
        return (validatePractitionerEnrollment(userName) ||
                validateCoordinatorEmployeeNumber(userName) ||
                validateProfessorEmployeeNumber(userName) ||
                validateAdministratorEmployeeNumber(userName));
    }
}
