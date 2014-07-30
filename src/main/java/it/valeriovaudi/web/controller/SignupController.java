package it.valeriovaudi.web.controller;

import it.valeriovaudi.factory.SecurityUserFactory;
import it.valeriovaudi.repository.PhonBookUserRepository;
import it.valeriovaudi.web.model.PhonBookUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class SignupController {
    private static final String SIGNUP_VIEW_NAME = "signup/signup";

    private PhonBookUserRepository phonBookUserRepository;
    private SecurityUserFactory<PhonBookUser> securityUserFactory;

	@RequestMapping(value = "signup")
	public String signup(Model model) {
		model.addAttribute("signupForm",new PhonBookUser());
        return SIGNUP_VIEW_NAME;
	}

    @RequestMapping(value = "/signup",method = RequestMethod.POST)
    public String signup(@ModelAttribute("signupForm") PhonBookUser phonBookUser, Errors errors, RedirectAttributes ra) {
        if (errors.hasErrors()) {
            return SIGNUP_VIEW_NAME;
        }

        phonBookUser = securityUserFactory.securityAccontWithPasswordEncoded(phonBookUser);
        phonBookUserRepository.save(phonBookUser);

        SecurityContextHolder.getContext().setAuthentication(securityUserFactory.getAutenticatedUser(phonBookUser));
        return "redirect:/index";
    }

    @Autowired
    public void setPhonBookUserRepository(PhonBookUserRepository phonBookUserRepository) {
        this.phonBookUserRepository = phonBookUserRepository;
    }

    @Autowired
    public void setSecurityUserFactory(SecurityUserFactory securityUserFactory) {
        this.securityUserFactory = securityUserFactory;
    }

}
