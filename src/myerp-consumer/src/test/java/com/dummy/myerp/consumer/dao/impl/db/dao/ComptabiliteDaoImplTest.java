package com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.EcritureComptableRM;
import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.SequenceEcritureComptableRM;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ComptabiliteDaoImplTest {

    @InjectMocks
    @Spy
    ComptabiliteDaoImpl comptabiliteDao;

    List<CompteComptable> compteComptables;
    List<EcritureComptable> ecritureComptables;
    List<JournalComptable> journalComptables;
    EcritureComptable ecritureComptable;
    List<SequenceEcritureComptable> sequenceEcritureComptables;

    @Test
    public void should_be_sure_comptabiliteDao_object_exists() {
        // Given // When // Then
        assertThat(comptabiliteDao).isNotNull();
    }

    @Test
    @Tag("getListCompteComptable")
    @DisplayName("Should return list of accounts")
    public void getListCompteComptable() {
        // GIVEN
        List<CompteComptable> compteComptablesStub = getListeComptesComptablesStub();
        // WHEN
        doReturn(compteComptablesStub).when(comptabiliteDao).getListCompteComptableQueryResult();
        // THEN
        List<CompteComptable> compteComptables = comptabiliteDao.getListCompteComptable();
        assertThat(compteComptables.get(0).getNumero()).isEqualTo(compteComptablesStub.get(0).getNumero());
        assertThat(compteComptables.get(0).getLibelle()).isEqualTo(compteComptablesStub.get(0).getLibelle());
        assertThat(compteComptables.get(1).getNumero()).isEqualTo(compteComptablesStub.get(1).getNumero());
        assertThat(compteComptables.get(1).getLibelle()).isEqualTo(compteComptablesStub.get(1).getLibelle());
    }

    @Test
    @Tag("getListJournalComptable")
    @DisplayName("Should return list of journal comptable")
    public void getListJournalComptable() {
        // GIVEN
        List<JournalComptable> journalComptablesStub = getListeJournalComptableStub();
        // WHEN
        doReturn(journalComptablesStub).when(comptabiliteDao)
                .getListJournalComptableQueryResult();
        // THEN
        List<JournalComptable> journalComptables = comptabiliteDao.getListJournalComptable();
        assertThat(journalComptables.get(0).getCode()).isEqualTo(journalComptablesStub.get(0).getCode());
        assertThat(journalComptables.get(0).getLibelle()).isEqualTo(journalComptablesStub.get(0).getLibelle());
        assertThat(journalComptables.get(1).getCode()).isEqualTo(journalComptablesStub.get(1).getCode());
        assertThat(journalComptables.get(1).getLibelle()).isEqualTo(journalComptablesStub.get(1).getLibelle());
    }

    @Test
    @Tag("getListEcritureComptable")
    @DisplayName("Should return list of ecriture comptable")
    public void getListEcritureComptable() {
        // GIVEN
        List<EcritureComptable> ecrituresComptablesStub = getListeEcrituresComptablesStub();
        // WHEN
        doReturn(ecrituresComptablesStub).when(comptabiliteDao)
                .getListEcritureComptableQueryResult();
        // THEN
        List<EcritureComptable> ecritureComptables = comptabiliteDao.getListEcritureComptable();
        assertThat(ecritureComptables.get(0).getReference()).isEqualTo(ecrituresComptablesStub.get(0).getReference());
        assertThat(ecritureComptables.get(0).getLibelle()).isEqualTo(ecrituresComptablesStub.get(0).getLibelle());
        assertThat(ecritureComptables.get(0).getJournal().getCode()).isEqualTo(ecrituresComptablesStub.get(0).getJournal().getCode());
        assertThat(ecritureComptables.get(0).getJournal().getLibelle()).isEqualTo(ecrituresComptablesStub.get(0).getJournal().getLibelle());
    }

    @Test
    @Tag("getEcritureComptable")
    @DisplayName("Should find and return an Ecriture Comptable by its Id")
    public void getEcritureComptable() throws NotFoundException {
        // GIVEN
        int id = 1;
        EcritureComptable ecritureComptableStub = getEcritureComptableStub();
        // WHEN
        doReturn(ecritureComptableStub).when(comptabiliteDao)
                .getEcritureComptableQueryResult(any(MapSqlParameterSource.class), any(EcritureComptableRM.class));
        // THEN
        EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptable(id);
        assertThat(ecritureComptable.getReference()).isEqualTo(ecritureComptableStub.getReference());
        assertThat(ecritureComptable.getLibelle()).isEqualTo(ecritureComptableStub.getLibelle());
    }

    @Test
    @Tag("getEcritureComptable_ExceptionCase")
    @DisplayName("Should throw a NotFoundException when result returned is null")
    public void getEcritureComptable_ExceptionCase() throws EmptyResultDataAccessException {
        // GIVEN
        int id = 1;
        // WHEN
        doThrow(EmptyResultDataAccessException.class)
                .when(comptabiliteDao)
                .getEcritureComptableQueryResult(any(MapSqlParameterSource.class), any(EcritureComptableRM.class));
        // THEN
        assertThrows(NotFoundException.class,
                () -> comptabiliteDao.getEcritureComptable(id),
                "EcritureComptable non trouvée : id=1");
    }

    @Test
    @Tag("getEcritureComptableByRef")
    @DisplayName("Should find and return an Ecriture Comptable by its Ref")
    public void getEcritureComptableByRef() throws NotFoundException {
        // GIVEN
        String ref = "AC-2019/00001";
        EcritureComptable ecritureComptableStub = getEcritureComptableStub();
        // WHEN
        doReturn(ecritureComptableStub).when(comptabiliteDao)
                .getEcritureComptableByRefQueryResult(any(MapSqlParameterSource.class), any(EcritureComptableRM.class));
        // THEN
        EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptableByRef(ref);
        assertThat(ecritureComptable.getReference()).isEqualTo(ecritureComptableStub.getReference());
        assertThat(ecritureComptable.getLibelle()).isEqualTo(ecritureComptableStub.getLibelle());
    }

    @Test
    @Tag("getEcritureComptableByRef_ExceptionCase")
    @DisplayName("Should throw a NotFoundException when result returned is null")
    public void getEcritureComptableByRef_ExceptionCase() throws EmptyResultDataAccessException {
        // GIVEN
        String ref = "AC-2019/00001";
        // WHEN
        doThrow(EmptyResultDataAccessException.class)
                .when(comptabiliteDao)
                .getEcritureComptableByRefQueryResult(any(MapSqlParameterSource.class), any(EcritureComptableRM.class));
        // THEN
        assertThrows(NotFoundException.class,
                () -> comptabiliteDao.getEcritureComptableByRef(ref),
                "\"EcritureComptable non trouvée : reference=AC-2019/00001");
    }

    @Test
    @Tag("getListSequenceEcritureComptable")
    @DisplayName("Should return list of sequence ecriture comptable")
    public void getListSequenceEcritureComptable() {
        // GIVEN
        List<SequenceEcritureComptable> sequenceEcritureComptablesStub = getListeSequenceEcritureComptableStub();
        // WHEN
        doReturn(sequenceEcritureComptablesStub).when(comptabiliteDao).getListSequenceEcritureComptablesQueryResult();
        // THEN
        List<SequenceEcritureComptable> sequenceEcritureComptables = comptabiliteDao.getListSequenceEcritureComptable();
        assertThat(sequenceEcritureComptables.get(1).getJournalCode())
                .isEqualTo(sequenceEcritureComptablesStub.get(1).getJournalCode());
        assertThat(sequenceEcritureComptables.get(1).getAnnee())
                .isEqualTo(sequenceEcritureComptablesStub.get(1).getAnnee());
        assertThat(sequenceEcritureComptables.get(1)
                .getDerniereValeur()).isEqualTo(sequenceEcritureComptablesStub.get(1).getDerniereValeur());
    }

    @Test
    @Tag("getSequenceEcritureComptableByYearAndJournalCode")
    @DisplayName("Should find and return a SequenceEcritureComptable by its journalCode and annee")
    public void getSequenceEcritureComptableByYearAndJournalCode() throws NotFoundException {
        // GIVEN
        int annee = 2021;
        String journalCode = "AC";
        SequenceEcritureComptable sequenceEcritureComptableStub = getListeSequenceEcritureComptableStub().get(0);
        // WHEN
        doReturn(sequenceEcritureComptableStub).when(comptabiliteDao)
                .getSequenceEcritureComptableQueryResult(any(MapSqlParameterSource.class), any(SequenceEcritureComptableRM.class));
        // THEN
        SequenceEcritureComptable sequenceEcritureComptable = comptabiliteDao
                .getSequenceEcritureComptableByYearAndJournalCode(journalCode, annee);
        assertThat(sequenceEcritureComptable.getJournalCode()).isEqualTo(sequenceEcritureComptableStub.getJournalCode());
        assertThat(sequenceEcritureComptable.getAnnee()).isEqualTo(sequenceEcritureComptableStub.getAnnee());
        assertThat(sequenceEcritureComptable.getDerniereValeur()).isEqualTo(sequenceEcritureComptableStub.getDerniereValeur());
    }

    @Test
    @Tag("getSequenceEcritureComptableByYearAndJournalCode_ExceptionCase")
    @DisplayName("Should throw a NotFoundException when result returned is null")
    public void getSequenceEcritureComptableByYearAndJournalCode_ExceptionCase() throws EmptyResultDataAccessException {
        // GIVEN
        String ref = "AC-2019/00001";
        // WHEN
        doThrow(EmptyResultDataAccessException.class)
                .when(comptabiliteDao)
                .getEcritureComptableByRefQueryResult(any(MapSqlParameterSource.class), any(EcritureComptableRM.class));
        // THEN
        assertThrows(NotFoundException.class,
                () -> comptabiliteDao.getEcritureComptableByRef(ref),
                "\"EcritureComptable non trouvée : reference=AC-2019/00001");
    }

    @Test
    @Tag("loadListLigneEcriture")
    @DisplayName("Should find and return a Liste of LigneEcritureComptable by the EcritureComptableId")
    public void loadListLigneEcriture() {
        //GIVEN
        EcritureComptable vEcritureComptable = new EcritureComptable();
        //WHEN
        comptabiliteDao.loadListLigneEcriture(vEcritureComptable);
        //THEN ??
    }

//--------------  STUBS  --------------------

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

//    /**
//     * Mock ComptabiliteDaoImpl
//     */
//    private static class ComptabiliteDaoImplFake extends ComptabiliteDaoImpl {
//        @Override
//        protected JdbcTemplate getJdbcTemplate() {
//            return new JdbcTemplate();
//        }
//
//        @Override
//        protected List<CompteComptable> getListCompteComptableQueryResult() {
//            return generateListeComptesComptables();
//        }
//
//        @Override
//        protected List<JournalComptable> getListJournalComptableQueryResult() {
//            return generateListeJournalComptable();
//        }
//
//        @Override
//        protected List<EcritureComptable> getListEcritureComptableQueryResult() {
//            return generateListeEcrituresComptables();
//        }
//
//        @Override
//        protected EcritureComptable getEcritureComptableQueryResult(MapSqlParameterSource vParams, EcritureComptableRM vRM) {
//            return generateListeEcrituresComptables().get(0);
//        }
//
//        @Override
//        protected EcritureComptable getEcritureComptableByRefQueryResult(MapSqlParameterSource vParams, EcritureComptableRM vRM) {
//            return generateListeEcrituresComptables().get(0);
//        }
//
//        @Override
//        protected List<SequenceEcritureComptable> getListSequenceEcritureComptablesQueryResult() {
//            return generateListeSequenceEcritureComptable();
//        }
//
//        @Override
//        protected SequenceEcritureComptable getSequenceEcritureComptableQueryResult(SequenceEcritureComptableRM vRM, MapSqlParameterSource vParams) {
//            return generateListeSequenceEcritureComptable().get(0);
//        }
//
//        @Override
//        protected List<LigneEcritureComptable> getLigneEcritureComptablesQueryResult(MapSqlParameterSource vSqlParams, LigneEcritureComptableRM vRM) {
//            List<EcritureComptable> ecritureComptables = this.generateListeEcrituresComptables();
//            return ecritureComptables.get(0).getListLigneEcriture();
//        }
//      }



