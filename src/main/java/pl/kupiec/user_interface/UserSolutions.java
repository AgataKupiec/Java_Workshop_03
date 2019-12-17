package pl.kupiec.user_interface;

import pl.kupiec.dao.ExerciseDao;
import pl.kupiec.dao.Solution;
import pl.kupiec.dao.SolutionDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/userSolutions")
public class UserSolutions extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("user");
        SolutionDao solutionDao = new SolutionDao();
        Solution[] solutions = solutionDao.findAllByUserId(userId);
        
        Map<String, Solution> exerciseSolution = new LinkedHashMap<>();
        ExerciseDao exerciseDao = new ExerciseDao();
        for (Solution solution : solutions) {
            String title = exerciseDao.read(solution.getExercise_id()).getTitle();
            exerciseSolution.put(title, solution);
        }
        request.setAttribute("solutions", exerciseSolution);
        
        getServletContext().getRequestDispatcher("/user_interface/showUserSolutions.jsp").forward(request, response);
    }
}
