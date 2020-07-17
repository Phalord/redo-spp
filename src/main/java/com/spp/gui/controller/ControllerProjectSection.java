package com.spp.gui.controller;

import com.spp.model.dataaccess.dao.ProjectDAO;
import com.spp.model.dataaccess.dao.ProjectRequestDAO;
import com.spp.model.dataaccess.idao.IProjectDAO;
import com.spp.model.dataaccess.idao.IProjectRequestDAO;
import com.spp.model.domain.Project;
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

public class ControllerProjectSection {
    @FXML private Menu topMenu;
    @FXML private BorderPane borderPane;

    public final void setTopMenuText(String username) {
        topMenu.setText(username);
    }

    @FXML
    private void viewMyProject() {
        ProjectDAO iProjectDAO = new ProjectDAO();
        Project project = iProjectDAO.getProjectByStudentEnrollment(topMenu.getText());
        if (project != null) {
            displayProjectInformation(project);
        } else {
            displayNoProjectAssigned();
        }
    }

    @FXML
    private void requestProject() {
        if (validateNotAlreadyRequested()) {
            IProjectDAO iProjectDAO = new ProjectDAO();
            List<Project> availableProjects = iProjectDAO.getAvailableProjects();
            if (availableProjects == null) {
                displayConnectionError();
            } else if (availableProjects.isEmpty()) {
                displayNoAvailableProjects();
            } else {
                setRequestProjectScene(availableProjects);
            }
        } else {
            displayAlreadyRequested();
        }
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

    private boolean validateNotAlreadyRequested() {
        IProjectRequestDAO iProjectRequestDAO = new ProjectRequestDAO();
        return (iProjectRequestDAO.getProjectRequestByStudentEnrollment(topMenu.getText()) == null);
    }

    private void setRequestProjectScene(List<Project> availableProjects) {
        Stage window = (Stage) borderPane.getScene().getWindow();
        Parent viewFile;
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/views/View_ProjectRequest.fxml"));
            viewFile = loader.load();
            ControllerRequestProject controllerRequestProject = loader.getController();
            controllerRequestProject.setTopMenuText(topMenu.getText());
            controllerRequestProject.populateTable(availableProjects);
            window.setScene(new Scene(viewFile, 600, 400));
        } catch (IOException ioException) {
            Logger.getLogger(ControllerProjectSection.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            displaySomethingWentWrong();
        }
    }

    private void displayProjectInformation(Project project) {
        Parent viewFile;
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/views/View_ProjectInformation.fxml"));
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerProjectSection.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            return;
        }
        ControllerProjectInformation controllerProjectInformation = loader.getController();
        controllerProjectInformation.buildScene(project);
        Stage window = new Stage();
        window.setScene(new Scene(viewFile, 600, 400));
        window.setResizable(false);
        window.setTitle(project.getTitle());
        window.showAndWait();
    }

    private void closeWindow() {
        Stage stage1 = (Stage) borderPane.getScene().getWindow();
        stage1.close();
    }

    private void displayLogin() {
        try {
            new ControllerLogin().display();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerProjectSection.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            displaySomethingWentWrong();
        }
    }

    private void displayNoProjectAssigned() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("No tiene proyecto");
        alert.setContentText(String.format("Actualmente no se encuentra asignado a ningún %s",
                "proyecto. Por favor genere su solicitud."));
        alert.showAndWait();
    }

    private void displayNoAvailableProjects() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("No hay proyectos disponibles");
        alert.setContentText(String.format("Actualmente no existen proyectos disponibles para %s",
                "solicitar."));
        alert.showAndWait();
    }

    private void displayAlreadyRequested() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Proyecto Solicitado");
        alert.setContentText("Ya se ha enviado una solicitud de proyecto.");
        alert.showAndWait();
    }

    private void backScene() {
        Stage window = (Stage) borderPane.getScene().getWindow();
        Parent viewFile;
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/views/View_PractitionerHome.fxml"));
            viewFile = loader.load();
            ControllerPractitionerHome controllerPractitionerHome = loader.getController();
            controllerPractitionerHome.setTopMenuText(topMenu.getText());
            window.setScene(new Scene(viewFile, 600, 400));
        } catch (IOException ioException) {
            Logger.getLogger(ControllerProjectSection.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
        }
    }
}
