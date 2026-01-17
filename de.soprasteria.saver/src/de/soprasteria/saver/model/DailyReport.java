package de.soprasteria.saver.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente un rapport journalier de l'entreprise ABC INTER.
 * Contient toutes les données opérationnelles et financières d'une journée.
 */
public class DailyReport {
    private LocalDate date;
    private int numberOfDeliveries;
    private double revenue; // Chiffre d'affaires en FCFA
    private double expenses; // Dépenses en FCFA
    private List<Delivery> deliveries;
    private List<DriverPerformance> driverPerformances;
    private String notes;
    
    public DailyReport(LocalDate date) {
        this.date = date;
        this.deliveries = new ArrayList<>();
        this.driverPerformances = new ArrayList<>();
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public int getNumberOfDeliveries() {
        return numberOfDeliveries;
    }
    
    public void setNumberOfDeliveries(int numberOfDeliveries) {
        this.numberOfDeliveries = numberOfDeliveries;
    }
    
    public double getRevenue() {
        return revenue;
    }
    
    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
    
    public double getExpenses() {
        return expenses;
    }
    
    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }
    
    public double getProfit() {
        return revenue - expenses;
    }
    
    public List<Delivery> getDeliveries() {
        return deliveries;
    }
    
    public void addDelivery(Delivery delivery) {
        this.deliveries.add(delivery);
        this.numberOfDeliveries = this.deliveries.size();
    }
    
    public List<DriverPerformance> getDriverPerformances() {
        return driverPerformances;
    }
    
    public void addDriverPerformance(DriverPerformance performance) {
        this.driverPerformances.add(performance);
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public double getOnTimeDeliveryRate() {
        if (deliveries.isEmpty()) {
            return 0.0;
        }
        long onTimeCount = deliveries.stream()
            .filter(Delivery::isOnTime)
            .count();
        return (onTimeCount * 100.0) / deliveries.size();
    }
    
    @Override
    public String toString() {
        return String.format("Rapport du %s: %d livraisons, CA: %.0f FCFA, Bénéfice: %.0f FCFA", 
            date, numberOfDeliveries, revenue, getProfit());
    }
}
