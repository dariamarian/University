USE Restaurant
COMMIT TRAN
DELETE FROM Clienti WHERE nume_client='Moldovan'
SELECT * FROM Clienti

-- Phantom Reads Transaction 2: delay + insert + commit
go
CREATE OR ALTER PROCEDURE Phantom_Reads_T2 AS
BEGIN
	BEGIN TRAN
	BEGIN TRY
		WAITFOR DELAY '00:00:05'
		INSERT INTO Clienti(nume_client, prenume_client) VALUES ('Moldovan','Denis')
			INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('INSERT', 'Clienti', CURRENT_TIMESTAMP)
		COMMIT TRAN
		SELECT 'Transaction committed' AS [Message]
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction rollbacked' AS [Message]
	END CATCH
END

EXECUTE Phantom_Reads_T2;
