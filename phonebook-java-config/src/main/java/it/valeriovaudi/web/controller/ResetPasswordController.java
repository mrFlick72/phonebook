package it.valeriovaudi.web.controller;

import it.valeriovaudi.integration.filter.AcceptableNonceFilter;
import it.valeriovaudi.repository.security.NonceRepository;
import it.valeriovaudi.service.PasswordService;
import it.valeriovaudi.web.model.security.Nonce;
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
    private NonceRepository nonceRepository;
    private AcceptableNonceFilter acceptableNonceFilter;

    @Autowired
    public void setAcceptableNonceFilter(AcceptableNonceFilter acceptableNonceFilter) {
        this.acceptableNonceFilter = acceptableNonceFilter;
    }

    @Autowired
    public void setPasswordService(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @Autowired
    public void setNonceRepository(NonceRepository nonceRepository) {
        this.nonceRepository = nonceRepository;
    }

    @RequestMapping(value = "/resetPassword/reset", method = RequestMethod.GET)
    public String resetPasswordInit(@RequestParam(value = "nonce") String nonce,
                                  Model model) {
        String page="exception/exception";
        Nonce nonceRepositoryByNonce = nonceRepository.findByNonce(nonce);
        if(acceptableNonceFilter.accept(nonceRepositoryByNonce)){
            model.addAttribute("nonce", nonce);
            page = "resetPassword/reset";
        }

        return page;
    }

    @RequestMapping(value = "/resetPassword/resetPasswordSuccessful", method = RequestMethod.GET)
    public void resetPasswordSuccessfulInit(@RequestParam("operation") String s){

    }

    @RequestMapping(value = "/resetPassword/reset", method = RequestMethod.POST)
    public String resetPassword(@ModelAttribute(value = "nonce") String nonce,
                                @RequestParam(value = "newPassword") String newPassword,
                                Model model,
                                SessionStatus sessionStatus) {
        passwordService.resetPassword(newPassword,nonce);
        sessionStatus.setComplete();
        model.addAttribute("operation","reset");
        return "resetPassword/resetPasswordSuccessful";
    }

    @RequestMapping(value = "/resetPassword/resetFormDataCollect", method = RequestMethod.GET)
    public void resetFormDataCollectInit() {}

    @RequestMapping(value = "/resetPassword/resetFormDataCollect", method = RequestMethod.POST)
    public String resetFormDataCollect(@RequestParam(value = "userName") String userName,
                                       @RequestParam(value = "mail") String mail,
                                       Model model) {
        passwordService.createNonce(userName, mail);
        model.addAttribute("operation","nonce");
        return "resetPassword/resetPasswordSuccessful";
    }

    @ExceptionHandler(org.springframework.integration.handler.ReplyRequiredException.class)
    public String exceptionManaged(){
        return "/exception/exception";
    }
}
