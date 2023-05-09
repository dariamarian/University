USE Restaurant
GO

--Clienti, Comenzi, ProduseComandate, Produse
SELECT * FROM Clienti
SELECT * FROM Comenzi
SELECT * FROM Produse
SELECT * FROM ProduseComandate

--CREAREA TABELULUI LOGS
/*
CREATE TABLE Logs
(
  id INT IDENTITY,
  operationType VARCHAR(20),
  tableName VARCHAR(20),
  executionTime DATETIME,
  CONSTRAINT pk_Logs PRIMARY KEY(id)
)
*/

--FUNCTII DE VALIDARE A DATELOR DIN TABELUL CLIENTI
GO
CREATE OR ALTER FUNCTION validareNumeClient (@nume_client VARCHAR(100))
RETURNS BIT
AS
BEGIN 
	IF @nume_client IS NULL --sir vid
		RETURN 0;
	IF LTRIM(@nume_client) = '' --contine doar spatii
		RETURN 0;
	IF LEN(@nume_client) < 3 --lungime mai mica decat 3
		RETURN 0;
	RETURN 1;
END
GO
--test valid
SELECT  dbo.validareNumeClient('Marian') -- returns 1
--test invalid
SELECT  dbo.validareNumeClient(NULL) -- returns 0
SELECT  dbo.validareNumeClient('aa') -- returns 0
SELECT  dbo.validareNumeClient('    ') -- returns 0
GO

CREATE OR ALTER FUNCTION validarePrenumeClient (@prenume_client VARCHAR(100))
RETURNS BIT
AS
BEGIN 
	IF @prenume_client IS NULL --sir vid
		RETURN 0;
	IF LTRIM(@prenume_client) = '' --contine doar spatii
		RETURN 0;
	IF LEN(@prenume_client) < 3 --lungime mai mica decat 3
		RETURN 0;
	RETURN 1;
END
GO
--test valid
SELECT  dbo.validarePrenumeClient('Daria') -- returns 1
--test invalid
SELECT  dbo.validarePrenumeClient(NULL) -- returns 0
SELECT  dbo.validarePrenumeClient('aa') -- returns 0
SELECT  dbo.validarePrenumeClient('    ') -- returns 0
GO

CREATE OR ALTER FUNCTION validareAdresaClient (@adresa_client VARCHAR(100))
RETURNS BIT
AS
BEGIN 
	IF @adresa_client IS NULL --sir vid
		RETURN 0;
	IF LTRIM(@adresa_client) = '' --contine doar spatii
		RETURN 0;
	IF LEN(@adresa_client) < 3 --lungime mai mica decat 3
		RETURN 0;
	RETURN 1;
END
GO
--test valid
SELECT  dbo.validareAdresaClient('Strada AVV 59') -- returns 1
--test invalid
SELECT  dbo.validareAdresaClient(NULL) -- returns 0
SELECT  dbo.validareAdresaClient('AV') -- returns 0
SELECT  dbo.validareAdresaClient('    ') -- returns 0
GO

CREATE OR ALTER FUNCTION validareOrasClient (@oras_client VARCHAR(100))
RETURNS BIT
AS
BEGIN 
	IF @oras_client IS NULL --sir vid
		RETURN 0;
	IF LTRIM(@oras_client) = '' --contine doar spatii
		RETURN 0;
	IF LEN(@oras_client) < 3 --lungime mai mica decat 3
		RETURN 0;
	RETURN 1;
END
GO
--test valid
SELECT  dbo.validareOrasClient('Cluj-Napoca') -- returns 1
--test invalid
SELECT  dbo.validareOrasClient(NULL) -- returns 0
SELECT  dbo.validareOrasClient('Cj') -- returns 0
SELECT  dbo.validareOrasClient('    ') -- returns 0
GO

CREATE OR ALTER FUNCTION validareTaraClient (@tara_client VARCHAR(100))
RETURNS BIT
AS
BEGIN 
	IF @tara_client IS NULL --sir vid
		RETURN 0;
	IF LTRIM(@tara_client) = '' --contine doar spatii
		RETURN 0;
	IF LEN(@tara_client) < 3 --lungime mai mica decat 3
		RETURN 0;
	RETURN 1;
END
GO
--test valid
SELECT  dbo.validareTaraClient('Romania') -- returns 1
--test invalid
SELECT  dbo.validareTaraClient(NULL) -- returns 0
SELECT  dbo.validareTaraClient('ro') -- returns 0
SELECT  dbo.validareTaraClient('    ') -- returns 0
GO

