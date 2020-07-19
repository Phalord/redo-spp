package com.spp.gui.controller;

import com.spp.model.domain.Activity;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class ControllerSetActualActivityCompletionHours extends Stage {
    @FXML private ComboBox<Activity> activityComboBox;
    @FXML private TextField actualCompletionHours;


    public final Activity showAndReturn(List<Activity> openActivities) {
        return null;
    }
}
