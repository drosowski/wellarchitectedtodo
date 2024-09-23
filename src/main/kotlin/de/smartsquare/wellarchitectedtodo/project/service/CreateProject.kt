package de.smartsquare.wellarchitectedtodo.project.service

import java.util.UUID

data class CreateProject(
    val name: String,
    val description: String,
    val ownerId: UUID
)