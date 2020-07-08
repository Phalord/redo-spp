import com.spp.model.dataaccess.dao.PractitionerDAO;
import com.spp.model.dataaccess.idao.IUserDAO;
import com.spp.model.domain.Practitioner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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
            assertNotNull(iUserDAO.getUserByUsername(practitioner.getUsername()));
        } else {
            fail("Error de conexión");
        }
    }

}
