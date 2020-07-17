package com.spp.model.dataaccess.idao;

import com.spp.model.domain.Project;

import java.util.List;

public interface IProjectDAO extends CRUD<Project> {
    Project getProjectByStudentEnrollment(String studentEnrollment);
    List<Project> getAvailableProjects();
}
