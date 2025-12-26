package com.travelfellows.servlets;

import com.travelfellows.exceptions.SessionExpiredException;
import com.travelfellows.models.Session;
import com.travelfellows.models.Trip;
import com.travelfellows.services.SessionService;
import com.travelfellows.services.TripService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/edit-trip")
public class EditTripServlet extends HttpServlet {

    private TripService tripService;
    private SessionService sessionService;

    @Override
    public void init(ServletConfig config) {
        this.tripService = (TripService) config.getServletContext().getAttribute("tripService");
        this.sessionService = (SessionService) config.getServletContext().getAttribute("sessionService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = sessionService.getUserIdFromSession(req);

        String tripIdParam = req.getParameter("tripId");
        if (tripIdParam == null || tripIdParam.trim().isEmpty()) {
            req.getSession().setAttribute("errorMessage", "ID поездки не указан");
            resp.sendRedirect(req.getContextPath() + "/my-trips");
            return;
        }

        try {
            Long tripId = Long.parseLong(tripIdParam);
            Trip trip = tripService.findTripById(tripId);

            if (!trip.getUserId().equals(userId)) {
                req.getSession().setAttribute("errorMessage", "Вы можете редактировать только свои поездки");
                resp.sendRedirect(req.getContextPath() + "/my-trips");
                return;
            }

            req.setAttribute("trip", trip);
            req.getRequestDispatcher("/WEB-INF/views/edit-trip.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            req.getSession().setAttribute("errorMessage", "Неверный формат ID поездки");
            resp.sendRedirect(req.getContextPath() + "/my-trips");
        } catch (ServletException e) {
            req.getSession().setAttribute("errorMessage", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/my-trips");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Long userId = sessionService.getUserIdFromSession(req);

            String tripIdParam = req.getParameter("tripId");
            if (tripIdParam == null || tripIdParam.trim().isEmpty()) {
                req.getSession().setAttribute("errorMessage", "ID поездки не указан");
                resp.sendRedirect(req.getContextPath() + "/my-trips");
                return;
            }

            try {
                Long tripId = Long.parseLong(tripIdParam);

                Trip existingTrip = tripService.findTripById(tripId);

                if (!existingTrip.getUserId().equals(userId)) {
                    req.getSession().setAttribute("errorMessage", "Вы можете редактировать только свои поездки");
                    resp.sendRedirect(req.getContextPath() + "/my-trips");
                    return;
                }

                String startAtStr = req.getParameter("startAt");
                String endAtStr = req.getParameter("endAt");
                String budgetStr = req.getParameter("budget");
                String maxFellowsStr = req.getParameter("maxFellows");
                String description = req.getParameter("description");
                String status = req.getParameter("status");
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

                Trip updatedTrip = new Trip(
                        tripId,
                        userId,
                        existingTrip.getDestination(),
                        existingTrip.getDeparturePoint(),
                        startAt,
                        endAt,
                        budget,
                        maxFellows,
                        description,
                        status,
                        tags
                );

                boolean isUpdated = tripService.update(updatedTrip);

                if (isUpdated) {
                    resp.sendRedirect(req.getContextPath() + "/my-trips");
                } else {
                    req.setAttribute("errorMessage", "Ошибка при обновлении поездки.");
                    req.setAttribute("trip", existingTrip);
                    req.getRequestDispatcher("/WEB-INF/views/edit-trip.jsp").forward(req, resp);
                }

            } catch (NumberFormatException e) {
                req.getSession().setAttribute("errorMessage", "Неверный формат ID поездки");
                resp.sendRedirect(req.getContextPath() + "/my-trips");
            } catch (ServletException e) {
                req.getSession().setAttribute("errorMessage", e.getMessage());
                resp.sendRedirect(req.getContextPath() + "/my-trips");
            }
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Ошибка при обновлении поездки: " + e.getMessage());

            String tripIdParam = req.getParameter("tripId");
            if (tripIdParam != null) {
                Long tripId = Long.parseLong(tripIdParam);
                Trip trip = tripService.findTripById(tripId);
                req.setAttribute("trip", trip);
            }
            req.getRequestDispatcher("/WEB-INF/views/edit-trip.jsp").forward(req, resp);
        }
    }
}
