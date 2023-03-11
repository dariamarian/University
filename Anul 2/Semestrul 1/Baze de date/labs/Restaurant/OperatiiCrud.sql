--Clienti, Comenzi, ProduseComandate, Produse

--FUNCTII DE VALIDARE A DATELOR DIN TABELUL CLIENTI

GO
CREATE OR ALTER FUNCTION validareNumeClient (@nume_client VARCHAR(100))
RETURNS BIT
AS
BEGIN 
	IF @nume_client IS NULL --sir vid
		RETURN 1;
	IF LTRIM(@nume_client) = '' --contine doar spatii
		RETURN 0;
	IF LEN(@nume_client) < 3 --lungime mai mica decat 3
		RETURN 0;
	RETURN 1;
END
GO

CREATE OR ALTER FUNCTION validarePrenumeClient (@prenume_client VARCHAR(100))
RETURNS BIT
AS
BEGIN 
	IF @prenume_client IS NULL --sir vid
		RETURN 1;
	IF LTRIM(@prenume_client) = '' --contine doar spatii
		RETURN 0;
	IF LEN(@prenume_client) < 3 --lungime mai mica decat 3
		RETURN 0;
	RETURN 1;
END
GO

CREATE OR ALTER FUNCTION validareAdresaClient (@adresa_client VARCHAR(100))
RETURNS BIT
AS
BEGIN 
	IF @adresa_client IS NULL --sir vid
		RETURN 1;
	IF LTRIM(@adresa_client) = '' --contine doar spatii
		RETURN 0;
	IF LEN(@adresa_client) < 3 --lungime mai mica decat 3
		RETURN 0;
	RETURN 1;
END
GO

CREATE OR ALTER FUNCTION validareOrasClient (@oras_client VARCHAR(100))
RETURNS BIT
AS
BEGIN 
	IF @oras_client IS NULL --sir vid
		RETURN 1;
	IF LTRIM(@oras_client) = '' --contine doar spatii
		RETURN 0;
	IF LEN(@oras_client) < 3 --lungime mai mica decat 3
		RETURN 0;
	RETURN 1;
END
GO

CREATE OR ALTER FUNCTION validareTaraClient (@tara_client VARCHAR(100))
RETURNS BIT
AS
BEGIN 
	IF @tara_client IS NULL --sir vid
		RETURN 1;
	IF LTRIM(@tara_client) = '' --contine doar spatii
		RETURN 0;
	IF LEN(@tara_client) < 3 --lungime mai mica decat 3
		RETURN 0;
	RETURN 1;
END
GO

CREATE OR ALTER FUNCTION validareNrTelClient (@nrtel_client VARCHAR(100))
RETURNS BIT
AS
BEGIN 
	IF @nrtel_client IS NULL --sir vid
		RETURN 1;
	IF LTRIM(@nrtel_client) = '' --contine doar spatii
		RETURN 0;
	IF LEN(@nrtel_client) < 10 OR LEN(@nrtel_client) > 15 --lungimea mai mica decat 10 ori mai mare decat 15 nu e buna
		RETURN 0;
	DECLARE @lungime INT
	SET @lungime = LEN(@nrtel_client);

	DECLARE @contor INT
	SET @contor = 1;
	
	WHILE @contor <= @lungime --parcurg tot sirul
	BEGIN
		IF NOT SUBSTRING(@nrtel_client, @contor, 1) LIKE '%[0-9]'  --nu vreau sa am altceva in afara de cifre
			RETURN 0;
		SET @contor = @contor + 1;
	END
	RETURN 1;
END
GO


--FUNCTII DE VALIDARE A DATELOR DIN TABELUL COMENZI

CREATE OR ALTER FUNCTION validareIdClientInComenzi (@id_client INT)
RETURNS BIT
AS
BEGIN 
	IF NOT EXISTS (SELECT * FROM Clienti WHERE id_client=@id_client)
		RETURN 0;
	RETURN 1;
END
GO

CREATE OR ALTER FUNCTION validareIdAdminInComenzi (@id_admin INT)
RETURNS BIT
AS
BEGIN 
	IF NOT EXISTS (SELECT * FROM Admini WHERE id_admin=@id_admin)
		RETURN 0;
	RETURN 1;
