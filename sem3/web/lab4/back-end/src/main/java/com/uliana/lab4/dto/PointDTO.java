package com.uliana.lab4.dto;

import java.math.BigDecimal;

public class PointDTO {
    private Long id;

    private double x;
    private BigDecimal y;
    private int r;

    private boolean hit;

    private String timeDoing;
    private String timeNow;

    public PointDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public BigDecimal getY() { return y; }
    public void setY(BigDecimal y) { this.y = y; }

    public int getR() { return r; }
    public void setR(int r) { this.r = r; }

    public boolean isHit() { return hit; }
    public void setHit(boolean hit) { this.hit = hit; }

    public String getTimeDoing() { return timeDoing; }
    public void setTimeDoing(String timeDoing) { this.timeDoing = timeDoing; }

    public String getTimeNow() { return timeNow; }
    public void setTimeNow(String timeNow) { this.timeNow = timeNow; }
}
