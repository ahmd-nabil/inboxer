package nabil.inboxer.boostrap;

import lombok.RequiredArgsConstructor;
import nabil.inboxer.email_list.EmailListItemRepository;
import nabil.inboxer.emails.EmailRepository;
import nabil.inboxer.services.ComposeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Ahmed Nabil
 */
@Component
@RequiredArgsConstructor
public class EmailsBootstrap implements CommandLineRunner {
    private final EmailListItemRepository emailListItemRepository;
    private final EmailRepository emailRepository;
    private final ComposeService composeService;
    private final static String CONTENT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
    @Override
    public void run(String... args) throws Exception {
        for (int i=0; i<10; i++) {
            composeService.sendEmail("ahmd-nabil", "ahmd-nabil, aliali, mmads", "New Book to public " + (i+1), CONTENT);
        }

    }
}
