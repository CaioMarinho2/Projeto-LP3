-- MySQL dump 10.13  Distrib 8.0.x
-- Host: localhost    Database: projeto_lp3
-- ------------------------------------------------------
-- Server version 8.0.x

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

USE projeto_lp3;

-----------------------------------------------------------
-- USUÁRIOS
-----------------------------------------------------------
LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;

INSERT INTO usuarios (id, nome, usuario, email, senha, nascimento, foto_perfil_url)
VALUES
(1, 'Renato Vasconcelos', 'renatovas', 'renato.v@example.com', 'senha123', '1999-05-14', NULL),
(2, 'Marina Tavares Lobo', 'marinalobo', 'marina.lobo@example.com', 'abc123', '2001-02-11', NULL),
(3, 'Diego Monteiro Farias', 'diegofarias', 'diego.f@example.com', 'diegopass', '1998-10-03', NULL),
(4, 'Bianca Alencar Mourão', 'biancamourao', 'bianca.m@example.com', 'senha789', '2000-08-25', NULL),
(5, 'Alex Carvalho Torres', 'alextorres', 'alex.t@example.com', 'torres2024', '1997-12-07', NULL);

 /*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

-----------------------------------------------------------
-- POSTS
-----------------------------------------------------------
LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;

INSERT INTO posts (id, usuario_id, conteudo, imagem_url)
VALUES
(1, 1, 'Primeiro post aqui na plataforma! Testando tudo.', NULL),
(2, 2, 'Hoje comecei a aprender JavaFX, muito divertido!', NULL),
(3, 3, 'Alguém aí recomenda livros de lógica de programação?', NULL),
(4, 4, 'Treinei bastante hoje! Corrida + musculação.', NULL),
(5, 5, 'Novo projeto pessoal saindo do forno!', NULL);

 /*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

-----------------------------------------------------------
-- COMENTÁRIOS
-----------------------------------------------------------
LOCK TABLES `comentarios` WRITE;
/*!40000 ALTER TABLE `comentarios` DISABLE KEYS */;

INSERT INTO comentarios (id, post_id, usuario_id, conteudo)
VALUES
(1, 1, 2, 'Boa Renato! Bem-vindo!'),
(2, 2, 1, 'JavaFX é muito bom mesmo!'),
(3, 3, 4, 'Recomendo o livro “Use a Cabeça! Lógica”.'),
(4, 4, 5, 'Disciplina é tudo! Parabéns.'),
(5, 5, 3, 'Mostra esse projeto quando finalizar!');

 /*!40000 ALTER TABLE `comentarios` ENABLE KEYS */;
UNLOCK TABLES;

-----------------------------------------------------------
-- CURTIDAS
-----------------------------------------------------------
LOCK TABLES `curtidas` WRITE;
/*!40000 ALTER_TABLE `curtidas` DISABLE KEYS */;

INSERT INTO curtidas (id, post_id, usuario_id)
VALUES
(1, 1, 2),
(2, 1, 3),
(3, 2, 1),
(4, 3, 4),
(5, 3, 2),
(6, 5, 1);

 /*!40000 ALTER TABLE `curtidas` ENABLE KEYS */;
UNLOCK TABLES;

-----------------------------------------------------------
-- COMPARTILHAMENTOS
-----------------------------------------------------------
LOCK TABLES `compartilhamentos` WRITE;
/*!40000 ALTER TABLE `compartilhamentos` DISABLE KEYS */;

INSERT INTO compartilhamentos (id, post_id, usuario_id)
VALUES
(1, 2, 4),
(2, 1, 5),
(3, 5, 3);

 /*!40000 ALTER TABLE `compartilhamentos` ENABLE KEYS */;
UNLOCK TABLES;

-----------------------------------------------------------
-- SEGUIDORES
-----------------------------------------------------------
LOCK TABLES `seguidores` WRITE;
/*!40000 ALTER TABLE `seguidores` DISABLE KEYS */;

INSERT INTO seguidores (id, seguidor_id, seguido_id)
VALUES
(1, 2, 1),
(2, 3, 1),
(3, 4, 1),
(4, 5, 1),
(5, 1, 2),
(6, 1, 3),
(7, 3, 4),
(8, 4, 5);

 /*!40000 ALTER TABLE `seguidores` ENABLE KEYS */;
UNLOCK TABLES;

-----------------------------------------------------------
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed successfully.