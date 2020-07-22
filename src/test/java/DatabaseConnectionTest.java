import com.spp.utils.MySQLConnection;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class DatabaseConnectionTest {
    private final MySQLConnection mySQLConnection = new MySQLConnection();

    @Test
    public void testConnection() {
        try (Connection connection = mySQLConnection.getConnection()) {
            assertNotNull(connection);
        } catch (SQLException exception) {
            System.out.println(String.format("SQLException: %s", exception.getMessage()));
            System.out.println(String.format("SQLException: %s", exception.getErrorCode()));
            System.out.println(String.format("SQLException: %s", exception.getSQLState()));
            fail();
        }
    }
}
