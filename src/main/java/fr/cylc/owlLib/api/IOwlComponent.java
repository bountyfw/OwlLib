package fr.cylc.owlLib.api;

/**
 * Interface de base pour tous les composants du système
 * Respecte le principe d'Interface Segregation (I de SOLID)
 */
public interface IOwlComponent {

    /**
     * Renvoie l'identifiant unique du composant
     */
    String getId();

    /**
     * Initialise le composant
     */
    void initialize();

    /**
     * Arrête proprement le composant et libère les ressources
     */
    void shutdown();

    /**
     * Vérifie si le composant est initialisé
     */
    boolean isInitialized();
}