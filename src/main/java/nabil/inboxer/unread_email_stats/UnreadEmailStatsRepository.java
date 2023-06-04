package nabil.inboxer.unread_email_stats;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;

/**
 * @author Ahmed Nabil
 */
public interface UnreadEmailStatsRepository extends CassandraRepository<UnreadEmailStats, String> {
    List<UnreadEmailStats> findAllByUserId(String userId);

    @Query("UPDATE unread_email_stats SET counter = counter + 1  WHERE user_id = ?0 AND label = ?1")
    void onEmailAdded(String userId, String label);
    @Query("UPDATE unread_email_stats SET counter = counter - 1  WHERE user_id = ?0 AND label = ?1")
    void onEmailRead(String userId, String label);
}
