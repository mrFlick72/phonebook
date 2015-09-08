package it.valeriovaudi.web.controller;

import it.valeriovaudi.repository.PhonBookUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Valerio on 18/11/2014.
 */

@Controller
public class SettingsController {

    @ModelAttribute("content")
    public String getFragmentContent(){
        return "settings";
    }

    @ModelAttribute("navigation")
    public String getFragmentPath(){
        return "user/settings";
    }

    private PhonBookUserRepository phonBookUserRepository;

    @Autowired
    public void setPhonBookUserRepository(PhonBookUserRepository phonBookUserRepository) {
        this.phonBookUserRepository = phonBookUserRepository;
    }

    @Secured(value = "IS_AUTHENTICATED_FULLY")
    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public String getUserPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("phoneBoockUser",  phonBookUserRepository.findByUserName(authentication.getName()));

        return "index";
    }
}
