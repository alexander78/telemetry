package de.soprasteria.saver.model;

import java.time.LocalDate;

/**
 * Représente un objectif commercial ou opérationnel avec suivi de réalisation.
 */
public class Objective {
    private String id;
    private String title;
    private String description;
    private ObjectiveType type;
    private LocalDate createdDate;
    private LocalDate targetDate;
    private LocalDate completedDate;
    private ObjectiveStatus status;
    private double targetValue;
    private double currentValue;
    private String assignedTo;
    private String notes;
    
    public enum ObjectiveType {
        CHIFFRE_AFFAIRES,      // Objectif de CA
        NOMBRE_LIVRAISONS,      // Objectif de volume
        PONCTUALITE,           // Objectif de ponctualité
        FIDELISATION,          // Objectif de fidélisation client
        REDUCTION_COUTS,       // Objectif de réduction des coûts
        NOTORIETE,             // Objectif de notoriété/marketing
        FORMATION,             // Objectif de formation
        AUTRE
    }
    
    public enum ObjectiveStatus {
        EN_COURS,
        COMPLETE,
        EN_RETARD,
        ABANDONNE
    }
    
    public Objective(String id, String title, ObjectiveType type) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.createdDate = LocalDate.now();
        this.status = ObjectiveStatus.EN_COURS;
    }
    
    public String getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public ObjectiveType getType() {
        return type;
    }
    
    public void setType(ObjectiveType type) {
        this.type = type;
    }
    
    public LocalDate getCreatedDate() {
        return createdDate;
    }
    
    public LocalDate getTargetDate() {
        return targetDate;
    }
    
    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }
    
    public LocalDate getCompletedDate() {
        return completedDate;
    }
    
    public void setCompletedDate(LocalDate completedDate) {
        this.completedDate = completedDate;
    }
    
    public ObjectiveStatus getStatus() {
        return status;
    }
    
    public void setStatus(ObjectiveStatus status) {
        this.status = status;
        if (status == ObjectiveStatus.COMPLETE && completedDate == null) {
            this.completedDate = LocalDate.now();
        }
    }
    
    public double getTargetValue() {
        return targetValue;
    }
    
    public void setTargetValue(double targetValue) {
        this.targetValue = targetValue;
    }
    
    public double getCurrentValue() {
        return currentValue;
    }
    
    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }
    
    public String getAssignedTo() {
        return assignedTo;
    }
    
    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public double getCompletionRate() {
        if (targetValue <= 0) {
            return 0.0;
        }
        return Math.min(100.0, (currentValue / targetValue) * 100.0);
    }
    
    public boolean isOverdue() {
        if (targetDate == null || status == ObjectiveStatus.COMPLETE) {
            return false;
        }
        return LocalDate.now().isAfter(targetDate);
    }
    
    @Override
    public String toString() {
        return String.format("%s [%s] - %.1f%% complété", 
            title, status, getCompletionRate());
    }
}
