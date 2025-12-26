package com.travelfellows.servlets;

import com.travelfellows.dto.TripWithOwner;
import com.travelfellows.services.TripService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/trip-info")
public class TripInfoServlet extends HttpServlet {

    private TripService tripService;

    @Override
    public void init(ServletConfig config) {
        this.tripService = (TripService) config.getServletContext().getAttribute("tripService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long tripId = Long.parseLong(req.getParameter("id"));

            TripWithOwner tripWithOwner = tripService.findTripWithOwnerById(tripId);

            if (tripWithOwner == null) {
                req.setAttribute("errorMessage", "Трип с ID " + tripId + " не найден");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/trips-feed.jsp");
                dispatcher.forward(req, resp);
                return;
            }

            req.setAttribute("tripWithOwner", tripWithOwner);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/trip-info.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Произошла ошибка при загрузке информации о трипе: " + e.getMessage());
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/trips-feed.jsp");
            dispatcher.forward(req, resp);
        }
    }
}