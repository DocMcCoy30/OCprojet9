package com.dummy.myerp.business.impl.manager;

import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ComptabiliteManagerImplTest {

    @InjectMocks
    @Spy
    private ComptabiliteManagerImpl comptabiliteManager;
    @Mock
    private DaoProxy daoProxyMock;
    @Mock
    private ComptabiliteDao comptabiliteDaoMock;

    private EcritureComptable ecritureComptableOK, ecritureComptableKO1, ecritureComptableKO2;

    @BeforeEach
    public void setUp() {
        AbstractBusinessManager.configure(null, daoProxyMock, null);
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
        assertThatExceptionOfType(FunctionalException.class).isThrownBy(
                () -> comptabiliteManager.formatValeur(5, -2))
                .withMessage("Le nombre de digits ne peut pas etre négatif.");
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
        assertThatExceptionOfType(FunctionalException.class).isThrownBy(
                        () -> comptabiliteManager.referenceTokenizer(reference, 5))
                .withMessage("Index erronné.");
    }

    //--------------- Tests ComptabiliteManagerImpl Check Methods ---------------

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
        Throwable exception = assertThrows(FunctionalException.class,
                () -> comptabiliteManager.checkEcritureConstraintViolation(ecritureComptableKO1));
        assertThat(exception.getMessage())
                .isEqualTo("ConstraintViolation : L'écriture comptable ne respecte pas les règles de gestion.");
    }

    @Tag("checkEcritureComptableUnitRG2")
    @DisplayName("Should return true if RG2 is respected")
    @Test
    public void checkEcritureComptableUnitRG2() throws FunctionalException {
        Boolean result = comptabiliteManager.checkIfEcritureIsEquilibreeRG2(ecritureComptableOK);
        assertThat(result).isTrue();
    }

    @Tag("checkEcritureComptableUnitRG2")
    @DisplayName("Should throw an Exception if RG3 is not respected")
    @Test
    public void checkEcritureComptableUnitRG2_FonctionnalException() {
        Throwable exception = assertThrows(FunctionalException.class,
                () -> comptabiliteManager.checkIfEcritureIsEquilibreeRG2(ecritureComptableKO2));
        assertThat(exception.getMessage())
                .isEqualTo("L'écriture comptable n'est pas équilibrée.");
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
        Throwable exception = assertThrows(FunctionalException.class,
                () -> comptabiliteManager.checkEcritureNumberOfLineRG3(ecritureComptableKO2));
        assertThat(exception.getMessage())
                .isEqualTo("L'écriture comptable doit avoir au moins deux lignes : une ligne au débit et une ligne au crédit.");
    }

    @Tag("checkEcritureFormatAndContainRG5")
    @DisplayName("Should return true if RG5 is respected")
    @Test
    public void checkEcritureFormatAndContainRG5() throws FunctionalException {
        Boolean result = comptabiliteManager.checkEcritureFormatAndContainRG5(ecritureComptableOK);
        assertThat(result).isTrue();
    }

    @Tag("checkEcritureFormatAndContainRG5_FonctionnalException_Case1")
    @DisplayName("Should throw an Exception if RG5 is not respected : code journal erronné")
    @Test
    public void checkEcritureFormatAndContainRG5_FonctionnalException_Case1() {
        Throwable exception = assertThrows(FunctionalException.class, () -> comptabiliteManager.checkEcritureFormatAndContainRG5(ecritureComptableKO1));
        assertThat(exception.getMessage())
                .isEqualTo("Le code journal dans la référence ne correspond pas au code de l'écriture");
    }

    @Tag("checkEcritureFormatAndContainRG5_FonctionnalException_Case2")
    @DisplayName("Should throw an Exception if RG5 is not respected : date erronnée")
    @Test
    public void checkEcritureFormatAndContainRG5_FonctionnalException_Case2() {
        ecritureComptableKO1.setReference("BQ-2016/0005");
        Throwable exception = assertThrows(FunctionalException.class, () -> comptabiliteManager.checkEcritureFormatAndContainRG5(ecritureComptableKO1));
        assertThat(exception.getMessage())
                .isEqualTo("L'année dans la référence ne correspond pas à la date de l'écriture");
    }

    @Tag("checkEcritureFormatAndContainRG5_FonctionnalException_Case3")
    @DisplayName("Should throw an Exception if RG5 is not respected : reference nulle")
    @Test
    public void checkEcritureFormatAndContainRG5_FonctionnalException_Case3() {
        ecritureComptableKO1.setReference(null);
        Throwable exception = assertThrows(FunctionalException.class, () -> comptabiliteManager.checkEcritureFormatAndContainRG5(ecritureComptableKO1));
        assertThat(exception.getMessage())
                .isEqualTo("Aucune référence pour cette écriture");
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
        Throwable exception = assertThrows(FunctionalException.class, () -> comptabiliteManager.checkEcritureComptableUnit(ecritureComptableKO1));
        assertThat(exception.getMessage())
                .isEqualTo("checkEcritureComptableUnit is KO");
    }

    @Tag("checkEcritureComptableContext_Case1")
    @DisplayName("Should return True if RG6 is respected")
    @Test
    public void checkEcritureComptableContext_Case1() throws FunctionalException, NotFoundException {
        EcritureComptable ecritureComptableStub = getEcritureComptableStub();
        when(daoProxyMock.getComptabiliteDao()).thenReturn(comptabiliteDaoMock);
        when(comptabiliteDaoMock.getEcritureComptableByRef(anyString())).thenReturn(ecritureComptableStub);
        boolean result = comptabiliteManager.checkEcritureComptableContext(ecritureComptableStub);
        assertThat(result).isTrue();
    }

    @Tag("checkEcritureComptableContext_Case2")
    @DisplayName("Should return true if EcritureComptable reference is null")
    @Test
    public void checkEcritureComptableContext_Case2() throws FunctionalException {
        EcritureComptable ecritureComptableStub = getEcritureComptableStub();
        ecritureComptableStub.setReference(null);
        boolean result = comptabiliteManager.checkEcritureComptableContext(ecritureComptableStub);
        assertThat(result).isTrue();
    }

    @Tag("checkEcritureComptableContext_FonctionnalException_Case1")
    @DisplayName("Should throw an exception if ecritureComptable Id is null")
    @Test
    public void checkEcritureComptableContext_FonctionnalException_Case1() throws NotFoundException {
        EcritureComptable ecritureComptableStub = getEcritureComptableStub();
        ecritureComptableStub.setId(null);
        when(daoProxyMock.getComptabiliteDao()).thenReturn(comptabiliteDaoMock);
        when(comptabiliteDaoMock.getEcritureComptableByRef(anyString())).thenReturn(ecritureComptableStub);
        Throwable exception = assertThrows(FunctionalException.class,
                () -> comptabiliteManager.checkEcritureComptableContext(ecritureComptableStub));
        assertThat(exception.getMessage())
                .isEqualTo("Une autre écriture comptable existe déjà avec la même référence.");
    }

    @Tag("checkEcritureComptableContext_FonctionnalException_Case2")
    @DisplayName("Should throw an exception if ecritureComptable Id's are !=")
    @Test
    public void checkEcritureComptableContext_FonctionnalException_Case2() throws NotFoundException {
        EcritureComptable ecritureComptableStub1 = getEcritureComptableStub();
        EcritureComptable ecritureComptableStub2 = getEcritureComptableStub();
        ecritureComptableStub2.setId(2);
        when(daoProxyMock.getComptabiliteDao()).thenReturn(comptabiliteDaoMock);
        when(comptabiliteDaoMock.getEcritureComptableByRef(anyString())).thenReturn(ecritureComptableStub1);
        Throwable exception = assertThrows(FunctionalException.class,
                () -> comptabiliteManager.checkEcritureComptableContext(ecritureComptableStub2));
        assertThat(exception.getMessage())
                .isEqualTo("Une autre écriture comptable existe déjà avec la même référence.");
    }

    //--------------- Tests ComptabiliteManagerImpl Crud Methods ---------------

    @Tag("getListCompteComptable")
    @DisplayName("Should return a List of CompteComptable")
    @Test
    public void getListCompteComptable() {
        List<CompteComptable> comptesComptablesStub = getListeComptesComptablesStub();
        doReturn(comptesComptablesStub).when(comptabiliteManager).getListCompteComptable();
        List<CompteComptable> comptesComptables = comptabiliteManager.getListCompteComptable();
        verify(comptabiliteManager, times(1)).getListCompteComptable();
        assertThat(comptesComptables.get(0).getLibelle()).isEqualTo("Compte n°1");
    }

    @Tag("getListJournalComptable")
    @DisplayName("Should return a list of JournalComptable")
    @Test
    public void getListJournalComptable() {
        List<JournalComptable> journalComptablesStub = getListeJournalComptableStub();
        doReturn(journalComptablesStub).when(comptabiliteManager).getListJournalComptable();
        when(comptabiliteManager.getListJournalComptable()).thenReturn(journalComptablesStub);
        List<JournalComptable> journalComptables = comptabiliteManager.getListJournalComptable();
        verify(comptabiliteManager, times(1)).getListJournalComptable();
        assertThat(journalComptables.get(0).getLibelle()).isEqualTo("Achat");
    }

    @Tag("getListEcritureComptable")
    @DisplayName("Should return a list of EcritureComptable")
    @Test
    public void getListEcritureComptable() {
        List<EcritureComptable> ecritureComptablesStub = getListeEcrituresComptablesStub();
        doReturn(ecritureComptablesStub).when(comptabiliteManager).getListEcritureComptable();
        List<EcritureComptable> ecritureComptables = comptabiliteManager.getListEcritureComptable();
        verify(comptabiliteManager, times(1)).getListEcritureComptable();
        assertThat(ecritureComptables.get(0).getReference()).isEqualTo("AC-2019/00001");
    }

    @Tag("addReference")
    @DisplayName("Should return add a reference in EcritureComptable")
    @Test
    public void addReference() throws FunctionalException {
    }

    //--------------  STUBS  --------------------
