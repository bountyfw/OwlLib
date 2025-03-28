package fr.cylc.owlLib.core;

import fr.cylc.owlLib.OwlLib;
import fr.cylc.owlLib.api.IOwlEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Bus d'événements pour OwlLib
 * Implémente le pattern Observer avec un bus d'événements
 */
public class OwlEventBus {

    private static Map<Class<?>, List<IOwlEventListener<?>>> listeners;
    private static ExecutorService eventExecutor;
    private static Logger logger;

    private OwlEventBus() {
        // Constructeur privé pour empêcher l'instanciation
    }

    /**
     * Initialise le bus d'événements
     */
    public static void initialize() {
        listeners = new HashMap<>();
        eventExecutor = Executors.newFixedThreadPool(2);
        logger = OwlLib.getInstance().getLogger();
        logger.info("Event Bus initialized");
    }

    /**
     * Enregistre un listener pour un type d'événement
     */
    public static <T> void register(IOwlEventListener<T> listener) {
        if (listeners == null) {
            throw new IllegalStateException("Event Bus not initialized");
        }

        Class<T> eventType = listener.getEventType();
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
        logger.info("Listener registered for event type: " + eventType.getSimpleName());
    }

    /**
     * Désenregistre un listener
     */
    public static <T> void unregister(IOwlEventListener<T> listener) {
        if (listeners == null) {
            throw new IllegalStateException("Event Bus not initialized");
        }

        Class<T> eventType = listener.getEventType();
        if (listeners.containsKey(eventType)) {
            listeners.get(eventType).remove(listener);
            logger.info("Listener unregistered for event type: " + eventType.getSimpleName());
        }
    }

    /**
     * Publie un événement à tous les listeners concernés
     */
    @SuppressWarnings("unchecked")
    public static <T> void publish(T event) {
        if (listeners == null) {
            throw new IllegalStateException("Event Bus not initialized");
        }

        Class<?> eventType = event.getClass();
        if (listeners.containsKey(eventType)) {
            for (IOwlEventListener<?> listener : listeners.get(eventType)) {
                publishToListener((IOwlEventListener<T>) listener, event);
            }
        }
    }

    /**
     * Publie un événement de manière asynchrone
     */
    private static <T> void publishToListener(IOwlEventListener<T> listener, T event) {
        eventExecutor.submit(() -> {
            try {
                listener.onEvent(event);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error in event listener", e);
            }
        });
    }

    /**
     * Libère les ressources utilisées par le bus d'événements
     */
    public static void shutdown() {
        if (eventExecutor != null) {
            eventExecutor.shutdown();
            eventExecutor = null;
        }

        if (listeners != null) {
            listeners.clear();
            listeners = null;
        }

        logger.info("Event Bus shut down");
    }
}
