package com.dummy.myerp.consumer.dao.impl.db.dao;

import java.sql.Types;
import java.util.List;

import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.*;
import com.dummy.myerp.model.bean.comptabilite.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.db.AbstractDbConsumer;
import com.dummy.myerp.consumer.db.DataSourcesEnum;
import com.dummy.myerp.technical.exception.NotFoundException;


/**
 * Implémentation de l'interface {@link ComptabiliteDao}
 */
public class ComptabiliteDaoImpl extends AbstractDbConsumer implements ComptabiliteDao {


    private Logger logger = LogManager.getLogger(ComptabiliteDaoImpl.class);

    // ==================== Constructeurs ====================
    /**
     * Instance unique de la classe (design pattern Singleton)
     */
    private static final ComptabiliteDaoImpl INSTANCE = new ComptabiliteDaoImpl();

    /**
     * Renvoie l'instance unique de la classe (design pattern Singleton).
     *
     * @return {@link ComptabiliteDaoImpl}
     */
    public static ComptabiliteDaoImpl getInstance() {
        return ComptabiliteDaoImpl.INSTANCE;
    }

    /**
     * Constructeur.
     */
    protected ComptabiliteDaoImpl() {
        super();
    }


    // ==================== CompteComptable - GET ====================
    private static String SQLgetListCompteComptable;

    public void setSQLgetListCompteComptable(String pSQLgetListCompteComptable) {
        SQLgetListCompteComptable = pSQLgetListCompteComptable;
    }

    @Override
    public List<CompteComptable> getListCompteComptable() {
        logger.info("Into getListCompteComptable from ComptabiliteDaoImpl.class");
        List<CompteComptable> vList = getListCompteComptableQueryResult();
        return vList;
//        JdbcTemplate vJdbcTemplate = getJdbcTemplate();
//        CompteComptableRM vRM = new CompteComptableRM();
//        List<CompteComptable> vList = vJdbcTemplate.query(SQLgetListCompteComptable, vRM);
//        return vList;
    }

    /**
     * Refactor getListCompteComptable() :
     *
     * @return List<CompteComptable>
     */
    protected List<CompteComptable> getListCompteComptableQueryResult() {
        return getJdbcTemplate().query(SQLgetListCompteComptable, new CompteComptableRM());
    }


    // ==================== JournalComptable - GET ====================
    private static String SQLgetListJournalComptable;

    public void setSQLgetListJournalComptable(String pSQLgetListJournalComptable) {
        SQLgetListJournalComptable = pSQLgetListJournalComptable;
    }

    @Override
    public List<JournalComptable> getListJournalComptable() {
//        JdbcTemplate vJdbcTemplate = getJdbcTemplate();
//        JournalComptableRM vRM = new JournalComptableRM();
        List<JournalComptable> vList = getListJournalComptableQueryResult();
        return vList;
    }

    /**
     * Refactor getListCompteComptable() :
     *
     * @return List<CompteComptable>
     */
    protected List<JournalComptable> getListJournalComptableQueryResult() {
        return getJdbcTemplate().query(SQLgetListJournalComptable, new JournalComptableRM());
    }

    // ==================== List<EcritureComptable> - GET ====================
    private static String SQLgetListEcritureComptable;

    public void setSQLgetListEcritureComptable(String pSQLgetListEcritureComptable) {
        SQLgetListEcritureComptable = pSQLgetListEcritureComptable;
    }

    @Override
    public List<EcritureComptable> getListEcritureComptable() {
//        JdbcTemplate vJdbcTemplate = getJdbcTemplate();
//        EcritureComptableRM vRM = new EcritureComptableRM();
//        List<EcritureComptable> vList = vJdbcTemplate.query(SQLgetListEcritureComptable, vRM);
        List<EcritureComptable> vList = getListeEcritureComptableQueryResult();
        return vList;
    }

    /**
     * Refactor getListEcritureComptable() :
     *
     * @return List<EcritureComptable>
     */
    protected List<EcritureComptable> getListeEcritureComptableQueryResult() {
        return getJdbcTemplate().query(SQLgetListEcritureComptable, new EcritureComptableRM());
    }

    // ==================== EcritureComptable - GET BY ====================

