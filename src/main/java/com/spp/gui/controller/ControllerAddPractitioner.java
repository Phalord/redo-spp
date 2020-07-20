package com.spp.gui.controller;

import static com.spp.gui.Dialog.displayCancelConfirmation;
import static com.spp.gui.Dialog.displayEmptyFields;
import static com.spp.gui.Dialog.displayRecordAlreadyExist;
import static com.spp.gui.Dialog.displayRecordConfirmation;
import static com.spp.gui.Dialog.displayRecordSuccessDialog;
import com.spp.model.dataaccess.dao.GroupDAO;
import static com.spp.utils.TextValidator.validatePractitionerEnrollment;
import com.spp.model.dataaccess.dao.PractitionerDAO;
import com.spp.model.dataaccess.idao.IUserDAO;
import com.spp.model.domain.Group;
import com.spp.model.domain.Practitioner;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.commons.lang3.RandomStringUtils;


public class ControllerAddPractitioner {
        
    @FXML private Menu topMenu;
    @FXML private BorderPane borderPaneAddPractitioner;
    @FXML private RadioButton radioButtonEvening;
    @FXML private RadioButton radioButtonMorning;
    @FXML private TextField usernameTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField surnamesTextField;
    @FXML private TableView<Practitioner> tableViewPractitioner;
    @FXML private TableColumn<Practitioner, String> userColumn;
    @FXML private TableColumn<Practitioner, String> passwordColumn;
    @FXML private TableColumn<Practitioner, String> nameColumn;
    @FXML private TableColumn<Practitioner, String> surnameColumn;
    @FXML private TableColumn<Practitioner, String> userTypeColumn;
    @FXML private TableColumn<Practitioner, String> shiftColumn;
    @FXML private TableColumn<Practitioner, String> groupIDColumn;
    @FXML private TableColumn<Practitioner, String> statusColumn;
    @FXML private ToggleGroup shiftGroup;
    @FXML private ComboBox<Group> groupIDComboBox;
    private ObservableList<Practitioner> observableListPractitioner;
    private final PractitionerDAO practitionerDAO;

    public ControllerAddPractitioner() {
        this.practitionerDAO = new PractitionerDAO();
    }
    
    public final void setTopMenuText(String username) {
        topMenu.setText(username);
    }

    public final void initialize(List<Group> availableGroups) {
        observableListPractitioner = FXCollections.observableArrayList();
        practitionerDAO.getPractitionerInformation(observableListPractitioner);
        ObservableList<Group> availableGroupsOL = FXCollections.observableArrayList(availableGroups);
        tableViewPractitioner.setItems(observableListPractitioner);
        groupIDComboBox.getItems().setAll(availableGroupsOL);
        linkColumnsWithAttributes();
        validateTextFields();
    }
    
    @FXML
    private void registerPractitionerActionButton() {   
        if (!validateEmpty()) {
            String username = this.usernameTextField.getText();
            String name = this.nameTextField.getText();
            String surnames = this.surnamesTextField.getText();
            String radioButton = this.radioButtonEvening.isSelected()?"Matutino":"Vespertino";
            int groupID = groupIDComboBox.getValue().getGroupID();
            Practitioner practitioner = new Practitioner();
            if (validatePractitionerEnrollment(username)) {
                practitioner.setUsername(username);
                practitioner.setName(name);
                practitioner.setSurnames(surnames);
                practitioner.setShift(radioButton);
                practitioner.setGroupID(groupID);
                practitioner.setPassword(generatePassword());
                practitioner.setUserType("Practitioner");
                practitioner.setActive(true);  
                if (displayRecordConfirmation()) {
                    IUserDAO<Practitioner> iUserDAO = new PractitionerDAO();
                    if (iUserDAO.addUser(practitioner)) {
                        displayUsernameDialog(username);
                        displayRecordSuccessDialog();
                        refreshTableView();
                        cleanTextField();
                    } else {
                        displayRecordAlreadyExist();
                    }   
                }                
            } else {
                displayNotValidEnrollment();
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
        groupIDColumn.setCellValueFactory(new PropertyValueFactory<>("groupID"));
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
        groupIDComboBox.setValue(null);
    }
    
    private void refreshTableView() {
        observableListPractitioner.clear();
        observableListPractitioner = FXCollections.observableArrayList();
        practitionerDAO.getPractitionerInformation(observableListPractitioner);
        tableViewPractitioner.setItems(observableListPractitioner);
        linkColumnsWithAttributes();
    }
    
    private boolean validateEmpty() {
        return (usernameTextField.getText().isEmpty() ||
                nameTextField.getText().isEmpty() || 
                surnamesTextField.getText().isEmpty() ||
                groupIDComboBox.getValue() == null ||
                shiftGroup.getSelectedToggle() == null);
    }
    
    private void displayUsernameDialog(String username) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Nombre de usuario");
        alert.setContentText("El username del Practicante registrado es: "+username);
        alert.showAndWait();   
    }
    
    private void displayNotValidEnrollment() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Matricula invÃ¡lida");
        alert.setContentText("Matricula invÃ¡lida. La primera letra debe ser una s minÃºscula seguida de 8 dÃ­gitos numÃ©ricos");
        alert.showAndWait(); 
    }
    
    public String generatePassword() {
        return(RandomStringUtils.randomAlphanumeric(10));
    }
}