END
GO

CREATE OR ALTER FUNCTION validareIdBucatarInComenzi (@id_bucatar INT)
RETURNS BIT
AS
BEGIN 
	IF NOT EXISTS (SELECT * FROM Bucatari WHERE id_bucatar=@id_bucatar)
		RETURN 0;
	RETURN 1;
END
GO

CREATE OR ALTER FUNCTION validareStartComanda (@start_comanda datetime)
RETURNS BIT
AS
BEGIN 
	IF ISDATE(@start_comanda)=1
		RETURN 1
	RETURN 0
END
GO

CREATE OR ALTER FUNCTION validareFinalComanda (@final_comanda datetime)
RETURNS BIT
AS
BEGIN 
	IF ISDATE(@final_comanda)=1
		RETURN 1
	RETURN 0
END
GO

CREATE OR ALTER FUNCTION validareDetaliiComanda (@detalii_comanda VARCHAR(100))
RETURNS BIT
AS
BEGIN 
	IF @detalii_comanda IS NULL --sir vid
		RETURN 1;
	IF LTRIM(@detalii_comanda) = '' --contine doar spatii
		RETURN 0;
	IF LEN(@detalii_comanda) < 3 --lungime mai mica decat 3
		RETURN 0;
	RETURN 1;
END
GO


--FUNCTII DE VALIDARE A DATELOR DIN TABELUL Produse

CREATE OR ALTER FUNCTION validareIdTipInProduse (@id_tip INT)
RETURNS BIT
AS
BEGIN 
	IF NOT EXISTS (SELECT * FROM TipProdus WHERE id_tip=@id_tip)
		RETURN 0;
	RETURN 1;
END
GO

CREATE OR ALTER FUNCTION validarePret (@pret INT)
RETURNS BIT
AS
BEGIN 
	IF @pret < 0 OR @pret > 1000
		RETURN 0;
	RETURN 1;
END
GO

CREATE OR ALTER FUNCTION validareDescriereProdus (@descriere_produs VARCHAR(100))
RETURNS BIT
AS
BEGIN 
	IF @descriere_produs IS NULL --sir vid
		RETURN 1;
	IF LTRIM(@descriere_produs) = '' --contine doar spatii
		RETURN 0;
	IF LEN(@descriere_produs) < 3 --lungime mai mica decat 3
		RETURN 0;
	RETURN 1;
END
GO


--FUNCTII DE VALIDARE A DATELOR DIN TABELUL ProduseComandate

CREATE OR ALTER FUNCTION validareIdComandaInProduseComandate (@id_comanda INT)
RETURNS BIT
AS
BEGIN 
	IF NOT EXISTS (SELECT * FROM Comenzi WHERE id_comanda=@id_comanda)
		RETURN 0;
	RETURN 1;
END
GO

CREATE OR ALTER FUNCTION validareIdProdusInProduseComandate (@id_produs INT)
RETURNS BIT
AS
BEGIN 
	IF NOT EXISTS (SELECT * FROM Produse WHERE id_produs=@id_produs)
		RETURN 0;
	RETURN 1;
END
GO


--OPERATII CRUD

GO
CREATE OR ALTER PROCEDURE CRUDPeClienti 
	@what VARCHAR(20),
	@id_client INT,
	@nume VARCHAR(100), 
	@prenume VARCHAR(100), 
	@adresa VARCHAR(100),  
	@oras VARCHAR(100),  
	@tara VARCHAR(100),
	@tel VARCHAR(100)
