package com.spp.gui.controller;

import static com.spp.gui.Dialog.displayCancelConfirmation;
import static com.spp.gui.Dialog.displayConnectionError;
import static com.spp.gui.Dialog.displayDeleteConfirmation;
import static com.spp.gui.Dialog.displaySomethingWentWrong;
import static com.spp.gui.Dialog.displaySuccessDisableDialog;
import com.spp.model.dataaccess.dao.ProfessorDAO;
import com.spp.model.dataaccess.idao.IUserDAO;
import com.spp.model.domain.Professor;
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

public class ControllerDeleteProfessor implements Initializable {
    
    @FXML private Menu topMenu;
    @FXML private BorderPane borderPaneDeleteProfessor;
    @FXML private TableView<Professor> professorTableView;
    @FXML private TableColumn<Professor, String> userColumn;
    @FXML private TableColumn<Professor, String> passwordColumn;
    @FXML private TableColumn<Professor, String> nameColumn;
    @FXML private TableColumn<Professor, String> surnameColumn;
    @FXML private TableColumn<Professor, String> groupNRCColumn;
    @FXML private TableColumn<Professor, String> statusColumn;
    private final ProfessorDAO professorDAO;
    private ObservableList<Professor> observableListProfessor;
    
    public ControllerDeleteProfessor() {
        this.professorDAO = new ProfessorDAO();
    }
    
    public final void setTopMenuText(String username) {
        topMenu.setText(username);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        observableListProfessor = FXCollections.observableArrayList();
        professorDAO.getProfessorInformation(observableListProfessor);
        professorTableView.setItems(observableListProfessor);
        linkColumnsWithAttributes();
    }
    
    @FXML
    private void deletePractitionerActionButton() {
        if (getValueFromCell() != null) {
            String username = getValueFromCell();
            if (displayDeleteConfirmation()) {
                Professor professor = new Professor();
                professor.setUsername(username);
                IUserDAO<Professor> iUserDAO = new ProfessorDAO();
                if (iUserDAO.deleteUser(professor)) {
                    displaySuccessDisableDialog();
                    refreshTableView();
                } else {
                    displayConnectionError();
                }
            }
        } else {
            displaySelectionProfessorDialog();
        }
    }
    
    @FXML
    private void cancel() {
        if (displayCancelConfirmation()) {
            backScene();        
        }
    }

    @FXML
    private void logOut() {
        closeWindow();
        displayLogin();
    }

    private void closeWindow() {
        Stage stage1 = (Stage) borderPaneDeleteProfessor.getScene().getWindow();
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
        Stage window = (Stage) borderPaneDeleteProfessor.getScene().getWindow();
        Parent viewFile;
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/views/View_AdministratorHome.fxml"));
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerDeleteProfessor.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            return;
        }
        ControllerAdministratorHome controllerAdministratorHome = loader.getController();
        controllerAdministratorHome.setTopMenuText(topMenu.getText());
        window.setScene(new Scene(viewFile, 600, 400));
    }
        
    public String getValueFromCell() {
        String username = null;
        if (professorTableView.getSelectionModel().getSelectedItem() != null) {
            TablePosition position = professorTableView.getSelectionModel()
                    .getSelectedCells().get(0);
            int row = position.getRow();
            Professor item = professorTableView.getItems().get(row);
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
        groupNRCColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("active"));
    }
    
    private void refreshTableView() {
        observableListProfessor.clear();
        observableListProfessor = FXCollections.observableArrayList();
        professorDAO.getProfessorInformation(observableListProfessor);
        professorTableView.setItems(observableListProfessor);
        linkColumnsWithAttributes();
    }
    
    private void displaySelectionProfessorDialog() {
        Alert selectPractitionerAlertDialog = new Alert(Alert.AlertType.WARNING);
        selectPractitionerAlertDialog.setTitle("Aviso");
        selectPractitionerAlertDialog.setHeaderText("No se selecccionó ningún profesor");
        selectPractitionerAlertDialog.setContentText("Debe seleccionar un profesor para eliminar"); 
        selectPractitionerAlertDialog.showAndWait();
    }   
}
