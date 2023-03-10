#include "RepoMasina.h"
#include <assert.h>
#include <iostream>
#include <sstream>
#include <algorithm>

using std::ostream;
using std::stringstream;

void MasinaRepo::store(const Masina& m)
{
	if (exist(m)) {
		throw MasinaRepoException("Exista deja masina cu acest id.");
	}
	this->lista.add(m);
}

void MasinaRepo::del(int id)
{
	auto it = lista.begin();
	while (it != lista.end() && id != (*it).getID()) {
		++it;
	}
	if (it != lista.end()) {
		lista.erase(it);
	}
	else {
		throw MasinaRepoException("Nu exista aceasta masina.");
	}
}


void MasinaRepo::update(const Masina& m)
{
	auto it = lista.begin();
	while (it != lista.end() && m.getID() != (*it).getID()) {
		++it;
	}
	if (it != lista.end()) {
		*it = m;
	}
	else {
		throw MasinaRepoException("Nu exista aceasta masina.");
	}
}

bool MasinaRepo::exist(const Masina& m) const {
	try {
		find(m.getID());
		return true;
	}
	catch (MasinaRepoException&) { return false; }
}

const Masina& MasinaRepo::find(int id) const {
	for (int i = 0; i < lista.size(); i++)
		if (lista[i].getID() == id)
			return lista[i];
	//daca nu exista arunc o exceptie
	throw MasinaRepoException("Nu exista aceasta masina.");
}

const DynamicVector<Masina>& MasinaRepo::getAll() const noexcept {
	return lista;
}


ostream& operator<<(ostream& out, const MasinaRepoException& ex) {
	out << ex.msg;
	return out;
}

void testAdauga() {
	MasinaRepo rep;
	rep.store(Masina{ 20,"a","a", "a", "a" });
	assert(rep.getAll().size() == 1);
	assert(rep.find(20).getNumar() == "a");

	rep.store(Masina{ 35,"b","b", "b", "b" });
	assert(rep.getAll().size() == 2);

	//nu pot adauga 2 cu acelasi id
	try {
		rep.store(Masina{ 35, "b","b", "b", "b" });
		//assert(false);
	}
	catch (const MasinaRepoException&) {
		assert(true);
	}
	//cauta inexistent
	try {
		rep.find(40);
		//assert(false);
	}
	catch (const MasinaRepoException& e) {
		stringstream os;
		os << e;
		assert(os.str().find("exista") >= 0);
	}
}

void testSterge() {
	MasinaRepo rep;
	rep.store(Masina{ 20,"a","a","a","a" });
	rep.store(Masina{ 35,"b","b","b","b" });
	assert(rep.getAll().size() == 2);
	rep.del(20);
	assert(rep.getAll().size() == 1);
	//cauta inexistent
	try {
		rep.del(40);
		//assert(false);
	}
	catch (const MasinaRepoException& e) {
		stringstream os;
		os << e;
		assert(os.str().find("exista") >= 0);
	}
}

void testModifica() {
	MasinaRepo rep;
	rep.store(Masina{ 20,"a","a","a","a" });
	rep.store(Masina{ 35,"b","b","b","b" });
	assert(rep.getAll().size() == 2);
	rep.update(Masina{ 35, "a2", "a2", "a2", "a2" });
	assert(rep.getAll().size() == 2);
	auto m = rep.find(35);
	assert(m.getNumar() == "a2");
	assert(m.getProducator() == "a2");
	assert(m.getModel() == "a2");
	assert(m.getTip() == "a2");
	try {
		rep.update(Masina{ 40, "a2", "a2", "a2", "a2" });
		//assert(false);
	}
	catch (const MasinaRepoException& e) {
		stringstream os;
		os << e;
		assert(os.str().find("exista") >= 0);
	}
}
void testCauta() {
	MasinaRepo rep;
	rep.store(Masina{ 20,"a","a","a","a" });
	rep.store(Masina{ 35,"b","b","b","b" });

	auto m = rep.find(20);
	assert(m.getNumar() == "a");
	assert(m.getProducator() == "a");
	assert(m.getModel() == "a");
	assert(m.getTip() == "a");

	//arunca exceptie daca nu gaseste
	try {
		rep.find(45);
		//assert(false);
	}
	catch (MasinaRepoException&) {
		assert(true);
	}
}

void testeRepo() {
	testAdauga();
	testSterge();
	testModifica();
	testCauta();
}