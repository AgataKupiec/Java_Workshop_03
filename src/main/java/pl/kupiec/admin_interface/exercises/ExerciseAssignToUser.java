package pl.kupiec.admin_interface.exercises;

import pl.kupiec.dao.ExerciseDao;
import pl.kupiec.dao.Solution;
import pl.kupiec.dao.SolutionDao;
import pl.kupiec.dao.UserDao;
import pl.kupiec.admin_interface.login.CheckAdmin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet("/exerciseAssignToUser")
public class ExerciseAssignToUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int exerciseId = Integer.parseInt(request.getParameter("exerciseId"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        Solution solution = new Solution(new Date(), exerciseId, userId);
        SolutionDao solutionDao = new SolutionDao();
        solutionDao.createPrimary(solution);
        doGet(request, response);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (CheckAdmin.isAdmin(request)) {
            ExerciseDao exerciseDao = new ExerciseDao();
            request.setAttribute("exercises", exerciseDao.findAll());
            UserDao userDao = new UserDao();
            request.setAttribute("users", userDao.findAll());
            getServletContext().getRequestDispatcher("/admin_interface/addExerciseToUser.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
