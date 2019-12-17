package pl.kupiec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupDao {
    
    private static final String CREATE_GROUP_QUERY =
            "INSERT INTO user_groups(name) VALUES (?)";
    private static final String READ_GROUP_QUERY =
            "SELECT * FROM user_groups where id = ?";
    private static final String UPDATE_GROUP_QUERY =
            "UPDATE user_groups SET name = ? where id = ?";
    private static final String DELETE_GROUP_QUERY =
            "DELETE FROM user_groups WHERE id = ?";
    private static final String FIND_ALL_GROUPS_QUERY =
            "SELECT * FROM user_groups";
    private static final String FIND_GROUPS_AND_USERS =
            "SELECT user_groups.id AS groupId, user_groups.name AS groupName, u.id AS userId, u.user_name AS userName, " +
                    "u.email AS userEmail  FROM user_groups LEFT JOIN users u on user_groups.id = u.user_group_id";
    
    
    public Group create(Group group) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement =
                     conn.prepareStatement(CREATE_GROUP_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, group.getName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                group.setId(resultSet.getInt(1));
            }
            return group;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    public Group read(int groupID) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(READ_GROUP_QUERY)) {
            statement.setInt(1, groupID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Group group = new Group();
                group.setId(resultSet.getInt("id"));
                group.setName(resultSet.getString("name"));
                return group;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void update(Group group) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(UPDATE_GROUP_QUERY)) {
            statement.setString(1, group.getName());
            statement.setInt(2, group.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void delete(int groupID) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(DELETE_GROUP_QUERY)) {
            statement.setInt(1, groupID);
            statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private Group[] addToArray(Group g, Group[] groups) {
        Group[] tmpGroups = Arrays.copyOf(groups, groups.length + 1);
        tmpGroups[groups.length] = g;
        return tmpGroups;
    }
    
    public Group[] findAll() {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(FIND_ALL_GROUPS_QUERY)) {
            Group[] groups = new Group[0];
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Group group = new Group();
                group.setName(resultSet.getString("name"));
                group.setId(resultSet.getInt("id"));
                groups = addToArray(group, groups);
            }
            return groups;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<User> findAllGroupsAndUsers() {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(FIND_GROUPS_AND_USERS)) {
            List<User> groupsAndUsers = new ArrayList<>();
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Group group = new Group();
                group.setName(resultSet.getString("groupName"));
                group.setId(resultSet.getInt("groupId"));
                User user = new User();
                user.setUser_name(resultSet.getString("userName"));
                user.setEmail(resultSet.getString("userEmail"));
                user.setId(resultSet.getInt("userId"));
                user.setGroup(group);
                groupsAndUsers.add(user);
            }
            return groupsAndUsers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
