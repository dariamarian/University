#pragma once
#include <iostream>
#include <utility>

typedef int TElem;
typedef std::pair <int, int> linCol;
typedef std::pair <linCol, int> TValue;

#define NULL_TELEMENT 0

class Nod {
public:
	Nod* urm = nullptr;
	Nod* ant = nullptr;
	TValue el;
};

class Matrice {

private:
	Nod* prim;
	int cap, dim;
	int linii, coloane;
	int primLiber, primul;
	int* urm;
	int* ant;
	TValue* e;

	int aloca();

	void dealoca(int i);

	int creeazaNod(TValue e);

	void adauga(TValue e);

public:

	//constructor
	//se arunca exceptie daca nrLinii<=0 sau nrColoane<=0
	Matrice(int nrLinii, int nrColoane);


	//destructor
	~Matrice();

	//returnare element de pe o linie si o coloana
	//se arunca exceptie daca (i,j) nu e pozitie valida in Matrice
	//indicii se considera incepand de la 0
	TElem element(int i, int j) const;


	// returnare numar linii
	int nrLinii() const;

	// returnare numar coloane
	int nrColoane() const;


	// modificare element de pe o linie si o coloana si returnarea vechii valori
	// se arunca exceptie daca (i,j) nu e o pozitie valida in Matrice
	TElem modifica(int i, int j, TElem);

	void redimensioneaza(int numarNouLinii, int numarNouColoane);
};