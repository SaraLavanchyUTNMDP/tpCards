-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 07-04-2018 a las 01:02:58
-- Versión del servidor: 10.1.26-MariaDB
-- Versión de PHP: 7.1.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `tpcards`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `addcard` (IN `vnumber` INT, IN `type` VARCHAR(20))  BEGIN
	declare vidtype int;
    set vidtype= (select id_typeofcard from typeofcard t where t.typeofcard like type limit 1);    
   IF NOT EXISTS (SELECT id_card 
   	from card c 	
    where c.number=vnumber and c.fk_idtypeofcard=vidtype)
   then
   insert into card (fk_idtypeofcard, number) values (vidtype, vnumber);
   end if;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `addHand` (IN `vtype` VARCHAR(20), IN `vidplayer` INT, IN `vidmatch` INT, IN `vnumber` INT)  BEGIN
	declare VidCard int;
    declare vmatchplayer int;
    set VidCard = (select id_card 
    				from card c
                    inner join typeofcard toc
                    on c.fk_idtypeofcard=toc.id_typeofcard
                    where toc.typeofcard like Vtype
                   			and c.number=vnumber
                    limit 1);
    set vmatchplayer = (select id_matchplayer 
                        from matchplayer m
                       where m.fk_idplayer=vidplayer 
                        and m.fk_idmatch=vidmatch
                       limit 1	
                       );
   	INSERT into hand (fk_idcard, fk_idmatchplayer) values (vidCard,vmatchplayer);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `addMatchPlayer` (IN `vname` VARCHAR(50), IN `vid_match` INT, IN `vpoints` INT)  BEGIN
	declare vidplayer int;
    set vidplayer = (select id_player from player where name like vname);
	
    if not exists(select mp.fk_idmatch from matchplayer mp 
             where mp.fk_idplayer = vidplayer AND
             mp.fk_idmatch = vid_match)
    then
		insert into matchplayer (fk_idmatch, fk_idplayer, points)
    	VALUES (vid_match, vidplayer, vpoints);
    end if;

end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `insertplayer` (IN `vname` VARCHAR(50), OUT `vidplayer` INT)  BEGIN

	if EXISTS (select id_player from player where name like vname)
    	then set vidplayer=(select id_player from player where name like vname);
    ELSE
    	insert into player (name) values (vname);
        set vidplayer = LAST_INSERT_ID();
    end if;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `newmatch` (IN `name` VARCHAR(50), IN `vpoints` INT)  BEGIN	
	declare idplayer int;
    declare idmatch int;
    call insertplayer(name,@idplayer);
    insert into matchs(winner) values (@idplayer);
    set idmatch = LAST_INSERT_ID();
    insert into matchplayer(fk_idmatch, fk_idplayer, points) 
    	values (idmatch, @idplayer, vpoints); 
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `card`
--

