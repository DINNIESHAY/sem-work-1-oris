package com.travelfellows.servlets;

import com.travelfellows.dto.TripWithOwner;
import com.travelfellows.services.ApplicationService;
import com.travelfellows.services.SessionService;
import com.travelfellows.services.TripService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/trip-application")
public class TripApplicationServlet extends HttpServlet {

    private TripService tripService;
    private ApplicationService applicationService;
    private SessionService sessionService;

    @Override
    public void init(ServletConfig config) {
        this.tripService = (TripService) config.getServletContext().getAttribute("tripService");
        this.sessionService = (SessionService) config.getServletContext().getAttribute("sessionService");
        this.applicationService = (ApplicationService) config.getServletContext().getAttribute("applicationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long tripId = Long.parseLong(req.getParameter("id"));

            TripWithOwner tripWithOwner = tripService.findTripWithOwnerById(tripId);

            req.setAttribute("tripWithOwner", tripWithOwner);
            req.getRequestDispatcher("/WEB-INF/views/trip-application.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Ошибка при загрузке формы заявки: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/trips-feed.jsp").forward(req, resp);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long userId = sessionService.getUserIdFromSession(req);
            Long tripId = Long.parseLong(req.getParameter("tripId"));
            String message = req.getParameter("message");
            String contacts = req.getParameter("contacts");

            applicationService.save(userId, tripId, message, contacts);

            req.setAttribute("successMessage", "Ваша заявка успешно отправлена организатору!");
            req.getRequestDispatcher("/WEB-INF/views/trip-application-success.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Ошибка при отправке заявки: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/trip-application.jsp").forward(req, resp);
        }
    }
}
