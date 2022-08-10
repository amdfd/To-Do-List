package com.todolist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todolist.model.Task;
import com.todolist.repository.TaskRepository;

@Service
public class TaskServiceImpl {
	
	@Autowired
	TaskRepository taskRepository;
	
	public boolean saveTask(Task task) {
		try {
			Task saveTask = taskRepository.save(task);
			if (saveTask != null) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			
		}
		return false;
	}
	
	@Autowired
	public List<Task> getLeads(){
		return taskRepository.findAll();
	}
}
