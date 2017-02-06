-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema spese
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema spese
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `spese` DEFAULT CHARACTER SET utf8 ;
USE `spese` ;

-- -----------------------------------------------------
-- Table `spese`.`tblcategorie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `spese`.`tblcategorie` (
  `ID_Categoria` INT(11) NOT NULL,
  `DescrizioneCategoria` VARCHAR(100) NULL DEFAULT NULL,
  `Entrata_Spesa` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_Categoria`),
  UNIQUE INDEX `ID_Categoria` (`ID_Categoria` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `spese`.`tblcausali`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `spese`.`tblcausali` (
  `ID_Causale` INT(11) NOT NULL,
  `DescrizioneCausale` VARCHAR(100) NULL DEFAULT NULL,
  `ID_Categoria` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_Causale`),
  UNIQUE INDEX `ID_Causale` (`ID_Causale` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `spese`.`tbltransazioni`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `spese`.`tbltransazioni` (
  `ID_Transazione` INT(11) NOT NULL AUTO_INCREMENT,
  `DescrizioneTransazione` VARCHAR(100) NULL DEFAULT NULL,
  `Data` DATETIME NULL DEFAULT NULL,
  `Note` VARCHAR(200) NULL DEFAULT NULL,
  `ID_Causale` INT(11) NULL DEFAULT NULL,
  `Ammontare` DOUBLE NULL DEFAULT NULL,
  PRIMARY KEY (`ID_Transazione`))
ENGINE = InnoDB
AUTO_INCREMENT = 5003
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `spese`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `spese`.`users` (
  `id` INT(11) NOT NULL,
  `user` VARCHAR(40) NULL DEFAULT NULL,
  `password` VARCHAR(40) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id` (`id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
