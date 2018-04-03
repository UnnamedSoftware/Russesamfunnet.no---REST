package no.ntnu.unnamedsoftware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.ntnu.unnamedsoftware.DAO.ErrorDAO;

@Service
public class ErrorService {

	@Autowired
	ErrorDAO errorDAO;
}