CREATE OR ALTER FUNCTION validareNrTelClient (@nrtel_client VARCHAR(100))
RETURNS BIT
AS
BEGIN 
	IF @nrtel_client IS NULL --sir vid
		RETURN 0;
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
--test valid
SELECT  dbo.validareNrTelClient('0749187030') -- returns 1
--test invalid
SELECT  dbo.validareNrTelClient(NULL) -- returns 0
SELECT  dbo.validareNrTelClient('07m134d258') -- returns 0
SELECT  dbo.validareNrTelClient('078812') -- returns 0
SELECT  dbo.validareNrTelClient('0040879416809580') -- returns 0
SELECT  dbo.validareNrTelClient('    ') -- returns 0
GO

GO
CREATE or ALTER FUNCTION validareParametriClient(
@nume_client VARCHAR(100),
@prenume_client VARCHAR(100),
@adresa_client VARCHAR(100),
@oras_client VARCHAR(100),
@tara_client VARCHAR(100),
@nrtel_client VARCHAR(100)
) 
RETURNS VARCHAR(200)
AS
BEGIN
	DECLARE @error_message VARCHAR(200)
	SET @error_message = ''

	IF (dbo.validareNumeClient(@nume_client) = 0)
		SET @error_message = @error_message + 'nume invalid'
	IF (dbo.validarePrenumeClient(@prenume_client) = 0)
		SET @error_message = @error_message + 'prenume invalid'
	IF (dbo.validareAdresaClient(@adresa_client) = 0)
		SET @error_message = @error_message + 'adresa invalida'
	IF (dbo.validareOrasClient(@oras_client) = 0)
		SET @error_message = @error_message + 'oras invalid'
	IF (dbo.validareTaraClient(@tara_client) = 0)
		SET @error_message = @error_message + 'tara invalida'
	IF (dbo.validareNrTelClient(@nrtel_client) = 0)
		SET @error_message = @error_message + 'nr tel invalid'
	RETURN @error_message
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
--test valid
SELECT  dbo.validareIdClientInComenzi(1) -- returns 1
--test invalid
SELECT  dbo.validareIdClientInComenzi(100) -- returns 0
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
--test valid
SELECT  dbo.validareIdAdminInComenzi(1) -- returns 1
--test invalid
SELECT  dbo.validareIdAdminInComenzi(100) -- returns 0
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
--test valid
SELECT  dbo.validareIdBucatarInComenzi(1) -- returns 1
--test invalid
SELECT  dbo.validareIdBucatarInComenzi(100) -- returns 0
GO

CREATE OR ALTER FUNCTION validareStartComanda (@start_comanda datetime)
RETURNS BIT
AS
BEGIN 
	IF @start_comanda IS NULL --sir vid
		RETURN 0;
	IF ISDATE(@start_comanda)=1
		RETURN 1
	RETURN 0
END
GO
--test valid
SELECT  dbo.validareStartComanda('20221027 18:22:30') -- returns 1
--test invalid
SELECT  dbo.validareStartComanda(NULL) -- returns 0
GO

CREATE OR ALTER FUNCTION validareFinalComanda (@final_comanda datetime)
RETURNS BIT
AS
BEGIN 
	IF @final_comanda IS NULL --sir vid
		RETURN 0;
	IF ISDATE(@final_comanda)=1
		RETURN 1
	RETURN 0
END
GO
--test valid
SELECT  dbo.validareFinalComanda('20221027 18:22:30') -- returns 1
--test invalid
SELECT  dbo.validareFinalComanda(NULL) -- returns 0
GO

CREATE OR ALTER FUNCTION validareDetaliiComanda (@detalii_comanda VARCHAR(100))
RETURNS BIT
AS
BEGIN 
	IF @detalii_comanda IS NULL --sir vid
		RETURN 0;
	IF LTRIM(@detalii_comanda) = '' --contine doar spatii
		RETURN 0;
	IF LEN(@detalii_comanda) < 3 --lungime mai mica decat 3
		RETURN 0;
	RETURN 1;
END
GO
--test valid
SELECT  dbo.validareDetaliiComanda('coca cola') -- returns 1
--test invalid
SELECT  dbo.validareDetaliiComanda(NULL) -- returns 0
SELECT  dbo.validareDetaliiComanda('aa') -- returns 0
SELECT  dbo.validareDetaliiComanda('    ') -- returns 0
GO

