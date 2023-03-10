#include "Dictionar.h"
#include <iostream>
#include "IteratorDictionar.h"

Dictionar::Dictionar() {
	/*
	Complexitate generala: teta(1)
	*/
	this->Count = 0;
	this->Size = 100000;
	this->Array = new TElem[this->Size];
}

Dictionar::~Dictionar() {
	/*
	Complexitate generala: teta(1)
	*/
	delete[]this->Array;
}

// adauga o pereche (cheie, valoare) in dictionar	
// daca exista deja cheia in dictionar, inlocuieste valoarea asociata cheii si returneaza vechea valoare
// daca nu exista cheia, adauga perechea si returneaza null: NULL_TVALOARE
TValoare Dictionar::adauga(TCheie c, TValoare v) {
	/*
	Complexitate generala: O(n)
	*/
	int index;

	for (index = 0; index < this->Count; index = index + 1)
	{
		if (this->Array[index].first == c)
		{
			int oldValue = this->Array[index].second;
			this->Array[index].second = v;
			return oldValue;
		}
	}

	// nu exista cheia c daca s-a ajuns aici

	this->Array[this->Count].first = c;
	this->Array[this->Count].second = v;
	this->Count = this->Count + 1;

	return NULL_TVALOARE;
}


//cauta o cheie si returneaza valoarea asociata (daca dictionarul contine cheia) sau null
TValoare Dictionar::cauta(TCheie c) const {
	/*
	Complexitate generala: O(n)
	*/
	int index;

	for (index = 0; index < this->Count; index = index + 1)
	{
		if (this->Array[index].first == c)
		{
			return this->Array[index].second;
		}
	}
	return NULL_TVALOARE;
}

//sterge o cheie si returneaza valoarea asociata (daca exista) sau null: NULL_TVALOARE
TValoare Dictionar::sterge(TCheie c) {
	/*
	Complexitate generala: O(n)
	*/
	int index;
	int oldValue;

	for (index = 0; index < this->Count; index = index + 1)
	{
		if (this->Array[index].first == c)
		{
			oldValue = this->Array[index].second;
			for (int index2 = index; index2 < this->Count - 1; index2 = index2 + 1)
			{
				this->Array[index2] = this->Array[index2 + 1];
			}
			this->Count = this->Count - 1;
			return oldValue;
		}
	}
	return NULL_TVALOARE;
}


int Dictionar::dim() const {
	/*
	Complexitate generala: teta(1)
	*/
	return this->Count;
}

bool Dictionar::vid() const {
	/*
	Complexitate generala: teta(1)
	*/
	return (0 == this->Count);
}


IteratorDictionar Dictionar::iterator() const {
	/*
	Complexitate generala: teta(1)
	*/
	return  IteratorDictionar(*this);
}