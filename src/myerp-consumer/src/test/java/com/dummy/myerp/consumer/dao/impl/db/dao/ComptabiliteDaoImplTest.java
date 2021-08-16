package com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.EcritureComptableRM;
import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.LigneEcritureComptableRM;
import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.SequenceEcritureComptableRM;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class ComptabiliteDaoImplTest {

    @InjectMocks
    ComptabiliteDaoImpl comptabiliteDao;

    List<CompteComptable> compteComptables;
    List<EcritureComptable> ecritureComptables;
    List<JournalComptable> journalComptables;
    EcritureComptable ecritureComptable;
    List<SequenceEcritureComptable> sequenceEcritureComptables;

    @BeforeEach
    public void init() {
        comptabiliteDao = new ComptabiliteDaoImplFake();
        compteComptables = comptabiliteDao.getListCompteComptableQueryResult();
        ecritureComptables = comptabiliteDao.getListEcritureComptableQueryResult();
        journalComptables = comptabiliteDao.getListJournalComptableQueryResult();
        ecritureComptable = comptabiliteDao.getEcritureComptableQueryResult(new MapSqlParameterSource(), new EcritureComptableRM());
        sequenceEcritureComptables = comptabiliteDao.getListSequenceEcritureComptablesQueryResult();
    }

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
        // WHEN
        List<CompteComptable> liste = comptabiliteDao.getListCompteComptable();
        // THEN
        assertThat(liste.get(0).getNumero()).isEqualTo(1);
        assertThat(liste.get(0).getLibelle()).isEqualTo("Compte n°1");
        assertThat(liste.get(1).getNumero()).isEqualTo(2);
        assertThat(liste.get(1).getLibelle()).isEqualTo("Compte n°2");
    }

    @Test
    @Tag("getListJournalComptable")
    @DisplayName("Should return list of journal comptable")
    public void getListJournalComptable() {
        // GIVEN
        // WHEN
        List<JournalComptable> liste = comptabiliteDao.getListJournalComptable();
        // THEN
        assertThat(liste.get(0).getCode()).isEqualTo("AC");
        assertThat(liste.get(0).getLibelle()).isEqualTo("Achat");
        assertThat(liste.get(1).getCode()).isEqualTo("VE");
        assertThat(liste.get(1).getLibelle()).isEqualTo("Vente");
    }

    @Test
    @Tag("getListEcritureComptable")
    @DisplayName("Should return list of ecriture comptable")
    public void getListEcritureComptable() {
        // GIVEN
        // WHEN
        List<EcritureComptable> liste = comptabiliteDao.getListEcritureComptable();
        // THEN
        assertThat(liste.get(0).getReference()).isEqualTo("AC-2019/00001");
        assertThat(liste.get(0).getLibelle()).isEqualTo("Libelle");
        assertThat(liste.get(0).getJournal().getCode()).isEqualTo("AC");
        assertThat(liste.get(0).getJournal().getLibelle()).isEqualTo("Achat");
    }

    @Test
    @Tag("getEcritureComptable")
    @DisplayName("Should find and return an Ecriture Comptable by its Id")
    public void getEcritureComptable() throws NotFoundException {
        // GIVEN
        int id = 1;
        // WHEN
        EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptable(id);
        // THEN
        assertThat(ecritureComptable.getReference()).isEqualTo("AC-2019/00001");
        assertThat(ecritureComptable.getLibelle()).isEqualTo("Libelle");
    }

    @Test
    @Tag("getEcritureComptableByRef")
    @DisplayName("Should find and return an Ecriture Comptable by its Ref")
    public void getEcritureComptableByRef() throws NotFoundException {
        // GIVEN
        String ref = "AC-2019/00001";
        // WHEN
        EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptableByRef(ref);
        // THEN
        assertThat(ecritureComptable.getReference()).isEqualTo("AC-2019/00001");
        assertThat(ecritureComptable.getLibelle()).isEqualTo("Libelle");
    }

    @Test
    @Tag("getListSequenceEcritureComptable")
    @DisplayName("Should return list of sequence ecriture comptable")
    public void getListSequenceEcritureComptable() {
        // GIVEN
        // WHEN
        List<SequenceEcritureComptable> liste = comptabiliteDao.getListSequenceEcritureComptable();
        // THEN
        assertThat(liste.get(1).getJournalCode()).isEqualTo("VE");
        assertThat(liste.get(1).getAnnee()).isEqualTo(2021);
        assertThat(liste.get(1).getDerniereValeur()).isEqualTo(1);
    }

    @Test
    @Tag("getEcritureComptableByRef")
    @DisplayName("Should find and return an Ecriture Comptable by its Ref")
    public void getSequenceEcritureComptableByYearAndJournalCode() throws NotFoundException {
        // GIVEN
        int annee = 2021;
        String journalCode = "AC";
        // WHEN
        SequenceEcritureComptable sequenceEcritureComptable = comptabiliteDao.getSequenceEcritureComptableByYearAndJournalCode(journalCode, annee);
        // THEN
        assertThat(sequenceEcritureComptable.getJournalCode()).isEqualTo("AC");
        assertThat(sequenceEcritureComptable.getAnnee()).isEqualTo(2021);
        assertThat(sequenceEcritureComptable.getDerniereValeur()).isEqualTo(1);
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


//----------------
//----- FAKE -----
//----------------

    /**
     * Mock ComptabiliteDaoImpl
     */
    private static class ComptabiliteDaoImplFake extends ComptabiliteDaoImpl {
        @Override
        protected JdbcTemplate getJdbcTemplate() {
            return new JdbcTemplate();
        }

        @Override
        protected List<CompteComptable> getListCompteComptableQueryResult() {
            return generateListeComptesComptables();
        }

        @Override
        protected List<JournalComptable> getListJournalComptableQueryResult() {
            return generateListeJournalComptable();
        }

        @Override
        protected List<EcritureComptable> getListEcritureComptableQueryResult() {
            return generateListeEcrituresComptables();
        }

        @Override
        protected EcritureComptable getEcritureComptableQueryResult(MapSqlParameterSource vParams, EcritureComptableRM vRM) {
            return generateListeEcrituresComptables().get(0);
        }

        @Override
        protected List<SequenceEcritureComptable> getListSequenceEcritureComptablesQueryResult() {
            return generateListeSequenceEcritureComptable();
        }

        @Override
        protected SequenceEcritureComptable getSequenceEcritureComptableQueryResult(SequenceEcritureComptableRM vRM, MapSqlParameterSource vParams) {
            return generateListeSequenceEcritureComptable().get(0);
        }

        @Override
        protected List<LigneEcritureComptable> getLigneEcritureComptablesQueryResult(MapSqlParameterSource vSqlParams, LigneEcritureComptableRM vRM) {
            List<EcritureComptable> ecritureComptables = this.generateListeEcrituresComptables();
            return ecritureComptables.get(0).getListLigneEcriture();
        }

        /**
         * STUB : Code pour créer une liste de comptes comptables.
         *
         * @return List<CompteComptable> une liste de comptes comptables.
         */
        private List<CompteComptable> generateListeComptesComptables() {
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
        private List<JournalComptable> generateListeJournalComptable() {
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
         * STUB : Code pour créer une liste d'écritures comptables.
         *
         * @return List<EcritureComptable> une liste d'écritures comptables.
         */
        private List<EcritureComptable> generateListeEcrituresComptables() {
            List<EcritureComptable> ecritureComptables = new ArrayList<>();
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
            ecritureComptables.add(vEcritureComptable);
            return ecritureComptables;
        }

        /**
         * STUB : Code pour créer une liste de sequences écritures comptables.
         *
         * @return List<SequenceEcritureComptable> une liste d'écritures comptables.
         */
        private List<SequenceEcritureComptable> generateListeSequenceEcritureComptable() {
            List<SequenceEcritureComptable> sequenceEcritureComptables = new ArrayList<>();
            sequenceEcritureComptables.add(new SequenceEcritureComptable("AC", 2021, 1));
            sequenceEcritureComptables.add(new SequenceEcritureComptable("VE", 2021, 1));
            sequenceEcritureComptables.add(new SequenceEcritureComptable("BQ", 2021, 1));
            return sequenceEcritureComptables;
        }
    }
}


