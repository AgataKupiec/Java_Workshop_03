package pl.kupiec.admin_interface.users;

import pl.kupiec.dao.Group;
import pl.kupiec.dao.GroupDao;
import pl.kupiec.dao.User;
import pl.kupiec.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/userAdd")
public class UserAdd extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String inputName = request.getParameter("inputName");
        String inputEmail = request.getParameter("inputEmail");
        String inputPassword = request.getParameter("inputPassword");
        String inputGroup = request.getParameter("inputGroup");
        int groupId = Integer.parseInt(inputGroup);
        
        GroupDao groupDao = new GroupDao();
        Group group = groupDao.read(groupId);
        
        User user = new User(inputName, inputEmail, inputPassword);
        user.setGroup(group);
        
        UserDao userDao = new UserDao();
        userDao.createWithGroup(user);
        
        doGet(request, response);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GroupDao groupDao = new GroupDao();
        request.setAttribute("groups", groupDao.findAll());
        getServletContext().getRequestDispatcher("/admin_interface/addUser.jsp").forward(request, response);
    }
}