GO
CREATE or ALTER FUNCTION validareParametriComanda(
@id_client INT,
@id_admin INT,
@id_bucatar INT,
@start_comanda DATETIME,
@final_comanda DATETIME,
@detalii_comanda VARCHAR(100)
) 
RETURNS VARCHAR(200)
AS
BEGIN
	DECLARE @error_message VARCHAR(200)
	SET @error_message = ''

	IF (dbo.validareIdClientInComenzi(@id_client) = 0)
		SET @error_message = @error_message + 'id client invalid'
	IF (dbo.validareIdAdminInComenzi(@id_admin) = 0)
		SET @error_message = @error_message + 'id admin invalid'
	IF (dbo.validareIdBucatarInComenzi(@id_bucatar) = 0)
		SET @error_message = @error_message + 'id bucatar invalid'
	IF (dbo.validareStartComanda(@start_comanda) = 0)
		SET @error_message = @error_message + 'start comanda invalid'
	IF (dbo.validareFinalComanda(@final_comanda) = 0)
		SET @error_message = @error_message + 'final comanda invalid'
	IF (dbo.validareDetaliiComanda(@detalii_comanda) = 0)
		SET @error_message = @error_message + 'detalii comanda invalide'
	RETURN @error_message
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
--test valid
SELECT  dbo.validareIdTipInProduse(1) -- returns 1
--test invalid
SELECT  dbo.validareIdTipInProduse(100) -- returns 0
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
--test valid
SELECT  dbo.validarePret(100) -- returns 1
--test invalid
SELECT  dbo.validarePret(1500) -- returns 0
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
--test valid
SELECT  dbo.validareDescriereProdus('cola') -- returns 1
--test invalid
SELECT  dbo.validareDescriereProdus(NULL) -- returns 0
SELECT  dbo.validareDescriereProdus('aa') -- returns 0
SELECT  dbo.validareDescriereProdus('    ') -- returns 0
GO

GO
CREATE or ALTER FUNCTION validareParametriProdus(
@id_tip INT,
@pret INT,
@descriere_produs VARCHAR(100)
) 
RETURNS VARCHAR(200)
AS
BEGIN
	DECLARE @error_message VARCHAR(200)
	SET @error_message = ''

	IF (dbo.validareIdTipInProduse(@id_tip) = 0)
		SET @error_message = @error_message + 'id tip invalid'
	IF (dbo.validarePret(@pret) = 0)
		SET @error_message = @error_message + 'pret invalid'
	IF (dbo.validareDescriereProdus(@descriere_produs) = 0)
		SET @error_message = @error_message + 'descriere produs invalida'
	RETURN @error_message
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
--test valid
SELECT  dbo.validareIdComandaInProduseComandate(1) -- returns 1
--test invalid
SELECT  dbo.validareIdComandaInProduseComandate(100) -- returns 0
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
--test valid
SELECT  dbo.validareIdProdusInProduseComandate(1) -- returns 1
--test invalid
SELECT  dbo.validareIdProdusInProduseComandate(100) -- returns 0
GO

GO
CREATE or ALTER FUNCTION validareParametriProduseComandate(
@id_comanda INT,
@id_produs INT
) 
RETURNS VARCHAR(200)
AS
BEGIN
	DECLARE @error_message VARCHAR(200)
	SET @error_message = ''

	IF (dbo.validareIdComandaInProduseComandate(@id_comanda) = 0)
		SET @error_message = @error_message + 'id comanda invalid'
	IF (dbo.validareIdProdusInProduseComandate(@id_produs) = 0)
		SET @error_message = @error_message + 'id produs invalid'
	RETURN @error_message
END
GO

