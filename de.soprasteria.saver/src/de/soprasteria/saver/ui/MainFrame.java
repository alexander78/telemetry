package de.soprasteria.saver.ui;

import de.soprasteria.saver.manager.BusinessManager;
import de.soprasteria.saver.model.DailyReport;
import de.soprasteria.saver.model.Delivery;
import de.soprasteria.saver.model.DriverPerformance;
import de.soprasteria.saver.model.Objective;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Interface graphique principale pour l'assistant de gestion ABC INTER.
 */
public class MainFrame extends JFrame {
    
    private BusinessManager manager;
    private JTextArea outputArea;
    private JTabbedPane tabbedPane;
    
    public MainFrame() {
        this.manager = new BusinessManager();
        initializeUI();
        loadSampleData();
    }
    
    private void initializeUI() {
        setTitle("Assistant de Gestion ABC INTER - Douala, Cameroun");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        // Créer le panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Bannière en haut
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setPreferredSize(new Dimension(1000, 80));
        JLabel titleLabel = new JLabel("🚚 ABC INTER - Assistant de Gestion Intelligent");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        // Zone de tabs
        tabbedPane = new JTabbedPane();
        
        // Tab 1: Analyse journalière
        JPanel dailyPanel = createDailyAnalysisPanel();
        tabbedPane.addTab("📊 Analyse Journalière", dailyPanel);
        
        // Tab 2: Objectifs
        JPanel objectivesPanel = createObjectivesPanel();
        tabbedPane.addTab("🎯 Suivi des Objectifs", objectivesPanel);
        
        // Tab 3: Synthèse mensuelle
        JPanel monthlyPanel = createMonthlySummaryPanel();
        tabbedPane.addTab("📈 Synthèse Mensuelle", monthlyPanel);
        
        // Tab 4: À propos
        JPanel aboutPanel = createAboutPanel();
        tabbedPane.addTab("ℹ️ À propos", aboutPanel);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JPanel createDailyAnalysisPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Zone de sortie
        outputArea = new JTextArea();
        outputArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        
        // Panneau de contrôle
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JButton analyzeButton = new JButton("📊 Analyser le rapport d'aujourd'hui");
        analyzeButton.setFont(new Font("Arial", Font.BOLD, 14));
        analyzeButton.addActionListener(e -> analyzeToday());
        
        JButton clearButton = new JButton("🗑️ Effacer");
        clearButton.addActionListener(e -> outputArea.setText(""));
        
        controlPanel.add(analyzeButton);
        controlPanel.add(clearButton);
        
        panel.add(controlPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createObjectivesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextArea objectivesArea = new JTextArea();
        objectivesArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        objectivesArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(objectivesArea);
        
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JButton showButton = new JButton("📋 Afficher les objectifs");
        showButton.setFont(new Font("Arial", Font.BOLD, 14));
        showButton.addActionListener(e -> {
            String report = manager.generateObjectivesReport();
            objectivesArea.setText(report);
        });
        
        JButton addButton = new JButton("➕ Ajouter un objectif");
        addButton.addActionListener(e -> showAddObjectiveDialog());
        
        controlPanel.add(showButton);
        controlPanel.add(addButton);
        
        panel.add(controlPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createMonthlySummaryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextArea summaryArea = new JTextArea();
        summaryArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        summaryArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(summaryArea);
        
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JLabel monthLabel = new JLabel("Mois:");
        JComboBox<String> monthCombo = new JComboBox<>(new String[]{
            "1 - Janvier", "2 - Février", "3 - Mars", "4 - Avril", "5 - Mai", "6 - Juin",
            "7 - Juillet", "8 - Août", "9 - Septembre", "10 - Octobre", "11 - Novembre", "12 - Décembre"
        });
        monthCombo.setSelectedIndex(LocalDate.now().getMonthValue() - 1);
        
        JLabel yearLabel = new JLabel("Année:");
        JComboBox<Integer> yearCombo = new JComboBox<>(new Integer[]{2024, 2025, 2026, 2027});
        yearCombo.setSelectedItem(LocalDate.now().getYear());
        
        JButton generateButton = new JButton("📈 Générer la synthèse");
        generateButton.setFont(new Font("Arial", Font.BOLD, 14));
        generateButton.addActionListener(e -> {
            int month = monthCombo.getSelectedIndex() + 1;
            int year = (Integer) yearCombo.getSelectedItem();
            String summary = manager.generateMonthlySummary(year, month);
            summaryArea.setText(summary);
        });
        
        controlPanel.add(monthLabel);
        controlPanel.add(monthCombo);
        controlPanel.add(yearLabel);
        controlPanel.add(yearCombo);
        controlPanel.add(generateButton);
        
        panel.add(controlPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createAboutPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextArea aboutText = new JTextArea();
        aboutText.setEditable(false);
        aboutText.setFont(new Font("Arial", Font.PLAIN, 14));
        aboutText.setLineWrap(true);
        aboutText.setWrapStyleWord(true);
        
        String about = """
                ═══════════════════════════════════════════════════════════
                    Assistant de Gestion Intelligent ABC INTER
                ═══════════════════════════════════════════════════════════
                
                🎯 OBJECTIF:
                Accompagner les dirigeants de PME de livraison dans leur 
                gestion quotidienne avec des analyses pertinentes et des
                recommandations concrètes.
                
                📋 FONCTIONNALITÉS:
                
                • Analyse des rapports journaliers avec indicateurs clés
                • Recommandations critiques et priorisées
                • Suivi de la performance des livreurs
                • Suivi des objectifs commerciaux et opérationnels
                • Alertes sur les problèmes critiques
                • Synthèses mensuelles automatiques
                
                🏢 CONTEXTE:
                Spécialement conçu pour les entreprises de livraison 
                de plis et colis au Cameroun, comme ABC INTER à Douala.
                
                💡 UTILISATION:
                
                1. Analyse Journalière: Analysez les performances du jour
                   et obtenez des recommandations concrètes
                
                2. Suivi des Objectifs: Gérez et suivez vos objectifs
                   commerciaux et opérationnels
                
                3. Synthèse Mensuelle: Obtenez une vue d'ensemble des
                   performances mensuelles
                
                📊 INDICATEURS SUIVIS:
                • Chiffre d'affaires et rentabilité
                • Nombre de livraisons
                • Taux de ponctualité
                • Performance des livreurs
                • Dépenses et optimisation des coûts
                
                ⚡ STYLE D'ANALYSE:
                Critique, pertinent et structuré - jamais superficiel.
                Toujours avec des solutions concrètes adaptées au contexte
                camerounais.
                
                ═══════════════════════════════════════════════════════════
                Version 1.0 - 2026
                ═══════════════════════════════════════════════════════════
                """;
        
        aboutText.setText(about);
        
        JScrollPane scrollPane = new JScrollPane(aboutText);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void analyzeToday() {
        LocalDate today = LocalDate.now();
        String analysis = manager.analyzeReport(today);
        outputArea.setText(analysis);
    }
    
    private void showAddObjectiveDialog() {
        JDialog dialog = new JDialog(this, "Ajouter un Objectif", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Champs du formulaire
        JTextField titleField = new JTextField(30);
        JTextArea descArea = new JTextArea(3, 30);
        JComboBox<Objective.ObjectiveType> typeCombo = new JComboBox<>(Objective.ObjectiveType.values());
        JTextField targetValueField = new JTextField(10);
        JTextField assignedToField = new JTextField(20);
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Titre:"), gbc);
        gbc.gridx = 1;
        panel.add(titleField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Type:"), gbc);
        gbc.gridx = 1;
        panel.add(typeCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Valeur cible:"), gbc);
        gbc.gridx = 1;
        panel.add(targetValueField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Assigné à:"), gbc);
        gbc.gridx = 1;
        panel.add(assignedToField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        panel.add(new JScrollPane(descArea), gbc);
        
        JButton saveButton = new JButton("Enregistrer");
        saveButton.addActionListener(e -> {
            String id = "OBJ-" + System.currentTimeMillis();
            Objective obj = new Objective(id, titleField.getText(), 
                (Objective.ObjectiveType) typeCombo.getSelectedItem());
            obj.setDescription(descArea.getText());
            obj.setAssignedTo(assignedToField.getText());
            try {
                obj.setTargetValue(Double.parseDouble(targetValueField.getText()));
            } catch (NumberFormatException ex) {
                obj.setTargetValue(0);
            }
            obj.setTargetDate(LocalDate.now().plusDays(30));
            
            manager.addObjective(obj);
            JOptionPane.showMessageDialog(dialog, "Objectif ajouté avec succès!");
            dialog.dispose();
        });
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(saveButton, gbc);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    /**
     * Charge des données d'exemple pour la démonstration.
     */
    private void loadSampleData() {
        LocalDate today = LocalDate.now();
        
        // Créer un rapport journalier d'exemple
        DailyReport report = new DailyReport(today);
        report.setRevenue(45000); // 45,000 FCFA
        report.setExpenses(32000); // 32,000 FCFA
        
        // Ajouter des livraisons
        for (int i = 1; i <= 8; i++) {
            Delivery delivery = new Delivery("LIV-" + i, "Client " + i);
            delivery.setAmount(5000 + (i * 500));
            delivery.setDriverName(i <= 4 ? "Livreur A" : "Livreur B");
            delivery.setScheduledTime(LocalDateTime.now().minusHours(5).plusHours(i));
            
            if (i <= 6) {
                delivery.setActualDeliveryTime(delivery.getScheduledTime().minusMinutes(10));
                delivery.setStatus(Delivery.DeliveryStatus.LIVREE);
            } else if (i == 7) {
                delivery.setActualDeliveryTime(delivery.getScheduledTime().plusMinutes(45));
                delivery.setStatus(Delivery.DeliveryStatus.LIVREE);
            } else {
                delivery.setStatus(Delivery.DeliveryStatus.EN_COURS);
            }
            
            report.addDelivery(delivery);
        }
        
        // Ajouter des performances de livreurs
        DriverPerformance perf1 = new DriverPerformance("Livreur A", today);
        perf1.setDeliveriesCompleted(4);
        perf1.setDeliveriesOnTime(4);
        perf1.setTotalRevenue(22000);
        perf1.setFuelExpenses(8000);
        report.addDriverPerformance(perf1);
        
        DriverPerformance perf2 = new DriverPerformance("Livreur B", today);
        perf2.setDeliveriesCompleted(3);
        perf2.setDeliveriesOnTime(2);
        perf2.setDeliveriesLate(1);
        perf2.setTotalRevenue(23000);
        perf2.setFuelExpenses(9000);
        report.addDriverPerformance(perf2);
        
        manager.addDailyReport(report);
        
        // Ajouter des objectifs d'exemple
        Objective obj1 = new Objective("OBJ-1", "Atteindre 300 livraisons ce mois", 
            Objective.ObjectiveType.NOMBRE_LIVRAISONS);
        obj1.setTargetValue(300);
        obj1.setCurrentValue(180);
        obj1.setTargetDate(today.plusDays(10));
        obj1.setAssignedTo("Directrice");
        manager.addObjective(obj1);
        
        Objective obj2 = new Objective("OBJ-2", "Améliorer la ponctualité à 90%", 
            Objective.ObjectiveType.PONCTUALITE);
        obj2.setTargetValue(90);
        obj2.setCurrentValue(78);
        obj2.setTargetDate(today.plusDays(15));
        obj2.setAssignedTo("Responsable Logistique");
        manager.addObjective(obj2);
        
        Objective obj3 = new Objective("OBJ-3", "Augmenter le CA mensuel à 1,500,000 FCFA", 
            Objective.ObjectiveType.CHIFFRE_AFFAIRES);
        obj3.setTargetValue(1500000);
        obj3.setCurrentValue(890000);
        obj3.setTargetDate(today.plusDays(20));
        obj3.setAssignedTo("Commerciale");
        manager.addObjective(obj3);
    }
}
