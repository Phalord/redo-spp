package com.spp.gui.controller;

import com.spp.model.dataaccess.dao.ProjectDAO;
import com.spp.model.dataaccess.dao.ProjectResponsibleDAO;
import com.spp.model.dataaccess.idao.CRUD;
import com.spp.model.dataaccess.idao.IProjectResponsibleDAO;
import com.spp.model.domain.Project;
import com.spp.model.domain.ProjectResponsible;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static com.spp.gui.Dialog.displayConnectionError;


public class ControllerProjectInformation {
    @FXML private BorderPane borderPane;
    @FXML private Label projectResponsibleName;
    @FXML private Text projectDescription;
    @FXML private Text projectResources;

    @FXML
    private void closeWindow() {
        Stage stage1 = (Stage) borderPane.getScene().getWindow();
        stage1.close();
    }

    public final void buildScene(Project project) {
        getProjectInformation(project);
    }

    private void getProjectInformation(Project project) {
        CRUD<Project> projectCRUD = new ProjectDAO();
        project = projectCRUD.getByID(project.getProjectID());
        if (project != null) {
            projectDescription.setText(project.getDescription());
            projectResources.setText(project.getResources());
            setResponsibleInformation(project);
        } else {
            displayConnectionError();
        }
    }

    private void setResponsibleInformation(Project project) {
        IProjectResponsibleDAO iProjectResponsibleDAO = new ProjectResponsibleDAO();
        ProjectResponsible projectResponsible =
                iProjectResponsibleDAO.getResponsibleByCompanyID(project.getRequestedBy().getRelatedCompanyID());
        if (projectResponsible != null) {
            projectResponsibleName.setText(String
                    .format("%s %s", projectResponsible.getName(), projectResponsible.getSurname()));
        } else {
            displayResponsibleNotFound();
        }
    }

    private void displayResponsibleNotFound() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Responsable no encontrado");
        alert.setContentText("Lamentamos las molestias que esto pueda ocasionarle.");
        alert.showAndWait();
    }
}
