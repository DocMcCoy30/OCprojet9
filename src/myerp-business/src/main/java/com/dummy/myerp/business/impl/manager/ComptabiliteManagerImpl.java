package com.dummy.myerp.business.impl.manager;

import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.TransactionStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.*;

import static org.apache.logging.log4j.LogManager.getLogger;


/**
 * Comptabilite manager implementation.
 */
public class ComptabiliteManagerImpl extends AbstractBusinessManager implements ComptabiliteManager {

    // ==================== Attributs ====================

    private static final Logger logger = getLogger(ComptabiliteManagerImpl.class);

    // ==================== Constructeurs ====================

    /**
     * Instantiates a new Comptabilite manager.
     */
    public ComptabiliteManagerImpl() {
    }


    // ==================== Getters/Setters ====================
    @Override
    public List<CompteComptable> getListCompteComptable() {
        return getDaoProxy().getComptabiliteDao().getListCompteComptable();
    }


    @Override
    public List<JournalComptable> getListJournalComptable() {
        return getDaoProxy().getComptabiliteDao().getListJournalComptable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EcritureComptable> getListEcritureComptable() {
        return getDaoProxy().getComptabiliteDao().getListEcritureComptable();
    }

    /**
     * {@inheritDoc}
     */
    // TODO à tester
    @Override
    public synchronized void addReference(EcritureComptable pEcritureComptable) throws FunctionalException {
        // TODO à implémenter : DONE
        //IMPLEMENTED : addReferenceMethod
//        try {
//            this.checkEcritureComptable(pEcritureComptable);
//        } catch (FunctionalException e) {
//            e.printStackTrace();
//            logger.info(e.getMessage());
//        }
        SequenceEcritureComptable sequence;
        int derniereValeur = 0;
        //1- Récupérer la date de l'EC (année)
        int annee = formatDateToYear(pEcritureComptable);
        //2- SELECT sur la table sequence (à implémenter dans dao)
        sequence = getBusinessProxy().getComptabiliteManager().getSequenceEcritureComptableByYearAndJournalCode(pEcritureComptable.getJournal().getCode(), annee);
        //3- Vérifier la présence d'un journal correspondant à l'EC pour l'année
        //  - si oui = dernière valeur =+1
        if (sequence.getDerniereValeur() != null) {
            derniereValeur = sequence.getDerniereValeur() + 1;
            sequence.setDerniereValeur(derniereValeur);
            //5- UPDATE sur la table sequence
            getBusinessProxy().getComptabiliteManager().updateSequenceEcritureComptable(sequence);
            //  - si non = derniere valeur =1
        } else {
            sequence.setJournalCode(pEcritureComptable.getJournal().getCode());
            sequence.setAnnee(annee);
            sequence.setDerniereValeur(1);
            //5- INSERT sur la table sequence
            getBusinessProxy().getComptabiliteManager().insertSequenceEcritureComptable(sequence);
        }
        //4- Mettre à jour la référence de l'EC
        String reference = sequence.getJournalCode() + "-" + annee + "/" + formatValeur(sequence.getDerniereValeur(), 5);
        pEcritureComptable.setReference(reference);
    }

    /**
     * {@inheritDoc}
     */
    // TODO à tester
    @Override
    public void checkEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        this.checkEcritureComptableUnit(pEcritureComptable);
        this.checkEcritureComptableContext(pEcritureComptable);
    }


