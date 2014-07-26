package it.valeriovaudi.web.controller;


import it.valeriovaudi.factory.SecurityUserFactory;
import it.valeriovaudi.web.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Security;


@Controller
public class SignupController {

    private static final String SIGNUP_VIEW_NAME = "signup/signup";

    private UserDetailsManager userDetailsManager;
    private SecurityUserFactory securityUserFactory;

	@RequestMapping(value = "signup")
	public String signup(Model model) {
		model.addAttribute("signupForm",new UserDTO());
        return SIGNUP_VIEW_NAME;
	}

    @RequestMapping(value = "signup",method = RequestMethod.POST)
    public String signup(UserDTO user, Errors errors, RedirectAttributes ra) {
        if (errors.hasErrors()) {
            return SIGNUP_VIEW_NAME;
        }
        userDetailsManager.createUser(securityUserFactory.createUser(user));
        SecurityContextHolder.getContext().setAuthentication(securityUserFactory.getAutenticatedUser(user));

        return "redirect:/index";
    }

    @Autowired
    public void setUserDetailsManager(UserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }

    @Autowired
    public void setSecurityUserFactory(SecurityUserFactory securityUserFactory) {
        this.securityUserFactory = securityUserFactory;
    }

}