    /**
     * SQLgetEcritureComptable
     */
    private static String SQLgetEcritureComptable;

    public void setSQLgetEcritureComptable(String pSQLgetEcritureComptable) {
        SQLgetEcritureComptable = pSQLgetEcritureComptable;
    }

    @Override
    public EcritureComptable getEcritureComptable(Integer pId) throws NotFoundException {
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("id", pId);
        EcritureComptableRM vRM = new EcritureComptableRM();
        EcritureComptable vBean;
        try {
            vBean = getEcritureComptableQueryResult(vSqlParams, vRM);
        } catch (EmptyResultDataAccessException vEx) {
            throw new NotFoundException("EcritureComptable non trouvée : id=" + pId);
        }
        return vBean;
    }

    /**
     * SQLgetEcritureComptableByRef
     */
    private static String SQLgetEcritureComptableByRef;

    public void setSQLgetEcritureComptableByRef(String pSQLgetEcritureComptableByRef) {
        SQLgetEcritureComptableByRef = pSQLgetEcritureComptableByRef;
    }

    @Override
    public EcritureComptable getEcritureComptableByRef(String pReference) throws NotFoundException {
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("reference", pReference);
        EcritureComptableRM vRM = new EcritureComptableRM();
        EcritureComptable vBean;
        try {
            vBean = getEcritureComptableQueryResult(vSqlParams, vRM);
        } catch (EmptyResultDataAccessException vEx) {
            throw new NotFoundException("EcritureComptable non trouvée : reference=" + pReference);
        }
        return vBean;
    }

    /**
     * Refactor getEcritureComptableBy() :
     *
     * @return EcritureComptable
     */
    protected EcritureComptable getEcritureComptableQueryResult(MapSqlParameterSource vSqlParams, EcritureComptableRM vRM) {
        return getNamedParameterJdbcTemplate().queryForObject(SQLgetEcritureComptable, vSqlParams, vRM);
    }


    /**
     * SQLloadListLigneEcriture
     */
    private static String SQLloadListLigneEcriture;

    public void setSQLloadListLigneEcriture(String pSQLloadListLigneEcriture) {
        SQLloadListLigneEcriture = pSQLloadListLigneEcriture;
    }

    @Override
    public void loadListLigneEcriture(EcritureComptable pEcritureComptable) {
        NamedParameterJdbcTemplate vJdbcTemplate = getNamedParameterJdbcTemplate();
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("ecriture_id", pEcritureComptable.getId());
        LigneEcritureComptableRM vRM = new LigneEcritureComptableRM();
        List<LigneEcritureComptable> vList = vJdbcTemplate.query(SQLloadListLigneEcriture, vSqlParams, vRM);
        pEcritureComptable.getListLigneEcriture().clear();
        pEcritureComptable.getListLigneEcriture().addAll(vList);
    }


    // ==================== EcritureComptable - INSERT ====================

    /**
     * SQLinsertEcritureComptable
     */
    private static String SQLinsertEcritureComptable;

    public void setSQLinsertEcritureComptable(String pSQLinsertEcritureComptable) {
        SQLinsertEcritureComptable = pSQLinsertEcritureComptable;
    }

    @Override
    public void insertEcritureComptable(EcritureComptable pEcritureComptable) {
        // ===== Ecriture Comptable
        NamedParameterJdbcTemplate vJdbcTemplate = getNamedParameterJdbcTemplate();
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("journal_code", pEcritureComptable.getJournal().getCode());
        vSqlParams.addValue("reference", pEcritureComptable.getReference());
        vSqlParams.addValue("date", pEcritureComptable.getDate(), Types.DATE);
        vSqlParams.addValue("libelle", pEcritureComptable.getLibelle());

        vJdbcTemplate.update(SQLinsertEcritureComptable, vSqlParams);

        // ----- Récupération de l'id
        Integer vId = this.queryGetSequenceValuePostgreSQL(DataSourcesEnum.MYERP, "myerp.ecriture_comptable_id_seq",
                Integer.class);
        pEcritureComptable.setId(vId);

        // ===== Liste des lignes d'écriture
        this.insertListLigneEcritureComptable(pEcritureComptable);
    }

