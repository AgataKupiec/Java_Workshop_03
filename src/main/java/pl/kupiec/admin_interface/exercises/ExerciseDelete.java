package pl.kupiec.admin_interface.exercises;

import pl.kupiec.dao.ExerciseDao;
import pl.kupiec.admin_interface.login.CheckAdmin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/exerciseDelete")
public class ExerciseDelete extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ExerciseDao exerciseDao = new ExerciseDao();
        List<Integer> allExercisesIds = exerciseDao.findAllIds();
        
        for (Integer id : allExercisesIds) {
            String parameterName = request.getParameter("delete" + id);
            if (parameterName != null) {
                exerciseDao.delete(id);
            }
        }
        doGet(request, response);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (CheckAdmin.isAdmin(request)) {
            ExerciseDao exerciseDao = new ExerciseDao();
            request.setAttribute("exercises", exerciseDao.findAll());
            getServletContext().getRequestDispatcher("/admin_interface/deleteExercises.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
        
        
    }
}
