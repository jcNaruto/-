package com.jc.laboratory.entity;

import java.util.Date;

public class Manipulation {
    private Integer manipulationId;

    private String userId;

    private Date time;

    private Integer functionType;

    private Integer boxId;

    public Integer getManipulationId() {
        return manipulationId;
    }

    public void setManipulationId(Integer manipulationId) {
        this.manipulationId = manipulationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getFunctionType() {
        return functionType;
    }

    public void setFunctionType(Integer functionType) {
        this.functionType = functionType;
    }

    public Integer getBoxId() {
        return boxId;
    }

    public void setBoxId(Integer boxId) {
        this.boxId = boxId;
    }
}