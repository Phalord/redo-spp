package com.spp.gui.controller;

import com.spp.model.dataaccess.dao.ProjectDAO;
import com.spp.model.dataaccess.dao.ProjectRequestDAO;
import com.spp.model.dataaccess.idao.IProjectDAO;
import com.spp.model.dataaccess.idao.IProjectRequestDAO;
import com.spp.model.domain.Coordinator;
import com.spp.model.domain.Practitioner;
import com.spp.model.domain.Project;
import com.spp.model.domain.ProjectAssignment;
import com.spp.model.domain.ProjectRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.spp.gui.Dialog.displayConfirmationDialog;
import static com.spp.gui.Dialog.displayConnectionError;
import static com.spp.gui.Dialog.displaySomethingWentWrong;
import static com.spp.gui.Dialog.displaySuccessDialog;

public class ControllerDetailedProjectRequest {
    @FXML private Menu topMenu;
    @FXML private BorderPane borderPane;
    @FXML private TableView<Project> requestedProjectsTable;
    @FXML private TableColumn<Project, Integer> idColumn;
    @FXML private TableColumn<Project, String> titleColumn;
    @FXML private TableColumn<Project, String> relatedCompanyColumn;
    private List<ProjectRequest> pendingProjectRequests;

    public final void setPendingProjectRequests(List<ProjectRequest> pendingProjectRequests) {
        this.pendingProjectRequests = pendingProjectRequests;
    }

    public final List<ProjectRequest> getPendingProjectRequests() {
        return pendingProjectRequests;
    }

    public final void setTopMenuText(String username) {
        topMenu.setText(username);
    }

    public final void populateTable(ProjectRequest projectRequest) {
        List<Project> requestedProjects = getRequestedProjects(projectRequest);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("projectID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        relatedCompanyColumn.setCellValueFactory(new PropertyValueFactory<>("requestedBy"));
        initializeDetailsColumn();
        initializeAssignColumn(projectRequest);
        if (requestedProjects == null || requestedProjects.isEmpty()) {
            displayConnectionError();
        } else {
            ObservableList<Project> requestedProjectsOL =
                    FXCollections.observableArrayList(requestedProjects);
            requestedProjectsTable.setItems(requestedProjectsOL);
        }
    }

    private void initializeDetailsColumn() {
        TableColumn<Project, Void> detailsColumn = new TableColumn<>("Detalles");
        Callback<TableColumn<Project, Void>, TableCell<Project, Void>> cellFactory =
                param -> new TableCell<>() {
                    private final Button button = new Button("Detalles");

                    {
                        button.setOnAction((ActionEvent event) -> {
                            Project project = getTableView().getItems().get(getIndex());
                            displayProjectInformation(project);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setAlignment(Pos.CENTER);
                            setGraphic(button);
                        }
                    }
                };
        detailsColumn.setCellFactory(cellFactory);
        detailsColumn.setPrefWidth(81);
        requestedProjectsTable.getColumns().add(detailsColumn);
    }

    private void initializeAssignColumn(ProjectRequest projectAssignment) {
        TableColumn<Project, Void> assignColumn = new TableColumn<>("Asignar");
        Callback<TableColumn<Project, Void>, TableCell<Project, Void>> cellFactory =
                param -> new TableCell<>() {
                    private final Button button = new Button("Asignar");

                    {
                        button.setOnAction((ActionEvent event) -> {
                            Project project = getTableView().getItems().get(getIndex());
                            if (displayConfirmationDialog("¿Desea realizar la asignación?")) {
                                assignProject(project, projectAssignment);
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setAlignment(Pos.CENTER);
                            setGraphic(button);
                        }
                    }
                };
        assignColumn.setCellFactory(cellFactory);
        assignColumn.setPrefWidth(82);
        requestedProjectsTable.getColumns().add(assignColumn);
    }

    private void assignProject(Project project, ProjectRequest projectRequest) {
        ProjectAssignment projectAssignment = new ProjectAssignment();
        projectAssignment.setStatus("Active");
        projectAssignment.setProject(project);
        Coordinator coordinator = new Coordinator();
        coordinator.setUsername(topMenu.getText());
        projectAssignment.setAssignedBy(coordinator);
        Practitioner practitioner = new Practitioner();
        practitioner.setUsername(projectRequest.getRequestedBy());
        projectAssignment.setPractitioner(practitioner);
        IProjectDAO iProjectDAO = new ProjectDAO();
        if (iProjectDAO.assignProject(projectAssignment)) {
            IProjectRequestDAO iProjectRequestDAO = new ProjectRequestDAO();
            if (iProjectRequestDAO.deleteElement(projectRequest.getProjectRequestID())) {
                displaySuccessDialog(String
                        .format("El proyecto %s se ha asignado correctamente.", project.getTitle()));
                displayPractitionerSection();
            } else {
                displayConnectionError();
            }
        } else {
            displayConnectionError();
        }
    }

    private void displayProjectInformation(Project project) {
        Parent viewFile;
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/views/View_ProjectInformation.fxml"));
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerProjectInformation.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            return;
        }
        ControllerProjectInformation controllerProjectInformation = loader.getController();
        controllerProjectInformation.buildScene(project);
        Stage window = new Stage();
        window.setScene(new Scene(viewFile, 600, 400));
        window.setTitle(project.getTitle());
        window.setResizable(false);
        window.showAndWait();
    }

    private void displayPractitionerSection() {
        Stage window = (Stage) borderPane.getScene().getWindow();
        Parent viewFile;
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/views/View_PractitionerSection.fxml"));
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerDetailedProjectRequest.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            return;
        }
        ControllerPractitionerSection controllerPractitionerSection = loader.getController();
        controllerPractitionerSection.setTopMenuText(topMenu.getText());
        window.setScene(new Scene(viewFile, 600, 400));
    }

    private List<Project> getRequestedProjects(ProjectRequest projectRequest) {
        IProjectDAO iProjectDAO = new ProjectDAO();
        return iProjectDAO.getProjectRequestedProjects(projectRequest.getProjectRequestID());
    }

    @FXML
    private void back() {
        setBackScene();
    }

    @FXML
    private void logOut() {
        closeWindow();
        displayLogin();
    }

    private void closeWindow() {
        Stage stage1 = (Stage) borderPane.getScene().getWindow();
        stage1.close();
    }

    private void displayLogin() {
        try {
            new ControllerLogin().display();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerPractitionerHome.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            displaySomethingWentWrong();
        }
    }

    private void setBackScene() {
        Parent viewFile;
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/views/View_ProjectRequests.fxml"));
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerRequestProject.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            return;
        }
        ControllerProjectRequests controllerProjectRequests = loader.getController();
        controllerProjectRequests.setTopMenuText(topMenu.getText());
        controllerProjectRequests.populateTable(getPendingProjectRequests());
        Stage window = (Stage) borderPane.getScene().getWindow();
        window.setScene(new Scene(viewFile, 600, 400));
    }
}
