#pragma once
#include "domain.h"
#include <iostream>
#include <vector>

using namespace std;

class FileRepo {
public:
	FileRepo(const string& filename) : filename{ filename } {
		ReadFromFile();
		WriteToFile();
	};
	//returneaza lista de Telefoane
	vector<Telefon>& rep_getAll();
	//adauga un telefon in lista - o folosim pentru ReadFromFile()
	void rep_store(const Telefon& t);
	~FileRepo() {};
private:
	//citire din fisier
	void ReadFromFile();
	//scriere in fisier
	void WriteToFile();
	string filename;
	//lista mea
	vector <Telefon> all;
};