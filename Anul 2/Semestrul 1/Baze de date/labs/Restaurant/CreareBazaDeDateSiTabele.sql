USE master
DROP DATABASE Restaurant
GO

CREATE DATABASE Restaurant
GO

USE Restaurant
GO

CREATE TABLE Clienti
(
    id_client INT PRIMARY KEY IDENTITY,
    nume_client VARCHAR(100) NOT NULL ,
    prenume_client VARCHAR(100) NOT NULL,
    adresa_client VARCHAR(100) ,
    oras_client VARCHAR(100) ,
    tara_client VARCHAR(100),
    nrtel_client INT NOT NULL UNIQUE
);
GO

CREATE TABLE Admini
(
    id_admin INT PRIMARY KEY IDENTITY,
    nume_admin VARCHAR(100)
);
GO

CREATE TABLE Bucatari
(
    id_bucatar INT PRIMARY KEY IDENTITY,
    nume_bucatar VARCHAR(100)
);
GO

CREATE TABLE TipProdus
(
    id_tip INT PRIMARY KEY IDENTITY,
    descriere_tip VARCHAR(100)
);
GO

CREATE TABLE Produse
(
    id_produs INT PRIMARY KEY IDENTITY,
    id_tip INT FOREIGN KEY REFERENCES TipProdus(id_tip) ON UPDATE CASCADE ON DELETE CASCADE,
    pret INT,
    descriere_produs VARCHAR(100)
);
GO

CREATE TABLE Ingrediente
(
    id_ingredient INT PRIMARY KEY IDENTITY,
    nume_ingredient VARCHAR(100)
);
GO

CREATE TABLE IngredienteProdus
(
    id_ingredient INT FOREIGN KEY REFERENCES Ingrediente(id_ingredient),
    id_produs INT FOREIGN KEY REFERENCES Produse(id_produs) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT pk_Ingrediente PRIMARY KEY(id_ingredient, id_produs)
);
GO

CREATE TABLE Comenzi
(
    id_comanda INT PRIMARY KEY IDENTITY,
    id_client INT FOREIGN KEY REFERENCES Clienti(id_client) ON UPDATE CASCADE ON DELETE CASCADE,
    id_admin INT FOREIGN KEY REFERENCES Admini(id_admin),
    id_bucatar INT FOREIGN KEY REFERENCES Bucatari(id_bucatar),
    id_produs INT FOREIGN KEY REFERENCES Produse(id_produs) ON UPDATE CASCADE ON DELETE CASCADE,
    start_comanda DATETIME,
    final_comanda DATETIME,
    detalii_comanda VARCHAR(100)
);
GO

CREATE TABLE Rezervari
(
    id_rezervare INT PRIMARY KEY IDENTITY,
    id_client INT FOREIGN KEY REFERENCES Clienti(id_client) ON UPDATE CASCADE ON DELETE CASCADE,
    id_admin INT FOREIGN KEY REFERENCES Admini(id_admin),
    id_bucatar INT FOREIGN KEY REFERENCES Bucatari(id_bucatar),
    data_rezervare DATE,
	ora_rezervare TIME,
    detalii_rezervare VARCHAR(100)
);
GO

CREATE TABLE StatusRezervare
(
 id_status INT PRIMARY KEY IDENTITY,
 id_rezervare INT FOREIGN KEY REFERENCES Rezervari(id_rezervare) ON UPDATE CASCADE ON DELETE CASCADE,
 status VARCHAR(100)
);
GO

CREATE TABLE Rating
(
    id_produs INT,
    id_client INT,
    nota INT,
    CONSTRAINT fk_ProduseNote FOREIGN KEY (id_produs) REFERENCES Produse(id_produs),
    CONSTRAINT fk_ClientNote FOREIGN KEY (id_client) REFERENCES Clienti(id_client),
    CONSTRAINT pk_Nota PRIMARY KEY(id_produs, id_client)
);
GO
