package fr.cylc.owlLib.core;

import fr.cylc.owlLib.OwlLib;
import fr.cylc.owlLib.api.IOwlService;
import fr.cylc.owlLib.exceptions.OwlException;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Localisateur de services pour OwlLib
 * Implémente le pattern Service Locator
 */
public class OwlServiceLocator {

    private static Map<Class<?>, IOwlService> services;
    private static Map<String, IOwlService> servicesByName;
    private static Logger logger;

    private OwlServiceLocator() {
        // Constructeur privé pour empêcher l'instanciation
    }

    /**
     * Initialise le localisateur de services
     */
    public static void initialize() {
        services = new HashMap<>();
        servicesByName = new HashMap<>();
        logger = OwlLib.getInstance().getLogger();
        logger.info("Service Locator initialized");
    }

    /**
     * Enregistre un service par son type
     */
    public static <T extends IOwlService> void registerService(Class<T> serviceType, T serviceImpl) {
        if (services == null) {
            throw new IllegalStateException("Service Locator not initialized");
        }

        services.put(serviceType, serviceImpl);
        servicesByName.put(serviceImpl.getId(), serviceImpl);
        logger.info("Service '" + serviceImpl.getServiceName() + "' registered");
    }

    /**
     * Récupère un service par son type
     */
    @SuppressWarnings("unchecked")
    public static <T extends IOwlService> T getService(Class<T> serviceType) throws OwlException {
        if (services == null) {
            throw new IllegalStateException("Service Locator not initialized");
        }

        T service = (T) services.get(serviceType);
        if (service == null) {
            throw new OwlException("Service of type " + serviceType.getName() + " not found");
        }
        return service;
    }

    /**
     * Récupère un service par son ID
     */
    @SuppressWarnings("unchecked")
    public static <T extends IOwlService> T getServiceById(String id) throws OwlException {
        if (servicesByName == null) {
            throw new IllegalStateException("Service Locator not initialized");
        }

        T service = (T) servicesByName.get(id);
        if (service == null) {
            throw new OwlException("Service with ID '" + id + "' not found");
        }
        return service;
    }

    /**
     * Désenregistre un service
     */
    public static <T extends IOwlService> void unregisterService(Class<T> serviceType) {
        if (services == null) {
            throw new IllegalStateException("Service Locator not initialized");
        }

        IOwlService service = services.remove(serviceType);
        if (service != null) {
            servicesByName.remove(service.getId());
            logger.info("Service '" + service.getServiceName() + "' unregistered");
        }
    }

    /**
     * Libère les ressources utilisées par le localisateur de services
     */
    public static void shutdown() {
        if (services != null) {
            // Arrête tous les services enregistrés
            for (IOwlService service : services.values()) {
                try {
                    service.shutdown();
                } catch (Exception e) {
                    logger.warning("Error shutting down service '" + service.getServiceName() + "': " + e.getMessage());
                }
            }

            services.clear();
            services = null;
            servicesByName.clear();
            servicesByName = null;
            logger.info("Service Locator shut down");
        }
    }
}
