#pragma once
#include <string>
#include <iostream>

using std::string;
using std::cout;

class Masina {
	int ID;
	std::string numar_inmatriculare;
	std::string producator;
	std::string model;
	std::string tip;
	
public:
	Masina(int id, const string nr, const string p, const string m, const string t) :ID{ id },numar_inmatriculare { nr }, producator{ p }, model{ m }, tip{ t }
	{
		//cout << "constructor\t";
	}
	
	int getID() const{
		return ID;
	}
	string getNumar() const {
		return numar_inmatriculare;
	}
	string getProducator() const {
		return producator;
	}
	string getModel() const {
		return model;
	}
	string getTip() const {
		return tip;
	}

};
/*
Compara dupa numarul de inmatriculare
returneaza true daca m1.numar_inmatriculare e mai mic decat m2.numar_inmatriculare
*/
bool cmpNumar(const Masina& m1, const Masina& m2);

/*
Compara dupa tip
returneaza true daca m1.tip e mai mic decat m2.tip
*/
bool cmpTip(const Masina& m1, const Masina& m2);

