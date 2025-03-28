CREATE TABLE `tipo_usuario` (
  `id_tipo_usuario` int NOT NULL,
  `tipo` varchar(20) NOT NULL,
  PRIMARY KEY (`id_tipo_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `usuario` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `username` varchar(30) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `numero_cel` VARCHAR(20) NOT NULL,
  `endereco` VARCHAR(255) NOT NULL,
  `bairro` VARCHAR(100) NOT NULL,
  `cidade` VARCHAR(100) NOT NULL,
  `estado` VARCHAR(50) NOT NULL,
  `cep` VARCHAR(10) NOT NULL,
  `id_tipo_usuario` int NOT NULL,
  PRIMARY KEY (`id_usuario`),
  KEY `fk_usuario_tipo` (`id_tipo_usuario`),
  CONSTRAINT `fk_usuario_tipo` FOREIGN KEY (`id_tipo_usuario`) REFERENCES `tipo_usuario` (`id_tipo_usuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `autor` (
  `id_autor` int NOT NULL AUTO_INCREMENT,
  `nome_autor` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_autor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `editora` (
  `id_editora` int NOT NULL AUTO_INCREMENT,
  `nome_editora` varchar(50) NOT NULL,  
  PRIMARY KEY (`id_editora`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `genero` (
  `id_genero` int NOT NULL AUTO_INCREMENT,
  `nome_genero` varchar(50) NOT NULL,  
  PRIMARY KEY (`id_genero`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `livro` (
  `id_livro` int NOT NULL AUTO_INCREMENT,
  `titulo_livro` varchar(45) NOT NULL,
  `id_genero` int NOT NULL,
  `id_autor` int NOT NULL,
  `id_editora` int NOT NULL,
  `disponibilidade` boolean NOT NULL,
  PRIMARY KEY (`id_livro`),
  KEY `fk_livro_autor` (`id_autor`),
  KEY `fk_livro_editora` (`id_editora`),
  KEY `fk_livro_genero` (`id_genero`),
  CONSTRAINT `fk_livro_autor` FOREIGN KEY (`id_autor`) REFERENCES `autor` (`id_autor`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_livro_editora` FOREIGN KEY (`id_editora`) REFERENCES `editora` (`id_editora`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_livro_genero` FOREIGN KEY (`id_genero`) REFERENCES `genero` (`id_genero`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `emprestimo` (
  `id_emprestimo` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `id_livro` int NOT NULL,
  PRIMARY KEY (`id_emprestimo`),
  KEY `fk_emprestimo_usuario` (`id_usuario`),
  KEY `fk_emprestimo_livro` (`id_livro`),
  CONSTRAINT `fk_emprestimo_livro` FOREIGN KEY (`id_livro`) REFERENCES `livro` (`id_livro`) ON DELETE CASCADE,
  CONSTRAINT `fk_emprestimo_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `tipo_usuario` (`id_tipo_usuario`, `tipo`) VALUES (1,'admin') ON DUPLICATE KEY UPDATE tipo='admin';
INSERT INTO `tipo_usuario` (`id_tipo_usuario`, `tipo`) VALUES (2,'bibliotecario') ON DUPLICATE KEY UPDATE tipo='bibliotecario';
INSERT INTO `tipo_usuario` (`id_tipo_usuario`, `tipo`) VALUES (3,'cliente') ON DUPLICATE KEY UPDATE tipo='cliente';

INSERT INTO `usuario` (`username`,`email`,`senha`,`numero_cel`,`endereco`,`bairro`,`cidade`,`estado`,`cep`,`id_tipo_usuario`) VALUES ('admin','admin@bibliojeba.com','qwe123','(96)98765-4321','Rua Fulano, 0110', 'Centro', 'Macapá', 'Amapá', '12345-678',1) ON DUPLICATE KEY UPDATE email ='admin@bibliojeba.com';