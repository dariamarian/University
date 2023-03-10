#include "Dictionar.h"
#include <vector>
#include <iostream>
#include <exception>
#include "IteratorDictionar.h"

using namespace std;

Nod::Nod(TElem e, PNod ant, PNod urm) {
	/*
	Complexitate generala: teta(1)
	*/
	this->e = e;
	this->ant = ant;
	this->urm = urm;
}

Dictionar::Dictionar() {
	/*
	Complexitate generala: teta(1)
	*/
	prim = nullptr;
	ultim = nullptr;
}

TElem Nod::element() {
	/*
	Complexitate generala: teta(1)
	*/
	return e;
}

PNod Nod::urmator() {
	/*
	Complexitate generala: teta(1)
	*/
	return urm;
}

Dictionar::~Dictionar() {
	/*
	Complexitate generala: teta(n)
	*/
	IteratorDictionar i = iterator();
	i.prim();
	while (i.valid()) {
		PNod curent = i.curent;
		i.urmator();
		delete curent;
	}
}

// adauga o pereche (cheie, valoare) in dictionar	
// daca exista deja cheia in dictionar, inlocuieste valoarea asociata cheii si returneaza vechea valoare
// daca nu exista cheia, adauga perechea si returneaza null: NULL_TVALOARE
TValoare Dictionar::adauga(TCheie c, TValoare v) {
	/*
	Complexitate generala: O(n)
	*/
	IteratorDictionar i = iterator();
	i.prim();
	while (i.valid()) {
		if (i.curent->e.first == c)
		{
			int oldValue = i.curent->e.second;
			i.curent->e.second = v;
			return oldValue;
		}
		i.urmator();
	}
	// nu exista cheia c daca s-a ajuns aici
	TElem e = make_pair(c, v);
	PNod q = new Nod(e, nullptr, nullptr);
	if (prim == nullptr) 
	{
		prim = q;
		ultim = prim;
	}
	else 
	{
		ultim->urm = q;
		q->ant = ultim;
		ultim = q;
	}
	return NULL_TVALOARE;
}


//cauta o cheie si returneaza valoarea asociata (daca dictionarul contine cheia) sau null
TValoare Dictionar::cauta(TCheie c) const {
	/*
	Complexitate generala: O(n)
	*/
	IteratorDictionar i = iterator();
	i.prim();
	while (i.valid()) {
		if (i.curent->e.first == c)
			return(i.curent->e.second);
		i.urmator();
	}
	return NULL_TVALOARE;
}

//sterge o cheie si returneaza valoarea asociata (daca exista) sau null: NULL_TVALOARE
TValoare Dictionar::sterge(TCheie c) {

	/*
	Complexitate generala: O(n)
	*/
	IteratorDictionar i = iterator();
	i.prim();
	if (!i.valid())
		return NULL_TVALOARE;
	while (i.valid()) {
		if (i.element().first == c) 
		{
			if (i.curent->ant != nullptr and i.curent->urm != nullptr) {
				PNod nod_anterior = i.curent->ant;
				PNod nod_urmator = i.curent->urm;
				nod_anterior->urm = nod_urmator;
				nod_urmator->ant = nod_anterior;
			}
			else if (i.curent->ant == nullptr and i.curent->urm != nullptr) {
				PNod nod_urmator = i.curent->urm;
				nod_urmator->ant = nullptr;
				prim = nod_urmator;
			}
			else if (i.curent->urm == nullptr and i.curent->ant != nullptr) {
				PNod nod_anterior = i.curent->ant;
				nod_anterior->urm = nullptr;
				ultim = nod_anterior;
			}
			else 
			{
				prim = nullptr;
				ultim = nullptr;
			}
			int old = i.element().second;
			delete i.curent;
			return old;
		}
		i.urmator();
	}
	return NULL_TVALOARE;
}


//returneaza un vector cu toate valorile dictionarului 
vector<TValoare> Dictionar::colectiaValorilor() const {
	/*
	Complexitatea in toate cazurile: teta(n)
	*/
	vector <TValoare> vect;
	IteratorDictionar i = iterator();
	i.prim();
	while (i.valid())
	{
		vect.push_back(i.curent->e.second);
		i.urmator();
	}
	return vect;
}

int Dictionar::dim() const {
	/*
	Complexitate generala: teta(n)
	*/
	IteratorDictionar i = iterator();
	int nr = 0;
	PNod curent = prim;
	while (curent != nullptr) {
		nr++;
		curent = curent->urm;
	}
	return nr;
}

bool Dictionar::vid() const {
	/*
	Complexitate generala: teta(1)
	*/
	if (prim == nullptr)
		return true;
	return false;
}


IteratorDictionar Dictionar::iterator() const {
	/*
	Complexitate generala: teta(1)
	*/
	return  IteratorDictionar(*this);
}