/*
    @Tag("")
    @DisplayName("")
    @Test
    */

    /**
     * STUB : Code pour créer une liste de comptes comptables.
     *
     * @return List<CompteComptable> une liste de comptes comptables.
     */
    private List<CompteComptable> getListeComptesComptablesStub() {
        List<CompteComptable> compteComptables = new LinkedList<>();
        CompteComptable compteComptable1 = new CompteComptable(1, "Compte n°1");
        CompteComptable compteComptable2 = new CompteComptable(2, "Compte n°2");
        compteComptables.add(compteComptable1);
        compteComptables.add(compteComptable2);
        return compteComptables;
    }

    /**
     * STUB : Code pour créer une liste de journaux comptables comptables.
     *
     * @return List<JournalComptable> une liste journaux comptables comptables.
     */
    private List<JournalComptable> getListeJournalComptableStub() {
        List<JournalComptable> journalComptables = new ArrayList<>();
        JournalComptable journalComptable1 = new JournalComptable("AC", "Achat");
        JournalComptable journalComptable2 = new JournalComptable("VE", "Vente");
        JournalComptable journalComptable3 = new JournalComptable("BQ", "Banque");
        journalComptables.add(journalComptable1);
        journalComptables.add(journalComptable2);
        journalComptables.add(journalComptable3);
        return journalComptables;
    }

    /**
     * STUB : Code pour créer une écriture comptable.
     *
     * @return EcritureComptable
     */
    private EcritureComptable getEcritureComptableStub() {
        EcritureComptable vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setId(1);
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setReference("AC-2019/00001");
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2, "fourniture"),
                "fournisseur", new BigDecimal(412),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(6, "la banque"),
                "", null,
                new BigDecimal(412)));
        return vEcritureComptable;
    }

    /**
     * STUB : Code pour créer une liste d'écritures comptables.
     *
     * @return List<EcritureComptable> une liste d'écritures comptables.
     */
    private List<EcritureComptable> getListeEcrituresComptablesStub() {
        List<EcritureComptable> ecritureComptables = new ArrayList<>();
        EcritureComptable vEcritureComptable = getEcritureComptableStub();
        ecritureComptables.add(vEcritureComptable);
        return ecritureComptables;
    }

    /**
     * STUB : Code pour créer une liste de sequences écritures comptables.
     *
     * @return List<SequenceEcritureComptable> une liste d'écritures comptables.
     */
    private List<SequenceEcritureComptable> getListeSequenceEcritureComptableStub() {
        List<SequenceEcritureComptable> sequenceEcritureComptables = new ArrayList<>();
        sequenceEcritureComptables.add(new SequenceEcritureComptable("AC", 2021, 1));
        sequenceEcritureComptables.add(new SequenceEcritureComptable("VE", 2021, 1));
        sequenceEcritureComptables.add(new SequenceEcritureComptable("BQ", 2021, 1));
        return sequenceEcritureComptables;
    }


}

