package com.travelfellows.servlets;

import com.travelfellows.services.CookieService;
import com.travelfellows.services.SessionService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private SessionService sessionService;

    @Override
    public void init(ServletConfig config) {
        this.sessionService = (SessionService) config.getServletContext().getAttribute("sessionService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        sessionService.removeSessionFromDatabase(req);
        CookieService.deleteCookie(resp);
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + "/welcome");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
