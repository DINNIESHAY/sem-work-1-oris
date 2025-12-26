package com.travelfellows.servlets;

import com.travelfellows.exceptions.InvalidEmailException;
import com.travelfellows.exceptions.InvalidUsernameException;
import com.travelfellows.exceptions.WrongPasswordException;
import com.travelfellows.services.AuthenticationService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private AuthenticationService authenticationService;

    @Override
    public void init(ServletConfig config) {
        this.authenticationService = (AuthenticationService) config.getServletContext().getAttribute("authenticationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/register.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeatPassword");

        try {
            authenticationService.registerUser(resp, email, username, password, repeatPassword);

            resp.sendRedirect(req.getContextPath() + "/create-profile");
        } catch (InvalidEmailException | InvalidUsernameException | WrongPasswordException e) {
            req.getSession().setAttribute("errorMessage", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/register");
        }
    }
}
