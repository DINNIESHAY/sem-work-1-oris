package com.travelfellows.servlets;

import com.travelfellows.models.User;
import com.travelfellows.models.UserInfo;
import com.travelfellows.services.UserInfoService;
import com.travelfellows.services.UserService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/user-profile")
public class UserProfileServlet extends HttpServlet {

    private UserService userService;
    private UserInfoService userInfoService;

    @Override
    public void init(ServletConfig config) {
        this.userService = (UserService) config.getServletContext().getAttribute("userService");
        this.userInfoService = (UserInfoService) config.getServletContext().getAttribute("userInfoService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String userIdParam = req.getParameter("userId");
            if (userIdParam == null || userIdParam.isEmpty()) {
                req.setAttribute("errorMessage", "ID пользователя не указан");
                req.getRequestDispatcher("/WEB-INF/views/applications-for-trip.jsp").forward(req, resp);
                return;
            }

            Long userId = Long.parseLong(userIdParam);

            User user = userService.findById(userId);
            if (user == null) {
                req.setAttribute("errorMessage", "Пользователь не найден");
                req.getRequestDispatcher("/WEB-INF/views/applications-for-trip.jsp").forward(req, resp);
                return;
            }

            UserInfo userInfo = userInfoService.findById(userId);

            req.setAttribute("user", user);
            req.setAttribute("userInfo", userInfo);
            req.getRequestDispatcher("/WEB-INF/views/user-profile.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Ошибка при загрузке профиля: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/applications-for-trip.jsp").forward(req, resp);
        }
    }
}
