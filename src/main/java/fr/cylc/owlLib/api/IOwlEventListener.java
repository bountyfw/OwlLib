package fr.cylc.owlLib.api;

/**
 * Interface pour les composants qui écoutent des événements
 * Implémente le pattern Observer
 */
public interface IOwlEventListener<T> {

    /**
     * Méthode appelée lorsqu'un événement est déclenché
     * @param event L'événement déclenché
     */
    void onEvent(T event);

    /**
     * Renvoie le type d'événement que ce listener gère
     * @return La classe de l'événement
     */
    Class<T> getEventType();
}