--VARIANTA 1 
CREATE OR ALTER PROCEDURE insertClientComandaProdusProduseComandateV1(
@nume_client VARCHAR(100),
@prenume_client VARCHAR(100),
@adresa_client VARCHAR(100),
@oras_client VARCHAR(100),
@tara_client VARCHAR(100),
@nrtel_client VARCHAR(100),
@id_admin INT,
@id_bucatar INT,
@start_comanda DATETIME,
@final_comanda DATETIME,
@detalii_comanda VARCHAR(100),
@id_tip INT,
@pret INT,
@descriere_produs VARCHAR(100)
)
AS
BEGIN
	BEGIN TRAN
	BEGIN TRY
		DECLARE @error_message VARCHAR(200)
		SET @error_message = dbo.validareParametriClient(@nume_client,@prenume_client,@adresa_client,@oras_client,@tara_client,@nrtel_client)
		IF (@error_message != '')
		BEGIN
			PRINT @error_message
				RAISERROR(@error_message, 14, 1)
		END

		INSERT INTO Clienti(nume_client,prenume_client,adresa_client,oras_client,tara_client,nrtel_client) VALUES (@nume_client,@prenume_client,@adresa_client,@oras_client,@tara_client,@nrtel_client)
		INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('insert', 'clienti', CURRENT_TIMESTAMP)

		SET @error_message = dbo.validareParametriProdus(@id_tip,@pret,@descriere_produs)
		IF (@error_message != '')
		BEGIN
			PRINT @error_message
				RAISERROR(@error_message, 14, 1)
		END
		INSERT INTO Produse(id_tip,pret,descriere_produs) VALUES (@id_tip,@pret,@descriere_produs)
		INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('insert', 'produse', CURRENT_TIMESTAMP)

		DECLARE @id_client INT
		SET @id_client = (SELECT max(id_client) FROM Clienti)
		INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('select', 'client id', CURRENT_TIMESTAMP)

		SET @error_message = dbo.validareParametriComanda(@id_client,@id_admin,@id_bucatar,@start_comanda,@final_comanda,@detalii_comanda)
		IF (@error_message != '')
		BEGIN
			PRINT @error_message
				RAISERROR(@error_message, 14, 1)
		END
		INSERT INTO Comenzi(id_client,id_admin,id_bucatar,start_comanda,final_comanda,detalii_comanda) VALUES (@id_client,@id_admin,@id_bucatar,@start_comanda,@final_comanda,@detalii_comanda)
		INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('insert', 'comenzi', CURRENT_TIMESTAMP)

		DECLARE @id_comanda INT
		SET @id_comanda = (SELECT max(id_comanda) FROM Comenzi)
		INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('select', 'comanda id', CURRENT_TIMESTAMP)

		DECLARE @id_produs INT
		SET @id_produs = (SELECT max(id_produs) FROM Produse)
		INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('select', 'produs id', CURRENT_TIMESTAMP)

		SET @error_message = dbo.validareParametriProduseComandate(@id_comanda,@id_produs)
		IF (@error_message != '')
		BEGIN
			PRINT @error_message
				RAISERROR(@error_message, 14, 1)
		END
		INSERT INTO ProduseComandate(id_comanda,id_produs) VALUES (@id_comanda,@id_produs)
		INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('insert', 'produse comandate', CURRENT_TIMESTAMP)

		COMMIT TRAN

		SELECT 'Transaction commited'
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction rollbacked'
	END CATCH
END
GO

--TESTE
SELECT * FROM Clienti
SELECT * FROM Comenzi
SELECT * FROM Produse
SELECT * FROM ProduseComandate
SELECT * FROM Logs

--EXECUTE
EXECUTE dbo.insertClientComandaProdusProduseComandateV1
'Moldovan',
'Denis',
'TM nr. 3',
'Arad',
'Romania',
'0799110943',
1,
1,
'20230504 12:10:00',
'20230504 15:30:00',
'3 sucuri',
1,
7,
'prigat'

SELECT * FROM Clienti
SELECT * FROM Comenzi
SELECT * FROM Produse
SELECT * FROM ProduseComandate
SELECT * FROM Logs

--ROLLBACK
EXECUTE dbo.insertClientComandaProdusProduseComandateV1
'Molnar',
'Eveline',
'BZ nr. 5',
'Deva',
'Romania',
'0744110943',
1,
1,
'20230504 12:10:00',
'20230504 15:30:00',
'',
1,
7,
'prigat'

SELECT * FROM Clienti
SELECT * FROM Comenzi
SELECT * FROM Produse
SELECT * FROM ProduseComandate
SELECT * FROM Logs

