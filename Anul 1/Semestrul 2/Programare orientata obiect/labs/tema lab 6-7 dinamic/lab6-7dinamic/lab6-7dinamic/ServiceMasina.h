#pragma once

#include "Masina.h"
#include "RepoMasina.h"
#include <string>
#include <vector>

#include <functional>
#include "ValidatorMasina.h"

using std::vector;
using std::function;

class ServiceMasina {
	MasinaRepo& rep;
	MasinaValidator& val;

	/*
	 Functie de sortare generala
	 maiMareF - functie care compara 2 Masini, verifica daca are loc relatia mai mare
			  - poate fi orice functe (in afara clasei) care respecta signatura (returneaza bool are 2 parametrii Masina)
			  - poate fi functie lambda (care nu capteaza nimic in capture list)
	 returneaza o lista sortata dupa criteriu dat ca paramatru
	*/
	DynamicVector<Masina> generalSort(bool (*maiMicF)(const Masina&, const Masina&));
	/*
	Functie generala de filtrare
	fct - poate fi o functie
	fct - poate fi lambda, am folosit function<> pentru a permite si functii lambda care au ceva in capture list
	returneaza doar masinile care trec de filtru (fct(Masina)==true)
	*/
	DynamicVector<Masina> filtreaza(function<bool(const Masina&)> fct);
public:
	ServiceMasina(MasinaRepo& rep, MasinaValidator& val) :rep{ rep }, val{ val } {
	}
	//nu permitem copierea de obiecte ServiceMasina
	ServiceMasina(const ServiceMasina& m) = delete;
	/*
	 returneaza toate masinile in ordinea in care au fost adaugate
	*/
	const DynamicVector<Masina>& getAll() noexcept {
		return rep.getAll();
	}

	/*
	Adauga o noua Masina
	arunca exceptie daca: nu se poate salva, nu este valid
	*/
	void addMasina(int ID, const string& numar_inmatriculare, const string& producator, const string& model, const string& tip);

	/*
	Sterge o Masina
	arunca exceptie daca: nu exista masina cautata
	*/
	void deleteMasina(int ID);

	/*
	Modifica o Masina
	arunca exceptie daca: nu exista masina cautata
	*/
	void updateMasina(int ID, const string& numar_inmatriculare, const string& producator, const string& model, const string& tip);

	/*
	Cauta o Masina
	arunca exceptie daca: nu exista masina cautata
	*/
	void cautaMasina(int ID);
	DynamicVector<Masina> sort(function<bool(const Masina& m1, const Masina& m2)>compareFunction) const;
	/*
	Sorteaza dupa numarul de inmatriculare
	*/
	//DynamicVector<Masina> sortByNumar();

	/*
	Sorteaza dupa tip
	*/
	//DynamicVector<Masina> sortByTip();

	/*
	Sorteaza dupa producator+model
	*/
	//DynamicVector<Masina> sortByProducatorModel();

	/*
	returneaza doar masinile care au producatorul dat
	*/
	DynamicVector<Masina> filtrareProducator(const string& producator);

	/*
	returneaza doar  masinile care au tipul dat
	*/
	DynamicVector<Masina> filtrareTip(const string& tip);

};
void testCtr();
