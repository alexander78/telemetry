package de.soprasteria.saver.analyzer;

import de.soprasteria.saver.model.DailyReport;
import de.soprasteria.saver.model.Delivery;
import de.soprasteria.saver.model.DriverPerformance;

import java.util.ArrayList;
import java.util.List;

/**
 * Analyseur de rapports journaliers pour générer des recommandations intelligentes.
 * Spécialisé pour les PME de livraison comme ABC INTER à Douala, Cameroun.
 */
public class ReportAnalyzer {
    
    private static final double MIN_ON_TIME_RATE = 85.0; // Taux minimum de ponctualité acceptable
    private static final double MIN_PROFIT_MARGIN = 0.20; // Marge bénéficiaire minimale de 20%
    private static final int MIN_DAILY_DELIVERIES = 10; // Objectif minimum de livraisons par jour
    
    /**
     * Analyse un rapport journalier et génère des recommandations critiques et concrètes.
     */
    public AnalysisResult analyze(DailyReport report) {
        AnalysisResult result = new AnalysisResult();
        
        // Analyse financière
        analyzeFinancialPerformance(report, result);
        
        // Analyse opérationnelle
        analyzeOperationalPerformance(report, result);
        
        // Analyse de la performance des livreurs
        analyzeDriversPerformance(report, result);
        
        // Analyse de la ponctualité
        analyzePunctuality(report, result);
        
        // Génération des priorités
        prioritizeRecommendations(result);
        
        return result;
    }
    
    private void analyzeFinancialPerformance(DailyReport report, AnalysisResult result) {
        double profit = report.getProfit();
        double profitMargin = report.getRevenue() > 0 ? profit / report.getRevenue() : 0;
        
        result.addMetric("Chiffre d'affaires", String.format("%.0f FCFA", report.getRevenue()));
        result.addMetric("Dépenses", String.format("%.0f FCFA", report.getExpenses()));
        result.addMetric("Bénéfice", String.format("%.0f FCFA", profit));
        result.addMetric("Marge bénéficiaire", String.format("%.1f%%", profitMargin * 100));
        
        if (profit <= 0) {
            result.addCriticalIssue("⚠️ ALERTE CRITIQUE: Aucun bénéfice aujourd'hui. Perte de " + 
                String.format("%.0f FCFA", Math.abs(profit)));
            result.addRecommendation("URGENT: Réduire les dépenses immédiatement. Analyser chaque poste de dépense.", 1);
            result.addRecommendation("Augmenter le prix moyen par livraison de 10-15%.", 1);
        } else if (profitMargin < MIN_PROFIT_MARGIN) {
            result.addWarning("Marge bénéficiaire faible (" + String.format("%.1f%%", profitMargin * 100) + 
                "). Objectif: minimum 20%.");
            result.addRecommendation("Optimiser les tournées pour réduire les coûts de carburant.", 2);
            result.addRecommendation("Négocier avec les fournisseurs pour réduire les coûts fixes.", 2);
        } else {
            result.addSuccess("✓ Bonne rentabilité avec une marge de " + String.format("%.1f%%", profitMargin * 100));
        }
        
        // Analyse du CA par livraison
        if (report.getNumberOfDeliveries() > 0) {
            double revenuePerDelivery = report.getRevenue() / report.getNumberOfDeliveries();
            result.addMetric("CA moyen par livraison", String.format("%.0f FCFA", revenuePerDelivery));
            
            if (revenuePerDelivery < 2000) {
                result.addRecommendation("Le CA moyen par livraison est trop bas. Cibler des clients avec des colis plus volumineux ou des distances plus longues.", 2);
            }
        }
    }
    
    private void analyzeOperationalPerformance(DailyReport report, AnalysisResult result) {
        int deliveries = report.getNumberOfDeliveries();
        result.addMetric("Nombre de livraisons", String.valueOf(deliveries));
        
        if (deliveries < MIN_DAILY_DELIVERIES) {
            result.addCriticalIssue("⚠️ Volume de livraisons insuffisant: " + deliveries + 
                " (objectif minimum: " + MIN_DAILY_DELIVERIES + ")");
            result.addRecommendation("URGENT: Intensifier les actions commerciales. Contacter les clients inactifs.", 1);
            result.addRecommendation("Lancer une campagne promotionnelle sur les réseaux sociaux (Facebook, WhatsApp Business).", 1);
            result.addRecommendation("Former la commerciale à la prospection téléphonique intensive.", 1);
        } else if (deliveries < MIN_DAILY_DELIVERIES * 1.5) {
            result.addWarning("Volume de livraisons moyen. Objectif: " + (MIN_DAILY_DELIVERIES * 2) + " livraisons/jour.");
            result.addRecommendation("Développer des partenariats avec des e-commerces locaux.", 2);
            result.addRecommendation("Créer un programme de fidélité pour augmenter la récurrence.", 2);
        } else {
            result.addSuccess("✓ Bon volume d'activité avec " + deliveries + " livraisons");
        }
    }
    
