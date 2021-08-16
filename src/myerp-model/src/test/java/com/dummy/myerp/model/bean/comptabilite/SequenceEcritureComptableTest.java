package com.dummy.myerp.model.bean.comptabilite;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SequenceEcritureComptableTest {

    public static final String STRING_EXPECTED = "SequenceEcritureComptable{annee=2021, derniereValeur=1, journalCode='AC'}";

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
