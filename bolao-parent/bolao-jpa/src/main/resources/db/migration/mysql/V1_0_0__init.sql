-- -----------------------------------------------------
-- Table `Usuario`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Usuario` (
  `idUsuario` INT NOT NULL AUTO_INCREMENT ,
  `email` VARCHAR(120) NOT NULL ,
  `senha` VARCHAR(80) NOT NULL ,
  `perfil` VARCHAR(80) NOT NULL ,
  `nome` VARCHAR(80) NOT NULL ,
  `dataCadastro` DATETIME NOT NULL ,
  `dataUltimoAcesso` DATETIME NOT NULL ,
  PRIMARY KEY (`idUsuario`) ,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Clube`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Clube` (
  `idClube` INT NOT NULL AUTO_INCREMENT ,
  `nome` VARCHAR(80) NULL ,
  `bandeira` BLOB NULL ,
  PRIMARY KEY (`idClube`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Campeonato`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Campeonato` (
  `idCampeonato` INT NOT NULL AUTO_INCREMENT ,
  `descricao` VARCHAR(80) NOT NULL ,
  PRIMARY KEY (`idCampeonato`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Bolao`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Bolao` (
  `idBolao` INT NOT NULL AUTO_INCREMENT ,
  `descricao` VARCHAR(80) NOT NULL ,
  `contribuicaoPorRodada` DECIMAL(15,2) NULL ,
  `pontosPlacarExato` INT(3) NULL ,
  `pontosResultadoEPlacar` INT(3) NULL ,
  `pontosResultado` INT(3) NULL ,
  `pontosPlacar` INT(3) NULL ,
  `idCampeonato` INT NOT NULL ,
  PRIMARY KEY (`idBolao`) ,
  INDEX `fk_Bolao_Campeonato1_idx` (`idCampeonato` ASC) ,
  UNIQUE INDEX `descricao_UNIQUE` (`descricao` ASC) ,
  CONSTRAINT `fk_Bolao_Campeonato1`
    FOREIGN KEY (`idCampeonato` )
    REFERENCES `Campeonato` (`idCampeonato` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Rodada`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Rodada` (
  `idRodada` INT NOT NULL AUTO_INCREMENT ,
  `numero` SMALLINT NOT NULL ,
  `idCampeonato` INT NOT NULL ,
  PRIMARY KEY (`idRodada`) ,
  INDEX `fk_Rodada_Campeonato1_idx` (`idCampeonato` ASC) ,
  UNIQUE INDEX `numero_UNIQUE` (`numero` ASC, `idCampeonato` ASC) ,
  CONSTRAINT `fk_Rodada_Campeonato1`
    FOREIGN KEY (`idCampeonato` )
    REFERENCES `Campeonato` (`idCampeonato` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Partida`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Partida` (
  `idPartida` INT NOT NULL AUTO_INCREMENT ,
  `dataPartida` DATETIME NULL ,
  `idClubeMandante` INT NOT NULL ,
  `idClubeVisitante` INT NOT NULL ,
  `idRodada` INT NOT NULL ,
  `placarVisitante` INT(2) NULL ,
  `placarMandante` INT(2) NULL ,
  PRIMARY KEY (`idPartida`) ,
  INDEX `fk_Partida_Clube_idx` (`idClubeMandante` ASC) ,
  INDEX `fk_Partida_Clube1_idx` (`idClubeVisitante` ASC) ,
  INDEX `fk_Partida_Rodada1_idx` (`idRodada` ASC) ,
  CONSTRAINT `fk_Partida_Clube`
    FOREIGN KEY (`idClubeMandante` )
    REFERENCES `Clube` (`idClube` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Partida_Clube1`
    FOREIGN KEY (`idClubeVisitante` )
    REFERENCES `Clube` (`idClube` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Partida_Rodada1`
    FOREIGN KEY (`idRodada` )
    REFERENCES `Rodada` (`idRodada` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `UsuarioBolao`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `UsuarioBolao` (
  `idUsuarioBolao` INT NOT NULL AUTO_INCREMENT ,
  `idUsuario` INT NOT NULL ,
  `idBolao` INT NOT NULL ,
  INDEX `fk_Usuario_has_Bolao_Bolao1_idx` (`idBolao` ASC) ,
  INDEX `fk_Usuario_has_Bolao_Usuario1_idx` (`idUsuario` ASC) ,
  PRIMARY KEY (`idUsuarioBolao`) ,
  CONSTRAINT `fk_Usuario_has_Bolao_Usuario1`
    FOREIGN KEY (`idUsuario` )
    REFERENCES `Usuario` (`idUsuario` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Usuario_has_Bolao_Bolao1`
    FOREIGN KEY (`idBolao` )
    REFERENCES `Bolao` (`idBolao` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Aposta`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Aposta` (
  `idAposta` INT NOT NULL AUTO_INCREMENT ,
  `placarMandante` INT(2) NULL ,
  `placarVisitante` INT(2) NULL ,
  `idPartida` INT NOT NULL ,
  `pontuacao` SMALLINT NULL ,
  `idUsuarioBolao` INT NOT NULL ,
  PRIMARY KEY (`idAposta`) ,
  INDEX `fk_Aposta_Partida1_idx` (`idPartida` ASC) ,
  INDEX `fk_Aposta_UsuarioBolao1_idx` (`idUsuarioBolao` ASC) ,
  CONSTRAINT `fk_Aposta_Partida1`
    FOREIGN KEY (`idPartida` )
    REFERENCES `Partida` (`idPartida` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Aposta_UsuarioBolao1`
    FOREIGN KEY (`idUsuarioBolao` )
    REFERENCES `UsuarioBolao` (`idUsuarioBolao` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SessaoUsuario`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `SessaoUsuario` (
  `idSessaoUsuario` INT NOT NULL AUTO_INCREMENT ,
  `token` VARCHAR(36) NOT NULL ,
  `dataCadastro` DATETIME NOT NULL ,
  `dataExpiracao` DATETIME NOT NULL ,
  `idUsuario` INT NOT NULL ,
  PRIMARY KEY (`idSessaoUsuario`) ,
  INDEX `fk_SessaoUsuario_Usuario1_idx` (`idUsuario` ASC) ,
  CONSTRAINT `fk_SessaoUsuario_Usuario1`
    FOREIGN KEY (`idUsuario` )
    REFERENCES `Usuario` (`idUsuario` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;