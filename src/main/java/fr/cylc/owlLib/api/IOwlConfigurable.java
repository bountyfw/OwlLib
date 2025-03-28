package fr.cylc.owlLib.api;

/**
 * Interface pour les composants qui peuvent être configurés
 * Respecte le principe d'Interface Segregation (I de SOLID)
 */
public interface IOwlConfigurable {

    /**
     * Charge la configuration depuis une source externe
     * @param configPath Chemin vers la configuration
     * @return true si le chargement a réussi, false sinon
     */
    boolean loadConfig(String configPath);

    /**
     * Sauvegarde la configuration courante
     * @return true si la sauvegarde a réussi, false sinon
     */
    boolean saveConfig();

    /**
     * Réinitialise la configuration aux valeurs par défaut
     */
    void resetToDefaultConfig();

    /**
     * Vérifie si la configuration est valide
     * @return true si la configuration est valide, false sinon
     */
    boolean validateConfig();
}