package com.spp.gui.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.spp.gui.Dialog.displaySomethingWentWrong;
import static com.spp.model.Main.*;
import static com.spp.utils.MailSender.notifyDevelopers;

public class HomeController implements IWindowGUI {
    private Stage window;

    public HomeController(String accountType, String username) {
        buildStage(new Stage(), accountType);
        switch (accountType) {
            case PRACTITIONER:
                buildPractitionerHomeScene(username);
                break;
            case COORDINATOR:
                buildCoordinatorHomeScene(username);
                break;
            case PROFESSOR:
                buildProfessorHomeScene(username);
                break;
            case ADMINISTRATOR:
                buildAdministratorHomeScene(username);
                break;
            default:
                displaySomethingWentWrong();
        }
    }

    @Override
    public void display() {
        window.show();
    }

    @Override
    public void buildStage(Stage stage, String title) {
        window = stage;
        window.setTitle(title);
    }

    private void buildPractitionerHomeScene(String username) {
        Parent viewFile;
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/views/View_PractitionerHome.fxml"));
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(HomeController.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            notifyDevelopers(ioException);
            displaySomethingWentWrong();
            return;
        }
        ControllerPractitionerHome controllerPractitionerHome = loader.getController();
        controllerPractitionerHome.setTopMenuText(username);
        window.setScene(new Scene(viewFile, 600, 400));
        window.setResizable(false);
    }

    private void buildCoordinatorHomeScene(String username) {
        Parent viewFile;
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/views/View_CoordinatorHome.fxml"));
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(HomeController.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            notifyDevelopers(ioException);
            displaySomethingWentWrong();
            return;
        }
        ControllerCoordinatorHome controllerCoordinatorHome = loader.getController();
        controllerCoordinatorHome.setTopMenuText(username);
        window.setScene(new Scene(viewFile, 600, 400));
        window.setResizable(false);
    }

    private void buildProfessorHomeScene(String username) {
        Parent viewFile;
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/views/View_ProfessorHome.fxml"));
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(HomeController.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            notifyDevelopers(ioException);
            displaySomethingWentWrong();
            return;
        }
        ControllerProfessorHome controllerProfessorHome = loader.getController();
        controllerProfessorHome.setTopMenuText(username);
        window.setScene(new Scene(viewFile, 600, 400));
        window.setResizable(false);
    }
    
    private void buildAdministratorHomeScene(String username) {
        Parent viewFile;
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/views/View_AdministratorHome.fxml"));
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(HomeController.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            notifyDevelopers(ioException);
            displaySomethingWentWrong();
            return;
        }
        ControllerAdministratorHome controllerAdministratorHome = loader.getController();
        controllerAdministratorHome.setTopMenuText(username);
        window.setScene(new Scene(viewFile, 600, 400));
        window.setResizable(false);
    }
}
