#pragma once
#include "Melodie.h"
#include <iostream>
#include <vector>

using namespace std;

class FileRepo {
public:
	FileRepo(const string& filename) : filename{ filename } {
		ReadFromFile();
	};
	/*
	* Functia realizeaza citirea din fisier
	* Param de intrare-nu avem
	* Param de iesire-nu avem
	*/
	void ReadFromFile();
	/*
	* Functia realizeaza scrierea in fisier
	* Param de intrare-nu avem
	* Param de iesire-nu avem
	*/
	void WriteToFile();
	/*
	* Functia returneaza lista de melodii
	* Param de intrare-nu avem
	* Param de iesire-returneaza vectorul
	*/
	vector<Melodie>& rep_getAll();
	/*
	* Functia adauga o melodie in lista
	* Param de intrare-Melodie m - o melodie
	* Param de iesire-nu avem
	*/
	void rep_add(Melodie& m);
	/*
	* Functia sterge o melodie din lista
	* Param de intrare-int id - id intreg
	* Param de iesire-nu avem
	*/
	void rep_del(int id);
	/*
	* Functia sterge melodiile din repo. (o folosim doar pentru teste)
	* Param de intrare-nu avem
	* Param de iesire-nu avem
	*/
	void clearRepo();
	~FileRepo() {};
private:
	//numele fisierului
	string filename;
	//lista mea
	vector <Melodie> all;
};

void testRepo();