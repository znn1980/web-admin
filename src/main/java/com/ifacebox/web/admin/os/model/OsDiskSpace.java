package com.ifacebox.web.admin.os.model;

/**
 * @author znn
 */
public class OsDiskSpace {
    private String name;
    private double total;
    private double free;
    private double rate;

    public OsDiskSpace() {

    }

    public OsDiskSpace(String name, double total, double free) {
        this.name = name;
        this.total = total;
        this.free = free;
        this.rate = this.total == 0 ? 0 : (this.total - this.free) / this.total * 100;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getFree() {
        return free;
    }

    public void setFree(double free) {
        this.free = free;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
