package com.spp.gui.controller;

import static com.spp.gui.Dialog.displayEmptyFields;
import static com.spp.gui.Dialog.displayRecordConfirmation;
import static com.spp.gui.Dialog.displayRecordSuccessDialog;
import static com.spp.gui.Dialog.displaySomethingWentWrong;
import com.spp.model.dataaccess.dao.SelfAppraisalDAO;
import com.spp.model.dataaccess.idao.ISelfAppraisalDAO;
import com.spp.model.domain.Practitioner;
import com.spp.model.domain.SelfAppraisal;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControllerSelfAppraisal {

    @FXML private ToggleGroup rateGroupFirst;
    @FXML private ToggleGroup rateGroupSecond;
    @FXML private ToggleGroup rateGroupThird;
    @FXML private ToggleGroup rateGroupFourth;
    @FXML private ToggleGroup rateGroupFifth;
    @FXML private AnchorPane container;
    private String studentEnrollment;
    
    @FXML
    private void generateSelfAppraisal() {
        if(!areRadioButtonsEmpty()){
            RadioButton selectedToggleFirstValue = (RadioButton) rateGroupFirst.getSelectedToggle();
            int firstValueParse = Integer.parseInt(selectedToggleFirstValue.getText());
            byte firstSentenceGrade = (byte) firstValueParse;
            RadioButton selectedToggleSecondValue = (RadioButton) rateGroupSecond.getSelectedToggle();
            int secondValueParse = Integer.parseInt(selectedToggleSecondValue.getText());
            byte secondSentenceGrade = (byte) secondValueParse;
            RadioButton selectedToggleThirdValue = (RadioButton) rateGroupThird.getSelectedToggle();
            int thirdValueParse = Integer.parseInt(selectedToggleThirdValue.getText());
            byte thirdSentenceGrade = (byte) thirdValueParse;
            RadioButton selectedToggleFourthValue = (RadioButton) rateGroupFourth.getSelectedToggle();
            int fourthValueParse = Integer.parseInt(selectedToggleFourthValue.getText());
            byte fourthSentenceGrade = (byte) fourthValueParse;
            RadioButton selectedToggleFifthValue = (RadioButton) rateGroupFifth.getSelectedToggle();
            int fifthValueParse = Integer.parseInt(selectedToggleFifthValue.getText());
            byte fifthSentenceGrade = (byte) fifthValueParse;
            
            SelfAppraisal selfAppraisal = new SelfAppraisal();
            selfAppraisal.setFirstSentence(firstSentenceGrade);
            selfAppraisal.setSecondSentence(secondSentenceGrade);
            selfAppraisal.setThirdSentence(thirdSentenceGrade);
            selfAppraisal.setFourthSentence(fourthSentenceGrade);
            selfAppraisal.setFifthSentence(fifthSentenceGrade);
            Practitioner practitioner = new Practitioner();
            practitioner.setUsername(studentEnrollment);
            selfAppraisal.setAuthor(practitioner);
            
            if(displayRecordConfirmation()){
                ISelfAppraisalDAO selfAppraisalDAO = new SelfAppraisalDAO();
                if(selfAppraisalDAO.addElement(selfAppraisal)){
                    displayRecordSuccessDialog();
                    cleanRadioButtons();
                } else {
                    displaySomethingWentWrong();
                }
            }
        } else {
            displayEmptyFields();
        }
    }

    @FXML
    private void returnGenerateDocumentation() {
        Stage window = (Stage) container.getScene().getWindow();
        Parent viewFile;
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/views/View_GenerateDocumentation.fxml"));
            viewFile = loader.load();
            ControllerGenerateDocumentation controllerGenerateDocumentation = loader.getController();
            controllerGenerateDocumentation.setTopMenuText(studentEnrollment);
            window.setScene(new Scene(viewFile));
        } catch (IOException exception) {
            Logger.getLogger(ControllerSelfAppraisal.class.getName())
                    .log(Level.SEVERE,exception.getMessage(), exception);
        }
    }
    
    private boolean areRadioButtonsEmpty(){
        return (rateGroupFirst.getSelectedToggle() == null ||
                rateGroupSecond.getSelectedToggle() == null ||
                rateGroupThird.getSelectedToggle() == null ||
                rateGroupFourth.getSelectedToggle() == null ||
                rateGroupFifth.getSelectedToggle() == null);
    }
    
    private void cleanRadioButtons(){
        RadioButton selectedToogleFirstValue = (RadioButton) rateGroupFirst.getSelectedToggle();
        selectedToogleFirstValue.setSelected(false);
        RadioButton selectedToogleSecondValue = (RadioButton) rateGroupSecond.getSelectedToggle();
        selectedToogleSecondValue.setSelected(false);
        RadioButton selectedToogleThirdValue = (RadioButton) rateGroupThird.getSelectedToggle();
        selectedToogleThirdValue.setSelected(false);
        RadioButton selectedToogleFourthValue = (RadioButton) rateGroupFourth.getSelectedToggle();
        selectedToogleFourthValue.setSelected(false);
        RadioButton selectedToogleFifthValue = (RadioButton) rateGroupFifth.getSelectedToggle();
        selectedToogleFifthValue.setSelected(false);
    }
    
    public final void setPractitionerEnrollment(String enrollment){
        studentEnrollment = enrollment;
    }
}
