#pragma once
#include <iostream>
#include <sstream>
using namespace std;

class Telefon
{
	int cod;
	string brand;
	string model;
	int pret;
public:
	//constructor Telefon
	Telefon(int co, const string bra, const string mod, int pr) :cod{ co },brand{ bra },model{ mod }, pret{ pr }{}
	//returneaza codul telefonului
	int getCod() const
	{
		return cod;
	}
	//returneaza brandul telefonului
	string getBrand() const
	{
		return brand;
	}
	//returneaza modelul telefonului
	string getModel() const
	{
		return model;
	}
	//returneaza pretul telefonului
	int getPret() const
	{
		return pret;
	}
	//afisarea pentru lista
	const string tostring();
	//afisarea pentru detalii
	const string tostring2();
};