package fr.cylc.owlLib.api;

import java.util.List;

/**
 * Interface pour définir des commandes dans le système
 * Utilise le pattern Command
 */
public interface IOwlCommand extends IOwlComponent {

    /**
     * Exécute la commande avec les paramètres spécifiés
     * @param params Les paramètres de la commande
     * @return Le résultat de l'exécution
     */
    CommandResult execute(String... params);

    /**
     * Vérifie si la commande peut être exécutée avec les paramètres spécifiés
     * @param params Les paramètres à vérifier
     * @return true si la commande peut être exécutée, false sinon
     */
    boolean canExecute(String... params);

    /**
     * Renvoie l'aide d'utilisation de la commande
     */
    String getUsage();

    /**
     * Renvoie la description de la commande
     */
    String getDescription();

    /**
     * Fournit l'auto-complétion pour cette commande
     * @param currentInput L'entrée actuelle de l'utilisateur
     * @return Une liste de suggestions d'auto-complétion
     */
    List<String> getTabCompletions(String currentInput);

    /**
     * Classe pour encapsuler le résultat d'une commande
     */
    class CommandResult {
        private final boolean success;
        private final String message;

        public CommandResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public static CommandResult success(String message) {
            return new CommandResult(true, message);
        }

        public static CommandResult failure(String message) {
            return new CommandResult(false, message);
        }
    }
}