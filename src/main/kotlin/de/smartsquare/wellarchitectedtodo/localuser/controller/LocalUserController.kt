package de.smartsquare.wellarchitectedtodo.localuser.controller

import de.smartsquare.wellarchitectedtodo.localuser.service.LocalUserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/users")
class LocalUserController(
    private val userService: LocalUserService
) {

    @PostMapping
    fun createUser(createUserDto: CreateUserDto) {
        userService.saveOrUpdate(createUserDto.toUser())
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: UUID) = userService.existsById(id)
}