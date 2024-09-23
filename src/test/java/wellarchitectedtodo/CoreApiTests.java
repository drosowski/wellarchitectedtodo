package wellarchitectedtodo;

import com.tngtech.archunit.core.domain.*;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchIgnore;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.elements.ClassesShouldConjunction;
import com.tngtech.archunit.library.metrics.ArchitectureMetrics;
import com.tngtech.archunit.library.metrics.LakosMetrics;
import com.tngtech.archunit.library.metrics.MetricsComponents;
import de.smartsquare.wellarchitectedtodo.WellarchitectedtodoApplicationTests;
import de.smartsquare.wellarchitectedtodo.project.service.CreateProject;
import de.smartsquare.wellarchitectedtodo.project.service.ProjectService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Set;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AnalyzeClasses(packages = "de.smartsquare.wellarchitectedtodo")
public class CoreApiTests {

    @Test
    @Disabled("takes a loooong time")
    void test_imports() {
        JavaClasses classes = new ClassFileImporter().importClasspath();
//        JavaClasses classes = new ClassFileImporter().importPath("/src/main/kotlin/de/smartsquare/wellarchitectedtodo");
        assertTrue(classes.contain(WellarchitectedtodoApplicationTests.class));
    }

    @Test
    void test_imports_with_package() {
        JavaClasses classes = new ClassFileImporter().importPackages("de.smartsquare.wellarchitectedtodo");
        assertTrue(classes.contain(WellarchitectedtodoApplicationTests.class));
    }

    @Test
    void test_imports_options() {
        ImportOption ignoreTests = location -> {
            return !location.contains("/test/"); // ignore any URI to sources that contains '/test/'
        };

        JavaClasses classes = new ClassFileImporter().withImportOption(ignoreTests).importPackages("de.smartsquare.wellarchitectedtodo");
        assertFalse(classes.contain(WellarchitectedtodoApplicationTests.class));
    }

    @Test
    void test_maven_gradle_imports() {
        JavaClasses classes =
                new ClassFileImporter()
                        .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
                        .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                        .importPackages("de.smartsquare.wellarchitectedtodo");

        assertThat(classes.size()).isGreaterThan(0);
        assertFalse(classes.contain(WellarchitectedtodoApplicationTests.class));
    }

    @ArchTest
    public static final ArchRule arch_test = classes().should().resideInAPackage("de.smartsquare.wellarchitectedtodo..");

    @ArchTest
    @ArchIgnore
    static void ignored_arch_test(JavaClasses classes) {
        ClassesShouldConjunction rule = classes().should().resideInAPackage("de.smartsquare.wellarchitectedtodo..");
        rule.check(classes);
    }

    @Test
    void test_archunit_domain() {
        JavaClasses classes =
                new ClassFileImporter()
                        .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
                        .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                        .importPackages("de.smartsquare.wellarchitectedtodo");

        JavaClass projectServiceClass = classes.get(ProjectService.class);

        System.out.println("=====================================");
        System.out.println("PROJECTSERVICE METHODS:");
        System.out.println("=====================================");
        projectServiceClass.getAllMethods().stream().forEach(System.out::println);

        System.out.println("=====================================");
        System.out.println("PROJECTSERVICE CONSTRUCTOR PARAMS:");
        System.out.println("=====================================");
        JavaConstructor constructor = projectServiceClass.getConstructors().iterator().next();
        constructor.getParameters().stream().forEach(System.out::println);

        System.out.println("=====================================");
        System.out.println("PROJECTSERVICE.CREATE ACCESSES:");
        System.out.println("=====================================");
        JavaMethod create = projectServiceClass.getMethod("create", CreateProject.class);
        create.getAccessesToSelf().stream().forEach(System.out::println);

        // .reflect() returns the underlying Java reflection object
        assertThat(create.reflect()).isInstanceOf(Method.class);
    }

    @Test
    void metrics() {
        JavaClasses classes =
                new ClassFileImporter()
                        .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
                        .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                        .importPackages("de.smartsquare.wellarchitectedtodo");

        Set<JavaPackage> packages = classes.getPackage("de.smartsquare.wellarchitectedtodo").getSubpackages();

// These components can also be created in a package agnostic way, compare MetricsComponents.from(..)
        MetricsComponents<JavaClass> components = MetricsComponents.fromPackages(packages);

        LakosMetrics metrics = ArchitectureMetrics.lakosMetrics(components);

        System.out.println("CCD: " + metrics.getCumulativeComponentDependency());
        System.out.println("ACD: " + metrics.getAverageComponentDependency());
        System.out.println("RACD: " + metrics.getRelativeAverageComponentDependency());
        System.out.println("NCCD: " + metrics.getNormalizedCumulativeComponentDependency());
    }
}
