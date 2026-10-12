package tn.enicarthage.cabinetMedical.model;

public class Utilisateur {

    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String tel;
    private String mdphash;
    private Role role;

    public Utilisateur() {
    }

    public Utilisateur(int id, String nom, String prenom, String email,
                       String tel, String mdphash, Role role) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.tel = tel;
        this.mdphash = mdphash;
        this.role = role;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }

    public String getMdphash() { return mdphash; }
    public void setMdphash(String mdphash) { this.mdphash = mdphash; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}