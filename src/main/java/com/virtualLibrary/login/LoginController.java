package com.virtualLibrary.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("name")
public class LoginController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showGoogleLoginPage() {
		return "login";
	}
}
