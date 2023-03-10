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
using namespace std;
//using std::vector;
using std::function;

class ServiceMasina {
protected:
	MasinaRepo& rep;
	MasinaValidator& val;
	vector<unique_ptr<UndoAction>>undoActions;

	
public:
	ServiceMasina(MasinaRepo& rep, MasinaValidator& val) :rep{ rep }, val{ val } {
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

};
void testCtr();

class ServiceSpalat
{
private:
	MasinaRepo& rep;
	Spalat& sp;
	MasinaValidator& val;

public:
	ServiceSpalat(MasinaRepo& _rep, Spalat& _sp, MasinaValidator& _val) : rep{ _rep }, sp{ _sp }, val{ _val }{}

	void addSpalat(std::string);
	void clearSpalatServ();
	const std::vector < Masina >& getSpalat() const;
	void addMasinaSP(int ID, const string& numar_inmatriculare, const string& producator, const string& model, const string& tip);
	size_t numar_masiniSrv();
	int addRandom(int NumberOfCars);
};
