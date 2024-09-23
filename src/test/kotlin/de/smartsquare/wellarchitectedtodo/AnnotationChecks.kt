package de.smartsquare.wellarchitectedtodo

import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@AnalyzeClasses(packages = ["de.smartsquare.wellarchitectedtodo"])
class AnnotationChecks {

    @ArchTest
    val `every repository should be annotated with Repository` =
        classes().that().haveNameMatching(".*Repository")
            .should().beAnnotatedWith(Repository::class.java)

    @ArchTest
    val `every service should be annotated with Service` =
        classes().that().haveNameMatching(".*Service")
            .should().beAnnotatedWith(Service::class.java)

    @ArchTest
    val `every service should be annotated with Transactional` =
        classes().that().haveNameMatching(".*Service")
            .should().beAnnotatedWith(Transactional::class.java)
}