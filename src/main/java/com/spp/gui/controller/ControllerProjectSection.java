package com.spp.gui.controller;

import com.spp.model.dataaccess.dao.ProjectDAO;
import com.spp.model.dataaccess.idao.CRUD;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerProjectSection {
    @FXML private Menu topMenu;
    @FXML private BorderPane borderPane;

    public final void setTopMenuText(String username) {
        topMenu.setText(username);
    }

    @FXML
    public void viewMyProject() {
        ProjectDAO iProjectDAO = new ProjectDAO();
        Project project = iProjectDAO.getProjectByStudentEnrollment(topMenu.getText());
        if (project != null) {
            try {
                new ControllerProjectInformation().display(project);
            } catch (IOException ioException) {
                Logger.getLogger(ControllerProjectSection.class.getName())
                        .log(Level.SEVERE, ioException.getMessage(), ioException);
                displaySomethingWentWrong();
            }
        } else {
            displayNoProjectAssigned();
        }
    }

    @FXML
    public void requestProject() {
        setRequestProjectScene();
    }

    @FXML
    public final void logOut() {
        closeWindow();
        displayLogin();
    }

    @FXML
    public void back() {
        backScene();
    }

    //TODO
    private void setRequestProjectScene() {
        Stage window = (Stage) borderPane.getScene().getWindow();
        Parent viewFile;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/View_ProjectRequest.fxml"));
            viewFile = loader.load();
            //ControllerRequestProject controllerRequestProject = loader.getController();
            //controllerRequestProject.setTopMenuText(topMenu.getText());
            //controllerRequestProject.loadProjects();
            window.setScene(new Scene(viewFile, 600, 400));
        } catch (IOException ioException) {
            Logger.getLogger(ControllerProjectSection.class.getName()).log(Level.SEVERE, ioException.getMessage(), ioException);
            displaySomethingWentWrong();
        }
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
        alert.setContentText("Actualmente no se encuentra asignado a ningún proyecto. Por favor genere su solicitud.");
        alert.showAndWait();
    }

    private void displaySomethingWentWrong() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Algo ha salido mal");
        alert.setContentText("Lamentamos las molestias que esto pueda ocasionarle. Seguimos en desarrollo.");
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
