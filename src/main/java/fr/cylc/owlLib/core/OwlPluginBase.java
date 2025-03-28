package fr.cylc.owlLib.core;

import fr.cylc.owlLib.OwlLib;
import fr.cylc.owlLib.api.IOwlComponent;
import fr.cylc.owlLib.api.IOwlConfigurable;
import fr.cylc.owlLib.utils.ConfigManager;

import java.util.List;
import java.util.logging.Logger;

/**
 * Classe abstraite qui sert de base pour tous les plugins
 * Respecte le principe Open/Closed (O de SOLID)
 */
public abstract class OwlPluginBase implements IOwlComponent, IOwlConfigurable {

    protected boolean initialized = false;
    protected final Logger logger;
    protected ConfigManager configManager;

    public OwlPluginBase() {
        this.logger = OwlLib.getInstance().getLogger();
        this.configManager = OwlLib.getInstance().getConfigManager();
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    /**
     * Méthode à implémenter par les plugins concrets pour fournir leurs composants
     */
    public abstract List<IOwlComponent> getComponents();

    /**
     * Template method pattern pour l'initialisation standard
     */
    @Override
    public void initialize() {
        if (initialized) {
            return;
        }

        // Logique d'initialisation commune
        try {
            loadConfig(null);  // Utilise le chemin par défaut

            // Initialise et enregistre les composants
            List<IOwlComponent> components = getComponents();
            for (IOwlComponent component : components) {
                component.initialize();
                OwlComponentRegistry.register(component);
            }

            initialized = true;
            logger.info("Plugin '" + getId() + "' initialized successfully");
        } catch (Exception e) {
            logger.severe("Failed to initialize plugin '" + getId() + "': " + e.getMessage());
        }
    }

    /**
     * Méthode à implémenter par les plugins concrets pour charger leur configuration
     */
    protected abstract void loadConfig();

    @Override
    public boolean loadConfig(String configPath) {
        try {
            loadConfig();
            return true;
        } catch (Exception e) {
            logger.severe("Failed to load configuration for plugin '" + getId() + "': " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean saveConfig() {
        // Par défaut, ne fait rien. Les plugins concrets peuvent surcharger cette méthode.
        return true;
    }

    @Override
    public void resetToDefaultConfig() {
        // Par défaut, ne fait rien. Les plugins concrets peuvent surcharger cette méthode.
    }

    @Override
    public boolean validateConfig() {
        // Par défaut, considère la configuration comme valide.
        return true;
    }

    @Override
    public void shutdown() {
        if (!initialized) {
            return;
        }

        try {
            // Arrête les composants dans l'ordre inverse de leur initialisation
            List<IOwlComponent> components = getComponents();
            for (int i = components.size() - 1; i >= 0; i--) {
                IOwlComponent component = components.get(i);
                component.shutdown();
            }

            initialized = false;
            logger.info("Plugin '" + getId() + "' shut down successfully");
        } catch (Exception e) {
            logger.severe("Error during shutdown of plugin '" + getId() + "': " + e.getMessage());
        }
    }
}
