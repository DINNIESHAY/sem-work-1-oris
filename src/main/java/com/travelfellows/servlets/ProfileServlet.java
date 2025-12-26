package com.travelfellows.servlets;

import com.travelfellows.models.UserInfo;
import com.travelfellows.services.SessionService;
import com.travelfellows.services.UserInfoService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private SessionService sessionService;
    private UserInfoService userInfoService;

    @Override
    public void init(ServletConfig config) {
        this.sessionService = (SessionService) config.getServletContext().getAttribute("sessionService");
        this.userInfoService = (UserInfoService) config.getServletContext().getAttribute("userInfoService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = sessionService.getUserIdFromSession(req);
        UserInfo userInfo = userInfoService.findById(userId);

        if (userInfo != null) {
            req.setAttribute("userInfo", userInfo);
        } else {
            resp.sendRedirect(req.getContextPath() + "/create-profile");
            return;
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/profile.jsp");
        dispatcher.forward(req, resp);
    }
}