    /**
     * SQLinsertListLigneEcritureComptable
     */
    private static String SQLinsertListLigneEcritureComptable;

    public void setSQLinsertListLigneEcritureComptable(String pSQLinsertListLigneEcritureComptable) {
        SQLinsertListLigneEcritureComptable = pSQLinsertListLigneEcritureComptable;
    }

    /**
     * Insert les lignes d'écriture de l'écriture comptable
     *
     * @param pEcritureComptable l'écriture comptable
     */
    protected void insertListLigneEcritureComptable(EcritureComptable pEcritureComptable) {
        NamedParameterJdbcTemplate vJdbcTemplate = getNamedParameterJdbcTemplate();
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("ecriture_id", pEcritureComptable.getId());

        int vLigneId = 0;
        for (LigneEcritureComptable vLigne : pEcritureComptable.getListLigneEcriture()) {
            vLigneId++;
            vSqlParams.addValue("ligne_id", vLigneId);
            vSqlParams.addValue("compte_comptable_numero", vLigne.getCompteComptable().getNumero());
            vSqlParams.addValue("libelle", vLigne.getLibelle());
            vSqlParams.addValue("debit", vLigne.getDebit());

            vSqlParams.addValue("credit", vLigne.getCredit());

            vJdbcTemplate.update(SQLinsertListLigneEcritureComptable, vSqlParams);
        }
    }


    // ==================== EcritureComptable - UPDATE ====================

    /**
     * SQLupdateEcritureComptable
     */
    private static String SQLupdateEcritureComptable;

    public void setSQLupdateEcritureComptable(String pSQLupdateEcritureComptable) {
        SQLupdateEcritureComptable = pSQLupdateEcritureComptable;
    }

    @Override
    public void updateEcritureComptable(EcritureComptable pEcritureComptable) {
        // ===== Ecriture Comptable
        NamedParameterJdbcTemplate vJdbcTemplate = getNamedParameterJdbcTemplate();
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("id", pEcritureComptable.getId());
        vSqlParams.addValue("journal_code", pEcritureComptable.getJournal().getCode());
        vSqlParams.addValue("reference", pEcritureComptable.getReference());
        vSqlParams.addValue("date", pEcritureComptable.getDate(), Types.DATE);
        vSqlParams.addValue("libelle", pEcritureComptable.getLibelle());

        vJdbcTemplate.update(SQLupdateEcritureComptable, vSqlParams);

        // ===== Liste des lignes d'écriture
        this.deleteListLigneEcritureComptable(pEcritureComptable.getId());
        this.insertListLigneEcritureComptable(pEcritureComptable);
    }


    // ==================== EcritureComptable - DELETE ====================

    /**
     * SQLdeleteEcritureComptable
     */
    private static String SQLdeleteEcritureComptable;

    public void setSQLdeleteEcritureComptable(String pSQLdeleteEcritureComptable) {
        SQLdeleteEcritureComptable = pSQLdeleteEcritureComptable;
    }

    @Override
    public void deleteEcritureComptable(Integer pId) {
        // ===== Suppression des lignes d'écriture
        this.deleteListLigneEcritureComptable(pId);

        // ===== Suppression de l'écriture
        NamedParameterJdbcTemplate vJdbcTemplate = getNamedParameterJdbcTemplate();
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("id", pId);
        vJdbcTemplate.update(SQLdeleteEcritureComptable, vSqlParams);
    }

    /**
     * SQLdeleteListLigneEcritureComptable
     */
    private static String SQLdeleteListLigneEcritureComptable;

    public void setSQLdeleteListLigneEcritureComptable(String pSQLdeleteListLigneEcritureComptable) {
        SQLdeleteListLigneEcritureComptable = pSQLdeleteListLigneEcritureComptable;
    }

    /**
     * Supprime les lignes d'écriture de l'écriture comptable d'id {@code pEcritureId}
     *
     * @param pEcritureId id de l'écriture comptable
     */
    protected void deleteListLigneEcritureComptable(Integer pEcritureId) {
        NamedParameterJdbcTemplate vJdbcTemplate = getNamedParameterJdbcTemplate();
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("ecriture_id", pEcritureId);
        vJdbcTemplate.update(SQLdeleteListLigneEcritureComptable, vSqlParams);
    }

