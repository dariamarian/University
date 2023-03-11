USE Restaurant
GO

CREATE TABLE DatabaseVersion(currentVersion SMALLINT);
INSERT INTO DatabaseVersion(currentVersion) VALUES (0);
GO

CREATE OR ALTER PROCEDURE Procedura1
AS
BEGIN
	ALTER TABLE Produse
	ALTER COLUMN descriere_produs VARCHAR(100);
END;
GO

CREATE OR ALTER PROCEDURE Procedura2
AS 
BEGIN
		IF (EXISTS (SELECT * FROM sys.default_constraints WHERE name='default_nota'))
			RAISERROR ('Constrangerea este deja setata default!',10,1);
		ELSE
			BEGIN
				ALTER TABLE Rating
				ADD CONSTRAINT default_nota DEFAULT 10 FOR nota
			END;
END;
GO

CREATE OR ALTER PROCEDURE Procedura3
AS 
BEGIN
	IF (NOT EXISTS (SELECT * FROM sys.tables WHERE name='Plata'))
		BEGIN
			CREATE TABLE Plata(
				id_plata INT PRIMARY KEY IDENTITY,
				id_comanda INT,
				tip_plata VARCHAR(100)
			)
		END;
	ELSE
		RAISERROR ('Tabelul Plata a fost deja creat!',10,1);
END;
GO

CREATE OR ALTER PROCEDURE Procedura4
AS 
BEGIN
	ALTER TABLE Plata
	ADD Suma INT
END;
GO

CREATE OR ALTER PROCEDURE Procedura5
AS 
BEGIN
	IF (NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE CONSTRAINT_NAME='fk_ForeignKeyinPlata'))
		BEGIN
			ALTER TABLE Plata
			ADD CONSTRAINT fk_ForeignKeyinPlata FOREIGN KEY (id_comanda) REFERENCES Comenzi(id_comanda) ON UPDATE CASCADE ON DELETE CASCADE
		END;
	ELSE
		RAISERROR ('Foreign Key a fost deja adaugata!',10,1);
END;
GO

CREATE OR ALTER PROCEDURE ProceduraOpusa1 
AS
BEGIN
	ALTER TABLE Produse
	ALTER COLUMN descriere_produs VARCHAR(200);
END;
GO

CREATE OR ALTER PROCEDURE ProceduraOpusa2
AS 
BEGIN
		IF (NOT EXISTS (SELECT * FROM sys.default_constraints WHERE name='default_nota'))
			RAISERROR ('Constrangerea nu este setata default!',10,1);
		ELSE
			BEGIN
				ALTER TABLE Rating
				DROP CONSTRAINT default_nota
			END;
END;
GO

CREATE OR ALTER PROCEDURE ProceduraOpusa3
AS 
BEGIN
	IF (NOT EXISTS (SELECT * FROM sys.tables WHERE name='Plata'))
		RAISERROR ('Tabelul Plata nu exista!',10,1);
	ELSE
		BEGIN
			DROP TABLE Plata
		END;
END;
GO

CREATE OR ALTER PROCEDURE ProceduraOpusa4
AS 
BEGIN
	ALTER TABLE Plata
	DROP COLUMN Suma
END;
GO

CREATE OR ALTER PROCEDURE ProceduraOpusa5
AS 
BEGIN
	
	IF (NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE CONSTRAINT_NAME='fk_ForeignKeyinPlata'))
		BEGIN
			RAISERROR ('Foreign Key nu exista!',10,1);
		END;
	ELSE
		BEGIN
			ALTER TABLE Plata
			DROP CONSTRAINT fk_ForeignKeyinPlata
		END;
END;
GO


CREATE OR ALTER PROCEDURE GetToVersion
@newVersion SMALLINT
AS
BEGIN
	DECLARE @currentVersion SMALLINT;
	DECLARE @procedure VARCHAR(50);
	SET @currentVersion = (SELECT currentVersion FROM DatabaseVersion);

	IF @newVersion = @currentVersion OR @newVersion > 5 OR @newVersion < 0
		RETURN;

	IF @newVersion > @currentVersion
	BEGIN
		WHILE @newVersion > @currentVersion
		BEGIN
			SET @currentVersion = @currentVersion + 1
			SET @procedure = 'Procedura' + CAST(@currentVersion AS VARCHAR);
			PRINT @procedure;
			EXEC @procedure
		END;
	END;
	ELSE
	BEGIN
		WHILE @newVersion < @currentVersion
		BEGIN
			SET @procedure = 'ProceduraOpusa' + CAST(@currentVersion AS VARCHAR);
			SET @currentVersion = @currentVersion - 1
			PRINT @procedure;
			EXEC @procedure
		END;
	END;

	UPDATE DatabaseVersion SET currentVersion=@newVersion;
END
GO

UPDATE DatabaseVersion SET currentVersion=0
EXEC GetToVersion @newVersion=4