package pl.kupiec.admin_interface.groups;

import pl.kupiec.dao.Group;
import pl.kupiec.dao.GroupDao;
import pl.kupiec.dao.User;
import pl.kupiec.dao.UserDao;
import pl.kupiec.admin_interface.login.CheckAdmin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/groupAddUser")
public class GroupAddUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("inputStudent"));
        int groupId = Integer.parseInt(request.getParameter("inputGroup"));
        UserDao userDao = new UserDao();
        userDao.addToGroup(userId, groupId);
        doGet(request, response);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (CheckAdmin.isAdmin(request)) {
            UserDao userDao = new UserDao();
            List<User> allUsers = userDao.findAll();
            GroupDao groupDao = new GroupDao();
            Group[] allGroups = groupDao.findAll();
            request.setAttribute("users", allUsers);
            request.setAttribute("groups", allGroups);
            getServletContext().getRequestDispatcher("/admin_interface/addUserToGroup.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
