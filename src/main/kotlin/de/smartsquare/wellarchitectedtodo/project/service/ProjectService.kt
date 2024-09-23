package de.smartsquare.wellarchitectedtodo.project.service

import de.smartsquare.wellarchitectedtodo.project.domain.Project
import de.smartsquare.wellarchitectedtodo.project.domain.ProjectRepository
import de.smartsquare.wellarchitectedtodo.userfacade.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class ProjectService(
    private val projectRepository: ProjectRepository,
    private val userService: UserService,
) : ProjectRepository by projectRepository {
    fun create(createProject: CreateProject): Project {
        if (!userService.existsById(createProject.ownerId)) {
            throw IllegalArgumentException("User not found")
        }

        val project =
            Project(
                id = UUID.randomUUID(),
                name = createProject.name,
                description = createProject.description,
                ownerId = createProject.ownerId,
            )
        return projectRepository.save(project)
    }
}
