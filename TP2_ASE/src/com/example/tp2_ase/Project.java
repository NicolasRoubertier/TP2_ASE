package com.example.tp2_ase;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "project")
public class Project {
	
	private static final long serialVersionUID = 1L; 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private long projectID;
	private String title;
	private long ownerID;
	
	public Project()
	{
		super();
	}
	
	public long getID()
	{
		return this.projectID;
	}
	
	public void setID(long id)
	{
		this.projectID = id;
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public long getOwnerID()
	{
		return this.ownerID;
	}
	
	public void setOwnerID(long id)
	{
		this.ownerID = id;
	}


}
