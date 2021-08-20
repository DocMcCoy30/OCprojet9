package com.dummy.myerp.testbusiness.business;

import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ComptabiliteManagerImplIntegrationTest extends BusinessTestCase {

    private final Logger logger = LogManager.getLogger(ComptabiliteManagerImplIntegrationTest.class);

    private ComptabiliteManager comptabiliteManager;
    private EcritureComptable ecritureComptable;

    @BeforeEach
    public void initComptabiliteManager() {
        comptabiliteManager = getBusinessProxy().getComptabiliteManager();
        ecritureComptable = new EcritureComptable();
        ecritureComptable.setId(1);
        ecritureComptable.setDate(new Date());
        ecritureComptable.setLibelle("Insert_referenceEcritureSequence_IT");
        ecritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        List<LigneEcritureComptable> listLigneEcritureComptable = ecritureComptable.getListLigneEcriture();
        listLigneEcritureComptable.add(
                new LigneEcritureComptable(
                        new CompteComptable(401, "Débit"), "Test d'intégration", new BigDecimal(123), null)
        );
        listLigneEcritureComptable.add(
                new LigneEcritureComptable(
                        new CompteComptable(512, "Crédit"), "Test d'intégration", null, new BigDecimal(123))
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

    @Tag("addReference")
    @DisplayName("Should add the reference and insert the EcritureComptable in the DB")
    @Test
    public void insertUpdateDelete_referenceEcritureSequence() throws NotFoundException, FunctionalException {
        comptabiliteManager.addReference(ecritureComptable);
        comptabiliteManager.insertEcritureComptable(ecritureComptable);

        List<EcritureComptable> ecritureComptable1 = comptabiliteManager.getListEcritureComptable();
        EcritureComptable ecritureSaved = ecritureComptable1.get(ecritureComptable1.size()-1);
        assertThat(ecritureSaved.getReference()).isEqualTo("AC-2021/00001");
        assertThat(ecritureSaved.getLibelle()).isEqualTo("Insert_referenceEcritureSequence_IT");

        ecritureSaved.setLibelle("Update_referenceEcritureSequence_IT");
        comptabiliteManager.updateEcritureComptable(ecritureSaved);
        EcritureComptable ecritureUpdated = ecritureComptable1.get(ecritureComptable1.size()-1);
        assertThat(ecritureUpdated.getReference()).isEqualTo("AC-2021/00001");
        assertThat(ecritureUpdated.getLibelle()).isEqualTo("Update_referenceEcritureSequence_IT");

        comptabiliteManager.deleteEcritureComptable(ecritureSaved.getId());
        List<EcritureComptable> ecritureComptables2 = comptabiliteManager.getListEcritureComptable();
        EcritureComptable lastEcriture = ecritureComptables2.get(ecritureComptables2.size()-1);
        assertThat(ecritureComptables2.size()).isEqualTo(ecritureComptable1.size()-1);
        assertThat(lastEcriture.getId()).isNotEqualTo(ecritureSaved.getId());

        comptabiliteManager.addReference(ecritureComptable);
        SequenceEcritureComptable sequenceUpdated = comptabiliteManager.getSequenceEcritureComptableByYearAndJournalCode("AC",2021);
        assertThat(sequenceUpdated.getDerniereValeur()).isEqualTo(2);
    }

}
