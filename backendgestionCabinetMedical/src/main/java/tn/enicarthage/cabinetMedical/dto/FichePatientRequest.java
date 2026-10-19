package tn.enicarthage.cabinetMedical.dto;

import java.time.LocalDate;

public class FichePatientRequest {
    private LocalDate date;
    private String groupeSanguin;
    private String allergie;
    private String traitementEnCours;
    private String antecedants;

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getGroupeSanguin() { return groupeSanguin; }
    public void setGroupeSanguin(String groupeSanguin) { this.groupeSanguin = groupeSanguin; }

    public String getAllergie() { return allergie; }
    public void setAllergie(String allergie) { this.allergie = allergie; }

    public String getTraitementEnCours() { return traitementEnCours; }
    public void setTraitementEnCours(String traitementEnCours) { this.traitementEnCours = traitementEnCours; }

    public String getAntecedants() { return antecedants; }
    public void setAntecedants(String antecedants) { this.antecedants = antecedants; }
}