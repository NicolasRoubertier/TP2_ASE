package com.example.tp2_ase;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.TextField;
import java.util.List;

@SuppressWarnings("serial")
@Theme("tp2_ase")
public class Tp2_aseUI extends UI {

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		final Label information = new Label("");
		layout.addComponent(information);
		setContent(layout);
		layout.setSpacing(true);
		
		final StudentEJB studentEjb = new StudentEJB();
		final ProjectEJB projectEjb = new ProjectEJB();
		
		/* Create  student */
		
		Button buttonCreate = new Button("Create student");
		buttonCreate.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				final VerticalLayout layoutCreateStudent = new VerticalLayout();
				final Label info = new Label("");
				final TextField name = new TextField("Student's name");
				final TextField age = new TextField("Student's age");
				final TextField address = new TextField("Student's address");
				layoutCreateStudent.setSpacing(true);
				layoutCreateStudent.setMargin(true);
				layoutCreateStudent.addComponent(info);
				layoutCreateStudent.addComponent(name);
				layoutCreateStudent.addComponent(age);
				layoutCreateStudent.addComponent(address);
				setContent(layoutCreateStudent);
				final Button confirmCreation = new Button("Confirm");
				confirmCreation.addClickListener(new Button.ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						if(name.getValue().equals("") || age.getValue().equals("") || address.getValue().equals(""))
						{
							info.setValue("Fill all the fields");
						}
						else
						{
							info.setValue("");	
							Student s = new Student();
							s.setAge(Integer.parseInt(age.getValue()));
							s.setName(name.getValue());
							s.setAddress(address.getValue());
							try
							{
								studentEjb.createStudent(s);
								information.setValue("The student has been successfully added into the database");
							}
							catch (Exception e)
							{
								information.setValue("Error");
							}
						
							setContent(layout);
						}
					}
				});
				layoutCreateStudent.addComponent(confirmCreation);
			}
		});
		layout.addComponent(buttonCreate);
		layout.setComponentAlignment(buttonCreate, Alignment.MIDDLE_CENTER);
		
		/* Remove student */
		
		Button buttonRemove = new Button("Remove");
		buttonRemove.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				final VerticalLayout layoutRemoveStudent = new VerticalLayout();
				final TextField ID = new TextField("Student's ID");
				layoutRemoveStudent.setMargin(true);
				layoutRemoveStudent.setSpacing(true);
				layoutRemoveStudent.addComponent(ID);
				setContent(layoutRemoveStudent);
				Button findStudentToRemove = new Button("Find");
				findStudentToRemove.addClickListener(new Button.ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						try
						{
							Student studentToRemove = studentEjb.findStudentById(Long.parseLong(ID.getValue()));
							if(projectEjb.findProjects().size() != 0)
							{
								List<Project> l = projectEjb.findProjects();
								for(int i = 0 ; i<l.size(); i++)
								{
									if(l.get(i).getOwnerID() == studentToRemove.getID())
									{
										projectEjb.deleteProject(l.get(i));
									}
								}
								studentEjb.deleteStudent(studentToRemove);
								information.setValue("The student and their project have been deleted from their databases");
							}
							else
							{
							studentEjb.deleteStudent(studentToRemove);
							information.setValue("The student has been deleted from the database");
							}
						}
						catch (Exception e)
						{
							information.setValue("This student is not in the database");
						}
						setContent(layout);
					}
				});
				layoutRemoveStudent.addComponent(findStudentToRemove);
			}
		});
		layout.addComponent(buttonRemove);
		layout.setComponentAlignment(buttonRemove, Alignment.MIDDLE_CENTER);
		
		/* Update student */
		
		Button buttonUpdate = new Button("Update");
		buttonUpdate.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				final VerticalLayout layoutUpdateAStudent = new VerticalLayout();
				layoutUpdateAStudent.setSpacing(true);
				layoutUpdateAStudent.setMargin(true);
				layoutUpdateAStudent.addComponent(new Label("Which student do you want to update?"));
				final TextField ID = new TextField("Student's ID");
				layoutUpdateAStudent.addComponent(ID);
				Button buttonFind = new Button("Find");
				setContent(layoutUpdateAStudent);
				buttonFind.addClickListener(new Button.ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						
						
						
						Student s = studentEjb.findStudentById(Long.parseLong(ID.getValue()));
						
						if(studentEjb.findStudentById(Long.parseLong(ID.getValue())) !=null)
						{
						final VerticalLayout layoutUpdateAStudent2 = new VerticalLayout();
						layoutUpdateAStudent2.setSpacing(true);
						layoutUpdateAStudent2.setMargin(true);
						final TextField name = new TextField("Student's name");
						final TextField age = new TextField("Student's age");
						final TextField address = new TextField("Student's address");
						layoutUpdateAStudent2.setSpacing(true);
						layoutUpdateAStudent2.setMargin(true);
						layoutUpdateAStudent2.addComponent(name);
						layoutUpdateAStudent2.addComponent(age);
						layoutUpdateAStudent2.addComponent(address);
						setContent(layoutUpdateAStudent2);
						final Button confirmUpdate = new Button("Confirm");
						confirmUpdate.addClickListener(new Button.ClickListener() {
							
							@Override
							public void buttonClick(ClickEvent event) {
								if(!name.getValue().equals(""))
								{	
									s.setName(name.getValue());
								}
								if(!age.getValue().equals(""))
								{
									s.setAge(Integer.parseInt(age.getValue()));
								}
								if(!address.getValue().equals(""))
								{
									s.setAddress(address.getValue());
								}
								try
								{
									studentEjb.updateStudent(s);
									information.setValue("The update of the student has been successful.");
								}
								catch (Exception e)
								{
									information.setValue("Error");
								}
								setContent(layout);
							}
						});
						layoutUpdateAStudent2.addComponent(confirmUpdate);
						}
						else
						{
							information.setValue("This student is not in the database");
							setContent(layout);
						}
					}
				});
				layoutUpdateAStudent.addComponent(buttonFind);
			}
		});
		layout.addComponent(buttonUpdate);
		layout.setComponentAlignment(buttonUpdate, Alignment.MIDDLE_CENTER);
		
		/* Find a student by ID */
		
		Button buttonFindStudentById = new Button("Find a student by ID");
		buttonFindStudentById.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				final VerticalLayout layoutFindAStudent = new VerticalLayout();
				layoutFindAStudent.setSpacing(true);
				layoutFindAStudent.setMargin(true);
				final TextField ID = new TextField("Student's ID");
				layoutFindAStudent.addComponent(ID);
				Button buttonFind = new Button("Find");
				setContent(layoutFindAStudent);
				buttonFind.addClickListener(new Button.ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						String result = "";
						try
						{
							Student s = studentEjb.findStudentById(Long.parseLong(ID.getValue()));
							result = "ID = " + s.getID() + ", name = " + s.getName() + ", age = " + s.getAge() + ", address = " + s.getAddress();
						}
						catch (Exception e)
						{
							result = "No student has this ID.";
						}
						information.setValue(result);
						setContent(layout);
						
					}
				});
				layoutFindAStudent.addComponent(buttonFind);
			}
		});
		layout.addComponent(buttonFindStudentById);
		layout.setComponentAlignment(buttonFindStudentById, Alignment.MIDDLE_CENTER);
		
		/* Find all students */
		
		Button buttonFindAllStudents = new Button("Get every student");
		buttonFindAllStudents.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String result = "";
				try
				{
					List<Student> l = studentEjb.findStudents();
					if(l.size() !=0)
					{
						for(int i =0; i<l.size(); i++)
						{
							if (i < l.size()-1)
							{
								result = result + "ID = " + l.get(i).getID() + ", name = " + l.get(i).getName() + ", age = " + l.get(i).getAge() + ", address = " + l.get(i).getAddress() + "   ||   ";
							}
							else
							{
								result = result + "ID = " + l.get(i).getID() + ", name = " + l.get(i).getName() + ", age = " + l.get(i).getAge() + ", address = " + l.get(i).getAddress();
							}
						}
					}
					else
					{
						result = "No student in the database.";
					}
				}
				catch (Exception e)
				{
					result = "No student in the database.";
				}
				information.setValue(result);
				
			}
		});
		layout.addComponent(buttonFindAllStudents);
		layout.setComponentAlignment(buttonFindAllStudents, Alignment.MIDDLE_CENTER);
		
		/* Create project */
		
		Button buttonCreateProject = new Button("Create project");
		buttonCreateProject.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				final VerticalLayout layoutCreateProject = new VerticalLayout();
				layoutCreateProject.setSpacing(true);
				layoutCreateProject.setMargin(true);
				final Label info = new Label("");
				final TextField studentID = new TextField("Student's ID");
				final TextField title = new TextField("Project's title");
				layoutCreateProject.setSpacing(true);
				layoutCreateProject.setMargin(true);
				layoutCreateProject.addComponent(info);
				layoutCreateProject.addComponent(studentID);
				layoutCreateProject.addComponent(title);
				setContent(layoutCreateProject);
				final Button confirmCreation = new Button("Confirm");
				confirmCreation.addClickListener(new Button.ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						if(studentID.getValue().equals("") || title.getValue().equals(""))
						{
							info.setValue("Fill all the fields");
						}
						else
						{
							info.setValue("");
							try
							{
								studentEjb.createProject(studentEjb.findStudentById(Long.parseLong(studentID.getValue())), title.getValue());
						
								information.setValue("The project has been successfully added into the database");
							}
							catch (Exception e)
							{
								information.setValue("Error");
							}
							setContent(layout);
						
						}
					}	
				});
				layoutCreateProject.addComponent(confirmCreation);
			}
		});
		layout.addComponent(buttonCreateProject);
		layout.setComponentAlignment(buttonCreateProject, Alignment.MIDDLE_CENTER);
	
	/* Remove project */ 
		
	Button buttonRemoveProject = new Button("Remove project");
	buttonRemoveProject.addClickListener(new Button.ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			
			final VerticalLayout layoutRemoveProject = new VerticalLayout();
			layoutRemoveProject.setSpacing(true);
			layoutRemoveProject.setMargin(true);
			final TextField ID = new TextField("Project's ID");
			layoutRemoveProject.addComponent(ID);
			setContent(layoutRemoveProject);
			Button findProjectToRemove = new Button("Find");
			findProjectToRemove.addClickListener(new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					try
					{
						projectEjb.deleteProject(projectEjb.findProjectByID(Long.parseLong(ID.getValue())));
					    information.setValue("The project has been deleted from their databases");
					}
					catch (Exception e)
					{
						information.setValue("The project is not in the database");
						
					}
					setContent(layout);
				}
			});
			layoutRemoveProject.addComponent(findProjectToRemove);
		}
	});
	layout.addComponent(buttonRemoveProject);
	layout.setComponentAlignment(buttonRemoveProject, Alignment.MIDDLE_CENTER);
	
	/* Update project */
	
	Button buttonUpdateProject = new Button("Update project");
	buttonUpdateProject.addClickListener(new Button.ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			
			final VerticalLayout layoutUpdateAProject = new VerticalLayout();
			layoutUpdateAProject.setSpacing(true);
			layoutUpdateAProject.setMargin(true);
			layoutUpdateAProject.addComponent(new Label("Which project do you want to update?"));
			final TextField ID = new TextField("Project's ID");
			layoutUpdateAProject.addComponent(ID);
			Button buttonFind = new Button("Find");
			setContent(layoutUpdateAProject);
			buttonFind.addClickListener(new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					Project p = projectEjb.findProjectByID(Long.parseLong(ID.getValue()));
					if(projectEjb.findProjectByID(Long.parseLong(ID.getValue())) != null)
					{	
					final VerticalLayout layoutUpdateAProject2 = new VerticalLayout();
					final Label info = new Label("");
					layoutUpdateAProject2.setSpacing(true);
					layoutUpdateAProject2.setMargin(true);
					final TextField title = new TextField("Project's title");
					final TextField ownerID = new TextField("Owner's ID");
					layoutUpdateAProject2.setSpacing(true);
					layoutUpdateAProject2.setMargin(true);
					layoutUpdateAProject2.addComponent(info);
					layoutUpdateAProject2.addComponent(title);
					layoutUpdateAProject2.addComponent(ownerID);
					setContent(layoutUpdateAProject2);
					final Button confirmUpdate = new Button("Confirm");
					confirmUpdate.addClickListener(new Button.ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							if(title.getValue().equals("") || ownerID.getValue().equals(""))
							{
								info.setValue("Fill all the fields");
							}
							else
							{	
								info.setValue("");
								if(studentEjb.findStudentById(Long.parseLong(ownerID.getValue())) != null)
								{
								
									if(!title.getValue().equals(""))
									{	
										p.setTitle(title.getValue());
									}
									if(!ownerID.getValue().equals(""))
									{
										p.setOwnerID(Long.parseLong(ownerID.getValue()));
									}
						
							
									try
									{
										projectEjb.updateProject(p);
										information.setValue("The update of the project has been successful.");
									}
									catch (Exception e)
									{
							    	information.setValue("Error");
									}
								}
								else
								{
									information.setValue("The student is not in the database");
								}
								setContent(layout);
							}	
						}
					});
					layoutUpdateAProject2.addComponent(confirmUpdate);
					}
					else
					{
						information.setValue("This project is not in the database");
						setContent(layout);
					}
				}
			});
			layoutUpdateAProject.addComponent(buttonFind);
		}
	});
	layout.addComponent(buttonUpdateProject);
	layout.setComponentAlignment(buttonUpdateProject, Alignment.MIDDLE_CENTER);
	
	/* Find a project by ID */
	
	Button buttonFindProjectById = new Button("Find a project by ID");
	buttonFindProjectById.addClickListener(new Button.ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			final VerticalLayout layoutFindAProject = new VerticalLayout();
			layoutFindAProject.setSpacing(true);
			layoutFindAProject.setMargin(true);
			final TextField ID = new TextField("Project's ID");
			layoutFindAProject.addComponent(ID);
			Button buttonFind = new Button("Find");
			setContent(layoutFindAProject);
			buttonFind.addClickListener(new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					String result = "";
					try
					{
						Project p = projectEjb.findProjectByID(Long.parseLong(ID.getValue()));
						result = result + "ID = " + p.getID() + ", title = " + p.getTitle() + ", ownerID = " + p.getOwnerID();
					}
					catch (Exception e)
					{
						result = "No project has this ID.";
					}
					information.setValue(result);
					setContent(layout);
					
				}
			});
			layoutFindAProject.addComponent(buttonFind);
		}
	});
	layout.addComponent(buttonFindProjectById);
	layout.setComponentAlignment(buttonFindProjectById, Alignment.MIDDLE_CENTER);
	
	/* Find all projects */
	
	Button buttonFindAllProjects = new Button("Get every project");
	buttonFindAllProjects.addClickListener(new Button.ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			String result = "";
			try
			{
				List<Project> l = projectEjb.findProjects();
				if(l.size() !=0)
				{
					for(int i =0; i<l.size(); i++)
					{
						if (i < l.size()-1)
						{
							result = result + "ID = " + l.get(i).getID() + ", title = " + l.get(i).getTitle() + ", ownerID = " +  "   ||   ";
						}
						else
						{
						result = result + "ID = " + l.get(i).getID() + ", title = " + l.get(i).getTitle() + ", ownerID = " + l.get(i).getOwnerID();
						}
					}
				}
				else
				{
					result = "No project in the database.";
				}
			}
			catch (Exception e)
			{
				result = "No project in the database.";
			}
			information.setValue(result);
			
		}
	});
	layout.addComponent(buttonFindAllProjects);
	layout.setComponentAlignment(buttonFindAllProjects, Alignment.MIDDLE_CENTER);
  }
}