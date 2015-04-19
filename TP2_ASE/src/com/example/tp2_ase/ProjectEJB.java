package com.example.tp2_ase;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


@Stateless
public class ProjectEJB implements ProjectEJBRemote {

    /**
     * Default constructor. 
     */
    public ProjectEJB() {
        
    }
    
    EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("TP2_ASE");
    EntityManager em = emf.createEntityManager();
    
    public List<Project> findProjects()
    {
    	em.getTransaction().begin();
    	Query query = em.createQuery("SELECT p from Project as p");
    	em.getTransaction().commit();
    	return query.getResultList();
    }
    
	public Project findProjectByID(long id)
	{
		return em.find(Project.class, id);
	}	
	
	public Project createProject(Project x)
	{
		em.getTransaction().begin();
		em.persist(x);
		em.getTransaction().commit();
		return x;
	}
	
	public void deleteProject(Project x)
	{
		em.getTransaction().begin();
		em.remove(x);
		em.getTransaction().commit();
	}
	
	public Project updateProject(Project x)
	{
		em.getTransaction().begin();
		em.merge(x);
		em.getTransaction().commit();
		return x;
	}	

}
