package de.smartsquare.wellarchitectedtodo

import com.tngtech.archunit.base.DescribedPredicate.alwaysTrue
import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.domain.JavaPackage
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.AbstractClassesTransformer
import com.tngtech.archunit.lang.ArchCondition
import com.tngtech.archunit.lang.ClassesTransformer
import com.tngtech.archunit.lang.ConditionEvents
import com.tngtech.archunit.lang.SimpleConditionEvent
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.all
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses


@AnalyzeClasses(packages = ["de.smartsquare.wellarchitectedtodo"])
class PackageDependencyChecks {

    @ArchTest
    private val `project should have no dependencies to the localuser implementation` =
        noClasses().that().resideInAnyPackage("de.smartsquare.wellarchitectedtodo.project..").should()
            .dependOnClassesThat().resideInAnyPackage("de.smartsquare.wellarchitectedtodo.localuser..")

    @ArchTest
    private val `todo should have no dependencies to the localuser implementation` =
        noClasses().that().resideInAnyPackage("de.smartsquare.wellarchitectedtodo.todo..").should()
            .dependOnClassesThat().resideInAnyPackage("de.smartsquare.wellarchitectedtodo.localuser..")

    @ArchTest
    private val `project should have no dependencies to the todo package` =
        noClasses().that().resideInAnyPackage("de.smartsquare.wellarchitectedtodo.project..").should()
            .dependOnClassesThat().resideInAnyPackage("de.smartsquare.wellarchitectedtodo.todo..")

    @ArchTest
    private val `userfacade should have no dependencies to the project package` =
        noClasses().that().resideInAnyPackage("de.smartsquare.wellarchitectedtodo.userfacade..").should()
            .dependOnClassesThat().resideInAnyPackage("de.smartsquare.wellarchitectedtodo.project..")

    @ArchTest
    private val `userfacade should have no dependencies to the todo package` =
        noClasses().that().resideInAnyPackage("de.smartsquare.wellarchitectedtodo.userfacade..").should()
            .dependOnClassesThat().resideInAnyPackage("de.smartsquare.wellarchitectedtodo.todo..")

    @ArchTest
    fun `facade should not depend on other packages`(classes: JavaClasses) {
        val rule = all(facadeModules).should(notDependOnOtherPackages())
        rule.check(classes)
    }

    private val facadeModules: ClassesTransformer<JavaPackage> =
        object : AbstractClassesTransformer<JavaPackage>("facades") {
            override fun doTransform(classes: JavaClasses): Iterable<JavaPackage> {
                val result = mutableSetOf<JavaPackage>()
                classes.defaultPackage.traversePackageTree(
                    alwaysTrue(),
                    JavaPackage.PackageVisitor { pkg ->
                        if (pkg.relativeName.endsWith("facade")) {
                            result.add(pkg)
                        }
                    })
                return result
            }
        }

    private fun notDependOnOtherPackages(): ArchCondition<JavaPackage> {
        return object : ArchCondition<JavaPackage>("not depend on other packages") {
            override fun check(item: JavaPackage, events: ConditionEvents) {
                item.classDependenciesFromThisPackage.forEach { dependency ->
                    if (
                        dependency.targetClass.packageName.startsWith("de.smartsquare.wellarchitectedtodo") &&
                        dependency.targetClass.packageName != item.name
                    ) {
                        val message = "${item.name} has a dependency to ${dependency.targetClass.fullName}"
                        events.add(SimpleConditionEvent.violated(dependency.targetClass, message))
                    }
                }
            }
        }
    }
}