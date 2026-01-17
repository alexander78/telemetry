package de.soprasteria.saver.model;

import java.time.LocalDateTime;

/**
 * Représente une livraison individuelle.
 */
public class Delivery {
    private String id;
    private String clientName;
    private String clientPhone;
    private String pickupAddress;
    private String deliveryAddress;
    private LocalDateTime scheduledTime;
    private LocalDateTime actualDeliveryTime;
    private String driverName;
    private double amount; // Montant en FCFA
    private DeliveryStatus status;
    private String notes;
    
    public enum DeliveryStatus {
        EN_ATTENTE,
        EN_COURS,
        LIVREE,
        ANNULEE,
        RETARDEE
    }
    
    public Delivery(String id, String clientName) {
        this.id = id;
        this.clientName = clientName;
        this.status = DeliveryStatus.EN_ATTENTE;
    }
    
    public String getId() {
        return id;
    }
    
    public String getClientName() {
        return clientName;
    }
    
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    
    public String getClientPhone() {
        return clientPhone;
    }
    
    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }
    
    public String getPickupAddress() {
        return pickupAddress;
    }
    
    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }
    
    public String getDeliveryAddress() {
        return deliveryAddress;
    }
    
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
    
    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }
    
    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
    
    public LocalDateTime getActualDeliveryTime() {
        return actualDeliveryTime;
    }
    
    public void setActualDeliveryTime(LocalDateTime actualDeliveryTime) {
        this.actualDeliveryTime = actualDeliveryTime;
    }
    
    public String getDriverName() {
        return driverName;
    }
    
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public DeliveryStatus getStatus() {
        return status;
    }
    
    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public boolean isOnTime() {
        if (actualDeliveryTime == null || scheduledTime == null) {
            return false;
        }
        return !actualDeliveryTime.isAfter(scheduledTime);
    }
    
    public long getDelayInMinutes() {
        if (actualDeliveryTime == null || scheduledTime == null) {
            return 0;
        }
        return java.time.Duration.between(scheduledTime, actualDeliveryTime).toMinutes();
    }
}
