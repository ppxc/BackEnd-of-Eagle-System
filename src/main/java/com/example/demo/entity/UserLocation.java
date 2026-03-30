package com.example.demo.entity;

import java.time.LocalDateTime;

public class UserLocation {
    private Long id;
    private String usercode;
    private Double longitude;
    private Double latitude;
    private LocalDateTime createTime;

    // ====================== 新增这 4 个字段 ======================
    private String username;  // 姓名
    private String ckl;       // ckl
    private String dsl;       // dsl
    private String hj;        // hj

    // ====================== Getter & Setter ======================
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    // ====================== 新增字段的 Get/Set ======================
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCkl() {
        return ckl;
    }

    public void setCkl(String ckl) {
        this.ckl = ckl;
    }

    public String getDsl() {
        return dsl;
    }

    public void setDsl(String dsl) {
        this.dsl = dsl;
    }

    public String getHj() {
        return hj;
    }

    public void setHj(String hj) {
        this.hj = hj;
    }
}