package com.biblio.analytic;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Properties;

public final class AppInfo {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = AppInfo.class.getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input != null) {
                PROPERTIES.load(input);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private AppInfo() {
    }

    /**
     * Nom de l'application.
     */
    public static String getAppName() {
        return PROPERTIES.getProperty("app.name", "Biblio");
    }

    /**
     * Version de l'application.
     */
    public static String getVersion() {
        return PROPERTIES.getProperty("app.version", "1.0.0");
    }

    /**
     * Canal de distribution.
     */
    public static String getChannel() {
        return PROPERTIES.getProperty("app.channel", "stable");
    }

    /**
     * Nom du système d'exploitation.
     */
    public static String getOSName() {
        return System.getProperty("os.name");
    }

    /**
     * Version du système d'exploitation.
     */
    public static String getOSVersion() {
        return System.getProperty("os.version");
    }

    /**
     * Architecture du processeur.
     */
    public static String getArchitecture() {
        return System.getProperty("os.arch");
    }

    /**
     * Version de Java.
     */
    public static String getJavaVersion() {
        return System.getProperty("java.version");
    }

    /**
     * Nom de l'utilisateur Windows.
     */
    public static String getUsername() {
        return System.getProperty("user.name");
    }

    /**
     * URL du service d'analyse.
     */
    public static String getAnalyticsUrl() {
        return PROPERTIES.getProperty("analytics.url", "http://kaytodo.infinityfree.me/api/biblio/analytics");
    }

    /**
     * Nom de la machine.
     */
    public static String getComputerName() {

        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return "Unknown";
        }

    }

}
