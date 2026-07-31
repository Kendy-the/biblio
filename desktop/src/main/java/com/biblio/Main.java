package com.biblio;

import javax.swing.SwingUtilities;

import com.biblio.analytic.AnalyticsScheduler;
import com.biblio.analytic.AnalyticsService;
import com.biblio.database.DatabaseInitializer;
import com.biblio.views.App;
import com.biblio.views.auth.SplashScreen;

@SuppressWarnings("unused")
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("App Running...");

        AnalyticsScheduler.start();
        AnalyticsService.startup();

        DatabaseInitializer.init();

        SwingUtilities.invokeLater(() -> {
            new SplashScreen();
        });
    }
}
