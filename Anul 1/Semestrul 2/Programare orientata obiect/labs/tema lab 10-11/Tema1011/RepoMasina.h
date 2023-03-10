#pragma once
#include "Masina.h"

#include <vector>
#include <string>
#include <ostream>

using std::vector;
using std::string;
using std::ostream;

class MasinaRepo {
	friend class ServiceSpalat;
	vector<Masina> all;
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
	virtual void store(const Masina& m);

	/*
	Sterge Masina
	arunca exceptie daca nu exista masina
	*/
	virtual void del(int id);

	/*
	Modifica Masina
	arunca exceptie daca nu exista masina
	*/
	virtual void update(const Masina& m);

	/*
	Cauta
	arunca exceptie daca nu exista masina
	*/
	const Masina& find(int id) const;

	const Masina& findCar(int id);

	/*
	returneaza toate masinile salvate
	*/
	const vector<Masina>& getAll() const noexcept;

	std::vector < Masina > FindCarsAfterNumar(std::string NrToFind);

	void clearRepo();

	size_t size() noexcept;
};

class Spalat
{
private:
	std::vector < Masina > MemorySpalat;
public:
	Spalat() { }
	void add(Masina&);
	void clear();
	const std::vector < Masina >& getSpalat() const;
	size_t numar_masini();
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
	string getMessage() const { return(msg); }
};

ostream& operator<<(ostream& out, const MasinaRepoException& ex);

void testeRepo();