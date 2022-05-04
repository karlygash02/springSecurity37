package kz.group37.springSecurity37.controllers;

import kz.group37.springSecurity37.model.AuthUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value = "/")
    public String index(Model model){
        model.addAttribute("currentUser", getCurrentUser());
        return "index";
    }

    @GetMapping(value = "/signin")
    public String singIn(Model model){
        model.addAttribute("currentUser", getCurrentUser());
        return "signin";
    }

    @GetMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profiePage(Model model){
        model.addAttribute("currentUser", getCurrentUser());
        return "profile";
    }

    @GetMapping(value = "/adminpanel")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String adminPanel(Model model){
        model.addAttribute("currentUser", getCurrentUser());
        return "adminpanel";
    }

    @GetMapping(value = "/accesserror")
    public String accessError(Model model){
        model.addAttribute("currentUser", getCurrentUser());
        return "accessdeniedpage";
    }

    private AuthUser getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            AuthUser authUser = (AuthUser) authentication.getPrincipal();
            return authUser;
        }
        return null;
    }

}