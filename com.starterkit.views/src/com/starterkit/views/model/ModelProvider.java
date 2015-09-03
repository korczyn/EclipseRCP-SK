package com.starterkit.views.model;

import java.util.ArrayList;
import java.util.List;

import com.starterkit.utils.ListUtils;

public enum ModelProvider{
	INSTANCE;
	
	private List<Task> tasks;
	private List<Task> archive;
	
	private ModelProvider(){
		tasks = new ArrayList<Task>();
		tasks.add(new Task(1L, "Kill the Zohan", "In progress", "03/10/1999", "He's in America"));
		tasks.add(new Task(2L, "Do some pushups", "In progress", "12/05/2010", "About 100 will do"));
		tasks.add(new Task(3L, "Sell stocks", "Done", "05/06/1995", "Sell everything!!!"));
		
		archive = new ArrayList<Task>();
		archive.add(new Task(4L, "New shoes", "Canceled", "03/09/2015", "For hammer throw"));
		archive.add(new Task(5L, "Collect in laws from airport", "Failed", "03/09/2015", "Be 30min late"));
		archive.add(new Task(6L, "Read a book", "Not started", "03/09/2015", "Dishwasher manual for example"));
	}
	
	public void changeTask(Task t){
		for (Task task : tasks) {
			if(task.getId().equals(t.getId())){
				task.setName(t.getName());
				task.setStatus(t.getStatus());
				task.setDate(t.getDate());
				task.setInfo(t.getInfo());
			}
		}
	}
	
	public List<Task> getTasks(){
		return tasks;
	}
	
	public List<Task> getArchive(){
		return archive;
	}
	
	public void sendToArchive(Task t){
		tasks.remove(t);
		archive.add(t);
	}
	
	public void sendToTasks(Task t){
		tasks.add(t);
		archive.remove(t);
	}
	
	public void addTask(Task t){
		t.setId(ListUtils.getNextId(tasks, archive));
		tasks.add(t);
	}
	
	public void deleteTask(Task t){
		tasks.remove(t);
	}
	
	public void deleteTaskFromArchive(Task t){
		archive.remove(t);
	}
	
}
