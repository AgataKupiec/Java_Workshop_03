package pl.kupiec.user_interface;

import pl.kupiec.dao.Exercise;
import pl.kupiec.dao.ExerciseDao;
import pl.kupiec.dao.SolutionDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/userAddSolution")
public class UserAddSolution extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("user");
        int exerciseId = Integer.parseInt(request.getParameter("exerciseId"));
        String description = request.getParameter("description");
        SolutionDao solutionDao = new SolutionDao();
        solutionDao.addUserSolution(description, userId, exerciseId);
        doGet(request, response);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("user");
        ExerciseDao exerciseDao = new ExerciseDao();
        List<Exercise> exercises = exerciseDao.readForUser(userId);
        request.setAttribute("exercises", exercises);
        
        getServletContext().getRequestDispatcher("/user_interface/addUserSolution.jsp").forward(request, response);
    }
}
