import com.spp.model.dataaccess.dao.PractitionerDAO;
import com.spp.model.dataaccess.dao.ProjectDAO;
import com.spp.model.dataaccess.dao.ProjectResponsibleDAO;
import com.spp.model.dataaccess.dao.RelatedCompanyDAO;
import com.spp.model.dataaccess.idao.CRUD;
import com.spp.model.dataaccess.idao.IUserDAO;
import com.spp.model.domain.Practitioner;
import com.spp.model.domain.Project;
import com.spp.model.domain.ProjectResponsible;
import com.spp.model.domain.RelatedCompany;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CoordinatorUseCaseTests {

    @Test
    public void registerPractitioner() {
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
    public void deletePractitioner() {
        Practitioner practitioner = new Practitioner();
        practitioner.setUsername("s18012155");
        IUserDAO<Practitioner> iUserDAO = new PractitionerDAO();
        if (iUserDAO.deleteUser(practitioner)) {
            assertEquals(practitioner.getUsername(),
                    iUserDAO.getUserByUsername(practitioner.getUsername()).getUsername());
        } else {
            fail("Error de conexión");
        }        
    }

    @Test
    public void registerRelatedCompany() {
        RelatedCompany relatedCompany = new RelatedCompany();
        relatedCompany.setName("Mi Cyber");
        relatedCompany.setAddress("Miami 15");
        relatedCompany.setState("Veracruz");
        relatedCompany.setCity("Xalapa");
        relatedCompany.setSector("Privado");
        relatedCompany.setEmail("comunicaciones@micyber.mx");
        relatedCompany.setPhone("228 102 3357");
        CRUD<RelatedCompany> companyCRUD = new RelatedCompanyDAO();
        if (companyCRUD.addElement(relatedCompany)) {
            assertEquals(relatedCompany.getName(),
                    companyCRUD.getByID(3).getName());
        } else {
            fail("Error de Conexión o Compañía ya agregada");
        }
    }

    @Test
    public void registerProjectResponsible() {
        ProjectResponsible projectResponsible = new ProjectResponsible();
        projectResponsible.setName("Gustavo Alfonso");
        projectResponsible.setSurname("Guevara Marcial");
        projectResponsible.setPhone("228 123 5548");
        projectResponsible.setRelatedCompanyID(3);
        projectResponsible.setEmail("gus.guevara@micyber.mx");
        CRUD<ProjectResponsible> projectResponsibleCRUD = new ProjectResponsibleDAO();
        if (projectResponsibleCRUD.addElement(projectResponsible)) {
            assertEquals(projectResponsible.getEmail(),
                    projectResponsibleCRUD.getByID(4).getEmail());
        } else {
            fail("Error de Conexión o Responsable ya agregado");
        }
    }

    @Test
    public void registerProject() {
        Project project = new Project();
        project.setTitle("Minecraft en Python");
        project.setDescription("Recrear la obra maestra, Minecraft, en Python. Why? pues nomás.");
        project.setStatus("Available");
        project.setResources("Una lap bien shila y saber programación en Python");
        RelatedCompany relatedCompany = new RelatedCompanyDAO().getByID(3);
        relatedCompany.setEmployee(new ProjectResponsibleDAO().getResponsibleByCompanyID(3));
        project.setRequestedBy(relatedCompany);
        CRUD<Project> projectCRUD = new ProjectDAO();
        if (projectCRUD.addElement(project)) {
            assertEquals(project.getTitle(), projectCRUD.getByID(3).getTitle());
        } else {
            fail("Error de Conexión");
        }
    }

}