CREATE TABLE `card` (
  `id_card` int(11) NOT NULL,
  `fk_idtypeofcard` int(11) NOT NULL,
  `number` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `card`
--

INSERT INTO `card` (`id_card`, `fk_idtypeofcard`, `number`) VALUES
(1, 3, 11),
(2, 2, 11),
(3, 2, 12),
(4, 3, 10),
(5, 4, 8),
(6, 1, 10),
(7, 3, 6),
(8, 4, 3),
(9, 3, 4),
(10, 2, 4),
(11, 4, 4),
(12, 2, 10),
(13, 4, 12),
(14, 1, 11),
(15, 4, 1),
(16, 2, 2),
(17, 1, 4),
(18, 1, 9),
(19, 3, 9),
(20, 2, 6),
(21, 2, 1),
(22, 1, 1),
(23, 2, 9),
(24, 4, 7),
(25, 1, 2),
(26, 4, 2),
(27, 2, 5),
(28, 3, 8),
(29, 1, 12),
(30, 2, 7),
(31, 3, 12),
(32, 3, 1),
(33, 3, 5),
(34, 1, 3),
(35, 1, 8),
(36, 4, 9),
(37, 1, 6),
(38, 3, 2),
(39, 2, 8),
(40, 3, 7),
(41, 2, 3),
(42, 4, 10),
(43, 1, 7),
(44, 4, 5),
(45, 1, 5),
(46, 4, 6),
(47, 4, 11),
(48, 3, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hand`
--

CREATE TABLE `hand` (
  `fk_idcard` int(11) NOT NULL,
  `fk_idmatchplayer` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `hand`
--

INSERT INTO `hand` (`fk_idcard`, `fk_idmatchplayer`) VALUES
(3, 28),
(14, 28),
(16, 28),
(17, 28),
(18, 28),
(19, 28),
(23, 28),
(27, 28),
(33, 28),
(36, 28),
(39, 28),
(40, 28),
(45, 28);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `matchplayer`
--

CREATE TABLE `matchplayer` (
  `id_matchplayer` int(11) NOT NULL,
  `fk_idplayer` int(11) NOT NULL,
  `fk_idmatch` int(11) NOT NULL,
  `points` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `matchplayer`
--

INSERT INTO `matchplayer` (`id_matchplayer`, `fk_idplayer`, `fk_idmatch`, `points`) VALUES
(28, 49, 26, 95),
(29, 50, 26, 70),
(30, 51, 26, 79),
(31, 52, 26, 60);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `matchs`
--

CREATE TABLE `matchs` (
  `id_match` int(11) NOT NULL,
  `winner` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `matchs`
--

INSERT INTO `matchs` (`id_match`, `winner`) VALUES
(26, 49);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `player`
--

CREATE TABLE `player` (
  `id_player` int(11) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `player`
--

INSERT INTO `player` (`id_player`, `name`) VALUES
(49, 'rodrigo'),
(50, 'marcos'),
(51, 'sara'),
(52, 'camila');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `typeofcard`
--

CREATE TABLE `typeofcard` (
  `id_typeofcard` int(11) NOT NULL,
  `typeofcard` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `typeofcard`
--

INSERT INTO `typeofcard` (`id_typeofcard`, `typeofcard`) VALUES
(1, 'GOLD'),
(2, 'WOOD'),
(3, 'HEART'),
(4, 'CUP');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `card`
--
ALTER TABLE `card`
  ADD PRIMARY KEY (`id_card`),
  ADD KEY `fk_idtypeofcard` (`fk_idtypeofcard`);

--
-- Indices de la tabla `hand`
--
ALTER TABLE `hand`
  ADD PRIMARY KEY (`fk_idcard`,`fk_idmatchplayer`),
  ADD KEY `fk_idmatchplayer` (`fk_idmatchplayer`);

--
-- Indices de la tabla `matchplayer`
--
ALTER TABLE `matchplayer`
  ADD PRIMARY KEY (`id_matchplayer`),
  ADD KEY `fk_idplayer` (`fk_idplayer`),
  ADD KEY `fk_idmatch` (`fk_idmatch`);

--
-- Indices de la tabla `matchs`
--
ALTER TABLE `matchs`
  ADD PRIMARY KEY (`id_match`),
  ADD KEY `winner` (`winner`);

--
-- Indices de la tabla `player`
--
ALTER TABLE `player`
  ADD PRIMARY KEY (`id_player`);

--
-- Indices de la tabla `typeofcard`
--
ALTER TABLE `typeofcard`
  ADD PRIMARY KEY (`id_typeofcard`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `card`
--
ALTER TABLE `card`
  MODIFY `id_card` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;
--
-- AUTO_INCREMENT de la tabla `matchplayer`
--
ALTER TABLE `matchplayer`
  MODIFY `id_matchplayer` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;
--
-- AUTO_INCREMENT de la tabla `matchs`
--
ALTER TABLE `matchs`
  MODIFY `id_match` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;
--
-- AUTO_INCREMENT de la tabla `player`
--
ALTER TABLE `player`
  MODIFY `id_player` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=53;
--
-- AUTO_INCREMENT de la tabla `typeofcard`
--
ALTER TABLE `typeofcard`
  MODIFY `id_typeofcard` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `card`
--
ALTER TABLE `card`
  ADD CONSTRAINT `card_ibfk_1` FOREIGN KEY (`fk_idtypeofcard`) REFERENCES `typeofcard` (`id_typeofcard`);

--
-- Filtros para la tabla `hand`
--
ALTER TABLE `hand`
  ADD CONSTRAINT `hand_ibfk_1` FOREIGN KEY (`fk_idmatchplayer`) REFERENCES `matchplayer` (`id_matchplayer`),
  ADD CONSTRAINT `hand_ibfk_2` FOREIGN KEY (`fk_idcard`) REFERENCES `card` (`id_card`);

--
-- Filtros para la tabla `matchplayer`
--
ALTER TABLE `matchplayer`
  ADD CONSTRAINT `matchplayer_ibfk_1` FOREIGN KEY (`fk_idplayer`) REFERENCES `player` (`id_player`),
  ADD CONSTRAINT `matchplayer_ibfk_2` FOREIGN KEY (`fk_idmatch`) REFERENCES `matchs` (`id_match`);

--
-- Filtros para la tabla `matchs`
--
ALTER TABLE `matchs`
  ADD CONSTRAINT `matchs_ibfk_1` FOREIGN KEY (`winner`) REFERENCES `player` (`id_player`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
