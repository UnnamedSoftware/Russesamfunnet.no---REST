package no.ntnu.unnamedsoftware.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import no.ntnu.unnamedsoftware.entity.Feed;
import no.ntnu.unnamedsoftware.entity.Russ;
import no.ntnu.unnamedsoftware.entity.School;

@Repository
public class RussDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	SchoolDAO schoolDAO;

	@Transactional
	public List<Russ> getRuss() {
		List<Russ> userInfo = null;

		try (Session currentSession = sessionFactory.openSession()) {
			Query theQuery = currentSession.createQuery("from Russ r");

			userInfo = theQuery.list();
			;
			// currentSession.close();
			return userInfo;
			// return JSON
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userInfo;
	}

	@Transactional
	public Russ getUserRussFromId(Long theRussId) {
		Russ russ = null;
		try (Session currentSession = sessionFactory.openSession()) {
			Query russQuery = currentSession.createQuery("from Russ r where (r.russId = :russId)")
					.setParameter("russId", theRussId);
			russ = (Russ) russQuery.uniqueResult();
			// currentSession.close();
			return russ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return russ;
	}

	@Transactional
	public Russ getUserRussFromEmail(String email) {
		Russ russ = null;
		try (Session currentSession = sessionFactory.openSession()) {
			Query russQuery = currentSession.createQuery("from Russ r where r.email =:email").setParameter("email",
					email);
			russ = (Russ) russQuery.uniqueResult();

			return russ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return russ;
	}

	@Transactional
	public Russ getRussFromFacebookId(String userId) {
		System.out.println(userId);
		Long russId = Long.valueOf(userId);
		try (Session currentSession = sessionFactory.openSession()) {
			Query russQuery = currentSession.createQuery("from Russ r where r.russIdAlt = :userId")
					.setParameter("userId", russId);
			Russ russ = (Russ) russQuery.uniqueResult();
			System.out.println(russ.getFirstName());
			return russ;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}

	@Transactional
	public List<Russ> getAllRussAtSchool(Long schoolId) {
		try (Session currentSession = sessionFactory.openSession()) {
			Query russQuery = currentSession.createQuery("from Russ r where r.schoolId.schoolId = :schoolId")
					.setParameter("schoolId", schoolId);
			List<Russ> russAtSchool = russQuery.list();
			System.out.println("russAtSchool " + russAtSchool.size());
			return russAtSchool;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}

	public List<Russ> getAllRussAtSchoolStatusFalse(Long schoolId) {
		try (Session currentSession = sessionFactory.openSession()) {
			Query russQuery = currentSession
					.createQuery("from Russ r where (r.schoolId.schoolId = :schoolId) AND r.russStatus = :russStatus")
					.setParameter("schoolId", schoolId).setParameter("russStatus", "false");
			List<Russ> russAtSchool = russQuery.list();
			System.out.println("russAtSchool " + russAtSchool.size());
			return russAtSchool;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}

	public String confirmRuss(Long russToConfirm) {
		try (Session currentSession = sessionFactory.openSession()) {
			Query russQuery = currentSession
					.createSQLQuery("UPDATE russ SET russ_status='confirmed' WHERE russ_id=" + russToConfirm);
			int test = russQuery.executeUpdate();
			return String.valueOf(test);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}

	public String unConfirmRuss(Long russToUnconfirm) {
		try (Session currentSession = sessionFactory.openSession()) {
			Query russQuery = currentSession
					.createSQLQuery("UPDATE russ SET russ_status='false' WHERE russ_id=" + russToUnconfirm);
			int test = russQuery.executeUpdate();
			return String.valueOf(test);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}

	public Russ registerAdmin(Long russId) {
		try (Session currentSession = sessionFactory.openSession()) {
			Query russQuery = currentSession
					.createSQLQuery("UPDATE russ SET russ_role='admin' WHERE russ_id=" + russId);
			russQuery.executeUpdate();
			return this.getUserRussFromId(russId);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}

	public String deleteUser(Long russToDelete) {
		try (Session currentSession = sessionFactory.openSession()) {
			Query russQuery = currentSession.createSQLQuery("DELETE russ WHERE russ_id=" + russToDelete);
			russQuery.executeUpdate();
			return "Russ successfully deleted.";
		} catch (Exception e) {
			e.getStackTrace();
			return "An error occured";
		}
	}

	public List<Russ> searchForRuss(Long russId, String parameter) {
		Long schoolId = schoolDAO.getSchoolId(russId);
		try (Session currentSession = sessionFactory.openSession()) {
			/*Query russQuery = currentSession.createSQLQuery("SELECT * FROM russ WHERE (first_name LIKE '" + parameters + "%' or "
																					+ "last_name  LIKE '" + parameters + "%' or "
																					+ "russ_status  LIKE '" + parameters + "%' or "
																					+ "email  LIKE '" + parameters + "%' or "
																					+ "russ_role  LIKE '" + parameters + "%') and "
																					+ "school_id=" + schoolId);
			*/
			Query russQuery = currentSession
					.createQuery("from Russ r where (r.firstName like '"+ parameter + "%' or "
							+ "r.lastName like '"+ parameter + "%' or "
							+ "r.russStatus like '"+ parameter + "%' or "
							+ "r.email like '"+ parameter + "%' or "
							+ "r.russRole like '"+ parameter + "%') " 
							+ "and r.schoolId.schoolId =:schoolId")
					.setParameter("schoolId", schoolId);//.setParameter("parameter", parameter);
			List<Russ> russ = russQuery.list();
			return russ;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}

}
