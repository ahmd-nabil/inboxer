package nabil.inboxer.controllers;

import lombok.RequiredArgsConstructor;
import nabil.inboxer.folders.Folder;
import nabil.inboxer.folders.FolderRepository;
import nabil.inboxer.services.MainService;
import nabil.inboxer.unread_email_stats.UnreadEmailStatsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Ahmed Nabil
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MainService mainService;
    private final UnreadEmailStatsService unreadEmailStatsService;
    private final FolderRepository folderRepository;
    @GetMapping("/")
    public String homePage(Model model, @AuthenticationPrincipal OAuth2User principal, @RequestParam(defaultValue = "inbox") String label) {
        String userName = principal.getAttribute("name");
        String userId = principal.getAttribute("login");
        if(userId != null && folderRepository.findAllByUserId(userId).size()==0) {
            folderRepository.save(Folder.builder().userId(userId).label("inbox").color("blue").build());
            folderRepository.save(Folder.builder().userId(userId).label("sent").color("gray").build());
        }
        mainService.addLabelToModel(model, label);
        mainService.addUserNameToModel(model, userName);
        mainService.addUserFoldersToModel(model, userId);
        mainService.addEmailListToModel(model, userId, label);
        model.addAttribute("activeLabel", label);
        model.addAttribute("unreadStats", unreadEmailStatsService.getUnreadStats(userId));
        return "home";
    }

    @GetMapping("/login")
    public String login(@AuthenticationPrincipal OAuth2User principal) {
        if(principal != null)
            return "home";
        return "login";
    }
}
