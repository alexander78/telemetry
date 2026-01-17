package de.soprasteria.saver.model;

import java.time.LocalDate;

/**
 * Représente la performance d'un livreur pour une journée donnée.
 */
public class DriverPerformance {
    private String driverName;
    private LocalDate date;
    private int deliveriesCompleted;
    private int deliveriesOnTime;
    private int deliveriesLate;
    private double totalRevenue;
    private double fuelExpenses;
    private String notes;
    
    public DriverPerformance(String driverName, LocalDate date) {
        this.driverName = driverName;
        this.date = date;
    }
    
    public String getDriverName() {
        return driverName;
    }
    
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public int getDeliveriesCompleted() {
        return deliveriesCompleted;
    }
    
    public void setDeliveriesCompleted(int deliveriesCompleted) {
        this.deliveriesCompleted = deliveriesCompleted;
    }
    
    public int getDeliveriesOnTime() {
        return deliveriesOnTime;
    }
    
    public void setDeliveriesOnTime(int deliveriesOnTime) {
        this.deliveriesOnTime = deliveriesOnTime;
    }
    
    public int getDeliveriesLate() {
        return deliveriesLate;
    }
    
    public void setDeliveriesLate(int deliveriesLate) {
        this.deliveriesLate = deliveriesLate;
    }
    
    public double getTotalRevenue() {
        return totalRevenue;
    }
    
    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
    
    public double getFuelExpenses() {
        return fuelExpenses;
    }
    
    public void setFuelExpenses(double fuelExpenses) {
        this.fuelExpenses = fuelExpenses;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public double getOnTimeRate() {
        if (deliveriesCompleted == 0) {
            return 0.0;
        }
        return (deliveriesOnTime * 100.0) / deliveriesCompleted;
    }
    
    public double getNetRevenue() {
        return totalRevenue - fuelExpenses;
    }
    
    @Override
    public String toString() {
        return String.format("%s - %d livraisons, %.1f%% à temps", 
            driverName, deliveriesCompleted, getOnTimeRate());
    }
}
