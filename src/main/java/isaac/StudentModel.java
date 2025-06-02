package isaac;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentModel {

    // Connect to SQL Server
    private Connection connect() {
        String url = "jdbc:sqlserver://DESKTOP-KDCMV5P\\SQLEXPRESS:1433;DatabaseName=Students;" +
                "user=aptech;" +
                "password=2929;" +
                "encrypt=false;" +
                "trustServerCertificate=true";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Database connection established.");
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    // Create Students table if it doesn't exist
    public void createTable() {
        String sql = "IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Students') " +
                "CREATE TABLE Students (" +
                "Id INT PRIMARY KEY IDENTITY(1,1), " +
                "Fullname VARCHAR(50), " +
                "Username VARCHAR(50) UNIQUE, " +
                "Passcode INT" +
                ")";

        try (Connection connection = connect();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
        } catch (SQLException e) {
            System.err.println("Table creation failed: " + e.getMessage());
        }
    }

    // Add a new student
    public void add(String fullname, String username, int passcode) {
        String sql = "INSERT INTO Students (Fullname, Username, Passcode) VALUES (?, ?, ?)";

        try (Connection connection = connect();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, fullname);
            statement.setString(2, username);
            statement.setInt(3, passcode);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Insertion failed: " + e.getMessage());
        }
    }

    // Check credentials
    public int check(String username, int passcode) {
        String sql = "SELECT Id, Passcode FROM Students WHERE Username = ?";
        int id = 0;

        try (Connection connection = connect();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int dbPasscode = resultSet.getInt("Passcode");
                if (dbPasscode == passcode) {
                    id = resultSet.getInt("Id");
                }
            }

        } catch (SQLException e) {
            System.err.println("Login check failed: " + e.getMessage());
        }

        return id;
    }

    // Get a list of all usernames in the database
    public java.util.List<String> getAllUsername() {
        java.util.List<String> usernames = new java.util.ArrayList<>();
        String sql = "SELECT Username FROM Students";

        try (Connection connection = connect();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                usernames.add(rs.getString("Username"));
            }

        } catch (SQLException e) {
            System.err.println("Failed to fetch usernames: " + e.getMessage());
        }

        return usernames;
    }
}
