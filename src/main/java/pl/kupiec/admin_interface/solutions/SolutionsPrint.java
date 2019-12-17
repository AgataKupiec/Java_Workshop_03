package pl.kupiec.admin_interface.solutions;

import pl.kupiec.dao.ExerciseDao;
import pl.kupiec.dao.Solution;
import pl.kupiec.dao.SolutionDao;
import pl.kupiec.dao.User;
import pl.kupiec.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/solutionsPrint")
public class SolutionsPrint extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        SolutionDao solutionDao = new SolutionDao();
        Solution[] solutions = solutionDao.findAllByUserId(userId);
        Map<String, Solution> exerciseSolution = new LinkedHashMap<>();
        ExerciseDao exerciseDao = new ExerciseDao();
        for (Solution solution : solutions) {
            String title = exerciseDao.read(solution.getExercise_id()).getTitle();
            exerciseSolution.put(title, solution);
        }
        UserDao userDao = new UserDao();
        request.setAttribute("selectedUser", userDao.read(userId));
        request.setAttribute("solutions", exerciseSolution);
        
        List<User> users = userDao.findAll();
        request.setAttribute("users", users);
        getServletContext().getRequestDispatcher("/admin_interface/showSolutions.jsp").forward(request, response);
        
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao userDao = new UserDao();
        List<User> users = userDao.findAll();
        request.setAttribute("users", users);
        getServletContext().getRequestDispatcher("/admin_interface/showSolutions.jsp").forward(request, response);
    }
}
