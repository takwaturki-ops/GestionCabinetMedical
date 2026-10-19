package tn.enicarthage.cabinetMedical.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FichePatient {
    private int id;
    private LocalDate date;
    private String groupeSanguin;
    private String allergie;
    private String traitementEnCours;
    private String antecedants;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

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

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}