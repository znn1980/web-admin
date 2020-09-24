package com.ifacebox.web.admin.os.model;

/**
 * @author znn
 */
public class OsMemory {
    private double total;
    private double free;
    private double rate;

    public OsMemory(double total, double free) {
        this.total = total;
        this.free = free;
        this.rate = this.total == 0 ? 0 : (this.total - this.free) / this.total * 100;
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
