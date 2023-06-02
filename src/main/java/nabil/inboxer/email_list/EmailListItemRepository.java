package nabil.inboxer.email_list;

import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;

/**
 * @author Ahmed Nabil
 */
public interface EmailListItemRepository extends CassandraRepository<EmailListItem, EmailListItemKey> {
//    List<EmailListItem> findAllByKey(EmailListItemKey key);

    List<EmailListItem> findAllByKey_UserIdAndKey_Label(String userId, String label);
}
