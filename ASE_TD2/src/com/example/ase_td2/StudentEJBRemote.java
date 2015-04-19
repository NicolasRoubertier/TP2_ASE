package com.example.ase_td2;

import javax.ejb.Remote;

import java.util.List;

@Remote
public interface StudentEJBRemote {
	
	public Student createStudent(Student x);
	public void deleteStudent(Student x);
	public Student updateStudent(Student x);
	public Student findStudentByID(long id);
	public List<Student> findStudents();

}
