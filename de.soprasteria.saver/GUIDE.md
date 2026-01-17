# Guide d'Utilisation - Assistant de Gestion ABC INTER

## 📦 Installation et Exécution

### Pré-requis
- Java JDK 11 ou supérieur installé
- Variable d'environnement JAVA_HOME configurée

### Méthode 1: Scripts de Build (Recommandé)

**Sur Linux/Mac:**
```bash
cd de.soprasteria.saver
./run.sh
```

**Sur Windows:**
```cmd
cd de.soprasteria.saver
run.bat
```

### Méthode 2: Compilation Manuelle

```bash
cd de.soprasteria.saver/src
javac de/soprasteria/saver/Starter.java de/soprasteria/saver/**/*.java
java de.soprasteria.saver.Starter
```

### Méthode 3: Exécuter les Tests

```bash
cd de.soprasteria.saver
javac -cp src:test -d build test/SimpleTest.java src/de/soprasteria/saver/**/*.java
cd build
java de.soprasteria.saver.test.SimpleTest
```

## 🎯 Fonctionnalités Principales

### 1. Analyse Journalière

L'assistant analyse les rapports journaliers et fournit:

**Indicateurs Clés:**
- Chiffre d'affaires (FCFA)
- Dépenses (FCFA)
- Bénéfice et marge bénéficiaire
- Nombre de livraisons
- Taux de ponctualité global
- CA moyen par livraison

**Analyses Critiques:**
- ⚠️ Problèmes critiques (perte, ponctualité inacceptable)
- ⚡ Points d'attention (marges faibles, volume insuffisant)
- ✅ Points positifs (performances excellentes)

**Recommandations Priorisées:**
- [URGENT] Actions à réaliser immédiatement
- [IMPORTANT] Actions importantes à planifier
- [SOUHAITABLE] Améliorations à long terme

**Exemple de Rapport:**

```
═══════════════════════════════════════════════════════════
    ANALYSE JOURNALIÈRE - ABC INTER DOUALA
═══════════════════════════════════════════════════════════

📊 INDICATEURS CLÉS:
   Chiffre d'affaires: 45000 FCFA
   Dépenses: 32000 FCFA
   Bénéfice: 13000 FCFA
   Marge bénéficiaire: 28.9%
   Nombre de livraisons: 8
   Taux de ponctualité global: 87.5%

✅ POINTS POSITIFS:
   ✓ Bonne rentabilité avec une marge de 28.9%
   ✓ Excellente ponctualité: 87.5%

⚡ POINTS D'ATTENTION:
   Volume de livraisons moyen. Objectif: 20 livraisons/jour.

👥 PERFORMANCE DES LIVREURS:
   • Livreur A: 4 livraisons, 100.0% ponctualité
   • Livreur B: 3 livraisons, 66.7% ponctualité

💡 RECOMMANDATIONS PAR ORDRE DE PRIORITÉ:
   [IMPORTANT] Développer des partenariats avec des e-commerces locaux.
   [IMPORTANT] Former Livreur B sur l'optimisation des trajets
```

### 2. Suivi des Objectifs

Gestion complète des objectifs commerciaux et opérationnels:

**Types d'Objectifs:**
- CHIFFRE_AFFAIRES: Objectifs de revenus
- NOMBRE_LIVRAISONS: Objectifs de volume
- PONCTUALITE: Objectifs de qualité de service
- FIDELISATION: Objectifs de rétention client
- REDUCTION_COUTS: Objectifs d'optimisation
- NOTORIETE: Objectifs marketing
- FORMATION: Objectifs RH

**Statuts:**
- EN_COURS: Objectif en cours de réalisation
- COMPLETE: Objectif atteint
- EN_RETARD: Objectif en retard
- ABANDONNE: Objectif abandonné (nécessite justification)

**Exemple de Suivi:**

```
═══════════════════════════════════════════════════════════
    SUIVI DES OBJECTIFS - ABC INTER
═══════════════════════════════════════════════════════════

🚨 OBJECTIFS EN RETARD (1):
   ⚠️ Augmenter le CA mensuel - 59.3% complété
      (échéance: 2026-01-15)

📋 OBJECTIFS EN COURS (2):
   • Atteindre 300 livraisons ce mois
     [██████░░░░] 60.0%
     Échéance: 2026-02-01
   
   • Améliorer la ponctualité à 90%
     [████████░░] 86.7%
     Échéance: 2026-02-05

✅ OBJECTIFS COMPLÉTÉS (1):
   ✓ Formation WhatsApp Business (complété le 2026-01-10)

❌ OBJECTIFS ABANDONNÉS (0):

⚠️ ATTENTION: Vérifier la justification de chaque abandon.
```

### 3. Synthèse Mensuelle

Vue d'ensemble des performances mensuelles:

**Indicateurs:**
- Nombre de jours d'activité
- Total et moyenne des livraisons
- Chiffre d'affaires total
- Dépenses totales
- Bénéfice et marge
- Taux de ponctualité moyen

**Exemple:**

