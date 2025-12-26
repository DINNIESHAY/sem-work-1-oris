package com.travelfellows.servlets;

import com.travelfellows.services.TripService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/delete-trip")
public class DeleteTripServlet extends HttpServlet {

    private TripService tripService;

    @Override
    public void init(ServletConfig config) {
        this.tripService = (TripService) config.getServletContext().getAttribute("tripService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String tripId = req.getParameter("tripId");

        if (tripId != null && !tripId.isEmpty()) {
            try {
                Long id = Long.parseLong(tripId);
                tripService.delete(id);
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        }

        resp.sendRedirect(req.getContextPath() + "/my-trips");
    }
}
