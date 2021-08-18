package com.dummy.myerp.testbusiness.business;

import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ComptabiliteManagerImplIntegrationTest extends BusinessTestCase{

    private Logger logger = LogManager.getLogger(ComptabiliteManagerImplIntegrationTest.class);

    private ComptabiliteManager comptabiliteManager;

    @BeforeEach
    public void initComptabiliteManager() {
        comptabiliteManager = getBusinessProxy().getComptabiliteManager();

    }

    @Test
    public void getListCompteComptable() {
        List<CompteComptable> compteComptables = comptabiliteManager.getListCompteComptable();
        for (CompteComptable compte:compteComptables) {
         logger.info(compte.toString());
        }
    }

    @Test
    public void getListJournalComptable() {
        List<JournalComptable> journalComptables = comptabiliteManager.getListJournalComptable();
        logger.info(journalComptables.size());
        assertThat(journalComptables.size()).isGreaterThanOrEqualTo(4);
    }
}
