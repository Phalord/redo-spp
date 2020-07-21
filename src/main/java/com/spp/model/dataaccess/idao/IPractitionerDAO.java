package com.spp.model.dataaccess.idao;

import com.spp.model.domain.Group;
import com.spp.model.domain.Practitioner;
import javafx.collections.ObservableList;
import java.util.List;

public interface IPractitionerDAO extends IUserDAO<Practitioner> {
    void getPractitionerInformation(ObservableList<Practitioner> listPractitioner);
    List<Practitioner> getGroupStudents(Group group);
}
