package com.spp.model.dataaccess.idao;

import com.spp.model.domain.Group;
import com.spp.model.domain.Practitioner;
import javafx.collections.ObservableList;

public interface IPractitionerDAO extends IUserDAO<Practitioner> {
    void getPractitionerInformation(ObservableList<Practitioner> listPractitioner);
import java.util.List;

public interface IPractitionerDAO extends IUserDAO<Practitioner> {

    List<Practitioner> getGroupStudents(Group group);

}
