package de.soprasteria.saver.manager;

import de.soprasteria.saver.model.DailyReport;
import de.soprasteria.saver.model.Objective;
import de.soprasteria.saver.analyzer.ReportAnalyzer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Gestionnaire principal pour ABC INTER.
 * Gère les rapports journaliers, les objectifs et génère des analyses.
 */
public class BusinessManager {
    
    private Map<LocalDate, DailyReport> dailyReports;
    private Map<String, Objective> objectives;
    private ReportAnalyzer analyzer;
    
    public BusinessManager() {
        this.dailyReports = new HashMap<>();
        this.objectives = new HashMap<>();
        this.analyzer = new ReportAnalyzer();
    }
    
    /**
     * Ajoute ou met à jour un rapport journalier.
     */
    public void addDailyReport(DailyReport report) {
        dailyReports.put(report.getDate(), report);
    }
    
    /**
     * Récupère un rapport journalier.
     */
    public DailyReport getDailyReport(LocalDate date) {
        return dailyReports.get(date);
    }
    
    /**
     * Récupère tous les rapports journaliers.
     */
    public List<DailyReport> getAllReports() {
        return new ArrayList<>(dailyReports.values());
    }
    
    /**
     * Analyse un rapport journalier et retourne les recommandations.
     */
    public String analyzeReport(LocalDate date) {
        DailyReport report = dailyReports.get(date);
        if (report == null) {
            return "Aucun rapport trouvé pour la date " + date;
        }
        
        ReportAnalyzer.AnalysisResult result = analyzer.analyze(report);
        return result.generateReport();
    }
    
    /**
     * Ajoute un nouvel objectif.
     */
    public void addObjective(Objective objective) {
        objectives.put(objective.getId(), objective);
    }
    
    /**
     * Récupère un objectif par son ID.
     */
    public Objective getObjective(String id) {
        return objectives.get(id);
    }
    
    /**
     * Récupère tous les objectifs.
     */
    public List<Objective> getAllObjectives() {
        return new ArrayList<>(objectives.values());
    }
    
    /**
     * Récupère les objectifs en cours.
     */
    public List<Objective> getActiveObjectives() {
        return objectives.values().stream()
            .filter(obj -> obj.getStatus() == Objective.ObjectiveStatus.EN_COURS)
            .collect(Collectors.toList());
    }
    
    /**
     * Récupère les objectifs en retard.
     */
    public List<Objective> getOverdueObjectives() {
        return objectives.values().stream()
            .filter(Objective::isOverdue)
            .collect(Collectors.toList());
    }
    
    /**
     * Met à jour le statut d'un objectif.
     */
    public void updateObjectiveStatus(String id, Objective.ObjectiveStatus status) {
        Objective objective = objectives.get(id);
        if (objective != null) {
            objective.setStatus(status);
        }
    }
    
    /**
     * Génère un rapport de suivi des objectifs.
     */
    public String generateObjectivesReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════════════════════════\n");
        sb.append("    SUIVI DES OBJECTIFS - ABC INTER\n");
        sb.append("═══════════════════════════════════════════════════════════\n\n");
        
        List<Objective> active = getActiveObjectives();
        List<Objective> overdue = getOverdueObjectives();
        
        if (!overdue.isEmpty()) {
            sb.append("🚨 OBJECTIFS EN RETARD (").append(overdue.size()).append("):\n");
            for (Objective obj : overdue) {
                sb.append("   ⚠️ ").append(obj.getTitle()).append(" - ");
                sb.append(String.format("%.1f%% complété", obj.getCompletionRate()));
                sb.append(" (échéance: ").append(obj.getTargetDate()).append(")\n");
                if (obj.getNotes() != null && !obj.getNotes().isEmpty()) {
                    sb.append("      Notes: ").append(obj.getNotes()).append("\n");
                }
            }
            sb.append("\n");
        }
        
        if (!active.isEmpty()) {
            sb.append("📋 OBJECTIFS EN COURS (").append(active.size()).append("):\n");
            for (Objective obj : active) {
                if (!obj.isOverdue()) {
                    String progress = generateProgressBar(obj.getCompletionRate());
                    sb.append("   • ").append(obj.getTitle()).append("\n");
                    sb.append("     ").append(progress).append(" ");
                    sb.append(String.format("%.1f%%", obj.getCompletionRate())).append("\n");
                    if (obj.getTargetDate() != null) {
                        sb.append("     Échéance: ").append(obj.getTargetDate()).append("\n");
                    }
                }
            }
            sb.append("\n");
        }
        
        List<Objective> completed = objectives.values().stream()
            .filter(obj -> obj.getStatus() == Objective.ObjectiveStatus.COMPLETE)
            .collect(Collectors.toList());
        
