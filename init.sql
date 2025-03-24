CREATE TABLE `tipo_usuario` (
  `id_tipo_usuario` int NOT NULL,
  `nome` varchar(20) NOT NULL,
  PRIMARY KEY (`id_tipo_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `usuario` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `senha` varchar(255) NOT NULL,
  `username` varchar(30) DEFAULT NULL,
  `id_tipo_usuario` int NOT NULL,
  PRIMARY KEY (`id_usuario`),
  KEY `fk_usuario_tipo` (`id_tipo_usuario`),
  CONSTRAINT `fk_usuario_tipo` FOREIGN KEY (`id_tipo_usuario`) REFERENCES `tipo_usuario` (`id_tipo_usuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `autor` (
  `idautor` int NOT NULL AUTO_INCREMENT,
  `nomeautor` varchar(45) DEFAULT NULL,
  `sobrenome` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idautor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `editora` (
  `ideditora` int NOT NULL AUTO_INCREMENT,
  `nomeeditora` varchar(45) NOT NULL,
  PRIMARY KEY (`ideditora`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `livro` (
  `idlivro` int NOT NULL AUTO_INCREMENT,
  `nomelivro` varchar(45) NOT NULL,
  `fkautor` int NOT NULL,
  `editora` int NOT NULL,
  PRIMARY KEY (`idlivro`),
  KEY `fkautor_idx` (`fkautor`),
  KEY `fkeditora_idx` (`editora`),
  CONSTRAINT `fkautor` FOREIGN KEY (`fkautor`) REFERENCES `autor` (`idautor`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fkeditora` FOREIGN KEY (`editora`) REFERENCES `editora` (`ideditora`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `usuario_livro` (
  `id_usuario_livro` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `idlivro` int NOT NULL,
  PRIMARY KEY (`id_usuario_livro`),
  KEY `fk_usuario` (`id_usuario`),
  KEY `fk_livro` (`idlivro`),
  CONSTRAINT `fk_livro` FOREIGN KEY (`idlivro`) REFERENCES `livro` (`idlivro`) ON DELETE CASCADE,
  CONSTRAINT `fk_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
