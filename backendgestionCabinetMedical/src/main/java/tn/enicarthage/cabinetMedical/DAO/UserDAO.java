//DAO = le traducteur

//DAO veut dire Data Access Object (objet d’accès aux données). C’est une classe dont le seul rôle est de parler à la base de données.

//Les 4 opérations (CRUD)

//Un DAO a presque toujours les mêmes méthodes :

//Méthode                            Sens	                 SQLcorrespondant

//ajouter	                         Create	                 INSERT
//trouverParId, trouverTous	         Read	                 SELECT
//modifier	                         Update	                 UPDATE
//supprimer	                         Delete	                 DELETE


package tn.enicarthage.cabinetMedical.DAO;

import tn.enicarthage.cabinetMedical.model.Role;
import tn.enicarthage.cabinetMedical.model.Utilisateur;
import tn.enicarthage.cabinetMedical.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // Transforme une ligne de la base en objet Utilisateur
    private Utilisateur mapper(ResultSet rs) throws SQLException {
        return new Utilisateur(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("email"),
                rs.getString("tel"),
                rs.getString("mdp_hash"),
                Role.valueOf(rs.getString("role"))
        );
    }

    public void create(Utilisateur utilisateur) {
        String sql = "INSERT INTO utilisateur (nom, prenom, email, tel, mdp_hash, role) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, utilisateur.getNom());
            ps.setString(2, utilisateur.getPrenom());
            ps.setString(3, utilisateur.getEmail());
            ps.setString(4, utilisateur.getTel());
            ps.setString(5, utilisateur.getMdphash());
            ps.setString(6, utilisateur.getRole().name());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Utilisateur> findAll() {
        List<Utilisateur> liste = new ArrayList<>();
        String sql = "SELECT * FROM utilisateur";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                liste.add(mapper(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public Utilisateur findById(int id) {
        String sql = "SELECT * FROM utilisateur WHERE id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapper(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Utilisateur findByEmail(String email) {
        String sql = "SELECT * FROM utilisateur WHERE email = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapper(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Utilisateur utilisateur) {
        String sql = "UPDATE utilisateur SET nom = ?, prenom = ?, email = ?, tel = ?, "
                + "mdp_hash = ?, role = ? WHERE id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, utilisateur.getNom());
            ps.setString(2, utilisateur.getPrenom());
            ps.setString(3, utilisateur.getEmail());
            ps.setString(4, utilisateur.getTel());
            ps.setString(5, utilisateur.getMdphash());
            ps.setString(6, utilisateur.getRole().name());
            ps.setInt(7, utilisateur.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM utilisateur WHERE id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}