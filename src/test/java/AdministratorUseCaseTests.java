import com.spp.model.dataaccess.dao.CoordinatorDAO;
import com.spp.model.dataaccess.dao.ProfessorDAO;
import com.spp.model.dataaccess.idao.IUserDAO;
import com.spp.model.domain.Coordinator;
import com.spp.model.domain.Professor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AdministratorUseCaseTests {

    @Test
    public void registerCoordinator() {
        Coordinator coordinator = new Coordinator();
        coordinator.setUsername("c11223344");
        coordinator.setPassword("contra123seña!");
        coordinator.setName("Mauricio");
        coordinator.setSurnames("Iñigo Villegas");
        coordinator.setUserType("Coordinator");
        coordinator.setActive(true);
        IUserDAO<Coordinator> coordinatorIUserDAO = new CoordinatorDAO();
        if (coordinatorIUserDAO.addUser(coordinator)) {
            assertEquals(coordinator.getUsername(),
                    coordinatorIUserDAO.getUserByUsername(coordinator.getUsername()).getUsername());
        } else {
            fail();
        }
    }

    @Test
    public void registerProfessor() {
        Professor professor = new Professor();
        professor.setUsername("p11223344");
        professor.setPassword("contra123seña!");
        professor.setName("Abner Jeffrey");
        professor.setSurnames("Tapia Cruz");
        professor.setUserType("Professor");
        professor.setActive(true);
        IUserDAO<Professor> professorIUserDAO = new ProfessorDAO();
        if (professorIUserDAO.addUser(professor)) {
            assertEquals(professor.getUsername(),
                    professorIUserDAO.getUserByUsername(professor.getUsername()).getUsername());
        }
    }

}
