package com.biblio.repository;

import com.biblio.analytic.AnalyticsEvent;
import com.biblio.analytic.AnalyticsQueueItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.util.ArrayList;
import java.util.List;

public class AnalyticsRepository extends Repository {

    private final ObjectMapper mapper = new ObjectMapper()
    .registerModule(new JavaTimeModule())
    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    /**
     * Sauvegarde un évènement dans SQLite.
     */
    public void save(AnalyticsEvent event) {

        String sql = """
                INSERT INTO analytics_queue
                (
                    event,
                    payload,
                    retry_count,
                    status
                )
                VALUES
                (
                    ?, ?, 0, 0
                )
                """;

        try{

            this.pst = db.prepareStatement(sql);

            this.pst.setString(1, event.getEvent());
            this.pst.setString(2, mapper.writeValueAsString(event));

            this.pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void markAsError(long id) {

        String sql = """
                UPDATE analytics_queue
                SET status = 2
                WHERE id = ?
                """;

        try{

            this.pst = db.prepareStatement(sql);
            this.pst.setLong(1, id);
            this.pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Retourne les évènements en attente.
     */
    public List<AnalyticsQueueItem> findPendingEvents() {

        List<AnalyticsQueueItem> list = new ArrayList<>();

        String sql = """
                SELECT *
                FROM analytics_queue
                WHERE status = 0
                ORDER BY id
                LIMIT 20
                """;

        try {

            this.pst = db.prepareStatement(sql);
            this.rs = this.pst.executeQuery();

            while (rs.next()) {

                AnalyticsQueueItem item = new AnalyticsQueueItem();

                item.setId(rs.getLong("id"));

                item.setPayload(rs.getString("payload"));

                item.setRetryCount(rs.getInt("retry_count"));

                list.add(item);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;

    }

    /**
     * Marque un évènement comme envoyé.
     */
    public void markAsSent(long id) {

        String sql = """
                UPDATE analytics_queue
                SET
                    status = 1,
                    sent_at = CURRENT_TIMESTAMP
                WHERE id = ?
                """;

        try {

            this.pst = db.prepareStatement(sql);
            this.pst.setLong(1, id);
            this.pst.executeUpdate();

            pst.setLong(1, id);

            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Incrémente le nombre de tentatives.
     */
    public void incrementRetry(long id) {

        String sql = """
                UPDATE analytics_queue
                SET retry_count = retry_count + 1
                WHERE id = ?
                """;
        try{

            this.pst = db.prepareStatement(sql);
            this.pst.setLong(1, id);
            this.pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
