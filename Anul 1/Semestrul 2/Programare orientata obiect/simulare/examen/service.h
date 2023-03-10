#pragma once
#include <iostream>
#include <functional>
#include "repo.h"

using namespace std;

class Service
{
protected:
	FileRepo& rep;
public:
	Service(FileRepo& rep) :rep{ rep } {}
	Service(const Service& t) = delete;
	//returneaza lista de telefoane
	vector<Telefon>& srv_getAll() {
		return rep.rep_getAll();
	}
	//sorteaza dupa:
	//cazul 1-pret
	//cazul 2-brand
	//cazul 3-model
	void sortare(int c, vector<Telefon>& list);
};