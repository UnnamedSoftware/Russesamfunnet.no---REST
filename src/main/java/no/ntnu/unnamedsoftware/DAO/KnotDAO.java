package no.ntnu.unnamedsoftware.DAO;

import java.io.IOException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import no.ntnu.unnamedsoftware.entity.Completed;
import no.ntnu.unnamedsoftware.entity.Knots;
import no.ntnu.unnamedsoftware.entity.Russ;
import no.ntnu.unnamedsoftware.entity.School;

@Repository
public class KnotDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	ObjectMapper mapper;

	@Transactional
	public String getKnots(Long theSchoolId) {
		try (Session currentSession = sessionFactory.openSession()) {
			Long schooldId = new Long(theSchoolId);
			String knotsInJsonString = null;
			Query knotQuery = currentSession.createQuery("from Knots k where k.schoolId.schoolId = :test") // ("from
																											// Knots k
																											// where
																											// (k.schoolId
																											// IS NULL)
																											// OR
																											// (k.schoolId.schoolId
																											// =
																											// :test)")
					.setParameter("test", schooldId);
			List<Knots> knots = knotQuery.list();
			try {
				knotsInJsonString = mapper.writeValueAsString(knots);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return knotsInJsonString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "getKnots error";
	}

	@Transactional
	public List<Completed> getCompleted(Long theRussId) {
		List<Completed> completed = null;
		try (Session currentSession = sessionFactory.openSession()) {
			Query completedQuery = currentSession.createQuery("from Completed c where c.russId.russId = :theRussId")
					.setParameter("theRussId", theRussId);
			completed = completedQuery.list();
			return completed;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return completed;
	}

	@Transactional
	public List<Knots> getKnotsList(Long theSchoolId) {
		List<Knots> knots = null;
		try (Session currentSession = sessionFactory.openSession()) {
			Long schoolId = new Long(theSchoolId);
			Query knotQuery = currentSession.createQuery("from Knots k where k.schoolId.schoolId = :test")
					.setParameter("test", schoolId);
			knots = knotQuery.list();
			return knots;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return knots;
	}

	@Transactional
	public List<Completed> getCompletedKnots(Long theRussId) {
		List<Completed> completed = null;
		try (Session currentSession = sessionFactory.openSession()) {
			Query completedQuery = currentSession.createQuery("from Completed c where c.russId.russId = :theRussId")
					.setParameter("theRussId", theRussId);
			completed = completedQuery.list();
			return completed;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return completed;
	}

	@Transactional
	public Knots getKnot(Long theKnotId) {
		Knots theKnot = null;
		try (Session currentSession = sessionFactory.openSession()) {
			Query theQuery = currentSession.createQuery("from Knots k where k.knotId = :theKnotId")
					.setParameter("theKnotId", theKnotId);
			theKnot = (Knots) theQuery.uniqueResult();
			return theKnot;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return theKnot;
	}

	@Transactional
	public String registerCompletedKnot(Russ theRussId, Knots theKnotId, Russ theWitnessId1, Russ theWitnessId2) {
		try (Session currentSession = sessionFactory.openSession()) {
			Completed newCompleted = new Completed();
			newCompleted.setRussId(theRussId);
			newCompleted.setKnotId(theKnotId);
			newCompleted.setWitnessId1(theWitnessId1);
			newCompleted.setWitnessId2(theWitnessId2);
			currentSession.save(newCompleted);
			return "OK";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "registerCompletedKnot error";
	}

	@Transactional
	public Russ getEmptyWitness() {
		Russ emptyWitness = new Russ();
		return emptyWitness;
	}

	@Transactional
	public Completed getCompletedKnot(int theCompletedKnotId) {
		Completed completedKnot = null;
		try (Session currentSession = sessionFactory.openSession()) {
			Query theQuery = currentSession.createQuery("from Completed c where c.completedId = :theCompletedKnotId")
					.setParameter("theCompletedKnotId", theCompletedKnotId);
			completedKnot = (Completed) theQuery.uniqueResult();
			return completedKnot;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return completedKnot;
	}

	@Transactional
	public String registerWitnessCompletedKnot(int theCompletedKnotId, Russ theWitness) {
		Long completedKnotId = new Long(theCompletedKnotId);
		try (Session currentSession = sessionFactory.openSession()) {
			Query theQuery = currentSession.createQuery("from Completed c where c.completedId = :theCompletedKnotId")
					.setParameter("theCompletedKnotId", completedKnotId);
			Completed completedKnot = (Completed) theQuery.uniqueResult();
			Russ witness1 = completedKnot.getWitnessId1();
			Russ witness2 = completedKnot.getWitnessId2();
			if (witness1 == null) {
				System.out.println("witness 1 == null");
				Query addWitness = currentSession.createSQLQuery("UPDATE completed SET witness_id1 = "
						+ theWitness.getRussId() + " WHERE completed_id = " + completedKnotId);
				addWitness.executeUpdate();
				return "OK 1";
			} else if (witness2 == null) {
				Query addWitness = currentSession.createSQLQuery("UPDATE completed SET witness_id2 = "
						+ theWitness.getRussId() + " WHERE completed_id = " + completedKnotId);
				addWitness.executeUpdate();
				return "OK 2";
			} else {
				return "Eror: Witness already registered for this knot";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Error: Something went wrong";
	}

	@Transactional
	public Knots addKnot(String knotName, String knotDescription, School school) {
		Knots knot = new Knots();
		try (Session currentSession = sessionFactory.openSession()) {
			knot.setKnotName(knotName);
			knot.setKnotDetails(knotDescription);
			knot.setSchoolId(school);
			currentSession.save(knot);
			return knot;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return knot;
	}

	@Transactional
	public String deleteKnot(Long knotId) {
		Knots knot = getKnot(knotId);
		try (Session currentSession = sessionFactory.openSession()) {
			Query deleteQuery = currentSession.createSQLQuery("delete from knots where knots.knot_id = :knotId");
			deleteQuery.setParameter("knotId", knotId);
			int result = deleteQuery.executeUpdate();
			if (result > 0) {
				return "Knot successfully deleted.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "An error occured";
	}
	
	@Transactional
	public String unRegisterCompletedKnot(Long russId, Long knotId) {
		try (Session currentSession = sessionFactory.openSession()) {
			Query deleteQuery = currentSession.createSQLQuery("delete from completed where completed.knot_id = :knotId and completed.russ_id =:russId");
			deleteQuery.setParameter("knotId", knotId).setParameter("russId", russId);
			int result = deleteQuery.executeUpdate();
			if (result > 0) {
				return "Knot successfully removed.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "An error occured";
	}

	@Transactional
	public Knots updateKnot(Long knotId, String knotName, String knotDescription) {
		//Knots knot = getKnot(knotId);
		//System.out.println(knot.getKnotDetails());
		try (Session currentSession = sessionFactory.openSession()) {
			//Query addWitness = currentSession.createSQLQuery("UPDATE completed SET witness_id1 = "
			//		+ theWitness.getRussId() + " WHERE completed_id = " + completedKnotId);
			//addWitness.executeUpdate();
			
			Query theQuery = currentSession.createSQLQuery("UPDATE knots SET knot_name='"+knotName+"' WHERE knot_id="+knotId);
			theQuery.executeUpdate();
			
			Query theQuery2 = currentSession.createSQLQuery("UPDATE knots SET knot_details='"+knotDescription+"' WHERE knot_id="+knotId);
			theQuery2.executeUpdate();
			//knot.setKnotDetails(knotDescription);
			//knot.setKnotName(knotName);
			//currentSession.update(knot);
			//knot.getKnotDetails();
			return getKnot(knotId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getKnot(knotId);
	}

	@Transactional
	public Long getKnotId(String knotName) {
		Long errorCode = new Long(9001);
		try (Session currentSession = sessionFactory.openSession()) {
			Query knotQuery = currentSession.createQuery("from Knots k where k.knotName = :knotName")
					.setParameter("knotName", knotName);
			Knots knot = (Knots) knotQuery.uniqueResult();
			return knot.getKnotId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return errorCode;

	}
}
