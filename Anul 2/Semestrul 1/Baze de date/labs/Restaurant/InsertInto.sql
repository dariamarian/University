INSERT INTO Clienti(nume_client,prenume_client,adresa_client,oras_client,tara_client,nrtel_client) VALUES ('Marian', 'Daria', 'Strada AVV nr. 59', 'Oradea', 'Romania', 0748165858);
INSERT INTO Clienti(nume_client,prenume_client,adresa_client,oras_client,tara_client,nrtel_client) VALUES ('Molnar', 'Eveline', 'Strada B nr. 25', 'Deva', 'Romania', 0799333666);
INSERT INTO Clienti(nume_client,prenume_client,adresa_client,oras_client,tara_client,nrtel_client) VALUES ('Mogage', 'Nicolae', 'Strada C nr. 7', 'Cluj', 'Romania', 0744111888);
INSERT INTO Clienti(nume_client,prenume_client,adresa_client,oras_client,tara_client,nrtel_client) VALUES ('Morozan', 'Dragos', 'Strada D nr. 100', 'Cluj', 'Romania', 0722777111);
INSERT INTO Clienti(nume_client,prenume_client,adresa_client,oras_client,tara_client,nrtel_client) VALUES ('Matei', 'Otniel', 'Strada E nr. 2', 'Cluj', 'Romania', 0711999888);

INSERT INTO Admini(nume_admin) VALUES ('Alexandra');
INSERT INTO Admini(nume_admin) VALUES ('Vlad');
INSERT INTO Admini(nume_admin) VALUES ('Denis');
INSERT INTO Admini(nume_admin) VALUES ('Mara');

INSERT INTO Bucatari(nume_bucatar) VALUES ('Andreea');
INSERT INTO Bucatari(nume_bucatar) VALUES ('Ioan');
INSERT INTO Bucatari(nume_bucatar) VALUES ('Alina');

INSERT INTO TipProdus(descriere_tip) VALUES('bautura');
INSERT INTO TipProdus(descriere_tip) VALUES('mancare');

INSERT INTO Produse(id_tip,descriere_produs,pret) VALUES(1,'coca-cola',6);
INSERT INTO Produse(id_tip,descriere_produs,pret) VALUES(2,'orez cu somon',40);
INSERT INTO Produse(id_tip,descriere_produs,pret) VALUES(2,'cartofi prajiti',8);

INSERT INTO Ingrediente(nume_ingredient) VALUES('cartofi');
INSERT INTO Ingrediente(nume_ingredient) VALUES('oua');
INSERT INTO Ingrediente(nume_ingredient) VALUES('sare');
INSERT INTO Ingrediente(nume_ingredient) VALUES('somon');
INSERT INTO Ingrediente(nume_ingredient) VALUES('orez');
INSERT INTO Ingrediente(nume_ingredient) VALUES('piept de pui');

INSERT INTO IngredienteProdus(id_ingredient, id_produs) VALUES(1,3);
INSERT INTO IngredienteProdus(id_ingredient, id_produs) VALUES(3,3);
INSERT INTO IngredienteProdus(id_ingredient, id_produs) VALUES(4,2);
INSERT INTO IngredienteProdus(id_ingredient, id_produs) VALUES(5,2);

INSERT INTO Comenzi(id_client,id_admin,id_bucatar,id_produs,start_comanda,final_comanda,detalii_comanda) VALUES(5,1,1,3, '20221027 18:22:30', '20221027 20:00:00', '3 oua ochi'); 
INSERT INTO Comenzi(id_client,id_admin,id_bucatar,id_produs,start_comanda,final_comanda,detalii_comanda) VALUES(2,3,2,1, '20221030 17:30:00', '20221030 19:04:27', 'cartofi prajiti'); 
INSERT INTO Comenzi(id_client,id_admin,id_bucatar,id_produs,start_comanda,final_comanda,detalii_comanda) VALUES(3,2,3,2, '20221201 8:15:15', '20221201 9:30:48', 'orez cu somon'); 

INSERT INTO Rezervari(id_client, id_admin, id_bucatar, data_rezervare, ora_rezervare, detalii_rezervare) VALUES (4,2,1, '20221028', '18:00:00', '2 persoane');
INSERT INTO Rezervari(id_client, id_admin, id_bucatar, data_rezervare, ora_rezervare, detalii_rezervare) VALUES (2,1,3, '20221130', '20:30:00', '4 persoane');
INSERT INTO Rezervari(id_client, id_admin, id_bucatar, data_rezervare, ora_rezervare, detalii_rezervare) VALUES (3,4,2, '20221130', '21:30:00', '10 persoane');

INSERT INTO StatusRezervare(id_rezervare,status) VALUES(1,'anulat');
INSERT INTO StatusRezervare(id_rezervare,status) VALUES(2,'neconfirmat');
INSERT INTO StatusRezervare(id_rezervare,status) VALUES(3,'confirmat');

INSERT INTO Rating(id_produs, id_client, nota) VALUES(2,4,10);
INSERT INTO Rating(id_produs, id_client, nota) VALUES(1,2,8);
INSERT INTO Rating(id_produs, id_client, nota) VALUES(3,5,10);
INSERT INTO Rating(id_produs, id_client, nota) VALUES(3,2,4);