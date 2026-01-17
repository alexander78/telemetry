package de.soprasteria.saver;

import de.soprasteria.saver.ui.MainFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Point d'entrée de l'application Assistant de Gestion ABC INTER.
 * 
 * Cette application est un assistant de gestion intelligent spécialisé dans 
 * l'accompagnement des dirigeants de PME de livraison de plis et colis,
 * comme ABC INTER à Douala (Cameroun).
 */
public class Starter {

	public static void main(String[] args) {
		// Utiliser le look and feel du système pour une meilleure intégration
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// Si l'application du look and feel échoue, continuer avec le défaut
			System.err.println("Impossible d'appliquer le look and feel du système: " + e.getMessage());
		}
		
		// Lancer l'interface graphique dans le thread EDT (Event Dispatch Thread)
		SwingUtilities.invokeLater(() -> {
			MainFrame frame = new MainFrame();
			frame.setVisible(true);
			System.out.println("═══════════════════════════════════════════════════════════");
			System.out.println("Assistant de Gestion ABC INTER démarré avec succès!");
			System.out.println("═══════════════════════════════════════════════════════════");
		});
	}

}
