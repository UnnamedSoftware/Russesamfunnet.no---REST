package no.ntnu.unnamedsoftware.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import no.ntnu.unnamedsoftware.DAO.FeedDAO;
import no.ntnu.unnamedsoftware.entity.Feed;

@Service
public class FeedService {

	@Autowired
	FeedDAO feedDAO;

	@Autowired
	ObjectMapper mapper;

	@Autowired
	GroupService groupService;

	public String getSchoolFeed(Long theRussId) {
		List<Feed> schoolFeed = feedDAO.getSchoolFeed(theRussId);
		return this.writeAsJsonString(schoolFeed);
	}

	public String getGroupFeed(Long theRussId, Long groupId) {
		if (groupService.isPartOfGroup(theRussId, groupId)) {
			System.out.println("Russ is part of the group");
			return this.writeAsJsonString(feedDAO.getGroupFeed(groupId));
		}
		return null;
	}

	private String writeAsJsonString(List<Feed> feed) {
		String feedInJsonString = null;
		try {
			feedInJsonString = mapper.writeValueAsString(feed);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return feedInJsonString;
	}

	private String writeAsJsonString(Object object) {
		String feedInJsonString = null;
		try {
			feedInJsonString = mapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return feedInJsonString;
	}

	public String postFeed(Long russId, String message) {
		return writeAsJsonString(feedDAO.postFeed(russId, message));

	}

}
