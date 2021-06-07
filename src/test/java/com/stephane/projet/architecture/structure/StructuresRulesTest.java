package com.stephane.projet.architecture.structure;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.stephane.projet.architecture.ArchitectureConstants.*;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = DEFAULT_PACKAGE, importOptions = ImportOption.DoNotIncludeTests.class)
public class StructuresRulesTest {
    @ArchTest
    static final ArchRule classesInRepositoryShouldBeNamedProperly = classes()
            .that().resideInAPackage(REPOSITORY_PACKAGE)
            .should().haveSimpleNameEndingWith(CONTROLLER_SUFFIX)
            .because(String.format("Les Classes dans le package %s doivent avec comme suffixe %s", REPOSITORY_PACKAGE, REPOSITORY_SUFFIX));
    @ArchTest
    static final ArchRule classesInDtoShouldBeNamedProperly = classes()
            .that().resideInAPackage(DTO_PACKAGE)
            .should().haveSimpleNameEndingWith(DTO_SUFFIX)
            .because(String.format("Les Classes dans le package %s doivent avec comme suffixe %s", DTO_PACKAGE, DTO_SUFFIX));
    private static final String CONFIG = "Config";
    /**
     * le package config(Optional)
     */
    @ArchTest
    static final ArchRule layeredArchitectureConfigRule = layeredArchitecture()
            .withOptionalLayers(true)
            .layer(CONFIG).definedBy(CONFIG_PACKAGE)
            .whereLayer(CONFIG)
            .mayNotBeAccessedByAnyLayer()
            .because("Les Classes de configuration ne doivent pas être utilisées dans d'autres packages");
    private static final String CONTROLLER = "Controller";
    private static final String DTO = "Dto";
    private static final String MAPPER = "Mapper";
    private static final String EXCEPTION = "Exception";
    private static final String MODEL = "Model";
    private static final String REPOSITORY = "Repository";
    /**
     * le package model(Optional)
     */
    @ArchTest
    static final ArchRule layeredArchitectureModelRule = layeredArchitecture()
            .withOptionalLayers(true)
            .layer(MODEL).definedBy(MODEL_PACKAGE)
            .layer(REPOSITORY).definedBy(REPOSITORY_PACKAGE)
            .whereLayer(MODEL)
            .mayOnlyBeAccessedByLayers(REPOSITORY)
            .because(String.format("Les modeles doivent être utilisés dans le package %s", REPOSITORY));
    /****************************************************************
     * packages optionels
     ****************************************************************/
    private static final String SECURITY = "Security";
    /**
     * le package security(Optional)
     */
    @ArchTest
    static final ArchRule layeredArchitectureSecuriteRule = layeredArchitecture()
            .withOptionalLayers(true)
            .layer(SECURITY).definedBy(SECURITY_PACKAGE)
            .whereLayer(SECURITY)
            .mayNotBeAccessedByAnyLayer()
            .because("Les Classes de securite ne doivent pas être utilisées dans d'autres packages");
    private static final String SERVICE = "Service";
    /**
     * le package repository(Optional)
     */
    @ArchTest
    static final ArchRule layeredArchitectureRepositoryRule = layeredArchitecture()
            .withOptionalLayers(true)
            .layer(REPOSITORY).definedBy(REPOSITORY_PACKAGE)
            .layer(SERVICE).definedBy(SERVICE_PACKAGE)
            .whereLayer(REPOSITORY)
            .mayOnlyBeAccessedByLayers(SERVICE)
            .because(String.format("Les repository doivent être utilisés dans le package %s", SERVICE));
    private static final String DOMAIN = "Domain";
    /**
     * le package Dto(Optional)
     */
    @ArchTest
    static final ArchRule layeredArchitectureDtoRule = layeredArchitecture()
            .withOptionalLayers(true)
            .layer(DTO).definedBy(DTO_PACKAGE)
            .layer(DOMAIN).definedBy(DOMAIN_PACKAGE)
            .layer(SERVICE).definedBy(SERVICE_PACKAGE)
            .whereLayer(MODEL)
            .mayOnlyBeAccessedByLayers(SERVICE, DOMAIN)
            .because(String.format("Les dto doivent être utilisés dans les package %s, %s", SERVICE, DOMAIN));
    private static final String UTIL = "Util";
    /**
     * le package util(Optional)
     */
    @ArchTest
    static final ArchRule layeredArchitectureUtilesRule = layeredArchitecture()
            .withOptionalLayers(true)
            .layer(UTIL).definedBy(UTIL_PACKAGE)
            .layer(DOMAIN).definedBy(DOMAIN_PACKAGE)
            .whereLayer(UTIL)
            .mayOnlyBeAccessedByLayers(DOMAIN)
            .because(String.format("Les Classes utilitaires doivent être utilisées dans le package %s", DOMAIN));
    /*@ArchTest
    static final ArchRule layeredArchitectureRule = layeredArchitecture()
            //Definition de la structure de base du projet
            .layer(CONFIG).definedBy(CONFIG_PACKAGE)
            .layer(CONTROLLER).definedBy(CONTROLLER_PACKAGE)
            .layer(DTO).definedBy(DTO_PACKAGE)
            .layer(EXCEPTION).definedBy(EXCEPTION_PACKAGE)
            .layer(MODEL).definedBy(MODEL_PACKAGE)
            .layer(REPOSITORY).definedBy(REPOSITORY_PACKAGE)
            .layer(SECURITY).definedBy(SECURITY_PACKAGE)
            .layer(SERVICE).definedBy(SERVICE_PACKAGE)
            .layer(MAPPER).definedBy(MAPPER_PACKAGE)
            .layer(UTIL).definedBy(UTIL_PACKAGE)
            //Definition des liaisons d'accès entre les packages/classes
            .whereLayer(CONTROLLER).mayNotBeAccessedByAnyLayer()
            .whereLayer(MODEL).mayOnlyBeAccessedByLayers(REPOSITORY, SERVICE)
            .whereLayer(REPOSITORY).mayOnlyBeAccessedByLayers(SERVICE)
            // un service peut être dans un contrôler et ou dans un autre service
            // MapStruct genere un @component
            .whereLayer(SERVICE).mayOnlyBeAccessedByLayers(CONTROLLER, SERVICE, MAPPER);*/
}
