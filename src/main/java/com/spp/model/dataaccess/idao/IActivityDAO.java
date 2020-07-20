package com.spp.model.dataaccess.idao;

import com.spp.model.domain.Activity;
import com.spp.model.domain.PartialReport;

import java.sql.Timestamp;
import java.util.List;

public interface IActivityDAO extends CRUD<Activity> {
    List<Activity> getPractitionerActivities(String studentEnrollment);
    List<Activity> getOpenPractitionerActivities(String studentEnrollment, Timestamp actualTime);
    boolean reportActivity(Activity activity, PartialReport reportID);
}
