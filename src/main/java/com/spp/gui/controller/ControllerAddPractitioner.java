package com.spp.gui.controller;

import static com.spp.gui.Dialog.displayCancelConfirmation;
import static com.spp.gui.Dialog.displayEmptyFields;
import static com.spp.gui.Dialog.displayRecordAlreadyExist;
import static com.spp.gui.Dialog.displayRecordConfirmation;
import static com.spp.gui.Dialog.displaySuccessDialog;
import static com.spp.utils.TextValidator.validatePractitionerEnrollment;
import com.spp.model.dataaccess.dao.PractitionerDAO;
import com.spp.model.dataaccess.idao.IUserDAO;
import com.spp.model.domain.Practitioner;
import com.spp.utils.MySQLConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.commons.lang3.RandomStringUtils;


public class ControllerAddPractitioner implements Initializable {
    @FXML private BorderPane borderPaneAddPractitioner;
    @FXML Button registerButton;
    @FXML RadioButton radioButtonEvening;
    @FXML RadioButton radioButtonMorning;
    @FXML TextField usernameTextField;
    @FXML TextField nameTextField;
    @FXML TextField surnamesTextField;
    @FXML ToggleGroup shiftGroup;
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

    public ControllerAddPractitioner() {
        this.practitionerDAO = new PractitionerDAO();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listPractitioner = FXCollections.observableArrayList();
        MySQLConnection mySQLConnection = new MySQLConnection();
        try {
            practitionerDAO.fillPractitionerTable(mySQLConnection.getConnection(),listPractitioner);
        } catch (SQLException sqlException) {
            Logger.getLogger(ControllerAddPractitioner.class.getName()).log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        tableViewPractitioner.setItems(listPractitioner);
        linkColumnsWithAttributes();
        validateTextFields();
    }
    
    @FXML
    private void registerPractitionerActionButton() {   
        if (!validateEmpty()) {
            String username = this.usernameTextField.getText();
            String name = this.nameTextField.getText();
            String surnames = this.surnamesTextField.getText();
            String radioButton = this.radioButtonEvening.isSelected()?"Morning":"Afternoon";
            Practitioner practitioner = new Practitioner();
            if (validatePractitionerEnrollment(username)) {
                practitioner.setUsername(username);
                practitioner.setName(name);
                practitioner.setSurnames(surnames);
                practitioner.setShift(radioButton);
                practitioner.setPassword(generatePassword());
                practitioner.setUserType("Practitioner");
                practitioner.setActive(true);  
                if (displayRecordConfirmation()) {
                    IUserDAO<Practitioner> iUserDAO = new PractitionerDAO();
                    if (iUserDAO.addUser(practitioner)) {
                        listPractitioner.add(practitioner);
                        displayUsernameDialog(username);
                        displaySuccessDialog();
                        cleanTextField();
                    } else {
                        displayRecordAlreadyExist();
                    }   
                }                
            } else {
                displayNotValidEnrrollment();
            }
        } else {
            displayEmptyFields();
        }
    }
    
    @FXML
    private void cancelActionButton() {
        if(displayCancelConfirmation()) {
            Stage window = (Stage) borderPaneAddPractitioner.getScene().getWindow();
            Parent viewFile;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/View_CoordinatorHome.fxml"));
                viewFile = loader.load();
                ControllerCoordinatorHome coordinatorHomeController = loader.getController();
                coordinatorHomeController = loader.getController();
                window.setScene(new Scene(viewFile));
            } catch (IOException ioException) {
                Logger.getLogger(ControllerAddPractitioner.class.getName())
                        .log(Level.SEVERE,ioException.getMessage(), ioException);
            }
        }
    }
    
    private void linkColumnsWithAttributes() {
        userColumn.setCellValueFactory(new PropertyValueFactory<> ("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<> ("password"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surnames"));
        userTypeColumn.setCellValueFactory(new PropertyValueFactory<>("userType"));
        shiftColumn.setCellValueFactory(new PropertyValueFactory<>("shift"));
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
    
    private void cleanTextField() {
        usernameTextField.setText("");
        nameTextField.setText("");
        surnamesTextField.setText("");
        radioButtonEvening.setSelected(false);
        radioButtonMorning.setSelected(false);
    }
    
    private boolean validateEmpty() {
        return (usernameTextField.getText().isEmpty() ||
                nameTextField.getText().isEmpty() || 
                surnamesTextField.getText().isEmpty() ||
                shiftGroup.getSelectedToggle() == null);
    }
    
    private void displayUsernameDialog(String username) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Nombre de usuario");
        alert.setContentText("El username del Practicante registrado es: "+username);
        alert.showAndWait();   
    }
    
    private void displayNotValidEnrrollment() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Matricula inválida");
        alert.setContentText("Matricula inválida. La primera letra debe ser una s minúcula seguida de 8 digitos numéricos");
        alert.showAndWait(); 
    }
    
    public String generatePassword() {
        return(RandomStringUtils.randomAlphanumeric(10));
    }
}
