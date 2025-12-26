package com.travelfellows.servlets;

import com.travelfellows.exceptions.SessionExpiredException;
import com.travelfellows.models.Session;
import com.travelfellows.models.Trip;
import com.travelfellows.services.SessionService;
import com.travelfellows.services.TripService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/create-trip")
public class CreateTripServlet extends HttpServlet {

    private TripService tripService;
    private SessionService sessionService;

    @Override
    public void init(ServletConfig config) {
        this.tripService = (TripService) config.getServletContext().getAttribute("tripService");
        this.sessionService = (SessionService) config.getServletContext().getAttribute("sessionService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/create-trip.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long userId = sessionService.getUserIdFromSession(req);

            String destination = req.getParameter("destination");
            String departurePoint = req.getParameter("departurePoint");
            String startAtStr = req.getParameter("startAt");
            String endAtStr = req.getParameter("endAt");
            String budgetStr = req.getParameter("budget");
            String maxFellowsStr = req.getParameter("maxFellows");
            String description = req.getParameter("description");
            String[] tagsArray = req.getParameterValues("tags");

            Date startAt = null;
            Date endAt = null;

            if (startAtStr != null && !startAtStr.trim().isEmpty()) {
                LocalDateTime localDateTime = LocalDateTime.parse(startAtStr.replace(" ", "T"));
                startAt = Timestamp.valueOf(localDateTime);
            }

            if (endAtStr != null && !endAtStr.trim().isEmpty()) {
                LocalDateTime localDateTime = LocalDateTime.parse(endAtStr.replace(" ", "T"));
                endAt = Timestamp.valueOf(localDateTime);
            }

            BigDecimal budget = null;
            Integer maxFellows = null;

            if (budgetStr != null && !budgetStr.trim().isEmpty()) {
                budget = new BigDecimal(budgetStr);
            }

            if (maxFellowsStr != null && !maxFellowsStr.trim().isEmpty()) {
                maxFellows = Integer.parseInt(maxFellowsStr);
            }

            List<String> tags = null;
            if (tagsArray != null && tagsArray.length > 0) {
                tags = Arrays.stream(tagsArray)
                        .filter(tag -> tag != null && !tag.trim().isEmpty())
                        .collect(Collectors.toList());
            }

            Trip trip = new Trip(
                    userId,
                    destination,
                    departurePoint,
                    startAt,
                    endAt,
                    budget,
                    maxFellows,
                    description,
                    "PLANNING",
                    tags
            );

            Trip createdTrip = tripService.save(trip);

            if (createdTrip != null) {
                resp.sendRedirect(req.getContextPath() + "/trips-feed");
            } else {
                req.setAttribute("errorMessage", "Ошибка при создании поездки");
                req.getRequestDispatcher("/WEB-INF/views/create-trip.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Ошибка при создании поездки: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/create-trip.jsp").forward(req, resp);
        }
    }
}
