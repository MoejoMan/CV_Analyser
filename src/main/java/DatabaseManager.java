import java.sql.*;

public class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:sqlite:resumes.db";  // Path to your SQLite database file

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    public static void createSchema() {
        String createResumesTable = "CREATE TABLE IF NOT EXISTS resumes ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT, "
                + "email TEXT, "
                + "phone TEXT, "
                + "skills TEXT, "
                + "education TEXT, "
                + "work_experience TEXT"
                + ");";

        String createJobDescriptionsTable = "CREATE TABLE IF NOT EXISTS job_descriptions ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "title TEXT, "
                + "description TEXT"
                + ");";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(createResumesTable);
            stmt.execute(createJobDescriptionsTable);
            System.out.println("Database schema created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating schema: " + e.getMessage());
        }
    }
}