AS 
BEGIN
	SET NOCOUNT ON;
	DECLARE @ok BIT = 1;

	IF dbo.validareNumeClient(@nume) = 0 
		OR dbo.validarePrenumeClient(@prenume) = 0 
		OR dbo.validareAdresaClient(@adresa) = 0 
		OR dbo.validareOrasClient(@oras) = 0 
		OR dbo.validareTaraClient(@tara) = 0
		OR dbo.validareNrTelClient(@tel) = 0
		SET @ok = 0;

	IF @ok = 1
	BEGIN
		IF @what = 'insert'
		BEGIN
			IF EXISTS (SELECT * FROM Clienti WHERE nrtel_client=@tel)
			BEGIN
				PRINT 'Nu se poate face insert, caci se repeta telefonul'
				RETURN 
			END
			INSERT INTO Clienti(nume_client,prenume_client,adresa_client,oras_client, tara_client,nrtel_client)
			VALUES (@nume,@prenume,@adresa,@oras,@tara, @tel)
			PRINT 'Inserare in tabelul Clienti pe: '+@nume+' '+@prenume
		END
		ELSE IF @what = 'delete'
		BEGIN
			IF NOT EXISTS (SELECT * FROM Clienti WHERE id_client = @id_client) OR @id_client IS NULL
			BEGIN
				PRINT 'Nu se poate sterge din tabelul Clienti pentru id = '+CAST(@id_client AS VARCHAR)
				RETURN
			END
			DELETE FROM Clienti WHERE id_client = @id_client
			PRINT 'Stergere din tabelul Clienti pe: '+@nume+' '+@prenume
		END
		ELSE IF @what = 'update'
		BEGIN
			IF NOT EXISTS (SELECT * FROM Clienti WHERE id_client = @id_client) OR @id_client IS NULL
			BEGIN
				PRINT 'Nu se poate face update in tabelul Clienti pentru id = '+CAST(@id_client AS VARCHAR)
				RETURN
			END
			UPDATE Clienti SET nume_client=@nume, prenume_client=@prenume, adresa_client=@adresa, oras_client=@oras, tara_client=@tara, nrtel_client=@tel WHERE id_client=@id_client
			PRINT 'Update in tabelul Clienti pentru id = '+CAST(@id_client AS VARCHAR)
		END
		ELSE IF @what = 'select'
		BEGIN 
			SELECT * FROM Clienti WHERE id_client=@id_client
			PRINT 'Select facut pe tabelul Clienti'
		END
		ELSE
			PRINT 'Actiune inexistenta'
	END
	ELSE 
		PRINT 'Eroare la validare'
END;
GO

CREATE OR ALTER PROCEDURE CRUDPeComenzi 
	@what VARCHAR(20),
	@id_comanda INT,
	@id_client INT,
	@id_admin INT,
	@id_bucatar INT,
	@start datetime, 
	@final datetime, 
	@detalii VARCHAR(100)
AS 
BEGIN
	SET NOCOUNT ON;
	DECLARE @ok BIT = 1;

	IF dbo.validareIdClientInComenzi(@id_client) = 0 
		OR dbo.validareIdAdminInComenzi(@id_admin) = 0 
		OR dbo.validareIdBucatarInComenzi(@id_bucatar) = 0 
		OR dbo.validareStartComanda(@start) = 0 
		OR dbo.validareFinalComanda(@final) = 0
		OR dbo.validareDetaliiComanda(@detalii) = 0
		SET @ok = 0;

	IF @ok = 1
	BEGIN
		IF @what = 'insert'
		BEGIN
			INSERT INTO Comenzi(id_client, id_admin, id_bucatar, start_comanda, final_comanda, detalii_comanda)
			VALUES (@id_client,@id_admin,@id_bucatar,@start,@final, @detalii)
			PRINT 'Inserare in tabelul Comenzi comanda: '+@detalii
		END
		ELSE IF @what = 'delete'
		BEGIN
			IF NOT EXISTS (SELECT * FROM Comenzi WHERE id_comanda = @id_comanda) OR @id_comanda IS NULL
			BEGIN
				PRINT 'Nu se poate sterge din tabelul Comenzi pentru id = '+CAST(@id_comanda AS VARCHAR)
				RETURN
			END
			DELETE FROM Comenzi WHERE id_comanda = @id_comanda
			PRINT 'Stergere din tabelul Comenzi comanda: '+@detalii
		END
		ELSE IF @what = 'update'
		BEGIN
			IF NOT EXISTS (SELECT * FROM Comenzi WHERE id_comanda = @id_comanda) OR @id_comanda IS NULL
			BEGIN
				PRINT 'Nu se poate face update in tabelul Comenzi pentru id = '+CAST(@id_comanda AS VARCHAR)
				RETURN
			END
			UPDATE Comenzi SET id_client=@id_client, id_admin=@id_admin, id_bucatar=@id_bucatar, start_comanda=@start, final_comanda=@final, detalii_comanda=@detalii WHERE id_comanda=@id_comanda
			PRINT 'Update in tabelul Comenzi pentru id = '+CAST(@id_comanda AS VARCHAR)
		END
		ELSE IF @what = 'select'
		BEGIN 
			SELECT * FROM Comenzi WHERE id_comanda=@id_comanda
			PRINT 'Select facut pe tabelul Comenzi'
		END
		ELSE
			PRINT 'Actiune inexistenta'
	END
	ELSE 
		PRINT 'Eroare la validare'
