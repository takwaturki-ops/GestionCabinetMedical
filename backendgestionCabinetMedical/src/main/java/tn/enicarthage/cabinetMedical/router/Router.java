package tn.enicarthage.cabinetMedical.router;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.sun.net.httpserver.HttpExchange;

import tn.enicarthage.cabinetMedical.controller.FichePatientController;
import tn.enicarthage.cabinetMedical.dto.FichePatientRequest;
import tn.enicarthage.cabinetMedical.exception.ResourceNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Router {

    // Représente une réponse HTTP (statut + corps JSON)
    private record Reponse(int statut, String corps) {
    }

    // Gson configuré pour gérer LocalDate / LocalDateTime
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class,
                    (JsonSerializer<LocalDate>) (src, type, ctx) -> new JsonPrimitive(src.toString()))
            .registerTypeAdapter(LocalDate.class,
                    (JsonDeserializer<LocalDate>) (json, type, ctx) ->
                            json.isJsonNull() ? null : LocalDate.parse(json.getAsString()))
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonSerializer<LocalDateTime>) (src, type, ctx) -> new JsonPrimitive(src.toString()))
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonDeserializer<LocalDateTime>) (json, type, ctx) ->
                            json.isJsonNull() ? null : LocalDateTime.parse(json.getAsString()))
            .create();

    // Contrôleurs
    private final FichePatientController fichePatientController = new FichePatientController();

    // Point d'entrée unique pour toutes les requêtes HTTP
    public void dispatcher(HttpExchange exchange) throws IOException {
        String methode = exchange.getRequestMethod();
        String chemin = exchange.getRequestURI().getPath();
        String[] segments = chemin.split("/");

        try {
            Reponse reponse = router(methode, segments, exchange);
            envoyer(exchange, reponse.statut(), reponse.corps());
        } catch (ResourceNotFoundException e) {
            envoyer(exchange, 404, messageErreur(e));
        } catch (IllegalArgumentException e) {
            envoyer(exchange, 400, messageErreur(e));
        } catch (Exception e) {
            envoyer(exchange, 500, messageErreur(e));
        }
    }

    // Dispatch par première ressource (/fiches-patients, ...)
    private Reponse router(String methode, String[] segments, HttpExchange exchange)
            throws IOException {
        if (segments.length < 2 || segments[1].isBlank()) {
            throw new ResourceNotFoundException("Route vide ou inconnue.");
        }

        switch (segments[1]) {
            case "fiches-patients":
                return routesFichePatient(methode, segments, exchange);
            // Ajoutez d'autres ressources ici : case "medecins": ...
            default:
                throw new ResourceNotFoundException("Route inconnue : /" + segments[1]);
        }
    }

    // GET /fiches-patients
    // GET /fiches-patients/{id}
    // POST /fiches-patients
    // PUT /fiches-patients/{id}
    // DELETE /fiches-patients/{id}
    private Reponse routesFichePatient(String methode, String[] segments, HttpExchange exchange)
            throws IOException {
        if (segments.length == 2) {
            switch (methode) {
                case "GET":
                    return new Reponse(200, gson.toJson(fichePatientController.lister()));
                case "POST": {
                    FichePatientRequest request = lireCorps(exchange, FichePatientRequest.class);
                    return new Reponse(201, gson.toJson(fichePatientController.creer(request)));
                }
                default:
                    throw new IllegalArgumentException("Methode non supportee : " + methode + " /fiches-patients");
            }
        }

        if (segments.length == 3) {
            int id = entier(segments[2]);
            switch (methode) {
                case "GET":
                    return new Reponse(200, gson.toJson(fichePatientController.obtenir(id)));
                case "PUT": {
                    FichePatientRequest request = lireCorps(exchange, FichePatientRequest.class);
                    return new Reponse(200, gson.toJson(fichePatientController.modifier(id, request)));
                }
                case "DELETE":
                    fichePatientController.supprimer(id);
                    return new Reponse(204, "");
                default:
                    throw new IllegalArgumentException("Methode non supportee : " + methode + " /fiches-patients/{id}");
            }
        }

        throw new ResourceNotFoundException("Route inconnue : /" + String.join("/", segments));
    }

    // ---------- Méthodes utilitaires ----------

    // Lit le corps JSON et le convertit en objet Java
    private <T> T lireCorps(HttpExchange exchange, Class<T> classe) throws IOException {
        try (InputStream is = exchange.getRequestBody()) {
            String corps = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            if (corps.isBlank()) {
                throw new IllegalArgumentException("Corps de la requete vide.");
            }
            try {
                T objet = gson.fromJson(corps, classe);
                if (objet == null) {
                    throw new IllegalArgumentException("JSON invalide.");
                }
                return objet;
            } catch (com.google.gson.JsonParseException e) {
                throw new IllegalArgumentException("JSON invalide : " + e.getMessage());
            }
        }
    }

    // Convertit l'ID de l'URL en entier (400 si invalide)
    private int entier(String valeur) {
        try {
            return Integer.parseInt(valeur);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID invalide : " + valeur);
        }
    }

    // Construit un JSON d'erreur {"erreur":"..."}
    private String messageErreur(Exception e) {
        java.util.Map<String, String> erreur = new java.util.HashMap<>();
        erreur.put("erreur", e.getMessage() == null ? "Erreur inconnue" : e.getMessage());
        return gson.toJson(erreur);
    }

    // Envoie la réponse HTTP avec statut et corps JSON
    private void envoyer(HttpExchange exchange, int statut, String corps) throws IOException {
        byte[] bytes = corps == null ? new byte[0] : corps.getBytes(StandardCharsets.UTF_8);
        if (bytes.length > 0) {
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
            exchange.sendResponseHeaders(statut, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        } else {
            exchange.sendResponseHeaders(statut, -1);
            exchange.close();
        }
    }
}
