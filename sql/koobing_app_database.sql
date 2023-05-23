-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 23-05-2023 a las 19:25:21
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
-- Base de datos: `koobing_app_database`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `autor`
--

CREATE TABLE `autor` (
  `id_autor` int(11) NOT NULL,
  `nom_autor` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `editorial`
--

CREATE TABLE `editorial` (
  `id_editorial` int(11) NOT NULL,
  `nom_editorial` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estat`
--

CREATE TABLE `estat` (
  `id_estat` int(11) NOT NULL,
  `nom_estat` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `genere`
--

CREATE TABLE `genere` (
  `id_genere` int(11) NOT NULL,
  `descrip` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `idioma`
--

CREATE TABLE `idioma` (
  `id_idioma` int(11) NOT NULL,
  `nom_idioma` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `llibre`
--

CREATE TABLE `llibre` (
  `ISBN` bigint(13) NOT NULL,
  `titol` varchar(255) NOT NULL,
  `id_autor` int(11) NOT NULL,
  `id_editorial` int(11) NOT NULL,
  `id_idioma` int(11) NOT NULL,
  `id_genere` int(11) NOT NULL,
  `edicio` int(11) NOT NULL,
  `stock` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `llibres_prestec`
--

CREATE TABLE `llibres_prestec` (
  `id_prestec` int(11) NOT NULL,
  `ISBN` bigint(13) NOT NULL,
  `id_usuari` int(11) NOT NULL,
  `id_treballador` int(11) NOT NULL,
  `data_start` date NOT NULL,
  `data_end` date NOT NULL,
  `id_estat` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `treballador`
--

CREATE TABLE `treballador` (
  `id_treballador` int(11) NOT NULL,
  `dni` varchar(8) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `cognom` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `isAdmin` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuari`
--

CREATE TABLE `usuari` (
  `id_usuari` int(11) NOT NULL,
  `dni` varchar(8) NOT NULL,
  `nom_usuari` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `autor`
--
ALTER TABLE `autor`
  ADD PRIMARY KEY (`id_autor`);

--
-- Indices de la tabla `editorial`
--
ALTER TABLE `editorial`
  ADD PRIMARY KEY (`id_editorial`);

--
-- Indices de la tabla `estat`
--
ALTER TABLE `estat`
  ADD PRIMARY KEY (`id_estat`);

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
  ADD KEY `autor_fk` (`id_autor`),
  ADD KEY `editorial_fk` (`id_editorial`),
  ADD KEY `genere_fk` (`id_genere`),
  ADD KEY `idioma_fk` (`id_idioma`);

--
-- Indices de la tabla `llibres_prestec`
--
ALTER TABLE `llibres_prestec`
  ADD PRIMARY KEY (`id_prestec`),
  ADD KEY `llibre_fk` (`ISBN`),
  ADD KEY `usuari_fk` (`id_usuari`),
  ADD KEY `treballador_fk` (`id_treballador`),
  ADD KEY `estat_fk` (`id_estat`);

--
-- Indices de la tabla `treballador`
--
ALTER TABLE `treballador`
  ADD PRIMARY KEY (`id_treballador`),
  ADD UNIQUE KEY `dni` (`dni`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indices de la tabla `usuari`
--
ALTER TABLE `usuari`
  ADD PRIMARY KEY (`id_usuari`),
  ADD UNIQUE KEY `dni` (`dni`,`email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `autor`
--
ALTER TABLE `autor`
  MODIFY `id_autor` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `editorial`
--
ALTER TABLE `editorial`
  MODIFY `id_editorial` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `estat`
--
ALTER TABLE `estat`
  MODIFY `id_estat` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `genere`
--
ALTER TABLE `genere`
  MODIFY `id_genere` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `idioma`
--
ALTER TABLE `idioma`
  MODIFY `id_idioma` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `llibres_prestec`
--
ALTER TABLE `llibres_prestec`
  MODIFY `id_prestec` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `treballador`
--
ALTER TABLE `treballador`
  MODIFY `id_treballador` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuari`
--
ALTER TABLE `usuari`
  MODIFY `id_usuari` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `llibre`
--
ALTER TABLE `llibre`
  ADD CONSTRAINT `autor_fk` FOREIGN KEY (`id_autor`) REFERENCES `autor` (`id_autor`),
  ADD CONSTRAINT `editorial_fk` FOREIGN KEY (`id_editorial`) REFERENCES `editorial` (`id_editorial`),
  ADD CONSTRAINT `genere_fk` FOREIGN KEY (`id_genere`) REFERENCES `genere` (`id_genere`),
  ADD CONSTRAINT `idioma_fk` FOREIGN KEY (`id_idioma`) REFERENCES `idioma` (`id_idioma`);

--
-- Filtros para la tabla `llibres_prestec`
--
ALTER TABLE `llibres_prestec`
  ADD CONSTRAINT `estat_fk` FOREIGN KEY (`id_estat`) REFERENCES `estat` (`id_estat`),
  ADD CONSTRAINT `llibre_fk` FOREIGN KEY (`ISBN`) REFERENCES `llibre` (`ISBN`),
  ADD CONSTRAINT `treballador_fk` FOREIGN KEY (`id_treballador`) REFERENCES `treballador` (`id_treballador`),
  ADD CONSTRAINT `usuari_fk` FOREIGN KEY (`id_usuari`) REFERENCES `usuari` (`id_usuari`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
