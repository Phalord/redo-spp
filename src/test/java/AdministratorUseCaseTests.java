import com.spp.model.dataaccess.dao.CoordinatorDAO;
import com.spp.model.dataaccess.idao.IUserDAO;
import com.spp.model.domain.Coordinator;
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
        IUserDAO<Coordinator> iUserDAO = new CoordinatorDAO();
        if (iUserDAO.addUser(coordinator)) {
            assertEquals(coordinator.getUsername(),
                    iUserDAO.getUserByUsername(coordinator.getUsername()).getUsername());
        } else {
            fail();
        }
    }

}
