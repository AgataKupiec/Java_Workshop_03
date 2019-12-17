package pl.kupiec.admin_interface.users;

import pl.kupiec.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/userDelete")
public class UserDelete extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao userDao = new UserDao();
        List<Integer> allUserIds = userDao.findAllUserIds();
        
        for (Integer userId : allUserIds) {
            String parameterName = request.getParameter("delete" + userId);
            if (parameterName != null) {
                userDao.delete(userId);
            }
        }
        
        doGet(request, response);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao userDao = new UserDao();
        request.setAttribute("users", userDao.findAll());
        getServletContext().getRequestDispatcher("/admin_interface/deleteUsers.jsp").forward(request, response);
    }
}
