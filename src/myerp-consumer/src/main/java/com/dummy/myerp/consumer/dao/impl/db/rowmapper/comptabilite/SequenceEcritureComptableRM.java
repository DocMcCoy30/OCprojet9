package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

//IMPLEMENTED : sequence-ecriture-comptable RowMapper
public class SequenceEcritureComptableRM implements RowMapper<SequenceEcritureComptable> {
    @Override
    public SequenceEcritureComptable mapRow(ResultSet resultSet, int pRowNum) throws SQLException {
        SequenceEcritureComptable sec = new SequenceEcritureComptable();
        sec.setJournalCode(resultSet.getString("journal_code"));
        sec.setAnnee(resultSet.getInt("annee"));
        sec.setDerniereValeur(resultSet.getInt("derniere_valeur"));
        return sec;
    }
}
