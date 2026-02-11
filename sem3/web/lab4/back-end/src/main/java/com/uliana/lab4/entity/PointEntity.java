package com.uliana.lab4.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Table(name = "hitslr3")
public class PointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double x;

    @Column(nullable = false, precision = 60, scale = 50)
    private BigDecimal y;

    @Column(nullable = false)
    private int r;

    @Column(nullable = false)
    private boolean hit;

    @Column(name = "time_doing", nullable = false)
    private String timeDoing;

    @Column(name = "time_now", nullable = false)
    private LocalTime timeNow;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    // ===== getters / setters =====

    public Long getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public String getTimeDoing() {
        return timeDoing;
    }

    public void setTimeDoing(String timeDoing) {
        this.timeDoing = timeDoing;
    }

    public LocalTime getTimeNow() {
        return timeNow;
    }

    public void setTimeNow(LocalTime timeNow) {
        this.timeNow = timeNow;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
