package com.spp.model.dataaccess.idao;

import com.spp.model.domain.Project;

public interface IProjectDAO extends CRUD<Project> {
    Project getProjectByStudentEnrollment(String studentEnrollment);
}
