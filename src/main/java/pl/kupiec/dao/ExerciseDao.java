package pl.kupiec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExerciseDao {
    
    private static final String CREATE_EXERCISE_QUERY =
            "INSERT INTO exercises(title, description) VALUES (?, ?)";
    private static final String READ_EXERCISE_QUERY =
            "SELECT * FROM exercises where id = ?";
    private static final String UPDATE_EXERCISE_QUERY =
            "UPDATE exercises SET title = ?, description = ?  where id = ?";
    private static final String DELETE_EXERCISE_QUERY =
            "DELETE FROM exercises WHERE id = ?";
    private static final String FIND_ALL_EXERCISES_QUERY =
            "SELECT * FROM exercises";
    private static final String FIND_ALL_EXERCISES_IDS_QUERY =
            "SELECT id FROM exercises";
    private static final String FIND_EXERCISES_BY_USER =
            "SELECT exercises.id, exercises.title, exercises.description FROM exercises JOIN solutions s on exercises.id = s.exercise_id WHERE user_id = ?";
    
    
    public Exercise create(Exercise exercise) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement =
                     conn.prepareStatement(CREATE_EXERCISE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, exercise.getTitle());
            statement.setString(2, exercise.getDescription());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                exercise.setId(resultSet.getInt(1));
            }
            return exercise;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    public Exercise read(int exerciseID) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(READ_EXERCISE_QUERY)) {
            statement.setInt(1, exerciseID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setTitle(resultSet.getString("title"));
                exercise.setDescription(resultSet.getString("description"));
                return exercise;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void update(Exercise exercise) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(UPDATE_EXERCISE_QUERY)) {
            statement.setString(1, exercise.getTitle());
            statement.setString(2, exercise.getDescription());
            statement.setInt(3, exercise.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void delete(int exerciseID) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(DELETE_EXERCISE_QUERY)) {
            statement.setInt(1, exerciseID);
            statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private Exercise[] addToArray(Exercise g, Exercise[] exercises) {
        Exercise[] tmpExercises = Arrays.copyOf(exercises, exercises.length + 1);
        tmpExercises[exercises.length] = g;
        return tmpExercises;
    }
    
    public Exercise[] findAll() {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(FIND_ALL_EXERCISES_QUERY)) {
            Exercise[] exercises = new Exercise[0];
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setTitle(resultSet.getString("title"));
                exercise.setDescription(resultSet.getString("description"));
                exercise.setId(resultSet.getInt("id"));
                exercises = addToArray(exercise, exercises);
            }
            return exercises;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Integer> findAllIds() {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(FIND_ALL_EXERCISES_IDS_QUERY)) {
            List<Integer> exercisesIds = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                exercisesIds.add(id);
            }
            return exercisesIds;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Exercise> readForUser(int userId) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(FIND_EXERCISES_BY_USER)) {
            statement.setInt(1, userId);
            List<Exercise> exercises = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setTitle(resultSet.getString("title"));
                exercise.setDescription(resultSet.getString("description"));
                exercises.add(exercise);
            }
            return exercises;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
