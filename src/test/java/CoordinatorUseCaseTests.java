import com.spp.model.dataaccess.dao.PractitionerDAO;
import com.spp.model.dataaccess.dao.ProjectResponsibleDAO;
import com.spp.model.dataaccess.dao.RelatedCompanyDAO;
import com.spp.model.dataaccess.idao.CRUD;
import com.spp.model.dataaccess.idao.IUserDAO;
import com.spp.model.domain.Practitioner;
import com.spp.model.domain.ProjectResponsible;
import com.spp.model.domain.RelatedCompany;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CoordinatorUseCaseTests {

    @Test
    public void RegisterPractitioner() {
        Practitioner practitioner = new Practitioner();
        practitioner.setUsername("s11223344");
        practitioner.setName("Pablo");
        practitioner.setSurnames("Rosario Muñoz");
        practitioner.setShift("Afternoon");
        practitioner.setPassword("contra123seña");
        practitioner.setUserType("Practitioner");
        practitioner.setActive(true);
        IUserDAO<Practitioner> iUserDAO = new PractitionerDAO();
        if (iUserDAO.addUser(practitioner)) {
            assertEquals(practitioner.getUsername(),
                    iUserDAO.getUserByUsername(practitioner.getUsername()).getUsername());
        } else {
            fail("Error de conexión");
        }
    }

    @Test
    public void RegisterRelatedCompany() {
        RelatedCompany relatedCompany = new RelatedCompany();
        relatedCompany.setName("Terraza Studios");
        relatedCompany.setAddress("Nevado de Toluca 17");
        relatedCompany.setState("Veracruz");
        relatedCompany.setCity("Xalapa");
        relatedCompany.setSector("Público");
        relatedCompany.setEmail("terrazamr@gmail.com");
        relatedCompany.setPhone("2291762528");
        CRUD<RelatedCompany> companyCRUD = new RelatedCompanyDAO();
        if (companyCRUD.addElement(relatedCompany)) {
            assertEquals(relatedCompany.getName(),
                    companyCRUD.getByID(1).getName());
        } else {
            fail("Error de Conexión o Compañía ya agregada");
        }
    }

    @Test
    public void RegisterProjectResponsible() {
        ProjectResponsible projectResponsible = new ProjectResponsible();
        projectResponsible.setName("Alejandro");
        projectResponsible.setSurname("Sandoval Bravo");
        projectResponsible.setPhone("2291762528");
        projectResponsible.setRelatedCompanyID(1);
        projectResponsible.setEmail("a.sandovalbr@gmail.com");
        CRUD<ProjectResponsible> projectResponsibleCRUD = new ProjectResponsibleDAO();
        if (projectResponsibleCRUD.addElement(projectResponsible)) {
            assertEquals(projectResponsible.getEmail(),
                    projectResponsibleCRUD.getByID(1).getEmail());
        } else {
            fail("Error de Conexión o Responsable ya agregado");
        }
    }


}
