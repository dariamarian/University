#include "Matrice.h"

#include <exception>

using namespace std;

//teta(1)
Matrice::Matrice(int m, int n) {
	if (n <= 0 || m <= 0)
		throw std::exception();
	linii = m;
	coloane = n;
	root = -1;
	nrElements = 0;
	elem = (cell*)malloc(1000000 * sizeof(cell));
	left = (int*)malloc(1000000 * sizeof(int));
	right = (int*)malloc(1000000 * sizeof(int));
}

//teta(1)
int Matrice::nrLinii() const {
	return linii;
}

//teta(1)
int Matrice::nrColoane() const {
	return coloane;
}

//O(log(NrElem))
TElem Matrice::element(int i, int j) const {
	if (i < 0 || j < 0 || i >= nrLinii() || j >= nrColoane())
		throw std::exception();
	int node = root;
	while (node != -1)
	{
		if (elem[node].lin == i && elem[node].col == j)
		{
			return elem[node].val;
		}
		else if ((elem[node].lin < i) || (elem[node].lin == i && elem[node].col < j)) {
			node = left[node];
		}
		else 
		{
			node = right[node];
		}
	}
	return NULL_TELEMENT;
}

//O(log(NrElem))
int Matrice::insert(int node, int i, int j, TElem e) {
	if (node == -1) {
		int poz = nrElements + 1;
		cell aux;
		aux.lin = i;
		aux.col = j;
		aux.val = e;
		elem[poz] = aux;
		left[poz] = -1;
		right[poz] = -1;
		return poz;
	}
	else {
		if ((elem[node].lin < i) || (elem[node].lin == i && elem[node].col < j))
			left[node] = insert(left[node], i, j, e);
		else
			right[node] = insert(right[node], i, j, e);
	}
	return node;
	nrElements++;
}

//O(log(NrElem))
TElem Matrice::modifica(int i, int j, TElem e) {
	if (i < 0 || j < 0 || i >= nrLinii() || j >= nrColoane())
		throw std::exception();
	int node = root;
	int prec = -1;
	if (node == -1)
	{
		root = insert(root, i, j, e);
		return NULL_TELEMENT;
	}
	else
	{
		while (node != -1)
		{
			prec = node;
			if (elem[node].lin == i && elem[node].col == j)
			{
				TElem v = elem[node].val;
				elem[node].val = e;
				return v;
			}
			else if ((elem[node].lin < i) || (elem[node].lin == i && elem[node].col < j))
			{
				prec = node;
				node = left[node];
			}
			else
			{
				prec = node;
				node = right[node];
			}
		}
	}
	return NULL_TELEMENT;
}