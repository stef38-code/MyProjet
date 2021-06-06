package com.stephane.projet.architecture.coding;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.stephane.projet.architecture.ArchitectureConstants.DEFAULT_PACKAGE;
import static com.stephane.projet.architecture.coding.TestingRules.notUseJunit4;

@AnalyzeClasses(packages = DEFAULT_PACKAGE)
public class TestingRulesTest {
    @ArchTest
    static final ArchRule ne_pas_utiliser_junit_4 = notUseJunit4(DEFAULT_PACKAGE);
}
