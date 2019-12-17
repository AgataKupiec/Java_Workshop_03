package pl.kupiec.admin_interface.login;

import org.mindrot.jbcrypt.BCrypt;
import pl.kupiec.dao.Admin;
import pl.kupiec.dao.AdminDao;
import pl.kupiec.dao.User;
import pl.kupiec.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String loginInput = request.getParameter("login");
        String passwordInput = request.getParameter("password");
        HttpSession session = request.getSession();
        UserDao userDao = new UserDao();
        List<User> users = userDao.findAll();
        boolean loggedIn = false;
        for (User user : users) {
            if (loginInput.equals(user.getEmail()) &&
                    BCrypt.checkpw(passwordInput, user.getPassword())) {
                session.setAttribute("user", user.getId());
                loggedIn = true;
                response.sendRedirect(request.getContextPath() + "/userDetails");
            }
        }
        AdminDao adminDao = new AdminDao();
        List<Admin> admins = adminDao.findAll();
        for (Admin admin : admins) {
            if (loginInput.equals(admin.getLogin())) {

                if (BCrypt.checkpw(passwordInput, admin.getPassword())) {
                    loggedIn = true;
                    session.setAttribute("adminLogin", admin.getId());
                    getServletContext().getRequestDispatcher("/admin_interface/indexAdmin.jsp").forward(request, response);
                }
            }
        }
        if (!loggedIn) {
            doGet(request, response);
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }
}

