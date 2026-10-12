package tn.enicarthage.cabinetMedical.main;

import tn.enicarthage.cabinetMedical.DAO.UserDAO;
import tn.enicarthage.cabinetMedical.model.Role;
import tn.enicarthage.cabinetMedical.model.Utilisateur;

public class TestDAO {

    public static void main(String[] args) {
        UserDAO dao = new UserDAO();

        // Email unique à chaque exécution (la colonne email est UNIQUE)
        String email = "test" + System.currentTimeMillis() + "@mail.com";

        Utilisateur u = new Utilisateur(0, "Ben Ali", "Sara", email,
                "20123456", "hash_provisoire", Role.PATIENT);

        dao.create(u);
        System.out.println("create termine");

        Utilisateur trouve = dao.findByEmail(email);
        if (trouve != null) {
            System.out.println("Trouve : " + trouve.getPrenom() + " " + trouve.getNom()
                    + " (" + trouve.getRole() + ")");
        } else {
            System.out.println("Utilisateur introuvable");
        }

        System.out.println("Nombre d'utilisateurs : " + dao.findAll().size());
    }
}