END;
GO

CREATE OR ALTER PROCEDURE CRUDPeProduse 
	@what VARCHAR(20),
	@id_produs INT,
	@id_tip INT,
	@descriere VARCHAR(100),
	@pret INT
	
AS 
BEGIN
	SET NOCOUNT ON;
	DECLARE @ok BIT = 1;

	IF dbo.validareIdTipInProduse(@id_tip) = 0 
		OR dbo.validarePret(@pret) = 0 
		OR dbo.validareDescriereProdus(@descriere) = 0 
		SET @ok = 0;

	IF @ok = 1
	BEGIN
		IF @what = 'insert'
		BEGIN
			INSERT INTO Produse(id_tip, pret, descriere_produs)
			VALUES (@id_tip,@pret,@descriere)
			PRINT 'Inserare in tabelul Produse pe: '+@descriere
		END
		ELSE IF @what = 'delete'
		BEGIN
			IF NOT EXISTS (SELECT * FROM Produse WHERE id_produs = @id_produs) OR @id_produs IS NULL
			BEGIN
				PRINT 'Nu se poate sterge din tabelul Produse pentru id = '+CAST(@id_produs AS VARCHAR)
				RETURN
			END
			DELETE FROM Produse WHERE id_produs = @id_produs
			PRINT 'Stergere din tabelul Produse pe: '+@descriere
		END
		ELSE IF @what = 'update'
		BEGIN
			IF NOT EXISTS (SELECT * FROM Produse WHERE id_produs= @id_produs) OR @id_produs IS NULL
			BEGIN
				PRINT 'Nu se poate face update in tabelul Produse pentru id = '+CAST(@id_produs AS VARCHAR)
				RETURN
			END
			UPDATE Produse SET id_tip=@id_tip, pret=@pret, descriere_produs=@descriere WHERE id_produs=@id_produs
			PRINT 'Update in tabelul Produse pentru id = '+CAST(@id_produs AS VARCHAR)
		END
		ELSE IF @what = 'select'
		BEGIN 
			SELECT * FROM Produse WHERE id_produs=@id_produs
			PRINT 'Select facut pe tabelul Produse'
		END
		ELSE
			PRINT 'Actiune inexistenta'
	END
	ELSE 
		PRINT 'Eroare la validare'
END;
GO

CREATE OR ALTER PROCEDURE CRUDPeProduseComandate
	@what VARCHAR(20),
	@id_comanda INT,
	@id_produs INT
AS 
BEGIN
	SET NOCOUNT ON;
	DECLARE @ok BIT = 1;

	IF dbo.validareIdComandaInProduseComandate(@id_comanda) = 0 
		OR dbo.validareIdProdusInProduseComandate(@id_produs) = 0
		SET @ok = 0;

	IF @ok = 1
	BEGIN
		IF @what = 'insert'
		BEGIN
			INSERT INTO ProduseComandate(id_comanda, id_produs)
			VALUES (@id_comanda,@id_produs)
			PRINT 'Inserare in tabelul ProduseComandate'
		END
		ELSE IF @what = 'delete'
		BEGIN
			DELETE FROM ProduseComandate WHERE id_comanda = @id_comanda AND id_produs=@id_produs
			PRINT 'Stergere din tabelul ProduseComandate'
		END
		ELSE IF @what = 'update'
		BEGIN
			IF NOT EXISTS (SELECT * FROM ProduseComandate WHERE id_comanda = @id_comanda AND id_produs=@id_produs)
			BEGIN
				PRINT 'Nu se poate face update in tabelul ProduseComandate'
				RETURN
			END
			UPDATE ProduseComandate SET id_comanda = @id_comanda, id_produs=@id_produs
			PRINT 'Update in tabelul ProduseComandate pentru id '
		END
		ELSE IF @what = 'select'
		BEGIN 
			SELECT * FROM ProduseComandate WHERE id_comanda = @id_comanda AND id_produs=@id_produs
			PRINT 'Select facut pe tabelul ProduseComandate'
		END
		ELSE
			PRINT 'Actiune inexistenta'
	END
	ELSE 
		PRINT 'Eroare la validare'