    /**
     * Vérifie que l'Ecriture comptable respecte les règles de gestion unitaires,
     * c'est à dire indépendemment du contexte (unicité de la référence, exercie comptable non cloturé...)
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
     */
    // TODO tests à compléter : DONE
    protected boolean checkEcritureComptableUnit(EcritureComptable pEcritureComptable) throws FunctionalException {

        try {
            // ===== Vérification des contraintes unitaires sur les attributs de l'écriture
            checkEcritureConstraintViolation(pEcritureComptable);

            // ===== RG_Compta_2 : Pour qu'une écriture comptable soit valide, elle doit être équilibrée
            checkIfEcritureIsEquilibreeRG2(pEcritureComptable);

            // ===== RG_Compta_3 : une écriture comptable doit avoir au moins 2 lignes d'écriture (1 au débit, 1 au crédit)
            checkEcritureNumberOfLineRG3(pEcritureComptable);

            // ===== RG_Compta_5 : Le formatage de la référence
            checkEcritureFormatAndContainRG5(pEcritureComptable);

        } catch (Exception e) {
            throw new FunctionalException("checkEcritureComptableUnit is KO");
        }
        return true;
    }


    //Refactor
    // ==================== Refactoring checkEcritureComptableUnit for testing  ====================
    public boolean checkEcritureConstraintViolation(EcritureComptable pEcritureComptable) throws FunctionalException {
        Set<ConstraintViolation<EcritureComptable>> vViolations = getConstraintValidator().validate(pEcritureComptable);
        if (!vViolations.isEmpty()) {
            logger.info("checkEcritureConstraintViolation is KO");
            throw new FunctionalException("ConstraintViolation : L'écriture comptable ne respecte pas les règles de gestion.",
                    new ConstraintViolationException(
                            "L'écriture comptable ne respecte pas les contraintes de validation",
                            vViolations));
        }
        logger.info("checkEcritureConstraintViolation is OK");
        return true;
    }

    public boolean checkIfEcritureIsEquilibreeRG2(EcritureComptable pEcritureComptable) throws FunctionalException {
        if (!pEcritureComptable.isEquilibree()) {
            logger.info("checkIfEcritureIsEquilibreeRG2 => KO");
            throw new FunctionalException("L'écriture comptable n'est pas équilibrée.");
        }
        logger.info("checkIfEcritureIsEquilibreeRG2 => OK");

        return true;
    }

