package pl.kupiec.admin_interface.groups;

import pl.kupiec.dao.GroupDao;
import pl.kupiec.dao.User;
import pl.kupiec.admin_interface.login.CheckAdmin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/groupsPrint")
public class GroupsPrint extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (CheckAdmin.isAdmin(request)) {
            GroupDao groupDao = new GroupDao();
            List<User> allGroupsAndUsers = groupDao.findAllGroupsAndUsers();
            request.setAttribute("groupsAndUsers", allGroupsAndUsers);
            getServletContext().getRequestDispatcher("/admin_interface/showGroups.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
        
    }
}
