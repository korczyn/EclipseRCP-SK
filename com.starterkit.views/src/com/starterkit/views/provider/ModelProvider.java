package com.starterkit.views.provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.starterkit.utils.ListUtils;
import com.starterkit.views.model.SortBy;
import com.starterkit.views.model.Status;
import com.starterkit.views.model.Task;

public enum ModelProvider {
	INSTANCE;

	private List<Task> tasks;
	private List<Task> archive;

	private ModelProvider() {
		tasks = new ArrayList<Task>();
		tasks.add(new Task(1L, "Kill the Zohan", Status.IN_PROGRESS,
				"03/10/1999", "He's in America"));
		tasks.add(new Task(2L, "Do some pushups", Status.IN_PROGRESS,
				"12/05/2010", "About 100 will do"));
		tasks.add(new Task(3L, "Sell stocks", Status.DONE, "05/06/1995",
				"Sell everything!!!"));

		archive = new ArrayList<Task>();
		archive.add(new Task(4L, "New shoes", Status.FAILED, "03/09/2015",
				"For hammer throw"));
		archive.add(new Task(5L, "Collect in laws from airport", Status.FAILED,
				"03/09/2015", "Be 30min late"));
		archive.add(new Task(6L, "Read a book", Status.NOT_STARTED,
				"03/09/2015", "Dishwasher manual for example"));
	}

	public void changeTask(Task t) {
		for (Task task : tasks) {
			if (task.getId().equals(t.getId())) {
				task.setName(t.getName());
				task.setStatus(Status.getStatusByValue(t.getStatus()));
				task.setDate(t.getDate());
				task.setInfo(t.getInfo());
			}
		}
	}

	public List<Task> sortBy(SortBy item) {
		if (item.equals(SortBy.ID)) {
			Collections.sort(tasks, new Comparator<Task>() {
				@Override
				public int compare(final Task object1, final Task object2) {
					return object1.getId().compareTo(object2.getId());
				}
			});
		}
		if (item.equals(SortBy.NAME)){
			Collections.sort(tasks, new Comparator<Task>() {
				@Override
				public int compare(final Task object1, final Task object2) {
					return object1.getName().compareTo(object2.getName());
				}
			});
		}
		if (item.equals(SortBy.STATUS)){
			Collections.sort(tasks, new Comparator<Task>() {
				@Override
				public int compare(final Task object1, final Task object2) {
					return object1.getStatus().compareTo(object2.getStatus());
				}
			});
		}

		return tasks;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public List<Task> getArchive() {
		return archive;
	}

	public void sendToArchive(Task t) {
		tasks.remove(t);
		archive.add(t);
	}

	public void sendToTasks(Task t) {
		tasks.add(t);
		archive.remove(t);
	}

	public void addTask(Task t) {
		t.setId(ListUtils.getNextId(tasks, archive));
		tasks.add(t);
	}

	public void deleteTask(Task t) {
		tasks.remove(t);
	}

	public void deleteTaskFromArchive(Task t) {
		archive.remove(t);
	}

}
