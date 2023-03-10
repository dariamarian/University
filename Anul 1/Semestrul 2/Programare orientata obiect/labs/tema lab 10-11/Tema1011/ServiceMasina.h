#pragma once

#include "Masina.h"
#include "RepoMasina.h"
#include "FileRepoMasina.h"
#include <string>
#include <vector>
#include <iterator>
#include <random>
#include <algorithm>
#include <functional>
#include <memory>
#include "ValidatorMasina.h"
#include "Undo.h"
#include "CosSpalat.h"
using namespace std;
//using std::vector;
using std::function;

class ServiceMasina: public Observable 
{
protected:
	MasinaRepo& rep;
	//MasinaValidator& val;
	vector<unique_ptr<UndoAction>>undoActions;
	CosSpalat cos;
public:
	ServiceMasina(MasinaRepo& rep) :rep{ rep }, cos{ rep } {
	}
	//nu permitem copierea de obiecte ServiceMasina
	ServiceMasina(const ServiceMasina& m) = delete;
	/*
	 returneaza toate masinile in ordinea in care au fost adaugate
	*/
	const vector<Masina>& getAll() noexcept {
		return rep.getAll();
	}

	/*
	Adauga o noua Masina
	arunca exceptie daca: nu se poate salva, nu este valid
	*/
	void addMasina(int ID,const string& numar_inmatriculare, const string& producator, const string& model, const string& tip);

	/*
	Sterge o Masina
	arunca exceptie daca: nu exista masina cautata
	*/
	void deleteMasina(int ID);

	/*
	Modifica o Masina
	arunca exceptie daca: nu exista masina cautata
	*/
	void updateMasina(int ID,const string& numar_inmatriculare, const string& producator, const string& model, const string& tip);

	/*
	Cauta o Masina
	arunca exceptie daca: nu exista masina cautata
	*/
	const Masina& findCarSrv(int id) const;
	void cautaMasina(int ID);

	/*
	Sorteaza dupa numarul de inmatriculare
	*/
	vector<Masina> sortByNumar();

	/*
	Sorteaza dupa tip
	*/
	vector<Masina> sortByTip();

	/*
	Sorteaza dupa producator+model
	*/
	vector<Masina> sortByProducatorModel();

	/*
	returneaza doar masinile care au producatorul dat
	*/
	vector<Masina> filtrareProducator(const string& producator);

	/*
	returneaza doar  masinile care au tipul dat
	*/
	vector<Masina> filtrareTip(const string& tip);

	/*
	 Functie de sortare generala
	 maiMareF - functie care compara 2 Masini, verifica daca are loc relatia mai mare
			  - poate fi orice functe (in afara clasei) care respecta signatura (returneaza bool are 2 parametrii Masina)
			  - poate fi functie lambda (care nu capteaza nimic in capture list)
	 returneaza o lista sortata dupa criteriu dat ca paramatru
	*/
	vector<Masina> generalSort(bool (*maiMicF)(const Masina&, const Masina&));
	/*
	Functie generala de filtrare
	fct - poate fi o functie
	fct - poate fi lambda, am folosit function<> pentru a permite si functii lambda care au ceva in capture list
	returneaza doar masinile care trec de filtru (fct(Masina)==true)
	*/
	vector<Masina> filtreaza(function<bool(const Masina&)> fct);

	void undo();

	std::vector<Masina> addToCos(int ID);

	std::vector<Masina> addRandom(int cate);

	std::vector<Masina> golesteCos();

	std::vector<Masina> toateDinCos();

	void exportSrv(const string& file);

	CosSpalat& getCos() {
		return cos;
	}
};
void testCtr();