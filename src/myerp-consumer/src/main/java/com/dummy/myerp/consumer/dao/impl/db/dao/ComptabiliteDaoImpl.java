package com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.*;
import com.dummy.myerp.consumer.db.AbstractDbConsumer;
import com.dummy.myerp.consumer.db.DataSourcesEnum;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Types;
import java.util.List;


/**
 * Implémentation de l'interface {@link ComptabiliteDao}
 */
public class ComptabiliteDaoImpl extends AbstractDbConsumer implements ComptabiliteDao {


    private final Logger logger = LogManager.getLogger(ComptabiliteDaoImpl.class);

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
        logger.info("Into getListJournalComptable from ComptabiliteDaoImpl.class");
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
        logger.info("Into getListEcritureComptable from ComptabiliteDaoImpl.class");
//        JdbcTemplate vJdbcTemplate = getJdbcTemplate();
//        EcritureComptableRM vRM = new EcritureComptableRM();
//        List<EcritureComptable> vList = vJdbcTemplate.query(SQLgetListEcritureComptable, vRM);
        List<EcritureComptable> vList = getListEcritureComptableQueryResult();
        return vList;
    }

    /**
     * Refactor getListEcritureComptable() :
     *
     * @return List<EcritureComptable>
     */
    protected List<EcritureComptable> getListEcritureComptableQueryResult() {
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
        logger.info("Into getEcritureComptable from ComptabiliteDaoImpl.class");
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
     * Refactor getEcritureComptable() :
     *
     * @return EcritureComptable
     */
    protected EcritureComptable getEcritureComptableQueryResult(MapSqlParameterSource vSqlParams, EcritureComptableRM vRM) {
        return getNamedParameterJdbcTemplate().queryForObject(SQLgetEcritureComptable, vSqlParams, vRM);
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
        logger.info("Into getEcritureComptableByRef from ComptabiliteDaoImpl.class");
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("reference", pReference);
        EcritureComptableRM vRM = new EcritureComptableRM();
        EcritureComptable vBean;
        try {
            vBean = getEcritureComptableByRefQueryResult(vSqlParams, vRM);
        } catch (EmptyResultDataAccessException vEx) {
            throw new NotFoundException("EcritureComptable non trouvée : reference=" + pReference);
        }
        return vBean;
    }

    /**
     * Refactor getEcritureComptableByRef() :
     *
     * @return EcritureComptable
     */
    protected EcritureComptable getEcritureComptableByRefQueryResult(MapSqlParameterSource vSqlParams, EcritureComptableRM vRM) {
        return getNamedParameterJdbcTemplate().queryForObject(SQLgetEcritureComptableByRef, vSqlParams, vRM);
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
    public int insertEcritureComptable(EcritureComptable pEcritureComptable) {
        // ===== Ecriture Comptable
        NamedParameterJdbcTemplate vJdbcTemplate = getNamedParameterJdbcTemplate();
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("journal_code", pEcritureComptable.getJournal().getCode());
        vSqlParams.addValue("reference", pEcritureComptable.getReference());
        vSqlParams.addValue("date", pEcritureComptable.getDate(), Types.DATE);
        vSqlParams.addValue("libelle", pEcritureComptable.getLibelle());

        insertEcritureComptableQuery(vSqlParams, SQLinsertEcritureComptable);

        // ----- Récupération de l'id
        Integer vId = this.queryGetSequenceValuePostgreSQL(DataSourcesEnum.MYERP, "myerp.ecriture_comptable_id_seq",
                Integer.class);
        pEcritureComptable.setId(vId);

        // ===== Liste des lignes d'écriture
        this.insertListLigneEcritureComptable(pEcritureComptable);

        return pEcritureComptable.getId();
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
    public int updateEcritureComptable(EcritureComptable pEcritureComptable) {
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

        return pEcritureComptable.getId();
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
    public int deleteEcritureComptable(Integer pId) {
        // ===== Suppression des lignes d'écriture
        this.deleteListLigneEcritureComptable(pId);

        // ===== Suppression de l'écriture
        NamedParameterJdbcTemplate vJdbcTemplate = getNamedParameterJdbcTemplate();
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("id", pId);
        vJdbcTemplate.update(SQLdeleteEcritureComptable, vSqlParams);
        return pId;
    }

    /**
     * Refactor insertEcritureComptable() :
     */
    protected void insertEcritureComptableQuery(MapSqlParameterSource vSqlParams, String sqLinsertEcritureComptable) {
        getNamedParameterJdbcTemplate().update(sqLinsertEcritureComptable, vSqlParams);
    }

    // ==================== LigneEcritureComptable - LOAD ====================

    /**
     * SQLloadListLigneEcriture
     */
    private static String SQLloadListLigneEcriture;

    public void setSQLloadListLigneEcriture(String pSQLloadListLigneEcriture) {
        SQLloadListLigneEcriture = pSQLloadListLigneEcriture;
    }

    @Override
    public void loadListLigneEcriture(EcritureComptable pEcritureComptable) {
        logger.info("Into loadListLigneEcriture from ComptabiliteDaoImpl.class");
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("ecriture_id", pEcritureComptable.getId());
        List<LigneEcritureComptable> vList = getLigneEcritureComptablesQueryResult(vSqlParams, new LigneEcritureComptableRM());
        pEcritureComptable.getListLigneEcriture().clear();
        pEcritureComptable.getListLigneEcriture().addAll(vList);
//        logger.info("Nombre de ligne = " + pEcritureComptable.getListLigneEcriture().size());
//        logger.info("Ligne.toString = " + pEcritureComptable.getListLigneEcriture().toString());
    }

    /**
     * Refactor loadListLigneEcriture() :
     *
     * @return List<LigneEcritureComptable>
     */
    protected List<LigneEcritureComptable> getLigneEcritureComptablesQueryResult(MapSqlParameterSource vSqlParams, LigneEcritureComptableRM vRM) {
        return getNamedParameterJdbcTemplate().query(SQLloadListLigneEcriture, vSqlParams, vRM);
    }

    // ==================== ListLigneEcritureComptable - INSERT ====================
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

    // ==================== ListLigneEcritureComptable - DELETE ====================
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

    // ==================== lit<SequenceEcritureComptable> - GET ====================

    /**
     * SQLgetListSequenceEcritureComptable
     */
    private static String SQLgetListSequenceEcritureComptable;

    public void setSQLgetListSequenceEcritureComptable(String pSQLgetListSequenceEcritureComptable) {
        SQLgetListSequenceEcritureComptable = pSQLgetListSequenceEcritureComptable;
    }

    @Override
    public List<SequenceEcritureComptable> getListSequenceEcritureComptable() {
        logger.info("Into getListSequenceEcritureComptable from ComptabiliteDaoImpl.class");
        List<SequenceEcritureComptable> sequences = getListSequenceEcritureComptablesQueryResult();
        return sequences;
    }

    /**
     * Refactor getListSequenceEcritureComptable() :
     *
     *
     */
    protected List<SequenceEcritureComptable> getListSequenceEcritureComptablesQueryResult() {
        JdbcTemplate vJdbcTemplate = getJdbcTemplate();
        RowMapper<SequenceEcritureComptable> vRM = new SequenceEcritureComptableRM();
        List<SequenceEcritureComptable> sequences = vJdbcTemplate.query(SQLgetListSequenceEcritureComptable, vRM);
        return sequences;
    }

    // ==================== SequenceEcritureComptable - GET BY ====================
    /**
     * SQLgetSequenceEcritureComptableByYearAndJournalCode
     */
    private static String SQLgetSequenceEcritureComptableByYearAndJournalCode;

    public void setSQLgetSequenceEcritureComptableByYearAndJournalCode(String pSQLgetSequenceEcritureComptableByYearAndJournalCode) {
        SQLgetSequenceEcritureComptableByYearAndJournalCode = pSQLgetSequenceEcritureComptableByYearAndJournalCode;
    }

    @Override
    public SequenceEcritureComptable getSequenceEcritureComptableByYearAndJournalCode(String code, int year) {
        logger.info("Into getSequenceEcritureComptableByYearAndJournalCode from ComptabiliteDaoImpl.class");
        SequenceEcritureComptable sequenceEcritureComptable;
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("journal_code", code);
        vSqlParams.addValue("annee", year);
        try {
            sequenceEcritureComptable = getSequenceEcritureComptableQueryResult(vSqlParams, new SequenceEcritureComptableRM());
        } catch (EmptyResultDataAccessException e) {
            sequenceEcritureComptable = new SequenceEcritureComptable();
        }
        return sequenceEcritureComptable;
    }

    /**
     * Refactor getSequenceEcritureComptableByYearAndJournalCode() :
     *
     * @return SequenceEcritureComptable
     */
    protected SequenceEcritureComptable getSequenceEcritureComptableQueryResult(MapSqlParameterSource vSqlParams, SequenceEcritureComptableRM vRM) {
        return getNamedParameterJdbcTemplate().queryForObject(SQLgetSequenceEcritureComptableByYearAndJournalCode, vSqlParams, vRM);
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
    public int insertSequenceEcritureComptable(SequenceEcritureComptable sequence) {
        NamedParameterJdbcTemplate vJdbcTemplate = getNamedParameterJdbcTemplate();
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("derniere_valeur", sequence.getDerniereValeur());
        vSqlParams.addValue("journal_code", sequence.getJournalCode());
        vSqlParams.addValue("annee", sequence.getAnnee());
        vJdbcTemplate.update(SQLinsertSequenceEcritureComptable, vSqlParams);
        return sequence.getDerniereValeur();
    }

    //***** REFACTOR *****
    protected JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
    }

    protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
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
    public int updateSequenceEcritureComptable(SequenceEcritureComptable sequence) {
        NamedParameterJdbcTemplate vJdbcTemplate = getNamedParameterJdbcTemplate();
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("derniere_valeur", sequence.getDerniereValeur());
        vSqlParams.addValue("journal_code", sequence.getJournalCode());
        vSqlParams.addValue("annee", sequence.getAnnee());
        vJdbcTemplate.update(SQLupdateSequenceEcritureComptable, vSqlParams);
        return sequence.getDerniereValeur();
    }

}
