package com.spp.model.dataaccess.idao;

import com.spp.model.domain.RelatedCompany;

public interface IRelatedCompanyDAO extends CRUD<RelatedCompany> {
    int getLastRelatedID();
}
