#pragma once

#define NULL_TVALOARE -1
#include <utility>
#include <vector>

using namespace std;

typedef int TCheie;
typedef int TValoare;

typedef std::pair<TCheie, TValoare> TElem;

class IteratorDictionar;

class Nod;
typedef Nod* PNod;

class Nod{
public:

	friend class Dictionar;
	friend class IteratorDictionar;
	//constructor

	Nod(TElem e, PNod ant, PNod urm);
	TElem element();
	PNod urmator();

private:

	TElem e;
	PNod urm;
	PNod ant;
};



class Dictionar {
	friend class IteratorDictionar;
	friend class Nod;

private:
	/* aici e reprezentarea */
	PNod prim;
	PNod ultim;

public:

	// constructorul implicit al dictionarului
	Dictionar();

	// adauga o pereche (cheie, valoare) in dictionar	
	//daca exista deja cheia in dictionar, inlocuieste valoarea asociata cheii si returneaza vechea valoare
	// daca nu exista cheia, adauga perechea si returneaza null: NULL_TVALOARE
	TValoare adauga(TCheie c, TValoare v);

	//cauta o cheie si returneaza valoarea asociata (daca dictionarul contine cheia) sau null: NULL_TVALOARE
	TValoare cauta(TCheie c) const;

	//sterge o cheie si returneaza valoarea asociata (daca exista) sau null: NULL_TVALOARE
	TValoare sterge(TCheie c);

	//returneaza un vector cu toate valorile dictionarului 
	vector<TValoare> colectiaValorilor() const;

	//returneaza numarul de perechi (cheie, valoare) din dictionar 
	int dim() const;

	//verifica daca dictionarul e vid 
	bool vid() const;

	// se returneaza iterator pe dictionar
	IteratorDictionar iterator() const;

	// destructorul dictionarului	
	~Dictionar();

};


