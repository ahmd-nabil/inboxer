package nabil.inboxer.services;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.RequiredArgsConstructor;
import nabil.inboxer.email_list.EmailListItem;
import nabil.inboxer.email_list.EmailListItemKey;
import nabil.inboxer.email_list.EmailListItemRepository;
import nabil.inboxer.emails.Email;
import nabil.inboxer.emails.EmailRepository;
import nabil.inboxer.mappers.RecipientsMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ahmed Nabil
 */
@Service
@RequiredArgsConstructor
public class ComposeService {
    private final EmailRepository emailRepository;
    private final EmailListItemRepository emailListItemRepository;
    private final RecipientsMapper recipientsMapper;
    public void sendEmail(String from, String recipients, String subject, String content) {
        List<String> to = recipientsMapper.convertStringToDistinctList(recipients);
        // when sending email we need to create Email view, and EmailListItem(inbox) for each recipientId and EmailListItem(sent) for the sender
        Email email = Email.builder()
                .id(Uuids.timeBased())
                .from(from)
                .to(to)
                .subject(subject)
                .content(content)
                .build();
        emailRepository.save(email);

        EmailListItem senderItem = getEmailListItem(from, "sent", email, to, subject);
        emailListItemRepository.save(senderItem);

        email.getTo().forEach(toId -> {
            EmailListItem emailListItem = getEmailListItem(toId, "inbox", email, to, subject);
            emailListItemRepository.save(emailListItem);
        });
    }

    private static EmailListItem getEmailListItem(String ownerId, String label, Email email, List<String> to, String subject) {
        EmailListItemKey key = EmailListItemKey.builder()
                .userId(ownerId)
                .label(label)
                .createdAt(email.getId()).build();
        return EmailListItem.builder()
                .key(key)
                .to(to)
                .subject(subject)
                .build();
    }
}
