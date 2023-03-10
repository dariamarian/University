#include "UI.h"

#include "Masina.h"
#include <iostream>
#include <fstream>
#include <string>
#include "Exceptii.h"
using namespace std;

using std::cout;
using std::cin;

void ConsolUI::tipareste(const vector<Masina>& Masini) {
	cout << "Masini:\n";
	for (const auto& Masina : Masini) {
		cout << ' ' << Masina.getID() << ' ' << Masina.getNumar() << ' ' << Masina.getProducator() << ' ' << Masina.getModel() << ' ' << Masina.getTip() << '\n';
	}
	cout << "Sfarsit lista masini\n";
}

void ConsolUI::adaugaUI() {
	string id, nr, pr, md, tp;
	int id_int;
	cout << "Dati ID:";
	getline(cin, id);
	cout << "Dati numar de inmatriculare:";
	getline(cin, nr);
	cout << "Dati producator:";
	getline(cin, pr);
	cout << "Dati model:";
	getline(cin, md);
	cout << "Dati tip:";
	getline(cin, tp);
	if (stoi(id) < 0)
	{
		cout << "Id invalid!\n";
		return;
	}
	if (nr.size() == 0 || nr.size() > 50)
	{
		cout << "Numar de inmatriculare invalid!\n";
		return;
	}
	if (pr.size() == 0 || pr.size() > 50)
	{
		cout << "Producator invalid!\n";
		return;
	}
	if (md.size() == 0 || md.size() > 50)
	{
		cout << "Model invalid!\n";
		return;
	}
	if (tp.size() == 0 || tp.size() > 50)
	{
		cout << "Tip invalid!\n";
		return;
	}
	if (id.size() > 16)
		cout << "ID-ul este prea mare!\n";
	else {
		try
		{
			for (int i = 0; i < id.size(); i++)
				if (id[i] < '0' || id[i] > '9')
					throw string("Id invalid!\n");
		}
		catch (string s)
		{
			cout << s;
			return;
		}
		id_int = stoi(id);
		if (id_int == 0)
			cout << "\n";
		try {
			ctr.addMasina(id_int, nr, pr, md, tp);
			cout << "Adaugata cu succes\n";
		}
		catch (const MasinaRepoException& ex) {
			cout << ex << "\n";
		}
	}
}

void ConsolUI::stergeUI() {
	string id;
	int id_int;
	cout << "Dati ID:";
	getline(cin, id);
	if (stoi(id) < 0)
	{
		cout << "Id invalid!\n";
		return;
	}
	if (id.size() > 16)
		cout << "ID-ul este prea mare!\n";
	else {
		try
		{
			for (int i = 0; i < id.size(); i++)
				if (id[i] < '0' || id[i] > '9')
					throw string("Id invalid!\n");
		}
		catch (string s)
		{
			cout << s;
			return;
		}
		id_int = stoi(id);
		if (id_int == 0)
			cout << "\n";
		try {
			ctr.deleteMasina(id_int);
			cout << "Stearsa cu succes\n";
		}
		catch (const MasinaRepoException& ex) {
			cout << ex << "\n";
		}
	}
}


