package com.dummy.myerp.model.bean.comptabilite;


import javax.validation.constraints.Size;

/**
 * Bean représentant une séquence pour les références d'écriture comptable
 */
public class SequenceEcritureComptable {

    // ==================== Attributs ====================
    //IMPLEMENTED : code_journal attribute
    private String journalCode;
    /** L'année */
    private Integer annee;
    /** La dernière valeur utilisée */
    private Integer derniereValeur;

// ==================== Constructeurs ====================
    /**
     * Constructeur
     */
    public SequenceEcritureComptable() {
    }

    /**
     * Constructeur
     *
     * @param pAnnee -
     * @param pDerniereValeur -
     */
    public SequenceEcritureComptable(Integer pAnnee, Integer pDerniereValeur) {
        annee = pAnnee;
        derniereValeur = pDerniereValeur;
    }

    public SequenceEcritureComptable(String journalCode, Integer annee, Integer derniereValeur) {
        this.journalCode = journalCode;
        this.annee = annee;
        this.derniereValeur = derniereValeur;
    }

    // ==================== Getters/Setters ====================
    public Integer getAnnee() {
        return annee;
    }
    public void setAnnee(Integer pAnnee) {
        annee = pAnnee;
    }
    public Integer getDerniereValeur() {
        return derniereValeur;
    }
    public void setDerniereValeur(Integer pDerniereValeur) {
        derniereValeur = pDerniereValeur;
    }

    public String getJournalCode() {
        return journalCode;
    }

    public void setJournalCode(String journalCode) {
        this.journalCode = journalCode;
    }

    @Override
    public String toString() {
        return "SequenceEcritureComptable{" +
                "journalCode='" + journalCode + '\'' +
                ", annee=" + annee +
                ", derniereValeur=" + derniereValeur +
                '}';
    }
}
