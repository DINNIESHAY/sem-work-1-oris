package com.travelfellows.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Trip {
    private final Long id;
    private final Long userId;
    private final String destination;
    private final String departurePoint;
    private final Date startAt;
    private final Date endAt;
    private final BigDecimal budget;
    private final Integer maxFellows;
    private final String description;
    private final String status;
    private final List<String> tags;

    public Trip(Long id, Long userId, String destination, String departurePoint,
                Date startAt, Date endAt, BigDecimal budget,
                Integer maxFellows, String description, String status,
                List<String> tags) {
        this.id = id;
        this.userId = userId;
        this.destination = destination;
        this.departurePoint = departurePoint;
        this.startAt = startAt;
        this.endAt = endAt;
        this.budget = budget;
        this.maxFellows = maxFellows;
        this.description = description;
        this.status = status;
        this.tags = tags;
    }

    public Trip(Long userId, String destination, String departurePoint,
                Date startAt, Date endAt, BigDecimal budget,
                Integer maxFellows, String description, String status,
                List<String> tags) {
        this(null, userId, destination, departurePoint, startAt, endAt, budget,
                maxFellows, description, status, tags);
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getDestination() { return destination; }
    public String getDeparturePoint() { return departurePoint; }
    public Date getStartAt() { return startAt; }
    public Date getEndAt() { return endAt; }
    public BigDecimal getBudget() { return budget; }
    public Integer getMaxFellows() { return maxFellows; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public List<String> getTags() { return tags; }
}