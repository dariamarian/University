USE master
CREATE DATABASE Politie
GO

USE Politie

CREATE TABLE Grade
(id_grad INT PRIMARY KEY IDENTITY,
denumire_grad VARCHAR(100) NOT NULL,
descriere_grad VARCHAR(100)
)

CREATE TABLE Functii
(id_functie INT PRIMARY KEY IDENTITY,
denumire_functie VARCHAR(100) NOT NULL,
descriere_functie VARCHAR(100)
)

CREATE TABLE Sectoare
(id_sector INT PRIMARY KEY IDENTITY,
denumire_sector VARCHAR(100) NOT NULL,
descriere_sector VARCHAR(100)
)

CREATE TABLE Sectii
(id_sectie INT PRIMARY KEY IDENTITY,
denumire_sectie VARCHAR(100) NOT NULL,
adresa_sectie VARCHAR(100)
)

CREATE TABLE Politisti
(id_politist INT PRIMARY KEY IDENTITY,
nume_politist VARCHAR(100) NOT NULL,
prenume_politist VARCHAR(100) NOT NULL,
id_sectie INT FOREIGN KEY REFERENCES Sectii(id_sectie),
id_grad INT FOREIGN KEY REFERENCES Grade(id_grad),
id_functie INT FOREIGN KEY REFERENCES Functii(id_functie) 
)

CREATE TABLE Programari
(id_programare INT IDENTITY PRIMARY KEY,
id_politist INT,
id_sector INT,
data_intrare DATE,
CONSTRAINT fk_ProgramariPolitisti FOREIGN KEY (id_politist) REFERENCES Politisti(id_politist),
CONSTRAINT fk_ProgramariSector FOREIGN KEY (id_sector) REFERENCES Sectoare(id_sector),
CONSTRAINT uniqueProgramare UNIQUE (id_politist,id_sector,data_intrare)
)

INSERT INTO Grade VALUES ('grad 1', 'descriere 1');
INSERT INTO Grade VALUES ('grad 2', 'descriere 2');
INSERT INTO Grade VALUES ('grad 3', 'descriere 3');

INSERT INTO Functii VALUES ('functie 1', 'descriere 1');
INSERT INTO Functii VALUES ('functie 2', 'descriere 2');
INSERT INTO Functii VALUES ('functie 3', 'descriere 3');

INSERT INTO Sectoare VALUES ('sector 1', 'descriere 1');
INSERT INTO Sectoare VALUES ('sector 2', 'descriere 2');
INSERT INTO Sectoare VALUES ('sector 3', 'descriere 3');

INSERT INTO Sectii VALUES ('sectia 1', 'adresa 1');
INSERT INTO Sectii VALUES ('sectia 2', 'adresa 2');
INSERT INTO Sectii VALUES ('sectia 3', 'adresa 3');

INSERT INTO Politisti VALUES ('nume 1', 'prenume 1', 1,1,1);
INSERT INTO Politisti VALUES ('nume 2', 'prenume 2', 2,2,2);
INSERT INTO Politisti VALUES ('nume 3', 'prenume 3', 3,3,3);
INSERT INTO Politisti VALUES ('nume 4', 'prenume 4', 1,2,3);
INSERT INTO Politisti VALUES ('nume 5', 'prenume 5', 1,3,2);

INSERT INTO Programari VALUES (1,1,'20220101');
INSERT INTO Programari VALUES (2,2,'20220110');
INSERT INTO Programari VALUES (1,3,'20220131');
INSERT INTO Programari VALUES (4,1,'20220115');
INSERT INTO Programari VALUES (5,2,'20220415');
INSERT INTO Programari VALUES (1,2,'20220101');
INSERT INTO Programari VALUES (1,3,'20220101');
INSERT INTO Programari VALUES (4,2,'20220115');
INSERT INTO Programari VALUES (4,2,'20220101');
INSERT INTO Programari VALUES (4,3,'20220101');

SELECT * FROM Sectii;
SELECT * FROM Grade;
SELECT * FROM Functii;
SELECT * FROM Sectoare;
SELECT * FROM Politisti;
SELECT * FROM Programari;

SELECT P.nume_politist, P.prenume_politist
FROM Politisti P
INNER JOIN Sectii S ON S.id_sectie=P.id_sectie
INNER JOIN Programari Pr ON Pr.id_politist=P.id_politist
WHERE S.denumire_sectie='sectia 1' AND Pr.data_intrare between '20220101' and '20220131'
GROUP BY S.denumire_sectie, P.nume_politist, P.prenume_politist
ORDER BY S.denumire_sectie, P.nume_politist, P.prenume_politist
GO

CREATE OR ALTER FUNCTION FN_Politisti(@data DATE)
RETURNS @tabel TABLE (
	denumire_sectie varchar(100),
	nume_politist varchar(100),
	prenume_politist varchar(100),
	denumire_grad varchar(100),
	denumire_functie varchar(100),
	nr_suprapuneri smallint
)
AS
BEGIN
	INSERT INTO @tabel 
	SELECT S.denumire_sectie as denumire_sectie, 
	P.nume_politist as nume_politist , P.prenume_politist as prenume_politist,
	G.denumire_grad as denumire_grad, F.denumire_functie as denumire_functie, COUNT(*) as nr_suprapuneri
	FROM Politisti P
	INNER JOIN Sectii S on P.id_sectie=S.id_sectie
	INNER JOIN Grade G on P.id_grad=G.id_grad
	INNER JOIN Functii F on P.id_functie=F.id_functie
	INNER JOIN Programari Pr on P.id_politist=Pr.id_politist
	WHERE Pr.data_intrare=@data
	GROUP BY S.denumire_sectie, P.nume_politist, P.prenume_politist, G.denumire_grad, F.denumire_functie
	HAVING COUNT(*) > 1
	ORDER BY nr_suprapuneri
	RETURN
END

SELECT * FROM FN_Politisti('20220101')