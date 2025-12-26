package com.travelfellows.listener;

import com.travelfellows.repositories.impl.*;
import com.travelfellows.services.*;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class InitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        JdbcUserRepository userRepository = new JdbcUserRepository();
        JdbcUserInfoRepository userInfoRepository = new JdbcUserInfoRepository();
        JdbcSessionRepository sessionRepository = new JdbcSessionRepository();
        JdbcTripRepository tripRepository = new JdbcTripRepository();
        JdbcFavoritesRepository favoritesRepository = new JdbcFavoritesRepository();
        JdbcApplicationRepository applicationRepository = new JdbcApplicationRepository();

        UserService userService = new UserService(userRepository);
        UserInfoService userInfoService = new UserInfoService(userInfoRepository);
        SessionService sessionService = new SessionService(sessionRepository);
        TripService tripService = new TripService(tripRepository, userRepository, userInfoRepository, favoritesRepository);
        CookieService cookieService = new CookieService();
        AuthenticationService authenticationService = new AuthenticationService(userService, sessionService);
        SecurityService securityService = new SecurityService(sessionService, userService);
        ApplicationService applicationService = new ApplicationService(applicationRepository);

        sce.getServletContext().setAttribute("userService", userService);
        sce.getServletContext().setAttribute("userInfoService", userInfoService);
        sce.getServletContext().setAttribute("sessionService", sessionService);
        sce.getServletContext().setAttribute("tripService", tripService);
        sce.getServletContext().setAttribute("authenticationService", authenticationService);
        sce.getServletContext().setAttribute("securityService", securityService);
        sce.getServletContext().setAttribute("applicationService", applicationService);
        sce.getServletContext().setAttribute("cookieService", cookieService);
    }
}
