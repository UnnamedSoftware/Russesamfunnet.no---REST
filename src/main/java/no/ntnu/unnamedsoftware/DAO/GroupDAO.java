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
import no.ntnu.unnamedsoftware.entity.Russ;

@Repository
public class GroupDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public List<Group> getGroups(Long russId)
	{
		List<Group> groups = null;
		try(Session currentSession = sessionFactory.openSession()){
			Query groupQuery = currentSession.createQuery("from RussGroup g where (g.russId.russId = :russId)")
					.setParameter("russId", russId);
			groups = groupQuery.list();
			return groups;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return groups;
	}
	
	@Transactional
	public List<Russ> getGroupRuss(Long groupId)
	{
		System.out.println("In getGroupRuss method");
		List<Russ> groupRuss = null;
		try(Session currentSession = sessionFactory.openSession()){
			Query groupQuery = currentSession.createQuery("select g.russId from RussGroup g where (g.groupId.groupId = :groupId)")
					.setParameter("groupId", groupId);
			groupRuss = groupQuery.list();
			System.out.println("After query");
			if(groupRuss != null)
			{
				Iterator it = groupRuss.iterator();
				while(it.hasNext())
				{
					Russ russ = (Russ) it.next();
					System.out.println(russ.getFirstName());
				}
			} else if(groupRuss == null)
			{
				System.out.println("There is no russ in this group");
			}
			return groupRuss;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return groupRuss;
	}

}
