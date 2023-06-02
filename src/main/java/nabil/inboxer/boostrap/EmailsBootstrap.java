package nabil.inboxer.boostrap;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.RequiredArgsConstructor;
import nabil.inboxer.email_list.EmailListItem;
import nabil.inboxer.email_list.EmailListItemKey;
import nabil.inboxer.email_list.EmailListItemRepository;
import nabil.inboxer.emails.Email;
import nabil.inboxer.emails.EmailRepository;
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
    private final EmailRepository emailRepository;
    private final static String CONTENT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
    @Override
    public void run(String... args) throws Exception {
        EmailListItemKey key1 = new EmailListItemKey("ahmd-nabil", "sent", Uuids.timeBased());
        EmailListItem item1 = EmailListItem
                .builder()
                .key(key1)
                .to(Stream.of("ahmd-nabil").toList())
                .subject("Hello Everyone!!")
                .build();

        Email email1= Email
                .builder()
                .id(key1.getCreatedAt())
                .from(item1.getKey().getUserId())
                .to(item1.getTo())
                .subject(item1.getSubject())
                .content(CONTENT)
                .build();

        emailListItemRepository.save(item1);
        emailRepository.save(email1);

        EmailListItemKey key2 = new EmailListItemKey("ahmd-nabil", "sent", Uuids.timeBased());
        EmailListItem item2 = EmailListItem
                .builder()
                .key(key2)
                .to(Stream.of("ahmd-nabil").toList())
                .subject("Welcome to my First Cassandra app")
                .build();

        Email email2= Email
                .builder()
                .id(key2.getCreatedAt())
                .from(item2.getKey().getUserId())
                .to(item2.getTo())
                .subject(item2.getSubject())
                .content(CONTENT)
                .build();
        emailListItemRepository.save(item2);
        emailRepository.save(email2);

        EmailListItemKey key3 = new EmailListItemKey("ahmd-nabil", "sent", Uuids.timeBased());
        EmailListItem item3 = EmailListItem
                .builder()
                .key(key3)
                .to(Stream.of("ahmd-nabil").toList())
                .subject("TESTING!!!!")
                .build();

        Email email3= Email
                .builder()
                .id(key3.getCreatedAt())
                .from(item3.getKey().getUserId())
                .to(item3.getTo())
                .subject(item3.getSubject())
                .content(CONTENT)
                .build();

        emailListItemRepository.save(item3);
        emailRepository.save(email3);

    }
}
