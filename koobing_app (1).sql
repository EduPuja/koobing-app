-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 08-05-2023 a las 15:21:54
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
(9788423354272, 4, 4, 1, 4, 'El Perfume', 5, '1985-01-01');

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
(26, 0x89504e470d0a1a0a0000000d49484452000000700000007b0806000000ac2704880000016f69434350696363000028917591bd4bc35014c54fbfa8d44a051d441c325471684114c4512ad8a53ab415acba24af492b2469784991e22ab838141c4417bf06ff035d055705415004113777bf1629f1bea6d022ed7bbcdc1f27ef5c6e4e007f4667861d9c070cd3e1d9744a5a2dac49e1778410c420ed88cc6c6b29b79847cff5f3089fa80f49d1abf7bdaeabbfa8da0cf0f511cf328b3bc4340d325b8e25788f789895e522f1097182d380c4b742573c7e135cf2f84b30cf671700bfe829953a58e96056e606f12471dcd0abac358ff892a86aaee4a88ed219838d2cd2484182822a36a1c34192aa499975f74d357dcba89087d1d3420d9c1c2594c99b20b54a5d55aa1ae92a6d1d3591fbff3c6d6d66daeb1e4d01a157d7fd1c07c2fb40a3eebabfa7aedb3803022fc0b5d9f65728a7b96fd2eb6d2d7e0cc47680cb9bb6a61c0057bbc0c8b32573b92905e8f8350df8b800060ac0d03d1059f7b26abdc7f91390dfa65f74071c1e0113743fb6f107bdca67eb10ae4584000000097048597300000b1200000b1201d2dd7efc00001d2c49444154785eed5d097c54d5d53fc94cf63d844020901040200988800a2d4b5c3e3e684bc51f686b71ab55d40fb5b65ab54afdb0566bd5d68a055c3eeda7a2284bcb52852a5b4096b219c8aa90404842424266b2efc9f4feefe40e6f266fe6bd99375b0af7f71b8933f7dd7bcef9df73ee39f79e7b1fd1255c56bebedc346bdc68d3252c82fec1bac9644aede8e8c83a53527af73f36acdffdc43d779aa62545f3cfc3f7fe8c0388ef0126fe65f5e7b1fa43fa037701fd814835340224a3d198da50d7985a79e142566dd9e9d4fada9ad4fad6ce9433c78f505ece51325696f1a6a20624923e3884ffbde88187e8b3f5ebe874ee116aae6ba0cdf9a5c49ea7ed1bd6d1b6bfbe448b9e594efff3f347fc564e7e4b9823d09826653121df5d927328b5a4b834b5f0eb43290dcdedd4d35a475d1dedd4de1d401d75e72d60e10f01986dbba82ffd3d36611007132057159fa21757af2ff8c18285196a06912feaf41b00196819fbb67fbe8269cbac0be7ce52636db52a80b4081560aef87443665c5c5cbe96763cf9acde938dbba36d00f7e15babd6def5fd1bd3a155c2fcc5250d7347f376db8066eac2a3283434d4a3fd686ddc6f35d06030647cf2e1eab51bdf599eeec8046a1580a3e701e298a9b3e8b9975fc98c8f8ff74b2df44b000f1c3a9affc6d25fa5632ef2b4a6290d0080181c33889e796d79c1b46b26fbdd5ce877003237def4c243f758798a4a42f6f4ef001173ee337f798f9843e357320bf434f3ceb48fc0fae9db1772adb3e7353ad39ebbea8216d004da9e7ff229bf0afcfd663441309f7fb0d2e726530974c49233e6cea797dffbc02f64e7171af8d1dbabf2d72d7fc9efc103b8d0c4bd5b379258c15102dcd3bffb7c14214cc84a4bc94b9fe477fe8143d99717e6fb4590ef7300bf372943d59c525ddb4811e1e6e52f7794e616f30a8c28687bc4e0688a8e8da2e34515141116acd84d7d793e6d2932f834c4f06920cf3cce7c789c8e4285e6d60eba72ec507ae28e59547cbede4aa81dcdad8a4216158223c2f89fb1217a0a090da1c8503de9d9bf71d1e1fcfba484180ad2eb48a70ba49d070a69d99bdb28714094c3f67b74d184589555f299f9f09906b285e78c45374ccf73242101deb207be47a12141aac1525bb1bbbbc752b5b3abdbf237fa7afda35db4edab42454d048ddb8e9dc80c0b0bf349a0ef330ddcb773471ee691e471f283178281490378d08cb6f64eb5b868ae87beee5f389ded4a34289a53e39993b4e3f3cf30107da20c5ef7422b0d2d196fefac31adfae0731a3c7294acb001de1036173df7f04d1c3ca9766846476503e8178367cef47184f9d75e010fe065fde13a536b6babd74da9d7460ddb20cd3878b265ed6b5f35a79bda9ba8e5edef3b0cd65f796c3e0d1f1cef13f0a460c19caed9768456add9eb704e6cb9fb9f14171240bf981e51303323d66b407a05c0c2b2c6fcf70e36a71faeeca2b88850ea3ebd87da3fbd837451a9b2031be08d189ae055b3e9483101e2b1c2b3f4e23b5fc856eb6e3c43610f1fa380904832b69be8ea243dfdf286e8cca4f8708fcf8b1e37a1302d0ffebd3ebda0a69b8f5094c0bab3a40b89ed230c98ce554b7fc435cf9b739e9255052d93c60d270c2cd028577aaa19567ae6d5321ec1eb6dab0d798cf7fe0b204ce6afd65599fe72a0893315aceb65bbab9d3acb4e501799dd7a5130cfac5c7a2b0d8c8ff2b9d994030820c22a8046db39118311835214f00a9e19efe937bf5561c2bcaf34485cfddd231a089339fbcdea3ca9d64909eca9afb09aff30aa17cc9ee85766d311880fde36c34a1331188d75157d1e01881d2c3a81364226ae82e4e839b703284ca695d64928800313d0506e45135645e0b6fb93d9b42734d078db9c2914a2b35e408a6cac957d4468e39d9bdad3211b7783e85600111e0893698fd080d61a621947969f85f679225077b7b0a4ed3db2e87a2b2d845521363dd82bc941ed30a90419b9932eb70188f9eee3bc768ba3628fc8eec66aea6e9700c8b4ef2773a7f40bed133c410b675d3386eaaacc4b7bd82fd4753611ac8ba302ab04194156ee02d12d0082207bf39d15a1cc4bb32da3872470c7a5bf15ac99ceca4ab7eb95dae34778a9ee02513380023c8b97a902091142c07c2e9c3d9ea46b922a1ef78b2aa079f635575868414eaada025961c0bb03444d00820004e7ce802775b7c1f0942b47fa65d8a0040696f7460c1fa854cdeeef901964a715449701546d361db088c5eafe683e054bd882b2f5469d4114e614206a716c5c02101d3aab79b68cc17c8e1935d4197efdae2e16bcb5265f09c7c655109d06704f7e9d2a6f5349da88fda6a527f7cbf94ff006476654ca407e1e434b1120ba12273a052056139edd615e1ad35a90c23030314ef5fc87d18e0fe245f111dfb94a8b5c9b681bdfab2d4909d1bcaa293a992f66bb5a7a97dec8d9151bd51bbad841bff9c3fa74ade0f5c40ee73c22e7047388a32282fbd31517e8f4d91a2a3b7781eadabaa8b3ae9182d87e21521ec6a424527a5a924b723b7aa298a769606d53b48986c6a70de60e0ad63e511cad1089b48bc018361d204c7210cc2b1109d962e11febc8c1c1c1aa96de5403f8e2f6f6bc08bd76cd031308e493060ee69a24271cf13df6e1b66717d23906184a0ce9a8277e00ff3b30ef5baaa76e1e4cbffeec429a3c41bd370b0d03783fffed7a4a613b1f285d616c3e6bade47fef3d5a6cee2f24846e9c358e6ebeee4a595a114a8c1c14c3b3b683270e20734bda0a64fccc2683ea1d7e5500b279cf6da653b087ec2fb902f09054f4d1661cb86ca188d870faf18de3691cb422653045b1fdc4c6e636fe68536b3bd7cc09e9a9aa4d319e430880675e7882ed3b324d8b0c332f3088b64f975651614915ed397286367c91c307d1a21f4ea1eba78db31a706807d3004a5cac7b1c32115e40e66a36861501c456c8bd9f18359b4ef3306747b6a212c91492c4cca779ee901680f7fe86bd5c70288bef9cc997aca4054283a051e2632234edda8bb6a5291b68339e693334faf6f9dfa5ec43dff0c1b472dd3e6ec2ef5a30c30a444c03710ad96bb223d5c19730a5ccd74887ec9536851501dc92d3ec3073cc59e28c61a9d415994633268db4f24061d6b0ebbd757b01a58e1d4c8fdd713d8f111dcd3f5a7365e49eb7fd0e5a377e4c32fde98d8d9c365802a9b9c6a09b3aee0a3ac6046164960126d099850d7bf2433b7fdad1a0684a1d7aa150632cbeba8320ec8b61b1f7f1a107e9a99b5269d8206b0f54084e80074df087ed25d0005a7ef9f07c3eb0904b2a0519f3e082f9dfa17b871670de82ba9af81ea0d6224ca992576ad72b81d7f9ec869abc928e08cd0082a1cc30233d3a7437c5eb0cf48f3dc53488390f575e916c250c9181e6ab4c344742b7471bbe3ffe6d399daf32d00f668e2443773cfdb9228bf25ae3dc22b7f4813a7ae1a6f84c7b5ea95d0dcc3d17b036b7255c3311100a46d392a4fd146a6aa26e5d14551b1a39c308846de737fcbf3dd3a835eed3a21582265bdac00378014fe00d3c825777582da185c8e6b347bb2c80983c91fea735e643a7d0beb9d1676850501599c8dc1dd2dcf30b2ee69028095604d618e9f83813682bb52d7e1783c3954122e5053c8257f0ec0e530a0c80056243395e649d987dc51d6b911ee70e00ab7b42e8cab0d3d4d2653e2c823903e713fa6690c88b1a02adacada717577d4686aa665e0973d10b0fcd732a74b007a474704093eadabb2c8b03ce98f244e670896d31f00a9edfa91d43c93a6dcb6ca01b586c3eceb5b00f887d006c6c6ccc78fad3caf4087d84dac1ab582f29b4a54f9dda0b2c0c979c4d70d4c8862f73a8bdcd4491c3cc97279d2a3dcfdd7b84015a3c5191efb9e2836c1e73229847f0fe41dd21be52f4f47db3793aa123670a3c8017db22c7b3a2a0ec548022651faf965da1e903e0d767bbf9dc17e7be935c566461cec0e9201435c2479daa53e5ec1996e105f3db594fb8bf05311991758ce88c80a05d87be3e492fbcb281125286d2b4e957f0253494f6b6766ee2972c799b9efdcd429a3d73825d5a050fe009bca9e1c9193a455d60d23b175a69a11580d0bedf6e6b66dae79e25333942316271b44b6d81a0078f4aa6ea3c96c9d656471d51b1845b99108f692986fa667af1dd2fe881fbe6d0dceb26f2a684450010f8eeaedbaea38fb71ea1eb242797647962bf8327b516c515ba81c99682b67476a55846404080659dd4ca8929afa315d8ea778707a544649b9d0c67dbe730a2917238906dfe360686514559197d7fd220bae6aad19a47fb6bbffe11070a26121ff4858ff87facf83cf4e3594aac78e577e191169537ad907668a50a7b4fb6f907b5322281d302672644af575ca151235180838fd262811a93d8d9e986c85d05d1d0425b8c2c1ac854336bebc90eaf689f0a5afb5481201362225509dd95f6fbc333d04260c4a6ba2c41af0540a69acbdc11b7f40741f4671a1152b0a9eeb13e00fac27c8a6da1fe2c506ff3d06b46275b0188db6c734b8c1e319f113d6d74a2b9ef56674bef9e5e7f064fd02ec70b7806ef9e28cc8c26b1298f07c5dc84169f6fbf178bd69e287cc4348c956d1a1bb2fdbdd8e3013cbb6325cb563e980731d555195bb916722fb4a6a17b7473978969a0fbe33f74b8b37930cd681a45ff155bc082f18b03c5d8d0a2694356ca5c255b0d417b6a0aae1651cac751d30e6254db3e23f46df4655d3ae719075a3c5180d5c9ca8ec5aced2d5c038bce778ef264f00e465ead98ca190b26f37a268adc8e84ab0c3b038833751dd1237622441df0061ec1aba7c0435fc08a61765103d9fc171facf38c0915ccc19c80b17f1a27506afd661a1063a2ca4a2c87b95ec4c53c88e5e04c6093b8ecbc91bad85298c85511ad0b4d411dd4450c88b550ac9ea889f5ec51091ec0cbd1fa34da70fa87645e8674bf25b3edff646d374fc5e326d4936b9fd28ec15879403c9576a5515a570be59e28a5b605cedfff02c1d7b0fdb723c78b69d7ce1caaaf336b75786fae0cfe0e0ad251c6c491fcfbfc9c629206db703a501775aefa6e26ddc816c595d237e400c4c0292a2c273de3259bf1a463bcc58578c66c4afb17ab3216005dd701179eec4d6c124f1694543a95d709f0b6eecaa12ffe799403a063f350bc4c8294000effa20e3ea2844aee41fb7a5f1e1dda7d9c66fff764cbb29a5aae403b0606b400c95a5a7242d5f629adc7d22d4c81489577e5612dcf98c2d8a91e7d38076027d320b5a77351efd5159b3978004180d2cd56696c3f586b8570f1c1df727504b8680b6da26d676801ede001bc709ebc5c98f36936a19e7460e478420a7a577c26e91acbd8e2740d21f35ae9521fb1fd83fa103800414964b935f171e60beba4252929811f5d4381a9959b6f0dc616aa466a0752f6599b681b5b4c4a49c2a005340b5abaa23235a5d5bb82bbc04ccfbc19579ed7fc4c4fe238d2b304e4d0b0187afbffb6f2ac2f647fd97328e0f1fd7df3412e68a1398bef9dcbff1e3a925f6c6fb7dc30c40ca46d696d3052717131ad5b9bcd0704da461f8e763a001eb6a24033ea07b0fdc99ec4d99ae5e14a03c04efdc69c2b3dd87b869d21a81cfd034ac97b9f791b315c78efafd9c5d3f3e46e681223dec02e9fc37c0793f8fbe7eea4b7d67f456b566fa6dd1506622fe7709ac2379efa351b44c5f4bba58be8f167feca35117dd8b30830aff80de081666ec29903533ef81af6b7b673114e13dffb402073473db284e69020e6c860ce3085990fa54010484b58fec626eea0a048938ba07d7b0e16524c2ccb1565e0cdbbe93b7caec26d8211b1d1b4e20f2f535d9dfa23ce9d9d9d74f0f031daf4c9c7bc0db48f36d136fa405fd28c39913783b31aa0d1021ea3b32b28910213d926b986432dae8207ecf46df50dec79cfc680720406440ca0ce91b329b8681d999816a2086762cf9e5c9a39733c9fc310af21631ba1008086532252e271467dffb112dafbd97a3af0e5161a3969aa2a59d4b1975b9dfad72e969f11cbcfb92316449b5b36ede77da0af63e929dc3b46cc883914340160a907cbcde7d85b08bc781b40b1e9ee1b130a31b3112b35a342f24240f00a37fe6d9f25b6138ecbf809295c3b108321e55d7aabee3707b35501884ae29660b48179171a8db611d701c40fdfff92b7256246f42f058fffc8cc2778f05581f20596777b5ffb2c0c278ca5ee117364f987b030dfd90a2e2cfee2257908be714597b8bbac353080d47c702c5a5c30243da32f6d5bf42b68902392d3ce78f0b6f6095a809def34b0570bcb27fe8c46ac9b4edd5193540d64e93dd9e2daabe3df547053ba74c214fa61e0c5635e793de6793133d00c7a53b88976369da3d74ee5f27bb871659634a5c2993bb8758dc7e8ec9ce53e034f082b00b7e9a9929ca72a31ef2de9c0ab5673a1a3aee040bcfcfcdd7d7259e2b6d592a944dd6e44405a3819e7980f8a8a0213fac46ffedf6ac5c6211d51c3a8e2a6777d0e6060b2eee2ee80a73072d82e9b0bcf4df8a9eaaee1c4c0a9b14daf07204d598e8f6c732d64756cc11347dbd4262705b093bce537beec73f0c08fef351054302dc42dbe69db9758420b254411bbc905fd00c374c2c81f8f3a6b5ea4681c1e440189ec50e8e030bbcf2cfddd474a5df2df01de99294f52c055b7fb1c40289fef351052c102f78899d474d52ff8ca8652e181ffa73b650fb90054fd5503a82b3d9a0aa6e8f9077feb86caaff20070b42596e61cf50dda3a32eff20bf04067684c343bafc8fed3e1a5645e25535a3b71114b9d3728ce8770f3b115b53a641f3f068d22b41180603140c46d9c51e6d122ae94a6c80b13bc7ae33ede569f10c1865880d73d6c26555efdb0cf350fa421ad62f400b6cb72f7834f2efbe6025b1672eac618251d71fdf7a6a1d752784713e9cf7dc59663ecdf62a867bb00e7cfd5526e511985b31489d0e020eae8eca20ddb0ed3be3d7984dfc507d47c53544ed58d2d94c2ae0e41bd6fcf56d39a757be8cca97314acf05211015e79d6ef5c67cccd4f7633d7736c828e0270c5d3a622ff4be81d90f31145e6be6559a5b1c73f4c1f9c0fa141d2652edb67a4bf615545ec272a994d689e3f812734f0a7574750e0d841ee7fa58d3b065bed947ba824eb15b3e3e0605e145b41a24fe9c6ad2d1db69bba8eea026cf4db34fe7ebf034ff095c876d1020746eb08594e7e577a1d9bd29bd75007d61b9930d53838eee083f713974aa5f3de270c247f2c02339e7d93b5bcdce48d441c970581ad9a0b45949cf32ee9caf6505740a4ea805b6d9fd0383d3bdf8e85f5d28c3bcd9e268a0f7619d4d08c14fbdd8f2407f876294d0da54288b12358f0fc0a5d115845f3eb56d29efd857c471c45cd5c26371f4acdeeb429c97468c8c374367e8af92d567e0a9c2d1f1c40bc2ac65be702d5622657afa3bd9d5a828359fa6010fdf89619dc79a9a936f26bb16aaa8c74121e696f661acf559114e92a0b1c1e64ace15c3bae3bc18e86aeab9e0eb6a4f50be0104200b3dd8c3f0e208b270a18808ef312b448de4dcf4a0f9e02108034848505f8a0e0ec20f242eb589a218e494b0b8e40c7b2cd5a6465e36eb4f6ae2efeb3487cf24052ba9bb8966f0698e1170e20f344f35928e1f7000a5692070da0d25a76eda4cdc14afc3fb4c9de9b3745fdfe7e26030e0c30b300383a29782551f32d1e1d326e689c9f5fecb58c38ec8972a1bec90a48b50bd278161a2cda8980a374dabcc2e18d23e65ac4818cb4ab86eb3eb400c86ec4dbcd2e31ef17f3a02de302007c2f3dab274ca4951965261645dc76a84588be7a16030cd76f4546461eb500883fae4d0dcd2ea869f6db33f2a0514933a4c0b8fa2a11a53e7c059cb45f865525bba9e21cbeb3ac807e7764f0327f208ed30073263eec7f31eac4c7d334f6e9cb86164ff7afa6fd8cc13aae7d2856c7685c790b8b9a0e15eb48732aebd844c48aa9ec08ff372da2991ebdce3a6d3d2ac060b9774db16d951502a8871a4dd627892bdbc2e9f7dbcd27a870d737bfac96a543f2cbcd7d94078a3dc0177f949419151575d189113cce4b0fcd3ebca36996270e7af691a31000030c2fc4aa3f7380ba1a2ba9bbc39c21a00b8e20636333bb187600258486533b8b01451197e6a9c4465535b48981212da69e2aaa2afedaeabba4e86832b05cd098d4693c1fd49b60c23acc9a945820c00361562b31cc1b5dc696d458c2a4074b2f70d8810768edb5a72c8009e044ef715111d4a233c7789e00cd964bdb3ee0086120494b751b93625b25551fe54e20850c18c5c1d40dbbd6e35a89f9994d754ba4f458ed02c21b9d3b3ad82dd724ca6a1c80f87a35556e7c880b001a0701d90ac94a60b5e6e5326f176cf8225d115640ae08bac10378014fe08d1799b7b469a51fda372a5e47c0c82e80f861c6e850f5d9b16aa9eacd79019355c5d98aa0499b3d56c36ea667e711bc5d903cfce9d1068215502a024cf0061e615da44e98d2f36a7e47f07ecfd4883ed8f4d9874f4dd02f419ce1964b7f3012d91c57bdf9513e4a95b4cd1e236feeaa527d6e4f8d3094ea882cb51d25d673a2d273823fae91ebefe33b28eed046b1f6393639d2ca7c829e3e00868585e533a40b34ef1132f0b8b9dcfd47ceb72333a924180872fdfe72af80282e79fde36e76e24985f63932afe0ddb0eb0f9a410416ccc12c90de5228fa95cd846148df8ad56e97b410c0b1dbe9a175c25c2a01a4f43b04f9ccf60a8f8388e45e2447fdecbd7ce2ce8ac682410b270d20f2d7b3ba30370aed634b67b7ca91230b209006e24e6b61efc66bcd8ee7356b9d2db1c3a3f41cc455ff305f95a9f628b45a0cc4edbdf3569e700b78a25f800847a76aeb532e99d4deb9cf2a7490f26437176dfc1093735ad80b5ef5fe37d5cacce97a00f1dd63b55c43909dad1548984b008705710c8cc5ebce384d93da0700249f4e9c981785f68d1b16257be139fa7678a1095e3af1f8968674c5f5412f80672b28b8f7630646d3cd5726d0d451113430269cf47aeb042d69e6b6c803857789f38038268dbdc3cd876b695f6d0f0f175c9df3d482887a58a848ca62970daa38d584b48935b7c7673a7afd8ee28d34483b74f87a711f80271598107c62a88ea60f0b62f7bdc4505a5c0fdf13c406af2800abc4683638874beae8303b965dd11e42d06a5f94c4ef3ce0104480f7d0b4c8828557c7dad53e450d4405dca37de7c7fc1d3eb20593b398f37c210839cd6c24fb37b647117bd7bd8bdea5bbf91b78c36f1cde6ef1b7c54332e53c4f291d8a1a88ca30a57831a15ce69a61ef9ff9247db9382f017d5412c5cf78b4cf83d0be5537c714389afbc443aa12ead1d04f3243f80b282c05e142ee3acb5aa6f3e45f7e02210664280d2f7a4d27a9010f125405202a2ebe7e600034d0121b326fca5d71dea50a253c53c8907ba6ac40b690319bf7545946a70044e595b7c465f29e98f6197237695a5db95441b3e51b20f2d08bc914319f45c62a05a45a03d11edcd957e7451760b1566c03a9ece7723507124068019962de537a63a76d334e018887619bef9999480d9ebf55f192011db2844cd5ce7b52c1380d201e9e3f7b7ac0fdb72db4bb5776c948de0d8c228e852c2153579a73e921d1d1ebef7c647a6bcd7abf89ab5c11802f9f11e0fdfcbe452ee3e0f2839741d406bd3bc003059a0144239735d13930dd059edb00bc0ca27a00dd099e5b0144631bbff8caf4f44b7fbc3c27dac1b3b8ac82de7ce979971d16b9665df242ed8d377852efbeb4941f7bba5cac258058efef6fbe5ae0aab7694f9e6e99036d1bc75bb0973cf9bf7945c5a72e796d14db5ddb56afb26453bb73707b04c0cb1eaa59023099bf7ef05ed212262881ed5100d1f9814347f31f7fe1b5746fed782b31ec8ddf45b6c0138f3d5230ed9ac90e3764b5d2e3710041a0d168ccf860fde779ffe941bfc8e2662b2b050fde75cbadc1c1c19697156b05caab73a0bdcece9494e62f7f6b75fab6c347fee3e6468037e7ea29f4c8fdb767a6a6a5781c382163af68a01450f6b2c98ca339b96bd76dd8f61f01a400ee9605733c6e2ee514c3eb000a22906b9357f8edda57def9241dde2a8abfe4aa28993b612aa171006eead5936e55ca5d516ad3d5df7d06a020b8b5b5352327b76005d3c859ff2a2af45a7a9f2b02138ed8b563c701b86c06dc125f01e73313ea48700683216bffae9d2bdedbf62fbfd14a01daa0c424f66e8989d9772c98bb2c3e3e7eb72b03c013cff85c03e598ea9d275f3a74bc6872f6a19ca4f3d59596bd474f9b59611ed18f00eda61ba62f638e89df802695995f022825106f6b6661c8e46f4e9d5e2c001573a6b49eb3c0ca1ddc1c3b7214b4ac20252525ffda299356da1ea6f48406696dd3ef0174109298724e9591beb18a8a0de6fc8ed25367e88ca18ea0b172051a95ca5e1c92322a95ffcc80e2ffba7b7d522b28ce3cff6f3caa483b0b027b3a0000000049454e44ae426082, '41531693H', 'Eduard', 'Pujadas', '2002-01-11', 'edu@mail.com', 'Wb4sPX/RiDOEUTxBePdn9u7s9z0Sw9W+v6QBlOzveoA=', '12345', 1),
(28, 0x6e756c6c, '12345678Z', 'Admin', 'super', '2023-05-04', 'admin@mail.com', 'Wb4sPX/RiDOEUTxBePdn9u7s9z0Sw9W+v6QBlOzveoA=', '123', 1),
(29, 0x6e756c6c, '33273489X', 'Guillem', 'Oriol', '2002-03-05', 'guillem@mail.com', 'A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ=', '1234', 0);

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
(12, '41531693H', 0x6e756c6c, 'Eduard', 'Pujadas', '2002-01-11', 'edu@mail.com', 'Wb4sPX/RiDOEUTxBePdn9u7s9z0Sw9W+v6QBlOzveoA='),
(13, '12345678Z', 0x6e756c6c, 'Pere', 'Lloveras', '2002-03-08', 'pere@mail.com', 'A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ=');

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
  MODIFY `id_treballador` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT de la tabla `usuari`
--
ALTER TABLE `usuari`
  MODIFY `id_usuari` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

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
