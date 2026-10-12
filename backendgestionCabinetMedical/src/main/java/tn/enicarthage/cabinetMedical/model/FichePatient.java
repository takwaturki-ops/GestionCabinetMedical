package tn.enicarthage.cabinetMedical.model;

import java.time.LocalDate;

public class FichePatient {
    private int id;
    private LocalDate dateCreation;
    private String groupeSanguin;
    private String allergie;
    private String traitementEnCours;
    private String antecedants;

    public FichePatient(){}
    //il faut ajouter les att concernant la classe patient!!!!!!!!<<<<3333
    public FichePatient(
            int id,
            LocalDate dateCreation,
            String groupeSanguin,
            String allergie,
            String traitementEnCours,
            String antecedants
    ){this.id = id;
        this.dateCreation = dateCreation;
        this.groupeSanguin = groupeSanguin;
        this.allergie = allergie;
        this.traitementEnCours = traitementEnCours;
        this.antecedants = antecedants;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getGroupeSanguin() {
        return groupeSanguin;
    }

    public void setGroupeSanguin(String groupeSanguin) {
        this.groupeSanguin = groupeSanguin;
    }

    public String getAllergie() {
        return allergie;
    }

    public void setAllergie(String allergie) {
        this.allergie = allergie;
    }

    public String getTraitementEnCours() {
        return traitementEnCours;
    }

    public void setTraitementEnCours(String traitementEnCours) {
        this.traitementEnCours = traitementEnCours;
    }

    public String getAntecedants() {
        return antecedants;
    }

    public void setAntecedants(String antecedants) {
        this.antecedants = antecedants;
    }
}

