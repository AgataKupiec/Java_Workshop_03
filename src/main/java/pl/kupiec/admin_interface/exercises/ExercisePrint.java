package pl.kupiec.admin_interface.exercises;

import pl.kupiec.dao.Exercise;
import pl.kupiec.dao.ExerciseDao;
import pl.kupiec.dao.Solution;
import pl.kupiec.dao.SolutionDao;
import pl.kupiec.dao.User;
import pl.kupiec.dao.UserDao;
import pl.kupiec.admin_interface.login.CheckAdmin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/exercisePrint")
public class ExercisePrint extends HttpServlet {
    
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (CheckAdmin.isAdmin(request)) {
            ExerciseDao exerciseDao = new ExerciseDao();
            Exercise[] exercises = exerciseDao.findAll();
            
            SolutionDao solutionDao = new SolutionDao();
            Solution[] solutions = solutionDao.findAll();
            
            UserDao userDao = new UserDao();
            List<User> users = userDao.findAll();
            
            Map<Exercise, List<User>> map = new LinkedHashMap<>();
            for (Exercise exercise : exercises) {
                int id = exercise.getId();
                Solution[] allByExerciseId = solutionDao.findAllByExerciseId(id);
                if (allByExerciseId != null) {
                    List<User> usersWithExercise = new ArrayList<>();
                    for (Solution solution : allByExerciseId) {
                        usersWithExercise.add(userDao.read(solution.getUser_id()));
                    }
                    map.put(exercise, usersWithExercise);
                } else {
                    map.put(exercise, null);
                }
            }
            request.setAttribute("allData", map);
            request.setAttribute("exercises", exercises);
            request.setAttribute("solutions", solutions);
            request.setAttribute("users", users);
            getServletContext().getRequestDispatcher("/admin_interface/showExercises.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
