USE Restaurant
COMMIT TRAN

-- Deadlock 1: update on Comenzi + delay + update on Clienti
go
CREATE OR ALTER PROCEDURE Deadlock_1 AS
BEGIN
	BEGIN TRAN
	BEGIN TRY
		UPDATE Comenzi SET detalii_comanda='cola' WHERE id_comanda=1;
		INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('UPDATE', 'Comenzi', CURRENT_TIMESTAMP)
		WAITFOR DELAY '00:00:05'
		UPDATE Clienti SET prenume_client='Georgi' WHERE nume_client='Marian';
		INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('UPDATE', 'Clienti', CURRENT_TIMESTAMP)
		COMMIT TRAN
		SELECT 'Transaction committed' AS [Message]
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction rollbacked' AS [Message]
	END CATCH
END

EXECUTE Deadlock_1;

-- Solution
go
CREATE OR ALTER PROCEDURE Deadlock_1_Solution AS
BEGIN
	BEGIN TRAN
	BEGIN TRY
		UPDATE Comenzi SET detalii_comanda='cola' WHERE id_comanda=1;
		INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('UPDATE', 'Comenzi', CURRENT_TIMESTAMP)
		WAITFOR DELAY '00:00:05'
		UPDATE Clienti SET prenume_client='Georgi' WHERE nume_client='Marian';
		INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('UPDATE', 'Clienti', CURRENT_TIMESTAMP)
		COMMIT TRAN
		SELECT 'Transaction committed' AS [Message]
	END TRY
	BEGIN CATCH
		IF ERROR_NUMBER() = 1205
			BEGIN
				ROLLBACK TRAN
				SELECT 'Transaction rollbacked' AS [Message]
				BEGIN TRAN
				UPDATE Comenzi SET detalii_comanda='cola' WHERE id_comanda=1;
				INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('UPDATE', 'Comenzi', CURRENT_TIMESTAMP)
				WAITFOR DELAY '00:00:05'
				UPDATE Clienti SET prenume_client='Georgi' WHERE nume_client='Marian';
				INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('UPDATE', 'Clienti', CURRENT_TIMESTAMP)
				COMMIT TRAN
				SELECT 'Transaction committed' AS [Message]
			END
	END CATCH
END


EXECUTE Deadlock_1_Solution;