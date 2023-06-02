package nabil.inboxer.emails;

import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

/**
 * @author Ahmed Nabil
 */
public interface EmailRepository extends CassandraRepository<Email, UUID> {
}
