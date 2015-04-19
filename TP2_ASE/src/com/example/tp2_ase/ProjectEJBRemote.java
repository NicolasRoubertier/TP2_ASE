package com.example.tp2_ase;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface ProjectEJBRemote {
	
	public List<Project> findProjects();
	public Project findProjectByID(long id);
	public Project createProject(Project x);
	public void deleteProject(Project x);
	public Project updateProject(Project x);

}