    private void analyzeDriversPerformance(DailyReport report, AnalysisResult result) {
        List<DriverPerformance> performances = report.getDriverPerformances();
        
        if (performances.isEmpty()) {
            result.addWarning("Aucune donnée de performance des livreurs enregistrée.");
            result.addRecommendation("Mettre en place un suivi quotidien obligatoire de la performance de chaque livreur.", 2);
            return;
        }
        
        for (DriverPerformance perf : performances) {
            String driverInfo = perf.getDriverName() + ": " + perf.getDeliveriesCompleted() + 
                " livraisons, " + String.format("%.1f%%", perf.getOnTimeRate()) + " ponctualité";
            
            if (perf.getOnTimeRate() < 70) {
                result.addCriticalIssue("⚠️ Performance critique de " + perf.getDriverName() + 
                    " - Seulement " + String.format("%.1f%%", perf.getOnTimeRate()) + " de ponctualité");
                result.addRecommendation("URGENT: Entretien individuel avec " + perf.getDriverName() + 
                    " pour identifier les obstacles et mettre en place un plan d'action.", 1);
            } else if (perf.getOnTimeRate() < MIN_ON_TIME_RATE) {
                result.addWarning(perf.getDriverName() + " doit améliorer sa ponctualité (" + 
                    String.format("%.1f%%", perf.getOnTimeRate()) + ")");
                result.addRecommendation("Former " + perf.getDriverName() + 
                    " sur l'optimisation des trajets et la gestion du temps.", 2);
            } else {
                result.addSuccess("✓ Excellente performance de " + perf.getDriverName() + 
                    " (" + String.format("%.1f%%", perf.getOnTimeRate()) + " ponctualité)");
            }
            
            result.addDriverSummary(driverInfo);
        }
    }
    
    private void analyzePunctuality(DailyReport report, AnalysisResult result) {
        double onTimeRate = report.getOnTimeDeliveryRate();
        result.addMetric("Taux de ponctualité global", String.format("%.1f%%", onTimeRate));
        
        if (onTimeRate < 70) {
            result.addCriticalIssue("⚠️ ALERTE: Ponctualité inacceptable - " + 
                String.format("%.1f%%", onTimeRate) + " des livraisons à l'heure");
            result.addRecommendation("URGENT: Réorganiser les tournées avec le responsable logistique dès demain matin.", 1);
            result.addRecommendation("Analyser les zones problématiques et ajuster les horaires de départ.", 1);
        } else if (onTimeRate < MIN_ON_TIME_RATE) {
            result.addWarning("Ponctualité à améliorer: " + String.format("%.1f%%", onTimeRate) + 
                " (objectif: " + String.format("%.0f%%", MIN_ON_TIME_RATE) + ")");
            result.addRecommendation("Mettre en place des plages horaires plus réalistes.", 2);
            result.addRecommendation("Utiliser une application de navigation (Google Maps, Waze) pour optimiser les trajets.", 2);
        } else {
            result.addSuccess("✓ Excellente ponctualité: " + String.format("%.1f%%", onTimeRate));
        }
        
        // Analyser les livraisons en retard
        List<Delivery> lateDeliveries = report.getDeliveries().stream()
            .filter(d -> d.getActualDeliveryTime() != null && !d.isOnTime())
            .toList();
        
        if (!lateDeliveries.isEmpty()) {
            result.addWarning(lateDeliveries.size() + " livraisons en retard aujourd'hui.");
            for (Delivery delivery : lateDeliveries) {
                if (delivery.getDelayInMinutes() > 60) {
                    result.addCriticalIssue("Retard important pour le client " + delivery.getClientName() + 
                        ": " + delivery.getDelayInMinutes() + " minutes");
                    result.addRecommendation("Contacter " + delivery.getClientName() + 
                        " pour présenter des excuses et offrir une compensation (réduction sur prochaine livraison).", 1);
                }
            }
        }
    }
    
    private void prioritizeRecommendations(AnalysisResult result) {
        // Les recommandations sont déjà triées par priorité lors de leur ajout
        if (!result.getRecommendations().isEmpty()) {
            result.addSummary("🎯 ACTIONS PRIORITAIRES À RÉALISER IMMÉDIATEMENT:");
            List<String> priority1 = result.getRecommendations().stream()
                .filter(r -> r.getPriority() == 1)
                .map(r -> "• " + r.getText())
                .toList();
            
            for (String rec : priority1) {
                result.addSummary(rec);
            }
        }
    }
    
