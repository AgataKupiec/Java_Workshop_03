package pl.kupiec.user_interface;

import pl.kupiec.dao.User;
import pl.kupiec.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/userDetails")
public class UserDetails extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("user");
        UserDao userDao = new UserDao();
        User user = userDao.read(userId);
        request.setAttribute("userObj", user);
        getServletContext().getRequestDispatcher("/user_interface/indexUser.jsp").forward(request, response);
    }
}
