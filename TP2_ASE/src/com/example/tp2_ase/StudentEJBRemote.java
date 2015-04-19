package com.example.tp2_ase;

import javax.ejb.Remote;

import java.util.List;

@Remote
public interface StudentEJBRemote {

	public List<Student> findStudents();
	public Student findStudentById(long id);
	public Student createStudent(Student x);
	public void deleteStudent(Student x);
	public Student updateStudent(Student x);
	public Project createProject(Student x, String title);
	
}
