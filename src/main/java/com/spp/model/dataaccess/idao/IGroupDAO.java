package com.spp.model.dataaccess.idao;

import com.spp.model.domain.Group;
import java.util.List;

public interface IGroupDAO extends CRUD<Group> {
    List<Group> getAvailableGroups();
    boolean assignLecturer(Group group);
    byte getAvailableQuota(int groupID);
    List<Group> getProfessorAvailableGroups();
    Group getProfessorGroup(String username);
    boolean addPractitioner(Group group);
}
