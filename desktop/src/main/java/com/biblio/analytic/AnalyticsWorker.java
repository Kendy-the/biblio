package com.biblio.analytic;

import com.biblio.repository.AnalyticsRepository;

import java.util.List;

public class AnalyticsWorker implements Runnable {

    /**
     * Nombre maximum d'essais avant abandon.
     */
    private static final int MAX_RETRY = 10;

    private final AnalyticsRepository repository;

    private volatile boolean running = true;

    public AnalyticsWorker() {

        this.repository = new AnalyticsRepository();

    }

    @Override
    public void run() {

        if (!running) {
            return;
        }

        try {

            processQueue();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    /**
     * Traite les évènements en attente.
     */
    private void processQueue() {

        List<AnalyticsQueueItem> events =
                repository.findPendingEvents();

        if (events.isEmpty()) {
            return;
        }

        for (AnalyticsQueueItem item : events) {

            try {

                boolean response =
                        HttpService.post(
                                AppInfo.getAnalyticsUrl(),
                                item.getPayload()
                            );

                if (response) {

                    repository.markAsSent(item.getId());
                    // System.out.println("Analytics event success : " + item.getPayload());

                } else {

                    repository.incrementRetry(item.getId());

                    if (item.getRetryCount() >= MAX_RETRY) {

                        repository.markAsError(item.getId());

                    }

                }

            } catch (Exception e) {

                repository.incrementRetry(item.getId());

            }

        }

    }

    /**
     * Arrête proprement le Worker.
     */
    public void stop() {
        running = false;
    }

}
