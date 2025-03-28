package fr.cylc.owlLib.utils;

import fr.cylc.owlLib.OwlLib;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * Gestionnaire de configuration pour OwlLib
 */
public class ConfigManager {

    private final OwlLib plugin;
    private FileConfiguration mainConfig;
    private final Map<String, FileConfiguration> customConfigs;

    public ConfigManager(OwlLib plugin) {
        this.plugin = plugin;
        this.mainConfig = plugin.getConfig();
        this.customConfigs = new HashMap<>();
    }

    /**
     * Charge une configuration personnalisée depuis un fichier
     */
    public FileConfiguration loadCustomConfig(String fileName) {
        File configFile = new File(plugin.getDataFolder(), fileName);

        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource(fileName, false);
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        customConfigs.put(fileName, config);
        return config;
    }

    /**
     * Sauvegarde une configuration personnalisée
     */
    public void saveCustomConfig(String fileName) {
        if (!customConfigs.containsKey(fileName)) {
            plugin.getLogger().warning("Cannot save config '" + fileName + "' because it was not loaded");
            return;
        }

        try {
            File configFile = new File(plugin.getDataFolder(), fileName);
            customConfigs.get(fileName).save(configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config '" + fileName + "'", e);
        }
    }

    /**
     * Recharge la configuration principale
     */
    public void reloadMainConfig() {
        plugin.reloadConfig();
        this.mainConfig = plugin.getConfig();
    }

    /**
     * Récupère une configuration personnalisée par son nom
     */
    public FileConfiguration getCustomConfig(String fileName) {
        return customConfigs.get(fileName);
    }

    /**
     * Récupère la configuration principale
     */
    public FileConfiguration getMainConfig() {
        return mainConfig;
    }

    /**
     * Récupère une valeur chaîne de caractères depuis la configuration principale
     */
    public String getStringValue(String path, String defaultValue) {
        return mainConfig.getString(path, defaultValue);
    }

    /**
     * Récupère une valeur int depuis la configuration principale
     */
    public int getIntValue(String path, int defaultValue) {
        return mainConfig.getInt(path, defaultValue);
    }

    /**
     * Récupère une valeur double depuis la configuration principale
     */
    public double getDoubleValue(String path, double defaultValue) {
        return mainConfig.getDouble(path, defaultValue);
    }

    /**
     * Récupère une valeur booléenne depuis la configuration principale
     */
    public boolean getBooleanValue(String path, boolean defaultValue) {
        return mainConfig.getBoolean(path, defaultValue);
    }

    /**
     * Récupère une valeur chaîne de caractères depuis une configuration personnalisée
     */
    public String getStringValue(String fileName, String path, String defaultValue) {
        FileConfiguration config = customConfigs.get(fileName);
        if (config == null) {
            return defaultValue;
        }
        return config.getString(path, defaultValue);
    }

    /**
     * Définit une valeur dans la configuration principale
     */
    public void setValue(String path, Object value) {
        mainConfig.set(path, value);
        plugin.saveConfig();
    }

    /**
     * Définit une valeur dans une configuration personnalisée
     */
    public void setValue(String fileName, String path, Object value) {
        FileConfiguration config = customConfigs.get(fileName);
        if (config == null) {
            plugin.getLogger().warning("Cannot set value in config '" + fileName + "' because it was not loaded");
            return;
        }

        config.set(path, value);
        saveCustomConfig(fileName);
    }
}