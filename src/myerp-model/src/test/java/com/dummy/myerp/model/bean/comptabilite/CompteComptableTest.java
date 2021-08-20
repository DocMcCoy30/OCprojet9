package com.dummy.myerp.model.bean.comptabilite;

import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.apache.logging.log4j.LogManager.getLogger;
import static org.assertj.core.api.Assertions.*;

public class CompteComptableTest {

    static Logger logger = getLogger(CompteComptableTest.class);

    /* Méthodes à tester :
    public CompteComptable(Integer pNumero, String pLibelle);
    public static CompteComptable getByNumero(List<? extends CompteComptable> pList, Integer pNumero);
     */

    private static final List<CompteComptable> compteComptables = new ArrayList<>();
    CompteComptable compteComptable = null;

    @BeforeAll
    public static void setUpCompteComptableList() {
        logger.info("Creation d'une liste de CompteComptable");
        compteComptables.add(new CompteComptable(3225, "Fournitures de bureau"));
        compteComptables.add(new CompteComptable(3451, "Prestations de services"));
        compteComptables.add(new CompteComptable(4111, "Clients - Ventes de biens ou de prestations de services"));
        compteComptables.add(new CompteComptable(4181, "Clients - Factures à établir"));
        compteComptables.add(new CompteComptable(512, "BANQUES"));
    }

    @Test
    @DisplayName("Test du constructeur CompteComptable avec tous les paramètres")
    public void constructorTest_withAllAttributes() {
        //GIVEN
        int numero = 101;
        String libelle = "CAPITAL";
        //WHEN
        CompteComptable compteComptableUnderTest = new CompteComptable(numero, libelle);
        //THEN
        assertThat(compteComptableUnderTest.getNumero()).isEqualTo(101);
        assertThat(compteComptableUnderTest.getLibelle()).isEqualTo("CAPITAL");
    }

    @Test
    @DisplayName("Recherche d'un Compte Comptable par son numéro")
    public void getByNumeroTest_ReturnsACompteComptable_whenNumeroIsGiven() {
        //GIVEN
        int numeroDeCompte = 3225;
        //WHEN
        CompteComptable actualCompteComptable = CompteComptable.getByNumero(compteComptables,numeroDeCompte);
        //THEN
        assertThat(actualCompteComptable).isEqualTo(compteComptables.get(0));
    }

}
