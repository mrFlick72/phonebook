package it.valeriovaudi.web.controller;

import it.valeriovaudi.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

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
