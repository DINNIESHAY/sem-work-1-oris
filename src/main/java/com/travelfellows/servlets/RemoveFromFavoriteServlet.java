package com.travelfellows.servlets;

import com.travelfellows.services.SessionService;
import com.travelfellows.services.TripService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/remove-from-favorites")
public class RemoveFromFavoriteServlet extends HttpServlet {

    private TripService tripService;
    private SessionService sessionService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        tripService = (TripService) config.getServletContext().getAttribute("tripService");
        sessionService = (SessionService) config.getServletContext().getAttribute("sessionService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Long userId = sessionService.getUserIdFromSession(req);
            Long tripId = Long.parseLong(req.getParameter("tripId"));
            tripService.removeFromFavorites(userId, tripId);
        } catch (Exception e) {
            req.getSession().setAttribute("errorMessage", "Ошибка: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/trips-feed");
        }

        String returnUrl = req.getParameter("returnUrl");
        if (returnUrl == null || returnUrl.isEmpty()) {
            returnUrl = "trips-feed";
        }
        resp.sendRedirect(req.getContextPath() + "/" + returnUrl);
    }
}