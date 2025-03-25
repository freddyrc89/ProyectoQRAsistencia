-- Selecciona la base de datos
CREATE DATABASE IF NOT EXISTS `u911718531_moviles20251` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `u911718531_moviles20251`;

-- ----------------------------------------------
-- 1. Crear tabla `alumnos` con la columna `dni` adecuada
-- ----------------------------------------------

CREATE TABLE IF NOT EXISTS `alumnos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dni` varchar(10) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `programa_estudios` varchar(255) NOT NULL,
  `estado` enum('A','D') NOT NULL COMMENT 'A=Activo, D=Deuda',
  `observaciones` text DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_dni` (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------------------------
-- 2. Crear tabla `accesos` con clave externa
-- ----------------------------------------------

CREATE TABLE IF NOT EXISTS `accesos` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `dni` VARCHAR(10) NOT NULL,
  `fecha_hora` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `estado_acceso` ENUM('PERMITIDO', 'DENEGADO') NOT NULL,
  `observaciones` TEXT DEFAULT NULL,
  `qr_creado` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `qr_expira` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_accesos_alumnos` (`dni`),
  CONSTRAINT `fk_accesos_alumnos` FOREIGN KEY (`dni`) REFERENCES `alumnos` (`dni`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------------------------
-- 3. Crear tabla `configuracion`
-- ----------------------------------------------

CREATE TABLE IF NOT EXISTS `configuracion` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `tiempo_caducidad` INT(11) NOT NULL DEFAULT 3,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------------------------
-- 4. Crear tabla `invitados`
-- ----------------------------------------------

CREATE TABLE IF NOT EXISTS `invitados` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NOT NULL,
  `apellido` VARCHAR(100) NOT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------------------------
-- 5. Crear tabla `vigilante`
-- ----------------------------------------------

CREATE TABLE IF NOT EXISTS `vigilante` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `dni` VARCHAR(10) NOT NULL,
  `nombre` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------------------------
-- 6. Insertar datos en `alumnos`
-- ----------------------------------------------

DELETE FROM `alumnos`;
INSERT INTO `alumnos` (`id`, `dni`, `nombre`, `programa_estudios`, `estado`, `observaciones`, `password`) VALUES
  (1, '1234567890', 'Juan Pérez', 'Ingeniería de Sistemas', 'A', 'Ninguna', NULL);

-- ----------------------------------------------
-- 7. Insertar datos en `configuracion`
-- ----------------------------------------------

DELETE FROM `configuracion`;
INSERT INTO `configuracion` (`id`, `tiempo_caducidad`) VALUES
  (1, 3);

-- ----------------------------------------------
-- 8. Insertar datos en `invitados`
-- ----------------------------------------------

DELETE FROM `invitados`;
INSERT INTO `invitados` (`id`, `nombre`, `apellido`, `created_at`) VALUES
  (1, 'Carlos', 'Pérez', '2025-03-21 17:59:32'),
  (2, 'María', 'Gómez', '2025-03-21 17:59:32'),
  (3, 'Carlos', 'Pérez', '2025-03-23 15:37:12'),
  (4, 'María', 'Gómez', '2025-03-23 15:37:12'),
  (5, 'jose', 'marca', '2025-03-23 15:37:12'),
  (6, 'Carlos', 'Pérez', '2025-03-23 17:01:14'),
  (7, 'María', 'Gómez', '2025-03-23 17:01:14'),
  (8, 'jose', 'marca', '2025-03-23 17:01:14');

-- ----------------------------------------------
-- 9. Insertar datos en `vigilante`
-- ----------------------------------------------

DELETE FROM `vigilante`;
INSERT INTO `vigilante` (`id`, `dni`, `nombre`, `password`) VALUES
  (1, '9876543210', 'Ana García', 'password123'),
  (2, '1234567890', 'Juan Pérez', 'password456');
