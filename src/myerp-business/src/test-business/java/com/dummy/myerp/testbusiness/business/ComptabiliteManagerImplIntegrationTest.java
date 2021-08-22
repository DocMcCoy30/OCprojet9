package com.dummy.myerp.testbusiness.business;

import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ComptabiliteManagerImplIntegrationTest extends BusinessTestCase {

    private ComptabiliteManager comptabiliteManager;
    private EcritureComptable ecritureComptable;

    @BeforeEach
    public void initComptabiliteManager() {
        comptabiliteManager = getBusinessProxy().getComptabiliteManager();
        ecritureComptable = new EcritureComptable();
        ecritureComptable.setId(1);
        ecritureComptable.setDate(new Date());
        ecritureComptable.setLibelle("Insert_Ecriture_IT");
        ecritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        List<LigneEcritureComptable> listLigneEcritureComptable = ecritureComptable.getListLigneEcriture();
        listLigneEcritureComptable.add(
                new LigneEcritureComptable(
                        new CompteComptable(401, "Débit"), "Test_Insert", new BigDecimal(123), null)
        );
        listLigneEcritureComptable.add(
                new LigneEcritureComptable(
                        new CompteComptable(512, "Crédit"), "Test_Insert", null, new BigDecimal(123))
        );
    }

    @Tag("getListCompteComptable")
    @DisplayName("Should be ok if the list of CompteComptable is >= 7")
    @Test
    public void getListCompteComptable() {
        List<CompteComptable> compteComptables = comptabiliteManager.getListCompteComptable();
        assertThat(compteComptables.size()).isGreaterThanOrEqualTo(7);
    }

    @Tag("getListJournalComptable")
    @DisplayName("Should be ok if the list of JournalComptable is >= 4")
    @Test
    public void getListJournalComptable() {
        List<JournalComptable> journalComptables = comptabiliteManager.getListJournalComptable();
        assertThat(journalComptables.size()).isGreaterThanOrEqualTo(4);
    }

    @Tag("getListEcritureComptable")
    @DisplayName("Should be ok if the list of EcritureComptable is >= 5")
    @Test
    public void getListEcritureComptable() {
        List<EcritureComptable> ecritureComptables = comptabiliteManager.getListEcritureComptable();
        assertThat(ecritureComptables.size()).isGreaterThanOrEqualTo(5);
    }


    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class ComptabiliteManagerImplSequenceInOrderIntegrationTest extends BusinessTestCase {

        @Order(1)
        @Tag("insert_Ecriture_And_Sequence")
        @DisplayName("Should insert an EcritureComptable and a SequenceEcritureComptable")
        @Test
        public void insert_Ecriture_And_Sequence() throws NotFoundException, FunctionalException {
            comptabiliteManager.addReference(ecritureComptable);
            comptabiliteManager.insertEcritureComptable(ecritureComptable);
            List<EcritureComptable> ecritureComptables = comptabiliteManager.getListEcritureComptable();
            EcritureComptable ecritureSaved = ecritureComptables.get(ecritureComptables.size() - 1);

            SequenceEcritureComptable sequenceUpdated = comptabiliteManager
                    .getSequenceEcritureComptableByYearAndJournalCode("AC", 2021);
            assertThat(sequenceUpdated.getDerniereValeur()).isGreaterThanOrEqualTo(1);
            assertThat(ecritureSaved.getLibelle()).isEqualTo("Insert_Ecriture_IT");
            assertThat(ecritureSaved.getJournal().getLibelle()).isEqualTo("Achat");
        }

        @Order(2)
        @Tag("update_Ecriture_And_Sequence")
        @DisplayName("Should update an EcritureComptable ")
        @Test
        public void update_Ecriture_And_Sequence() throws FunctionalException, NotFoundException {
            List<EcritureComptable> ecritureComptables = comptabiliteManager.getListEcritureComptable();
            EcritureComptable ecritureSaved = ecritureComptables.get(ecritureComptables.size() - 1);
            ecritureSaved.setLibelle("Update_Ecriture_IT");
            ecritureSaved.setJournal(new JournalComptable("BQ", "Banque"));
            comptabiliteManager.addReference(ecritureSaved);
            comptabiliteManager.updateEcritureComptable(ecritureSaved);
            List<EcritureComptable> ecritureComptables2 = comptabiliteManager.getListEcritureComptable();
            EcritureComptable ecritureUpdated = ecritureComptables.get(ecritureComptables2.size() - 1);
            SequenceEcritureComptable sequenceUpdated = comptabiliteManager
                    .getSequenceEcritureComptableByYearAndJournalCode("BQ", 2021);
            assertThat(sequenceUpdated.getDerniereValeur()).isGreaterThanOrEqualTo(1);
            assertThat(ecritureUpdated.getLibelle()).isEqualTo("Update_Ecriture_IT");
            assertThat(ecritureUpdated.getJournal().getLibelle()).isEqualTo("Banque");

        }

        @Order(3)
        @Tag("delete_Ecriture")
        @DisplayName("Should delete an EcritureComptable")
        @Test
        public void delete_Ecriture() {
            List<EcritureComptable> ecritureComptable1 = comptabiliteManager.getListEcritureComptable();
            EcritureComptable ecritureToDelete = ecritureComptable1.get(ecritureComptable1.size() - 1);
            comptabiliteManager.deleteEcritureComptable(ecritureToDelete.getId());
            List<EcritureComptable> ecritureComptables2 = comptabiliteManager.getListEcritureComptable();
            EcritureComptable lastEcriture = ecritureComptables2.get(ecritureComptables2.size() - 1);
            assertThat(ecritureComptables2.size()).isEqualTo(ecritureComptable1.size() - 1);
            assertThat(lastEcriture.getId()).isNotEqualTo(ecritureToDelete.getId());
        }
    }

}

