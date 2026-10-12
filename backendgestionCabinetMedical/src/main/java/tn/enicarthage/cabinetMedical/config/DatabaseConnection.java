package tn.enicarthage.cabinetMedical.config;

/*
 * Ce fichier sert à établir la connexion entre notre application Java et la base de données.
 * Les autres classes peuvent utiliser cette connexion pour enregistrer, consulter,
 * modifier ou supprimer les données des patients.
 */


// Importer les classes nécessaires pour se connecter à la base de données
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // URL de connexion : adresse du serveur, port et nom de la base de données
    private static final String URL =
            "jdbc:mariadb://localhost:3306/gestion_cabinet";

    // Identifiants utilisés pour se connecter à MariaDB
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
        // Établir la connexion avec les informations définies ci-dessus
    }
}
