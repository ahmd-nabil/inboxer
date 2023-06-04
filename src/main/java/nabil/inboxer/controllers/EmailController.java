package nabil.inboxer.controllers;

import lombok.RequiredArgsConstructor;
import nabil.inboxer.emails.Email;
import nabil.inboxer.emails.EmailRepository;
import nabil.inboxer.mappers.RecipientsMapper;
import nabil.inboxer.services.MainService;
import nabil.inboxer.unread_email_stats.UnreadEmailStatsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Ahmed Nabil
 */
@RequiredArgsConstructor
@RequestMapping("/emails")
@Controller
public class EmailController {

    private final MainService mainService;
    private final EmailRepository emailRepository;
    private final RecipientsMapper recipientsMapper;
    private final UnreadEmailStatsService unreadEmailStatsService;

    @GetMapping("/{id}")
    public String getEmail(
            @PathVariable UUID id,
            @AuthenticationPrincipal OAuth2User principal,
            Model model) {
        if(principal == null || !StringUtils.hasText(principal.getAttribute("login"))) {
            return "redirect:/";
        }
        String userName = principal.getAttribute("name");
        String userId = principal.getAttribute("login");
        Optional<Email> emailOptional = emailRepository.findById(id);
        if(emailOptional.isEmpty() || (!emailOptional.get().getFrom().equals(userId) && !emailOptional.get().getTo().contains(userId))) {
            return "redirect:/";  // TODO: informative error page
        }
        unreadEmailStatsService.onReadEmail(emailOptional.get(), userId);
        mainService.addUserNameToModel(model, userName);
        mainService.addUserFoldersToModel(model, userId);
        model.addAttribute("email", emailOptional.get());
        String recipients = recipientsMapper.convertListToString(emailOptional.get().getTo());
        model.addAttribute("recipients", recipients);
        model.addAttribute("unreadStats", unreadEmailStatsService.getUnreadStats(userId));
        return "email";
    }

}
