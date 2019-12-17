package pl.kupiec.admin_interface.users;

import pl.kupiec.dao.User;
import pl.kupiec.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/userEdit")
public class UserEdit extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao userDao = new UserDao();
        List<Integer> allUserIds = userDao.findAllUserIds();
        
        for (Integer userId : allUserIds) {
            String parameterName = request.getParameter("name" + userId);
            if (parameterName != null) {
                User read = userDao.read(userId);
                read.setUser_name(parameterName);
                userDao.update(read);
            }
            String parameterEmail = request.getParameter("email" + userId);
            if (parameterEmail != null) {
                User read = userDao.read(userId);
                read.setEmail(parameterEmail);
                userDao.update(read);
            }
        }
        doGet(request, response);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao userDao = new UserDao();
        request.setAttribute("users", userDao.findAll());
        getServletContext().getRequestDispatcher("/admin_interface/editUsers.jsp").forward(request, response);
    }
}
