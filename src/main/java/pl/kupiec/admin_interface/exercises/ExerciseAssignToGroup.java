package pl.kupiec.admin_interface.exercises;

import pl.kupiec.dao.ExerciseDao;
import pl.kupiec.dao.GroupDao;
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
import java.util.Date;

@WebServlet("/exerciseAssignToGroup")
public class ExerciseAssignToGroup extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int exerciseId = Integer.parseInt(request.getParameter("exerciseId"));
        int groupId = Integer.parseInt(request.getParameter("groupId"));
        UserDao userDao = new UserDao();
        User[] allByGroupId = userDao.findAllByGroupId(groupId);
        SolutionDao solutionDao = new SolutionDao();
        for (User user : allByGroupId) {
            int userId = user.getId();
            Solution solution = new Solution(new Date(), exerciseId, userId);
            solutionDao.createPrimary(solution);
        }
        
        doGet(request, response);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (CheckAdmin.isAdmin(request)) {
            ExerciseDao exerciseDao = new ExerciseDao();
            request.setAttribute("exercises", exerciseDao.findAll());
            GroupDao groupDao = new GroupDao();
            request.setAttribute("groups", groupDao.findAll());
            getServletContext().getRequestDispatcher("/admin_interface/addExerciseToGroup.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
