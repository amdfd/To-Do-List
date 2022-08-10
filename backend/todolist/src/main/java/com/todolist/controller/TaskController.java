package com.todolist.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.todolist.model.Task;
import com.todolist.repository.TaskRepository;
import com.todolist.service.TaskService;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private TaskService taskService;
	
	// Read all tasks
	@GetMapping(path = "/getTasks")
	public ResponseEntity<List<Task>> getTasks(){
		return new ResponseEntity<>(taskService.getTasks(), HttpStatus.OK);
	}
	
	// Create task
	@PostMapping(path = "/save")
	public ResponseEntity<String> saveTask(@RequestBody Task task){
		if (taskService.saveTask(task)) {
			return new ResponseEntity<>("Task saved", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Task failed to save", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Update task
	@PutMapping
	public ResponseEntity<Task> put(@Valid @RequestBody Task task){
		return taskRepository.findById(task.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK)
						.body(taskRepository.save(task)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	// Delete task
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Task> task = taskRepository.findById(id);
		
		if(task.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		taskRepository.deleteById(id);
	}
}
