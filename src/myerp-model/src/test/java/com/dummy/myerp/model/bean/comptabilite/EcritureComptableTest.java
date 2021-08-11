package com.dummy.myerp.model.bean.comptabilite;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

import static org.apache.logging.log4j.LogManager.getLogger;
import static org.assertj.core.api.Assertions.*;


public class EcritureComptableTest {

    static Logger logger = getLogger(EcritureComptableTest.class);

    /* Méthodes à tester :
    public BigDecimal getTotalDebit();
    public BigDecimal getTotalCredit();
    public boolean isEquilibree();
     */

    //TESTS
    @Test
        public void getTotalDebitTest_returnsTheSumOfDebits_ofSeveralLigneEcritureComptable() {
        //GIVEN
        int a = 25, b=75, c=50, expectedResult = a+b+c;
        EcritureComptable ecritureComptable = new EcritureComptable();
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
        EcritureComptable ecritureComptable = new EcritureComptable();
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
        EcritureComptable vEcriture = new EcritureComptable();
        vEcriture.setLibelle("Equilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "7"));
//      // CORRECTED Assert.assertTrue(vEcriture.toString(), vEcriture.isEquilibree());
        BigDecimal credit = vEcriture.getTotalCredit(), debit = vEcriture.getTotalCredit();
        logger.info("isEquilibree()_TotalCredit = " + vEcriture.getTotalCredit());
        logger.info("isEquilibree()_TotalDebit = " + vEcriture.getTotalDebit());
        //WHEN
        boolean actualResult = vEcriture.isEquilibree();
        //THEN
        assertThat(vEcriture.isEquilibree()).isTrue();

    }

    @Test
    public void isNotEquilibree_returnsFalse_whenCreditsAndDebitsAreNotEquals() {
        //GIVEN
        EcritureComptable vEcriture = new EcritureComptable();
        vEcriture.getListLigneEcriture().clear();
        vEcriture.setLibelle("Non équilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "10", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "20", "1"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "30"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "1", "2"));
        BigDecimal credit = vEcriture.getTotalCredit(), debit = vEcriture.getTotalCredit();
        logger.info("isNotEquilibree()_TotalCredit = " + vEcriture.getTotalCredit());
        logger.info("isNotEquilibree()_TotalDebit = " + vEcriture.getTotalDebit());
        //WHEN
        boolean actualResult = vEcriture.isEquilibree();
//      //CORRECTED Assert.assertFalse(vEcriture.toString(), vEcriture.isEquilibree());
        assertThat(actualResult).isFalse();
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
