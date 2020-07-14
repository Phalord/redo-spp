package com.spp.gui.controller;

import com.spp.model.dataaccess.dao.UserDAO;
import com.spp.model.dataaccess.idao.IUserDAO;
import com.spp.model.domain.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

import static com.spp.utils.TextValidator.*;

public class ControllerLogin {
    private final Stage window = new Stage();
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label logInMessage;

    public void display() throws IOException {
        Parent viewFile = FXMLLoader.load(getClass().getResource("/views/View_Login.fxml"));
        window.setScene(new Scene(viewFile, 300, 600));
        window.setResizable(false);
        window.show();
    }

    @FXML
    private void logIn() {
        IUserDAO<User> iUserDAO = new UserDAO();
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (validateUserName(username)) {
            User user = iUserDAO.getUserByUsername(username);
            if (user != null) {
                if (BCrypt.checkpw(password, iUserDAO.getUserByUsername(username).getPassword())) {
                    HomeController homeController = new HomeController(user.getUserType(), username);
                    homeController.display();
                    closeWindow();
                } else {
                    incorrectCredentials();
                }
            } else {
                incorrectCredentials();
            }
        } else {
            incorrectUserName();
        }
    }

    private void incorrectCredentials() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Credenciales Incorrectas");
        alert.setContentText("Usuario o contraseña incorrectas. Por favor intente nuevamente.");
        alert.showAndWait();
    }

    private void incorrectUserName() {
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
                validateProfessorEmployeeNumber(userName));
    }
}
