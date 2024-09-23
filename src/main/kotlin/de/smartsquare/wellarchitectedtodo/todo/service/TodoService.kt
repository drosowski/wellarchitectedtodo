package de.smartsquare.wellarchitectedtodo.todo.service

import de.smartsquare.wellarchitectedtodo.project.service.ProjectService
import de.smartsquare.wellarchitectedtodo.todo.domain.Todo
import de.smartsquare.wellarchitectedtodo.todo.domain.TodoRepository
import de.smartsquare.wellarchitectedtodo.todo.domain.TodoState
import de.smartsquare.wellarchitectedtodo.userfacade.UserService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class TodoService(
    private val todoRepository: TodoRepository,
    private val projectService: ProjectService,
    private val userService: UserService,
) : TodoRepository by todoRepository {
    fun createTodo(createTodo: CreateTodo): Todo {
        val project =
            projectService.findByIdOrNull(createTodo.projectId)
                ?: throw IllegalArgumentException("Project not found")

        if (!userService.existsById(createTodo.creatorId)) {
            throw IllegalArgumentException("User not found")
        }

        if (!userService.existsById(createTodo.assigneeId)) {
            throw IllegalArgumentException("User not found")
        }

        val todo =
            Todo(
                id = UUID.randomUUID(),
                title = createTodo.title,
                description = createTodo.description,
                state = TodoState.OPEN,
                project = project,
                creatorId = createTodo.creatorId,
                assigneeId = createTodo.assigneeId,
            )

        return todoRepository.save(todo)
    }
}
