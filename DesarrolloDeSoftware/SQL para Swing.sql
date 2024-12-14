-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 13-12-2024 a las 14:15:45
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `tp_8`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `bebida`
--

CREATE TABLE `bebida` (
  `nombre` varchar(45) NOT NULL,
  `descripcion` varchar(128) DEFAULT NULL,
  `precio` double NOT NULL,
  `id_categoria` bigint(5) NOT NULL,
  `graduacionAlcoholica` double DEFAULT NULL,
  `volumen` double NOT NULL,
  `id_itemMenu` bigint(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `bebida`
--

INSERT INTO `bebida` (`nombre`, `descripcion`, `precio`, `id_categoria`, `graduacionAlcoholica`, `volumen`, `id_itemMenu`) VALUES
('GASEOSA', 'COCA-COLA/SPRITE/FANTA', 2500, 5, 0, 250, 61),
('JUGO', 'DE NARANJA', 1500, 6, 0, 150, 62),
('LIMONADA', 'CON MENTA YJENGIBRE', 3500, 6, 0, 1000, 63),
('TE VERDE', 'CON LIMON', 1250, 7, 0, 100, 64),
('CAFE ', 'CON LECHE', 2500, 7, 0, 100, 65),
('CAPUCCINO', '', 4500, 7, 0, 150, 66),
('CERVEZA SANTA FE', '', 4500, 8, 25, 1000, 67),
('CERVEZA QUILMES', '', 4250, 8, 30, 1000, 68),
('APEROL', '', 2500, 10, 35, 250, 69),
('CAIPIRINHA', '', 3500, 10, 25, 250, 70);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `id_categoria` bigint(5) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `tipo_item` varchar(255) NOT NULL,
  `item` enum('BEBIDA','COMIDA') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`id_categoria`, `descripcion`, `tipo_item`, `item`) VALUES
(1, 'ENTRADA', 'PLATO', 'COMIDA'),
(2, 'PLATO PRINCIPAL', 'PLATO', 'COMIDA'),
(3, 'GUARNICION', 'PLATO', 'COMIDA'),
(4, 'POSTRE', 'PLATO', 'COMIDA'),
(5, 'GASEOSA', 'BEBIDA', 'BEBIDA'),
(6, 'JUGO', 'BEBIDA', 'BEBIDA'),
(7, 'INFUSION', 'BEBIDA', 'BEBIDA'),
(8, 'CERVEZA', 'BEBIDA', 'BEBIDA'),
(9, 'VINO', 'BEBIDA', 'BEBIDA'),
(10, 'BEBIDA ALCOHOLICA', 'BEBIDA', 'BEBIDA');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `id_cliente` bigint(5) NOT NULL,
  `cuit` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `alias` varchar(255) DEFAULT NULL,
  `cbu` varchar(255) DEFAULT NULL,
  `id_coordenada` bigint(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`id_cliente`, `cuit`, `email`, `direccion`, `alias`, `cbu`, `id_coordenada`) VALUES
(9, '20123456789', 'juan.perez@gmail.com', 'Av. Rivadavia 123', 'juan.perez123', '0123456789', 35),
(10, '27876543213', 'maria.gonzalez@hotmail.com', 'San Martin', 'maria.gonzalez.mp', '0987654321', 36),
(11, '23556677880', 'lucas.martinez@gmail.com', 'Av. Mitre 789', 'lucas.martinez.cuenta', '56489952', 37),
(12, '26334455668', 'carolina.lopez@outlook.es', 'Calle Belgrano 321', 'carolina.lopez.dolar', '45678912', 38),
(13, '27998877661', 'valeria.gomez@hotmail.es', 'Calle Guemes 234', 'carolina.lopez.dolar', '000015458879923', 39);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `coordenada`
--

CREATE TABLE `coordenada` (
  `id` bigint(20) NOT NULL,
  `latitud` double NOT NULL,
  `longitud` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `coordenadas`
--

CREATE TABLE `coordenadas` (
  `id_coordenada` bigint(5) NOT NULL,
  `longitud` bigint(10) NOT NULL,
  `latitud` bigint(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `coordenadas`
--

INSERT INTO `coordenadas` (`id_coordenada`, `longitud`, `latitud`) VALUES
(35, -35, -58),
(36, 31, 25),
(37, -32, -68),
(38, -24, -65),
(39, 23, 45),
(40, 42, -37),
(41, 12, -21),
(42, 15, -55),
(43, 14, -35),
(44, 45, -39),
(45, 13, -12);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `itemmenu`
--

CREATE TABLE `itemmenu` (
  `id_itemMenu` bigint(5) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `descripcion` varchar(128) DEFAULT NULL,
  `precio` double NOT NULL,
  `id_categoria` bigint(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `itemmenu`
--

INSERT INTO `itemmenu` (`id_itemMenu`, `nombre`, `descripcion`, `precio`, `id_categoria`) VALUES
(46, 'EMPANADA', 'DE CARNE', 1500, 1),
(47, 'TABLA', 'DE QUESOS', 10000, 1),
(48, 'PROVOLETA', 'AL HORNO', 12000, 1),
(49, 'BASTONCITOS', 'RELLENOS DE QUESO', 8500, 1),
(50, 'BIFE DE CHORIZO', 'C/GUARNICION', 16000, 2),
(51, 'RISOTTO', 'DE HONGOS', 20000, 2),
(52, 'PAELLA ', 'DE MARISCOS', 22000, 2),
(53, 'PAPAS', 'FRITAS', 7000, 3),
(54, 'PAPAS', 'RUSTICAS', 6000, 3),
(55, 'PURE', 'DE PAPA', 5500, 3),
(56, 'ENSALADA', 'MIXTA', 3500, 3),
(57, 'FLAN CASERO', 'CON DULCE DE LECHE', 6500, 4),
(58, 'TIRAMISU', 'CLASICO', 8500, 4),
(59, 'HELADO', 'ARTESANAL', 3500, 4),
(61, 'GASEOSA', 'COCA-COLA/SPRITE/FANTA', 2500, 5),
(62, 'JUGO', 'DE NARANJA', 1500, 5),
(63, 'LIMONADA', 'CON MENTA YJENGIBRE', 3500, 5),
(64, 'TE VERDE', 'CON LIMON', 1250, 7),
(65, 'CAFE ', 'CON LECHE', 2500, 7),
(66, 'CAPUCCINO', '', 4500, 7),
(67, 'CERVEZA SANTA FE', '', 4500, 8),
(68, 'CERVEZA QUILMES', '', 4250, 8),
(69, 'APEROL', '', 2500, 10),
(70, 'CAIPIRINHA', '', 3500, 10);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pago`
--

CREATE TABLE `pago` (
  `id_pago` bigint(5) NOT NULL,
  `monto` double NOT NULL,
  `fecha` date DEFAULT NULL,
  `dtype` varchar(31) DEFAULT NULL,
  `alias` varchar(255) DEFAULT NULL,
  `recargo` double DEFAULT NULL,
  `cbu` varchar(255) DEFAULT NULL,
  `cuit` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pago`
--

INSERT INTO `pago` (`id_pago`, `monto`, `fecha`, `dtype`, `alias`, `recargo`, `cbu`, `cuit`) VALUES
(48, 22500, '2024-12-13', NULL, NULL, NULL, NULL, NULL),
(49, 14250, '2024-12-13', NULL, NULL, NULL, NULL, NULL),
(50, 30000, '2024-12-13', NULL, NULL, NULL, NULL, NULL),
(51, 30000, '2024-12-13', NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pagopormp`
--

CREATE TABLE `pagopormp` (
  `monto` double NOT NULL,
  `fecha` date NOT NULL,
  `recargo` double DEFAULT NULL,
  `alias` varchar(45) NOT NULL,
  `id_pago` bigint(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pagopormp`
--

INSERT INTO `pagopormp` (`monto`, `fecha`, `recargo`, `alias`, `id_pago`) VALUES
(22500, '2024-12-13', 900, 'juan.perez123', 48),
(30000, '2024-12-13', 1200, 'carolina.lopez.dolar', 51);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pagoportransferencia`
--

CREATE TABLE `pagoportransferencia` (
  `monto` double NOT NULL,
  `fecha` date DEFAULT NULL,
  `recargo` double DEFAULT NULL,
  `cbu` varchar(15) NOT NULL,
  `cuit` varchar(15) NOT NULL,
  `id_pago` bigint(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pagoportransferencia`
--

INSERT INTO `pagoportransferencia` (`monto`, `fecha`, `recargo`, `cbu`, `cuit`, `id_pago`) VALUES
(14250, '2024-12-13', 285, '0987654321', '27876543213', 49),
(30000, '2024-12-13', 600, '56489952', '23556677880', 50);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedido`
--

CREATE TABLE `pedido` (
  `id_pedido` bigint(5) NOT NULL,
  `estado` varchar(255) NOT NULL,
  `id_cliente` bigint(5) NOT NULL,
  `id_vendedor` bigint(5) NOT NULL,
  `id_pago` bigint(5) NOT NULL,
  `metodo_pago` varchar(255) DEFAULT NULL,
  `metodo_de_pago` varchar(255) DEFAULT NULL,
  `cliente_id` bigint(20) DEFAULT NULL,
  `pago_id` bigint(20) DEFAULT NULL,
  `vendedor_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pedido`
--

INSERT INTO `pedido` (`id_pedido`, `estado`, `id_cliente`, `id_vendedor`, `id_pago`, `metodo_pago`, `metodo_de_pago`, `cliente_id`, `pago_id`, `vendedor_id`) VALUES
(34, 'PENDIENTE', 9, 14, 48, 'MP', NULL, NULL, NULL, NULL),
(35, 'PENDIENTE', 10, 15, 49, 'Por Transferencia', NULL, NULL, NULL, NULL),
(36, 'PENDIENTE', 11, 16, 50, 'Por Transferencia', NULL, NULL, NULL, NULL),
(37, 'PENDIENTE', 12, 17, 51, 'MP', NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedido_itemmenu`
--

CREATE TABLE `pedido_itemmenu` (
  `id_pedido` bigint(5) NOT NULL,
  `id_itemMenu` bigint(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pedido_itemmenu`
--

INSERT INTO `pedido_itemmenu` (`id_pedido`, `id_itemMenu`) VALUES
(34, 51),
(34, 61),
(35, 56),
(35, 57),
(35, 68),
(36, 50),
(36, 53),
(36, 56),
(36, 59),
(37, 50),
(37, 53),
(37, 56),
(37, 59);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `plato`
--

CREATE TABLE `plato` (
  `nombre` varchar(45) NOT NULL,
  `descripcion` varchar(128) DEFAULT NULL,
  `precio` double NOT NULL,
  `id_categoria` bigint(5) NOT NULL,
  `calorias` double NOT NULL,
  `aptoCeliaco` tinyint(4) DEFAULT NULL,
  `aptoVegetariano` tinyint(4) DEFAULT NULL,
  `peso` double NOT NULL,
  `id_itemMenu` bigint(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `plato`
--

INSERT INTO `plato` (`nombre`, `descripcion`, `precio`, `id_categoria`, `calorias`, `aptoCeliaco`, `aptoVegetariano`, `peso`, `id_itemMenu`) VALUES
('EMPANADA', 'DE CARNE', 1500, 1, 455, 1, 0, 456, 46),
('TABLA', 'DE QUESOS', 10000, 1, 500, 1, 0, 800, 47),
('PROVOLETA', 'AL HORNO', 12000, 1, 850, 1, 1, 1000, 48),
('BASTONCITOS', 'RELLENOS DE QUESO', 8500, 1, 1200, 1, 1, 1200, 49),
('BIFE DE CHORIZO', 'C/GUARNICION', 16000, 2, 2000, 1, 1, 900, 50),
('RISOTTO', 'DE HONGOS', 20000, 2, 1100, 1, 0, 550, 51),
('PAELLA ', 'DE MARISCOS', 22000, 2, 1500, 1, 1, 700, 52),
('PAPAS', 'FRITAS', 7000, 3, 700, 1, 1, 1000, 53),
('PAPAS', 'RUSTICAS', 6000, 3, 750, 1, 1, 1000, 54),
('PURE', 'DE PAPA', 5500, 3, 560, 1, 1, 890, 55),
('ENSALADA', 'MIXTA', 3500, 3, 560, 1, 1, 530, 56),
('FLAN CASERO', 'CON DULCE DE LECHE', 6500, 4, 780, 1, 1, 250, 57),
('TIRAMISU', 'CLASICO', 8500, 4, 500, 1, 1, 150, 58),
('HELADO', 'ARTESANAL', 3500, 4, 550, 1, 1, 500, 59);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vendedor`
--

CREATE TABLE `vendedor` (
  `id_vendedor` bigint(5) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `id_coordenada` bigint(5) NOT NULL,
  `coordenadas_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `vendedor`
--

INSERT INTO `vendedor` (`id_vendedor`, `nombre`, `direccion`, `id_coordenada`, `coordenadas_id`) VALUES
(14, 'Laura Martinez', 'Av. San Juan 236', 40, NULL),
(15, 'Javier Lopez', 'Calle Mendoza 567', 41, NULL),
(16, 'Mariana Gomez', 'Ruta Nacional 40, Km 300', 42, NULL),
(17, 'Natalia Torres', 'Av. Libertador 4321', 43, NULL),
(18, 'Matias Fernandez', 'Av. Mitre 1010', 44, NULL),
(19, 'Diego Benitez', 'Calle Rivadavia 234', 45, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vendedor_itemmenu`
--

CREATE TABLE `vendedor_itemmenu` (
  `id_vendedor` bigint(5) NOT NULL,
  `id_itemMenu` bigint(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `vendedor_itemmenu`
--

INSERT INTO `vendedor_itemmenu` (`id_vendedor`, `id_itemMenu`) VALUES
(14, 49),
(14, 51),
(14, 52),
(14, 61),
(15, 46),
(15, 54),
(15, 56),
(15, 57),
(15, 68),
(16, 50),
(16, 52),
(16, 53),
(16, 56),
(16, 59),
(16, 63),
(17, 47),
(17, 49),
(17, 50),
(17, 51),
(17, 52),
(17, 55),
(17, 56),
(17, 59),
(17, 62),
(17, 63),
(18, 47),
(18, 49),
(18, 50),
(18, 53),
(18, 57),
(18, 62),
(18, 65),
(19, 48),
(19, 53),
(19, 54),
(19, 63),
(19, 64),
(19, 65),
(19, 70);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vendedor_pedidos`
--

CREATE TABLE `vendedor_pedidos` (
  `vendedor_id` bigint(20) NOT NULL,
  `pedidos_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `bebida`
--
ALTER TABLE `bebida`
  ADD PRIMARY KEY (`id_itemMenu`),
  ADD KEY `fk_Bebida_ItemMenu1_idx` (`id_itemMenu`);

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`id_categoria`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id_cliente`),
  ADD UNIQUE KEY `cuit_UNIQUE` (`cuit`),
  ADD KEY `fk_Cliente_Coordenada1_idx` (`id_coordenada`);

--
-- Indices de la tabla `coordenada`
--
ALTER TABLE `coordenada`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `coordenadas`
--
ALTER TABLE `coordenadas`
  ADD PRIMARY KEY (`id_coordenada`),
  ADD UNIQUE KEY `id_coordenada_UNIQUE` (`id_coordenada`);

--
-- Indices de la tabla `itemmenu`
--
ALTER TABLE `itemmenu`
  ADD PRIMARY KEY (`id_itemMenu`),
  ADD KEY `fk_ItemMenu_Categoria1_idx` (`id_categoria`);

--
-- Indices de la tabla `pago`
--
ALTER TABLE `pago`
  ADD PRIMARY KEY (`id_pago`);

--
-- Indices de la tabla `pagopormp`
--
ALTER TABLE `pagopormp`
  ADD PRIMARY KEY (`id_pago`),
  ADD KEY `fk_PagoPorMP_Pago1_idx` (`id_pago`);

--
-- Indices de la tabla `pagoportransferencia`
--
ALTER TABLE `pagoportransferencia`
  ADD PRIMARY KEY (`id_pago`),
  ADD KEY `fk_PagoPorTransferencia_Pago1_idx` (`id_pago`);

--
-- Indices de la tabla `pedido`
--
ALTER TABLE `pedido`
  ADD PRIMARY KEY (`id_pedido`),
  ADD KEY `fk_Pedido_Cliente1_idx` (`id_cliente`),
  ADD KEY `fk_Pedido_Vendedor1_idx` (`id_vendedor`),
  ADD KEY `fk_Pedido_Pago1_idx` (`id_pago`);

--
-- Indices de la tabla `pedido_itemmenu`
--
ALTER TABLE `pedido_itemmenu`
  ADD PRIMARY KEY (`id_pedido`,`id_itemMenu`),
  ADD KEY `fk_Pedido_has_ItemMenu_ItemMenu1_idx` (`id_itemMenu`),
  ADD KEY `fk_Pedido_has_ItemMenu_Pedido1_idx` (`id_pedido`);

--
-- Indices de la tabla `plato`
--
ALTER TABLE `plato`
  ADD PRIMARY KEY (`id_itemMenu`),
  ADD KEY `fk_Plato_ItemMenu1_idx` (`id_itemMenu`);

--
-- Indices de la tabla `vendedor`
--
ALTER TABLE `vendedor`
  ADD PRIMARY KEY (`id_vendedor`),
  ADD UNIQUE KEY `UK1wdejfi9beapwmnffahee3oba` (`coordenadas_id`),
  ADD KEY `fk_Vendedor_Coordenada_idx` (`id_coordenada`);

--
-- Indices de la tabla `vendedor_itemmenu`
--
ALTER TABLE `vendedor_itemmenu`
  ADD PRIMARY KEY (`id_vendedor`,`id_itemMenu`),
  ADD KEY `fk_Vendedor_has_ItemMenu_ItemMenu1_idx` (`id_itemMenu`),
  ADD KEY `fk_Vendedor_has_ItemMenu_Vendedor1_idx` (`id_vendedor`);

--
-- Indices de la tabla `vendedor_pedidos`
--
ALTER TABLE `vendedor_pedidos`
  ADD UNIQUE KEY `UKskqmxbo6a9l7q0222gikkfp85` (`pedidos_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `id_categoria` bigint(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id_cliente` bigint(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `coordenada`
--
ALTER TABLE `coordenada`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `coordenadas`
--
ALTER TABLE `coordenadas`
  MODIFY `id_coordenada` bigint(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;

--
-- AUTO_INCREMENT de la tabla `itemmenu`
--
ALTER TABLE `itemmenu`
  MODIFY `id_itemMenu` bigint(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=71;

--
-- AUTO_INCREMENT de la tabla `pago`
--
ALTER TABLE `pago`
  MODIFY `id_pago` bigint(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT de la tabla `pedido`
--
ALTER TABLE `pedido`
  MODIFY `id_pedido` bigint(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT de la tabla `vendedor`
--
ALTER TABLE `vendedor`
  MODIFY `id_vendedor` bigint(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `bebida`
--
ALTER TABLE `bebida`
  ADD CONSTRAINT `fk_Bebida_ItemMenu1` FOREIGN KEY (`id_itemMenu`) REFERENCES `itemmenu` (`id_itemMenu`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `fk_Cliente_Coordenada1` FOREIGN KEY (`id_coordenada`) REFERENCES `coordenadas` (`id_coordenada`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `itemmenu`
--
ALTER TABLE `itemmenu`
  ADD CONSTRAINT `fk_ItemMenu_Categoria1` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id_categoria`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `pagopormp`
--
ALTER TABLE `pagopormp`
  ADD CONSTRAINT `fk_PagoPorMP_Pago1` FOREIGN KEY (`id_pago`) REFERENCES `pago` (`id_pago`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `pagoportransferencia`
--
ALTER TABLE `pagoportransferencia`
  ADD CONSTRAINT `fk_PagoPorTransferencia_Pago1` FOREIGN KEY (`id_pago`) REFERENCES `pago` (`id_pago`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `pedido`
--
ALTER TABLE `pedido`
  ADD CONSTRAINT `fk_Pedido_Cliente1` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Pedido_Pago1` FOREIGN KEY (`id_pago`) REFERENCES `pago` (`id_pago`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Pedido_Vendedor1` FOREIGN KEY (`id_vendedor`) REFERENCES `vendedor` (`id_vendedor`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `pedido_itemmenu`
--
ALTER TABLE `pedido_itemmenu`
  ADD CONSTRAINT `fk_Pedido_has_ItemMenu_ItemMenu1` FOREIGN KEY (`id_itemMenu`) REFERENCES `itemmenu` (`id_itemMenu`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Pedido_has_ItemMenu_Pedido1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id_pedido`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `plato`
--
ALTER TABLE `plato`
  ADD CONSTRAINT `fk_Plato_ItemMenu1` FOREIGN KEY (`id_itemMenu`) REFERENCES `itemmenu` (`id_itemMenu`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `vendedor`
--
ALTER TABLE `vendedor`
  ADD CONSTRAINT `FKb38jjduf0t9kqk5ye22n4iama` FOREIGN KEY (`coordenadas_id`) REFERENCES `coordenada` (`id`),
  ADD CONSTRAINT `fk_Vendedor_Coordenada` FOREIGN KEY (`id_coordenada`) REFERENCES `coordenadas` (`id_coordenada`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `vendedor_itemmenu`
--
ALTER TABLE `vendedor_itemmenu`
  ADD CONSTRAINT `fk_Vendedor_has_ItemMenu_ItemMenu1` FOREIGN KEY (`id_itemMenu`) REFERENCES `itemmenu` (`id_itemMenu`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Vendedor_has_ItemMenu_Vendedor1` FOREIGN KEY (`id_vendedor`) REFERENCES `vendedor` (`id_vendedor`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
