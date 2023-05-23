USE Restaurant

go
CREATE OR ALTER PROCEDURE Deadlock_T1_C# AS
BEGIN
	BEGIN TRAN
	UPDATE Comenzi SET detalii_comanda='cola' WHERE id_comanda=1;
	INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('UPDATE', 'Comenzi', CURRENT_TIMESTAMP)
	WAITFOR DELAY '00:00:05'
	UPDATE Clienti SET prenume_client='Georgi' WHERE nume_client='Marian';
	INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('UPDATE', 'Clienti', CURRENT_TIMESTAMP)
	COMMIT TRAN
END