package com.biblio.errors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Classe utilitaire pour stocker et gérer les erreurs globales de l'application.
 */
public class BErrorMgr {

    // Liste statique pour stocker les erreurs
    public static List<HashMap<String, String>> errors = new ArrayList<>();


    /**
     * Ajoute une erreur à la liste.
     * @param errorMessage Message d'erreur à ajouter
     */
    public static void addError(String key, String errorMessage) {
        if (errorMessage != null && !errorMessage.trim().isEmpty()) {

            HashMap<String, String> error = new HashMap<>();
            error.put(key, errorMessage.trim());
            
            errors.add(error);
        }
    }

    /**
     * Récupère le message d'erreur associé à une clé spécifique.
     * @param key Clé de l'erreur
     * @return Message d'erreur ou null si la clé n'existe pas
     */
    public static String get(String key) {
        StringBuilder errorMessage = new StringBuilder();
        for (HashMap<String, String> error : errors) {
            if (error.containsKey(key)) {
                errorMessage.append(key).append(" : ").append(error.get(key)).append("\n");
                clear(key);
                return errorMessage.toString();
            }
        }
        return null;
    }

    public static void clear(String key) {
        errors.removeIf(error -> error.containsKey(key));
    }

    /**
     * Retourne une copie non modifiable de la liste des erreurs.
     */
    public static List<HashMap<String, String>> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    /**
     * Retourne un message concaténé de toutes les erreurs.
     */
    public static String getErrorsMessage() {
        StringBuilder errorMessages = new StringBuilder();
        for (HashMap<String, String> error : errors) {
            for (String key : error.keySet()) {
                errorMessages.append(key).append(" : ").append(error.get(key)).append("\n");
            }
        }

        errors.clear();
        return errorMessages.toString();
    }

    /**
     * Efface toutes les erreurs.
     */
    public static void clearErrors() {
        errors.clear();
    }

    /**
     * Vérifie si des erreurs existent.
    */
    public static boolean hasErrors() {
        return !errors.isEmpty();
    }
}
