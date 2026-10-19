package tn.enicarthage.cabinetMedical.service;

import tn.enicarthage.cabinetMedical.dao.FichePatientDao;
import tn.enicarthage.cabinetMedical.dto.FichePatientRequest;
import tn.enicarthage.cabinetMedical.exception.ResourceNotFoundException;
import tn.enicarthage.cabinetMedical.model.FichePatient;

import java.util.List;

public class FichePatientService {

    private final FichePatientDao dao = new FichePatientDao();

    public List<FichePatient> lister() {
        return dao.trouverTous();
    }

    public FichePatient obtenir(int id) {
        return dao.trouverParId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fiche patient introuvable : " + id));
    }

    public FichePatient creer(FichePatientRequest request) {
        FichePatient f = versModele(request);
        int id = dao.inserer(f);
        return obtenir(id);
    }

    public FichePatient modifier(int id, FichePatientRequest request) {
        FichePatient f = versModele(request);
        if (!dao.modifier(id, f)) {
            throw new ResourceNotFoundException("Fiche patient introuvable : " + id);
        }
        return obtenir(id);
    }

    public void supprimer(int id) {
        if (!dao.supprimer(id)) {
            throw new ResourceNotFoundException("Fiche patient introuvable : " + id);
        }
    }

    // Validation + conversion DTO -> modèle
    private FichePatient versModele(FichePatientRequest r) {
        if (r.getGroupeSanguin() != null && r.getGroupeSanguin().length() > 10) {
            throw new IllegalArgumentException("groupeSanguin : 10 caracteres maximum.");
        }
        FichePatient f = new FichePatient();
        f.setDate(r.getDate());
        f.setGroupeSanguin(r.getGroupeSanguin());
        f.setAllergie(r.getAllergie());
        f.setTraitementEnCours(r.getTraitementEnCours());
        f.setAntecedants(r.getAntecedants());
        return f;
    }
}
