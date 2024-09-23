package de.smartsquare.wellarchitectedtodo.localuser.service

import de.smartsquare.wellarchitectedtodo.localuser.domain.LocalUser
import de.smartsquare.wellarchitectedtodo.localuser.domain.LocalUserRepository
import de.smartsquare.wellarchitectedtodo.userfacade.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LocalUserService(
    private val repository: LocalUserRepository
) : UserService, LocalUserRepository by repository {
    fun saveOrUpdate(localUser: LocalUser): LocalUser {
        return repository.save(localUser)
    }
}