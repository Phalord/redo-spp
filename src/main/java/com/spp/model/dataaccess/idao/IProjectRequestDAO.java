package com.spp.model.dataaccess.idao;

import com.spp.model.domain.ProjectRequest;

import java.util.List;

public interface IProjectRequestDAO extends CRUD<ProjectRequest> {
    ProjectRequest getProjectRequestByStudentEnrollment(String studentEnrollment);
    List<ProjectRequest> getPendingRequests();
}
