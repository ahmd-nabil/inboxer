package nabil.inboxer.controllers;

import lombok.RequiredArgsConstructor;
import nabil.inboxer.emails.Email;
import nabil.inboxer.services.MainService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Ahmed Nabil
 */
@Controller
@RequiredArgsConstructor
public class ComposeController {

    private final MainService mainService;
    @GetMapping("/compose")
    public String getComposePage(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal == null || !StringUtils.hasText(principal.getAttribute("login"))) {
            return "redirect:/";
        }
        mainService.addUserNameToModel(model, principal.getAttribute("name"));
        mainService.addUserFoldersToModel(model, principal.getAttribute("login"));
        model.addAttribute("newEmail", new Email());
        return "compose-page";
    }

}
