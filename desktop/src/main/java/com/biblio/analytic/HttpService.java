package com.biblio.analytic;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.UnknownHostException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class HttpService {

    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private HttpService() {
    }

    /**
     * Envoie un objet Java au format JSON via HTTP POST.
     *
     * @param url    URL de l'API
     * @param object Objet à convertir en JSON
     * @return true si succès
     */
    public static boolean post(String url, Object object) {

        try {

            String json = MAPPER.writeValueAsString(object);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(20))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = CLIENT.send(
                    request,
                    HttpResponse.BodyHandlers.ofString());

                    System.out.println("Response status: " + response.statusCode() + " for payload: " + json);

            return response.statusCode() >= 200
                    && response.statusCode() < 300;
        
        }catch (ConnectException e) {
            System.out.println("Serveur Analytics indisponible.");
            Thread.currentThread().interrupt();
            return false;
        }
        catch (UnknownHostException e) {
            System.out.println("Adresse du serveur invalide.");
            Thread.currentThread().interrupt();
            return false;
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
        catch (Exception e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            return false;
        }

    }

    /**
     * Effectue une requête GET.
     *
     * @param url URL
     * @return Corps de la réponse ou null
     */
    public static String get(String url) {

        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(20))
                    .GET()
                    .build();

            HttpResponse<String> response = CLIENT.send(
                    request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200
                    && response.statusCode() < 300) {

                return response.body();
            }

        } catch (IOException | InterruptedException e) {

            e.printStackTrace();

            Thread.currentThread().interrupt();

        }

        return null;

    }

}