    public boolean checkEcritureNumberOfLineRG3(EcritureComptable pEcritureComptable) throws FunctionalException {
        int vNbrCredit = 0;
        int vNbrDebit = 0;
        for (LigneEcritureComptable vLigneEcritureComptable : pEcritureComptable.getListLigneEcriture()) {
            if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getCredit(),
                    BigDecimal.ZERO)) != 0) {
                vNbrCredit++;
            }
            if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getDebit(),
                    BigDecimal.ZERO)) != 0) {
                vNbrDebit++;
            }
        }
        // On test le nombre de lignes car si l'écriture à une seule ligne avec un montant au débit et un montant au crédit ce n'est pas valable
        if (pEcritureComptable.getListLigneEcriture().size() < 2
                || vNbrCredit < 1
                || vNbrDebit < 1) {
            logger.info("checkEcritureNumberOfLineRG3 => KO");
            throw new FunctionalException(
                    "L'écriture comptable doit avoir au moins deux lignes : une ligne au débit et une ligne au crédit.");
        }
        logger.info("checkEcritureNumberOfLineRG3 => OK");
        return true;
    }

    // TODO ===== RG_Compta_5 : DONE : Format et contenu de la référence - // vérifier que l'année dans la référence correspond bien à la date de l'écriture, idem pour le code journal...
    // IMPLEMENTED RG_Compta_5
    public boolean checkEcritureFormatAndContainRG5(EcritureComptable pEcritureComptable) throws FunctionalException {
        String refCodeJournal;
        String refDate;
        String reference = pEcritureComptable.getReference();
        if (reference != null) {
            refCodeJournal = referenceTokenizer(reference, 0);
            refDate = referenceTokenizer(reference, 1);
            //Vérification du code journal
            if (!Objects.equals(refCodeJournal, pEcritureComptable.getJournal().getCode())) {
                logger.info("checkEcritureFormatAndContainRG5 => KO");
                throw new FunctionalException("Le code journal dans la référence ne correspond pas au code de l'écriture");
            }
            //Verification de l'année
            String ecDate = Integer.toString(formatDateToYear(pEcritureComptable));
            if (!Objects.equals(refDate, ecDate)) {
                logger.info("checkEcritureFormatAndContainRG5 => KO");
                throw new FunctionalException("L'année dans la référence ne correspond pas à la date de l'écriture");
            }
        } else {
            logger.info("checkEcritureFormatAndContainRG5 => KO");
            throw new FunctionalException("Aucune référence pour cette écriture");
        }
        logger.info("checkEcritureFormatAndContainRG5 => OK");
        return true;
    }


    /**
     * Vérifie que l'Ecriture comptable respecte les règles de gestion liées au contexte
     * (unicité de la référence, année comptable non cloturé...)
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
     */
    protected boolean checkEcritureComptableContext(EcritureComptable pEcritureComptable) throws FunctionalException {
        // ===== RG_Compta_6 : La référence d'une écriture comptable doit être unique
        if (StringUtils.isNoneEmpty(pEcritureComptable.getReference())) {
            try {
                // Recherche d'une écriture ayant la même référence
                EcritureComptable vECRef = getDaoProxy().getComptabiliteDao().getEcritureComptableByRef(
                        pEcritureComptable.getReference());

                // Si l'écriture à vérifier est une nouvelle écriture (id == null),
                // ou si elle ne correspond pas à l'écriture trouvée (id != idECRef),
                // c'est qu'il y a déjà une autre écriture avec la même référence
                if (pEcritureComptable.getId() == null
                        || !pEcritureComptable.getId().equals(vECRef.getId())) {
                    logger.info("checkEcritureComptableContext => KO");
                    throw new FunctionalException("Une autre écriture comptable existe déjà avec la même référence.");
                }
            } catch (NotFoundException vEx) {
                // Dans ce cas, c'est bon, ça veut dire qu'on n'a aucune autre écriture avec la même référence.
            }
        }
        logger.info("checkEcritureComptableContext => OK");
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        this.checkEcritureComptable(pEcritureComptable);
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().insertEcritureComptable(pEcritureComptable);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().updateEcritureComptable(pEcritureComptable);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteEcritureComptable(Integer pId) {
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().deleteEcritureComptable(pId);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

    //IMPLEMENTED : méthodes SequenceEcritureComptable

    /**
     * {@inheritDoc}
     */
    @Override
    public SequenceEcritureComptable getSequenceEcritureComptableByYearAndJournalCode(String code, int annee) {
        SequenceEcritureComptable sequenceEcritureComptable = null;
        sequenceEcritureComptable = getDaoProxy().getComptabiliteDao().getSequenceEcritureComptableByYearAndJournalCode(code, annee);
        return sequenceEcritureComptable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateSequenceEcritureComptable(SequenceEcritureComptable sequence) {
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().updateSequenceEcritureComptable(sequence);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertSequenceEcritureComptable(SequenceEcritureComptable sequence) {
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().insertSequenceEcritureComptable(sequence);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

    // IMPLEMENTED : Méthodes Utilitaires
    //---------- Méthodes Utilitaires --------------

    public int formatDateToYear(EcritureComptable pEcritureComptable) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(pEcritureComptable.getDate());
        int annee = calendar.get(Calendar.YEAR);
        return annee;
    }

    public String formatValeur(int valeur, int nbDeDigits) throws FunctionalException {
        String formatedString;
        try {
            formatedString = String.format("%0" + nbDeDigits + "d", valeur);
        } catch (IllegalFormatFlagsException e) {
            throw new FunctionalException("Le nombre de digits ne peut pas etre négatif.");
        }
        return formatedString;
    }

    public String referenceTokenizer(String myString, int index) throws FunctionalException {
        String result;
        String str = myString.replace("/", "-");
        String[] tokens = str.split("-");
        try {
            result = tokens[index];
        } catch (IndexOutOfBoundsException e) {
            throw new FunctionalException("Index erronné.");
        }
        return result;
    }
}
