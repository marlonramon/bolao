insert into campeonato (idcampeonato,descricao,imagem,ativo) values (1,'Copa América 2015','campeonatos/copaAmerica.png',1);

insert into bolao (descricao,pontosAcertoDoisPlacares,pontosAcertoUmPlacar,pontosAcertoREsultado,idcampeonato) values ('Bolão da DC',20,5,10,1);

insert into clube (idclube, nome,bandeira) values (33,'Bolivia','bandeiras/Bolivia.png');
insert into clube (idclube, nome,bandeira) values (34,'Jamaica','bandeiras/Jamaica.png');
insert into clube (idclube, nome,bandeira) values (35,'Paraguai','bandeiras/Paraguay.png');
insert into clube (idclube, nome,bandeira) values (36,'Peru','bandeiras/Peru.png');
insert into clube (idclube, nome,bandeira) values (37,'Venezuela','bandeiras/Venezuela.png');

insert into rodada (idrodada,numero,idCampeonato,rodadaAtual) values (1,1,1,1);

insert into partida (dataPartida,idClubeMandante,idClubeVisitante,idRodada) values (STR_TO_DATE('11/06/15 20:30', '%d/%m/%y %H:%i'),9,15,1);
insert into partida (dataPartida,idClubeMandante,idClubeVisitante,idRodada) values (STR_TO_DATE('12/06/15 20:30', '%d/%m/%y %H:%i'),27,33,1);

insert into partida (dataPartida,idClubeMandante,idClubeVisitante,idRodada) values (STR_TO_DATE('13/06/15 16:00', '%d/%m/%y %H:%i'),32,34,1);
insert into partida (dataPartida,idClubeMandante,idClubeVisitante,idRodada) values (STR_TO_DATE('13/06/15 18:30', '%d/%m/%y %H:%i'),3,35,1);

insert into partida (dataPartida,idClubeMandante,idClubeVisitante,idRodada) values (STR_TO_DATE('14/06/15 16:00', '%d/%m/%y %H:%i'),10,37,1);
insert into partida (dataPartida,idClubeMandante,idClubeVisitante,idRodada) values (STR_TO_DATE('14/06/15 18:30', '%d/%m/%y %H:%i'),5,36,1);


insert into rodada (idrodada,numero,idCampeonato,rodadaAtual) values (2,2,1,0);

insert into partida (dataPartida,idClubeMandante,idClubeVisitante,idRodada) values (STR_TO_DATE('15/06/15 18:00', '%d/%m/%y %H:%i'),15,33,2);
insert into partida (dataPartida,idClubeMandante,idClubeVisitante,idRodada) values (STR_TO_DATE('15/06/15 20:30', '%d/%m/%y %H:%i'),9,27,2);

insert into partida (dataPartida,idClubeMandante,idClubeVisitante,idRodada) values (STR_TO_DATE('16/06/15 18:00', '%d/%m/%y %H:%i'),35,34,2);
insert into partida (dataPartida,idClubeMandante,idClubeVisitante,idRodada) values (STR_TO_DATE('16/06/15 20:30', '%d/%m/%y %H:%i'),3,32,2);

insert into partida (dataPartida,idClubeMandante,idClubeVisitante,idRodada) values (STR_TO_DATE('17/06/15 21:00', '%d/%m/%y %H:%i'),5,10,2);
insert into partida (dataPartida,idClubeMandante,idClubeVisitante,idRodada) values (STR_TO_DATE('18/06/15 20:30', '%d/%m/%y %H:%i'),36,37,2);


insert into rodada (idrodada,numero,idCampeonato,rodadaAtual) values (3,3,1,0);

insert into partida (dataPartida,idClubeMandante,idClubeVisitante,idRodada) values (STR_TO_DATE('19/06/15 18:00', '%d/%m/%y %H:%i'),27,15,3);
insert into partida (dataPartida,idClubeMandante,idClubeVisitante,idRodada) values (STR_TO_DATE('19/06/15 20:30', '%d/%m/%y %H:%i'),9,33,3);

insert into partida (dataPartida,idClubeMandante,idClubeVisitante,idRodada) values (STR_TO_DATE('20/06/15 16:00', '%d/%m/%y %H:%i'),32,35,3);
insert into partida (dataPartida,idClubeMandante,idClubeVisitante,idRodada) values (STR_TO_DATE('20/06/15 18:30', '%d/%m/%y %H:%i'),3,34,3);

insert into partida (dataPartida,idClubeMandante,idClubeVisitante,idRodada) values (STR_TO_DATE('21/06/15 16:00', '%d/%m/%y %H:%i'),10,36,3);
insert into partida (dataPartida,idClubeMandante,idClubeVisitante,idRodada) values (STR_TO_DATE('21/06/15 18:30', '%d/%m/%y %H:%i'),5,37,3);








