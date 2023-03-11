CREATE OR ALTER VIEW View_Cienti
AS
SELECT * FROM Clienti
GO

CREATE OR ALTER VIEW View_Comenzi
AS
SELECT Cl.nume_client, Cl.prenume_client, C.detalii_comanda
FROM Comenzi C
INNER JOIN Clienti Cl ON Cl.id_client=C.id_client
GO

CREATE OR ALTER VIEW View_ProduseComandate
AS
SELECT C.id_comanda,  C.detalii_comanda, P.descriere_produs, B.nume_bucatar 
FROM Produse P, Comenzi C, ProduseComandate PC, Bucatari B
WHERE P.id_produs=PC.id_produs and C.id_comanda=PC.id_comanda and C.id_bucatar=B.id_bucatar
GROUP BY B.nume_bucatar, P.descriere_produs, C.detalii_comanda, C.id_comanda
GO

