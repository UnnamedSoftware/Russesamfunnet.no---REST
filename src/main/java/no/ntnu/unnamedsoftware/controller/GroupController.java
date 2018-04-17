package no.ntnu.unnamedsoftware.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import no.ntnu.unnamedsoftware.service.AccessTokenDecrypterAndParser;
import no.ntnu.unnamedsoftware.service.GroupService;
@CrossOrigin
@Controller
public class GroupController {
	
	@Autowired
	GroupService groupService;
	
	@Autowired
	private AccessTokenDecrypterAndParser tokenParser;
	
	
	@RequestMapping(value="/groups", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	@ResponseBody
	public String getGroupfeed(@RequestParam String accessToken, @RequestParam String type) {
		Long theRussId = null;
		if (type.equals("facebook")) {
			theRussId = tokenParser.decryptFacebookToken(accessToken);
		}else if(type.equals("russesamfunnet"))
		{
			theRussId = tokenParser.getRussId(accessToken);
		}	
		return groupService.getGroups(theRussId);
		
	}
	
	@RequestMapping(value="/createGroup", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	@ResponseBody
	public String createGroup(@RequestParam String accessToken, @RequestParam String type, @RequestParam String groupName) {
		Long theRussId = null;
		if (type.equals("facebook")) {
			theRussId = tokenParser.decryptFacebookToken(accessToken);
		}else if(type.equals("russesamfunnet"))
		{
			theRussId = tokenParser.getRussId(accessToken);
		}	
		return groupService.createGroup(theRussId, groupName);
		
	}
	
	@RequestMapping(value="/deleteGroup", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	@ResponseBody
	public String deleteGroup(@RequestParam String accessToken, @RequestParam String type, @RequestParam Long groupId) {
		Long theRussId = null;
		if (type.equals("facebook")) {
			theRussId = tokenParser.decryptFacebookToken(accessToken);
		}else if(type.equals("russesamfunnet"))
		{
			theRussId = tokenParser.getRussId(accessToken);
		}	
		return groupService.deleteGroup(theRussId, groupId);
		
	}
	
	@RequestMapping(value="/addGroupMember", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	@ResponseBody
	public String addGroupMember(@RequestParam String accessToken, @RequestParam String type, @RequestParam Long groupId, @RequestParam Long russId) {
		Long theRussId = null;
		if (type.equals("facebook")) {
			theRussId = tokenParser.decryptFacebookToken(accessToken);
		}else if(type.equals("russesamfunnet"))
		{
			theRussId = tokenParser.getRussId(accessToken);
		}	
		return groupService.addGroupMember(theRussId, groupId, russId);
		
	}
	
	@RequestMapping(value="/removeGroupMember", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	@ResponseBody
	public String removeGroupMember(@RequestParam String accessToken, @RequestParam String type, @RequestParam Long groupId, @RequestParam Long russId) {
		Long theRussId = null;
		if (type.equals("facebook")) {
			theRussId = tokenParser.decryptFacebookToken(accessToken);
		}else if(type.equals("russesamfunnet"))
		{
			theRussId = tokenParser.getRussId(accessToken);
		}	
		return groupService.removeGroupMember(theRussId, groupId, russId);
		
	}
	
	

}
