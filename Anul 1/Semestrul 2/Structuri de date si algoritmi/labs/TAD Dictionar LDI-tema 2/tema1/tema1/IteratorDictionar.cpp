#include "IteratorDictionar.h"
#include "Dictionar.h"

using namespace std;

IteratorDictionar::IteratorDictionar(const Dictionar& d) : dict(d) {
	/*
	Complexitate generala: teta(1)
	*/
	curent = dict.prim;
}


void IteratorDictionar::prim() {
	/*
	Complexitate generala: teta(1)
	*/
	curent = dict.prim;
}


void IteratorDictionar::urmator() {
	/*
	Complexitate generala: teta(1)
	*/
	curent=curent->urmator();
}


TElem IteratorDictionar::element() const {
	/*
	Complexitate generala: teta(1)
	*/
	if (valid())
		return curent->element();
	else
		throw ("Exceptie");
}


bool IteratorDictionar::valid() const {
	/*
	Complexitate generala: teta(1)
	*/
	if (curent != nullptr)
		return true;
	return false;
}