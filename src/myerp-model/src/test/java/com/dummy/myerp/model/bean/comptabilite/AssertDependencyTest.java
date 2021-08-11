package com.dummy.myerp.model.bean.comptabilite;

import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.apache.logging.log4j.LogManager.getLogger;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Classe de test pour injection des dépendances JUnit5 et AssertJ
 */
@Tag("AssertJClasseTest")
@DisplayName("Test de 2 opérations basiques")
class AssertDependencyTest {

    /*
    assertThat(actual).isEqualTo(expected);
    assertThat(actual).as("assertion desc").isEqualTo(expected);
    */

    private AssertDependency assertDependency;
    static Logger logger = getLogger(AssertDependencyTest.class);

    @BeforeEach
    public void setUp() {
        assertDependency = new AssertDependency();
        logger.info("SetUp ClassUnderTest");
    }

    @Test
    @DisplayName("Addition de 2 nombres")
    public void addTest_shouldReturnTheSum_OfTwoInt() {

        //GIVEN
        int a = 2;
        int b = 3;
        //WHEN
        int actualResult = assertDependency.add(a, b);
        //THEN
        assertThat(actualResult).isEqualTo(5);
    }

    @Test
    @DisplayName("Soustraction de 2 nombres")
    public void addTest_shouldReturnTheDif_OfTwoInt() {
        //GIVEN
        int a = 10;
        int b = 5;
        //WHEN
        int actualResult = assertDependency.sub(a, b);
        //THEN
        assertThat(actualResult).isEqualTo(5);
    }

}