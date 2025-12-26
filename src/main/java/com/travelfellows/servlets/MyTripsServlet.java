package com.travelfellows.servlets;

import com.travelfellows.dto.TripWithLikes;
import com.travelfellows.services.SessionService;
import com.travelfellows.services.TripService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/my-trips")
public class MyTripsServlet extends HttpServlet {

    private TripService tripService;
    private SessionService sessionService;

    @Override
    public void init(ServletConfig config) {
        this.tripService = (TripService) config.getServletContext().getAttribute("tripService");
        this.sessionService = (SessionService) config.getServletContext().getAttribute("sessionService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Long userId = sessionService.getUserIdFromSession(req);
            List<TripWithLikes> trips = tripService.findTripsWithLikes(userId);

            req.setAttribute("trips", trips);
            req.getRequestDispatcher("/WEB-INF/views/my-trips.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Ошибка при загрузке ленты поездок: " + e.getMessage());
            req.getRequestDispatcher("welcome.jsp").forward(req, resp);
        }
    }
}
