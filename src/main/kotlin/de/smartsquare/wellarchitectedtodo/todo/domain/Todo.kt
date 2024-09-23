package de.smartsquare.wellarchitectedtodo.todo.domain

import de.smartsquare.wellarchitectedtodo.project.domain.Project
import jakarta.persistence.Entity
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.util.UUID

// do not use data classes for entities in real world applications!
@Entity
data class Todo(
    @Id
    val id: UUID,
    val title: String,
    val description: String,
    @Enumerated
    val state: TodoState,
    val creatorId: UUID,
    val assigneeId: UUID,
    @ManyToOne
    @JoinColumn(name = "belongs_to_project_id")
    val project: Project
)
