package com.travelfellows.filters;

import com.travelfellows.exceptions.AccessException;
import com.travelfellows.exceptions.SessionExpiredException;
import com.travelfellows.models.User;
import com.travelfellows.services.CookieService;
import com.travelfellows.services.SecurityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebFilter(urlPatterns = "*")
public class AuthFilter extends HttpFilter {

    private final List<String> protectedPaths;
    private SecurityService securityService;

    public AuthFilter() {
        this.protectedPaths = List.of("/profile", "create-profile", "/edit-profile", "/create-trip", "/my-trips", "/my-favorites", "/edit-trip", "/trip-application");
    }

    @Override
    public void init(FilterConfig config) {
        this.securityService = (SecurityService) config.getServletContext().getAttribute("securityService");
    }

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        String path = req.getServletPath();
        String sessionId = CookieService.extractSessionId(req);

        if (sessionId != null) {
            try {
                User currUser = securityService.getUserBySessionId(sessionId);
                req.setAttribute("user", currUser);
            } catch (AccessException | SessionExpiredException | IllegalArgumentException e) {
                CookieService.deleteCookie(resp);

                if (protectedPaths.contains(path)) {
                    req.getSession().setAttribute("error", e.getMessage());
                    resp.sendRedirect(req.getContextPath() + "/login");
                    return;
                }
            }
        }

        if (protectedPaths.contains(path) && sessionId == null) {
            req.getSession().setAttribute("error", "Необходимо войти в учетную запись.");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        chain.doFilter(req, resp);
    }
}