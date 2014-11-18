package it.valeriovaudi.web.controller;

import it.valeriovaudi.security.PhoneBookSecurityRole;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Valerio on 19/09/2014.
 */

@Controller
public class IndexPageController {

    @ModelAttribute("content")
    public String getFragmentContent(){
        return "index";
    }

    @ModelAttribute("navigation")
    public String getFragmentPath(){
        return "user/content";
    }

    @Secured(value = "IS_AUTHENTICATED_FULLY")
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public void getUserPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("phoneBoockUserName",  authentication.getName());
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            if(grantedAuthority.getAuthority().equals(PhoneBookSecurityRole.ADMIN.getRole())){
                model.addAttribute("controller","administrationController");
                break;
            }

            if(grantedAuthority.getAuthority().equals(PhoneBookSecurityRole.USER.getRole())){
                model.addAttribute("controller","handleFormController");
                break;
            }
        }

/*        model.addAttribute("content", "settings");
        model.addAttribute("navigation",  "user/settings");*/
    }
}
