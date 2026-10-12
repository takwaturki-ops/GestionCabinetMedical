
package tn.enicarthage.cabinetMedical.main;

import tn.enicarthage.cabinetMedical.config.DatabaseConnection;
import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        try (Connection connection =

                     DatabaseConnection.getConnection()) {

            System.out.println("Connexion réussie à MariaDB !");

        } catch (Exception e) {
            System.out.println("Erreur de connexion : "
                    + e.getMessage());
        }
    }
}
