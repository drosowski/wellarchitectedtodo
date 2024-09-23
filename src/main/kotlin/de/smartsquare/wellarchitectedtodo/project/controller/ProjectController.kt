package de.smartsquare.wellarchitectedtodo.project.controller

import de.smartsquare.wellarchitectedtodo.project.service.CreateProject
import de.smartsquare.wellarchitectedtodo.project.service.ProjectService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/projects")
class ProjectController(
    private val projectService: ProjectService,
) {
    @PostMapping
    fun createProject(createProject: CreateProject) = projectService.create(createProject)

    @GetMapping("/{id}")
    fun getProject(@PathVariable id: UUID) = projectService.findByIdOrNull(id)
}