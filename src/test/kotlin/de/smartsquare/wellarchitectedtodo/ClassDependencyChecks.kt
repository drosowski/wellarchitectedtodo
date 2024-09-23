package de.smartsquare.wellarchitectedtodo

import com.tngtech.archunit.core.domain.JavaClass
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.ArchCondition
import com.tngtech.archunit.lang.ConditionEvents
import com.tngtech.archunit.lang.SimpleConditionEvent
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RestController


@AnalyzeClasses(packages = ["de.smartsquare.wellarchitectedtodo"])
class ClassDependencyChecks {

    @ArchTest
    private val `controllers should only depend on services` =
        classes().that().areAnnotatedWith(RestController::class.java)
            .should().onlyHaveDependentClassesThat().areAnnotatedWith(Service::class.java)

    @ArchTest
    private val `services should be annotated with transactional` =
        classes().that().areAnnotatedWith(Service::class.java)
            .should().beAnnotatedWith(Transactional::class.java)

    @ArchTest
    private val `services should only depend on other services outside their own package` =
        classes().that().areAnnotatedWith(Service::class.java)
            .should(onlyDependOnServicesOutsideOwnPackage())

    private fun onlyDependOnServicesOutsideOwnPackage(): ArchCondition<JavaClass> {
        return object : ArchCondition<JavaClass>("only depend on other services outside their own package") {
            override fun check(item: JavaClass, events: ConditionEvents) {
                item.allConstructors.firstOrNull()?.parameters?.forEach { dependency ->
                    val paramClass = dependency.rawType
                    if (!paramClass.isAnnotatedWith(Service::class.java) && !isInSameParentPackage(
                            paramClass,
                            item
                        )
                    ) {
                        val message =
                            "${item.name} has a dependency to ${paramClass.name} that is not a service and in a different package"
                        events.add(SimpleConditionEvent.violated(dependency, message))
                    }
                }
            }
        }
    }

    private fun isInSameParentPackage(dependency: JavaClass, origin: JavaClass) =
        dependency.`package`.parent.get().name == origin.`package`.parent.get().name
}