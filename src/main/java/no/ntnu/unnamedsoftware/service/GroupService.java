package no.ntnu.unnamedsoftware.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import no.ntnu.unnamedsoftware.DAO.GroupDAO;
import no.ntnu.unnamedsoftware.DAO.RussDAO;
import no.ntnu.unnamedsoftware.entity.Feed;
import no.ntnu.unnamedsoftware.entity.Group;
import no.ntnu.unnamedsoftware.entity.Russ;

@Service
public class GroupService {

	@Autowired
	ObjectMapper mapper;

	@Autowired
	GroupDAO groupDAO;

	@Autowired
	RussDAO russDAO;

	private String writeObjectAsJsonString(Object object) {
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

	private String writeAsJsonString(List<Group> object) {
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

	public String getGroups(Long russId) {
		return writeAsJsonString(groupDAO.getGroups(russId));
	}

	public Boolean isPartOfGroup(Long russId, Long groupId) {
		List<Russ> group = groupDAO.getGroupRuss(groupId);
		if (group.contains(russDAO.getUserRussFromId(russId))) {
			return true;
		}
		return false;
	}

}
