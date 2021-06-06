package com.stephane.projet.architecture.coding;

import ch.qos.logback.classic.Logger;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.GeneralCodingRules;

import static com.stephane.projet.architecture.ArchitectureConstants.DEFAULT_PACKAGE;
import static com.stephane.projet.architecture.coding.DefautRules.attributesOfTheClassShouldNotBePublic;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;

@AnalyzeClasses(packages = DEFAULT_PACKAGE, importOptions = ImportOption.DoNotIncludeTests.class)
public class DefautRulesTest {
    /**
     * Les Attributs
     */
    @ArchTest
    static final ArchRule les_attributs_de_la_classe_ne_doivent_pas_etre_publics = attributesOfTheClassShouldNotBePublic(DEFAULT_PACKAGE);

    @ArchTest
    static final ArchRule ne_pas_utiliser_Jodatime = GeneralCodingRules.NO_CLASSES_SHOULD_USE_JODATIME
            .because("Utilisez plutôt des objets java.time");

    @ArchTest
    static final ArchRule varaible_filan_static_majuscule = fields().that().areStatic().and().areFinal()
            .and().doNotHaveName("serialVersionUID")
            .and().doNotHaveName("log")
            .and().doNotHaveModifier(JavaModifier.SYNTHETIC)
            .should().haveNameMatching(".*^[A-Z].*")
            .because("Les variables static final doivent être nommées en majuscules");
    /**
     * Logger
     */
    @ArchTest
    static final ArchRule noJavaUtilLogging = GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING
            .because("Utilisez plutôt org.slf4j.Logger ou @Slf4j");
    @ArchTest
    static final ArchRule nom_variable_logger = fields().that().haveRawType(Logger.class)
            .should().bePrivate()
            .andShould().beStatic()
            .andShould().beFinal()
            .andShould().haveName("LOGGER")
            .because("Les variables de tracing doivent être privées, statiques et définitives, et elles doivent être nommées log");
}
