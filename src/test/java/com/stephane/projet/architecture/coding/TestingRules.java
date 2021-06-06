package com.stephane.projet.architecture.coding;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class TestingRules {
    static ArchRule notUseJunit4(String packageName) {
        JavaClasses classes = new ClassFileImporter()
                .importPackages(packageName);
        return noClasses()
                .should().accessClassesThat().resideInAnyPackage("org.junit")
                .because("Les tests doivent utiliser Junit5 au lieu de Junit4")
                ;//.check(classes);
    }
}
