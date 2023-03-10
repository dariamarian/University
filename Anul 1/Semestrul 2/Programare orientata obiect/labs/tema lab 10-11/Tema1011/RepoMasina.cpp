#include "RepoMasina.h"
#include <assert.h>
#include <iostream>
#include <sstream>
#include <algorithm>
using namespace std;

using std::ostream;
using std::stringstream;

size_t Spalat::numar_masini()
{
	std::vector < Masina > masini;
	masini = getSpalat();
	return masini.size();
}
void MasinaRepo::store(const Masina& m)
{
	if (exist(m)==1) {
		throw MasinaRepoException("Exista deja masina cu acest id.");
	}
	else
		all.push_back(m);
}
 
void MasinaRepo::del(int id)
{
	auto it = find_if(all.begin(), all.end(), [=](const Masina& ma) {return ma.getID() == id; });
	if (it != all.end()) {
		all.erase(it);
		return;
	}
	else {
		throw MasinaRepoException("Nu exista aceasta masina.");
	}
}

void MasinaRepo::update(const Masina& m)
{
	auto it = find_if(all.begin(), all.end(), [&](const Masina& ma) {return ma.getID() == m.getID(); });
	if (it != all.end())
		*it = m;
	else {
		throw MasinaRepoException("Nu exista aceasta masina.");
	}
}

bool MasinaRepo::exist(const Masina& m) const {
	for (const auto& mas : all) {
		if (mas.getID() == m.getID())
			return 1;
	}
	return 0;
}

const Masina& MasinaRepo::find(int id) const {
	for (const auto& m : all) {
		if (m.getID()==id) {
			return m;
		}
	}
	//daca nu exista arunc o exceptie
	throw MasinaRepoException("Nu exista aceasta masina.");
}

const Masina& MasinaRepo::findCar(int id)
{
	auto it = find_if(all.begin(), all.end(), [=](const Masina& m) {return m.getID() == id; });
	if (it != all.end())
	{
		return *it;
	}
	else throw MasinaRepoException("Nu exista masina!\n");
}

const vector<Masina>& MasinaRepo::getAll() const noexcept {
	return all;
}

std::vector < Masina > MasinaRepo::FindCarsAfterNumar(std::string NrToFind)
{
	std::vector < Masina > toReturn;
	for (auto& iterator : all)
	{
		if (iterator.getNumar() == NrToFind)
		{
			toReturn.push_back(iterator);
		}
	}
	return toReturn;
}

void Spalat::add(Masina& ToAdd)
{
	MemorySpalat.push_back(ToAdd);
}

void Spalat::clear()
{
	MemorySpalat.clear();
}

const std::vector < Masina >& Spalat::getSpalat() const
{
	return MemorySpalat;
}

ostream& operator<<(ostream& out, const MasinaRepoException& ex) {
	out << ex.msg;
	return out;
}

void MasinaRepo::clearRepo()
{
	all.clear();
}

size_t MasinaRepo::size() noexcept
{
	return all.size();
}

void testAdauga() {
	MasinaRepo rep;
	rep.store(Masina{ 20,"a","a", "a", "a"});
	assert(rep.getAll().size() == 1);
	assert(rep.find(20).getNumar() == "a");

	rep.store(Masina{ 35,"b","b", "b", "b"});
	assert(rep.getAll().size() == 2);

	//nu pot adauga 2 cu acelasi id
	try {
		rep.store(Masina{35, "b","b", "b", "b"});
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

void testSpalatRepo()
{
	MasinaRepo repo;
	Spalat sp;
	Masina masina1(1, "nr", "pr", "md", "tip");
	Masina masina2(2, "nr2", "pr2", "md2", "tip2");
	Masina masina3(3, "nr3", "pr3", "md3", "tip3");
	Masina masina4(4, "nr4", "pr4", "md4", "tip4");
	std::vector < Masina > masini;

	repo.store(masina1);
	repo.store(masina2);
	repo.store(masina3);
	repo.store(masina4);

	sp.add(masina1);
	sp.add(masina2);
	sp.add(masina3);

	masini = sp.getSpalat();
	assert(masini.size() == 3);

	sp.clear();
	masini = sp.getSpalat();
	assert(masini.size() == 0);
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
		assert(false);
	}
	catch (const MasinaRepoException& e) {
		stringstream os;
		os << e;
		assert(os.str().find("exista") >= 0);
	}
	try {
		m = rep.findCar(25);
	}
	catch (const MasinaRepoException& ex) {
		stringstream os;
		os << ex;
		assert(os.str().find("exista") >= 0);
	}
}
void testCauta() {
	MasinaRepo rep;
	rep.store(Masina{ 20,"a","a","a","a"});
	rep.store(Masina{ 35,"b","b","b","b"});

	auto m = rep.find(20);
	assert(m.getNumar() == "a");
	assert(m.getProducator() == "a");
	assert(m.getModel() == "a");
	assert(m.getTip() == "a");
	
	//arunca exceptie daca nu gaseste
	try {
		rep.find(45);
		assert(false);
	}
	catch (MasinaRepoException&) {
		assert(true);
	}
}

void testClear()
{
	MasinaRepo rep;
	rep.store(Masina{ 20,"a","a","a","a" });
	rep.store(Masina{ 35,"b","b","b","b" });
	rep.clearRepo();
	assert(rep.size() == 0);
}
void testeRepo() {
	testAdauga();
	testSterge();
	testModifica();
	testCauta();
	testSpalatRepo();
	testClear();
}