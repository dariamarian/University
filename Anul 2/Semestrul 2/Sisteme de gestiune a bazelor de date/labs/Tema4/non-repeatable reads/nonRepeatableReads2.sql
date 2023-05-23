USE Restaurant
UPDATE Clienti SET nume_client='NumeFabi' WHERE id_client=2;
COMMIT TRAN
SELECT * FROM sys.dm_tran_session_transactions 

-- Non-Repeatable Reads Transaction 2: delay + update + commit
go
CREATE OR ALTER PROCEDURE Non_Repeatable_Reads_T2 AS
BEGIN
	BEGIN TRAN
	BEGIN TRY
		WAITFOR DELAY '00:00:05'
		UPDATE Clienti SET nume_client='Martin' WHERE id_client=2;
			INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('UPDATE', 'Clienti', CURRENT_TIMESTAMP)
		COMMIT TRAN
		SELECT 'Transaction committed' AS [Message]
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction rollbacked' AS [Message]
	END CATCH
END

EXECUTE Non_Repeatable_Reads_T2;

