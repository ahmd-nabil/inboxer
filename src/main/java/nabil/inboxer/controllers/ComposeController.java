package nabil.inboxer.controllers;

import lombok.RequiredArgsConstructor;
import nabil.inboxer.mappers.RecipientsMapper;
import nabil.inboxer.services.ComposeService;
import nabil.inboxer.services.MainService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Ahmed Nabil
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/compose")
public class ComposeController {

    private final MainService mainService;
    private final ComposeService composeService;
    private final RecipientsMapper mapper;
    @GetMapping
    public String getComposePage(@AuthenticationPrincipal OAuth2User principal, Model model,
                                 @RequestParam(name = "to", defaultValue = "") String recipients,
                                 @RequestParam(name = "subject", defaultValue = "") String subject) {
        if (principal == null || !StringUtils.hasText(principal.getAttribute("login"))) {
            return "redirect:/";
        }
        mainService.addUserNameToModel(model, principal.getAttribute("name"));
        mainService.addUserFoldersToModel(model, principal.getAttribute("login"));
        model.addAttribute("recipients", mapper.convertListToString(mapper.convertStringToDistinctList(recipients)));
        model.addAttribute("subject", subject);
        return "compose-page";
    }

    @PostMapping
    public ModelAndView sendEmail(@RequestBody MultiValueMap<String, String> form,
                                  @AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal == null || !StringUtils.hasText(principal.getAttribute("login"))) {
            return new ModelAndView("redirect:/");
        }

        composeService.sendEmail(
                principal.getAttribute("login"),
                form.getFirst("recipients"),
                form.getFirst("subject"),
                form.getFirst("content")
        );
        return new ModelAndView("redirect:/");
    }


}
