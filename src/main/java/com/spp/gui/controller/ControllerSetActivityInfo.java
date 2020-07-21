package com.spp.gui.controller;

import com.spp.model.domain.Activity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

import java.util.List;
import java.util.function.UnaryOperator;

import static com.spp.gui.Dialog.displayTooManyCharacters;

public class ControllerSetActivityInfo extends Stage {
    @FXML private ComboBox<Activity> activityComboBox;
    @FXML private TextField actualCompletionHours;
    @FXML private Button addActivity;
    Activity activity = null;

    public final Activity showAndReturn(List<Activity> openActivities) {
        initializeActivityComboBox(openActivities);
        initializeActualCompletionHoursTextField();
        addActivity.setOnAction((ActionEvent event) -> {
            activity = activityComboBox.getValue();
            activity.setActualCompletionHours((short) Integer
                    .parseInt(actualCompletionHours.getText()));
            super.close();
        });
        super.showAndWait();
        return activity;
    }

    @FXML
    private void cancel() {
        super.close();
    }

    private void initializeActivityComboBox(List<Activity> openActivities) {
        ObservableList<Activity> openActivitiesOL =
                FXCollections.observableArrayList(openActivities);
        activityComboBox.getItems().setAll(openActivitiesOL);
    }

    private void initializeActualCompletionHoursTextField() {
        UnaryOperator<TextFormatter.Change> rejectChange = change -> {
            if (change.isContentChange()) {
                if (change.getControlNewText().length() > 4) {
                    displayTooManyCharacters((short) 4);
                    return null;
                }
                if (!change.getControlNewText().matches("([1-9][0-9]*)?")) {
                    actualCompletionHours.setText("");
                    return null;
                }
            }
            return change;
        };
        actualCompletionHours
                .setTextFormatter(new TextFormatter<TextFormatter.Change>(rejectChange));
    }
}