END;
GO


--MAIN CRUD

GO
CREATE OR ALTER PROCEDURE mainCRUD 
AS
BEGIN
	SET NOCOUNT ON;
	DELETE FROM ProduseComandate
	DELETE FROM Produse  
	DELETE FROM Comenzi 
	DELETE FROM Clienti
	
	 
	DBCC CHECKIDENT (Clienti, RESEED, 0);
	DBCC CHECKIDENT (Comenzi, RESEED, 0);
	DBCC CHECKIDENT (Produse, RESEED, 0);
	
	PRINT ''
	PRINT 'OPERATII CRUD PE TABELUL CLIENTI'

	DECLARE @flag BIT
	EXEC CRUDPeClienti 'insert', 1, 'Marian', 'Daria', 'Strada AVV nr. 59', 'Oradea', 'Romania', '0748165858'
	EXEC CRUDPeClienti 'insert', 2, 'Molnar', 'Eveline', 'Strada B nr. 25', 'Deva', 'Romania', '0799333666'
	EXEC CRUDPeClienti 'insert', 3, 'Mogage', 'Nicolae', 'Strada C nr. 7', 'Cluj', 'Romania', '0748165858' --se repeta tel
	EXEC CRUDPeClienti 'insert', 3, 'Mogage', 'Nicolae', 'Strada C nr. 7', 'Cluj', 'Romania', '0744111888'
	EXEC CRUDPeClienti 'insert', 4, 'Morozan', 'Dragos', 'D2', 'Cluj', 'Romania', '0722777111' --nu e ok adresa
	EXEC CRUDPeClienti 'insert', 4, 'Morozan', 'Dragos', 'Strada D nr. 100', 'Cluj', 'Romania', '0722777111'
	EXEC CRUDPeClienti 'insert', 5, 'Matei', 'Otniel', 'Strada E nr. 2', 'Cluj', 'Romania', '0711999888'
	EXEC CRUDPeClienti 'select', 1, 'Matei', 'Otniel', 'Strada E nr. 2', 'Cluj', 'Romania', '0711999888'
	EXEC CRUDPeClienti 'select', 5, 'Matei', 'Otniel', 'Strada E nr. 2', 'Cluj', 'Romania', '0711999888'
	EXEC CRUDPeClienti 'delete', 2, 'Matei', 'Otniel', 'Strada E nr. 2', 'Cluj', 'Romania', '0711999888'
	EXEC CRUDPeClienti 'update', 2, 'Morozan', 'Dragos', 'Strada D nr. 100', 'Sangeorz-Bai', 'Romania', '0722777111' --nu exista id 2

	PRINT ''
	PRINT 'OPERATII CRUD PE TABELUL COMENZI'

	EXEC CRUDPeComenzi 'insert',1,1,1,1, '20221027 18:22:30', '20221027 20:00:00', '3 oua ochi'
	EXEC CRUDPeComenzi 'insert',2,1,1,1, '20221027 18:22:30', '20221027 20:00:00', 'cola zero'
	EXEC CRUDPeComenzi 'insert',3,1,1,1, '20221027 18:22:30', '20221027 20:00:00', 'cartofi prajiti si piept de pui'
	EXEC CRUDPeComenzi 'insert',4,1,1,1, '20221027 18:22:30', '20221027 20:00:00', 'salata caesar si o limonada cu menta'
	EXEC CRUDPeComenzi 'insert',5,1,1,1, '20221027 18:22:30', '20221027 20:00:00', 'sa'--nu e buna descrierea
	EXEC CRUDPeComenzi 'select',5,1,1,1, '20221027 18:22:30', '20221027 20:00:00', '3 oua ochi' 
	EXEC CRUDPeComenzi 'delete',2,1,1,1, '20221027 18:22:30', '20221027 20:00:00', 'salata caesar si o limonada cu menta'
	EXEC CRUDPeComenzi 'update',2,1,1,1, '20221027 18:22:30', '20221027 20:00:00', '2 cola zero' --nu exista id 2

	PRINT ''
	PRINT 'OPERATII CRUD PE TABELUL PRODUSE'

	EXEC CRUDPeProduse'insert', 1, 1,'coca-cola',6
	EXEC CRUDPeProduse'insert', 2, 2, 'orez cu somon',100
	EXEC CRUDPeProduse'insert', 3, 2, 'cartofi prajiti',10100 --nu e ok pretul
	EXEC CRUDPeProduse'insert', 3, 2, 'cartofi prajiti',20
	EXEC CRUDPeProduse'select', 3, 2, 'cartofi prajiti',20
	EXEC CRUDPeProduse'delete', 4, 2, 'cartofi prajiti',20 --nu e ok id-ul
	EXEC CRUDPeProduse'delete', 3, 2, 'cartofi prajiti',20
	EXEC CRUDPeProduse'update', 2, 2, 'orez cu somon la cuptor',100

	PRINT ''
	PRINT 'OPERATII CRUD PE TABELUL PRODUSE COMANDATE'
	PRINT ''

	EXEC CRUDPeProduseComandate 'insert',1,1
	EXEC CRUDPeProduseComandate 'insert',3,2 
	EXEC CRUDPeProduseComandate 'insert',2,1 --nu exista comanda cu id 2
	EXEC CRUDPeProduseComandate 'select',1,1	
	EXEC CRUDPeProduseComandate 'update',4,2 --nu exista comanda cu id 4
	EXEC CRUDPeProduseComandate 'delete',3,2
	EXEC CRUDPeProduseComandate 'insert',3,2 

	END;
