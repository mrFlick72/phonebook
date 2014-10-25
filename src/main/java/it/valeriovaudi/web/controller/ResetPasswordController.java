package it.valeriovaudi.web.controller;

import it.valeriovaudi.integration.AcceptableNonceRouter;
import it.valeriovaudi.service.PasswordService;
import org.h2.engine.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

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
    public void resetPasswordInit(@RequestParam(value = "nonce") String nonce,Model model) {
        model.addAttribute("nonce", nonce);
    }

    @RequestMapping(value = "/resetPassword/resetPasswordSuccessful", method = RequestMethod.GET)
    public void resetPasswordSuccessfulInit(){}

    @RequestMapping(value = "/resetPassword/reset", method = RequestMethod.POST)
    public String resetPassword(@ModelAttribute(value = "nonce") String nonce,
                                @RequestParam(value = "newPassword") String newPassword,
                                SessionStatus sessionStatus) {
        passwordService.resetPassword(newPassword,nonce);
        sessionStatus.setComplete();
        return "resetPassword/resetPasswordSuccessful";
    }

    @RequestMapping(value = "/resetPassword/resetFormDataCollect", method = RequestMethod.GET)
    public void resetFormDataCollectInit() {}

    @RequestMapping(value = "/resetPassword/resetFormDataCollect", method = RequestMethod.POST)
    public String resetFormDataCollect(@RequestParam(value = "userName") String userName,
                                       @RequestParam(value = "mail") String mail) {
        passwordService.createNonce(userName, mail);

        return "redirect:index";
    }
}
