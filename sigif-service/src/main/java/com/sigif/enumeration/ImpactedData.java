package com.sigif.enumeration;

/**
 * 
 * @author Mamadou Ndir 7 juin 2017 16:44:50
 */
public enum ImpactedData {
    idTypeAchat,
    idProgramme,
    idAction,
    idActivite,
    reference,
    designation,
    idCategorieImmo,
    idUnite,
    idVilleLivraison,
    idRegionLivraison,
    idLieuStockage,
    idServiceBeneficiaire,
    idFonds,
    demandeur,
    idMinistere,
    idServiceDepensier,
    idCategorieAchat;
   
    public static String displayName(ImpactedData data) {
        if (data != null) {
            switch (data) {
            case idTypeAchat:
                return "Type achat";
            case idProgramme:
                return "Programme";
            case idAction:
                return "Action";
            case idActivite:
                return "Activité";
            case reference:
                return "Référence";
            case designation:
                return "Désignation";
            case idCategorieImmo:
                return "Catégorie immobilière";
            case idUnite:
                return "Unité";
            case idVilleLivraison:
                return "Ville de livraison";
            case idRegionLivraison:
                return "Région de livraison";
            case idLieuStockage:
                return "Lieu de stockage";
            case idServiceBeneficiaire:
                return "Service bénéficiaire";
            case idFonds:
                return "Fonds";
            case demandeur:
                return "Demandeur";
            case idMinistere:
                return "Ministère";
            case idServiceDepensier:
                return "Service dépensier";
            case idCategorieAchat:
                return "Catégorie achat";
            default:
                return null;
            }
        }
        return null;
    }

    public static ImpactedData fromValue(String value) {
        if (value != null && !value.isEmpty()) {
            switch (value) {
            case "Type achat":
                return ImpactedData.idTypeAchat;
            case "Programme":
                return ImpactedData.idProgramme;
            case "Action":
                return ImpactedData.idAction;
            case "Activité":
                return ImpactedData.idActivite;
            case "Référence":
                return ImpactedData.reference;
            case "Désignation":
                return ImpactedData.designation;
            case "Catégorie immobilière":
                return ImpactedData.idCategorieImmo;
            case "Unité":
                return ImpactedData.idUnite;
            case "Ville de livraison":
                return ImpactedData.idVilleLivraison;
            case "Région de livraison":
                return ImpactedData.idRegionLivraison;
            case "Lieu de stockage":
                return ImpactedData.idLieuStockage;
            case "Service bénéficiaire":
                return ImpactedData.idServiceBeneficiaire;
            case "Fonds":
                return ImpactedData.idFonds;
            case "Demandeur":
                return ImpactedData.demandeur;
            case "Ministère":
                return ImpactedData.idMinistere;
            case "Service dépensier":
                return ImpactedData.idServiceDepensier;
            case "Catégorie achat":
                return ImpactedData.idCategorieAchat;

            default:
                return null;
            }
        }
        return null;
    }

}
