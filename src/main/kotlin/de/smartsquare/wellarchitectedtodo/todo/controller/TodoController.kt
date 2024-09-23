package de.smartsquare.wellarchitectedtodo.todo.controller

import de.smartsquare.wellarchitectedtodo.todo.service.CreateTodo
import de.smartsquare.wellarchitectedtodo.todo.service.TodoService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/todos")
class TodoController(
    private val todoService: TodoService
) {
    @PostMapping
    fun createTodo(createTodo: CreateTodo) = todoService.createTodo(createTodo)

    @GetMapping("/{id}")
    fun getTodo(@PathVariable id: UUID) = todoService.findByIdOrNull(id)
}