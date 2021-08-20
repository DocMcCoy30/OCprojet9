package com.dummy.myerp.model.bean.comptabilite;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SequenceEcritureComptableTest {

    public static final String STRING_EXPECTED = "SequenceEcritureComptable{journalCode='AC', annee=2021, derniereValeur=1}";

    @Test
    @DisplayName("Should return an instance with all attributes")
    public void  constructorTest_withAllAttributes() {
        String journalCode = "AC";
        Integer annee = 2021;
        Integer derniereValeur = 1;
        SequenceEcritureComptable secUnderTest = new SequenceEcritureComptable(journalCode,annee,derniereValeur);
        assertThat(secUnderTest.getJournalCode()).isEqualTo("AC");
        assertThat(secUnderTest.getAnnee()).isEqualTo(2021);
        assertThat(secUnderTest.getDerniereValeur()).isEqualTo(1);
    }

    @Test
    public void toStringTest() {
        //GIVEN
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable();
        sequenceEcritureComptable.setAnnee(2021);
        sequenceEcritureComptable.setDerniereValeur(1);
        sequenceEcritureComptable.setJournalCode("AC");
        // WHEN
        String resultActual = sequenceEcritureComptable.toString();
        // THEN
        assertThat(resultActual).isEqualTo(STRING_EXPECTED);
    }
}