    /**
     * Classe pour stocker les résultats de l'analyse.
     */
    public static class AnalysisResult {
        private List<String> criticalIssues = new ArrayList<>();
        private List<String> warnings = new ArrayList<>();
        private List<String> successes = new ArrayList<>();
        private List<Recommendation> recommendations = new ArrayList<>();
        private List<String> metrics = new ArrayList<>();
        private List<String> driverSummaries = new ArrayList<>();
        private List<String> summaryPoints = new ArrayList<>();
        
        public void addCriticalIssue(String issue) {
            criticalIssues.add(issue);
        }
        
        public void addWarning(String warning) {
            warnings.add(warning);
        }
        
        public void addSuccess(String success) {
            successes.add(success);
        }
        
        public void addRecommendation(String text, int priority) {
            recommendations.add(new Recommendation(text, priority));
        }
        
        public void addMetric(String name, String value) {
            metrics.add(name + ": " + value);
        }
        
        public void addDriverSummary(String summary) {
            driverSummaries.add(summary);
        }
        
        public void addSummary(String point) {
            summaryPoints.add(point);
        }
        
        public List<String> getCriticalIssues() { return criticalIssues; }
        public List<String> getWarnings() { return warnings; }
        public List<String> getSuccesses() { return successes; }
        public List<Recommendation> getRecommendations() { return recommendations; }
        public List<String> getMetrics() { return metrics; }
        public List<String> getDriverSummaries() { return driverSummaries; }
        public List<String> getSummaryPoints() { return summaryPoints; }
        
        public String generateReport() {
            StringBuilder sb = new StringBuilder();
            sb.append("═══════════════════════════════════════════════════════════\n");
            sb.append("    ANALYSE JOURNALIÈRE - ABC INTER DOUALA\n");
            sb.append("═══════════════════════════════════════════════════════════\n\n");
            
            if (!metrics.isEmpty()) {
                sb.append("📊 INDICATEURS CLÉS:\n");
                for (String metric : metrics) {
                    sb.append("   ").append(metric).append("\n");
                }
                sb.append("\n");
            }
            
            if (!successes.isEmpty()) {
                sb.append("✅ POINTS POSITIFS:\n");
                for (String success : successes) {
                    sb.append("   ").append(success).append("\n");
                }
                sb.append("\n");
            }
            
            if (!criticalIssues.isEmpty()) {
                sb.append("🚨 PROBLÈMES CRITIQUES:\n");
                for (String issue : criticalIssues) {
                    sb.append("   ").append(issue).append("\n");
                }
                sb.append("\n");
            }
            
            if (!warnings.isEmpty()) {
                sb.append("⚡ POINTS D'ATTENTION:\n");
                for (String warning : warnings) {
                    sb.append("   ").append(warning).append("\n");
                }
                sb.append("\n");
            }
            
            if (!driverSummaries.isEmpty()) {
                sb.append("👥 PERFORMANCE DES LIVREURS:\n");
                for (String summary : driverSummaries) {
                    sb.append("   • ").append(summary).append("\n");
                }
                sb.append("\n");
            }
            
            if (!summaryPoints.isEmpty()) {
                sb.append("\n");
                for (String point : summaryPoints) {
                    sb.append(point).append("\n");
                }
                sb.append("\n");
            }
            
            if (!recommendations.isEmpty()) {
                sb.append("💡 RECOMMANDATIONS PAR ORDRE DE PRIORITÉ:\n\n");
                recommendations.stream()
                    .sorted((r1, r2) -> Integer.compare(r1.getPriority(), r2.getPriority()))
                    .forEach(rec -> {
                        String priorityLabel = rec.getPriority() == 1 ? "[URGENT]" : "[IMPORTANT]";
                        sb.append("   ").append(priorityLabel).append(" ").append(rec.getText()).append("\n");
                    });
                sb.append("\n");
            }
            
            sb.append("═══════════════════════════════════════════════════════════\n");
            sb.append("Note: Cette analyse est générée automatiquement pour vous\n");
            sb.append("accompagner dans la gestion quotidienne de votre entreprise.\n");
            sb.append("═══════════════════════════════════════════════════════════\n");
            
            return sb.toString();
        }
    }
    
    /**
     * Classe pour représenter une recommandation avec sa priorité.
     */
    public static class Recommendation {
        private String text;
        private int priority; // 1 = urgent, 2 = important, 3 = souhaitable
        
        public Recommendation(String text, int priority) {
            this.text = text;
            this.priority = priority;
        }
        
        public String getText() { return text; }
        public int getPriority() { return priority; }
    }
}
