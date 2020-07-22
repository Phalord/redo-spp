package com.spp.gui.controller;

import static com.spp.gui.Dialog.displayCancelConfirmation;
import static com.spp.gui.Dialog.displayEmptyFields;
import static com.spp.gui.Dialog.displayRecordAlreadyExist;
import static com.spp.gui.Dialog.displayRecordConfirmation;
import static com.spp.gui.Dialog.displayRecordSuccessDialog;
import static com.spp.gui.Dialog.displaySomethingWentWrong;
import com.spp.model.dataaccess.dao.GroupDAO;
import com.spp.model.dataaccess.dao.ProfessorDAO;
import com.spp.model.dataaccess.idao.IGroupDAO;
import com.spp.model.dataaccess.idao.IUserDAO;
import com.spp.model.domain.Group;
import com.spp.model.domain.Professor;
import static com.spp.utils.TextValidator.validateProfessorEmployeeNumber;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ControllerAddProfessor {
    
    @FXML private Menu topMenu;
    @FXML private BorderPane borderPaneAddProfessor;
    @FXML private TextField usernameTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField surnamesTextField;
    @FXML private PasswordField professorPasswordField;
    @FXML private ComboBox<Group> groupIDComboBox;
    @FXML private TableView<Professor> professorTableView;
    @FXML private TableColumn<Professor, String> userColumn;
    @FXML private TableColumn<Professor, String> passwordColumn;
    @FXML private TableColumn<Professor, String> nameColumn;
    @FXML private TableColumn<Professor, String> surnameColumn;
    @FXML private TableColumn<Professor, String> groupNRCColumn;
    @FXML private TableColumn<Professor, String> statusColumn;
    private ObservableList<Professor> observableListProfessor;
    private final ProfessorDAO professorDAO;
    
    public ControllerAddProfessor() {
        this.professorDAO = new ProfessorDAO();
    }
    
    public final void setTopMenuText(String username) {
        topMenu.setText(username);
    }
    
    public void initialize(List<Group> availableGroups) {
        observableListProfessor = FXCollections.observableArrayList();
        professorDAO.getProfessorInformation(observableListProfessor);
        ObservableList<Group> availableGroupsOL = FXCollections.observableArrayList(availableGroups);
        professorTableView.setItems(observableListProfessor);
        groupIDComboBox.getItems().setAll(availableGroupsOL);
        linkColumnsWithAttributes();
        validateTextFields();
    }
    
    @FXML
    private void registerProfessorActionButton() {
        if (!areFieldsEmpty()) {
            String username = this.usernameTextField.getText();
            String name = this.nameTextField.getText();
            String surnames = this.surnamesTextField.getText();
            String password = this.professorPasswordField.getText();
            Group group = groupIDComboBox.getValue();
            Professor professor = new Professor();
            if (validateProfessorEmployeeNumber(username)) {
                professor.setUsername(username);
                professor.setName(name);
                professor.setSurnames(surnames);
                professor.setPassword(password);
                group.setLecturer(professor);
                professor.setUserType("Professor");
                professor.setActive(true);
                if (displayRecordConfirmation()) {
                    IUserDAO<Professor> iUserDAO = new ProfessorDAO();
                    if (iUserDAO.addUser(professor)) {
                        IGroupDAO iGroupDAO = new GroupDAO();
                        if (iGroupDAO.assignLecturer(group)) {
                            displayUsernameDialog(username, password);
                            displayRecordSuccessDialog();
                            refreshTableView();
                            backScene();
                        } else {
                            displaySomethingWentWrong();
                        }
                    } else {
                        displayRecordAlreadyExist();
                    }
                }
            } else {
                displayNotValidPersonalNumber();
            }
        } else {
            displayEmptyFields();
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
        Stage stage1 = (Stage) borderPaneAddProfessor.getScene().getWindow();
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
        Stage window = (Stage) borderPaneAddProfessor.getScene().getWindow();
        Parent viewFile;
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/views/View_AdministratorHome.fxml"));
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerAddProfessor.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            return;
        }
        ControllerAdministratorHome controllerAdministratorHome = loader.getController();
        controllerAdministratorHome.setTopMenuText(topMenu.getText());
        window.setScene(new Scene(viewFile, 600, 400));
    }
    
    private void linkColumnsWithAttributes() {
        userColumn.setCellValueFactory(new PropertyValueFactory<> ("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<> ("password"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surnames"));
        groupNRCColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("active"));
    }
    
    public void validateTextFields() {
        nameTextField.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable,
            String oldValue, String newValue) {
                if (!newValue.matches("\\sa-zA-Z*")) {
                    nameTextField.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
                }
            }
        });
        surnamesTextField.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable,
            String oldValue, String newValue) {
                if (!newValue.matches("\\sa-zA-Z*")) {
                    surnamesTextField.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
                }
            }
        });
    }
    
    private void refreshTableView() {
        observableListProfessor.clear();
        observableListProfessor = FXCollections.observableArrayList();
        professorDAO.getProfessorInformation(observableListProfessor);
        professorTableView.setItems(observableListProfessor);
        linkColumnsWithAttributes();
    }
    
    private boolean areFieldsEmpty() {
        return (usernameTextField.getText().isEmpty() ||
                nameTextField.getText().isEmpty() || 
                surnamesTextField.getText().isEmpty() ||
                professorPasswordField.getText().isEmpty()) ||
                groupIDComboBox.getValue() == null;
    }

    private void displayUsernameDialog(String username, String password) {
        String title = String.format("Nombre de usuario: %s", username);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(title);
        //alert.setContentText(String.format("La cuenta del Profesor registrado es: %s ", username));
        alert.setContentText(String.format("La contraseña del Profesor registrado es: %s", password));
        alert.showAndWait();   
    }
    
    private void displayNotValidPersonalNumber() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("No. de personal invÃ¡lido");
        alert.setContentText("No. invÃ¡lido. La primera letra debe ser una p minÃºscula seguida de 8 dÃ­gitos numÃ©ricos");
        alert.showAndWait(); 
    }
}
