USE Restaurant
SELECT * FROM sys.dm_tran_session_transactions
COMMIT TRAN

-- Phantom Reads Transaction 1: select + delay + select
go
CREATE OR ALTER PROCEDURE Phantom_Reads_T1 AS
BEGIN
	SET TRANSACTION ISOLATION LEVEL REPEATABLE READ		
	BEGIN TRAN
	BEGIN TRY
		SELECT * FROM Clienti
			INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('SELECT', 'Clienti', CURRENT_TIMESTAMP)
		WAITFOR DELAY '00:00:10'
		SELECT * FROM Clienti
			INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('SELECT', 'Clienti', CURRENT_TIMESTAMP)
		COMMIT TRAN
		SELECT 'Transaction committed' AS [Message]
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction rollbacked' AS [Message]
	END CATCH
END

EXECUTE Phantom_Reads_T1;

-- Solution:
go
CREATE OR ALTER PROCEDURE Phantom_Reads_T1_Solution AS
BEGIN
	SET TRANSACTION ISOLATION LEVEL SERIALIZABLE
	BEGIN TRAN
	BEGIN TRY
		SELECT * FROM Clienti
			INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('SELECT', 'Clienti', CURRENT_TIMESTAMP)
		WAITFOR DELAY '00:00:10'
			INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('SELECT', 'Clienti', CURRENT_TIMESTAMP)
		SELECT * FROM Clienti
		COMMIT TRAN
		SELECT 'Transaction committed' AS [Message]
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction rollbacked' AS [Message]
	END CATCH
END

EXECUTE Phantom_Reads_T1_Solution;