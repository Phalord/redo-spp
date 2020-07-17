package com.spp.gui.controller;

import com.mysql.cj.conf.BooleanProperty;
import com.spp.model.domain.Project;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.spp.gui.Dialog.displaySomethingWentWrong;

public class ControllerRequestProject {
    @FXML private Menu topMenu;
    @FXML private BorderPane borderPane;
    @FXML private TableView<Project> availableProjectsTable;
    @FXML private TableColumn<Project, Integer> idColumn;
    @FXML private TableColumn<Project, String> titleColumn;
    @FXML private TableColumn<Project, String> requestColumn;

    public final void setTopMenuText(String username) {
        topMenu.setText(username);
    }

    public final void populateTable(List<Project> availableProjects) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("projectID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        initializeDetailsColumn();
        ObservableList<Project> availableProjectsOL =
                FXCollections.observableArrayList(availableProjects);
        availableProjectsTable.setItems(availableProjectsOL);
        availableProjectsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
                            setGraphic(button);
                            setAlignment(Pos.CENTER);
                        }
                    }
                };
        detailsColumn.setCellFactory(cellFactory);
        detailsColumn.setPrefWidth(120);
        availableProjectsTable.getColumns().add(detailsColumn);
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
        window.setResizable(false);
        window.setTitle(project.getTitle());
        window.showAndWait();
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

    @FXML
    private void request() {
        displaySomethingWentWrong();
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

    private void backScene() {
        Stage window = (Stage) borderPane.getScene().getWindow();
        Parent viewFile;
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/views/View_ProjectSection.fxml"));
            viewFile = loader.load();
            ControllerProjectSection controllerProjectSection = loader.getController();
            controllerProjectSection.setTopMenuText(topMenu.getText());
            window.setScene(new Scene(viewFile, 600, 400));
        } catch (IOException ioException) {
            Logger.getLogger(ControllerRequestProject.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
        }
    }
}
