package nabil.inboxer.controllers;

import lombok.RequiredArgsConstructor;
import nabil.inboxer.services.MainService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Ahmed Nabil
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MainService mainService;

    @GetMapping("/")
    public String homePage(Model model, @AuthenticationPrincipal OAuth2User principal, @RequestParam(defaultValue = "inbox") String label) {
        System.out.println(principal);
        if(principal == null || !StringUtils.hasText(principal.getAttribute("login"))) {
            return "index";
        }
        String userName = principal.getAttribute("name");
        String userId = principal.getAttribute("login");
        mainService.addUserFoldersToModel(model, userName, userId);
        mainService.addEmailListToModel(model, userId, label);
        model.addAttribute("activeLabel", label);
        return "home";
    }



}
