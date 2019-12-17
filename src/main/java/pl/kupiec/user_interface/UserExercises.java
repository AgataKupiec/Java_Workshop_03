package pl.kupiec.user_interface;

import pl.kupiec.dao.Exercise;
import pl.kupiec.dao.ExerciseDao;
import pl.kupiec.dao.User;
import pl.kupiec.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/userExercises")
public class UserExercises extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("user");
        
        UserDao userDao = new UserDao();
        User user = userDao.read(userId);
        
        ExerciseDao exerciseDao = new ExerciseDao();
        List<Exercise> exercises = exerciseDao.readForUser(userId);
        request.setAttribute("exercises", exercises);
        
        getServletContext().getRequestDispatcher("/user_interface/showUserExercises.jsp").forward(request, response);
    }
}
