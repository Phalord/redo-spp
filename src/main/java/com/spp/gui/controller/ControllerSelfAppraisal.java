package com.spp.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class ControllerSelfAppraisal {

    @FXML private ToggleGroup rateGroupFirst;
    @FXML private ToggleGroup rateGroupSecond;
    @FXML private ToggleGroup rateGroupThird;
    @FXML private ToggleGroup rateGroupFourth;
    @FXML private ToggleGroup rateGroupFifth;
    @FXML private RadioButton radioButtonFirst0;
    @FXML private RadioButton radioButtonFirst1;
    @FXML private RadioButton radioButtonFirst2;
    @FXML private RadioButton radioButtonFirst3;
    @FXML private RadioButton radioButtonFirst4;
    @FXML private RadioButton radioButtonFirst5;
    @FXML private RadioButton radioButtonSecond0;
    @FXML private RadioButton radioButtonSecond1;
    @FXML private RadioButton radioButtonSecond2;
    @FXML private RadioButton radioButtonSecond3;
    @FXML private RadioButton radioButtonSecond4;
    @FXML private RadioButton radioButtonSecond5;
    @FXML private RadioButton radioButtonThird0;
    @FXML private RadioButton radioButtonThird1;
    @FXML private RadioButton radioButtonThird2;
    @FXML private RadioButton radioButtonThird3;
    @FXML private RadioButton radioButtonThird4;
    @FXML private RadioButton radioButtonThird5;
    @FXML private RadioButton radioButtonFourth0;
    @FXML private RadioButton radioButtonFourth1;
    @FXML private RadioButton radioButtonFourth2;
    @FXML private RadioButton radioButtonFourth3;
    @FXML private RadioButton radioButtonFourth4;
    @FXML private RadioButton radioButtonFourth5;
    @FXML private RadioButton radioButtonFifth0;
    @FXML private RadioButton radioButtonFifth1;
    @FXML private RadioButton radioButtonFifth2;
    @FXML private RadioButton radioButtonFifth3;
    @FXML private RadioButton radioButtonFifth4;
    @FXML private RadioButton radioButtonFifth5;

    @FXML
    private void generateSelfAppraisal() {
        if(!areRadioButtonsEmpty()){
            RadioButton selectedToogleFirstValue = (RadioButton) rateGroupFirst.getSelectedToggle();
            int firstValueParse = Integer.parseInt(selectedToogleFirstValue.getText());
            byte firstSentenceGrade = (byte) firstValueParse;
            RadioButton selectedToogleSecondValue = (RadioButton) rateGroupSecond.getSelectedToggle();
            int secondValueParse = Integer.parseInt(selectedToogleSecondValue.getText());
            byte secondSentenceGrade = (byte) secondValueParse;
            RadioButton selectedToogleThirdValue = (RadioButton) rateGroupThird.getSelectedToggle();
            int thirdValueParse = Integer.parseInt(selectedToogleThirdValue.getText());
            byte thirdSentenceGrade = (byte) thirdValueParse;
            RadioButton selectedToogleFourthValue = (RadioButton) rateGroupFourth.getSelectedToggle();
            int fourthValueParse = Integer.parseInt(selectedToogleFourthValue.getText());
            byte fourthSentenceGrade = (byte) fourthValueParse;
            RadioButton selectedToogleFifthValue = (RadioButton) rateGroupFifth.getSelectedToggle();
            int fifthValueParse = Integer.parseInt(selectedToogleFifthValue.getText());
            byte fifthSentenceGrade = (byte) fifthValueParse;
        }
    }

    @FXML
    private void returnGenerateDocumentation() {
    }
    
    private boolean areRadioButtonsEmpty(){
        return (rateGroupFirst.getSelectedToggle() == null || rateGroupSecond.getSelectedToggle() == null || 
                rateGroupThird.getSelectedToggle() == null || rateGroupFourth.getSelectedToggle() == null ||
                rateGroupFifth.getSelectedToggle() == null);
    }
}
