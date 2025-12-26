package com.travelfellows.services;

import com.travelfellows.models.TripApplication;
import com.travelfellows.repositories.impl.JdbcApplicationRepository;

import java.util.List;

public class ApplicationService {

    private final JdbcApplicationRepository applicationRepository;

    public ApplicationService(JdbcApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public void save(Long userId, Long tripId, String message, String contacts) {
        TripApplication application = new TripApplication(userId, tripId, message, contacts);

        if (applicationRepository.existsByUserAndTrip(application.getUserId(), application.getTripId())) {
            throw new IllegalArgumentException("Вы уже отправляли заявку на участие в этой поездке");
        }

        applicationRepository.save(application);
    }

    public List<TripApplication> getApplicationsByTripId(Long tripId) {
        return applicationRepository.findByTripId(tripId);
    }
}
