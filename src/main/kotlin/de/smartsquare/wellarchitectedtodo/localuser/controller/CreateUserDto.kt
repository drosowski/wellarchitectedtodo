package de.smartsquare.wellarchitectedtodo.localuser.controller

import de.smartsquare.wellarchitectedtodo.localuser.domain.LocalUser
import java.util.UUID

data class CreateUserDto(
    val firstName: String,
    val lastName: String,
    val email: String
) {
    fun toUser() = LocalUser(
        id = UUID.randomUUID(),
        firstName = firstName,
        lastName = lastName,
        email = email
    )
}
