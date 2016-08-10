
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `{#db_database_name#}` ;
CREATE SCHEMA IF NOT EXISTS `{#db_database_name#}` DEFAULT CHARACTER SET utf8 ;
USE `{#db_database_name#}` ;


-- -----------------------------------------------------
-- Table `mpg_com_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mpg_com_code` (
  `codeGroup` INT NOT NULL ,
  `code` INT NOT NULL ,
  `codeTitle` VARCHAR(255) NULL ,
  `codeValue` VARCHAR(255) NULL ,
  PRIMARY KEY (`codeGroup`, `code`)  )
ENGINE = InnoDB
DEFAULT CHARSET = utf8;


-- -----------------------------------------------------
-- Table `mpg_member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mpg_member` (
  `memberId` VARCHAR(255) NOT NULL ,
  `password` VARCHAR(255) NOT NULL ,
  `passwordFailCnt` INT NOT NULL DEFAULT 0 ,
  `nickname` VARCHAR(255) NOT NULL ,
  `gradeCodeGroup` INT NOT NULL ,
  `gradeCode` INT NOT NULL ,
  `registeredDate` DATETIME NOT NULL ,
  `modifiedDate` DATETIME NOT NULL ,
  `lastLoginDate` DATETIME ,
  PRIMARY KEY (`memberId`)  ,
  INDEX `fk_mpg_member_mpg_com_code_idx` (`gradeCodeGroup` ASC, `gradeCode` ASC)  ,
  CONSTRAINT `fk_mpg_member_mpg_com_code`
    FOREIGN KEY (`gradeCodeGroup` , `gradeCode`)
    REFERENCES `mpg_com_code` (`codeGroup` , `code`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARSET = utf8;


-- -----------------------------------------------------
-- Table `mpg_persistent_login`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mpg_persistent_login` (
  `memberId` VARCHAR(255) NOT NULL ,
  `series` VARCHAR(64) NOT NULL ,
  `tokenValue` VARCHAR(64) NOT NULL ,
  `lastUsed` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  PRIMARY KEY (`memberId`)  )
ENGINE = InnoDB
DEFAULT CHARSET = utf8;


-- -----------------------------------------------------
-- Table `mpg_service`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mpg_service` (
  `serviceId` INT NOT NULL ,
  `title` VARCHAR(255) NOT NULL ,
  `description` VARCHAR(255) NOT NULL ,
  `sliderImage` VARCHAR(255) NOT NULL ,
  `iconImage` VARCHAR(255) NOT NULL ,
  `pageName` VARCHAR(255) NOT NULL ,
  `registeredDate` DATETIME NOT NULL ,
  `modifiedDate` DATETIME NOT NULL ,
  PRIMARY KEY (`serviceId`)  )
ENGINE = InnoDB
DEFAULT CHARSET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
