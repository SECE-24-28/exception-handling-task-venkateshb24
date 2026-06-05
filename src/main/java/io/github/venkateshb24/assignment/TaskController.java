package io.github.venkateshb24.assignment;

import io.github.venkateshb24.assignment.Task;
import io.github.venkateshb24.assignment.exception.TaskAlreadyExistsException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    List<Task> tasks = new ArrayList<>();

    @GetMapping
    public List<Task> getTasks() {
        return tasks;
    }

    @PostMapping
    public String addTask(@RequestBody Task task) {

        for (Task existingTask : tasks) {
            if (existingTask.getId() == task.getId()) {
                throw new TaskAlreadyExistsException(
                        "Task with ID " + task.getId() + " already exists!"
                );
            }
        }

        tasks.add(task);

        return "Task Added Successfully!";
    }

    @PutMapping("/{id}")
    public String updateTask(@PathVariable int id, @RequestBody Task updatedTask) {

        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setTitle(updatedTask.getTitle());
                task.setCompleted(updatedTask.isCompleted());
                return "Task Updated Successfully!";
            }
        }

        return "Task Not Found!";
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable int id) {

        tasks.removeIf(task -> task.getId() == id);

        return "Task Deleted Successfully!";
    }
}