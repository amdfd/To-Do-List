package com.todolist.service;

import java.util.List;

import com.todolist.model.Task;

public interface TaskService {
	boolean saveTask(Task task);
	
	List<Task> getTasks();
}
