package de.smartsquare.wellarchitectedtodo

import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.library.Architectures.layeredArchitecture
import com.tngtech.archunit.library.Architectures.onionArchitecture
import com.tngtech.archunit.library.GeneralCodingRules
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices
import com.tngtech.archunit.library.modules.syntax.ModuleRuleDefinition.modules

@AnalyzeClasses(packages = ["de.smartsquare.wellarchitectedtodo"])
class ArchitectureChecks {
    @ArchTest
    private val `layered architecture` =
        layeredArchitecture()
            .consideringOnlyDependenciesInLayers()
            .layer("Controller").definedBy("..controller..")
            .layer("Service").definedBy("..service..")
            .layer("Domain").definedBy("..domain..")
            .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
            .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller")
            .whereLayer("Domain").mayNotAccessAnyLayer()

    @ArchTest
    private val `hexagonal architecture` =
        onionArchitecture()
            .domainModels("de.smartsquare.wellarchitectedtodo..domain..")
            .domainServices("de.smartsquare.wellarchitectedtodo..service..")
            .applicationServices("de.smartsquare.wellarchitectedtodo")
            .adapter("rest", "de.smartsquare.wellarchitectedtodo..controller..")
    // this could be used to define a user facade adapter without local implementation
    //.adapter("user", "de.smartsquare.wellarchitectedtodo.userfacade..")

    @ArchTest
    private val `slices free of cycles` =
        slices().matching("de.smartsquare.wellarchitectedtodo.(*)..")
            .should().beFreeOfCycles()

    @ArchTest
    private val `modules free of cycles` =
        modules().definedByPackages("de.smartsquare.wellarchitectedtodo.(*)..")
            .should().beFreeOfCycles()

    @ArchTest
    private val `no generic exceptions` =
        GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS
            .because("we want to have a more specific exception handling")
}