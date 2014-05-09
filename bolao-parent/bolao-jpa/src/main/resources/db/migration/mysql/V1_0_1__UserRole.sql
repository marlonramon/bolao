CREATE TABLE UserRole (
  idUserRole INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  role VARCHAR(80) NOT NULL ,
  idUser INT NOT NULL ,
  CONSTRAINT User_UserRole FOREIGN KEY
    (idUser)
    REFERENCES User (idUser)
  ) ENGINE=InnoDB;