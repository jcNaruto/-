package com.jc.laboratory.entity;

import java.util.Date;

public class Tool {
    private Integer boxId;

    private String name;

    private Integer isBorrowed;

    private String userId;

    private Date createTime;

    private Date updateTime;

    public Integer getBoxId() {
        return boxId;
    }

    public void setBoxId(Integer boxId) {
        this.boxId = boxId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getIsBorrowed() {
        return isBorrowed;
    }

    public void setIsBorrowed(Integer isBorrowed) {
        this.isBorrowed = isBorrowed;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}