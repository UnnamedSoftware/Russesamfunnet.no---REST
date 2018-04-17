package no.ntnu.unnamedsoftware.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import no.ntnu.unnamedsoftware.entity.Russ;

@Repository
public class CompletedDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	ObjectMapper mapper;
	
	public List<Russ> getRussThatHaveCompletedKnot(Long knotId)
	{
		List<Russ> russ = null;
		try(Session currentSession = sessionFactory.openSession()){
			Query completedQuery = currentSession.createQuery("select c.russId from Completed c where (c.knotId.knotId = :knotId)")
					.setParameter("knotId", knotId);
			russ = completedQuery.list();
			
			//currentSession.close();
			return russ;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return russ;
	}

}
