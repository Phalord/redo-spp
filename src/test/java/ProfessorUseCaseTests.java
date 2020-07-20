import com.spp.model.dataaccess.dao.ActivityDAO;
import com.spp.model.dataaccess.idao.CRUD;
import com.spp.model.domain.Activity;
import com.spp.model.domain.Practitioner;
import com.spp.model.domain.Professor;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ProfessorUseCaseTests {

    @Test
    public void addActivity() {
        Activity activity = new Activity();
        activity.setTitle("Actividad de Prueba 4");
        activity.setDescription("Esta actividad está siendo ingresada desde la clase ProfessorUseCaseTests");
        activity.setEstimatedCompletionHours((short) 6);
        Practitioner practitioner = new Practitioner();
        practitioner.setUsername("s11223331");
        activity.setDeliveredBy(practitioner);
        activity.setDueDate(Timestamp.valueOf("2020-07-26 13:25:00"));
        Professor professor = new Professor();
        professor.setUsername("p11223344");
        activity.setCreatedBy(professor);
        CRUD<Activity> activityCRUD = new ActivityDAO();
        if (activityCRUD.addElement(activity)) {
            assertTrue(activity.getTitle().equalsIgnoreCase(activityCRUD.getByID(11).getTitle()));
        } else {
            fail("No se guardó");
        }
    }

}
