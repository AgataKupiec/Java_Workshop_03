package pl.kupiec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {
    
    private static final String READ_ADMIN_QUERY =
            "SELECT * FROM admins where id = ?";
    
    private static final String FIND_ALL_ADMINS_QUERY =
            "SELECT * FROM admins";
    
    private static final String CREATE_ADMIN =
            "INSERT INTO admins(login, password) VALUES (?, ?);";
    
    public Admin read(int adminId) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(READ_ADMIN_QUERY)) {
            statement.setInt(1, adminId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Admin admin = new Admin();
                admin.setLogin(resultSet.getString("login"));
                admin.setPasswordAlreadyHashed(resultSet.getString("password"));
                admin.setId(resultSet.getInt("id"));
                return admin;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Admin> findAll() {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(FIND_ALL_ADMINS_QUERY)) {
            List<Admin> admins = new ArrayList<>();
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Admin admin = new Admin();
                admin.setLogin(resultSet.getString("login"));
                admin.setPasswordAlreadyHashed(resultSet.getString("password"));
                admin.setId(resultSet.getInt("id"));
                admins.add(admin);
            }
            return admins;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void create(Admin admin) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(CREATE_ADMIN)) {
            statement.setString(1, admin.getLogin());
            statement.setString(2, admin.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
