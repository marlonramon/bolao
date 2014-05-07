CREATE TABLE UserSession (
  idUserSession INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  token VARCHAR(36) NOT NULL ,
  createDate DATETIME NOT NULL,
  expiratedDate DATETIME NOT NULL,
  idUser INT NOT NULL ,
  CONSTRAINT User_UserSession FOREIGN KEY
    (idUser)
    REFERENCES User (idUser)
  ) ENGINE=InnoDB;
  
  CREATE UNIQUE INDEX UserSession_token_IDX ON UserSession(token);