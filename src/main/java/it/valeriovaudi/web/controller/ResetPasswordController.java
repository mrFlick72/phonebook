package it.valeriovaudi.web.controller;

import it.valeriovaudi.factory.SecurityUserFactory;
import it.valeriovaudi.repository.PhonBookUserRepository;
import it.valeriovaudi.repository.security.NonceRepository;
import it.valeriovaudi.service.PasswordService;
import it.valeriovaudi.web.model.PhoneBookUser;
import it.valeriovaudi.web.model.security.Nonce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Valerio on 18/10/2014.
 */

@Controller
@SessionAttributes("nonce")
public class ResetPasswordController {

    private PasswordService passwordService;
    private NonceRepository nonceRepository;
    private PhonBookUserRepository phonBookUserRepository;
    private SecurityUserFactory<PhoneBookUser> securityUserFactory;

    @Autowired
    public void setNonceRepository(NonceRepository nonceRepository) {
        this.nonceRepository = nonceRepository;
    }

    @Autowired
    public void setPhonBookUserRepository(PhonBookUserRepository phonBookUserRepository) {
        this.phonBookUserRepository = phonBookUserRepository;
    }

    @Autowired
    public void setSecurityUserFactory(SecurityUserFactory<PhoneBookUser> securityUserFactory) {
        this.securityUserFactory = securityUserFactory;
    }

    @Autowired
    public void setPasswordService(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @RequestMapping(value = "/resetPassword/reset", method = RequestMethod.GET)
    public void resetPasswordInit(@RequestParam(value = "nonce") String nonce,
                            Model model) {
        model.addAttribute("nonce", nonce);
        model.addAttribute("controller", "resetPasswordController");
    }

    @RequestMapping(value = "/resetPassword/reset", method = RequestMethod.POST)
    public void resetPassword(@ModelAttribute(value = "nonce") String nonce,
                              @RequestParam(value = "newPassword") String newPassword) {
        Nonce nonceRepositoryByNonce = nonceRepository.findByNonce(nonce);

        String userName = nonceRepositoryByNonce.getUserName();
        PhoneBookUser phoneBookUser = phonBookUserRepository.findByUserName(userName);
        phoneBookUser.setPassword(newPassword);
        phoneBookUser = securityUserFactory.securityAccontWithPasswordEncoded(phoneBookUser);

        phonBookUserRepository.save(phoneBookUser);
    }

    @RequestMapping(value = "/resetPassword/resetFormDataCollect", method = RequestMethod.GET)
    public void resetFormDataCollectInit() {}

    @RequestMapping(value = "/resetPassword/resetFormDataCollect", method = RequestMethod.POST)
    public void resetFormDataCollect(@RequestParam(value = "userName") String userName,
                                     @RequestParam(value = "mail") String mail) {
        passwordService.resetPassword(userName,mail);
    }
}
