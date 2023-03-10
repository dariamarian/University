#include "IteratorMDO.h"
#include "MDO.h"
#include <iostream>
#include <vector>
#include <math.h>
#include <exception>
using namespace std;

int MDO::hash(TElem Element, int I) const
{
	//teta(1)
	return (int)(abs(Element.first)+abs(Element.second) + 0.5 * I + 0.5 * I * I) % size;
}

MDO::MDO(Relatie r) {
	//teta(1)
	count = 0;
	size = INITIAL_SIZE;
	rel = r;
	tabela = std::vector < std::pair < int, int > >(INITIAL_SIZE, { NULL_TELEM , NULL_TELEM });
}


void MDO::adauga(TCheie c, TValoare v) {
	//O(n)
	TElem elem(c,v);
	int index = 0, hashCode;
	do
	{
		hashCode = hash(elem, index);
		if (NULL_TELEM == tabela[hashCode].second)
		{
			tabela[hashCode] = { elem };
			break;
		}
		if (elem == tabela[hashCode])
		{
			tabela[hashCode].second = tabela[hashCode].second + 1;
			break;
		}
		index = index + 1;
	} while (index < size);
	if (index != size)
	{
		count = count + 1;
	}
}

vector<TValoare> MDO::cauta(TCheie c) const {
	//O(n)
	vector <TValoare> vect;
	int index = 0, hashCode = 0;
	do
	{
		if (c == tabela[hashCode].first && NULL_TELEM != tabela[hashCode].second)
		{
			vect.push_back(tabela[hashCode].second);
		}
		hashCode = hashCode + 1;
	} while (hashCode < size);
	return vect;
}

bool MDO::sterge(TCheie c, TValoare v) {
	//O(n)
	TElem elem(c, v);
	int index = 0, hashCode = 0;
	do
	{
		if (elem == tabela[hashCode] && NULL_TELEM != tabela[hashCode].second)
		{
			tabela[hashCode].second = tabela[hashCode].second - 1;
			count = count - 1;
			if (0 == tabela[hashCode].second)
			{
				break;
			}
			return true;
		}
		hashCode = hashCode + 1;
	} while (hashCode < size);
	if (hashCode == size)
	{
		return false;
	}
}

int MDO::dim() const {
	//teta(1)
	return count;
}

bool MDO::vid() const {
	//teta(1)
	return (0 == count);
}

IteratorMDO MDO::iterator() const {
	return IteratorMDO(*this);
}

MDO::~MDO() {
	tabela.~vector();
}
