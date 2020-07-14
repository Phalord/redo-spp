package com.spp.gui.controller;

import com.spp.model.dataaccess.dao.ProjectDAO;
import com.spp.model.dataaccess.idao.IProjectDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.spp.gui.Dialog.displaySomethingWentWrong;

public class ControllerRequestProject {
    @FXML private FlowPane projectCards;
    @FXML private Menu topMenu;
    @FXML private BorderPane borderPane;
    @FXML private Label noProjectsMessage;

    public final void setTopMenuText(String username) {
        topMenu.setText(username);
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

    public void loadProjects() {
        IProjectDAO iProjectDAO = new ProjectDAO();

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
