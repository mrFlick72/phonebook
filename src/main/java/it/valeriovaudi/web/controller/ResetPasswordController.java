package it.valeriovaudi.web.controller;

import com.sun.org.glassfish.external.statistics.annotations.Reset;
import it.valeriovaudi.repository.PhonBookUserRepository;
import it.valeriovaudi.repository.security.NonceRepository;
import it.valeriovaudi.service.PasswordService;
import it.valeriovaudi.web.model.PhoneBookUser;
import it.valeriovaudi.web.model.security.Nonce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Valerio on 18/10/2014.
 */

@Controller
@SessionAttributes("nonce")
public class ResetPasswordController {

    private PasswordService passwordService;

    @Autowired
    public void setPasswordService(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @RequestMapping(value = "/resetPassword/reset", method = RequestMethod.GET)
    public void getUserPage(@RequestParam(value = "nonce") String nonce,
                            Model model) {
        model.addAttribute("nonce", nonce);
        model.addAttribute("controller", "resetPasswordController");
    }

    @RequestMapping(value = "/resetPassword/resetFormDataCollect", method = RequestMethod.GET)
    public void resetFormDataCollectInit() {}

    @RequestMapping(value = "/resetPassword/resetFormDataCollect", method = RequestMethod.POST)
    public void resetFormDataCollect(@RequestParam(value = "userName") String userName,
                                     @RequestParam(value = "mail") String mail) {

        System.out.println(userName);
        System.out.println(mail);

        passwordService.resetPassword(userName,mail);
    }
}
