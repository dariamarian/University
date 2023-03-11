USE Restaurant
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER PROCEDURE Insert_Rows 
	@nb_of_rows varchar(30),
	@table_name varchar(30)
AS
BEGIN
	SET NOCOUNT ON;

	declare @table varchar(100)
	set @table = ('[' + @table_name + ']')

	if @table_name = 'Clienti' or @table_name = 'Produse' or @table_name = 'Comenzi'
	begin
		DBCC CHECKIDENT (@table, RESEED, 0);
	end

	if ISNUMERIC(@nb_of_rows) != 1
	BEGIN
		print('Not a number')
		return 1 
	END
	
	SET @nb_of_rows = cast(@nb_of_rows as INT)

	declare @contor int
	set @contor = 1

	declare @nume_client varchar(50)
	declare @prenume_client varchar(50)
	declare @adresa_client varchar(50)
	declare @oras_client varchar(50)
	set @oras_client= 'Cluj-Napoca'
	declare @tara_client varchar(50)
	set @tara_client= 'Romania'
	declare @nrtel_client int
	set @nrtel_client= 0748165858

	declare @id_tip int
	set @id_tip=2
	declare @pret int
	set @pret=50
	declare @descriere_produs varchar(50)

	declare @id_client int
	set @id_client=1
	declare @id_admin int
	set @id_admin=1
	declare @id_bucatar int
	set @id_bucatar=1
	declare @start_comanda DATETIME
	set @start_comanda = GETDATE()
	declare @final_comanda DATETIME
	set @final_comanda = GETDATE()
	declare @detalii_comanda varchar(50)

	declare @id_comanda int
	declare @id_produs int
	

	while @contor <= @nb_of_rows
	begin
		if @table_name = 'Clienti'
		begin
			set @nume_client = 'nume' + convert(varchar(7), @contor)
			set @prenume_client = 'prenume' + convert(varchar(7), @contor)
			set @adresa_client= 'adresa' + convert(varchar(7), @contor)
			insert into Clienti(nume_client,prenume_client,adresa_client,oras_client,tara_client,nrtel_client) 
			values (@nume_client, @prenume_client, @adresa_client, @oras_client, @tara_client, @nrtel_client)
		end
		if @table_name = 'Produse'
		begin
			set @descriere_produs = 'descriere' + convert(varchar(7), @contor)
			insert into Produse(id_tip,pret,descriere_produs) values (@id_tip, @pret, @descriere_produs)
		end

		if @table_name = 'Comenzi'
		begin
			set @detalii_comanda = 'detalii' + convert(varchar(7), @contor)
			insert into Comenzi (id_client,id_admin,id_bucatar,start_comanda,final_comanda,detalii_comanda) 
			values (@id_client,@id_admin,@id_bucatar,@start_comanda,@final_comanda,@detalii_comanda)

		end

		if @table_name = 'ProduseComandate'
		begin
			set @id_comanda=@contor
			set @id_produs=@contor
			insert into ProduseComandate(id_comanda,id_produs) values (@id_comanda, @id_produs)
		end

		set @contor = @contor + 1
	end
	
END