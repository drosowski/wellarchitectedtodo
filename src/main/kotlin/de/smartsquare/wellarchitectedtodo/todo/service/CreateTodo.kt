package de.smartsquare.wellarchitectedtodo.todo.service

import java.util.UUID

data class CreateTodo(
    val title: String,
    val description: String,
    val projectId: UUID,
    val creatorId: UUID,
    val assigneeId: UUID,
)
