
DROP DATABASE IF EXISTS gestion_cabinet;
 CREATE DATABASE gestion_cabinet
 CHARACTER SET utf8mb4
 COLLATE utf8mb4_unicode_ci;
 USE gestion_cabinet;

CREATE TABLE fichepatient (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              date DATE,
                              groupe_sanguin VARCHAR(10),
                              allergie TEXT,
                              traitementencours TEXT,
                              antecedants TEXT,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                                  ON UPDATE CURRENT_TIMESTAMP
);