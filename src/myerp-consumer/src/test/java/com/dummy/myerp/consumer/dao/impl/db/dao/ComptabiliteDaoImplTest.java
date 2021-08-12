package com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.testconsumer.consumer.ConsumerTestCase;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.apache.logging.log4j.LogManager.getLogger;


/**
 * Classe de test Consumer de la classe {@link com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl}
 */
public class ComptabiliteDaoImplTest extends ConsumerTestCase {

    static Logger logger = getLogger(ComptabiliteDaoImplTest.class);
    private ComptabiliteDao dao = getDaoProxy().getComptabiliteDao();


    // ==================== Méthodes ====================

    @Test
    public void getListCompteComptable() {
        List<CompteComptable> vList = dao.getListCompteComptable();
        logger.info("logger is working");
        logger.log(vList.size() == 0 ? Level.WARN : Level.INFO, "Nbr de CompteComptable : " + vList.size());
//        Assume.assumeTrue(vList.size() > 0); // Le test n'est pas concluant car on n'est pas passé par les rowmapper
    }


    /**
     * Test : {@link ComptabiliteDaoImpl#getListJournalComptable()}
     */
    @Test
    public void getListJournalComptable() {
        List<JournalComptable> vList = dao.getListJournalComptable();
        logger.log(vList.size() == 0 ? Level.WARN : Level.INFO, "Nbr de JournalComptable : " + vList.size());
//        Assume.assumeTrue(vList.size() > 0); // Le test n'est pas concluant car on n'est pas passé par les rowmapper
    }


    /**
     * Test : {@link ComptabiliteDaoImpl#getListEcritureComptable()}
     */
    @Test
    public void getListEcritureComptable() {
        List<EcritureComptable> vList = dao.getListEcritureComptable();
        logger.log(vList.size() == 0 ? Level.WARN : Level.INFO, "Nbr de EcritureComptable : " + vList.size());
        for (EcritureComptable ec:vList) {
            logger.info(ec.toString());
        }
//        Assume.assumeTrue(vList.size() > 0); // Le test n'est pas concluant car on n'est pas passé par les rowmapper
    }


    /**
     * Test : {@link ComptabiliteDaoImpl#getEcritureComptable(Integer)}
     */
    @Test
    public void getEcritureComptable() {
        EcritureComptable vBean = null;
        try {
            vBean = dao.getEcritureComptable(-1);
//            Assume.assumeNotNull(vBean); // Le test n'est pas concluant car on n'est pas passé par les rowmapper
        } catch (NotFoundException vEx) {
//            Assume.assumeNoException("L'écriture comptable n'a pas été trouvée, le row mapper n'a pas été testé.", vEx);
        }
    }
}
