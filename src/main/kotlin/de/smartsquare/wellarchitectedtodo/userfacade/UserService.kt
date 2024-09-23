package de.smartsquare.wellarchitectedtodo.userfacade

import java.util.UUID

interface UserService {
    fun existsById(id: UUID): Boolean
}