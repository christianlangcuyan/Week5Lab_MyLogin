package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import service.AccountService;
/**
 *
 * @author Christian
 * 
 * result messages:
 * Invalid Login
 * You have successfully logged out
 * 
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String logout = request.getParameter("logout");
        if (logout != null) {
            HttpSession session = request.getSession();
            session.invalidate();
            request.setAttribute("result", "You have successfully logged out.");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp")
                    .forward(request, response);
            return;
        }
        HttpSession session = request.getSession();
        if(session != null){
            User user = (User) session.getAttribute("user");
            if(user != null) {
                response.sendRedirect("/Week5Lab_MyLogin/home");
                return;
            }
        }
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp")
                .forward(request, response);
       
        }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password  = request.getParameter("password");
        
        if(username.equals("") || username == null || password.equals("") || password == null) {
                request.setAttribute("result", "Invalid login.");
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp")
                .forward(request, response);
                return;
            }
        
            AccountService accountService = new AccountService();
            User user = accountService.login(username, password);
        if (user != null){
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("/Week5Lab_MyLogin/home");
        } else {
            request.setAttribute("result", "Invalid login.");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp")
                .forward(request, response);
        }
        
    }

}
