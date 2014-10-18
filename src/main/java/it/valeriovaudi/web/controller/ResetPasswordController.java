package it.valeriovaudi.web.controller;

import it.valeriovaudi.repository.PhonBookUserRepository;
import it.valeriovaudi.web.model.PhoneBookUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Valerio on 18/10/2014.
 */

@Controller
public class ResetPasswordController {

    private PhonBookUserRepository phonBookUserRepository;
    /** nonceRepository*/
    public void setPhonBookUserRepository(PhonBookUserRepository phonBookUserRepository) {
        this.phonBookUserRepository = phonBookUserRepository;
    }

    @RequestMapping(value = "/resetPassword",method = RequestMethod.GET)
    public void getUserPage(@RequestParam(value = "userName") String userName,
                            @RequestParam(value = "nonce") String nonce,
                            Model model){

        PhoneBookUser byUserName = phonBookUserRepository.findByUserName(userName);
        Assert.notNull(byUserName);


        model.addAttribute("controller","resetPasswordController");
    }

}
