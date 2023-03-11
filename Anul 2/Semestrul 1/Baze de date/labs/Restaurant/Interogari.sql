USE Restaurant;

/* toti clientii din Cluj */
--distinct
SELECT DISTINCT nume_client, prenume_client, oras_client
FROM Clienti
WHERE oras_client LIKE 'Cluj' 

/* numarul de clienti */
--distinct
SELECT COUNT(DISTINCT id_client) as numarul_de_clienti
FROM Clienti;

/* numarul de clienti din fiecare oras */
--group by
SELECT COUNT(id_client) as numarul_de_clienti, oras_client as oras
FROM Clienti 
GROUP BY oras_client; 

/* clientii grupati in functie de oras care au oferit nota 10*/
--group by, having
SELECT nume_client, oras_client, nota
FROM Clienti C
	INNER JOIN Rating R on C.id_client = R.id_client
GROUP BY oras_client, nume_client, nota
HAVING MAX(nota) = 10

/* produsele care au pretul maxim 50, grupate dupa tip */
--group by, having
SELECT P.descriere_produs, P.pret, T.descriere_tip
FROM Produse P
	INNER JOIN TipProdus T on P.id_tip=T.id_tip
GROUP BY T.descriere_tip, P.pret, P.descriere_produs
HAVING pret < 50


/* ratingurile mai mici sau egale cu 5 */
--mai mult de 2 tabele
SELECT C.nume_client, P.descriere_produs, R.nota
FROM Clienti C
	INNER JOIN Rating R on C.id_client=R.id_client
	INNER JOIN Produse P on P.id_produs=R.id_produs
WHERE R.nota<6; 

/* rezervarile de azi */
--mai mult de 2 tabele
SELECT C.nume_client, R.detalii_rezervare, R.data_rezervare, R.ora_rezervare, S.status
FROM Clienti C
	INNER JOIN Rezervari R on C.id_client=R.id_client
	INNER JOIN StatusRezervare S on R.id_rezervare=S.id_rezervare
WHERE R.data_rezervare=CAST( GETDATE() AS Date );

/* toate rezervarile confirmate */
--mai mult de 2 tabele
SELECT C.nume_client, R.detalii_rezervare, R.data_rezervare, R.ora_rezervare, S.status
FROM Clienti C
	INNER JOIN Rezervari R on C.id_client=R.id_client
	INNER JOIN StatusRezervare S on R.id_rezervare=S.id_rezervare
WHERE S.status LIKE 'confirmat'; 

/*  ratingurile produselor de la bucatarie */
--mai mult de 2 tabele
SELECT C.nume_client, P.descriere_produs, R.nota, T.descriere_tip
FROM Clienti C
	INNER JOIN Rating R on C.id_client=R.id_client
	INNER JOIN Produse P on P.id_produs=R.id_produs
	INNER JOIN TipProdus T on P.id_tip=T.id_tip
WHERE T.descriere_tip LIKE 'mancare';

/* ratingurile produselor de la bar */
--mai mult de 2 tabele
SELECT C.nume_client, P.descriere_produs, R.nota, T.descriere_tip
FROM Clienti C
	INNER JOIN Rating R on C.id_client=R.id_client
	INNER JOIN Produse P on P.id_produs=R.id_produs
	INNER JOIN TipProdus T on P.id_tip=T.id_tip
WHERE T.descriere_tip LIKE 'bautura'; 

/* toate ratingurile */
--mai mult de 2 tabele, m-m
SELECT C.nume_client, P.descriere_produs, R.nota
FROM Clienti C, Produse P, Rating R
WHERE C.id_client=R.id_client AND P.id_produs=R.id_produs;

/* ingredientele tuturor produselor */
--mai mult de 2 tabele, m-m
SELECT P.descriere_produs, I.nume_ingredient 
FROM Produse P, Ingrediente I, IngredienteProdus I2
WHERE P.id_produs=I2.id_produs AND I2.id_ingredient=I.id_ingredient; 