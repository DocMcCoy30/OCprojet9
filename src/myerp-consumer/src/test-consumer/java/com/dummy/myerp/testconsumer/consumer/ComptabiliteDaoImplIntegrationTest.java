package com.dummy.myerp.testconsumer.consumer;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

public class ComptabiliteDaoImplIntegrationTest extends ConsumerTestCase {

    Logger logger = LogManager.getLogger(ComptabiliteDaoImplIntegrationTest.class);
    ComptabiliteDao comptabiliteDao = getDaoProxy().getComptabiliteDao();

    @Test
    @Tag("getListCompteComptable")
    @DisplayName("Should return all the comptes comptables from DB")
    public void getListCompteComptable() {
        logger.info("Logger is working");
        List<CompteComptable> compteComptables = comptabiliteDao.getListCompteComptable();
        assertThat(compteComptables.size()).isGreaterThanOrEqualTo(7);
    }

    @Test
    @Tag("getListJournalComptable")
    @DisplayName("Should return all the journaux comptables from DB")
    public void getListJournalComptable() {
        List<JournalComptable> journalComptables = comptabiliteDao.getListJournalComptable();
        assertThat(journalComptables.size()).isGreaterThanOrEqualTo(4);
    }

    @Test
    @Tag("getListEcritureComptable")
    @DisplayName("Should return all the ecritures comptables from DB")
    public void getListEcritureComptable() {
        List<EcritureComptable> ecritureComptables = comptabiliteDao.getListEcritureComptable();
        assertThat(ecritureComptables.size()).isGreaterThanOrEqualTo(5);
    }

    @Test
    @Tag("getEcritureComptable")
    @DisplayName("Should find & return an ecriture comptable by its Id")
    public void getEcritureComptable() throws NotFoundException {
        EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptable(-1);
        assertThat(ecritureComptable.getReference()).isEqualTo("AC-2016/00001");
    }

    @Test
    @Tag("getEcritureComptable_ExceptionTest")
    @DisplayName("Should throw an exception when ecritureComptable.id is not found or null")
    public void getEcritureComptable_exceptionCase() {
        assertThrows(NotFoundException.class, () -> {
            comptabiliteDao.getEcritureComptable(12);
            comptabiliteDao.getEcritureComptable(null);
        });
    }

    @Test
    @Tag("getEcritureComptableByRef")
    @DisplayName("Should find & return an ecriture comptable by its reference")
    public void getEcritureComptableByRef() throws NotFoundException {
        EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptableByRef("AC-2016/00001");
        assertThat(ecritureComptable).isNotNull();
        assertThat(ecritureComptable.getLibelle()).isEqualTo("Cartouches d’imprimante");
    }

    @Test
    @Tag("getEcritureComptableByRef_ExceptionTest")
    @DisplayName("Should throw an exception when ecritureComptable.reference is not found or null")
    public void getEcritureComptableByRef_xceptionCase() {
        assertThrows(NotFoundException.class, () -> {
            comptabiliteDao.getEcritureComptableByRef("AC-1942-00254");
            comptabiliteDao.getEcritureComptableByRef(null);

        });
    }

    @Test
    @Tag("insertEcritureComptable")
    @DisplayName("Should insert the ecriture comptable AND the list of LigneEcritureComptable in DB")
    public void insertEcritureComptable() {
        //GIVEN
        //Date de l'écriture
        Date date = new Date();
        //Annee de la référence
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int annee = calendar.get(Calendar.YEAR);
        //Journal comptable
        JournalComptable journalComptable = comptabiliteDao.getListJournalComptable().get(0);
        //Libellé
        String libelle = "libellé insertLigneComptableTest";
        //Référence
        String reference = journalComptable.getCode() + "-" + annee + "/00009";
        //Set EcritureCompable
        EcritureComptable ecritureComptable = new EcritureComptable(journalComptable, reference, date, libelle);
        //Set LigneEcriture
//        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2, "fourniture"),
//                "fournisseur", new BigDecimal(412),
//                null));
//        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(6, "la banque"),
//                "", null,
//                new BigDecimal(412)));
        //WHEN
        comptabiliteDao.insertEcritureComptable(ecritureComptable);
        //THEN
        assertThat(ecritureComptable.getReference()).isEqualTo("AC-2021/00009");
    }

    @Test
    @Tag("getListSequenceEcritureComptable")
    @DisplayName("Should return a list of all the SequenceEcritureComptable from DB")
    public void getListSequenceEcritureComptable() {
        //WHEN
        List<SequenceEcritureComptable> sequenceEcritureComptables = comptabiliteDao.getListSequenceEcritureComptable();
        //THEN
        assertThat(sequenceEcritureComptables.size()).isGreaterThanOrEqualTo(4);
        assertThat(sequenceEcritureComptables.get(0).getJournalCode()).isEqualTo("AC");
        assertThat(sequenceEcritureComptables.get(2).getDerniereValeur()).isEqualTo(51);
    }

    @Test
    @Tag("getSequenceEcritureComptableByYearAndJournalCode")
    @DisplayName("Should find & return a SequenceEcritureComptable from DB with its journalCode + annee")
    public void getSequenceEcritureComptableByYearAndJournalCode() throws NotFoundException {
        SequenceEcritureComptable sequenceEcritureComptable = comptabiliteDao.getSequenceEcritureComptableByYearAndJournalCode("BQ", 2016);
        assertThat(sequenceEcritureComptable).isNotNull();
        assertThat(sequenceEcritureComptable.getDerniereValeur()).isEqualTo(51);
    }

}

    /* Méthodes à tester :
    void loadListLigneEcriture(EcritureComptable pEcritureComptable);
    => void insertEcritureComptable(EcritureComptable pEcritureComptable);
    void updateEcritureComptable(EcritureComptable pEcritureComptable);
    void deleteEcritureComptable(Integer pId);
    SequenceEcritureComptable getSequenceEcritureComptableByYearAndJournalCode(String code, int year) throws NotFoundException;
    void updateSequenceEcritureComptable(SequenceEcritureComptable sequence);
    void insertSequenceEcritureComptable(SequenceEcritureComptable sequence);
     */
