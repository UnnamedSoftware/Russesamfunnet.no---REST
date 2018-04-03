package no.ntnu.unnamedsoftware.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import no.ntnu.unnamedsoftware.entity.ErrorReport;
import no.ntnu.unnamedsoftware.entity.Feed;
import no.ntnu.unnamedsoftware.entity.Russ;
import no.ntnu.unnamedsoftware.entity.School;

@Repository
public class ErrorDAO {

	@Autowired
	private SessionFactory sessionFactory;
	

	@Transactional
	public ErrorReport createErrorReport(Russ russ, String subject, String message)
	{	
		try(Session currentSession = sessionFactory.openSession()){
		ErrorReport errorReport = new ErrorReport();
		errorReport.setErrorMessage(message);
		errorReport.setErrorSubject(subject);
		errorReport.setRussId(russ);
		currentSession.save(errorReport);
		return errorReport;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
