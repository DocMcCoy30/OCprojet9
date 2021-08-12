package com.dummy.myerp.testconsumer.consumer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


/**
 * Classe de test de l'initialisation du contexte Spring
 */
public class TestInitSpring extends ConsumerTestCase {

    /**
     * Constructeur.
     */
    public TestInitSpring() {
        super();
    }


    /**
     * Teste l'initialisation du contexte Spring
     */
    @Test
    public void testInit() {
        SpringRegistry.init();
        assertThat(SpringRegistry.getDaoProxy()).isNotNull();
    }
}
