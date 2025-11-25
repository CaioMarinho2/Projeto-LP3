

-- ------------------------------------------------------
-- Script de criação do banco de dados 
-- ------------------------------------------------------

DROP DATABASE IF EXISTS projeto_lp3;
CREATE DATABASE projeto_lp3;
USE projeto_lp3;

-- ------------------------------------------------------
-- Tabela: usuarios
-- ------------------------------------------------------

CREATE TABLE usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    usuario VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    nascimento DATE NOT NULL,
    foto_perfil_url VARCHAR(255),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ------------------------------------------------------
-- Tabela: posts
-- ------------------------------------------------------

CREATE TABLE posts (
    id INT PRIMARY KEY AUTO_INCREMENT,
    usuario_id INT NOT NULL,
    conteudo TEXT NOT NULL,
    imagem_url VARCHAR(255),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    curtidas INT DEFAULT 0,
    compartilhamentos INT DEFAULT 0,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- ------------------------------------------------------
-- Tabela: comentarios
-- ------------------------------------------------------

CREATE TABLE comentarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    post_id INT NOT NULL,
    usuario_id INT NOT NULL,
    conteudo TEXT NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES posts(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- ------------------------------------------------------
-- Tabela: compartilhamentos
-- ------------------------------------------------------

CREATE TABLE compartilhamentos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    post_id INT NOT NULL,
    usuario_id INT NOT NULL,
    data_compartilhamento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES posts(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- ------------------------------------------------------
-- Tabela: seguidores
-- ------------------------------------------------------

CREATE TABLE seguidores (
    id INT PRIMARY KEY AUTO_INCREMENT,
    seguidor_id INT NOT NULL,
    seguido_id INT NOT NULL,
    data_seguindo TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (seguidor_id) REFERENCES usuarios(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (seguido_id) REFERENCES usuarios(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT unq_seguidor_seguido UNIQUE (seguidor_id, seguido_id)
);

-- ------------------------------------------------------
-- Tabela: curtidas
-- ------------------------------------------------------

CREATE TABLE curtidas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    post_id INT NOT NULL,
    usuario_id INT NOT NULL,
    data_curtida TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (post_id, usuario_id),
    FOREIGN KEY (post_id) REFERENCES posts(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);






