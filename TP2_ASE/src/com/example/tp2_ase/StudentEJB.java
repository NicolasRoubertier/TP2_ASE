package com.example.tp2_ase;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


/**
 * Session Bean implementation class StudentEJB
 */
@Stateless
public class StudentEJB implements StudentEJBRemote {

    /**
     * Default constructor. 
     */
    public StudentEJB() {
        // TODO Auto-generated constructor stub
    }
    
    ProjectEJB projectEjb = new ProjectEJB();
    
    EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("TP2_ASE");
    EntityManager em = emf.createEntityManager();
    
    public List<Student> findStudents()
    {
    	em.getTransaction().begin();
    	Query query = em.createQuery("SELECT s from Student as s");
    	em.getTransaction().commit();
    	return query.getResultList();
    	
    }
    
	public Student findStudentById(long id)
	{
		return em.find(Student.class, id);
	}
	
	public Student createStudent(Student x)
	{
		em.getTransaction().begin();
		em.persist(x);
		em.getTransaction().commit();
		return x;
		
	}
	
	public void deleteStudent(Student x)
	{
		em.getTransaction().begin();
		em.remove(x);
		em.getTransaction().commit();
	}
	
	public Student updateStudent(Student x)
	{
		em.getTransaction().begin();
		em.merge(x);
		em.getTransaction().commit();
		return x;
	}
	
	public Project createProject(Student x, String title)
	{
		Project p = new Project();
		p.setOwnerID(x.getID());
		p.setTitle(title);
		em.getTransaction().begin();
		projectEjb.createProject(p);
		em.getTransaction().commit();
		return p;
	}

}
