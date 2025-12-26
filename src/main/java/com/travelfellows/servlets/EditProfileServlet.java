package com.travelfellows.servlets;

import com.travelfellows.exceptions.SessionExpiredException;
import com.travelfellows.models.UserInfo;
import com.travelfellows.services.SessionService;
import com.travelfellows.services.UserInfoService;
import com.travelfellows.util.FileUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/edit-profile")
@MultipartConfig(
    maxFileSize = 5 * 1024 * 1024,
    maxRequestSize = 10 * 1024 * 1024
)
public class EditProfileServlet extends HttpServlet {

    private SessionService sessionService;
    private UserInfoService userInfoService;
    private String uploadDirectory;

    @Override
    public void init(ServletConfig config) {
        this.sessionService = (SessionService) config.getServletContext().getAttribute("sessionService");
        this.userInfoService = (UserInfoService) config.getServletContext().getAttribute("userInfoService");

        this.uploadDirectory = config.getServletContext().getRealPath("/uploads");
        if (this.uploadDirectory == null) {
            this.uploadDirectory = System.getProperty("java.io.tmpdir");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = sessionService.getUserIdFromSession(req);

        UserInfo userInfo = userInfoService.findById(userId);
        if (userInfo != null) {
            req.setAttribute("userInfo", userInfo);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/edit-profile.jsp");
            dispatcher.forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/create-profile");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Long userId = sessionService.getUserIdFromSession(req);

            UserInfo existingUserInfo = userInfoService.findById(userId);
            if (existingUserInfo == null) {
                resp.sendRedirect(req.getContextPath() + "/create-profile");
                return;
            }

            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String dateOfBirthStr = req.getParameter("dateOfBirth");
            String email = req.getParameter("email");
            String phoneNumber = req.getParameter("phoneNumber");
            String country = req.getParameter("country");
            String city = req.getParameter("city");
            String bio = req.getParameter("bio");

            Part filePart = req.getPart("avatar");
            String avatarUrl = FileUtil.processUploadedImage(filePart, uploadDirectory, userId);
            if (avatarUrl == null) {
                avatarUrl = existingUserInfo.getAvatarUrl();
            }

            LocalDate dateOfBirth = null;
            if (dateOfBirthStr != null && !dateOfBirthStr.trim().isEmpty()) {
                try {
                    dateOfBirth = LocalDate.parse(dateOfBirthStr);
                } catch (Exception e) {
                    System.err.println("Ошибка при получении даты: " + e.getMessage());
                }
            }

            UserInfo updatedUserInfo = new UserInfo(
                    userId,
                    firstName,
                    lastName,
                    email,
                    phoneNumber,
                    dateOfBirth,
                    country,
                    city,
                    avatarUrl,
                    bio
            );

            boolean isUpdated = userInfoService.update(updatedUserInfo);

            if (isUpdated) {
                resp.sendRedirect(req.getContextPath() + "/profile");
            } else {
                req.getSession().setAttribute("errorMessage", "Произошла ошибка при обновлении профиля. Пожалуйста, попробуйте еще раз.");
                resp.sendRedirect(req.getContextPath() + "/edit-profile");
            }
        } catch (SessionExpiredException e) {
            req.getSession().setAttribute("errorMessage", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/login");
        } catch (Exception e) {
            req.getSession().setAttribute("errorMessage", "Ошибка при обновлении профиля: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/edit-profile");
        }
    }
}