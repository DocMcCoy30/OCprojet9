package com.dummy.myerp.business.impl.manager;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ComptabiliteManagerImplTest {

    @InjectMocks
    private ComptabiliteManagerImpl comptabiliteManager;

    private EcritureComptable ecritureComptableOK, ecritureComptableKO1, ecritureComptableKO2;

    @BeforeEach
    public void setUp() {
        ecritureComptableOK = new EcritureComptable();
        ecritureComptableOK.setJournal(new JournalComptable("AC", "Achat"));
        ecritureComptableOK.setDate(new Date());
        ecritureComptableOK.setLibelle("Libelle");
        ecritureComptableOK.setReference("AC-2021/00005");
        ecritureComptableOK.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        ecritureComptableOK.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));

        ecritureComptableKO1 = new EcritureComptable();
        ecritureComptableKO1 = new EcritureComptable();
        ecritureComptableKO1.setJournal(new JournalComptable("BQ", "Achat"));
        ecritureComptableKO1.setDate(new Date());
        ecritureComptableKO1.setLibelle("Libelle");
        ecritureComptableKO1.setReference("AC-2016/0005");
        ecritureComptableKO1.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        ecritureComptableKO1.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(321)));

        ecritureComptableKO2 = ecritureComptableKO1;
        ecritureComptableKO2.getListLigneEcriture().remove(1);
    }

    // --------------- Tests Méthodes Utilitaires --------------

    @Tag("formatDateToYear")
    @DisplayName("Should return the formated (yyyy) annee  of an EcritureComptable")
    @Test
    public void formatDateToYear_returnTheAnnee_ofEcritureComptable() {
        //GIVEN
        //WHEN
        int actualAnnee = comptabiliteManager.formatDateToYear(ecritureComptableOK);
        //THEN
        assertThat(actualAnnee).isEqualTo(2021);
    }

    @Tag("formatValeur")
    @ParameterizedTest(name = "Format int {0} to String {1}")
    @CsvSource({"1,'00001'", "22,'00022'", "333,'00333'", "4444,'04444'", "55555,'55555'"})
    public void formatValeur(int valeur, String expectedResult) throws FunctionalException {
        String actualResult = comptabiliteManager.formatValeur(valeur, 5);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Tag("formatValeur_NegativeDigits")
    @DisplayName("FormatValeur with negative digits value should throw an exception")
    @Test
    public void formatValeur_NegativeDigits() throws FunctionalException {
        assertThrows(FunctionalException.class, () -> comptabiliteManager.formatValeur(5, -2));
    }

    @Tag("referenceTokenizer")
    @DisplayName("Should split the reference and return the token with the specified index number")
    @Test
    public void referenceTokenizer() throws FunctionalException {
        String reference = "AC-2021/00002";
        String actualResult1 = comptabiliteManager.referenceTokenizer(reference, 0);
        String actualResult2 = comptabiliteManager.referenceTokenizer(reference, 1);
        String actualResult3 = comptabiliteManager.referenceTokenizer(reference, 2);
        assertThat(actualResult1).isEqualTo("AC");
        assertThat(actualResult2).isEqualTo("2021");
        assertThat(actualResult3).isEqualTo("00002");
    }

    @Tag("referenceTokenizer_OutOfBoundException")
    @DisplayName("Should throw an exception when index number is wrong")
    @Test
    public void referenceTokenizer_OufOfBoundException() {
        String reference = "AC-2021/00002";
        assertThrows(FunctionalException.class, () -> comptabiliteManager.referenceTokenizer(reference, 5));
    }

    //--------------- Tests ComptabiliteManagerImpl Methods ---------------

    @Tag("checkEcritureComptableUnitViolation")
    @DisplayName("Should return true if ConstraintViolation is respected")
    @Test
    public void checkEcritureComptableUnitViolation() throws Exception {
        Boolean result = comptabiliteManager.checkEcritureConstraintViolation(ecritureComptableOK);
        assertThat(result).isTrue();
    }

    @Tag("checkEcritureComptableUnitViolation_FonctionnalException")
    @DisplayName("Should throw an Exception if ConstraintViolation is not respected")
    @Test
    public void checkEcritureComptableUnitViolation_FonctionnalException() {
        assertThrows(FunctionalException.class, () -> comptabiliteManager.checkEcritureConstraintViolation(ecritureComptableKO1));
    }

    @Tag("checkEcritureComptableUnitRG2")
    @DisplayName("Should return true if RG2 is respected")
    @Test
    public void checkEcritureComptableUnitRG2() throws Exception {
        Boolean result = comptabiliteManager.checkIfEcritureIsEquilibreeRG2(ecritureComptableOK);
        assertThat(result).isTrue();
    }

    @Tag("checkEcritureComptableUnitRG3")
    @DisplayName("Should return true if RG3 is respected")
    @Test
    public void checkEcritureComptableUnitRG3() throws Exception {
        Boolean result = comptabiliteManager.checkEcritureNumberOfLineRG3(ecritureComptableOK);
        assertThat(result).isTrue();
    }

    @Tag("checkEcritureComptableUnitRG3_FonctionnalException")
    @DisplayName("Should throw an Exception if RG3 is not respected")
    @Test
    public void checkEcritureComptableUnitRG3_FonctionnalException() {
        assertThrows(FunctionalException.class, () -> comptabiliteManager.checkEcritureNumberOfLineRG3(ecritureComptableKO2));
    }

    @Tag("checkEcritureFormatAndContainRG5")
    @DisplayName("Should return true if RG5 is respected")
    @Test
    public void checkEcritureFormatAndContainRG5() throws FunctionalException {
        Boolean result = comptabiliteManager.checkEcritureFormatAndContainRG5(ecritureComptableOK);
        assertThat(result).isTrue();
    }

    @Tag("checkEcritureFormatAndContainRG5_FonctionnalException")
    @DisplayName("Should throw an Exception if RG5 is not respected")
    @Test
    public void checkEcritureFormatAndContainRG5_FonctionnalException() {
        assertThrows(FunctionalException.class, () -> comptabiliteManager.checkEcritureFormatAndContainRG5(ecritureComptableKO1));
    }

    @Tag("checkEcritureComptableUnit")
    @DisplayName("Should return true if ConstraintViolation, RG2, RG3 or RG5 are respected")
    @Test
    public void checkEcritureComptableUnit() throws FunctionalException {
        Boolean result = comptabiliteManager.checkEcritureComptableUnit(ecritureComptableOK);
        assertThat(result).isTrue();
    }

    @Tag("checkEcritureComptableUnit_FonctionnalException")
    @DisplayName("Should throw an Exception if ConstraintViolation, RG2, RG3 or RG5 are not respected")
    @Test
    public void checkEcritureComptableUnit_FonctionnalException() {
        assertThrows(FunctionalException.class, () -> comptabiliteManager.checkEcritureComptableUnit(ecritureComptableKO1));
    }
}

