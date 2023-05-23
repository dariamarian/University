USE Restaurant
UPDATE Clienti SET nume_client='Martin' WHERE id_client=2;
COMMIT TRAN

-- Dirty Reads Transaction 1: update + delay + rollback
go
CREATE OR ALTER PROCEDURE Dirty_Reads_T1 AS
BEGIN
	BEGIN TRAN
	BEGIN TRY
		UPDATE Clienti SET nume_client='client modificat' WHERE id_client=2;
		INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('UPDATE', 'Clienti', CURRENT_TIMESTAMP)
		WAITFOR DELAY '00:00:05'
		ROLLBACK TRAN
		SELECT 'Transaction rollbacked - good!' AS [Message]
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction rollbacked - bad!' AS [Message]
	END CATCH
END

EXECUTE Dirty_Reads_T1;