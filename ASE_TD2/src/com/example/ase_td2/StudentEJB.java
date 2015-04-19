package com.example.ase_td2;

import javax.ejb.Stateless;
import java.util.List;
import javax.persistence.*;

@Stateless
public class StudentEJB implements StudentEJBRemote{
	
	@PersistenceContext(unitName = "ASE_TD2")
	private EntityManager em;
	
	public Student createStudent(Student x)
	{
		em.persist(x);
		return x;
	}
	
	public void deleteStudent(Student x)
	{
		em.remove(x);
	}
	
	public Student updateStudent(Student x)
	{
		em.merge(x);
		return x;
	}
	
	public Student findStudentByID(long id)
	{
		Query query = em.createQuery("SELECT s from Student s WHERE s.getID() =:id");
		return (Student) query.getResultList().get(0);
	}
	
	public List<Student> findStudents()
	{
		Query query = em.createQuery("SELECT s from Student as s");
		return query.getResultList();
	}

}
