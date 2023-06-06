package nabil.inboxer.unread_email_stats;

import lombok.RequiredArgsConstructor;
import nabil.inboxer.email_list.EmailListItem;
import nabil.inboxer.email_list.EmailListItemKey;
import nabil.inboxer.email_list.EmailListItemRepository;
import nabil.inboxer.emails.Email;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Ahmed Nabil
 */
@Service
@RequiredArgsConstructor
public class UnreadEmailStatsService {

    private final EmailListItemRepository emailListItemRepository;
    private final UnreadEmailStatsRepository unreadEmailStatsRepository;
    public void onReadEmail(Email email, String userId, String label) {
        EmailListItemKey key = EmailListItemKey
                .builder()
                .userId(userId)
                .label(label)
                .createdAt(email.getId()).build();
        EmailListItem item = emailListItemRepository.findById(key).orElseThrow(RuntimeException::new);
        if(!item.isRead()) {
            item.setRead(true);
            emailListItemRepository.save(item);
            unreadEmailStatsRepository.onEmailRead(key.getUserId(), label);
        }
    }

    public Map<String, Long> getUnreadStats(String userId) {
        return unreadEmailStatsRepository.findAllByUserId(userId)
                .stream().collect(Collectors.toMap(UnreadEmailStats::getLabel, UnreadEmailStats::getCounter));
    }
}
