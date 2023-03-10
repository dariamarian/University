#pragma once
#include <iostream>
#include <functional>
#include "Repo.h"

using namespace std;

class Service
{
protected:
	FileRepo& rep;
public:
	Service(FileRepo& rep) :rep{ rep } {}
	/*
	* Functia returneaza lista de melodii
	* Param de intrare-nu avem
	* Param de iesire-returneaza vectorul
	*/
	vector<Melodie>& srv_getAll();
	/*
	* Functia sorteaza lista de melodii dupa artist
	* Param de intrare-o lista
	* Param de iesire-lista sortata
	*/
	vector<Melodie>& sortare(vector<Melodie>& list);
	/*
	* Functia cauta o melodie dupa id
	* Param de intrare-int id - id intreg
	* Param de iesire-melodia gasita
	*/
	Melodie& srv_find(int id);
	/*
	* Functia adauga o melodie in lista
	* Param de intrare- string titlu - titlul melodiei
						string artist - artistul melodiei
						string gen - genul melodiei
	* Param de iesire-nu avem
	*/
	void srv_add(string titlu, string artist, string gen);
	/*
	* Functia sterge o melodie din lista
	* Param de intrare- int id - id-ul melodiei
	* Param de iesire-nu avem
	*/
	void srv_del(int id);
};

void testService();