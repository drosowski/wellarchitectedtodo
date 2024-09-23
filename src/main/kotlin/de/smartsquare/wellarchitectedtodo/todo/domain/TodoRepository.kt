package de.smartsquare.wellarchitectedtodo.todo.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TodoRepository : JpaRepository<Todo, UUID> {
    fun findAllByProjectId(id: UUID): Any
}