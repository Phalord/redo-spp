package com.spp.model.dataaccess.idao;

import com.spp.model.domain.Project;
import com.spp.model.domain.ProjectAssignment;

import java.util.List;

public interface IProjectDAO extends CRUD<Project> {
    Project getProjectByStudentEnrollment(String studentEnrollment);
    List<Project> getProjectRequestedProjects(int projectRequestID);
    List<Project> getAvailableProjects();
    boolean assignProject(ProjectAssignment projectAssignment);
}
