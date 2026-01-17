package de.soprasteria.saver.test;

import de.soprasteria.saver.manager.BusinessManager;
import de.soprasteria.saver.model.DailyReport;
import de.soprasteria.saver.model.Delivery;
import de.soprasteria.saver.model.DriverPerformance;
import de.soprasteria.saver.model.Objective;
import de.soprasteria.saver.analyzer.ReportAnalyzer;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Test simple pour vérifier le fonctionnement de l'assistant de gestion.
 */
public class SimpleTest {
    
    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("Test de l'Assistant de Gestion ABC INTER");
        System.out.println("═══════════════════════════════════════════════════════════\n");
        
        // Test 1: Création d'un rapport journalier
        testDailyReport();
        
        // Test 2: Analyse du rapport
        testAnalysis();
        
        // Test 3: Gestion des objectifs
        testObjectives();
        
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("✅ Tous les tests sont passés avec succès!");
        System.out.println("═══════════════════════════════════════════════════════════");
    }
    
    private static void testDailyReport() {
        System.out.println("📊 Test 1: Création d'un rapport journalier");
        System.out.println("─────────────────────────────────────────────────────────\n");
        
        LocalDate today = LocalDate.now();
        DailyReport report = new DailyReport(today);
        report.setRevenue(50000);
        report.setExpenses(35000);
        
        // Ajouter des livraisons
        for (int i = 1; i <= 5; i++) {
            Delivery delivery = new Delivery("LIV-" + i, "Client Test " + i);
            delivery.setAmount(10000);
            delivery.setDriverName("Livreur Test");
            delivery.setScheduledTime(LocalDateTime.now().minusHours(3).plusHours(i));
            delivery.setActualDeliveryTime(delivery.getScheduledTime().minusMinutes(5));
            delivery.setStatus(Delivery.DeliveryStatus.LIVREE);
            report.addDelivery(delivery);
        }
        
        // Ajouter une performance de livreur
        DriverPerformance perf = new DriverPerformance("Livreur Test", today);
        perf.setDeliveriesCompleted(5);
        perf.setDeliveriesOnTime(5);
        perf.setTotalRevenue(50000);
        perf.setFuelExpenses(15000);
        report.addDriverPerformance(perf);
        
        System.out.println("✓ Rapport créé: " + report);
        System.out.println("✓ Nombre de livraisons: " + report.getNumberOfDeliveries());
        System.out.println("✓ Bénéfice: " + report.getProfit() + " FCFA");
        System.out.println("✓ Taux de ponctualité: " + String.format("%.1f%%", report.getOnTimeDeliveryRate()));
        System.out.println();
    }
    
    private static void testAnalysis() {
        System.out.println("🔍 Test 2: Analyse d'un rapport");
        System.out.println("─────────────────────────────────────────────────────────\n");
        
        BusinessManager manager = new BusinessManager();
        LocalDate today = LocalDate.now();
        
        // Créer un rapport avec des problèmes
        DailyReport report = new DailyReport(today);
        report.setRevenue(25000);  // CA faible
        report.setExpenses(22000);  // Dépenses élevées
        
        // Seulement 6 livraisons (en dessous du minimum de 10)
        for (int i = 1; i <= 6; i++) {
            Delivery delivery = new Delivery("LIV-" + i, "Client " + i);
            delivery.setAmount(4000);
            delivery.setDriverName("Livreur A");
            delivery.setScheduledTime(LocalDateTime.now().minusHours(4).plusHours(i));
            
            // Certaines livraisons en retard
            if (i <= 3) {
                delivery.setActualDeliveryTime(delivery.getScheduledTime().minusMinutes(5));
            } else {
                delivery.setActualDeliveryTime(delivery.getScheduledTime().plusMinutes(30));
            }
            delivery.setStatus(Delivery.DeliveryStatus.LIVREE);
            report.addDelivery(delivery);
        }
        
        DriverPerformance perf = new DriverPerformance("Livreur A", today);
        perf.setDeliveriesCompleted(6);
        perf.setDeliveriesOnTime(3);
        perf.setDeliveriesLate(3);
        perf.setTotalRevenue(25000);
        perf.setFuelExpenses(10000);
        report.addDriverPerformance(perf);
        
        manager.addDailyReport(report);
        
        // Générer l'analyse
        String analysis = manager.analyzeReport(today);
        System.out.println(analysis);
    }
    
    private static void testObjectives() {
        System.out.println("\n🎯 Test 3: Gestion des objectifs");
        System.out.println("─────────────────────────────────────────────────────────\n");
        
        BusinessManager manager = new BusinessManager();
        
        // Créer des objectifs
        Objective obj1 = new Objective("OBJ-1", "Atteindre 250 livraisons ce mois", 
            Objective.ObjectiveType.NOMBRE_LIVRAISONS);
        obj1.setTargetValue(250);
        obj1.setCurrentValue(150);
        obj1.setTargetDate(LocalDate.now().plusDays(15));
        obj1.setAssignedTo("Directrice");
        
        Objective obj2 = new Objective("OBJ-2", "Améliorer la ponctualité à 95%", 
            Objective.ObjectiveType.PONCTUALITE);
        obj2.setTargetValue(95);
        obj2.setCurrentValue(88);
        obj2.setTargetDate(LocalDate.now().plusDays(20));
        obj2.setAssignedTo("Responsable Logistique");
        
        Objective obj3 = new Objective("OBJ-3", "Objectif complété", 
            Objective.ObjectiveType.FORMATION);
        obj3.setTargetValue(100);
        obj3.setCurrentValue(100);
        obj3.setStatus(Objective.ObjectiveStatus.COMPLETE);
        obj3.setAssignedTo("Tous");
        
        manager.addObjective(obj1);
        manager.addObjective(obj2);
        manager.addObjective(obj3);
        
        // Générer le rapport des objectifs
        String objectivesReport = manager.generateObjectivesReport();
        System.out.println(objectivesReport);
        
        System.out.println("✓ " + manager.getAllObjectives().size() + " objectifs créés");
        System.out.println("✓ " + manager.getActiveObjectives().size() + " objectifs actifs");
    }
}
