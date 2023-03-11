CREATE OR ALTER PROCEDURE TestDatabase
AS
BEGIN
	SET NOCOUNT ON

	DECLARE @NoOfRows INT,  @testRunId INT
	DECLARE @IDTest INT, @TestName NVARCHAR(50)
	DECLARE @IDTable INT, @TableName NVARCHAR(50)
	DECLARE @IDView INT, @ViewName NVARCHAR(50)
	DECLARE @testStartTime DATETIME, @testFinishTime DATETIME
	DECLARE @auxStartTime DATETIME, @auxFinishTime DATETIME

	DECLARE CursorTeste cursor forward_only
	for select * from Tests
		open CursorTeste
		fetch next from CursorTeste into @IDTest,@TestName

		DELETE FROM TestRuns;
		DELETE FROM TestRunTables;
		DELETE FROM TestRunViews;

		while @@FETCH_STATUS = 0 begin
			-- DELETE_URI
			declare CursorDelete cursor forward_only
			for select T.Name, TT.NoOfRows, TT.TableID
				from Tables T inner join dbo.TestTables TT on T.TableID = TT.TableID
				where TestID = @IDTest
				order by Position
			
			open CursorDelete
			fetch next from CursorDelete into @TableName, @NoOfRows, @IDTable
			while @@FETCH_STATUS = 0 begin
				exec('delete from ' + @TableName)
				fetch next from CursorDelete into @TableName, @NoOfRows, @IDTable
			end
			close CursorDelete
			deallocate CursorDelete

			set @testStartTime = getdate()
			INSERT INTO TestRuns (Description, StartAt) VALUES (@TestName, @testStartTime)
			set @testRunId = @@IDENTITY;

			--INSERT_URI
			declare CursorInsert cursor forward_only
			for select T.Name, TT.NoOfRows, TT.TableID
				from Tables T inner join dbo.TestTables TT on T.TableID = TT.TableID
				where TestID = @IDTest
				order by Position desc
			open CursorInsert
			fetch next from CursorInsert into @TableName, @NoOfRows, @IDTable
			while @@FETCH_STATUS = 0 begin
				set @auxStartTime = getdate()
				exec Insert_Rows @NoOfRows, @TableName
				set @auxFinishTime = getdate()
				INSERT INTO TestRunTables (TestRunID, TableID, StartAt, EndAt) VALUES (@testRunId, @IDTable, @auxStartTime, @auxFinishTime)
				fetch next from CursorInsert into @TableName, @NoOfRows, @IDTable
			end
			close CursorInsert
			deallocate CursorInsert

			--VIEW_URI
			declare CursorView CURSOR FORWARD_ONLY
			for select TV.ViewID, V.Name
				from TestViews TV INNER JOIN Views V on TV.ViewID = V.ViewID
				where TV.TestID = @IDTest;
			open CursorView
			fetch next from CursorView INTO @IDView, @ViewName;
			while @@FETCH_STATUS = 0 BEGIN
				set @auxStartTime = getdate()
				exec ('SELECT * FROM ' + @ViewName)
				set @auxFinishTime = getdate()
				INSERT INTO TestRunViews(TestRunID, ViewID, StartAt, EndAt) VALUES (@testRunId, @IDView, @auxStartTime, @auxFinishTime)
				fetch next from CursorView INTO @IDView, @ViewName;
			end
			close CursorView
			deallocate CursorView

			set @testFinishTime = getdate()
			UPDATE TestRuns SET EndAt=@testFinishTime where TestRunID=@TestRunId;

			fetch next from CursorTeste INTO @IDTest, @TestName
		END
		CLOSE CursorTeste
		DEALLOCATE CursorTeste
END
GO

DBCC CHECKIDENT (TestRuns, RESEED, 0);

EXEC TestDatabase;

SELECT * FROM TestRuns GO
SELECT * FROM TestRunTables GO
SELECT * FROM TestRunViews GO