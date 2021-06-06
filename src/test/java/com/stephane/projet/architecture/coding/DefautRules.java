package com.stephane.projet.architecture.coding;

import com.stephane.projet.architecture.CustomConditions;
import com.tngtech.archunit.lang.ArchRule;

import java.util.Arrays;
import java.util.Map;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;

public class DefautRules {

    /**
     * Pas d'attributs public dans les classes
     *
     * @param packageName le package de base
     * @return ArchRule
     */
    static ArchRule attributesOfTheClassShouldNotBePublic(String packageName) {
        return fields().that()
                .areDeclaredInClassesThat().resideInAPackage(packageName)
                .should().notBePublic()
                .because(String.format("les attributs une classe ne doivent pas être publics %s", packageName));
    }

    /**
     * @param exclusions   les elements à exclure
     * @param packageNames le package de base
     * @return ArchRule
     */
    static ArchRule fieldsShouldHaveGetterRule(Map< String, String > exclusions, String... packageNames) {
        return fields().that()
                .areDeclaredInClassesThat().resideInAnyPackage(packageNames)
                .and().areDeclaredInClassesThat().areNotMemberClasses()
                .and().arePrivate().and().areNotFinal().and().areNotStatic()
                .should(CustomConditions.haveGetter(exclusions))
                .because("Private fields should have getters in %s" + Arrays.toString(packageNames));
    }

}
