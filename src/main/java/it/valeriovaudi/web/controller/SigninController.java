package it.valeriovaudi.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SigninController {

    private static final String SIGNIN_VIEW_NAME = "signin/signin";

    @RequestMapping(value = "signin")
	public String signin() {
        return SIGNIN_VIEW_NAME;
    }
}
