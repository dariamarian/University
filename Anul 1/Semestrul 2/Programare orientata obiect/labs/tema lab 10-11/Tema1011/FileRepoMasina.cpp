#include "FileRepoMasina.h"
#include "RepoMasina.h"
#include <assert.h>
#include <fstream>
#include <sstream>
#include <cstring>

using namespace std;

//filerepo
void FileRepoMasina::readFromFile()
{
	ifstream in(filepath);
	string line, numar, producator, model, tip,id;
	int id_int;

	while (getline(in, line))
	{
		istringstream stringInput(line);
		//stringInput >> id;
		getline(stringInput, id, ' ');
		getline(stringInput, numar, ' ');
		getline(stringInput, producator, ' ');
		getline(stringInput, model, ' ');
		getline(stringInput, tip, ' ');
		id_int = stoi(id);
		Masina toAdd{ id_int, numar, producator, model, tip };
		MasinaRepo::store(toAdd);
	}

	in.close();
}

void FileRepoMasina::writeToFile()
{
	vector < Masina > masini = MasinaRepo::getAll();
	ofstream out(filepath);
	if (!out.is_open()) cout << "Unable to open file: " + filepath + "\n";
	for (const auto& masina : masini)
	{
		out << masina.getID() << " " << masina.getNumar() << " " << masina.getProducator() << " " << masina.getModel() << " " << masina.getTip() << "\n";
	}
	out.close();
}

FileRepoMasina::FileRepoMasina(std::string FileName) : MasinaRepo()
{
	filepath = "save_files/" + FileName;
	readFromFile();
}

void FileRepoMasina::store(const Masina& m)
{
	MasinaRepo::store(m);
	writeToFile();
}
void FileRepoMasina::del(int id)
{
	MasinaRepo::del(id);
	writeToFile();
}
void FileRepoMasina::update(const Masina& m) 
{
	MasinaRepo::update(m);
	writeToFile();
}

void testAdauga2() {
	FileRepoMasina rep{ "masini2.txt" };
	rep.clearRepo();
	rep.store(Masina{ 20,"a","a", "a", "a" });
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

void testSterge2() {
	FileRepoMasina rep{ "masini2.txt" };
	rep.clearRepo();
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

void testModifica2() {
	FileRepoMasina rep{ "masini2.txt" };
	rep.clearRepo();
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
	try {
		m = rep.findCar(25);
	}
	catch (const MasinaRepoException& ex) {
		stringstream os;
		os << ex;
		assert(os.str().find("exista") >= 0);
	}
}

void testeFileRepo()
{
	testAdauga2();
	testModifica2();
	testSterge2();
}