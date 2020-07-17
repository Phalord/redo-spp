package com.spp.model.dataaccess.idao;

import com.spp.model.domain.ProjectRequest;

public interface IProjectRequestDAO extends CRUD<ProjectRequest> {
    ProjectRequest getProjectRequestByStudentEnrollment(String studentEnrollment);
}
