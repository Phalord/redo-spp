import com.spp.model.dataaccess.dao.ActivityDAO;
import com.spp.model.dataaccess.dao.PartialReportDAO;
import com.spp.model.dataaccess.idao.IActivityDAO;
import com.spp.model.dataaccess.idao.IPartialReportDAO;
import com.spp.model.domain.Activity;
import com.spp.model.domain.PartialReport;
import org.junit.jupiter.api.Test;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PractitionerUseCaseTests {

    @Test
    public void generatePartialReport() {
        PartialReport partialReport = new PartialReport();
        partialReport.setReportType("Parcial");
        partialReport.setPartialPeriod("Primer Parcial");
        partialReport.setProjectHoursCovered((short) 100);
        partialReport.setReportNumber((byte) 1);
        Activity activity = new Activity();
        activity.setActivityID(3);
        activity.setActualCompletionHours((short) 8);
        activity.setDeliveredAt(Timestamp.valueOf("2020-07-20 06:39:00"));
        Activity activity2 = new Activity();
        activity2.setActivityID(4);
        activity2.setActualCompletionHours((short) 16);
        activity2.setDeliveredAt(Timestamp.valueOf("2020-07-20 06:39:00"));
        List<Activity> activities = new ArrayList<>();
        activities.add(activity);
        activities.add(activity2);
        partialReport.generateFolio(Timestamp.valueOf("2020-07-20 06:39:00"), "s11223344");
        partialReport.setActivities(activities);
        IPartialReportDAO iPartialReportDAO = new PartialReportDAO();
        boolean result = false;
        if (iPartialReportDAO.addElement(partialReport)) {
            IActivityDAO activityDAO = new ActivityDAO();
            for (Activity activity1: partialReport.getActivities()) {
                result = activityDAO.reportActivity(activity1, partialReport);
            }
        }
        assertTrue(result);
    }

}