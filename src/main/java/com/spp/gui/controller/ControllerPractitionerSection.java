package com.spp.gui.controller;

import com.spp.model.dataaccess.dao.GroupDAO;
import com.spp.model.dataaccess.dao.ProjectRequestDAO;
import com.spp.model.dataaccess.idao.IGroupDAO;
import com.spp.model.dataaccess.idao.IProjectRequestDAO;
import com.spp.model.domain.Group;
import com.spp.model.domain.ProjectRequest;
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

public class ControllerPractitionerSection {
    @FXML private Menu topMenu;
    @FXML private BorderPane borderPane;

    public final void setTopMenuText(String username) {
        topMenu.setText(username);
    }

    @FXML
    private void registerPractitioner() {
        IGroupDAO iGroupDAO = new GroupDAO();
        List<Group> availableGroups = iGroupDAO.getAvailableGroups();
        if (availableGroups == null) {
            displayConnectionError();
        } else if (availableGroups.isEmpty()) {
            displayNoAvailableGroups();
        } else {
            displayAddPractitionerScene(availableGroups);
        }
    }

    public static void displayNoAvailableGroups() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("No hay Grupos disponibles");
        alert.setContentText("No se encontraron grupos con capacidad para otro Practicante.");
        alert.showAndWait();
    }

    private void displayAddPractitionerScene(List<Group> availableGroups) {
        Stage window = (Stage) borderPane.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/views/View_AddPractitioner.fxml"));
        Parent viewFile;
        try {
            viewFile = loader.load();
            ControllerAddPractitioner controllerAddPractitioner = loader.getController();
            controllerAddPractitioner.initialize(availableGroups);
            controllerAddPractitioner.setTopMenuText(topMenu.getText());
            window.setScene(new Scene(viewFile));
        } catch (IOException ioException) {
            Logger.getLogger(ControllerPractitionerSection.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            displaySomethingWentWrong();
        }
    }

    @FXML
    private void assignProject() {
        IProjectRequestDAO iProjectRequestDAO = new ProjectRequestDAO();
        List<ProjectRequest> pendingProjectRequests = iProjectRequestDAO.getPendingRequests();
        if (pendingProjectRequests == null) {
            displayConnectionError();
        } else if (pendingProjectRequests.isEmpty()) {
            displayNoPendingRequests();
        } else {
            setAssignProjectScene(pendingProjectRequests);
        }
    }

    private void setAssignProjectScene(List<ProjectRequest> pendingProjectRequests) {
        Stage window = (Stage) borderPane.getScene().getWindow();
        Parent viewFile;
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/views/View_ProjectRequests.fxml"));
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerPractitionerSection.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            notifyDevelopers(ioException);
            displaySomethingWentWrong();
            return;
        }
        ControllerProjectRequests controllerProjectRequests = loader.getController();
        controllerProjectRequests.setTopMenuText(topMenu.getText());
        controllerProjectRequests.populateTable(pendingProjectRequests);
        window.setScene(new Scene(viewFile, 600, 400));
    }

    @FXML
    private void goPractitionerList() {

    }

    @FXML
    private void deletePractitioner() {
        Stage window = (Stage) borderPane.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/views/View_DeletePractitioner.fxml"));
        Parent viewFile;
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerPractitionerSection.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            notifyDevelopers(ioException);
            displaySomethingWentWrong();
            return;
        }
        ControllerDeletePractitioner controllerDeletePractitioner = loader.getController();
        controllerDeletePractitioner.setTopMenuText(topMenu.getText());
        window.setScene(new Scene(viewFile));
    }

    @FXML
    private void back() {
        backScene();
    }

    @FXML
    private void logOut() {
        closeWindow();
        displayLogin();
    }

    private void backScene() {
        Stage window = (Stage) borderPane.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/views/View_CoordinatorHome.fxml"));
        Parent viewFile;
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerPractitionerSection.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            notifyDevelopers(ioException);
            displaySomethingWentWrong();
            return;
        }
        ControllerCoordinatorHome controllerCoordinatorHome = loader.getController();
        controllerCoordinatorHome.setTopMenuText(topMenu.getText());
        window.setScene(new Scene(viewFile, 600, 400));
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
            Logger.getLogger(ControllerPractitionerSection.class.getName())
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

    private void displayNoPendingRequests() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("No hay proyectos disponibles");
        alert.setContentText(String.format("Actualmente no existen proyectos disponibles para %s",
                "solicitar."));
        alert.showAndWait();
    }
}