```
═══════════════════════════════════════════════════════════
    SYNTHÈSE MENSUELLE - 1/2026
═══════════════════════════════════════════════════════════

📊 INDICATEURS DU MOIS:
   • Nombre de jours d'activité: 20
   • Total livraisons: 245
   • Moyenne par jour: 12.3
   • Chiffre d'affaires: 1,225,000 FCFA
   • Dépenses: 856,000 FCFA
   • Bénéfice: 369,000 FCFA
   • Marge: 30.1%
   • Taux de ponctualité moyen: 84.5%

💡 RECOMMANDATIONS POUR LE MOIS PROCHAIN:
   • Maintenir le bon niveau de rentabilité
   • Améliorer la ponctualité des livraisons
   • Augmenter légèrement le volume pour atteindre 15 livraisons/jour
```

## 🔍 Architecture du Code

```
de.soprasteria.saver/
│
├── model/                      # Modèles de données
│   ├── DailyReport.java       # Rapport journalier
│   ├── Delivery.java          # Livraison individuelle
│   ├── DriverPerformance.java # Performance d'un livreur
│   └── Objective.java         # Objectif/tâche
│
├── analyzer/                   # Moteur d'analyse
│   └── ReportAnalyzer.java    # Analyse et recommandations
│
├── manager/                    # Gestion métier
│   └── BusinessManager.java   # Gestionnaire principal
│
├── ui/                         # Interface utilisateur
│   └── MainFrame.java         # Interface graphique Swing
│
├── Starter.java               # Point d'entrée
│
├── test/                      # Tests
│   └── SimpleTest.java        # Tests unitaires
│
├── run.sh                     # Script de build Linux/Mac
└── run.bat                    # Script de build Windows
```

## 💡 Utilisation de l'Interface Graphique

### Onglet 1: Analyse Journalière
1. Cliquez sur "📊 Analyser le rapport d'aujourd'hui"
2. Consultez les indicateurs, problèmes et recommandations
3. Utilisez "🗑️ Effacer" pour nettoyer l'affichage

### Onglet 2: Suivi des Objectifs
1. Cliquez sur "📋 Afficher les objectifs" pour voir tous les objectifs
2. Cliquez sur "➕ Ajouter un objectif" pour créer un nouvel objectif
3. Remplissez le formulaire et cliquez sur "Enregistrer"

### Onglet 3: Synthèse Mensuelle
1. Sélectionnez le mois et l'année
2. Cliquez sur "📈 Générer la synthèse"
3. Consultez les statistiques mensuelles

### Onglet 4: À propos
- Documentation complète de l'application
- Fonctionnalités et utilisation
- Contexte et objectifs

## 🎨 Personnalisation

### Modifier les Seuils d'Alerte

Dans `ReportAnalyzer.java`, vous pouvez ajuster:
```java
private static final double MIN_ON_TIME_RATE = 85.0;      // Ponctualité minimum
private static final double MIN_PROFIT_MARGIN = 0.20;      // Marge minimum 20%
private static final int MIN_DAILY_DELIVERIES = 10;        // Livraisons minimum/jour
```

### Ajouter de Nouveaux Types d'Objectifs

Dans `Objective.java`, ajoutez dans l'enum:
```java
public enum ObjectiveType {
    CHIFFRE_AFFAIRES,
    NOMBRE_LIVRAISONS,
    // ... existants
    NOUVEAU_TYPE  // Votre nouveau type
}
```

### Personnaliser les Recommandations

Modifiez la méthode `analyze()` dans `ReportAnalyzer.java` pour ajouter vos propres règles métier.

## 📊 Données d'Exemple

L'application charge automatiquement des données d'exemple au démarrage:
- 1 rapport journalier avec 8 livraisons
- 2 performances de livreurs
- 3 objectifs (2 en cours, 1 complété)

Ces données permettent de tester immédiatement toutes les fonctionnalités.

## 🔒 Sécurité et Confidentialité

- Les données sont stockées en mémoire uniquement (pas de base de données)
- Aucune connexion réseau requise
- Les données sont perdues à la fermeture de l'application
- Pour une utilisation en production, implémenter une couche de persistance

## 🚀 Évolutions Futures Possibles

1. **Persistance des données**: Base de données SQLite ou fichiers JSON
2. **Import/Export**: CSV, Excel pour les rapports
3. **Graphiques**: Visualisations avec JFreeChart
4. **Multi-utilisateurs**: Authentification et droits d'accès
5. **API REST**: Interface web pour accès mobile
6. **Notifications**: Alertes WhatsApp Business pour les problèmes critiques
7. **Intégration GPS**: Suivi en temps réel des livreurs
8. **IA prédictive**: Prévisions de demande et optimisation automatique

## 📞 Support

Pour toute question ou suggestion d'amélioration:
- Email: support@abcinter.cm
- Téléphone: +237 XXX XXX XXX
- Adresse: Douala, Cameroun

---

**Version**: 1.0  
**Date**: Janvier 2026  
**Développé pour**: ABC INTER - Douala, Cameroun
