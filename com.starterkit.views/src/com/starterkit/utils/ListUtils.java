package com.starterkit.utils;

import java.util.List;

import com.starterkit.views.model.Task;

public class ListUtils {

	public static Long getNextId(List<Task> list, List<Task> list2){
		Long max = 0L;
		for (Task task : list) {
			if(task.getId() > max){
				max = task.getId();
			}
		}
		for (Task task : list2) {
			if(task.getId() > max){
				max = task.getId();
			}
		}
		return max + 1;
	}
}
