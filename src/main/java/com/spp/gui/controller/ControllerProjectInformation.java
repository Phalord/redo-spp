package com.spp.gui.controller;

import com.spp.model.dataaccess.dao.ProjectResponsibleDAO;
import com.spp.model.dataaccess.idao.IProjectResponsibleDAO;
import com.spp.model.domain.Project;
import com.spp.model.domain.ProjectResponsible;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerProjectInformation {
    Stage window = new Stage();
    @FXML private BorderPane borderPane;
    @FXML private Label projectResponsible;
    @FXML private Text projectDescription;
    @FXML private Text projectResources;

    public void display(Project project) throws IOException {
        Parent viewFile = FXMLLoader
                .load(getClass().getResource("/views/View_ProjectInformation.fxml"));
        window.setScene(new Scene(viewFile, 300, 400));
        setResponsibleInformation(project);
        window.setResizable(false);
        window.showAndWait();
    }

    @FXML
    private void closeWindow() {
        Stage stage1 = (Stage) borderPane.getScene().getWindow();
        stage1.close();
    }

    private void setResponsibleInformation(Project project) {
        IProjectResponsibleDAO iProjectResponsibleDAO = new ProjectResponsibleDAO();
        ProjectResponsible projectResponsible =
                iProjectResponsibleDAO.getResponsibleByCompanyID(project.getProjectID());
        if (projectResponsible != null) {
            fillInformation(project, projectResponsible);
        } else {
            displayResponsibleNotFound();
        }
    }

    private void fillInformation(Project project, ProjectResponsible projectResponsible) {
        this.projectResponsible.setText(String
                .format("%s%s", projectResponsible.getName(), projectResponsible.getSurname()));
        this.projectDescription.setText(project.getDescription());
        this.projectResources.setText(project.getResources());
    }

    private void displayResponsibleNotFound() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Responsable no encontrado");
        alert.setContentText("Lamentamos las molestias que esto pueda ocasionarle.");
        alert.showAndWait();
    }
}
