package com.biblio.analytic;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class AnalyticsScheduler {

    private static final ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor(r -> {
                Thread thread = new Thread(r);
                thread.setName("Analytics-Worker");
                thread.setDaemon(true);
                return thread;
            });

    private static final AnalyticsWorker worker = new AnalyticsWorker();

    private static boolean started = false;

    private AnalyticsScheduler() {
    }

    /**
     * Démarre le worker d'analytics.
     */
    public static synchronized void start() {

        if (started) {
            return;
        }

        scheduler.scheduleWithFixedDelay(
                worker,
                5,
                30,
                TimeUnit.SECONDS
        );

        started = true;

        System.out.println("Analytics Scheduler démarré.");

    }

    /**
     * Arrête le scheduler.
     */
    public static synchronized void stop() {

        if (!started) {
            return;
        }

        worker.stop();

        scheduler.shutdown();

        started = false;

        System.out.println("Analytics Scheduler arrêté.");

    }

}
