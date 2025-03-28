package fr.cylc.owlLib.exceptions;

/**
 * Exception personnalisée pour OwlLib
 */
public class OwlException extends Exception {

    public OwlException(String message) {
        super(message);
    }

    public OwlException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception levée lorsqu'un composant n'est pas trouvé
     */
    public static class ComponentNotFoundException extends OwlException {
        public ComponentNotFoundException(String componentId) {
            super("Component not found: " + componentId);
        }
    }

    /**
     * Exception levée lorsqu'un service n'est pas trouvé
     */
    public static class ServiceNotFoundException extends OwlException {
        public ServiceNotFoundException(String serviceId) {
            super("Service not found: " + serviceId);
        }
    }

    /**
     * Exception levée lorsqu'une configuration est invalide
     */
    public static class ConfigurationException extends OwlException {
        public ConfigurationException(String message) {
            super("Configuration error: " + message);
        }
    }

    /**
     * Exception levée lorsqu'une opération est effectuée sur un composant non initialisé
     */
    public static class NotInitializedException extends OwlException {
        public NotInitializedException(String componentId) {
            super("Component not initialized: " + componentId);
        }
    }
}
