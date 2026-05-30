package main.web;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author zubbo
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String showHomePage(Model model, Authentication authentication) {
        boolean isAuthenticated = authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);

        model.addAttribute("pageTitle", "Каталог товаров и обратная связь");
        model.addAttribute("isAuthenticated", isAuthenticated);

        return "index";
    }
}
