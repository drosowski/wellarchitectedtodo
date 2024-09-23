package de.smartsquare.wellarchitectedtodo.localuser.domain

import de.smartsquare.wellarchitectedtodo.userfacade.UserRepresentation
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.UUID

// do not use data classes for entities in real world applications!
@Entity
data class LocalUser(
    @Id
    val id: UUID,
    override val firstName: String,
    override val lastName: String,
    override val email: String
) : UserRepresentation
