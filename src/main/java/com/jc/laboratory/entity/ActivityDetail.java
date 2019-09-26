package com.jc.laboratory.entity;

public class ActivityDetail {
    private Integer activityDetailId;

    private String week;

    private String day;

    private String activityOrder;

    private String location;

    private Integer activityId;

    public Integer getActivityDetailId() {
        return activityDetailId;
    }

    public void setActivityDetailId(Integer activityDetailId) {
        this.activityDetailId = activityDetailId;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week == null ? null : week.trim();
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day == null ? null : day.trim();
    }

    public String getActivityOrder() {
        return activityOrder;
    }

    public void setActivityOrder(String activityOrder) {
        this.activityOrder = activityOrder == null ? null : activityOrder.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }
}