void ConsolUI::modificaUI() {
	string nr, pr, md, tp, id;
	int id_int;
	cout << "Dati ID-ul masinii pe care doriti sa o modificati:";
	getline(cin, id);
	cout << "Dati noul numar de inmatriculare:";
	getline(cin, nr);
	cout << "Dati noul producator:";
	getline(cin, pr);
	cout << "Dati noul model:";
	getline(cin, md);
	cout << "Dati noul tip:";
	getline(cin, tp);
	if (stoi(id) < 0)
	{	
		cout << "Id invalid!\n";
		return;
	}
	if (nr.size() == 0 || nr.size() > 50)
	{
		cout << "Numar de inmatriculare invalid!\n";
		return;
	}
	if (pr.size() == 0 || pr.size() > 50)
	{
		cout << "Producator invalid!\n";
		return;
	}
	if (md.size() == 0 || md.size() > 50)
	{
		cout << "Model invalid!\n";
		return;
	}
	if (tp.size() == 0 || tp.size() > 50)
	{
		cout << "Tip invalid!\n";
		return;
	}
	if (id.size() > 16)
		cout << "ID-ul este prea mare!\n";
	else {
		try
		{
			for (int i = 0; i < id.size(); i++)
				if (id[i] < '0' || id[i] > '9')
					throw string("Id invalid!\n");
		}
		catch (string s)
		{
			cout << s;
			return;
		}
		id_int = stoi(id);
		if (id_int == 0)
			cout << "\n";
		try {
			ctr.updateMasina(id_int, nr, pr, md, tp);
			cout << "Modificata cu succes\n";
		}
		catch (const MasinaRepoException& ex) {
			cout << ex << "\n";
		}
	}
}
void ConsolUI::addSpalatUI()
{
	string nrI;
	cout << "Introduceti nr. de inmatriculare:\n";
	getline(cin, nrI);
	if (nrI.size() == 0 || nrI.size() > 50)
	{
		cout << "Numar de inmatriculare invalid!\n";
		return;
	}
	try {
		spl.addSpalat(nrI);
		cout << "Adaugata cu succes\n";
	}
	catch (const MasinaRepoException& ex) {
		cout << ex << "\n";
	}
}
void ConsolUI::clearSpalatUI()
{
	spl.clearSpalatServ();
	cout << "Lista de masini s-a golit!\n";
}

void ConsolUI::addRandomUI()
{

	cout << "Cate masini doriti sa adaugati?\n";
	string numar;
	int numar_int = 0;
	getline(cin, numar);
	if (stoi(numar) < 0)
	{
		cout << "Numarul nu poate fi negativ";
		return;
	}
	if (numar.length() > 4)
	{
		cout << "Ati introdus un numar prea mare!\n";
	}
	else {
		try {
			numar_int = stoi(numar);
		}
		catch (const exception&) {
			cout << "Id-ul este invalid!\n";
			return;
		}

		try {
			int cate = spl.addRandom(numar_int);
			cout << "S-au adaugat " << cate << " masini!\n";
		}
		catch (const exception& ex) {
			cout << ex.what();
		}

	}
}

void ConsolUI::cautaUI() {
	string id;
	int id_int = 0;
	cout << "Dati ID:";
	getline(cin, id);
	if (stoi(id) < 0)
	{
		cout << "Id invalid!\n";
		return;
	}
	if (id.size() > 16)
		cout << "ID-ul este prea mare!\n";
	else {
		try
		{
			for (int i = 0; i < id.size(); i++)
				if (id[i] < '0' || id[i] > '9')
					throw string("Id invalid!\n");
		}
		catch (string s)
		{
			cout << s;
			return;
		}
		id_int = stoi(id);
		if (id_int == 0)
			cout << "\n";
		try {
			ctr.cautaMasina(id_int);
			cout << "Gasita cu succes\n";
		}
		catch (const MasinaRepoException& ex) {
			cout << ex << "\n";
		}
	}
}
void ConsolUI::batch_mode()
{
	vector<string>comenzi;
	string delim = " ";

	comenzi.push_back("add 14 inm prod model tip ");
	comenzi.push_back("update 1 inm prod model tip ");
	comenzi.push_back("del 2 ");

	vector<string> words;
	for (auto comanda : comenzi) {
		size_t pos = 0;
		words.clear();

		while ((pos = comanda.find(delim)) != string::npos) {
			words.push_back(comanda.substr(0, pos));
			comanda.erase(0, pos + delim.length());
		}

		if (words[0] == "add") {

			if (words.size() == 6)
				try {
				int id = stoi(words[1]);
				ctr.addMasina(id, words[2], words[3], words[4], words[5]);

			}
			catch (const exception& ex) {
				cout << ex.what();
			}

		}
		else if (words[0] == "update") {
			if (words.size() == 6)
				try {
				ctr.updateMasina(stoi(words[1]), words[2], words[3], words[4], words[5]);

			}
			catch (const exception& ex) {
				cout << ex.what();
			}
		}
		else if (words[0] == "del") {
			if (words.size() == 2)
				try {
				ctr.deleteMasina(stoi(words[1]));
			}
			catch (const exception& ex) {
				cout << ex.what();
			}
		}
		else cout << "Comanda invalida!\n";
	}
}

