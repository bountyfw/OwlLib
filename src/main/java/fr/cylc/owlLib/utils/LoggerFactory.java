package fr.cylc.owlLib.utils;

import fr.cylc.owlLib.OwlLib;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Factory pour créer des instances de logger
 */
public class LoggerFactory {

    private static final Map<String, OwlLogger> loggers = new HashMap<>();

    private LoggerFactory() {
        // Constructeur privé pour empêcher l'instanciation
    }

    /**
     * Récupère ou crée un logger pour le composant spécifié
     */
    public static OwlLogger getLogger(String componentName) {
        return loggers.computeIfAbsent(componentName, name -> new OwlLogger(name));
    }

    /**
     * Wrapper autour du logger Java standard pour ajouter des fonctionnalités
     */
    public static class OwlLogger {
        private final String componentName;
        private final Logger logger;

        private OwlLogger(String componentName) {
            this.componentName = componentName;
            this.logger = OwlLib.getInstance().getLogger();
        }

        public void info(String message) {
            logger.info("[" + componentName + "] " + message);
        }

        public void warning(String message) {
            logger.warning("[" + componentName + "] " + message);
        }

        public void severe(String message) {
            logger.severe("[" + componentName + "] " + message);
        }

        public void debug(String message) {
            // Ne log que si le mode debug est activé
            if (OwlLib.getInstance().getConfigManager().getBooleanValue("debug", false)) {
                logger.info("[" + componentName + "][DEBUG] " + message);
            }
        }

        public void error(String message, Throwable throwable) {
            logger.severe("[" + componentName + "] " + message);
            if (throwable != null && OwlLib.getInstance().getConfigManager().getBooleanValue("debug", false)) {
                throwable.printStackTrace();
            }
        }
    }
}