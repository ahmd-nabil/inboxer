package nabil.inboxer.boostrap;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.RequiredArgsConstructor;
import nabil.inboxer.email_list.EmailListItem;
import nabil.inboxer.email_list.EmailListItemKey;
import nabil.inboxer.email_list.EmailListItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * @author Ahmed Nabil
 */
@Component
@RequiredArgsConstructor
public class EmailsBootstrap implements CommandLineRunner {
    private final EmailListItemRepository emailListItemRepository;


    @Override
    public void run(String... args) throws Exception {
        EmailListItemKey key1 = new EmailListItemKey("ahmd-nabil", "sent", Uuids.timeBased());
        emailListItemRepository.save(
                EmailListItem
                        .builder()
                        .key(key1)
                        .to(Stream.of("ahmd-nabil").toList())
                        .subject("Hello Everyone!!")
                        .build()
                );

        EmailListItemKey key2 = new EmailListItemKey("ahmd-nabil", "sent", Uuids.timeBased());
        emailListItemRepository.save(
                EmailListItem
                        .builder()
                        .key(key2)
                        .to(Stream.of("ahmd-nabil").toList())
                        .subject("Welcome to my First Cassandra app")
                        .build()
        );

        EmailListItemKey key3 = new EmailListItemKey("ahmd-nabil", "sent", Uuids.timeBased());
        emailListItemRepository.save(
                EmailListItem
                        .builder()
                        .key(key3)
                        .to(Stream.of("ahmd-nabil").toList())
                        .subject("TESTING!!!!")
                        .build()
        );
    }
}
