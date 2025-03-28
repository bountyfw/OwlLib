package fr.cylc.owlLib.api;

/**
 * Interface pour définir un service dans le système
 * Étend IOwlComponent pour respecter le Liskov Substitution Principle (L de SOLID)
 */
public interface IOwlService extends IOwlComponent {

    /**
     * Renvoie le nom du service
     */
    String getServiceName();

    /**
     * Renvoie l'état actuel du service
     */
    ServiceStatus getStatus();

    /**
     * Les statuts possibles d'un service
     */
    enum ServiceStatus {
        STOPPED, RUNNING, ERROR, PAUSED
    }
}