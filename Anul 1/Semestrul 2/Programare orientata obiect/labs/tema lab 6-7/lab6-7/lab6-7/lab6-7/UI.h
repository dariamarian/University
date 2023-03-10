#pragma once
#include "ServiceMasina.h"
#include "Masina.h"
#include "ValidatorMasina.h"
class ConsolUI {
	ServiceMasina& ctr;
	ServiceSpalat& spl;
	/*
	Citeste datele de la tastatura si adauga Masina
	arunca exceptie daca: nu se poate salva, nu e valida
	*/
	void adaugaUI();

	/*
	Citeste id-ul de la tastatura si sterge Masina
	arunca exceptie daca: nu exista masina cautata
	*/
	void stergeUI();

	/*
	Citeste id-ul de la tastatura si modifica Masina
	arunca exceptie daca: nu exista masina cautata
	*/
	void modificaUI();

	/*
	Citeste id-ul de la tastatura si cauta Masina
	arunca exceptie daca: nu exista masina cautata
	*/
	void cautaUI();
	
	/*
	Tipareste o lista de masini la consola
	*/
	void tipareste(const vector<Masina>& Masini);

	void addSpalatUI();
	void clearSpalatUI();
	void addRandomUI();
	void batch_mode();
	void exportt();
	void undo();
public:
	ConsolUI(ServiceMasina& ctr, ServiceSpalat& spl) :ctr{ ctr }, spl{ spl }{
	}
	//nu permitem copierea obiectelor
	ConsolUI(const ConsolUI& ot) = delete;

	void start();
};