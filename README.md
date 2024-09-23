# An ArchUnit tested TODO app

This is a simple TODO app that is used to demonstrate some common use cases of ArchUnit.

## Architecture

### Modules

![Module diagram](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/drosowski/wellarchitectedtodo/refs/heads/master/docs/architecture.puml)

### Layers

![Layer diagram](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/drosowski/wellarchitectedtodo/refs/heads/master/docs/layers.puml)

## Tests

Here are some tests demonstrating the use of ArchUnit:

* [CoreApiTests](src/test/java/de/smartsquare/wellarchitectedtodo/CoreApiTests.java) - introduction to the Core API and the domain model of ArchUnit
* [PackageDependencyChecks](src/test/kotlin/de/smartsquare/wellarchitectedtodo/PackageDependencyChecks.kt) - checking for package dependencies using the Lang API
* [ClassDependencyChecks](src/test/kotlin/de/smartsquare/wellarchitectedtodo/ClassDependencyChecks.kt) - checking for class dependencies
* [AnnotationChecks](src/test/kotlin/de/smartsquare/wellarchitectedtodo/AnnotationChecks.kt) - checking for proper usage of annotations
* [ArchitectureChecks](src/test/kotlin/de/smartsquare/wellarchitectedtodo/ArchitectureChecks.kt) - tests the architecture of the application using the Library API

## Resources

* [archunit.properties](src/test/resources/archunit.properties) - configuration file for ArchUnit
* [archunit_ignore_patterns.txt](src/test/resources/archunit_ignore_patterns.txt) - ignore certain files in ArchUnit
