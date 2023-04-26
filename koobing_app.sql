-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 26-04-2023 a las 17:12:54
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `koobing_app`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `autor`
--

CREATE TABLE `autor` (
  `id_autor` int(11) NOT NULL,
  `nom_autor` varchar(255) NOT NULL,
  `data_naix` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `autor`
--

INSERT INTO `autor` (`id_autor`, `nom_autor`, `data_naix`) VALUES
(1, 'Miguel de Cervantes', '1547-10-29'),
(2, 'Charles Dickens', '1812-02-07'),
(3, 'Ana Frank', '1929-06-12'),
(4, 'Vladimir Nabokov', '1899-04-22'),
(5, 'Manuel Mesado i Mañé', '2003-11-01'),
(6, 'Joan M. Marín i Torres', '1980-07-25'),
(7, 'Trinidad Sánchez Pacheco', '1450-06-20'),
(8, 'Rosendo Tello Aina', '1759-08-20'),
(9, 'Ángel Albert Esteve', '1970-03-14'),
(10, 'Juan José Gual Ortí', '1970-05-01'),
(11, 'Antonio José Gascó Sidro', '2010-05-02'),
(12, 'Antonio Beltrán Martínez', '1992-04-01'),
(13, 'José I. Royo Guillen', '2002-11-01'),
(14, 'José Luis Soler Foguet', '1870-08-01'),
(15, 'Inocencio V. Pérez Guillén', '2000-11-30'),
(16, 'Amparo Marco Torres', '1890-01-01'),
(17, 'José Ramón Hinojosa Montalvo', '1961-10-30'),
(18, 'Miguel Ángel Martí Tomás', '1970-04-06'),
(19, 'Lluís Meseguer Pallarés', '1973-05-01'),
(20, 'Antonio Arbeloa Garcia', '1979-01-01'),
(21, 'Ferran Olucha Montins', '1630-12-11'),
(22, 'Francesc Gusi i Jener', '1890-08-01'),
(23, 'Matilde Pepín Fernández', '1920-01-01'),
(24, 'Jose Luis Alonso Bayón', '1920-05-24');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `biblioteca`
--

CREATE TABLE `biblioteca` (
  `id_biblioteca` int(11) NOT NULL,
  `id_poblacio` int(11) NOT NULL,
  `nom_biblio` varchar(255) NOT NULL,
  `latitud` double NOT NULL,
  `longitud` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `biblioteca`
--

INSERT INTO `biblioteca` (`id_biblioteca`, `id_poblacio`, `nom_biblio`, `latitud`, `longitud`) VALUES
(1, 1, 'Biblioteca Municipal De Pals', 41.8463, 3.1246),
(2, 3, 'Biblioteca Pere Caner', 41.8587, 3.0793);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `editorial`
--

CREATE TABLE `editorial` (
  `id_editorial` int(11) NOT NULL,
  `nom_editorial` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `editorial`
--

INSERT INTO `editorial` (`id_editorial`, `nom_editorial`) VALUES
(1, 'Editorial Mirahadas'),
(2, 'Editorial Errata Naturae'),
(3, 'Editorial Sexto Piso'),
(4, 'Editorial Pre-textos');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `genere`
--

CREATE TABLE `genere` (
  `id_genere` int(11) NOT NULL,
  `descrip` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `genere`
--

INSERT INTO `genere` (`id_genere`, `descrip`) VALUES
(1, 'Romantica'),
(2, 'Drama'),
(3, 'Comedia'),
(4, 'Aventura'),
(5, 'Infantil'),
(6, 'Ciencia Ficcio'),
(7, 'Anime');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `idioma`
--

CREATE TABLE `idioma` (
  `id_idioma` int(11) NOT NULL,
  `nom_idioma` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `idioma`
--

INSERT INTO `idioma` (`id_idioma`, `nom_idioma`) VALUES
(1, 'Catala'),
(2, 'Español'),
(3, 'Ingles'),
(4, 'Aleman');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `llibre`
--

CREATE TABLE `llibre` (
  `ISBN` bigint(13) NOT NULL,
  `id_autor` int(11) NOT NULL,
  `id_editor` int(11) NOT NULL,
  `id_idioma` int(11) NOT NULL,
  `id_genere` int(11) NOT NULL,
  `titol` varchar(255) NOT NULL,
  `versio` int(11) NOT NULL,
  `data_publi` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `llibre`
--

INSERT INTO `llibre` (`ISBN`, `id_autor`, `id_editor`, `id_idioma`, `id_genere`, `titol`, `versio`, `data_publi`) VALUES
(9780582400115, 2, 3, 2, 4, 'Historia de dos ciudades', 1, '1859-04-25'),
(9783498046460, 4, 4, 3, 2, 'Lolita', 1, '1955-10-21'),
(9788401351983, 3, 2, 2, 2, 'Diario de Ana Frank\r\n', 1, '1947-06-25'),
(9788417430677, 1, 2, 4, 4, 'El Quijote', 1, '1605-01-01'),
(9788423354272, 4, 4, 1, 4, 'El Perfume', 5, '1985-01-01'),
(9788424659969, 2, 1, 4, 3, 'Cien años de soledad', 1, '1967-01-01'),
(9788445005484, 3, 2, 1, 3, 'La catedral del mar', 1, '2006-01-01');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `poblacio`
--

CREATE TABLE `poblacio` (
  `id_poblacio` int(11) NOT NULL,
  `nom_poble` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `poblacio`
--

INSERT INTO `poblacio` (`id_poblacio`, `nom_poble`) VALUES
(1, 'Pals'),
(2, 'Begur'),
(3, 'Calonge'),
(4, 'Platja D\'Aro');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reserves`
--

CREATE TABLE `reserves` (
  `id_reserva` int(11) NOT NULL,
  `id_usuari` int(11) NOT NULL,
  `id_treballador` int(11) NOT NULL,
  `id_biblioteca` int(11) NOT NULL,
  `ISBN` bigint(13) NOT NULL,
  `data_hora_reserva` datetime NOT NULL,
  `data_hora_entrega` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `reserves`
--

INSERT INTO `reserves` (`id_reserva`, `id_usuari`, `id_treballador`, `id_biblioteca`, `ISBN`, `data_hora_reserva`, `data_hora_entrega`) VALUES
(1, 2, 2, 1, 9788401351983, '2023-04-25 15:35:12', '2023-04-25 20:35:13');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `treballador`
--

CREATE TABLE `treballador` (
  `id_treballador` int(11) NOT NULL,
  `avatar` blob NOT NULL,
  `dni` varchar(9) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `cognom` varchar(255) NOT NULL,
  `data_naix` date NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `num_seg_social` varchar(9) NOT NULL,
  `isAdmin` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `treballador`
--

INSERT INTO `treballador` (`id_treballador`, `avatar`, `dni`, `nom`, `cognom`, `data_naix`, `email`, `password`, `num_seg_social`, `isAdmin`) VALUES
(2, 0x6e756c6c, '12345678A', 'admin', 'super', '2023-02-23', 'admin@mail.com', '47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU=', '123', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuari`
--

CREATE TABLE `usuari` (
  `id_usuari` int(11) NOT NULL,
  `dni` varchar(9) NOT NULL,
  `avatar` blob DEFAULT NULL,
  `nom` varchar(255) NOT NULL,
  `cognom` varchar(255) NOT NULL,
  `data_naix` date NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuari`
--

INSERT INTO `usuari` (`id_usuari`, `dni`, `avatar`, `nom`, `cognom`, `data_naix`, `email`, `password`) VALUES
(1, '41531693H', 0x6e756c6c, 'Eduard', 'Pujadas', '2002-02-11', 'edu@mail.com', '47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU='),
(2, '12345678A', 0x6e756c6c, 'Prova', 'Hola', '2023-03-28', 'prova@mail.com', '47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU='),
(7, '53168398L', 0x6e756c6c, 'Manel', 'Bordoll', '2003-03-05', 'mbordoll@mail.com', '47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU='),
(8, '5631231B', 0x636f6d2e6d7973716c2e636a2e6a6462632e426c6f62403536353766383165, 'Lola', 'Manuela', '2001-02-06', 'lola@mail.com', 'GYPAV8lMJ0/apArAILqrrR9mJGXrlrpjxPxscjRuKfc='),
(10, '78653212B', 0x6e756c6c, 'Pepe', 'Prova', '1992-02-12', 'pep@mailc.om', '47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU=');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `autor`
--
ALTER TABLE `autor`
  ADD PRIMARY KEY (`id_autor`);

--
-- Indices de la tabla `biblioteca`
--
ALTER TABLE `biblioteca`
  ADD PRIMARY KEY (`id_biblioteca`),
  ADD KEY `fk_poblacio_biblio` (`id_poblacio`);

--
-- Indices de la tabla `editorial`
--
ALTER TABLE `editorial`
  ADD PRIMARY KEY (`id_editorial`);

--
-- Indices de la tabla `genere`
--
ALTER TABLE `genere`
  ADD PRIMARY KEY (`id_genere`);

--
-- Indices de la tabla `idioma`
--
ALTER TABLE `idioma`
  ADD PRIMARY KEY (`id_idioma`);

--
-- Indices de la tabla `llibre`
--
ALTER TABLE `llibre`
  ADD PRIMARY KEY (`ISBN`),
  ADD KEY `autor_FK` (`id_autor`),
  ADD KEY `editorial_FK` (`id_editor`),
  ADD KEY `idioma_fk` (`id_idioma`),
  ADD KEY `genere_fk` (`id_genere`);

--
-- Indices de la tabla `poblacio`
--
ALTER TABLE `poblacio`
  ADD PRIMARY KEY (`id_poblacio`);

--
-- Indices de la tabla `reserves`
--
ALTER TABLE `reserves`
  ADD PRIMARY KEY (`id_reserva`),
  ADD KEY `biblioFK` (`id_biblioteca`),
  ADD KEY `llibreFK` (`ISBN`),
  ADD KEY `workerFK` (`id_treballador`),
  ADD KEY `userFK` (`id_usuari`);

--
-- Indices de la tabla `treballador`
--
ALTER TABLE `treballador`
  ADD PRIMARY KEY (`id_treballador`),
  ADD UNIQUE KEY `dni` (`dni`,`email`);

--
-- Indices de la tabla `usuari`
--
ALTER TABLE `usuari`
  ADD PRIMARY KEY (`id_usuari`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `dni` (`dni`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `treballador`
--
ALTER TABLE `treballador`
  MODIFY `id_treballador` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `usuari`
--
ALTER TABLE `usuari`
  MODIFY `id_usuari` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `biblioteca`
--
ALTER TABLE `biblioteca`
  ADD CONSTRAINT `fk_poblacio_biblio` FOREIGN KEY (`id_poblacio`) REFERENCES `poblacio` (`id_poblacio`);

--
-- Filtros para la tabla `llibre`
--
ALTER TABLE `llibre`
  ADD CONSTRAINT `autor_FK` FOREIGN KEY (`id_autor`) REFERENCES `autor` (`id_autor`),
  ADD CONSTRAINT `editorial_FK` FOREIGN KEY (`id_editor`) REFERENCES `editorial` (`id_editorial`),
  ADD CONSTRAINT `genere_fk` FOREIGN KEY (`id_genere`) REFERENCES `genere` (`id_genere`),
  ADD CONSTRAINT `idioma_fk` FOREIGN KEY (`id_idioma`) REFERENCES `idioma` (`id_idioma`);

--
-- Filtros para la tabla `reserves`
--
ALTER TABLE `reserves`
  ADD CONSTRAINT `biblioFK` FOREIGN KEY (`id_biblioteca`) REFERENCES `biblioteca` (`id_biblioteca`),
  ADD CONSTRAINT `llibreFK` FOREIGN KEY (`ISBN`) REFERENCES `llibre` (`ISBN`),
  ADD CONSTRAINT `userFK` FOREIGN KEY (`id_usuari`) REFERENCES `usuari` (`id_usuari`),
  ADD CONSTRAINT `workerFK` FOREIGN KEY (`id_treballador`) REFERENCES `treballador` (`id_treballador`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
