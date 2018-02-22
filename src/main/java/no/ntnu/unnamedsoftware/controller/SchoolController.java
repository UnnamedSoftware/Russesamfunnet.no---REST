package no.ntnu.unnamedsoftware.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import no.ntnu.unnamedsoftware.entity.*;
import no.ntnu.unnamedsoftware.service.SchoolService;

@CrossOrigin
@RestController
public class SchoolController {
	
	
	
	@Autowired
	private SchoolService schoolService;
	

	@RequestMapping(value="/getAllSchools", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public String getAllSchools() {
		return schoolService.getAllSchools();
		
	}
	
	@RequestMapping(value="/getSchools", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public String getSchools(@RequestParam String municipality, @RequestParam String location) {
		return schoolService.getSchools(municipality, location);
		
	}
	

}
