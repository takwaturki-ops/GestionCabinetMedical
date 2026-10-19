package tn.enicarthage.cabinetMedical.dao;

import tn.enicarthage.cabinetMedical.config.DatabaseConnection;
import tn.enicarthage.cabinetMedical.model.FichePatient;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FichePatientDao {

    private static final String SELECT =
            "SELECT id, `date`, groupe_sanguin, allergie, traitementencours, "
                    + "antecedants, created_at, updated_at FROM fichepatient";

    public List<FichePatient> trouverTous() {
        List<FichePatient> liste = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT + " ORDER BY id");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                liste.add(mapper(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur SQL : " + e.getMessage(), e);
        }
        return liste;
    }

    public Optional<FichePatient> trouverParId(int id) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT + " WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapper(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur SQL : " + e.getMessage(), e);
        }
    }

    // Retourne l'id généré
    public int inserer(FichePatient f) {
        String sql = "INSERT INTO fichepatient (`date`, groupe_sanguin, allergie, "
                + "traitementencours, antecedants) VALUES (?, ?, ?, ?, ?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            remplir(ps, f);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
                throw new RuntimeException("Aucun id genere.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur SQL : " + e.getMessage(), e);
        }
    }

    // Retourne true si une ligne a été modifiée
    public boolean modifier(int id, FichePatient f) {
        String sql = "UPDATE fichepatient SET `date` = ?, groupe_sanguin = ?, allergie = ?, "
                + "traitementencours = ?, antecedants = ? WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            remplir(ps, f);
            ps.setInt(6, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur SQL : " + e.getMessage(), e);
        }
    }

    public boolean supprimer(int id) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM fichepatient WHERE id = ?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur SQL : " + e.getMessage(), e);
        }
    }

    private void remplir(PreparedStatement ps, FichePatient f) throws SQLException {
        ps.setDate(1, f.getDate() == null ? null : Date.valueOf(f.getDate()));
        ps.setString(2, f.getGroupeSanguin());
        ps.setString(3, f.getAllergie());
        ps.setString(4, f.getTraitementEnCours());
        ps.setString(5, f.getAntecedants());
    }

    private FichePatient mapper(ResultSet rs) throws SQLException {
        FichePatient f = new FichePatient();
        f.setId(rs.getInt("id"));
        Date d = rs.getDate("date");
        f.setDate(d == null ? null : d.toLocalDate());
        f.setGroupeSanguin(rs.getString("groupe_sanguin"));
        f.setAllergie(rs.getString("allergie"));
        f.setTraitementEnCours(rs.getString("traitementencours"));
        f.setAntecedants(rs.getString("antecedants"));
        Timestamp cr = rs.getTimestamp("created_at");
        Timestamp up = rs.getTimestamp("updated_at");
        f.setCreatedAt(cr == null ? null : cr.toLocalDateTime());
        f.setUpdatedAt(up == null ? null : up.toLocalDateTime());
        return f;
    }
}