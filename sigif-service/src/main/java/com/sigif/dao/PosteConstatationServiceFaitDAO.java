package com.sigif.dao;

import java.util.List;
import java.util.Set;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.PosteConstatationServiceFait;

/**
 * Classe d'accès aux données des postes des constatations de service fait.
 * 
 * @author Mickael Beaupoil
 *
 */
public interface PosteConstatationServiceFaitDAO extends AbstractDAO<PosteConstatationServiceFait> {
    /**
     * Compte le nombre de postes de CSF liés à un poste de CA.
     * 
     * @param idPosteCa
     *            id du poste de CA
     * @return le nombre de postes de CSF liés au poste de CA
     * @throws TechnicalException
     *             Si l'accès à la BD échoue
     */
    int getNbPostesCsfLinkedToPosteCa(int idPosteCa) throws TechnicalException;

    /**
     * Récupère les postes CSF liés à un poste CA.
     * 
     * @param numCa
     *            Numéro de CA (idSap)
     * @param numPoste
     *            numéro de poste (idSAP)
     * @param numTeledossier
     *            Numéro de télédossier des postes à exclure des résultats
     * @return Une liste de PosteConstatationServiceFait
     * @throws TechnicalException
     *             si l'accès BD échoue
     * @author Mamadou Ndir
     * 
     */
    List<PosteConstatationServiceFait> getPostesCsfByPosteCa(String numCa, String numPoste, String numTeledossier)
            throws TechnicalException;

    /**
     * Récupère un poste CSF en fonction du nuléro de dossier de la CSF liée et
     * de l'idCSFPoste du posteCSF.
     * 
     * @param numDossierCSF
     *            de la CSF liée
     * @param idCSFPoste
     *            du posteCSF
     * @return Un PosteConstatationServiceFait
     * @throws TechnicalException
     *             si l'accès BD échoue
     * @author Meissa Mame
     * @since 23/06/2017
     */
    PosteConstatationServiceFait getPosteCsfByCsfAndIdCsfPoste(String numDossierCSF, String idCSFPoste)
            throws TechnicalException;

    /**
     * Récupère les postes d'une CSF.
     * 
     * @author Mamadou Ndir
     * @param numCsf
     *            Numéro CSF
     * @return Une liste de PosteConstatationServiceFait
     * @throws TechnicalException
     *             si l'accès BD échoue
     * @throws TechnicalException
     *             si l'accès aux données de la BD échoue
     */
    List<PosteConstatationServiceFait> getItemsCSF(String numCsf) throws TechnicalException;

    /**
     * Récupère le poste d'une CSF.
     * 
     * @author Mamadou Ndir
     * @param numCsf
     *            Numéro CSF
     * @param numPosteCsf
     *            numéro de poste
     * @return Une liste de PosteConstatationServiceFait
     * @throws TechnicalException
     *             si l'accès aux données de la BD échoue
     */
    PosteConstatationServiceFait getPosteCSFInfo(String numCsf, String numPosteCsf) throws TechnicalException;

    /* alpha ajout*/
    
    void initialise(Set<PosteConstatationServiceFait> postesASupprimer);
}
