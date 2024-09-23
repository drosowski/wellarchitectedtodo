package de.smartsquare.wellarchitectedtodo.project.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.UUID

// do not use data classes for entities in real world applications!
@Entity
data class Project(
    @Id
    val id: UUID,
    val name: String,
    val description: String,
    val ownerId: UUID
)