        if (!completed.isEmpty()) {
            sb.append("✅ OBJECTIFS COMPLÉTÉS (").append(completed.size()).append("):\n");
            for (Objective obj : completed) {
                sb.append("   ✓ ").append(obj.getTitle());
                if (obj.getCompletedDate() != null) {
                    sb.append(" (complété le ").append(obj.getCompletedDate()).append(")");
                }
                sb.append("\n");
            }
            sb.append("\n");
        }
        
        List<Objective> abandoned = objectives.values().stream()
            .filter(obj -> obj.getStatus() == Objective.ObjectiveStatus.ABANDONNE)
            .collect(Collectors.toList());
        
        if (!abandoned.isEmpty()) {
            sb.append("❌ OBJECTIFS ABANDONNÉS (").append(abandoned.size()).append("):\n");
            for (Objective obj : abandoned) {
                sb.append("   ✗ ").append(obj.getTitle()).append("\n");
                if (obj.getNotes() != null && !obj.getNotes().isEmpty()) {
                    sb.append("     Raison: ").append(obj.getNotes()).append("\n");
                }
            }
            sb.append("\n⚠️ ATTENTION: Vérifier la justification de chaque abandon.\n\n");
        }
        
        sb.append("═══════════════════════════════════════════════════════════\n");
        
        return sb.toString();
    }
    
    /**
     * Génère une synthèse mensuelle.
     */
    public String generateMonthlySummary(int year, int month) {
        List<DailyReport> monthReports = dailyReports.values().stream()
            .filter(r -> r.getDate().getYear() == year && r.getDate().getMonthValue() == month)
            .sorted((r1, r2) -> r1.getDate().compareTo(r2.getDate()))
            .collect(Collectors.toList());
        
        if (monthReports.isEmpty()) {
            return "Aucun rapport pour le mois " + month + "/" + year;
        }
        
        int totalDeliveries = monthReports.stream()
            .mapToInt(DailyReport::getNumberOfDeliveries)
            .sum();
        
        double totalRevenue = monthReports.stream()
            .mapToDouble(DailyReport::getRevenue)
            .sum();
        
        double totalExpenses = monthReports.stream()
            .mapToDouble(DailyReport::getExpenses)
            .sum();
        
        double totalProfit = totalRevenue - totalExpenses;
        
        double avgOnTimeRate = monthReports.stream()
            .mapToDouble(DailyReport::getOnTimeDeliveryRate)
            .average()
            .orElse(0.0);
        
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════════════════════════\n");
        sb.append("    SYNTHÈSE MENSUELLE - ").append(month).append("/").append(year).append("\n");
        sb.append("═══════════════════════════════════════════════════════════\n\n");
        
        sb.append("📊 INDICATEURS DU MOIS:\n");
        sb.append("   • Nombre de jours d'activité: ").append(monthReports.size()).append("\n");
        sb.append("   • Total livraisons: ").append(totalDeliveries).append("\n");
        sb.append("   • Moyenne par jour: ").append(String.format("%.1f", (double)totalDeliveries / monthReports.size())).append("\n");
        sb.append("   • Chiffre d'affaires: ").append(String.format("%.0f FCFA", totalRevenue)).append("\n");
        sb.append("   • Dépenses: ").append(String.format("%.0f FCFA", totalExpenses)).append("\n");
        sb.append("   • Bénéfice: ").append(String.format("%.0f FCFA", totalProfit)).append("\n");
        sb.append("   • Marge: ").append(String.format("%.1f%%", (totalProfit / totalRevenue) * 100)).append("\n");
        sb.append("   • Taux de ponctualité moyen: ").append(String.format("%.1f%%", avgOnTimeRate)).append("\n\n");
        
        sb.append("💡 RECOMMANDATIONS POUR LE MOIS PROCHAIN:\n");
        
        if (totalDeliveries / monthReports.size() < 10) {
            sb.append("   • URGENT: Augmenter le volume de livraisons (actuellement seulement ");
            sb.append(String.format("%.1f", (double)totalDeliveries / monthReports.size()));
            sb.append(" par jour)\n");
        }
        
        if ((totalProfit / totalRevenue) < 0.20) {
            sb.append("   • Améliorer la rentabilité - marge actuelle trop faible\n");
        }
        
        if (avgOnTimeRate < 85.0) {
            sb.append("   • Améliorer la ponctualité des livraisons\n");
        }
        
        sb.append("\n═══════════════════════════════════════════════════════════\n");
        
        return sb.toString();
    }
    
    /**
     * Génère une barre de progression visuelle.
     */
    private String generateProgressBar(double percentage) {
        int filled = (int) (percentage / 10);
        int empty = 10 - filled;
        return "[" + "█".repeat(Math.max(0, filled)) + "░".repeat(Math.max(0, empty)) + "]";
    }
}
