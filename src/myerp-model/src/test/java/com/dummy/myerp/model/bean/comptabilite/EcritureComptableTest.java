package com.dummy.myerp.model.bean.comptabilite;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

import static org.apache.logging.log4j.LogManager.getLogger;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class EcritureComptableTest {

    static Logger logger = getLogger(EcritureComptableTest.class);

    /* Méthodes à tester :
    public BigDecimal getTotalDebit();
    public BigDecimal getTotalCredit();
    public boolean isEquilibree();
    public String toString()
     */

    private  EcritureComptable ecritureComptable = null;

    public static final String STRING_EXPECTED = "EcritureComptable{id=null, journal=null, reference='null', date=null, libelle='Equilibrée', totalDebit=341.00, totalCredit=341, listLigneEcriture=[\n" +
            "LigneEcritureComptable{compteComptable=CompteComptable{numero=1, libelle='null'}, libelle='200.50', debit=200.50, credit=null}\n" +
            "LigneEcritureComptable{compteComptable=CompteComptable{numero=1, libelle='null'}, libelle='67.50', debit=100.50, credit=33}\n" +
            "LigneEcritureComptable{compteComptable=CompteComptable{numero=2, libelle='null'}, libelle='-301', debit=null, credit=301}\n" +
            "LigneEcritureComptable{compteComptable=CompteComptable{numero=2, libelle='null'}, libelle='33', debit=40, credit=7}\n" +
            "]}";

    //TESTS
    @Test
        public void getTotalDebitTest_returnsTheSumOfDebits_ofSeveralLigneEcritureComptable() {
        //GIVEN
        int a = 25, b=75, c=50, expectedResult = a+b+c;
        ecritureComptable = new EcritureComptable();
        LigneEcritureComptable ligne1 = this.createLigne(1, Integer.toString(a), "10");
        LigneEcritureComptable ligne2 = this.createLigne(1, Integer.toString(b), "10");
        LigneEcritureComptable ligne3 = this.createLigne(1, Integer.toString(c), "10");
        ecritureComptable.getListLigneEcriture().add(ligne1);
        ecritureComptable.getListLigneEcriture().add(ligne2);
        ecritureComptable.getListLigneEcriture().add(ligne3);
        //WHEN
        BigDecimal actualDebit = ecritureComptable.getTotalDebit();
        //THEN
        assertThat(actualDebit).isEqualTo(BigDecimal.valueOf(expectedResult));
    }

    @Test
    public void getTotalCreditTest_returnsTheSumOfCredits_ofSeveralLigneEcritureComptable() {
        //GIVEN
        int a = 25, b=75, c=50, expectedResult = a+b+c;
        ecritureComptable = new EcritureComptable();
        LigneEcritureComptable ligne1 = this.createLigne(1, "10", Integer.toString(a));
        LigneEcritureComptable ligne2 = this.createLigne(1, "10", Integer.toString(b));
        LigneEcritureComptable ligne3 = this.createLigne(1, "10", Integer.toString(c));
        ecritureComptable.getListLigneEcriture().add(ligne1);
        ecritureComptable.getListLigneEcriture().add(ligne2);
        ecritureComptable.getListLigneEcriture().add(ligne3);
        //WHEN
        BigDecimal actualDebit = ecritureComptable.getTotalCredit();
        //THEN
        assertThat(actualDebit).isEqualTo(BigDecimal.valueOf(expectedResult));
    }

    @Test
    public void isEquilibree_returnsTrue_whenCreditsAndDebitsAreEquals() {
        //GIVEN
        ecritureComptable = new EcritureComptable();
        ecritureComptable.setLibelle("Equilibrée");
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "40", "7"));
        logger.info("isEquilibree()_TotalCredit = " + ecritureComptable.getTotalCredit());
        logger.info("isEquilibree()_TotalDebit = " + ecritureComptable.getTotalDebit());
        //WHEN
        boolean actualResult = ecritureComptable.isEquilibree();
        //THEN
        assertThat(actualResult).isTrue();

    }

    @Test
    public void isNotEquilibree_returnsFalse_whenCreditsAndDebitsAreNotEquals() {
        //GIVEN
        ecritureComptable = new EcritureComptable();
        ecritureComptable.setLibelle("Non équilibrée");
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1, "10", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1, "20", "1"));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, null, "30"));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "1", "2"));
        logger.info("isNotEquilibree()_TotalCredit = " + ecritureComptable.getTotalCredit());
        logger.info("isNotEquilibree()_TotalDebit = " + ecritureComptable.getTotalDebit());
        //WHEN
        boolean actualResult = ecritureComptable.isEquilibree();
        assertThat(actualResult).isFalse();
    }

    @Test
    public void toStringTest() {
        // GIVEN
        ecritureComptable = new EcritureComptable();
        ecritureComptable.setLibelle("Equilibrée");
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "40", "7"));
        // WHEN
        String resultActual = ecritureComptable.toString();
        // THEN
        assertThat(resultActual).isEqualTo(STRING_EXPECTED);
    }

    // METHODE UTILITAIRE
    private LigneEcritureComptable createLigne(Integer pCompteComptableNumero, String pDebit, String pCredit) {
        BigDecimal vDebit = pDebit == null ? null : new BigDecimal(pDebit);
        BigDecimal vCredit = pCredit == null ? null : new BigDecimal(pCredit);
        String vLibelle = ObjectUtils.defaultIfNull(vDebit, BigDecimal.ZERO)
                .subtract(ObjectUtils.defaultIfNull(vCredit, BigDecimal.ZERO)).toPlainString();
        LigneEcritureComptable vRetour = new LigneEcritureComptable(new CompteComptable(pCompteComptableNumero),
                vLibelle,
                vDebit, vCredit);
        return vRetour;
    }
}
