package de.smartsquare.wellarchitectedtodo.localuser.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface LocalUserRepository : JpaRepository<LocalUser, UUID>