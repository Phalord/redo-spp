package com.spp.model.dataaccess.idao;

import com.spp.model.domain.ProjectResponsible;

public interface IProjectResponsibleDAO extends CRUD<ProjectResponsible> {

    ProjectResponsible getResponsibleByCompanyID(int id);

}
