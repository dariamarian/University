#pragma once
#include "Masina.h"
#include "DynamicVector.h"
#include <vector>
#include <string>
#include <ostream>

using std::vector;
using std::string;
using std::ostream;

class MasinaRepo {
private:
	DynamicVector<Masina>lista;
	//DynamicVector<Masina> all;
	/*
	metoda privata verifica daca exista deja m in repository
	*/
	bool exist(const Masina& m) const;
public:
	MasinaRepo() = default;
	//nu permitem copierea de obiecte
	MasinaRepo(const MasinaRepo& m) = delete;

	/*
	Adauga Masina
	arunca exceptie daca mai exista o masina cu acelasi id
	*/
	void store(const Masina& m);

	/*
	Sterge Masina
	arunca exceptie daca nu exista masina
	*/
	void del(int id);

	/*
	Modifica Masina
	arunca exceptie daca nu exista masina
	*/
	void update(const Masina& m);

	/*
	Cauta
	arunca exceptie daca nu exista masina
	*/
	const Masina& find(int id) const;

	/*
	returneaza toate masinile salvate
	*/
	const DynamicVector<Masina>& getAll() const noexcept;

};

/*
Folosit pentru a semnala situatii exceptionale care pot aparea in repo
*/
class MasinaRepoException {
	string msg;
public:
	MasinaRepoException(string m) :msg{ m } {}
	//functie friend (vreau sa folosesc membru privat msg)
	friend ostream& operator<<(ostream& out, const MasinaRepoException& ex);
};

ostream& operator<<(ostream& out, const MasinaRepoException& ex);

void testeRepo();