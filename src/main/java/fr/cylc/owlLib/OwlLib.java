package fr.cylc.owlLib;

import fr.cylc.owlLib.core.OwlComponentRegistry;
import fr.cylc.owlLib.core.OwlEventBus;
import fr.cylc.owlLib.core.OwlServiceLocator;
import fr.cylc.owlLib.utils.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Classe principale du plugin OwlLib
 * Fournit une API et des services réutilisables pour d'autres plugins
 */
public final class OwlLib extends JavaPlugin {

    private static OwlLib instance;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        instance = this;

        // Initialisation du fichier de configuration
        saveDefaultConfig();
        this.configManager = new ConfigManager(this);

        // Initialisation des composants core
        OwlComponentRegistry.initialize();
        OwlEventBus.initialize();
        OwlServiceLocator.initialize();

        // Enregistrer les commandes (si nécessaire)
        registerCommands();

        getLogger().info("OwlLib has been enabled! Version: " + getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        // Nettoyage des ressources
        OwlComponentRegistry.shutdown();
        OwlEventBus.shutdown();
        OwlServiceLocator.shutdown();

        getLogger().info("OwlLib has been disabled!");
        instance = null;
    }

    /**
     * Permet aux autres plugins d'accéder à l'instance de OwlLib
     */
    public static OwlLib getInstance() {
        return instance;
    }

    /**
     * Enregistre les commandes fournies par OwlLib (si nécessaire)
     */
    private void registerCommands() {
        // Si des commandes sont nécessaires, elles seraient enregistrées ici
    }

    /**
     * Permet aux autres plugins d'accéder au gestionnaire de configuration
     */
    public ConfigManager getConfigManager() {
        return configManager;
    }
}
