CREATE TABLE fiche_patient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL UNIQUE,
    date_creation DATE NOT NULL,
    groupe_sanguin VARCHAR(5),
    allergie TEXT,
    traitement_en_cours TEXT,
    antecedants TEXT,

    FOREIGN KEY (patient_id)
                           REFERECES patient (id)
                           ON DELETE CASCADE

)