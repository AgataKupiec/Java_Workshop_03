package pl.kupiec.admin_interface.groups;

import pl.kupiec.dao.GroupDao;
import pl.kupiec.admin_interface.login.CheckAdmin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/groupDelete")
public class GroupDelete extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int inputGroup = Integer.parseInt(request.getParameter("inputGroup"));
        GroupDao groupDao = new GroupDao();
        groupDao.delete(inputGroup);
        response.sendRedirect(request.getContextPath() + "/groupsPrint");
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (CheckAdmin.isAdmin(request)) {
            GroupDao groupDao = new GroupDao();
            request.setAttribute("groups", groupDao.findAll());
            getServletContext().getRequestDispatcher("/admin_interface/deleteGroup.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
