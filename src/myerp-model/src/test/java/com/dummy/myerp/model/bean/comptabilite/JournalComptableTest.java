package com.dummy.myerp.model.bean.comptabilite;

import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.apache.logging.log4j.LogManager.getLogger;
import static org.assertj.core.api.Assertions.*;

public class JournalComptableTest {

    public static final String STRING_EXPECTED = "JournalComptable{code='AC', libelle='Achat'}";


    static Logger logger = getLogger(JournalComptableTest.class);

    /* Méthodes à tester :
    public JournalComptable(String pCode, String pLibelle);
    public static JournalComptable getByCode(List<? extends JournalComptable> pList, String pCode);
    public String toString()
     */

    private static final List<JournalComptable> journalComptables = new ArrayList<>();

    @BeforeAll
    public static void setUpJournalComptableList() {
        journalComptables.add(new JournalComptable("AC", "Achat"));
        journalComptables.add(new JournalComptable("VE", "Vente"));
        journalComptables.add(new JournalComptable("BQ", "Banque"));
        journalComptables.add(new JournalComptable("OD", "Opérations Diverses"));
    }

    @Test
    @DisplayName("Test du constructeur JournalComptable avec tous les paramètres")
    public void constructorTest_returnsAnInstanceOfJournalComptable_withAllAttributes() {
        //GIVEN
        String code = "AC";
        String libelle = "Achat";
        //WHEN
        JournalComptable journalComptable = new JournalComptable(code, libelle);
        //THEN
        assertThat(journalComptable.getCode()).isEqualTo("AC");
        assertThat(journalComptable.getLibelle()).isEqualTo("Achat");
    }

    @Test
    public void getByCodeTest_returnsAJournalComptable_WithHisCode() {
        //GIVEN
        String code = "AC";
        //WHEN
        JournalComptable actualJournal = JournalComptable.getByCode(journalComptables, code);
        //THEN
        assertThat(actualJournal.getCode()).isEqualTo("AC");
        assertThat(actualJournal.getLibelle()).isEqualTo("Achat");
    }

    @Test
    public void toStringTest() {
        //GIVEN
        JournalComptable journalComptable = new JournalComptable("AC", "Achat");
        // WHEN
        String resultActual = journalComptable.toString();
        // THEN
        assertThat(resultActual).isEqualTo(STRING_EXPECTED);
    }
}
