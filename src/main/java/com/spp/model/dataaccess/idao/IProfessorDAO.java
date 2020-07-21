package com.spp.model.dataaccess.idao;

import com.spp.model.domain.Professor;

import java.util.List;
import javafx.collections.ObservableList;

public interface IProfessorDAO extends IUserDAO<Professor> {
    List<Professor> getAllProfessors();
    void getProfessorInformation(ObservableList<Professor> listProfessor);
}
