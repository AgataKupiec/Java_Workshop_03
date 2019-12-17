package pl.kupiec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDao {
    
    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(user_name, email, password) VALUES (?, ?, ?)";
    private static final String CREATE_USER_WITH_GROUP_QUERY =
            "INSERT INTO users(user_name, email, password, user_group_id) VALUES (?, ?, ?, ?)";
    private static final String READ_USER_QUERY =
            "SELECT * FROM users where id = ?";
    private static final String UPDATE_USER_QUERY =
            "UPDATE users SET user_name = ?, email = ?, password = ? where id = ?";
    private static final String ADD_TO_GROUP =
            "UPDATE users SET user_group_id = ? where id = ?";
    private static final String DELETE_USER_QUERY =
            "DELETE FROM users WHERE id = ?";
    private static final String FIND_ALL_USERS_QUERY =
            "SELECT * FROM users LEFT JOIN user_groups ON users.user_group_id = user_groups.id;";
    private static final String FIND_ALL_USERS_BY_GROUP_QUERY =
            "SELECT * FROM users WHERE user_group_id = ?";
    private static final String FIND_ALL_USER_IDS =
            "SELECT id FROM users";
    
    public User create(User user) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement =
                     conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, user.getUser_name());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public User createWithGroup(User user) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement =
                     conn.prepareStatement(CREATE_USER_WITH_GROUP_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, user.getUser_name());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getGroup().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    public User read(int userId) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(READ_USER_QUERY)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUser_name(resultSet.getString("user_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPasswordAlreadyHashed(resultSet.getString("password"));
                GroupDao groupDao = new GroupDao();
                Group group = groupDao.read(resultSet.getInt("user_group_id"));
                if (group != null) {
                    user.setGroup(group);
                }
                //user.setUser_group_id(resultSet.getInt("user_group_id"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void update(User user) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(UPDATE_USER_QUERY)) {
            statement.setString(1, user.getUser_name());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addToGroup(int userId, int groupId) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(ADD_TO_GROUP)) {
            statement.setInt(1, groupId);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void delete(int userID) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(DELETE_USER_QUERY)) {
            statement.setInt(1, userID);
            statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1);
        tmpUsers[users.length] = u;
        return tmpUsers;
    }
    
    public List<User> findAll() {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(FIND_ALL_USERS_QUERY)) {
            List<User> users = new ArrayList<>();
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setUser_name(resultSet.getString("user_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPasswordAlreadyHashed(resultSet.getString("password"));
                user.setId(resultSet.getInt("id"));
                Group group = new Group();
                group.setId(resultSet.getInt("user_group_id"));
                group.setName(resultSet.getString("name"));
                user.setGroup(group);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Integer> findAllUserIds() {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(FIND_ALL_USER_IDS)) {
            List<Integer> userIds = new ArrayList<>();
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                
                userIds.add(resultSet.getInt(1));
            }
            return userIds;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public User[] findAllByGroupId(int groupId) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(FIND_ALL_USERS_BY_GROUP_QUERY)) {
            User[] users = new User[0];
            statement.setInt(1, groupId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setUser_name(resultSet.getString("user_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPasswordAlreadyHashed(resultSet.getString("password"));
                user.setId(resultSet.getInt("id"));
                users = addToArray(user, users);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
