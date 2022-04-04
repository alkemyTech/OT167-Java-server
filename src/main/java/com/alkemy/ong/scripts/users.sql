SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `creation_date` date DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `update_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`id`, `creation_date`, `deleted`, `email`, `first_name`, `last_name`, `password`, `photo`, `update_date`) VALUES
(1, '2022-04-04', b'0', 'victor@mail.com', 'Salvatierra', 'Victor', '/img', '$2a$10$TeaVCXEhJe4.cvNXLaq2ZuLHaayoKn.DhImbsgIT3dfO5DxSLevZ.', '2022-04-04'),
(2, '2022-04-04', b'0', 'facundo@mail.com', 'Villalba', 'Facundo', '/img', '$2a$10$U2qKgTNSvJLYCLeePW.G9.L7kkmWOUhlaxCf6AFZsdd3yGV8FFuwO', '2022-04-04'),
(3, '2022-04-04', b'0', 'andres@mail.com', 'Rodirguez', 'Andres', '/img', '$2a$10$Uta6ITxyA4UWfoawrv7bQuncvWJ/uDrgIRiJJd5TJgQEklV8DZmge', '2022-04-04'),
(4, '2022-04-04', b'0', 'pablo@mail.com', 'Ibañez', 'Pablo', '/img', '$2a$10$tUch5V69eGl3SlpmhRqtye.qmHip7DgGpLrdfA2q69qDyE1f29V7i', '2022-04-04'),
(5, '2022-04-04', b'0', 'mickaela@mail.com', 'Tarazaga', 'Mickaela', '/img', '$2a$10$K.SuZ40DKIHjoyxZRy2FHeVFQKPC7IEpX4LUjBbPEo6yeeSuQ4apC', '2022-04-04'),
(6, '2022-04-04', b'0', 'agustin@mail.com', 'Garcia', 'Agustin', '/img', '$2a$10$.NyIRlLyhFQU8AQUUVO1QeIko68WXdVL4u3gjbztMjMl9.h1EYbw.', '2022-04-04'),
(7, '2022-04-04', b'0', 'luz@mail.com', 'Brito', 'Luz', '/img', '$2a$10$8ses6EHPqPGuO9.YK6SH5ucdqvLp4aFTqA79f7g70rbfDDNySHg4y', '2022-04-04'),
(8, '2022-04-04', b'0', 'luciano@mail.com', 'Lattante', 'Luciano', '/img', '$2a$10$Q8.XSkcLphf6h3kAdDQide5yvDi.Gc9.NZodT6cdnlxWGC.oB9x22', '2022-04-04'),
(9, '2022-04-04', b'0', 'gabriel@mail.com', 'Rosa', 'Gabriel', '/img', '$2a$10$vYA4gzDnOg4jxDdv2yeXweUhJRaQcsncvKCaOVJConLSElzfgztBq', '2022-04-04'),
(10, '2022-04-04', b'0', 'horacio@mail.com', 'Cruz', 'Horacio', '/img', '$2a$10$zyQfx2hmx0Dy2ZFhHqoXse3QVmEDFi6VTubhSKwaG5lfqRzUSem.G', '2022-04-04'),
(11, '2022-04-04', b'0', 'diego@mail.com', 'Padilla', 'Diego', '/img', '$2a$10$DmZBN5asKncAxJMHtPfEseQhokzLMA0SL8cgxks96NEoi.ctwAR0m', '2022-04-04'),
(12, '2022-04-04', b'0', 'carlos@mail.com', 'Rodriguez', 'Carlos', '/img', '$2a$10$QBi9KwAg/7h4SzmDhFo93O8.tEIjAaqgf2.Eq2f8LRfRp5j81b1kK', '2022-04-04'),
(13, '2022-04-04', b'0', 'juan@mail.com', 'Morata', 'Juan', '/img', '$2a$10$rXqyI3s/AsRrCyPML9CmU.Dtz5JuxEMUwXFnOvBD0egtsTLBuOPpW', '2022-04-04'),
(14, '2022-04-04', b'0', 'valeria@mail.com', 'Villalba', 'Valeria', '/img', '$2a$10$c2AHH13xu4yKDfTJaI9ru.qxUnvwg523jdBBqShQp7VYprZXe9FaK', '2022-04-04'),
(15, '2022-04-04', b'0', 'nicole@mail.com', 'Lopez', 'Nicole', '/img', '$2a$10$KCT4JNvghnEbjAG5HTj9U.FWOz8v6EpjI.Wcn.BmuiEVrlcuws206', '2022-04-04'),
(16, '2022-04-04', b'0', 'norma@mail.com', 'Gomez', 'Norma', '/img', '$2a$10$kVVKfhSK7gFYMqCIJxttz.2hrtZV/6XF2rkcxT13xM8H/7PCtuIzS', '2022-04-04'),
(17, '2022-04-04', b'0', 'raquel@mail.com', 'Diaz', 'Raquel', '/img', '$2a$10$TLmpWdXcyzeyVlCdqM1NFOeDAbLGi0T.a/d.3GF0mBkLp4z8g1Hie', '2022-04-04'),
(18, '2022-04-04', b'0', 'romina@mail.com', 'Perez', 'Romina', '/img', '$2a$10$voZFFbqyB3bfwPMfdi0NwulJVFBwoVsMP7I/qv.XkHFC4xMC..wvm', '2022-04-04'),
(19, '2022-04-04', b'0', 'horacio@mail.com', 'Martin', 'Horacio', '/img', '$2a$10$LGHu80qmit.nPIMEcOKWZev0s1QKZScBrEqJh7QsB.WFjQPnxNaJK', '2022-04-04'),
(20, '2022-04-04', b'0', 'abel@mail.com', 'Ojeda', 'Abel', '/img', '$2a$10$nrrhleLY1nVomgaEWWFdU.H/c3NgFDhlH5erfGdGoqnn6j7LKA/Mm', '2022-04-04');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--
--
-- AUTO_INCREMENT de la tabla `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
COMMIT;