--VARIANTA 2
CREATE OR ALTER PROCEDURE insertClientComandaProdusProduseComandateV2(
@nume_client VARCHAR(100),
@prenume_client VARCHAR(100),
@adresa_client VARCHAR(100),
@oras_client VARCHAR(100),
@tara_client VARCHAR(100),
@nrtel_client VARCHAR(100),
@id_admin INT,
@id_bucatar INT,
@start_comanda DATETIME,
@final_comanda DATETIME,
@detalii_comanda VARCHAR(100),
@id_tip INT,
@pret INT,
@descriere_produs VARCHAR(100)
)
AS
BEGIN
	DECLARE @error BIT
	SET @error = 1

	BEGIN TRAN
	BEGIN TRY
		DECLARE @error_message VARCHAR(200)
		SET @error_message = dbo.validareParametriClient(@nume_client,@prenume_client,@adresa_client,@oras_client,@tara_client,@nrtel_client)
		IF (@error_message != '')
		BEGIN
			PRINT @error_message
				RAISERROR(@error_message, 14, 1)
		END

		INSERT INTO Clienti(nume_client,prenume_client,adresa_client,oras_client,tara_client,nrtel_client) VALUES (@nume_client,@prenume_client,@adresa_client,@oras_client,@tara_client,@nrtel_client)
		INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('insert', 'clienti', CURRENT_TIMESTAMP)
		COMMIT TRAN

		SELECT 'Transaction commited clienti'
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction rollbacked clienti'
		INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('rollback', 'clienti', CURRENT_TIMESTAMP)
		SET @error = 0
	END CATCH

	BEGIN TRAN
	BEGIN TRY
		SET @error_message = dbo.validareParametriProdus(@id_tip,@pret,@descriere_produs)
		IF (@error_message != '')
		BEGIN
			PRINT @error_message
				RAISERROR(@error_message, 14, 1)
		END
		INSERT INTO Produse(id_tip,pret,descriere_produs) VALUES (@id_tip,@pret,@descriere_produs)
		INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('insert', 'produse', CURRENT_TIMESTAMP)
		COMMIT TRAN

		SELECT 'Transaction commited produse'
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction rollbacked produse'
		INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('rollback', 'produse', CURRENT_TIMESTAMP)
		SET @error = 0
	END CATCH

	IF (@error = 0)
		RETURN;
	
	BEGIN TRAN
	BEGIN TRY
		DECLARE @id_client INT
		SET @id_client = (SELECT max(id_client) FROM Clienti)
		INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('select', 'client id', CURRENT_TIMESTAMP)

		SET @error_message = dbo.validareParametriComanda(@id_client,@id_admin,@id_bucatar,@start_comanda,@final_comanda,@detalii_comanda)
		IF (@error_message != '')
		BEGIN
			PRINT @error_message
				RAISERROR(@error_message, 14, 1)
		END
		INSERT INTO Comenzi(id_client,id_admin,id_bucatar,start_comanda,final_comanda,detalii_comanda) VALUES (@id_client,@id_admin,@id_bucatar,@start_comanda,@final_comanda,@detalii_comanda)
		INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('insert', 'comenzi', CURRENT_TIMESTAMP)
		COMMIT TRAN

		SELECT 'Transaction commited comenzi'
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction rollbacked comenzi'
		INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('rollback', 'comenzi', CURRENT_TIMESTAMP)
		SET @error = 0
	END CATCH

	IF (@error = 0)
		RETURN;

	BEGIN TRAN
	BEGIN TRY
		DECLARE @id_comanda INT
		SET @id_comanda = (SELECT max(id_comanda) FROM Comenzi)
		INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('select', 'comanda id', CURRENT_TIMESTAMP)

		DECLARE @id_produs INT
		SET @id_produs = (SELECT max(id_produs) FROM Produse)
		INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('select', 'produs id', CURRENT_TIMESTAMP)

		SET @error_message = dbo.validareParametriProduseComandate(@id_comanda,@id_produs)
		IF (@error_message != '')
		BEGIN
			PRINT @error_message
				RAISERROR(@error_message, 14, 1)
		END
		INSERT INTO ProduseComandate(id_comanda,id_produs) VALUES (@id_comanda,@id_produs)
		INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('insert', 'produse comandate', CURRENT_TIMESTAMP)
		COMMIT TRAN

		SELECT 'Transaction commited produse comandate'
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction rollbacked produse comandate'
		INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('rollback', 'produse comandate', CURRENT_TIMESTAMP)
	END CATCH
END
GO

--TESTE
SELECT * FROM Clienti
SELECT * FROM Comenzi
SELECT * FROM Produse
SELECT * FROM ProduseComandate
SELECT * FROM Logs

--EXECUTE
EXECUTE dbo.insertClientComandaProdusProduseComandateV2
'Marian',
'Ale',
'SC nr. 3',
'Oradea',
'Romania',
'0722911943',
1,
1,
'20230226 19:10:00',
'20230226 22:30:00',
'2 pizza',
1,
39,
'pizza 4 formaggi'

SELECT * FROM Clienti
SELECT * FROM Comenzi
SELECT * FROM Produse
SELECT * FROM ProduseComandate
SELECT * FROM Logs

--ROLLBACK
EXECUTE dbo.insertClientComandaProdusProduseComandateV2
'Daria',
'Georgiana',
'AS nr. 29',
'Cluj-Napoca',
'Romania',
'0768712256',
1,
1,
'20230415 23:00:00',
'20230415 23:30:30',
'',
1,
7,
'hugo'

SELECT * FROM Clienti
SELECT * FROM Comenzi
SELECT * FROM Produse
SELECT * FROM ProduseComandate
SELECT * FROM Logs

DELETE FROM Logs
DELETE FROM Clienti
DELETE FROM Produse
DELETE FROM Comenzi
DELETE FROM ProduseComandate