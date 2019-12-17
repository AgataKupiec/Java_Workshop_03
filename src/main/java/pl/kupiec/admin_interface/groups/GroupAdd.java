package pl.kupiec.admin_interface.groups;

import pl.kupiec.dao.Group;
import pl.kupiec.dao.GroupDao;
import pl.kupiec.admin_interface.login.CheckAdmin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/groupAdd")
public class GroupAdd extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String inputName = request.getParameter("inputName");
        
        GroupDao groupDao = new GroupDao();
        
        Group[] all = groupDao.findAll();
        boolean isDuplicate = false;
        for (Group group : all) {
            if (group.getName().equals(inputName)) {
                isDuplicate = true;
                break;
            }
        }
        if (!isDuplicate) {
            Group newGroup = new Group(inputName);
            groupDao.create(newGroup);
            request.removeAttribute("group_not_added");
            
            response.sendRedirect(request.getContextPath() + "/groupsPrint");
        } else {
            request.setAttribute("group_not_added", "duplicate");
            getServletContext().getRequestDispatcher("/admin_interface/addGroup.jsp").forward(request, response);
        }
        
        
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (CheckAdmin.isAdmin(request)) {
            GroupDao groupDao = new GroupDao();
            request.setAttribute("groups", groupDao.findAll());
            getServletContext().getRequestDispatcher("/admin_interface/addGroup.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
