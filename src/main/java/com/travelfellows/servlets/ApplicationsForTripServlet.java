package com.travelfellows.servlets;

import com.travelfellows.dto.TripWithOwner;
import com.travelfellows.exceptions.SessionExpiredException;
import com.travelfellows.models.TripApplication;
import com.travelfellows.services.ApplicationService;
import com.travelfellows.services.TripService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/applications-for-trip")
public class ApplicationsForTripServlet extends HttpServlet {

    private TripService tripService;
    private ApplicationService applicationService;

    @Override
    public void init(ServletConfig config) {
        this.tripService = (TripService) config.getServletContext().getAttribute("tripService");
        this.applicationService = (ApplicationService) config.getServletContext().getAttribute("applicationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long tripId = Long.parseLong(req.getParameter("tripId"));
            TripWithOwner tripWithOwner = tripService.findTripWithOwnerById(tripId);

            List<TripApplication> applications = applicationService.getApplicationsByTripId(tripId);

            req.setAttribute("trip", tripWithOwner);
            req.setAttribute("applications", applications);
            req.getRequestDispatcher("/WEB-INF/views/applications-for-trip.jsp").forward(req, resp);

        } catch (SessionExpiredException e) {
            req.getSession().setAttribute("errorMessage", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/login");
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Ошибка при загрузке заявок: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/my-trips.jsp").forward(req, resp);
        }
    }
}