GO
EXEC mainCRUD


SELECT * FROM Clienti
SELECT * FROM Comenzi
SELECT * FROM Produse
SELECT * FROM ProduseComandate

GO

CREATE or alter view ViewClienti
AS
	SELECT id_client,nume_client,prenume_client,nrtel_client FROM Clienti
	where nume_client='a'
GO


CREATE OR ALTER VIEW ViewCateProduseDeFiecareTipSuntComandate
AS 
	SELECT T.descriere_tip, COUNT(*) AS NrProduseComandate
	FROM Produse P 
	INNER JOIN TipProdus T ON P.id_tip=T.id_tip
	INNER JOIN ProduseComandate PC ON PC.id_produs=P.id_produs
	INNER JOIN Comenzi C ON C.id_comanda=PC.id_comanda
	GROUP BY T.descriere_tip
GO

DROP INDEX IX_Nume_Prenume_Client_Asc ON Clienti
DROP INDEX IX_Produse_De_Tip_Asc ON TipProdus

CREATE INDEX IX_Nume_Prenume_Client_Asc ON Clienti (nume_client asc)
ALTER INDEX IX_Nume_Prenume_Client_Asc ON Clienti DISABLE
ALTER INDEX IX_Nume_Prenume_Client_Asc ON Clienti REBUILD

CREATE INDEX IX_Produse_De_Tip_Asc ON TipProdus(descriere_tip asc)
ALTER INDEX IX_Produse_De_Tip_Asc ON TipProdus DISABLE
ALTER INDEX IX_Produse_De_Tip_Asc ON TipProdus REBUILD


SELECT * FROM View_Cienti
SELECT * FROM ViewCateProduseDeFiecareTipSuntComandate

SELECT * FROM View_Cienti
SELECT nume_client FROM ViewClienti

GO
SELECT i2.name, i1.user_scans, i1.user_seeks, i1.user_updates, i1.last_user_scan, i1.last_user_seek, i1.last_user_update
FROM sys.dm_db_index_usage_stats i1 INNER JOIN sys.indexes i2 ON i1.index_id = i2.index_id
WHERE  i1.object_id = i2.object_id