package fr.cylc.owlLib.utils;

import org.bukkit.Bukkit;

/**
 * Utilitaire pour la vérification des versions
 */
public class VersionUtil {

    private VersionUtil() {
        // Constructeur privé pour empêcher l'instanciation
    }

    /**
     * Vérifie si la version du serveur est supérieure ou égale à la version spécifiée
     */
    public static boolean isServerVersionAtLeast(String version) {
        String serverVersion = Bukkit.getBukkitVersion();
        return compareVersions(serverVersion, version) >= 0;
    }

    /**
     * Compare deux versions sémantiques
     * @return -1 si v1 < v2, 0 si v1 == v2, 1 si v1 > v2
     */
    public static int compareVersions(String v1, String v2) {
        // Nettoie les versions pour extraire seulement les numéros
        v1 = cleanVersion(v1);
        v2 = cleanVersion(v2);

        String[] parts1 = v1.split("\\.");
        String[] parts2 = v2.split("\\.");

        int length = Math.max(parts1.length, parts2.length);

        for (int i = 0; i < length; i++) {
            int p1 = (i < parts1.length) ? Integer.parseInt(parts1[i]) : 0;
            int p2 = (i < parts2.length) ? Integer.parseInt(parts2[i]) : 0;

            if (p1 < p2) {
                return -1;
            }
            if (p1 > p2) {
                return 1;
            }
        }

        return 0; // Versions égales
    }

    /**
     * Nettoie une chaîne de version pour ne garder que les numéros
     */
    private static String cleanVersion(String version) {
        // Supprime tout ce qui n'est pas un chiffre ou un point
        return version.replaceAll("[^0-9.]", "");
    }

    /**
     * Vérifie si un plugin est présent et activé
     */
    public static boolean isPluginEnabled(String pluginName) {
        return Bukkit.getPluginManager().getPlugin(pluginName) != null &&
                Bukkit.getPluginManager().isPluginEnabled(pluginName);
    }

    /**
     * Récupère la version d'un plugin
     */
    public static String getPluginVersion(String pluginName) {
        if (isPluginEnabled(pluginName)) {
            return Bukkit.getPluginManager().getPlugin(pluginName).getDescription().getVersion();
        }
        return null;
    }
}
