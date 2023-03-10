#pragma once
#include "ServiceMasina.h"
#include "Masina.h"
class ConsolUI {
	ServiceMasina& ctr;
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

	void sortUI();

	/*
	Tipareste o lista de masini la consola
	*/
	void tipareste(const DynamicVector<Masina>& Masini);
public:
	ConsolUI(ServiceMasina& ctr) :ctr{ ctr } {
	}
	//nu permitem copierea obiectelor
	ConsolUI(const ConsolUI& ot) = delete;

	void start();
};