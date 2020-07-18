package com.spp.gui.controller;

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
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.spp.gui.Dialog.displaySomethingWentWrong;

public class ControllerProjectRequests {
    @FXML private Menu topMenu;
    @FXML private BorderPane borderPane;
    @FXML private TableView<ProjectRequest> pendingRequestsTable;
    @FXML private TableColumn<ProjectRequest, Integer> idColumn;
    @FXML private TableColumn<ProjectRequest, String> requestedByColumn;
    @FXML private TableColumn<ProjectRequest, Timestamp> requestedAtColumn;

    public final void setTopMenuText(String username) {
        topMenu.setText(username);
    }

    public final void populateTable(List<ProjectRequest> pendingProjectRequests) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("projectRequestID"));
        requestedByColumn.setCellValueFactory(new PropertyValueFactory<>("requestedBy"));
        requestedAtColumn.setCellValueFactory(new PropertyValueFactory<>("requestedAt"));
        initializeDetailsColumns(pendingProjectRequests);
        ObservableList<ProjectRequest> pendingProjectRequestsOL =
                FXCollections.observableArrayList(pendingProjectRequests);
        pendingRequestsTable.setItems(pendingProjectRequestsOL);
    }

    private void initializeDetailsColumns(List<ProjectRequest> pendingProjectRequests) {
        TableColumn<ProjectRequest, Void> detailsColumn = new TableColumn<>("Detalles");
        Callback<TableColumn<ProjectRequest, Void>, TableCell<ProjectRequest, Void>> cellFactory =
                param -> new TableCell<>() {
                    private final Button button = new Button("Ver más");

                    {
                        button.setOnAction((ActionEvent event) -> {
                            ProjectRequest projectRequest = getTableView().getItems().get(getIndex());
                            displayProjectRequest(projectRequest, pendingProjectRequests);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(button);
                            setAlignment(Pos.CENTER);
                        }
                    }
                };
        detailsColumn.setCellFactory(cellFactory);
        detailsColumn.setPrefWidth(120);
        pendingRequestsTable.getColumns().add(detailsColumn);
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

    private void displayProjectRequest(ProjectRequest projectRequest,
                                       List<ProjectRequest> pendingProjectRequests) {
        Stage window = (Stage) borderPane.getScene().getWindow();
        Parent viewFile;
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/views/View_DetailedProjectRequest.fxml"));
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerRequestProject.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            return;
        }
        ControllerDetailedProjectRequest controllerDetailedProjectRequest = loader.getController();
        controllerDetailedProjectRequest.setTopMenuText(topMenu.getText());
        controllerDetailedProjectRequest.populateTable(projectRequest);
        controllerDetailedProjectRequest.setPendingProjectRequests(pendingProjectRequests);
        window.setScene(new Scene(viewFile, 600, 400));
    }

    private void backScene() {
        Stage window = (Stage) borderPane.getScene().getWindow();
        Parent viewFile;
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/views/View_PractitionerSection.fxml"));
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerProjectRequests.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            return;
        }
        ControllerPractitionerSection controllerPractitionerSection = loader.getController();
        controllerPractitionerSection.setTopMenuText(topMenu.getText());
        window.setScene(new Scene(viewFile, 600, 400));
    }
}
