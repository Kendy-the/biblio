package com.biblio.analytic;

import com.biblio.repository.AnalyticsRepository;

public final class AnalyticsQueue {

    private static final AnalyticsRepository repository = new AnalyticsRepository();

    private AnalyticsQueue() {
    }

    /**
     * Ajoute un événement dans la file d'attente SQLite.
     */
    public static void enqueue(AnalyticsEvent event) {

        try {

            repository.save(event);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}