void ConsolUI::exportt()
{
	fstream fout;
	vector < Masina > masini = spl.getSpalat();
	size_t length = masini.size();
	string fileName = "";
	string path = "fisiere_pt_export/";

	while (fileName == "")
	{
		cout << "Numele fisierului pentru export: ";
		getline(cin, fileName);
	}

	path = path + fileName + ".csv";

	fout.open(path, std::ios::out, std::ios::trunc);
	fout.close();
	fout.open(path, std::ios::out | std::ios::app);

	vector<Masina>v = spl.getSpalat();
	for (const auto& masina : v) {
		fout << masina.getID() << " , " << masina.getNumar() << " , " << masina.getProducator() << " , " << masina.getModel() << " , " << masina.getTip() << '\n';
	}
	cout << "Export cu succes!\n"; 
}

void ConsolUI::undo()
{
	try {
		ctr.undo();
		cout << "Undo cu succes.\n";
	}
	catch (const exception& ex) {
		cout << ex.what();
	}
}

void ConsolUI::start() {
	while (true) {
		cout << "\nIn lista sunt " << spl.numar_masiniSrv() << " masini.\n";
		cout << "Meniu:\n";
		cout << "1. adauga\n";
		cout << "2. sterge\n";
		cout << "3. modifica\n";
		cout << "4. cauta\n";
		cout << "5. tipareste\n";
		cout << "6. sort by numar\n";
		cout << "7. sort by tip\n";
		cout << "8. sort by producator+model\n";
		cout << "9. filtrare producator\n";
		cout << "10. filtrare tip\n";
		cout << "11. adauga dupa numar de inmatriculare\n";
		cout << "12. goleste lista\n";
		cout << "13. genereaza random\n";
		cout << "14. batch mode\n";
		cout << "15. export\n";
		cout << "16. undo\n";
		cout << "0. iesire\n";
		cout << "Dati comanda : ";
		string cmd;
		getline(cin, cmd);
		if (cmd == "1")
			adaugaUI();
		else if (cmd == "2")
			stergeUI();
		else if (cmd == "3")
			modificaUI();
		else if (cmd == "4")
			cautaUI();
		else if (cmd == "5")
			tipareste(ctr.getAll());
		else if (cmd == "6")
			tipareste(ctr.sortByNumar());
		else if (cmd == "7")
			tipareste(ctr.sortByTip());
		else if (cmd == "8")
			tipareste(ctr.sortByProducatorModel());
		else if (cmd == "9")
		{
			cout << "Dati producator: ";
			string producator;
			getline(cin, producator);
			if (producator.size() == 0 || producator.size() > 50)
			{
				cout << "Producator invalid!\n";
				return;
			}
			tipareste(ctr.filtrareProducator(producator));
		}
		else if (cmd == "10")
		{
			cout << "Dati tip: ";
			string tip;
			getline(cin, tip);
			if (tip.size() == 0 || tip.size() > 50)
			{
				cout << "Tip invalid!\n";
				return;
			}
			tipareste(ctr.filtrareTip(tip));
		}
		else if (cmd == "11")
			addSpalatUI();
		else if (cmd == "12")
			clearSpalatUI();
		else if (cmd == "13")
			addRandomUI();
		else if (cmd == "14")
			batch_mode();
		else if (cmd == "15")
			exportt();
		else if (cmd == "16")
			undo();
		else if (cmd == "0")
			return;
		else
			cout << "comanda invalida\n";
	}
}