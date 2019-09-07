package pl.sda.ownApp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
public String homePage(){
       return "homePage";
    }
    @GetMapping("/restricted")
    public String restrictedPage(Authentication authentication) {
        authentication.getName();
        return "restrictedPage";
    }


}
