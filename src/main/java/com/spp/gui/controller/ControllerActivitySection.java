package com.spp.gui.controller;

import com.spp.model.dataaccess.dao.GroupDAO;
import com.spp.model.dataaccess.dao.PractitionerDAO;
import com.spp.model.dataaccess.idao.IGroupDAO;
import com.spp.model.dataaccess.idao.IPractitionerDAO;
import com.spp.model.domain.Group;
import com.spp.model.domain.Practitioner;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.spp.gui.Dialog.displayConnectionError;
import static com.spp.gui.Dialog.displaySomethingWentWrong;
import static com.spp.utils.MailSender.notifyDevelopers;

public class ControllerActivitySection {
    @FXML private Menu topMenu;
    @FXML private BorderPane borderPane;

    public final void setTopMenuText(String username) {
        topMenu.setText(username);
    }

    @FXML
    private void generateActivity() {
        IGroupDAO iGroupDAO = new GroupDAO();
        Group group = iGroupDAO.getProfessorGroup(topMenu.getText());
        if (group == null) {
            displayConnectionError();
        } else {
            IPractitionerDAO iPractitionerDAO = new PractitionerDAO();
            List<Practitioner> groupStudents = iPractitionerDAO.getGroupStudents(group);
            if (groupStudents == null) {
                displayConnectionError();
            } else if (groupStudents.isEmpty()) {
                displayNotGroupStudents();
            } else {
                setGenerateActivityScene(groupStudents);
            }
        }
    }

    private void setGenerateActivityScene(List<Practitioner> groupStudents) {
        Stage window = (Stage) borderPane.getScene().getWindow();
        Parent viewFile;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/View_AddActivity.fxml"));
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerActivitySection.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            notifyDevelopers(ioException);
            displaySomethingWentWrong();
            return;
        }
        ControllerAddActivity controllerAddActivity = loader.getController();
        String professorUsername = topMenu.getText();
        controllerAddActivity.setProfessorUsername(professorUsername);
        controllerAddActivity.initializePractitionerComboBox(groupStudents);
        window.setScene(new Scene(viewFile, 800, 400));
    }

    private void displayNotGroupStudents() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("No hay Practicantes");
        alert.setContentText("No hay Practicantes inscritos en el grupo.");
        alert.showAndWait();
    }

    @FXML
    private void goDeliveredActivities() {

    }

    @FXML
    private void logOut() {
        closeWindow();
        displayLogin();
    }

    @FXML
    private void back() {
        backScene();
    }

    private void closeWindow() {
        Stage stage1 = (Stage) borderPane.getScene().getWindow();
        stage1.close();
    }

    private void displayLogin() {
        Stage window = (Stage) borderPane.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/View_Login.fxml"));
        Parent viewFile;
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerActivitySection.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            notifyDevelopers(ioException);
            return;
        }
        ControllerLogin controllerLogin = loader.getController();
        controllerLogin.display();
        window.setScene(new Scene(viewFile, 300, 600));
        window.setResizable(false);
        window.show();
    }

    private void backScene() {
        Stage window = (Stage) borderPane.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/View_ProfessorHome.fxml"));
        Parent viewFile;
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerActivitySection.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            notifyDevelopers(ioException);
            displaySomethingWentWrong();
            return;
        }
        ControllerProfessorHome controllerProfessorHome = loader.getController();
        controllerProfessorHome.setTopMenuText(topMenu.getText());
        window.setScene(new Scene(viewFile, 600, 400));
    }
}
