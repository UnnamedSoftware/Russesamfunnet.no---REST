package no.ntnu.unnamedsoftware.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import no.ntnu.unnamedsoftware.service.LoginService;

@RestController
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	
	@RequestMapping(value="/login", produces=MediaType.APPLICATION_JSON_VALUE)
	public String Login(@RequestParam("email") String email, @RequestParam("password") String password) {
		return loginService.Login(email, password);
	}
	
	@RequestMapping(value="/facebookLogin", produces=MediaType.APPLICATION_JSON_VALUE)
	public String Login(@RequestParam("accessToken") String accessToken) {
		return loginService.facebookLogin(accessToken);
	}

}
