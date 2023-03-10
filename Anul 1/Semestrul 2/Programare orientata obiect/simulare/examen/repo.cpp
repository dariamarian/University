#include "repo.h"
#include <fstream>
#include <sstream>

void FileRepo::ReadFromFile()
{
	ifstream in(filename);
	string line, cod, brand, model, pret;
	int cod_int;
	int pret_int;

	while (getline(in, line))
	{
		istringstream stringInput(line);
		getline(stringInput, cod, ' ');
		getline(stringInput, brand, ' ');
		getline(stringInput, model, ' ');
		getline(stringInput, pret, ' ');
		cod_int = stoi(cod);
		pret_int = stoi(pret);
		Telefon toAdd{ cod_int, brand, model, pret_int };
		rep_store(toAdd);
	}
	in.close();
}

void FileRepo::WriteToFile()
{
	vector < Telefon > tel = rep_getAll();
	ofstream out(filename);
	if (!out.is_open()) cout << "Unable to open file: " + filename + "\n";
	for (const auto& t :tel)
	{
		out << t.getCod() << " " << t.getBrand() << " " << t.getModel() << " " << t.getPret() << "\n";
	}
	out.close();
}

void FileRepo::rep_store(const Telefon& t)
{
	all.push_back(t);
	WriteToFile();
}

vector <Telefon>& FileRepo::rep_getAll() {
	return all;
}

