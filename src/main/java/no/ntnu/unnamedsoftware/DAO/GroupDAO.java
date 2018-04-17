package no.ntnu.unnamedsoftware.DAO;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import no.ntnu.unnamedsoftware.entity.Feed;
import no.ntnu.unnamedsoftware.entity.Group;
import no.ntnu.unnamedsoftware.entity.Knots;
import no.ntnu.unnamedsoftware.entity.Russ;
import no.ntnu.unnamedsoftware.entity.RussGroup;
import no.ntnu.unnamedsoftware.entity.Scoreboard;

@Repository
public class GroupDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public List<Group> getGroups(Long russId) {
		List<Group> groups = null;
		try (Session currentSession = sessionFactory.openSession()) {
			Query groupQuery = currentSession.createQuery("from RussGroup g where (g.russId.russId = :russId)")
					.setParameter("russId", russId);
			groups = groupQuery.list();
			return groups;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return groups;
	}

	@Transactional
	public Group getGroup(Long groupId) {
		Group group = null;
		try (Session currentSession = sessionFactory.openSession()) {
			Query groupQuery = currentSession.createQuery("from Group g where (g.groupId = :groupId)")
					.setParameter("groupId", groupId);
			group = (Group) groupQuery.uniqueResult();
			return group;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return group;
	}

	@Transactional
	public List<Russ> getGroupRuss(Long groupId) {
		System.out.println("In getGroupRuss method");
		List<Russ> groupRuss = null;
		try (Session currentSession = sessionFactory.openSession()) {
			Query groupQuery = currentSession
					.createQuery("select g.russId from RussGroup g where (g.groupId.groupId = :groupId)")
					.setParameter("groupId", groupId);
			groupRuss = groupQuery.list();
			System.out.println("After query");
			if (groupRuss != null) {
				Iterator it = groupRuss.iterator();
				while (it.hasNext()) {
					Russ russ = (Russ) it.next();
					System.out.println(russ.getFirstName());
				}
			} else if (groupRuss == null) {
				System.out.println("There is no russ in this group");
			}
			return groupRuss;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return groupRuss;
	}

	@Transactional
	public String createGroup(Russ russ, String groupName) {
		Group group = new Group();
		RussGroup russGroup = new RussGroup();
		try (Session currentSession = sessionFactory.openSession()) {
			group.setGroupName(groupName);
			currentSession.save(group);
			russGroup.setGroupId(group);
			russGroup.setRussId(russ);
			currentSession.save(russGroup);
			// currentSession.close();
			return "Group successfully created";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "An error occured";
	}

	@Transactional
	public String deleteGroup(Long groupId) {
		try (Session currentSession = sessionFactory.openSession()) {
			if (deleteRussGroup(groupId) > 0) {
				Query deleteQuery2 = currentSession.createSQLQuery("delete from friend_group where friend_group.group_id=" + groupId);
				int result2 = deleteQuery2.executeUpdate();
				if (result2 > 0) {
					return "Group successfully deleted.";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "An error occured";
	}

	@Transactional
	public int deleteRussGroup(Long groupId) {
		try (Session currentSession = sessionFactory.openSession()) {
			Query deleteQuery1 = currentSession
					.createSQLQuery("delete from russ_group where russ_group.group_id=" + groupId);
			int result1 = deleteQuery1.executeUpdate();
			return result1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Transactional
	public String addGroupMember(Russ russ, Long groupId) {
		RussGroup russGroup = new RussGroup();
		try (Session currentSession = sessionFactory.openSession()) {
			russGroup.setRussId(russ);
			russGroup.setGroupId(getGroup(groupId));
			currentSession.save(russGroup);

			// currentSession.close();
			return "New group member successfully added";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "An error occured";
	}
	
	@Transactional
	public String removeGroupMember(Russ russ, Long groupId) {
		try (Session currentSession = sessionFactory.openSession()) {
			Query deleteQuery1 = currentSession
					.createSQLQuery("delete from russ_group where russ_group.group_id=" + groupId + " and russ_group.russ_id = " + russ.getRussId());
			int result1 = deleteQuery1.executeUpdate();
			return "Russ removed from group";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}
		
	}

}
