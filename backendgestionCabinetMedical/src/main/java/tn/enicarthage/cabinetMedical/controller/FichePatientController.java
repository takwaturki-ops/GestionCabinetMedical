package tn.enicarthage.cabinetMedical.controller;

import tn.enicarthage.cabinetMedical.dto.FichePatientRequest;
import tn.enicarthage.cabinetMedical.model.FichePatient;
import tn.enicarthage.cabinetMedical.service.FichePatientService;

import java.util.List;

public class FichePatientController {

    private final FichePatientService service = new FichePatientService();

    public List<FichePatient> lister() {
        return service.lister();
    }

    public FichePatient obtenir(int id) {
        return service.obtenir(id);
    }

    public FichePatient creer(FichePatientRequest request) {
        return service.creer(request);
    }

    public FichePatient modifier(int id, FichePatientRequest request) {
        return service.modifier(id, request);
    }

    public void supprimer(int id) {
        service.supprimer(id);
    }
}