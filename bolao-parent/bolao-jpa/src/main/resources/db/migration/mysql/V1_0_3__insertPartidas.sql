
INSERT INTO campeonato (idCampeonato,descricao) VALUES (1,'Copa do Mundo 2014');

INSERT INTO bolao (idBolao,descricao,contribuicaoPorRodada,pontosAcertoDoisPlacares,pontosAcertoUmPlacar,pontosAcertoResultado,idCampeonato) VALUES (1,'Bolão da DC',2.00,20,5,10,1);

INSERT INTO rodada (idRodada,numero,idCampeonato) VALUES (1,1,1);

INSERT INTO partida (idPartida,dataPartida,idClubeMandante,idClubeVisitante,idRodada,placarVisitante,placarMandante) VALUES (1, STR_TO_DATE('2014-06-12 17:00:00','%Y-%m-%d %H:%i:%s'),5,14,1,null,null);
INSERT INTO partida (idPartida,dataPartida,idClubeMandante,idClubeVisitante,idRodada,placarVisitante,placarMandante) VALUES (2, STR_TO_DATE('2014-06-13 13:00:00','%Y-%m-%d %H:%i:%s'),27,8,1,null,null);
INSERT INTO partida (idPartida,dataPartida,idClubeMandante,idClubeVisitante,idRodada,placarVisitante,placarMandante) VALUES (3, STR_TO_DATE('2014-06-13 16:00:00','%Y-%m-%d %H:%i:%s'),16,21,1,null,null);
INSERT INTO partida (idPartida,dataPartida,idClubeMandante,idClubeVisitante,idRodada,placarVisitante,placarMandante) VALUES (4, STR_TO_DATE('2014-06-13 19:00:00','%Y-%m-%d %H:%i:%s'),9,4,1,null,null);
INSERT INTO partida (idPartida,dataPartida,idClubeMandante,idClubeVisitante,idRodada,placarVisitante,placarMandante) VALUES (5, STR_TO_DATE('2014-06-14 13:00:00','%Y-%m-%d %H:%i:%s'),10,20,1,null,null);
INSERT INTO partida (idPartida,dataPartida,idClubeMandante,idClubeVisitante,idRodada,placarVisitante,placarMandante) VALUES (6, STR_TO_DATE('2014-06-14 22:00:00','%Y-%m-%d %H:%i:%s'),12,26,1,null,null);
INSERT INTO partida (idPartida,dataPartida,idClubeMandante,idClubeVisitante,idRodada,placarVisitante,placarMandante) VALUES (7, STR_TO_DATE('2014-06-14 16:00:00','%Y-%m-%d %H:%i:%s'),32,13,1,null,null);
INSERT INTO partida (idPartida,dataPartida,idClubeMandante,idClubeVisitante,idRodada,placarVisitante,placarMandante) VALUES (8, STR_TO_DATE('2014-06-14 19:00:00','%Y-%m-%d %H:%i:%s'),23,25,1,null,null);
INSERT INTO partida (idPartida,dataPartida,idClubeMandante,idClubeVisitante,idRodada,placarVisitante,placarMandante) VALUES (9, STR_TO_DATE('2014-06-15 13:00:00','%Y-%m-%d %H:%i:%s'),31,15,1,null,null);
INSERT INTO partida (idPartida,dataPartida,idClubeMandante,idClubeVisitante,idRodada,placarVisitante,placarMandante) VALUES (10, STR_TO_DATE('2014-06-15 16:00:00','%Y-%m-%d %H:%i:%s'),18,21,1,null,null);
INSERT INTO partida (idPartida,dataPartida,idClubeMandante,idClubeVisitante,idRodada,placarVisitante,placarMandante) VALUES (11, STR_TO_DATE('2014-06-15 19:00:00','%Y-%m-%d %H:%i:%s'),3,7,1,null,null);
INSERT INTO partida (idPartida,dataPartida,idClubeMandante,idClubeVisitante,idRodada,placarVisitante,placarMandante) VALUES (12, STR_TO_DATE('2014-06-16 16:00:00','%Y-%m-%d %H:%i:%s'),24,28,1,null,null);
INSERT INTO partida (idPartida,dataPartida,idClubeMandante,idClubeVisitante,idRodada,placarVisitante,placarMandante) VALUES (13, STR_TO_DATE('2014-06-16 13:00:00','%Y-%m-%d %H:%i:%s'),1,29,1,null,null);
INSERT INTO partida (idPartida,dataPartida,idClubeMandante,idClubeVisitante,idRodada,placarVisitante,placarMandante) VALUES (14, STR_TO_DATE('2014-06-16 19:00:00','%Y-%m-%d %H:%i:%s'),19,17,1,null,null);
INSERT INTO partida (idPartida,dataPartida,idClubeMandante,idClubeVisitante,idRodada,placarVisitante,placarMandante) VALUES (15, STR_TO_DATE('2014-06-17 13:00:00','%Y-%m-%d %H:%i:%s'),6,2,1,null,null);
INSERT INTO partida (idPartida,dataPartida,idClubeMandante,idClubeVisitante,idRodada,placarVisitante,placarMandante) VALUES (16, STR_TO_DATE('2014-06-17 19:00:00','%Y-%m-%d %H:%i:%s'),30,1,1,null,null);





