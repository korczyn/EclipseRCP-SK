package com.starterkit.views.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;

import com.starterkit.utils.ListUtils;

public enum ModelProvider {
	INSTANCE;
	
	private List<Task> tasks;
	
	private ModelProvider(){
		tasks = new ArrayList<Task>();
		tasks.add(new Task(1L, "kup", "in progress", "2001-12-03", "malo wazne"));
		tasks.add(new Task(2L, "kup mleka", "in progress", "2001-12-03", "malo wazne"));
		tasks.add(new Task(3L, "sprzedaj akcje", "done", "2001-12-03", "malo wazne"));
	}
	
	public void changeTask(Task t){
		for (Task task : tasks) {
			if(task.getId().equals(t.getId())){
				System.out.println("found");
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
	
	public void addTask(Task t){
		System.out.println("task before " + tasks.size());
		t.setId(ListUtils.getNextId(tasks));
		tasks.add(t);
		System.out.println("task after " + tasks.size());
	}
	
	public void deleteTask(Task t){
		System.out.println("task before " + tasks.size());
		tasks.remove(t);
		System.out.println("task after " + tasks.size());
	}
	
}
