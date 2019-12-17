package pl.kupiec.admin_interface.login;

import pl.kupiec.dao.Admin;
import pl.kupiec.dao.AdminDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CheckAdmin {
    public static boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        AdminDao adminDao = new AdminDao();
        if (session.getAttribute("adminLogin") == null) {
            return false;
        }
        int attribute = (int) session.getAttribute("adminLogin");
        Admin admin = adminDao.read(attribute);
        return admin != null;
    }
}
