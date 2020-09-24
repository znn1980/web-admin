package com.ifacebox.web.admin.os.model;

/**
 * @author znn
 */
public class JvmMemory {
    private double total;
    private double free;
    private double max;
    private double rate;

    public JvmMemory(double total, double free, double max) {
        this.total = total;
        this.free = free;
        this.max = max;
        this.rate = this.total == 0 ? 0 : (this.total - this.free) / this.total * 100;
    }

    public JvmMemory(Runtime runtime) {
        this(runtime.totalMemory(), runtime.freeMemory(), runtime.maxMemory());
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

    public double getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