    //IMPLEMENTED : méthodes table sequence-ecriture-comptable

    // ==================== SequenceEcritureComptable - GET ====================

    /**
     * SQLgetListSequenceEcritureComptable
     */
    private static String SQLgetListSequenceEcritureComptable;

    public void setSQLgetListSequenceEcritureComptable(String pSQLgetListSequenceEcritureComptable) {
        SQLgetListSequenceEcritureComptable = pSQLgetListSequenceEcritureComptable;
    }

    @Override
    public List<SequenceEcritureComptable> getListSequenceEcritureComptable() {
        JdbcTemplate vJdbcTemplate = getJdbcTemplate();
        RowMapper<SequenceEcritureComptable> vRM = new SequenceEcritureComptableRM();
        List<SequenceEcritureComptable> sequences = vJdbcTemplate.query(SQLgetListSequenceEcritureComptable, vRM);
        return sequences;
    }


    /**
     * SQLgetSequenceEcritureComptableByYearAndJournalCode
     */
    private static String SQLgetSequenceEcritureComptableByYearAndJournalCode;

    public void setSQLgetSequenceEcritureComptableByYearAndJournalCode(String pSQLgetSequenceEcritureComptableByYearAndJournalCode) {
        SQLgetSequenceEcritureComptableByYearAndJournalCode = pSQLgetSequenceEcritureComptableByYearAndJournalCode;
    }

    @Override
    public SequenceEcritureComptable getSequenceEcritureComptableByYearAndJournalCode(String code, int year) throws NotFoundException {
        SequenceEcritureComptable sec;
        NamedParameterJdbcTemplate vJdbcTemplate = getNamedParameterJdbcTemplate();
        RowMapper<SequenceEcritureComptable> vRM = new SequenceEcritureComptableRM();
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("journal_code", code);
        vSqlParams.addValue("annee", year);
        try {
            sec = vJdbcTemplate.queryForObject(SQLgetSequenceEcritureComptableByYearAndJournalCode, vSqlParams, vRM);
        } catch (Exception e) {
            throw new NotFoundException("Pas de sequence avec ces références");
        }
        return sec;
    }

    // ==================== SequenceEcritureComptable - UPDATE ====================

    /**
     * SQLupdateSequenceEcritureComptable
     */
    private static String SQLupdateSequenceEcritureComptable;

    public void setSQLupdateSequenceEcritureComptable(String pSQLupdateSequenceEcritureComptable) {
        SQLupdateSequenceEcritureComptable = pSQLupdateSequenceEcritureComptable;
    }

    @Override
    public void updateSequenceEcritureComptable(SequenceEcritureComptable sequence) {
        NamedParameterJdbcTemplate vJdbcTemplate = getNamedParameterJdbcTemplate();
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("derniere_valeur", sequence.getDerniereValeur());
        vSqlParams.addValue("journal_code", sequence.getJournalCode());
        vSqlParams.addValue("annee", sequence.getAnnee());
        vJdbcTemplate.update(SQLupdateSequenceEcritureComptable, vSqlParams);
    }

    // ==================== SequenceEcritureComptable - INSERT ====================
    
    /**
     * SQLinsertSequenceEcritureComptable
     */
    private static String SQLinsertSequenceEcritureComptable;

    public void setSQLinsertSequenceEcritureComptable(String pSQLinsertSequenceEcritureComptable) {
        SQLinsertSequenceEcritureComptable = pSQLinsertSequenceEcritureComptable;
    }

    @Override
    public void insertSequenceEcritureComptable(SequenceEcritureComptable sequence) {
        NamedParameterJdbcTemplate vJdbcTemplate = getNamedParameterJdbcTemplate();
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("derniere_valeur", sequence.getDerniereValeur());
        vSqlParams.addValue("journal_code", sequence.getJournalCode());
        vSqlParams.addValue("annee", sequence.getAnnee());
        vJdbcTemplate.update(SQLupdateSequenceEcritureComptable, vSqlParams);
    }

    //***** REFACTOR *****
    protected JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
    }

    protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
    }

}
