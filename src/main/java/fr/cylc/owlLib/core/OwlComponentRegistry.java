package fr.cylc.owlLib.core;

import fr.cylc.owlLib.OwlLib;
import fr.cylc.owlLib.api.IOwlComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Registre des composants pour OwlLib
 * Implémente le pattern Registry
 */
public class OwlComponentRegistry {

    private static Map<String, IOwlComponent> components;
    private static Logger logger;

    private OwlComponentRegistry() {
        // Constructeur privé pour empêcher l'instanciation
    }

    /**
     * Initialise le registre
     */
    public static void initialize() {
        components = new HashMap<>();
        logger = OwlLib.getInstance().getLogger();
        logger.info("Component Registry initialized");
    }

    /**
     * Enregistre un nouveau composant
     */
    public static void register(IOwlComponent component) {
        if (components == null) {
            throw new IllegalStateException("Component Registry not initialized");
        }

        String id = component.getId();
        if (components.containsKey(id)) {
            logger.warning("Component with id '" + id + "' already registered. Overwriting.");
        }

        components.put(id, component);
        logger.info("Component '" + id + "' registered successfully");
    }

    /**
     * Récupère un composant par son ID
     */
    @SuppressWarnings("unchecked")
    public static <T extends IOwlComponent> T getComponent(String id) {
        if (components == null) {
            throw new IllegalStateException("Component Registry not initialized");
        }

        return (T) components.get(id);
    }

    /**
     * Libère les ressources utilisées par le registre
     */
    public static void shutdown() {
        if (components != null) {
            // Arrête tous les composants enregistrés
            for (IOwlComponent component : components.values()) {
                try {
                    component.shutdown();
                } catch (Exception e) {
                    logger.warning("Error shutting down component '" + component.getId() + "': " + e.getMessage());
                }
            }

            components.clear();
            components = null;
            logger.info("Component Registry shut down");
        }
    }

    /**
     * Vérifie si un composant est enregistré
     */
    public static boolean hasComponent(String id) {
        if (components == null) {
            return false;
        }
        return components.containsKey(id);
    }

    /**
     * Renvoie le nombre de composants enregistrés
     */
    public static int getComponentCount() {
        if (components == null) {
            return 0;
        }
        return components.size();
    }
}