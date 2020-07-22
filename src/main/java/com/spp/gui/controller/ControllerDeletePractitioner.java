package com.spp.gui.controller;

import static com.spp.gui.Dialog.displayCancelConfirmation;
import static com.spp.gui.Dialog.displayConnectionError;
import static com.spp.gui.Dialog.displayDeleteConfirmation;
import static com.spp.gui.Dialog.displaySomethingWentWrong;
import static com.spp.gui.Dialog.displaySuccessDisableDialog;
import static com.spp.utils.MailSender.notifyDevelopers;

import com.spp.model.dataaccess.dao.PractitionerDAO;
import com.spp.model.dataaccess.idao.IUserDAO;
import com.spp.model.domain.Practitioner;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ControllerDeletePractitioner implements Initializable {
    
    @FXML private Menu topMenu;
    @FXML private BorderPane borderPaneDeletePractitioner;
    @FXML private TableView<Practitioner> tableViewPractitioner;
    @FXML private TableColumn<Practitioner, String> userColumn;
    @FXML private TableColumn<Practitioner, String> passwordColumn;
    @FXML private TableColumn<Practitioner, String> nameColumn;
    @FXML private TableColumn<Practitioner, String> surnameColumn;
    @FXML private TableColumn<Practitioner, String> userTypeColumn;
    @FXML private TableColumn<Practitioner, String> shiftColumn;
    @FXML private TableColumn<Practitioner, String> groupIDColumn;
    @FXML private TableColumn<Practitioner, String> statusColumn;
    private ObservableList<Practitioner> listPractitioner;
    private final PractitionerDAO practitionerDAO;
    
    public ControllerDeletePractitioner() {
        this.practitionerDAO = new PractitionerDAO();
    }
    
    public final void setTopMenuText(String username) {
        topMenu.setText(username);
    }

    @Override
    public final void initialize(URL url, ResourceBundle rb) {
        listPractitioner = FXCollections.observableArrayList();
        practitionerDAO.getPractitionerInformation(listPractitioner);
        tableViewPractitioner.setItems(listPractitioner);
        linkColumnsWithAttributes();
    }
    
    @FXML
    private void deletePractitionerActionButton() {
        if (getValueFromCell() != null) {
            String username = getValueFromCell();
            if (displayDeleteConfirmation()){
                Practitioner practitioner = new Practitioner();
                practitioner.setUsername(username);
                IUserDAO<Practitioner> iUserDAO = new PractitionerDAO();
                if (iUserDAO.deleteUser(practitioner)) {
                    displaySuccessDisableDialog();
                    refreshTableView();
                } else {
                    displayConnectionError();
                }                 
            }
        } else {
            displaySelectionPractitionerDialog();
        }
    }
    
    @FXML
    private void cancel() {
        backScene();
    }

    @FXML
    private void logOut() {
        closeWindow();
        displayLogin();
    }

    private void closeWindow() {
        Stage stage1 = (Stage) borderPaneDeletePractitioner.getScene().getWindow();
        stage1.close();
    }

    private void displayLogin() {
        Stage window = (Stage) borderPaneDeletePractitioner.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/View_Login.fxml"));
        Parent viewFile;
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerDeletePractitioner.class.getName())
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

    private void backScene() {
        if(displayCancelConfirmation()) {
            Stage window = (Stage) borderPaneDeletePractitioner.getScene().getWindow();
            Parent viewFile;
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/views/View_CoordinatorHome.fxml"));
            try {
                viewFile = loader.load();
            } catch (IOException ioException) {
                Logger.getLogger(ControllerDeletePractitioner.class.getName())
                        .log(Level.SEVERE, ioException.getMessage(), ioException);
                return;
            }
            ControllerCoordinatorHome controllerCoordinatorHome = loader.getController();
            controllerCoordinatorHome.setTopMenuText(topMenu.getText());
            window.setScene(new Scene(viewFile, 600, 400));
        }
    }

    public final String getValueFromCell() {
        String username = null;
        if (tableViewPractitioner.getSelectionModel().getSelectedItem() != null) {
            TablePosition position = tableViewPractitioner.getSelectionModel().getSelectedCells().get(0);
            int row = position.getRow();
            Practitioner item = tableViewPractitioner.getItems().get(row);
            TableColumn column = userColumn;
            username = (String) column.getCellObservableValue(item).getValue();
        }
        return username;
    }
    
    private void linkColumnsWithAttributes() {
        userColumn.setCellValueFactory(new PropertyValueFactory<> ("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<> ("password"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surnames"));
        userTypeColumn.setCellValueFactory(new PropertyValueFactory<>("userType"));
        shiftColumn.setCellValueFactory(new PropertyValueFactory<>("shift"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("active"));
        groupIDColumn.setCellValueFactory(new PropertyValueFactory<>("groupID"));
    }
    
    private void refreshTableView() {
        listPractitioner.clear();
        listPractitioner = FXCollections.observableArrayList();
        practitionerDAO.getPractitionerInformation(listPractitioner);
        tableViewPractitioner.setItems(listPractitioner);
        linkColumnsWithAttributes();
    }
    
    private void displaySelectionPractitionerDialog() {
            Alert selectPractitionerAlertDialog = new Alert(Alert.AlertType.WARNING);
            selectPractitionerAlertDialog.setTitle("Aviso");
            selectPractitionerAlertDialog.setHeaderText("No se seleccionó ningún practicante");
            selectPractitionerAlertDialog.setContentText("Debe seleccionar un practicante para eliminar"); 
            selectPractitionerAlertDialog.showAndWait();
    }
}
