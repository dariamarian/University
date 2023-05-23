USE Restaurant

go
CREATE OR ALTER PROCEDURE Deadlock_T2_C# AS
BEGIN
	SET DEADLOCK_PRIORITY HIGH
	-- SET DEADLOCK_PRIORITY LOW
	BEGIN TRAN
	UPDATE Clienti SET prenume_client='Georgiana' WHERE nume_client='Marian';
	INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('UPDATE', 'Clienti', CURRENT_TIMESTAMP)
	WAITFOR DELAY '00:00:05'
	UPDATE Comenzi SET detalii_comanda='cola zeroooo' WHERE id_comanda=1;
	INSERT INTO Logs(operationType, tableName, executionTime) VALUES ('UPDATE', 'Comenzi', CURRENT_TIMESTAMP)
	COMMIT TRAN
END

SELECT * FROM Clienti
SELECT * FROM Admini
SELECT * FROM Bucatari

insert into Comenzi(id_client,id_admin,id_bucatar) values (2,1,1)
insert into Clienti(id_client,nume_client,prenume_client) values (459855, 'Deadlock', 'Client')