package com.biblio.analytic;

public final class AnalyticsService {

    private AnalyticsService() {
    }

    /**
     * Envoie un événement personnalisé.
     */
    public static void track(String eventType) {

        AnalyticsEvent event = new AnalyticsEvent();

        // Type d'événement
        event.setEvent(eventType);

        // Informations de l'application
        event.setAppName(AppInfo.getAppName());
        event.setAppVersion(AppInfo.getVersion());
        event.setChannel(AppInfo.getChannel());

        // Informations du poste
        event.setDeviceId(DeviceManager.getDeviceId());

        event.setOs(AppInfo.getOSName());
        event.setOsVersion(AppInfo.getOSVersion());
        event.setArchitecture(AppInfo.getArchitecture());

        event.setJavaVersion(AppInfo.getJavaVersion());

        event.setUsername(AppInfo.getUsername());
        event.setComputerName(AppInfo.getComputerName());

        // Ajout dans la file d'attente
        AnalyticsQueue.enqueue(event);
    }

    // =========================
    // Evénements système
    // =========================

    public static void startup() {
        track("startup");
    }

    public static void shutdown() {
        track("shutdown");
    }

    public static void install() {
        track("install");
    }

    public static void update() {
        track("update");
    }

    // =========================
    // Utilisateur
    // =========================

    public static void login() {
        track("login");
    }

    public static void logout() {
        track("logout");
    }

    // =========================
    // Livres
    // =========================

    public static void bookCreated() {
        track("book_created");
    }

    public static void bookUpdated() {
        track("book_updated");
    }

    public static void bookDeleted() {
        track("book_deleted");
    }

    // =========================
    // Emprunts
    // =========================

    public static void loanCreated() {
        track("loan_created");
    }

    public static void loanReturned() {
        track("loan_returned");
    }

    // =========================
    // Sauvegardes
    // =========================

    public static void backupCreated() {
        track("backup_created");
    }

    public static void restorePerformed() {
        track("restore_performed");
    }

    // =========================
    // Rapports
    // =========================

    public static void reportGenerated() {
        track("report_generated");
    }

    // =========================
    // Gestion des erreurs
    // =========================

    public static void error(Exception exception) {

        AnalyticsEvent event = new AnalyticsEvent();

        event.setEvent("error");

        event.setAppName(AppInfo.getAppName());
        event.setAppVersion(AppInfo.getVersion());
        event.setChannel(AppInfo.getChannel());

        event.setDeviceId(DeviceManager.getDeviceId());

        event.setOs(AppInfo.getOSName());
        event.setOsVersion(AppInfo.getOSVersion());
        event.setArchitecture(AppInfo.getArchitecture());

        event.setJavaVersion(AppInfo.getJavaVersion());

        event.setUsername(AppInfo.getUsername());
        event.setComputerName(AppInfo.getComputerName());

        // Si AnalyticsEvent possède un champ "message" ou "details",
        // tu peux y enregistrer les informations de l'exception.

        AnalyticsQueue.enqueue(event);

    }

}
