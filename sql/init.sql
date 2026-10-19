CREATE DATABASE IF NOT EXISTS redsocial CHARACTER SET utf8mb4;
USE redsocial;

CREATE TABLE IF NOT EXISTS usuario (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    email      VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS comentario (
    id_comentario   BIGINT AUTO_INCREMENT PRIMARY KEY,
    texto           VARCHAR(500) NOT NULL,
    data_comentario DATETIME(6)  NOT NULL,
    id_emisor       BIGINT       NOT NULL,
    id_receptor     BIGINT       NOT NULL,
    CONSTRAINT fk_comentario_emisor   FOREIGN KEY (id_emisor)   REFERENCES usuario(id_usuario),
    CONSTRAINT fk_comentario_receptor FOREIGN KEY (id_receptor) REFERENCES usuario(id_usuario)
);

INSERT INTO usuario (id_usuario, email) VALUES
(1, 'ana@example.com'),
(2, 'luis@example.com'),
(3, 'marta@example.com');

-- Uno de hace 2 días (entra en el filtro de 7 días) y otro de hace 10 días (queda fuera)
INSERT INTO comentario (texto, data_comentario, id_emisor, id_receptor) VALUES
('Buen trabajo con el proyecto de Hibernate.', NOW(6) - INTERVAL 2 DAY, 1, 2),
('Este comentario es antiguo y no debe salir en el filtro.', NOW(6) - INTERVAL 10 DAY, 1, 3),
('Gracias, quedamos para repasar las consultas HQL.', NOW(6) - INTERVAL 1 DAY, 2, 1);