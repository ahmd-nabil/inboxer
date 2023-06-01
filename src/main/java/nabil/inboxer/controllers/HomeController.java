package nabil.inboxer.controllers;

import lombok.RequiredArgsConstructor;
import nabil.inboxer.folders.Folder;
import nabil.inboxer.folders.FolderRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author Ahmed Nabil
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final FolderRepository folderRepository;

    @GetMapping("/")
    public String homePage(Model model, @AuthenticationPrincipal OAuth2User principal) {
        System.out.println(principal);
        if(principal == null || !StringUtils.hasText(principal.getAttribute("name"))) {
            return "index";
        }
        String userName = principal.getAttribute("name");
        Integer userId = principal.getAttribute("id");
        List<Folder> folders = folderRepository.findAllByUserId(userId);
        model.addAttribute("userName", userName);
        model.addAttribute("folders", folders);
        return "home";
    }

}
