#include "IteratorDictionar.h"
#include "Dictionar.h"

using namespace std;

IteratorDictionar::IteratorDictionar(const Dictionar& d) : dict(d) {
	/*
	Complexitate generala: teta(1)
	*/
	curent = 0;
}


void IteratorDictionar::prim() {
	/*
	Complexitate generala: teta(1)
	*/
	curent = 0;
}


void IteratorDictionar::urmator() {
	/*
	Complexitate generala: teta(1)
	*/
	curent++;
}


TElem IteratorDictionar::element() const {
	/*
	Complexitate generala: teta(1)
	*/
	return dict.Array[curent];
}


bool IteratorDictionar::valid() const {
	/*
	Complexitate generala: teta(1)
	*/
	if (curent < dict.Count)
		return true;
	return false;
}