package com.spp.model.dataaccess.idao;

import com.spp.model.domain.Group;

import java.util.List;

public interface IGroupDAO extends CRUD<Group> {
    List<Group> getAvailableGroups();
    Group getProfessorGroup(String username);
}
