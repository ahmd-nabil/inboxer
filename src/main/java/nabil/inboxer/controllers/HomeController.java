package nabil.inboxer.controllers;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.RequiredArgsConstructor;
import nabil.inboxer.email_list.EmailListItem;
import nabil.inboxer.email_list.EmailListItemRepository;
import nabil.inboxer.folders.Folder;
import nabil.inboxer.folders.FolderRepository;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Ahmed Nabil
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final FolderRepository folderRepository;
    private final EmailListItemRepository emailListItemRepository;

    @GetMapping("/")
    public String homePage(Model model, @AuthenticationPrincipal OAuth2User principal) {
        System.out.println(principal);
        if(principal == null || !StringUtils.hasText(principal.getAttribute("login"))) {
            return "index";
        }
        String userName = principal.getAttribute("name");
        String userId = principal.getAttribute("login");
        fetchUserFolders(model, userName, userId);
        fetchUserEmailListItems(model, userId);
        return "home";
    }

    private void fetchUserEmailListItems(Model model, String userId) {
        List<EmailListItem> emailListItems = emailListItemRepository.findAllByKey_UserIdAndKey_Label(userId, "sent");
        emailListItems.forEach(item -> {
            UUID timeUUID = item.getKey().getCreatedAt();
            Date createdDate = new Date(Uuids.unixTimestamp(timeUUID));
            item.setTimeAgo(new PrettyTime().format(createdDate));
        });
        model.addAttribute("emailItems", emailListItems);
    }

    private void fetchUserFolders(Model model, String userName, String userId) {
        List<Folder> userFolders = folderRepository.findAllByUserId(userId);
        model.addAttribute("userName", userName);
        model.addAttribute("userFolders", userFolders);
    }

}
