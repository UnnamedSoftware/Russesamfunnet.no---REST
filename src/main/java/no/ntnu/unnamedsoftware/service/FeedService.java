package no.ntnu.unnamedsoftware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import no.ntnu.unnamedsoftware.DAO.FeedDAO;

@Service
public class FeedService {
	
	@Autowired
	FeedDAO feedDAO;
	
	public String getSchoolFeed(int theRussId)
	{
		int theSchoolId = feedDAO.getSchoolId(theRussId);
		return feedDAO.getSchoolFeed(theSchoolId);
	}

}
