#include "UI.h"

#include "Masina.h"
#include <iostream>
#include <string>

using std::cout;
using std::cin;

void ConsolUI::sortUI()
{
	string cmd;
	cout << "Sortare dupa: 1.numar 2.tip 3.prod+model\n";
	cin >> cmd;

	if (cmd == "1") {
		DynamicVector<Masina>v = ctr.sort([](const Masina& m1, const Masina& m2) {if (m1.getNumar() < m2.getNumar()) return true; else return false; });

		for (const auto& el : v)
			cout << el.getID() << " " << el.getNumar() << " " << el.getProducator() << " " << el.getModel() << " " << el.getTip() << '\n';


	}
	else if (cmd == "2") {
		DynamicVector<Masina>v = ctr.sort([](const Masina& m1, const Masina& m2) {if (m1.getTip() < m2.getTip()) return true; else return false; });
		for (const auto& el : v)
			cout << el.getID() << " " << el.getNumar() << " " << el.getProducator()  << " " << el.getModel() << " " << el.getTip() << '\n';

	}
	else if (cmd == "3") {
		DynamicVector<Masina>v = ctr.sort([](const Masina& m1, const Masina& m2) {
			if (m1.getProducator() < m2.getProducator())
				return true;
			else if (m1.getProducator() == m2.getProducator())
				if (m1.getModel() < m2.getModel())
					return true;
				else return false;
			else return false;
			});
		for (const auto& el : v)
			cout << el.getID() << " " << el.getNumar() << " " <<el.getProducator()   << " " <<el.getModel() << " " << el.getTip() << '\n';

	}
	else cout << "Comanda invalida\n";
}

void ConsolUI::tipareste(const DynamicVector<Masina>& Masini) {
	cout << "Masini:\n";
	DynamicVector<Masina> v1;
	v1 = Masini;
	for (const auto& Masina : v1) {
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


void ConsolUI::cautaUI() {
	string id;
	int id_int = 0;
	cout << "Dati ID:";
	getline(cin, id);
	if (id.size() > 16)
		cout << "ID-ul este prea mare!\n";
	else {
		try
		{
			for (int i = 0; i < id.size(); i++)
				if (id[i] < '0' || id[i] > '9')
					throw string("Id invalid!\n");
		}
		catch(string s)
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

void ConsolUI::start() {
	while (true) {
		cout << "Meniu:\n";
		cout << "1. adauga\n";
		cout << "2. sterge\n";
		cout << "3. modifica\n";
		cout << "4. cauta\n";
		cout << "5. tipareste\n";
		cout << "6. sort \n";
		cout << "7. filtrare producator\n";
		cout << "8. filtrare tip\n";
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
			sortUI();
		else if (cmd == "7")
		{
			cout << "Dati producator: ";
			string producator;
			getline(cin, producator);
			tipareste(ctr.filtrareProducator(producator));
		}
		else if (cmd == "8")
		{
			cout << "Dati tip: ";
			string tip;
			getline(cin, tip);
			tipareste(ctr.filtrareTip(tip));
		}
		else if (cmd == "0")
			return;
		else
			cout << "comanda invalida\n";
	}
}