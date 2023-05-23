USE Restaurant
SELECT * FROM sys.dm_tran_session_transactions
COMMIT TRAN

-- Deadlock 2 : update on Clienti + delay + update on Comenzi
go
CREATE OR ALTER PROCEDURE Deadlock_2 AS
BEGIN
	SET DEADLOCK_PRIORITY HIGH
	--SET DEADLOCK_PRIORITY LOW
	BEGIN TRAN
	BEGIN TRY
	UPDATE Clienti SET prenume_client='Georgiana' WHERE nume_client='Marian';
		INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('UPDATE', 'Clienti', CURRENT_TIMESTAMP)
		WAITFOR DELAY '00:00:05'
		UPDATE Comenzi SET detalii_comanda='cola zeroooo' WHERE id_comanda=1;
		INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('UPDATE', 'Comenzi', CURRENT_TIMESTAMP)
		COMMIT TRAN
		SELECT 'Transaction committed' AS [Message]
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction rollbacked' AS [Message]
	END CATCH
END

EXECUTE Deadlock_2;

-- Solution:
go
CREATE OR ALTER PROCEDURE Deadlock_2_Solution AS
BEGIN
	SET DEADLOCK_PRIORITY HIGH
	--SET DEADLOCK_PRIORITY LOW
	BEGIN TRAN
	BEGIN TRY
	UPDATE Clienti SET prenume_client='Georgiana' WHERE nume_client='Marian';
		INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('UPDATE', 'Clienti', CURRENT_TIMESTAMP)
		WAITFOR DELAY '00:00:05'
		UPDATE Comenzi SET detalii_comanda='cola zeroooo' WHERE id_comanda=1;
		INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('UPDATE', 'Comenzi', CURRENT_TIMESTAMP)
		COMMIT TRAN
		SELECT 'Transaction committed' AS [Message]
	END TRY
	BEGIN CATCH
		IF ERROR_NUMBER() = 1205
			BEGIN
				ROLLBACK TRAN
				SELECT 'Transaction rollbacked' AS [Message]
				BEGIN TRAN
				UPDATE Clienti SET prenume_client='Georgiana' WHERE nume_client='Marian';
				INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('UPDATE', 'Clienti', CURRENT_TIMESTAMP)
				WAITFOR DELAY '00:00:05'
				UPDATE Comenzi SET detalii_comanda='cola zeroooo' WHERE id_comanda=1;
				INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('UPDATE', 'Comenzi', CURRENT_TIMESTAMP)
				COMMIT TRAN
				SELECT 'Transaction committed' AS [Message]
			END
	END CATCH
END

EXECUTE Deadlock_2_Solution;