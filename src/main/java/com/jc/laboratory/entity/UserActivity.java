package com.jc.laboratory.entity;

public class UserActivity extends UserActivityKey {
    private Integer isComplete;

    private Integer score;

    public Integer getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(Integer isComplete) {
        this.isComplete = isComplete;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}