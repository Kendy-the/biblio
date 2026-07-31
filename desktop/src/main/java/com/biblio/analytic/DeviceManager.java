package com.biblio.analytic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DeviceManager {

    private static final String APP_NAME = "Biblio";
    private static final String CONFIG_FILE = "config.json";

    private DeviceManager() {
    }

    /**
     * Retourne l'identifiant unique de cette installation.
     * Le fichier config.json est créé automatiquement s'il n'existe pas.
     */
    public static String getDeviceId() {

        try {

            Path config = getConfigFile();

            if (!Files.exists(config)) {
                createConfigFile(config);
            }

            String json = Files.readString(config, StandardCharsets.UTF_8);

            Pattern pattern = Pattern.compile("\"device_id\"\\s*:\\s*\"([^\"]+)\"");
            Matcher matcher = pattern.matcher(json);

            if (matcher.find()) {
                return matcher.group(1);
            }

            // Si le fichier existe mais est invalide
            createConfigFile(config);

            json = Files.readString(config, StandardCharsets.UTF_8);

            matcher = pattern.matcher(json);

            if (matcher.find()) {
                return matcher.group(1);
            }

            throw new RuntimeException("Impossible de lire le Device ID.");

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier config.", e);
        }

    }

    /**
     * Retourne le dossier AppData\Local\Biblio
     */
    public static Path getAppFolder() {

        String localAppData = System.getenv("LOCALAPPDATA");

        if (localAppData == null || localAppData.isBlank()) {
            localAppData = System.getProperty("user.home");
        }

        return Path.of(localAppData, APP_NAME);

    }

    /**
     * Retourne le chemin du fichier config.json
     */
    public static Path getConfigFile() {
        return getAppFolder().resolve(CONFIG_FILE);
    }

    /**
     * Crée automatiquement le dossier de l'application et le fichier config.json.
     */
    private static void createConfigFile(Path configFile) throws IOException {

        Files.createDirectories(getAppFolder());

        String deviceId = UUID.randomUUID().toString();

        String json = String.format("""
                {
                    "device_id": "%s",
                    "installedAt": "%s",
                    "lastVersion": "1.0.1",
                    "channel": "stable"
                }
                """,
                deviceId,
                LocalDateTime.now());

        Files.writeString(
                configFile,
                json,
                StandardCharsets.UTF_8
        );

    }

}
